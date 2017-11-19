package com.llb.subway.view.login;

import com.llb.subway.model.bean.LoginPageResponse;
import com.llb.subway.model.bean.PostDetailResponse;

/**
 * Created by llb on 2017-09-12.
 */

public interface LoginContract {
    interface Presenter{
        void getLoginPageData(String url);
    }

    interface View{
        void setLoginPageData(LoginPageResponse response);
    }
}
