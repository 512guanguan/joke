package com.dream.llb.subway.view.home_fragment;

import com.dream.llb.subway.model.bean.HomePageResponse;

/**
 * Created by llb on 2017-09-12.
 */

public interface HomeContract {
    public interface Presenter{
        /**
         * 下拉刷新页面
         */
        void refreshPage();
    }
    public interface View{
        void hideProgressDialog();
        void showProgressDialog();
        /**
         * 下拉刷新结束
         */
        void onFinishRefresh(HomePageResponse response);
    }
}
