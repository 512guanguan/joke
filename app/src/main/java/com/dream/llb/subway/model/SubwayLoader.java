package com.dream.llb.subway.model;


import android.util.Log;

import com.dream.llb.subway.model.bean.PostDetailResponse;
import com.dream.llb.subway.model.bean.ForumListItem;
import com.dream.llb.subway.model.bean.LoginPageResponse;
import com.dream.llb.subway.model.bean.PostListItem;
import com.dream.llb.subway.model.http.DefaultObservableTransformer;
import com.dream.llb.subway.view.base.BaseActivity;

import io.reactivex.Observable;

/**
 * 目前打算把所有没登录态的请求放这里
 * Created by llb on 2017-09-06.
 */

public class SubwayLoader extends BaseLoader {
    private static SubwayLoader instance;

    private SubwayLoader() {
        super();
    }

    public static SubwayLoader getInstance() {
        return instance == null ? new SubwayLoader() : instance;
    }

    /**
     * 获取首页各个论坛分区信息
     *
     * @return
     */
    public Observable<ForumListItem> getForumListData(String url) {
        return requestByGet(url)
                .flatMap((response) -> {//io线程
                    Log.i("llb","数据回来了，等待解析\n" + response);
                    // do something like cache
                    BaseActivity.forumListItems = (new ForumListItem()).new Builder().parse(response);
                    return  Observable.just(BaseActivity.forumListItems);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }

    /**
     * 获取各个分论坛帖子列表信息
     *
     * @return
     */
    public Observable<PostListItem> getPostListData(String url) {
        return requestByGet(url)
                .flatMap((response) -> {//io线程
                    Log.i("llb","数据回来了，等待解析\n" + response);
                    // do something like cache
                    BaseActivity.postListItems = (new PostListItem()).new Builder().parse(response);
                    return  Observable.just(BaseActivity.postListItems);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }

    /**
     * 获取帖子详情页面信息
     *
     * @return
     */
    public Observable<PostDetailResponse> getPostDetailData(String url) {
        return requestByGet(url)
                .flatMap((response) -> {//io线程
                    Log.i("llb","数据回来了，等待解析\n" + response);
                    // do something like cache
                    PostDetailResponse res = (new PostDetailResponse()).new Builder().parsePage(response);
                    return  Observable.just(res);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }

    /**
     * 解析登录页面信息
     * @param url
     * @return
     */
    public Observable<LoginPageResponse> getLoginPage(String url){
        return requestByGet(url, "http://www.ditiezu.com/forum.php?mod=forum")
                .flatMap((response) -> {
                    Log.i("llb","数据回来了，等待解析\n" + response);
                    // do something like cache
                    LoginPageResponse res = (new LoginPageResponse()).new Builder().parsePage(response);
                    return  Observable.just(res);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }
    /**
     * TODO　解析登录页的验证码信息
     * @param url
     * @return
     */
    public Observable<String> getCaptchaImage(String url){
        return requestGzipImageByGet(url,"http://www.ditiezu.com/member.php?mod=logging&action=login&mobile=yes")
//                .flatMap((response) -> {
//                    Log.i("llb","数据回来了，等待解析\n" + response);
//                    // do something like cache
//                    return  Observable.just(response);
//                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }
}


