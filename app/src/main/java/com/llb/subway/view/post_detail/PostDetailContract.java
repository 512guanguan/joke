package com.llb.subway.view.post_detail;

import com.llb.subway.model.bean.PostDetailResponse;
import com.llb.subway.model.bean.PostListItem;

/**
 * Created by llb on 2017-09-12.
 */

public interface PostDetailContract {
    interface Presenter{
        void getPostDetailData(String url);
    }

    interface View{
        void setPostDetailData(PostDetailResponse response);
    }
}
