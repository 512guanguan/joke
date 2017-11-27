package com.llb.subway.view.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.llb.subway.R;
import com.llb.subway.common.helper.ImageHelper;
import com.llb.subway.model.api.SubwayURL;
import com.llb.subway.model.bean.LoginPageResponse;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{
    private LoginPageResponse loginPageResponse;
    private LoginContract.Presenter presenter;
    private ImageView captchaIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        presenter = new LoginPresenter(this);
        presenter.getLoginPageData(SubwayURL.SUBWAY_LOGIN_PAGE);
        captchaIV = (ImageView) findViewById(R.id.captcha_iv);
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
}
