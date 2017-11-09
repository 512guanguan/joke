package com.llb.subway.view.home_fragment;

import android.util.Log;

import com.llb.subway.model.SubwayLoader;
import com.llb.subway.model.api.SubwayURL;
import com.llb.subway.model.bean.ForumListItem;

/**
 * Created by llb on 2017-09-12.
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View homeView;

    public HomePresenter(HomeContract.View homeView) {
        this.homeView = homeView;
    }

    @Override
    public void getPostListData() {
        SubwayLoader.getInstance().getForumListData(SubwayURL.SUBWAY_HOME).subscribe((ForumListItem response) -> {
            Log.i("llb", "response = " + response);
            homeView.parsePostListData(response);
        }, (Throwable e) -> {
            Log.d("llb", "error " + e.getMessage());
        }, () -> {
            Log.d("llb", "completed");
        });
    }
}
