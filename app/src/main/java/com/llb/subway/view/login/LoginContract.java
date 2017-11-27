package com.llb.subway.view.login;

import com.llb.subway.model.bean.LoginPageResponse;

/**
 * Created by llb on 2017-09-12.
 */

public interface LoginContract {
    interface Presenter{
        void getLoginPageData(String url);
        void getCaptchaImage(String url);
    }

    interface View{
        void setLoginPageData(LoginPageResponse response);
        void setCaptchaImage(String path);
    }
}
