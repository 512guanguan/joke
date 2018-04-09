package com.dream.llb.subway.view.edit_comment;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.helper.ImageHelper;
import com.dream.llb.subway.model.bean.EditCommentPageResponse;
import com.dream.llb.subway.model.bean.LoginPageResponse;
import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.EmotionLayout;
import com.lqr.emoji.IEmotionSelectedListener;
import com.lqr.emoji.LQREmotionKit;
import com.lqr.emoji.MoonUtils;

import org.w3c.dom.Text;

public class EditCommentActivity extends AppCompatActivity implements View.OnClickListener,EditCommentContract.View{
    private EditCommentContract.Presenter presenter;
    private TextView quoteTV,contentTV;//引用内容
    private EmotionLayout emotionLayout;
    private EmotionKeyboard mEmotionKeyboard;
    private LinearLayout topLayout;

    private EditText commentET;
    private RelativeLayout captchaLayout;//有些帐号是不需要验证码的
    private ImageView captchaIV,emotion_iv;
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
        topLayout = (LinearLayout) findViewById(R.id.top_layout);
        quoteTV = (TextView) findViewById(R.id.quote_tv);
        contentTV = (TextView) findViewById(R.id.content_tv);
        commentET = (EditText) findViewById(R.id.comment_edit);
        emotion_iv = (ImageView) findViewById(R.id.emotion_iv);
//        emotion_iv.setOnClickListener(this);
        captchaLayout = (RelativeLayout) findViewById(R.id.captcha_layout_rl);
        captchaIV = (ImageView) findViewById(R.id.captcha_iv);
        emotionLayout = (EmotionLayout) findViewById(R.id.emotion_layout);
        presenter = new EditCommentPresenter(this);
        presenter.getPageData(pageURL,referer);
        initEmotionLayout();
    }

    private void initEmotionLayout() {
        emotionLayout.attachEditText(commentET);
        emotionLayout.setEmotionSelectedListener(new IEmotionSelectedListener() {
            @Override
            public void onEmojiSelected(String key) {
                if (commentET == null)
                    return;
                Editable editable = commentET.getText();
                if (key.equals("/DEL")) {
                    commentET.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                } else {
                    int start = commentET.getSelectionStart();
                    int end = commentET.getSelectionEnd();
                    start = (start < 0 ? 0 : start);
                    end = (start < 0 ? 0 : end);
                    editable.replace(start, end, key);

                    int editEnd = commentET.getSelectionEnd();
                    MoonUtils.replaceEmoticons(LQREmotionKit.getContext(), editable, 0, editable.toString().length());
                    commentET.setSelection(editEnd);
                }
            }
            @Override
            public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {
            }
        });

        mEmotionKeyboard = EmotionKeyboard.with(this);
        mEmotionKeyboard.bindToContent(topLayout);
        mEmotionKeyboard.bindToEmotionButton(emotion_iv);
        mEmotionKeyboard.bindToEditText(commentET);
        mEmotionKeyboard.setEmotionLayout(emotionLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.emotion_iv:
                break;
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
