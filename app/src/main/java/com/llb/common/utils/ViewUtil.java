package com.llb.common.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by llb on 2017-07-22.
 */

public class ViewUtil {
    public static DisplayMetrics getWindowDisplayMetrics(Context mContext){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
//        return manager.getDefaultDisplay().getWidth();
    }
}
