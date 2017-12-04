package com.dream.llb.subway.model.http;

import com.dream.llb.subway.BuildConfig;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Eliwn on 2017/8/8.
 * 重写HttpLoggingInterceptor，定制自己的打印信息
 */

public final class MyHttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final Charset GBK = Charset.forName("GBK");

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * <p>Example:
         * <pre>{@code
         * url + Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void log(String message);

        /**
         * A {@link Logger} defaults output appropriate for the current platform.
         */
        Logger DEFAULT = new Logger() {
            @Override
            public void log(String message) {
//                Platform.get().log(INFO, message, null);
                if (BuildConfig.DEBUG) {
                    System.out.println(message);
                }
            }
        };
    }

    public MyHttpLoggingInterceptor() {
        this(Logger.DEFAULT);
    }

    public MyHttpLoggingInterceptor(Logger logger) {
        this.logger = logger;
    }

    private final Logger logger;

    private volatile Level level = Level.NONE;

    /**
     * Change the level at which this interceptor logs.
     */
    public MyHttpLoggingInterceptor setLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Level level = this.level;

        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == Level.BODY;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        if (!logBody || !hasRequestBody) {
            logger.log("--> END " + request.method());
        } else if (bodyEncoded(request.headers())) {
            logger.log("--> END " + request.method() + " (encoded body omitted)");
        } else {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = GBK;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(GBK);
            }
            logger.log("");
            if (isPlaintext(buffer)) {
                logger.log(request.url() + "?" + buffer.readString(charset));
                logger.log("--> END " + request.method()
                        + " (" + requestBody.contentLength() + "-byte body)");
            } else {
                logger.log("--> END " + request.method() + " (binary "
                        + requestBody.contentLength() + "-byte body omitted)");
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        logger.log("<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms"  + ')');

            if (!logBody || !HttpHeaders.hasBody(response)) {
                logger.log("<-- END HTTP");
            } else if (bodyEncoded(response.headers())) {
                logger.log("<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = GBK;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(GBK);
                }

                if (!isPlaintext(buffer)) {
                    logger.log("");
                    logger.log("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    return response;
                }

                if (contentLength != 0 && 200==response.code()) {//不是正常的返回信息，不打印
                    logger.log("");
                    logger.log(buffer.clone().readString(charset));
                }

                logger.log("<-- END HTTP (" + buffer.size() + "-byte body)");
            }


        return response;
    }


    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}