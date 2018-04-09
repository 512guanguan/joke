package com.dream.llb.subway.view.login;

import android.util.Log;

import com.dream.llb.subway.model.AccountLoader;
import com.dream.llb.subway.model.SubwayLoader;
import com.dream.llb.subway.model.bean.LoginPageResponse;

import java.util.HashMap;

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
        SubwayLoader.getInstance().getCaptchaImage(url,"http://www.ditiezu.com/member.php?mod=logging&action=login&mobile=yes")
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
    public void login(String url, String name, String password, String captcha,LoginPageResponse loginPageResponse) {
        HashMap<String,String> data = new HashMap<>();
        /**
         * formhash:673e7f41
         referer:http://www.ditiezu.com/forum.php?mod=forum
         username:路飞llb
         password:lailubo
         sechash:SkSzW
         seccodeverify:evec
         questionid:0
         answer:
         cookietime:2592000
         submit:登录
         */
        data.put("formhash",loginPageResponse.formHash);
        data.put("username",name);
        data.put("password",password);
        data.put("sechash",loginPageResponse.secHash);
        data.put("seccodeverify",captcha);
        data.put("questionid","0");
        data.put("answer","");
        data.put("cookietime",loginPageResponse.cookieTime);
        data.put("submit",loginPageResponse.submit);
        data.put("referer",loginPageResponse.referer);
        AccountLoader.getInstance().login(url,data,loginPageResponse.referer)
                .subscribe((response) -> {
                    Log.i("llb", "存储路径path = " + response);
                    loginView.onLoginSuccess();
                }, (Throwable e) -> {
                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
                    Log.d("llb", "completed");
                });
    }
}
