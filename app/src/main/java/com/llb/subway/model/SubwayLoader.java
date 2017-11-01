package com.llb.subway.model;


import com.llb.subway.model.api.HttpUtil;
import com.llb.subway.model.api.SubwayURL;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
/**
 * Created by llb on 2017-09-06.
 */

public class SubwayLoader {
    private static SubwayLoader instance;

    public static SubwayLoader getInstance() {
        return instance == null ? new SubwayLoader() : instance;
    }

    public Observable<String> getPostListData() {
        return Observable.just("")
                .observeOn(Schedulers.io())
                .map((s) -> {
                            try {
                                Response res = HttpUtil.getInstance()
                                        .get(SubwayURL.SUBWAY_HOME)
                                        .addHeader("Accept-Encoding","gzip,deflate")
                                        .addHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6")
                                        .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                                        .addHeader("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1")
//                    .addParameter("username", userName)
                                        .execute();
//                                return new String(res.body().bytes(), "utf-8");
                                InputStream inputStream = new ByteArrayInputStream(res.body().bytes());
                                return zipInputStream(inputStream);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return "";
                        }
                )
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 处理gzip,deflate返回流
     *
     * @param is
     * @return
     * @throws IOException
     */
    private String zipInputStream(InputStream is) throws IOException {
        GZIPInputStream gzip = new GZIPInputStream(is);
        BufferedReader in = new BufferedReader(new InputStreamReader(gzip, "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null)
            buffer.append(line + "\n");
        is.close();
        return buffer.toString();
    }
}


