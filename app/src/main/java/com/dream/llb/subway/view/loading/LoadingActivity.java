package com.dream.llb.subway.view.loading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anzhi.sdk.ad.main.AzSplashAd;
import com.anzhi.sdk.ad.manage.AnzhiAdCallBack;
import com.dream.llb.common.Constants;
import com.dream.llb.subway.R;
import com.dream.llb.subway.view.base.base_activity.BaseActivity;
import com.dream.llb.subway.view.main.MainActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class LoadingActivity extends BaseActivity {
    private AzSplashAd splshAd;
    private ImageView ad_view;
    private TextView skipAdTv;
    private boolean next;
    private boolean isFront = false;//这个值表示页面是否在前台（true 表示在，false 表示不在前台）
    private boolean isClosed = false;//该值表示广告是否关闭（isClosed 为 true 表示该广告关闭了，false 表示该广告没有关闭）


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.loading_activity);
        // 如果是Android6.0以下的机器，默认在安装时获得了所有权限，可以直接调用SDK
        loadAnZhiAdSplash();
    }
    private void loadAnZhiAdSplash() {
        ad_view = (ImageView) findViewById(R.id.ad_splash_iv);
        skipAdTv = (TextView) findViewById(R.id.ad_skip_view);
        splshAd = new AzSplashAd(this, Constants.AN_ZHI_APP_KEY, Constants.AN_ZHI_SPLASH_ID, new AnzhiAdCallBack() {
            @Override
            public void onShow() {
                ad_view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onReceiveAd() {
                ad_view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadFailed(String result) {
                toNextActivity();
            }
            /*
             * 关闭广告
             */
            @Override
            public void onCloseAd() {
                toNextActivity();
            }

            @Override
            public void onAdExposure() {
            }

            @Override
            public void onADTick(long millisUntilFinished) {
                skipAdTv.setText(String.format("点击跳过%d", Math.round(millisUntilFinished / 1000f)));
            }

            @Override
            public void onAdClik() {
            }
        }, (LinearLayout) findViewById(R.id.splash_container), skipAdTv);
        splshAd.setDelayTimes(4);
        splshAd.loadAd();
    }

    /**
     * 跳转到新的页面
     */
    private void toNextActivity() {
        isClosed = true;
        if (isFront) {
            if (next) {
                return;
            }
            next = true;
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFront = true;
        if (isClosed) {
            toNextActivity();
        }
        if (splshAd != null) {
            splshAd.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        splshAd.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        isFront = false;
        super.onPause();
    }
}
