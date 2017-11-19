package com.llb.subway.view.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.llb.subway.R;
import com.llb.subway.model.api.SubwayURL;
import com.llb.subway.model.bean.LoginPageResponse;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{
    private LoginPageResponse loginPageResponse;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        presenter = new LoginPresenter(this);
        presenter.getLoginPageData(SubwayURL.SUBWAY_LOGIN_PAGE);
    }

    @Override
    public void setLoginPageData(LoginPageResponse response) {
        loginPageResponse = response;
        Toast.makeText(this, "数据解析完了", Toast.LENGTH_SHORT).show();
    }
}
