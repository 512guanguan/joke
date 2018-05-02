package com.dream.llb.subway.view.about;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dream.llb.subway.R;
import com.dream.llb.subway.view.base.base_activity.BaseActivity;

public class AboutActivity extends BaseActivity {
    private TextView versionTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initHeadView();
        headTitleTV.setText("关于信息");
        headRightTV.setVisibility(View.GONE);

        versionTv = (TextView) findViewById(R.id.versionTv);
        try {
            versionTv.setText("App Version "+getPackageManager().getPackageInfo(getPackageName(),0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
