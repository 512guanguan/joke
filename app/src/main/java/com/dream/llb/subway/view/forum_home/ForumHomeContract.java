package com.dream.llb.subway.view.forum_home;

import com.dream.llb.subway.model.bean.PostListItem;

/**
 * Created by llb on 2017-09-12.
 */

public interface ForumHomeContract {
    interface Presenter{
        void getPostListData();
    }
    interface View{
        String getUrl();
        void parsePostListData(PostListItem response);
    }
}
