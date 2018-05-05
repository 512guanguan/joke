package com.dream.llb.subway.view.about;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.dream.llb.subway.R;
import com.dream.llb.subway.view.base.base_activity.BaseActivity;

import net.nightwhistler.htmlspanner.HtmlSpanner;

public class AboutActivity extends BaseActivity {
    private TextView versionTv, aboutTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initHeadView();
        headTitleTV.setText("关于信息");
        headRightTV.setVisibility(View.GONE);

        versionTv = (TextView) findViewById(R.id.versionTv);
        aboutTv = (TextView) findViewById(R.id.aboutTv);
//        Spanned aboutText;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            aboutText = Html.fromHtml(getResources().getText(R.string.about_text).toString(),Html.FROM_HTML_MODE_LEGACY);
//        } else {
//            aboutText = Html.fromHtml(getResources().getText(R.string.about_text).toString());
//        };
        String aboutStr = "<html><body><p>&nbsp;&nbsp;&nbsp;&nbsp;地铁族建站于2006年7月26日，有管理员1人（Levi），超版5人（下一站永丰、jason130、kimipippo、我爱柯南、howchou），是全国最大的都市地铁生活的讨论区。" +
                "<br/>&nbsp;&nbsp;&nbsp;&nbsp;这个客户端提供了较全面的论坛帖子浏览、发表、回复功能，帐号相关的功能目前支持地铁族论坛帐号登录、登出和最新消息提醒功能。目前已上线google play应用商店，墙内的朋友可以翻墙下载，" +
                "或者手机浏览器打开<a href='https://fir.im/ditiezu'>地铁族</a>下载，该网址也可以自行关注检查最新版本。<br/>&nbsp;&nbsp;&nbsp;&nbsp;如果坛友们喜欢or有改进建议，欢迎邮件反馈512luffy@gmail.com，" +
                "你们的支持是我后续继续完善更多功能的最大动力！同时也衷心希望广大坛友可以多分享传播，让更多的族友用上更棒的APP！</p></body></html>";
        aboutTv.setText(new HtmlSpanner().fromHtml(aboutStr));
        aboutTv.setMovementMethod(LinkMovementMethod.getInstance());

        try {
            versionTv.setText("扫描二维码可直接下载App V" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
