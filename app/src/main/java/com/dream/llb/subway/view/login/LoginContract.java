package com.dream.llb.subway.view.login;

import com.dream.llb.subway.model.bean.LoginPageResponse;
import com.dream.llb.subway.model.bean.LoginResponse;

/**
 * Created by llb on 2017-09-12.
 */

public interface LoginContract {
    interface Presenter {
        void getLoginPageData(String url);

        void getCaptchaImage(String url);

        void login(String url, String name, String password, String captcha, LoginPageResponse response);
    }

    interface View {
        void setLoginPageData(LoginPageResponse response);

        void setCaptchaImage(String path);

        void onLoginSuccess();

        void onLoginFailed(LoginResponse response);
    }
}
