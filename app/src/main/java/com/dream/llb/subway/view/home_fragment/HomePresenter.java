package com.dream.llb.subway.view.home_fragment;

import android.util.Log;

import com.dream.llb.subway.model.SubwayLoader;
import com.dream.llb.subway.model.api.SubwayURL;
import com.dream.llb.subway.model.bean.ForumListItem;
import com.dream.llb.subway.view.base.BaseActivity;

/**
 * 首页，展示所有的版块信息
 * Created by llb on 2017-09-12.
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View homeView;

    public HomePresenter(HomeContract.View homeView) {
        this.homeView = homeView;
    }

    @Override
    public void refreshPage() {
        homeView.showProgressDialog();
        SubwayLoader.getInstance().getForumListData(SubwayURL.SUBWAY_HOME).subscribe((ForumListItem response) -> {
            Log.i("llb", "response = " + response);
            homeView.hideProgressDialog();
            if (response instanceof ForumListItem) {
                BaseActivity.forumListItems = response;
                homeView.onFinishRefresh(response);
            }
        }, (Throwable e) -> {
            homeView.hideProgressDialog();
            Log.d("llb", "error " + e.getMessage());
        }, () -> {
            homeView.hideProgressDialog();
            Log.d("llb", "completed");
        });
    }
}
