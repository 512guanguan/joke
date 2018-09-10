package com.dream.llb.subway.view.setting;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.annotations.MyAnnotations;
import com.dream.llb.subway.common.util.SharedPreferencesUtil;
import com.dream.llb.subway.view.base.base_activity.BaseActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    Switch mSwitch;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mContext = this;

        initHeadView();
        headTitleTV.setText("设置");
        headRightTV.setVisibility(View.GONE);
        headLeftIcon.setOnClickListener(this);

        mSwitch = (Switch) findViewById(R.id.orderSwitch);
        int currentOrder = (int) SharedPreferencesUtil.get(mContext, SharedPreferencesUtil.SETTING_ORDER, MyAnnotations.DESC);
        mSwitch.setChecked(currentOrder == MyAnnotations.ASC);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.i("isChecked=", isChecked + "");
                //正序浏览
                @MyAnnotations.CommentOrders int order = MyAnnotations.ASC;
                if (!isChecked) {
                    order = MyAnnotations.DESC;
                }
                SharedPreferencesUtil.put(mContext, SharedPreferencesUtil.SETTING_ORDER, order);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headLeftIcon:
                finish();
                break;
        }
    }

}
