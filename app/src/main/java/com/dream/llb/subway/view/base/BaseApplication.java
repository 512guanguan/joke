package com.dream.llb.subway.view.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.dream.llb.subway.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.lqr.emoji.IImageLoader;
import com.lqr.emoji.LQREmotionKit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by llb on 2017-11-25.
 */

public class BaseApplication extends Application{
    public static Context mContext;
    private static BaseApplication instance;
    private ArrayList<Activity> activities;
    public static InterstitialAd mInterstitialAd;//admod插屏广告
    public static Boolean isLogin = false;//TODO 标记是否登录了

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        activities = new ArrayList<>();
        //初始化表情编辑lib
        LQREmotionKit.init(this, new IImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
//                Glide.with(context).load(path).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
                Picasso.with(context).load(path).centerCrop().into(imageView);
            }
        });

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
