package com.dream.llb.subway.model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dream.llb.subway.common.helper.ImageHelper;
import com.dream.llb.subway.model.api.PostRequest;
import com.dream.llb.subway.model.api.GetRequest;
import com.dream.llb.subway.model.api.HttpUtil;
import com.dream.llb.subway.view.base.BaseApplication;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
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
     * @param referer 额外的请求头
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
     * gzip压缩的图片网络请求
     * 目前仅有验证码图片是这个方式请求的
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
    protected Observable<String> requestByPost(String url, HashMap<String,String> data) {
        return Observable.just("")
                .observeOn(Schedulers.io())
                .map((s) -> {
                            try {
                                PostRequest request = HttpUtil.getInstance().post(url,data);
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
     * 处理gzip,deflate页面数据返回流
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
        String path = ImageHelper.saveImage(BaseApplication.mContext,bitmap,100);
        gzip.close();
        return path;
    }
}


