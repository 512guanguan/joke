package com.llb.subway.view.home_fragment;

/**
 * Created by llb on 2017-09-12.
 */

public interface HomeContract {
    public interface Presenter{
        void getPostListData();
    }
    public interface View{
        void parsePostListData(String response);
    }
}
