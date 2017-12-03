package com.llb.subway.view.home_fragment;

import com.llb.subway.model.bean.ForumListItem;

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
        void onFinishRefresh(ForumListItem response);
    }
}
