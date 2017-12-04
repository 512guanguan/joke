package com.dream.llb.subway.view.home_fragment;

import com.dream.llb.subway.model.bean.ForumListItem;

/**
 * Created by llb on 2017-09-12.
 */

public interface HomeContract {
    public interface Presenter{
        void getPostListData();
    }
    public interface View{
        void parsePostListData(ForumListItem response);
    }
}
