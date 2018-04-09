package com.dream.llb.subway.view.edit_post;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.util.SharedPreferencesUtil;
import com.dream.llb.subway.common.widget.CaptchaPopupWindow;
import com.dream.llb.subway.model.api.SubwayURL;
import com.dream.llb.subway.model.bean.EditPostPageResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse;
import com.dream.llb.subway.view.base.BaseActivity;
import com.dream.llb.subway.view.post_detail.PostDetailActivity;

public class EditPostActivity extends BaseActivity implements View.OnClickListener, EditPostContract.View, AdapterView.OnItemSelectedListener {
    private EditPostContract.Presenter presenter;
    private String pageURL;
    private String referer;
    private Spinner lineSpinner;
    private EditText titleET, contentET;
    //    private Button submitBtn;
    private SimpleArrayAdapter arrayAdapter;
    private EditPostPageResponse response;
    private String typeID;//类别选择结果
    private CaptchaPopupWindow captchaPopupWindow;
//    private RelativeLayout middleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_post_activity);
        pageURL = getIntent().getStringExtra("pageURL");
        referer = getIntent().getStringExtra("referer");
        initView();
    }

    private void initView() {
        initHeadView();
        headTitleTV.setText("发帖");
        headRightTV.setText("发送");
        headRightTV.setOnClickListener(this);
        presenter = new EditPostPresenter(this);
        presenter.getPageData(pageURL, referer);
//        middleLayout = (RelativeLayout) findViewById(R.id.middleLayout);
        lineSpinner = (Spinner) findViewById(R.id.lineSpinner);
        titleET = (EditText) findViewById(R.id.postTitleET);
        contentET = (EditText) findViewById(R.id.postContentET);
//        submitBtn = (Button) findViewById(R.id.submitButton);
        arrayAdapter = new SimpleArrayAdapter(this, R.layout.item_simple_line_spinner);
        /*adapter设置一个下拉列表样式，参数为系统子布局*/
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        lineSpinner.setAdapter(arrayAdapter);
        lineSpinner.setOnItemSelectedListener(this);
//        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String title = titleET.getText().toString();
        String content = contentET.getText().toString();
        switch (v.getId()) {
            case R.id.headRightTV://添加各种限制
                if (TextUtils.isEmpty(typeID) || typeID.equals("0")) {
                    Toast.makeText(this, "请先选择帖子内容的主题分类", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(this, "请先填写帖子标题", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(content) || content.length() < 10) {
                    Toast.makeText(this, "帖子内容太短了，别灌水", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(response.captchaUrl)) {
                    presenter.getCaptchaImage(response.captchaUrl, pageURL);
                } else {
                    String subjectID = (String) SharedPreferencesUtil.get(this, SharedPreferencesUtil.currentSubjectID, "");
                    presenter.submitPostData(SubwayURL.SUBWAY_SUBMIT_POST.replace("FID", subjectID), referer, response,
                            content, title, typeID, "");
                }
                break;
            case R.id.popup_send_btn:
                String subjectID = (String) SharedPreferencesUtil.get(this, SharedPreferencesUtil.currentSubjectID, "");
                presenter.submitPostData(SubwayURL.SUBWAY_SUBMIT_POST.replace("FID", subjectID), referer, response,
                        content, title, typeID, captchaPopupWindow.getCaptcha());
                captchaPopupWindow.dismiss();
                break;
        }
    }

    @Override
    public void setPageData(EditPostPageResponse response) {
        Log.i("llb", response.toString());
        this.response = response;
        arrayAdapter.addAll(response.subjectMap.keySet());
    }

    @Override
    public void setCaptchaImage(String path) {
        captchaPopupWindow = new CaptchaPopupWindow(this, this, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, false);
        captchaPopupWindow.showAtLocation(lineSpinner, Gravity.CENTER, 0, 0);
        captchaPopupWindow.setCaptchaImage(response.captchaUrl);
    }

    @Override
    public void onSubmitPostSuccess(PostDetailResponse response) {
        Log.i("llb", response.toString());
        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra("url", response.currentPageUrl);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("llb", arrayAdapter.getItem(position).toString());
        this.typeID = response.subjectMap.get(arrayAdapter.getItem(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
