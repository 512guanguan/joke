package com.llb.subway.model;

import com.llb.subway.model.api.HttpUtil;
import com.llb.subway.model.api.SubwayURL;

import java.io.IOException;

import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                                return new String(res.body().bytes(), "utf-8");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return "";
                        }
                )
                .observeOn(AndroidSchedulers.mainThread());
    }
}


