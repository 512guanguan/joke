package com.dream.llb.subway.view.post_detail;

import com.dream.llb.subway.model.bean.PostDetailResponse;

/**
 * Created by llb on 2017-09-12.
 */

public interface PostDetailContract {
    interface Presenter{
//        void getPostDetailData(String url);
        /**
         * 下拉刷新页面
         */
        void refreshPage(String url);
        /**
         * 上拉加载更多
         */
        void loadMoreData(String url,int currentPage);
    }

    interface View{
//        void setPostDetailData(PostDetailResponse response);
        void hideProgressDialog();
        void showProgressDialog();
        /**
         * 上拉刷新结束
         */
        void onFinishLoadMore(PostDetailResponse response);
        /**
         * 下拉刷新结束
         */
        void onFinishRefresh(PostDetailResponse response);
    }
}
