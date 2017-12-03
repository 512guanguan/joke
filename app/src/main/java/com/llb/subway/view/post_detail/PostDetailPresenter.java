package com.llb.subway.view.post_detail;

import android.util.Log;

import com.llb.subway.model.SubwayLoader;
import com.llb.subway.model.bean.PostDetailResponse;
import com.llb.subway.model.bean.PostListItem;
import com.llb.subway.view.base.BaseActivity;

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
        currentPage++;
        SubwayLoader.getInstance().getPostDetailData(url+"&page="+currentPage)
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
