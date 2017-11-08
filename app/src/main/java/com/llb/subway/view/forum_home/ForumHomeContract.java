package com.llb.subway.view.forum_home;

/**
 * Created by llb on 2017-09-12.
 */

public interface ForumHomeContract {
    public interface Presenter{
        void getPostListData();
    }
    public interface View{
        String getUrl();
        void parsePostListData(String response);
    }
}
