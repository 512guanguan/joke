package com.dream.llb.subway.view.post_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.widget.CaptchaPopupWindow;
import com.dream.llb.subway.common.widget.SimpleDividerItemDecoration;
import com.dream.llb.subway.model.api.SubwayURL;
import com.dream.llb.subway.model.bean.BaseResponse;
import com.dream.llb.subway.model.bean.EditCommentPageResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse.CommentInformation;
import com.dream.llb.subway.model.bean.WarningPageResponse;
import com.dream.llb.subway.view.base.base_activity.BaseActivity;
import com.dream.llb.subway.view.base.BaseApplication;
import com.dream.llb.subway.view.edit_post.EditPostActivity;
import com.dream.llb.subway.view.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends BaseActivity implements PostDetailContract.View, ActivityCompat.OnRequestPermissionsResultCallback, View.OnClickListener {
    private Context mContext;
    private String url = "";
    private PostDetailContract.Presenter presenter;
    private RecyclerView recyclerView = null;
    private List<CommentInformation> commentListData = null;
    private PostDetailResponse postDetailResponse;//存储的首次加载，即首屏页面的数据
    private PostCommentAdapter adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currentPage = 1;
    public static final int LOGIN_CODE = 0;
    private Button loginBtn;
    private Button SubmitBtn;
    private TextView postCommentTV;
    private RelativeLayout editLayout;
    private EditText commentEdit;
    private EditCommentPageResponse editCommentPageResponse;//回复别人的评论时页面信息
    private CaptchaPopupWindow captchaPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail_activity);
        mContext = this;
        presenter = new PostDetailPresenter(this);
        url = getIntent().getStringExtra("url");
