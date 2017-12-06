package com.dream.llb.subway.view.loading;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dream.llb.subway.R;
import com.dream.llb.subway.view.base.BaseActivity;
import com.dream.llb.subway.view.base.BaseApplication;
import com.dream.llb.subway.view.main.MainActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class LoadingActivity extends BaseActivity {

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.loading_activity);

        Observable.interval(2000, TimeUnit.MILLISECONDS)
                .take(1)
                .subscribe((response)->{
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                },(error)->{

                },()->{

                });
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                Log.i("llb","onAdLoaded");
//                mInterstitialAd.show();
//            }
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//            }
//            @Override
//            public void onAdOpened() {
//            }
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//            }
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when when the interstitial ad is closed.
//                Intent intent = new Intent(mContext, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}
