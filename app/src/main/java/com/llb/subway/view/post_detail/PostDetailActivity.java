package com.llb.subway.view.post_detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.llb.joke.R;
import com.llb.subway.common.OnItemClickListener;
import com.llb.subway.model.bean.PostDetailResponse;
import com.llb.subway.model.bean.PostDetailResponse.CommentInformation;

import net.nightwhistler.htmlspanner.HtmlSpanner;

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
    private int currentPage = 0;
    private TextView postTV;

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
        //TODO　布局
        postTV = (TextView) findViewById(R.id.post_content_tv);
        recyclerView = (RecyclerView) findViewById(R.id.comment_list_rv);
        commentListData = new ArrayList<>();
        adapter = new PostCommentAdapter(this, R.layout.item_post_comment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
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
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i("llb", "onScrolled dx=" + dx + "  dy = " + dy);
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (recyclerView.computeVerticalScrollOffset() + recyclerView.computeHorizontalScrollExtent() >= recyclerView.computeVerticalScrollRange()) {
                    Log.i("llb", "onScrolled 底部啦");
                    // 是否正在下拉刷新
                    if (swipeRefreshLayout.isRefreshing()) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    // 触发上拉刷新
                    if (!adapter.isLoadingMore()) {
//                        adapter.setLoadingMore(true);
                        Log.i("llb", "触发上拉刷新");
//                        getForumListData();
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("llb", "setOnRefreshListener()");
//                fetchData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setRefreshing(true);
        presenter.getPostDetailData(url);
    }

    @Override
    public void setPostDetailData(PostDetailResponse response) {
        Toast.makeText(this, "数据解析完了", Toast.LENGTH_SHORT).show();
        postTV.setText(new HtmlSpanner().fromHtml(response.postContent));
        adapter.setData(response.commentList);
        swipeRefreshLayout.setRefreshing(false);
    }
}