//        PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, mPermissionGrant);
        initView();
    }

    private void initView() {
        initHeadView();
        initADMob();
        headTitleTV.setText("地铁族");
        headRightTV.setVisibility(View.GONE);
        headRightTV.setOnClickListener(this);
//        postTV = (TextView) findViewById(R.id.post_content_tv);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        commentEdit = (EditText) findViewById(R.id.comment_edit);
        postCommentTV = (TextView) findViewById(R.id.post_comment_tv);
        postCommentTV.setOnClickListener(this);
        editLayout = (RelativeLayout) findViewById(R.id.edit_layout);

        recyclerView = (RecyclerView) findViewById(R.id.comment_list_rv);
        commentListData = new ArrayList<>();
        adapter = new PostCommentAdapter(this, R.layout.item_post_comment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext, R.drawable.item_horizontal_divider, 3));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.comment_swipeRefreshLayout);
        adapter.setOnItemClickListener(new PostCommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Log.i("llb", "onItemClick position=" + position);
//                Toast.makeText(mContext, "onItemClick position=" + position, Toast.LENGTH_SHORT).show();
//                captchaPopupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Log.i("llb", "onItemLongClick position=" + position);
            }

            @Override
            public void onReplyButtonClick(String replyPageUrl, String authorName) {
                //TODO 这里请求并解析回复页面数据
                //TODO 这里应该清理掉可能已有的sechash之类的?
                editCommentPageResponse = null;
                presenter.getReplyPageData(replyPageUrl, url);
                commentEdit.setText("");
                commentEdit.setHint("@" + authorName);
                commentEdit.setSelected(true);
//                Intent intent = new Intent(mContext, EditCommentActivity.class);
//                intent.putExtra("pageURL", replyPageUrl);
//                intent.putExtra("referer", url);
//                mContext.startActivity(intent);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Log.i("llb", "onScrollStateChanged newState=" + newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !adapter.isLastPage) {//不是最后一页
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int lastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    if (visibleItemCount > 0 && lastVisiblePosition >= totalItemCount - 1 && !adapter.isLoadingMore) {
                        adapter.setLoadingMore(true);
                        presenter.loadMoreData(url, currentPage);
                    }
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshPage(url);
            }
        });

        presenter.refreshPage(url);

        //测试而已
        captchaPopupWindow = new CaptchaPopupWindow(mContext, this, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        Log.i("llb", "onNewIntent");
        url = getIntent().getStringExtra("url");
        presenter.refreshPage(url);
    }

    @Override
    public void setReplyPageData(EditCommentPageResponse response) {
        editCommentPageResponse = response;
//        Toast.makeText(mContext, "quoteText = " + response.quoteText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressDialog() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgressDialog() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onFinishLoadMore(PostDetailResponse response) {
//        Toast.makeText(this, "数据解析完了", Toast.LENGTH_SHORT).show();
        adapter.setLoadingMore(false);
        //TODO
        if (response instanceof PostDetailResponse) {
            currentPage++;
            adapter.setMoreData(response);
        }
    }

    @Override
    public void onFinishRefresh(PostDetailResponse response) {
//        Toast.makeText(this, "数据解析完了", Toast.LENGTH_SHORT).show();
        if (response instanceof PostDetailResponse) {
            if (!TextUtils.isEmpty(response.pageWarning)) {
                Toast.makeText(this, response.pageWarning, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                postDetailResponse = response;
                headRightTV.setVisibility(View.VISIBLE);
                setHeaderView();
                adapter.setData(response);
            }

        }
        if (BaseApplication.isLogin) {
            if(!TextUtils.isEmpty(response.replyCommentUrl)){
                editLayout.setVisibility(View.VISIBLE);
            }else{
                editLayout.setVisibility(View.GONE);
            }
            loginBtn.setVisibility(View.GONE);
        } else {
            editLayout.setVisibility(View.INVISIBLE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPostCommentFinished(BaseResponse response) {
        if (response instanceof WarningPageResponse) {//发帖失败原因提醒
            Toast.makeText(mContext, ((WarningPageResponse) response).warning, Toast.LENGTH_LONG).show();
//            presenter.refreshPage(url);
        } else if (response instanceof PostDetailResponse) {//发帖成功并返回了新页面
            onFinishRefresh((PostDetailResponse) response);
            commentEdit.setText("");
            commentEdit.setHint("快说两句吧……");
            commentEdit.clearFocus();
            editCommentPageResponse = null;
        } else {

        }
    }

    @Override
    public void setCaptchaImage(String response) {
        captchaPopupWindow = new CaptchaPopupWindow(mContext, this, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, false);
//        captchaPopupWindow.getContentView().findViewById(R.id.popup_send_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (editCommentPageResponse != null) {
//                    //认为是回复评论
//                    presenter.postReplyComment(editCommentPageResponse, commentEdit.getText().toString(), captchaPopupWindow.getCaptcha(), url);
//                } else {
//                    presenter.postComment(postDetailResponse, commentEdit.getText().toString(), captchaPopupWindow.getCaptcha());
//                }
//            }
//        });
        captchaPopupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);
        captchaPopupWindow.setCaptchaImage(response);
    }

    private void setHeaderView() {
        View header = LayoutInflater.from(this).inflate(R.layout.item_post_detail_header, recyclerView, false);
        adapter.setHeaderView(header);
    }

    @Override
    public void onClick(View v) {
        String commentMsg = commentEdit.getText().toString();
        switch (v.getId()) {
            case R.id.post_comment_tv:
//                commentMsg = commentEdit.getText().toString();
                if (TextUtils.isEmpty(commentMsg)) {
                    Toast.makeText(this, "评论信息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (commentMsg.getBytes().length < 10) {
                    Toast.makeText(this, "别太水，评论最少得有5个字哟", Toast.LENGTH_SHORT).show();
                    return;
                }
//                commentMsg += "\n【愿世界和平】";
//                commentMsg += "[url=百度一下]www.baidu.com[/url]";
                if (editCommentPageResponse != null) {
                    //认为是回复评论
                    //TODO 验证码逻辑
                    if (!TextUtils.isEmpty(editCommentPageResponse.captchaUrl)) {
                        presenter.getCaptchaImage(editCommentPageResponse.captchaUrl, url);
                    } else {
                        presenter.postReplyComment(editCommentPageResponse, commentMsg, "", url);
                    }
                } else {
                    if (!TextUtils.isEmpty(postDetailResponse.CAPTCHA_URL)) {
                        presenter.getCaptchaImage(postDetailResponse.CAPTCHA_URL, url);
                    } else {
                        presenter.postComment(postDetailResponse, commentMsg, "");
                    }
                }
                break;
            case R.id.popup_send_btn:
                if (editCommentPageResponse != null) {
                    //认为是回复评论
                    presenter.postReplyComment(editCommentPageResponse, commentMsg, captchaPopupWindow.getCaptcha(), url);
                } else {
                    presenter.postComment(postDetailResponse, commentMsg, captchaPopupWindow.getCaptcha());
                }
                captchaPopupWindow.dismiss();
                break;
            case R.id.login_btn:
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivityForResult(intent, LOGIN_CODE);
                break;
            case R.id.headRightTV:
                //跳转去发表帖子入口
                if(BaseApplication.isLogin){
                    Intent intentTest = new Intent(mContext, EditPostActivity.class);
                    intentTest.putExtra("referer", url);
                    intentTest.putExtra("pageURL", SubwayURL.SUBWAY_EDIT_PAGE.replace("FID", postDetailResponse.subjectID));
                    startActivity(intentTest);
                }else {
                    Intent intent1 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.headLeftIcon:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LOGIN_CODE:
                    presenter.refreshPage(url);
                    break;
            }
        }
    }

}
