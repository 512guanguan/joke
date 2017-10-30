package com.llb.subway.view;

/**
 * Created by llb on 2017-09-12.
 */

public interface SubwayFirstContract {
    public interface Presenter{
        void getPostListData();
    }
    public interface View{
        void parsePostListData();
    }
}
