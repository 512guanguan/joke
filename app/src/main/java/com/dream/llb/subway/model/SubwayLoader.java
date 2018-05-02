package com.dream.llb.subway.model;


import android.util.Log;

import com.dream.llb.subway.model.bean.EditCommentPageResponse;
import com.dream.llb.subway.model.bean.EditPostPageResponse;
import com.dream.llb.subway.model.bean.HomePageResponse;
import com.dream.llb.subway.model.bean.LoginPageResponse;
import com.dream.llb.subway.model.bean.NoticeMsgResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse;
import com.dream.llb.subway.model.bean.PostListItem;
import com.dream.llb.subway.model.http.DefaultObservableTransformer;

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
    public Observable<HomePageResponse> getForumListData(String url) {
        return requestByGet(url)
                .flatMap((response) -> {//io线程
//                    Log.i("llb", "数据回来了，等待解析\n" + response);
                    // do something like cache
                    HomePageResponse homePageResponse = (new HomePageResponse()).new Builder().parse(response);
                    return Observable.just(homePageResponse);
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
//                    Log.i("llb", "数据回来了，等待解析\n" + response);
                    // do something like cache
                    PostListItem postListItems = (new PostListItem()).new Builder().parse(response);
                    return Observable.just(postListItems);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }
    /**
     * 获取提醒消息的列表信息
     *
     * @return
     */
    public Observable<NoticeMsgResponse> getNoticeListData(String url) {
        return requestByGet(url)
                .flatMap((response) -> {//io线程
//                    Log.i("llb", "数据回来了，等待解析\n" + response);
                    // do something like cache
                    NoticeMsgResponse res = (new NoticeMsgResponse()).new Builder().parsePage(response);
                    return Observable.just(res);
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
//                    Log.i("llb", "数据回来了，等待解析\n" + response);
                    // do something like cache
                    PostDetailResponse res = (new PostDetailResponse()).new Builder().parsePage(response);
                    res.currentPageUrl = url;
                    return Observable.just(res);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }

    /**
     * 获取更多评论内容信息
     *
     * @return
     */
    public Observable<PostDetailResponse> getMoreCommentData(String url, String referer) {
        return requestByGet(url,referer, true)
                .flatMap((response) -> {//io线程
//                    Log.i("llb", "数据回来了，等待解析\n" + response);
                    // do something like cache
                    PostDetailResponse res = (new PostDetailResponse()).new Builder().parsePage(response);
                    return Observable.just(res);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }

    /**
     * 解析登录页面信息
     *
     * @param url
     * @return
     */
    public Observable<LoginPageResponse> getLoginPage(String url) {
        return requestByGet(url, "http://www.ditiezu.com/forum.php?mod=forum", false)
                .flatMap((response) -> {
//                    Log.i("llb", "数据回来了，等待解析\n" + response);
                    // do something like cache
                    LoginPageResponse res = (new LoginPageResponse()).new Builder().parsePage(response);
                    return Observable.just(res);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }

    /**
     * 解析评论编辑页面信息
     *
     * @param url
     * @return
     */
    public Observable<EditCommentPageResponse> getEditCommentPage(String url,String referer) {
        return requestByGet(url, referer, false)
                .flatMap((response) -> {
//                    Log.i("llb", "数据回来了，等待解析\n" + response);
                    // do something like cache
                    EditCommentPageResponse res = (new EditCommentPageResponse()).new Builder().parse(response);
                    return Observable.just(res);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }
    /**
     * 解析帖子编辑页面信息
     * @param url
     * @return
     */
    public Observable<EditPostPageResponse> getEditPostPage(String url, String referer) {
        return requestByGet(url, referer, false)
                .flatMap((response) -> {
//                    Log.i("llb", "编辑帖子页面数据回来了，等待解析\n" + response);
                    // do something like cache
                    EditPostPageResponse res = (new EditPostPageResponse()).new Builder().parse(response);
                    return Observable.just(res);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }

    /**
     * TODO　解析登录页的验证码信息
     *
     * @param url
     * @return
     */
    public Observable<String> getCaptchaImage(String url,String referer) {
        return requestGzipImageByGet(url, referer)
//                .flatMap((response) -> {
//                    Log.i("llb","数据回来了，等待解析\n" + response);
//                    // do something like cache
//                    to_left  Observable.just(response);
//                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }
}


