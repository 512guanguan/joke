package com.llb.subway.view.login;

import android.util.Log;

import com.llb.subway.model.SubwayLoader;
import com.llb.subway.model.bean.LoginPageResponse;

/**
 * Created by llb on 2017-09-12.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View loginView;

    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
    }

    @Override
    public void getLoginPageData(String url) {
        SubwayLoader.getInstance().getLoginPage(url)
                .subscribe((LoginPageResponse response) -> {
            Log.i("llb", "response = " + response);
                    loginView.setLoginPageData(response);
        }, (Throwable e) -> {
            Log.d("llb", "error " + e.getMessage());
        }, () -> {
            Log.d("llb", "completed");
        });
    }

    @Override
    public void getCaptchaImage(String url) {
        SubwayLoader.getInstance().getCaptchaImage(url)
                .subscribe((response) -> {
                    Log.i("llb", "存储路径path = " + response);
                    loginView.setCaptchaImage(response);
                }, (Throwable e) -> {
                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
                    Log.d("llb", "completed");
                });
    }

    @Override
    public void login(String url, String name, String password, String captcha) {

    }
}
