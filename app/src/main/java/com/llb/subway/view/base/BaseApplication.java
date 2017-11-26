package com.llb.subway.view.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by llb on 2017-11-25.
 */

public class BaseApplication extends Application{
    public static Context mContext;
    private BaseApplication instance;
    private ArrayList<Activity> activities;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        activities = new ArrayList<>();
    }

    /**
     * 单例模式
     * @return
     */public BaseApplication getInstance(){
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
