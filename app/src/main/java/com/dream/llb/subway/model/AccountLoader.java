package com.dream.llb.subway.model;


import android.util.Log;

import com.dream.llb.subway.model.bean.LoginResponse;
import com.dream.llb.subway.model.http.DefaultObservableTransformer;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * 目前打算把所有登录态的请求放这里
 * Created by llb on 2017-09-06.
 */

public class AccountLoader extends BaseLoader {
    private static AccountLoader instance;

    private AccountLoader() {
        super();
    }

    public static AccountLoader getInstance() {
        return instance == null ? new AccountLoader() : instance;
    }

    /**
     * 解析登录操作返回页面信息
     * @param url
     * @return
     */
    public Observable<LoginResponse> login(String url, HashMap<String,String> data){
        return requestByPost(url,data)
                .flatMap((response) -> {
                    Log.i("llb","数据回来了，等待解析\n" + response);
                    LoginResponse res = (new LoginResponse()).new Builder().parsePage(response);
                    return  Observable.just(res);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }
}


