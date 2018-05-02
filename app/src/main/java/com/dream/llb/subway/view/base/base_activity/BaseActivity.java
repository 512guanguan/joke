package com.dream.llb.subway.view.base.base_activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.llb.common.Constants;
import com.dream.llb.common.widget.PopupUtil;
import com.dream.llb.subway.R;
import com.dream.llb.subway.model.api.SubwayURL;
import com.dream.llb.subway.model.bean.HomePageResponse;
import com.dream.llb.subway.model.bean.PostListItem;
import com.dream.llb.subway.view.base.BaseApplication;
import com.dream.llb.subway.view.login.LoginActivity;
import com.google.android.gms.ads.MobileAds;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener, BaseContract.View {
    public static HomePageResponse homePageResponse;
    public static PostListItem postListItems;
    protected ImageView headLeftIcon;
    protected TextView headTitleTV;
    protected TextView headRightTV;
    protected BroadcastReceiver receiver;
    private BasePresenter basePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        basePresenter = new BasePresenter(this);
    }

    protected void initHeadView() {
        headLeftIcon = (ImageView) findViewById(R.id.headLeftIcon);
        headTitleTV = (TextView) findViewById(R.id.headTitleTV);
        headRightTV = (TextView) findViewById(R.id.headRightTV);
        headLeftIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headLeftIcon:
                finish();
                break;
        }
    }

    /**
     * 广播注册
     */
//    public void mRegisterBroadcast(Context mContext, BroadcastReceiver receiver, String action) {
//        // 1. 实例化BroadcastReceiver子类 &  IntentFilter
//        this.receiver = receiver;//new MyBroadcastReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        // 2. 设置接收广播的类型
//        intentFilter.addAction(action);
//        // 3. 动态注册：调用Context的registerReceiver（）方法
//        mContext.registerReceiver(receiver, intentFilter);
//    }

    /**
     * 广播注销
     */
//    public void mUnregisterBroadcast(Context mContext) {
//        mContext.unregisterReceiver(receiver);
//    }

    public void doLogin(Context mContext) {
        doLogin(mContext, null);
    }

    /**
     * 登录or退出登录
     *
     * @param mContext
     */
    public void doLogin(Context mContext, View parentView) {
        if (!BaseApplication.isLogin) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        } else {
            //todo 弹框提示是否退出登录
            PopupUtil popupUtil = new PopupUtil(mContext, R.layout.log_out_notice_view,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                    parentView, Gravity.CENTER, 0, 0, new PopupUtil.ClickListener() {
                @Override
                public void setUplistener(final PopupUtil.PopBuilder builder) {
//                    builder.setText(R.id.btn_sqlite_find_all, "查询");
                    builder.getView(R.id.logoutBtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            basePresenter.logOut(SubwayURL.SUBWAY_BASE + homePageResponse.accountInfoUrls.logOutUrl, SubwayURL.SUBWAY_HOME);
                            builder.dismiss();
                        }
                    });
                    builder.getView(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });

                }
            }) {
            };
        }
    }

    /**
     * 初始化ADMOB
     */
    protected void initADMob() {
        MobileAds.initialize(this, Constants.ADMOB_APP_ID);
    }

    @Override
    public void onLogOutSuccess() {
        Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction(Constants.BROADCAST_LOGOUT_ACTION);
        getApplicationContext().sendBroadcast(intent);
    }
}
