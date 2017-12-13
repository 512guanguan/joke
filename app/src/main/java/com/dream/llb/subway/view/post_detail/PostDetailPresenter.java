package com.dream.llb.subway.view.post_detail;

import android.util.Log;

import com.dream.llb.subway.model.SubwayLoader;
import com.dream.llb.subway.model.bean.PostDetailResponse;

/**
 * Created by llb on 2017-09-12.
 */

public class PostDetailPresenter implements PostDetailContract.Presenter {
    private PostDetailContract.View postDetailView;

    public PostDetailPresenter(PostDetailContract.View postDetailView) {
        this.postDetailView = postDetailView;
    }

    @Override
    public void refreshPage(String url) {
        postDetailView.showProgressDialog();
        SubwayLoader.getInstance().getPostDetailData(url)
                .subscribe((PostDetailResponse response) -> {
                    Log.i("llb", "response = " + response);
                    postDetailView.hideProgressDialog();
                    postDetailView.onFinishRefresh(response);
                }, (Throwable e) -> {
                    postDetailView.hideProgressDialog();
                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
                    postDetailView.hideProgressDialog();
                    Log.d("llb", "completed");
                });
    }

    @Override
    public void loadMoreData(String url,int currentPage) {
        currentPage++;//X-Requested-With:XMLHttpRequest
        //forum.php?mod=viewthread&tid=539523&extra=&ordertype=1&threads=thread&mobile=yes&page=2
        url += "&extra=&ordertype=1&threads=thread";
        SubwayLoader.getInstance().getMoreCommentData(url + "&page="+currentPage, url)
                .subscribe((PostDetailResponse response) -> {
                    Log.i("llb", "response = " + response);
                    postDetailView.onFinishLoadMore(response);
                }, (Throwable e) -> {
                    postDetailView.onFinishLoadMore(null);
                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
                    Log.d("llb", "completed");
                });
    }
}
