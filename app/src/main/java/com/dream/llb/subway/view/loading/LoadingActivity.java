package com.dream.llb.subway.view.loading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dream.llb.common.Constants;
import com.dream.llb.subway.R;
import com.dream.llb.subway.view.base.base_activity.BaseActivity;
import com.dream.llb.subway.view.main.MainActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class LoadingActivity extends BaseActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.loading_activity);
//        initBaiduAD();
        Observable.interval(2000, TimeUnit.MILLISECONDS)
                .take(1)
                .subscribe((response) -> {
//                    destroyAD();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }, (error) -> {
//                    destroyAD();
                }, () -> {

                });
    }

//    private void initBaiduAD() {
//        //创建baidu开屏广告
//        splashAd = new BDSplashAd(this, Constants.BD_SDK_APP_KEY, Constants.BD_SDK_SPLASH_AD_ID);
////        splashAd.setAdListener(...);//设置监听回调
//        //如果本地无广告可用，需要下载广告，待下次启动使用
//        if (!splashAd.isLoaded()) {
//            splashAd.loadAd();
//        }
//        //展示开屏广告
//        if (splashAd.isLoaded()) {
//            splashAd.showAd();
//        }
//    }

//    private void destroyAD() {
//        //销毁广告对象
//        if(splashAd != null){
//            splashAd.destroy();
//            splashAd = null;
//        }
//    }
}
