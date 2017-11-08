package com.llb.subway.view.forum_home;

import android.util.Log;

import com.llb.subway.model.SubwayLoader;

/**
 * Created by llb on 2017-09-12.
 */

public class ForumHomePresenter implements ForumHomeContract.Presenter {
    private ForumHomeContract.View forumHomeView;

    public ForumHomePresenter(ForumHomeContract.View forumHomeView) {
        this.forumHomeView = forumHomeView;
    }

    @Override
    public void getPostListData() {
        SubwayLoader.getInstance().getPostListData(forumHomeView.getUrl()).subscribe((String response) -> {
            Log.i("llb", "response = " + response);
            forumHomeView.parsePostListData(response);
        }, (Throwable e) -> {
            Log.d("llb", "error " + e.getMessage());
        }, () -> {
            Log.d("llb", "completed");
        });
    }
}
