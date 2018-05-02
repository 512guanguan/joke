package com.dream.llb.subway.view.base.base_activity;

import android.content.Intent;
import android.util.Log;

import com.dream.llb.common.Constants;
import com.dream.llb.subway.model.AccountLoader;

import java.util.HashMap;

/**
 * Created by llb on 2017-09-12.
 */

public class BasePresenter implements BaseContract.Presenter {
    private BaseContract.View baseView;

    public BasePresenter(BaseContract.View baseView) {
        this.baseView = baseView;
    }

    @Override
    public void logOut(String url, String referer) {
        HashMap<String, String> data = new HashMap<>();
        //TODO　显示一个统一的菊花
        AccountLoader.getInstance().logOut(url, data, referer)
                .subscribe((response) -> {
//                    Log.i("llb", "退出成功后的 = " + response);
                    // 发送退出广播
                    baseView.onLogOutSuccess();
                }, (Throwable e) -> {
//                    Log.d("llb", "error " + e.getMessage());
                }, () -> {
//                    Log.d("llb", "completed");
                });
    }
}
