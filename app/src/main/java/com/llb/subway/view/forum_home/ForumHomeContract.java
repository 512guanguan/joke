package com.llb.subway.view.forum_home;

import com.llb.subway.model.bean.ForumListItem;
import com.llb.subway.model.bean.PostListItem;

/**
 * Created by llb on 2017-09-12.
 */

public interface ForumHomeContract {
    interface Presenter{
        /**
         * 下拉刷新页面
         */
        void refreshPage();
        /**
         * 上拉加载更多
         */
        void loadMoreData(int currentPage);
    }
    interface View{
        void hideProgressDialog();
        void showProgressDialog();
        String getUrl();
        /**
         * 上拉刷新结束
         */
        void onFinishLoadMore(PostListItem response);
        /**
         * 下拉刷新结束
         */
        void onFinishRefresh(PostListItem response);
    }
}
