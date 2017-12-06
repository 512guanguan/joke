package com.dream.llb.subway.view.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

/**
 * Created by llb on 2017-11-25.
 */

public class BaseApplication extends Application{
    public static Context mContext;
    private static BaseApplication instance;
    private ArrayList<Activity> activities;
    public static InterstitialAd mInterstitialAd;//admod插屏广告

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        activities = new ArrayList<>();

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-1863836438957183/2446447638");
        //测试号
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    /**
     * 单例模式
     * @return
     */public static BaseApplication getInstance(){
        if(instance == null)
            return new BaseApplication();
        return instance;
    }

    /**
     * 添加activity
     * @param a
     */
    public void addActivity(Activity a){
        activities.add(a);
    }

    /**
     * 遍历所有Activity并finish
     */
    public void finishActivity() {
        for (Activity activity : activities) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
