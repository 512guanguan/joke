package com.dream.llb.subway.view.edit_comment;

import com.dream.llb.subway.model.bean.EditCommentPageResponse;
import com.dream.llb.subway.model.bean.LoginPageResponse;

/**
 * Created by llb on 2017-09-12.
 */

public interface EditCommentContract {
    interface Presenter{
        void getPageData(String url,String referer);
        void getCaptchaImage(String url, String referer);
    }

    interface View{
        void setPageData(EditCommentPageResponse response);
        void setCaptchaImage(String path);
    }
}
