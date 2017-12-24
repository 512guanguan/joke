package com.dream.llb.subway.view.edit_comment;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.helper.ImageHelper;
import com.dream.llb.subway.model.bean.EditCommentPageResponse;
import com.dream.llb.subway.model.bean.LoginPageResponse;

import org.w3c.dom.Text;

public class EditCommentActivity extends AppCompatActivity implements View.OnClickListener,EditCommentContract.View{
    private EditCommentContract.Presenter presenter;
    private TextView quoteTV;//引用内容
    private RelativeLayout captchaLayout;//有些帐号是不需要验证码的
    private ImageView captchaIV;
    private String pageURL;//传进来的回复页面url
    private String referer;//原页面url
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_comment_activity);
        pageURL = getIntent().getStringExtra("pageURL");
        referer = getIntent().getStringExtra("referer");
        initView();
    }

    private void initView() {
        quoteTV = (TextView) findViewById(R.id.quote_tv);
        captchaLayout = (RelativeLayout) findViewById(R.id.captcha_layout_rl);
        captchaIV = (ImageView) findViewById(R.id.captcha_iv);
        presenter = new EditCommentPresenter(this);
        presenter.getPageData(pageURL,referer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    @Override
    public void setPageData(EditCommentPageResponse response) {
        if(!TextUtils.isEmpty(response.captchaUrl)){
            captchaLayout.setVisibility(View.VISIBLE);
            presenter.getCaptchaImage(pageURL,referer);
        }else {
            captchaLayout.setVisibility(View.GONE);
            quoteTV.setText(Html.fromHtml(response.quoteText));
        }

    }

    @Override
    public void setCaptchaImage(String path) {
        if(!TextUtils.isEmpty(path)){
            Bitmap bitmap = ImageHelper.getBitmap(this, path);
            captchaIV.setImageBitmap(bitmap);
        }
    }
}
