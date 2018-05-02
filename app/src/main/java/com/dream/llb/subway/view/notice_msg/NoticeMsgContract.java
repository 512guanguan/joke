package com.dream.llb.subway.view.notice_msg;

import com.dream.llb.subway.model.bean.LoginPageResponse;
import com.dream.llb.subway.model.bean.LoginResponse;
import com.dream.llb.subway.model.bean.NoticeMsgResponse;

/**
 * Created by llb on 2017-09-12.
 */

public interface NoticeMsgContract {
    interface Presenter {
        void getNoticeData(String readurl, String unreadUrl);
    }

    interface View {
        void setNoticeData(NoticeMsgResponse response);
    }
}
