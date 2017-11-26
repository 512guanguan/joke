package com.llb.subway.model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.llb.subway.model.api.GetRequest;
import com.llb.subway.model.api.HttpUtil;
import com.llb.subway.model.bean.ForumListItem;
import com.llb.subway.model.bean.PostDetailResponse;
import com.llb.subway.model.bean.PostListItem;
import com.llb.subway.model.http.DefaultObservableTransformer;
import com.llb.subway.view.base.BaseActivity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

/**
 * Created by llb on 2017-09-06.
 */

public class BaseLoader {
    /**
     * 通用的网络请求
     *
     * @param url
     * @return
     */
    protected Observable<String> requestByGet(String url, String referer) {
        return Observable.just("")
                .observeOn(Schedulers.io())
                .map((s) -> {
                            try {
                                GetRequest request = HttpUtil.getInstance().get(url);
                                request.addHeader("Referer",referer);
                                Response res = HttpUtil.addCommonHeaders(request).execute();
                                InputStream inputStream = new ByteArrayInputStream(res.body().bytes());
                                return zipInputStream(inputStream);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return "";
                        }
                );
    }

    /**
     * 通用的gzip图片网络请求
     *
     * @param url
     * @return
     */
    protected Observable<String> requestGzipImageByGet(String url, String referer) {
        return Observable.just("")
                .observeOn(Schedulers.io())
                .map((s) -> {
                            try {
                                GetRequest request = HttpUtil.getInstance().get(url);
                                request.addHeader("Referer",referer);
                                Response res = HttpUtil.addCommonHeaders(request).execute();
                                InputStream inputStream = new ByteArrayInputStream(res.body().bytes());
                                return zipImageInputStream(inputStream);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return "";
                        }
                );
    }

    /**
     * 通用的网络请求
     *
     * @param url
     * @return
     */
    protected Observable<String> requestByGet(String url) {
        return Observable.just("")
                .observeOn(Schedulers.io())
                .map((s) -> {
                            try {
                                GetRequest request = HttpUtil.getInstance().get(url);
                                Response res = HttpUtil.addCommonHeaders(request).execute();
                                InputStream inputStream = new ByteArrayInputStream(res.body().bytes());
                                return zipInputStream(inputStream);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return "";
                        }
                );
    }

    /**
     * 处理gzip,deflate返回流
     *
     * @param is
     * @return
     * @throws IOException
     */
    protected String zipInputStream(InputStream is) throws IOException {
        GZIPInputStream gzip = new GZIPInputStream(is);
        BufferedReader in = new BufferedReader(new InputStreamReader(gzip, "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null)
            buffer.append(line + "\n");
        is.close();
        return buffer.toString();
    }

    /**
     * 处理gzip图片返回流
     * Content-Encoding: gzip
     *Content-Type: image/bmp
     * @param is
     * @return
     * @throws IOException
     */
    protected String zipImageInputStream(InputStream is) throws IOException {
        GZIPInputStream gzip = new GZIPInputStream(is);
        Bitmap bitmap = BitmapFactory.decodeStream(gzip);
        String path = savePicture(bitmap);
        gzip.close();
        return path;
    }

    protected String savePicture(Bitmap bitmap) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/captcha"+".jpg";
        File file = new File(path);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}


