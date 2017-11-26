package com.llb.subway.view.login;

import android.util.Log;

import com.llb.subway.model.SubwayLoader;
import com.llb.subway.model.bean.LoginPageResponse;
import com.llb.subway.model.bean.PostDetailResponse;
import com.llb.subway.view.post_detail.PostDetailContract;

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
                    Log.i("llb", "response = " + response);
                }, (Throwable e) -> {
                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
                    Log.d("llb", "completed");
                });
    }
}