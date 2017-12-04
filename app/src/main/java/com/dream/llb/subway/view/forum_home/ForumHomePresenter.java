package com.dream.llb.subway.view.forum_home;

import android.util.Log;

import com.dream.llb.subway.model.SubwayLoader;
import com.dream.llb.subway.model.bean.PostListItem;
import com.dream.llb.subway.view.base.BaseActivity;


/**
 * Created by llb on 2017-09-12.
 */

public class ForumHomePresenter implements ForumHomeContract.Presenter {
    private ForumHomeContract.View forumHomeView;

    public ForumHomePresenter(ForumHomeContract.View forumHomeView) {
        this.forumHomeView = forumHomeView;
    }

    @Override
    public void refreshPage() {
        forumHomeView.showProgressDialog();
        SubwayLoader.getInstance().getPostListData(forumHomeView.getUrl())
                .subscribe((PostListItem response) -> {
                    Log.i("llb", "response = " + response);
                    forumHomeView.hideProgressDialog();
                    if (response instanceof PostListItem) {
                        BaseActivity.postListItems = response;
                        forumHomeView.onFinishRefresh(response);
                    }
                }, (Throwable e) -> {
                    forumHomeView.hideProgressDialog();
                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
                    forumHomeView.hideProgressDialog();
                    Log.d("llb", "completed");
                });
    }

    @Override
    public void loadMoreData(int currentPage) {
        currentPage++;
        SubwayLoader.getInstance().getPostListData(forumHomeView.getUrl()+"&page="+currentPage)
                .subscribe((PostListItem response) -> {
                    Log.i("llb", "response = " + response);
                    if (response instanceof PostListItem) {
                        BaseActivity.postListItems.postList.addAll(response.postList);
                    }
                    forumHomeView.onFinishLoadMore(response);
                }, (Throwable e) -> {
                    forumHomeView.onFinishLoadMore(null);
                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
                    Log.d("llb", "completed");
                });
    }
}
