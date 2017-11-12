package com.llb.subway.model;


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

public class SubwayLoader {
    private static SubwayLoader instance;

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
                    Log.i("llb","数据那回来了，等待解析\n" + response);
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
                    Log.i("llb","数据那回来了，等待解析\n" + response);
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
                    Log.i("llb","数据那回来了，等待解析\n" + response);
                    // do something like cache
                    PostDetailResponse res = (new PostDetailResponse()).new Builder().parsePage(response);
                    return  Observable.just(res);
                })
                .compose(DefaultObservableTransformer.defaultTransformer());
    }

    /**
     * 通用的网络请求
     *
     * @param url
     * @return
     */
    private Observable<String> requestByGet(String url) {
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
//                .observeOn(AndroidSchedulers.mainThread());
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


