package com.dream.llb.subway.view.edit_post;

import com.dream.llb.subway.model.bean.EditPostPageResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse;

/**
 * Created by llb on 2017-09-12.
 */

public interface EditPostContract {
    interface Presenter {
        void getPageData(String url, String referer);

        void getCaptchaImage(String url, String referer);

        void submitPostData(String url, String referer, EditPostPageResponse response, String content,
                            String title, String typeID, String captcha);
    }

    interface View {
        void setPageData(EditPostPageResponse response);

        void setCaptchaImage(String path);

        void onSubmitPostSuccess(PostDetailResponse response);
    }
}
