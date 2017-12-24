package com.dream.llb.subway.view.post_detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.OnItemClickListener;
import com.dream.llb.subway.common.util.PermissionUtils;
import com.dream.llb.subway.common.widget.SimpleDividerItemDecoration;
import com.dream.llb.subway.model.bean.PostDetailResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse.CommentInformation;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity implements PostDetailContract.View, ActivityCompat.OnRequestPermissionsResultCallback{
    private Context mContext;
    private String url = "";
    private PostDetailContract.Presenter presenter;
    private RecyclerView recyclerView = null;
    private List<CommentInformation> commentListData = null;
    private PostCommentAdapter adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currentPage = 1;
//    private TextView postTV;

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
//        postTV = (TextView) findViewById(R.id.post_content_tv);
        recyclerView = (RecyclerView) findViewById(R.id.comment_list_rv);
        commentListData = new ArrayList<>();
        adapter = new PostCommentAdapter(this, R.layout.item_post_comment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext,R.drawable.item_horizontal_divider,3));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.comment_swipeRefreshLayout);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("llb", "onItemClick position=" + position);
                Toast.makeText(mContext,"onItemClick position=" + position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.i("llb", "onItemLongClick position=" + position);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("llb", "onScrollStateChanged newState=" + newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && !adapter.isLastPage){//不是最后一页
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int lastVisiblePosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    if(visibleItemCount>0 && lastVisiblePosition>=totalItemCount-1 && !adapter.isLoadingMore){
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
        Toast.makeText(this, "数据解析完了", Toast.LENGTH_SHORT).show();
        adapter.setLoadingMore(false);
        //TODO
        if(response instanceof PostDetailResponse){
            currentPage++;
            adapter.setMoreData(response);
        }
    }

    @Override
    public void onFinishRefresh(PostDetailResponse response) {
        Toast.makeText(this, "数据解析完了", Toast.LENGTH_SHORT).show();
        if(response instanceof PostDetailResponse){
            if(!TextUtils.isEmpty(response.pageWaring)){
                Toast.makeText(this,response.pageWaring,Toast.LENGTH_SHORT).show();
                finish();
            }else {
                setHeaderView();
                adapter.setData(response);
            }

        }
    }

    private void setHeaderView() {
        View header = LayoutInflater.from(this).inflate(R.layout.item_post_detail_header, recyclerView, false);
        adapter.setHeaderView(header);
    }
//    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
//        @Override
//        public void onPermissionGranted(int requestCode) {
//            initView();
//            switch (requestCode) {
//                case PermissionUtils.CODE_RECORD_AUDIO:
//                    Toast.makeText(mContext, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_GET_ACCOUNTS:
//                    Toast.makeText(mContext, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_READ_PHONE_STATE:
//                    Toast.makeText(mContext, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_CALL_PHONE:
//                    Toast.makeText(mContext, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_CAMERA:
//                    Toast.makeText(mContext, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
//                    Toast.makeText(mContext, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
//                    Toast.makeText(mContext, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
//                    Toast.makeText(mContext, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
//                    Toast.makeText(mContext, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    /**
     * Callback received when a permissions request has been completed.
     */
//    @Override
//    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
//    }
}
