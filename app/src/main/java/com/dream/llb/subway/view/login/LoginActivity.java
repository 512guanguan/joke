package com.dream.llb.subway.view.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.helper.ImageHelper;
import com.dream.llb.subway.model.api.SubwayURL;
import com.dream.llb.subway.model.bean.LoginPageResponse;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener{
    private LoginPageResponse loginPageResponse;
    private LoginContract.Presenter presenter;
    private ImageView captchaIV;
    private EditText nameTV, passwordTV, captchaTV;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        presenter = new LoginPresenter(this);
        presenter.getLoginPageData(SubwayURL.SUBWAY_LOGIN_PAGE);
        captchaIV = (ImageView) findViewById(R.id.captcha_iv);
        nameTV = (EditText) findViewById(R.id.name_tv);
        passwordTV = (EditText) findViewById(R.id.password_tv);
        captchaTV = (EditText) findViewById(R.id.captcha_tv);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void setLoginPageData(LoginPageResponse response) {
        loginPageResponse = response;
        Toast.makeText(this, "数据解析完了", Toast.LENGTH_SHORT).show();
        loadCaptchaImage(SubwayURL.SUBWAY_BASE + response.CAPTCHA_URL);
    }

    @Override
    public void setCaptchaImage(String path) {
        if(!TextUtils.isEmpty(path)){
            Bitmap bitmap = ImageHelper.getBitmap(this, path);
            captchaIV.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onLoginSuccess() {

    }

    public void loadCaptchaImage(String url){
        Log.i("llb","captcha url = "+url);
        presenter.getCaptchaImage(url);

//        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new CommonHeaderInterceptor())
//                .build();
//        new Picasso.Builder(this)
//                .downloader(new OkHttp3Downloader(okHttpClient))
//                .build()
//                .load(url)
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .networkPolicy(NetworkPolicy.NO_CACHE)
//                .placeholder(R.mipmap.ic_launcher)//下载中显示的图片
//                .error(R.mipmap.ic_launcher)//下载失败显示的图片
//                .into(captchaIV);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                login();
                break;
        }
    }

    private void login() {
        String name = nameTV.getText().toString();
        String password = passwordTV.getText().toString();
        String captcha = captchaTV.getText().toString();
        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(password)||TextUtils.isEmpty(captcha)){
            Toast.makeText(this,"请先填写账户信息",Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.login(SubwayURL.SUBWAY_BASE+loginPageResponse.loginURL,name,password,captcha,loginPageResponse);
    }
}
