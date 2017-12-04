package com.dream.llb.subway.view.post_detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.widget.SimpleDividerItemDecoration;
import com.dream.llb.subway.model.bean.PostDetailResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse.CommentInformation;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity implements PostDetailContract.View{
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
//        adapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Log.i("llb", "onItemClick position=" + position);
//                Toast.makeText(mContext,"onItemClick position=" + position,Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//                Log.i("llb", "onItemLongClick position=" + position);
//            }
//        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("llb", "onScrollStateChanged newState=" + newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int lastVisiblePosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    if(visibleItemCount>0 && lastVisiblePosition>=totalItemCount-1 && !adapter.isLoadingMore){
                        adapter.setLoadingMore(true);
                        presenter.loadMoreData(url, currentPage);
                        Log.i("llb", "onScrollStateChanged 底部啦");
                        Log.i("llb", "lastVisiblePosition="+lastVisiblePosition+" visibleItemCount="+visibleItemCount+" totalItemCount="+totalItemCount);
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
            setHeaderView();
            adapter.setData(response);
        }
    }

    private void setHeaderView() {
        View header = LayoutInflater.from(this).inflate(R.layout.item_post_detail_header, recyclerView, false);
        adapter.setHeaderView(header);
    }
}
