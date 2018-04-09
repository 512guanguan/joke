package com.dream.llb.subway.model;


import android.text.TextUtils;
import android.util.Log;

import com.dream.llb.subway.model.bean.BaseResponse;
import com.dream.llb.subway.model.bean.LoginResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse;
import com.dream.llb.subway.model.bean.WarningPageResponse;
import com.dream.llb.subway.model.http.DefaultObservableTransformer;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
    public Observable<LoginResponse> login(String url, HashMap<String,String> data,String referer){
        return requestByPost(url,data,referer)
                .debounce(300, TimeUnit.MICROSECONDS)
                .flatMap((response) -> {
                    Log.i("llb","数据回来了，等待解析\n" + response);
                    LoginResponse res = (new LoginResponse()).new Builder().parsePage(response);
                    return  Observable.just(res);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }

    /**
     * 发表评论信息
     * @param url
     * @return
     */
    public Observable<BaseResponse> postComment(String url, HashMap<String,String> data, String referer){
        return requestByPost(url,data,referer)
                .debounce(300, TimeUnit.MICROSECONDS)
                .flatMap((response) -> {
                    Log.i("llb","评论发送后的页面：\n" + response);
                    try {
                        PostDetailResponse res = (new PostDetailResponse()).new Builder().parsePage(response);
                        if(!TextUtils.isEmpty(res.author)){
                            return Observable.just(res);
                        }else {
                            WarningPageResponse warningPageResponse = (new WarningPageResponse()).new Builder().parsePage(response);
                            if(!TextUtils.isEmpty(warningPageResponse.warning)){
                                return Observable.just(warningPageResponse);
                            }
                        }
                    }catch (Exception e){
                        return Observable.just(null);
                    }
                    return  Observable.just(null);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }
    /**
     * 发表帖子
     * @param url
     * @return
     */
    public Observable<BaseResponse> submitPost(String url, HashMap<String,String> data, String referer){
        return requestByPost(url,data,referer)
                .debounce(300, TimeUnit.MICROSECONDS)
                .flatMap((response) -> {
                    Log.i("llb","帖子发送后的页面：\n" + response);
                    try {
                        PostDetailResponse res = (new PostDetailResponse()).new Builder().parsePage(response);
                        if(!TextUtils.isEmpty(res.author)){
                            return Observable.just(res);
                        }else {
                            WarningPageResponse warningPageResponse = (new WarningPageResponse()).new Builder().parsePage(response);
                            if(!TextUtils.isEmpty(warningPageResponse.warning)){
                                return Observable.just(warningPageResponse);
                            }
                        }
                    }catch (Exception e){
                        return Observable.just(null);
                    }
                    return  Observable.just(null);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }
}


