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
    public void getPostDetailData(String url) {
        SubwayLoader.getInstance().getPostDetailData(url)
                .subscribe((PostDetailResponse response) -> {
            Log.i("llb", "response = " + response);
                    postDetailView.setPostDetailData(response);
        }, (Throwable e) -> {
            Log.d("llb", "error " + e.getMessage());
        }, () -> {
            Log.d("llb", "completed");
        });
    }
}
