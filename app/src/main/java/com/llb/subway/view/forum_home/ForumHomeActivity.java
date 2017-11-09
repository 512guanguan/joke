package com.llb.subway.view.forum_home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.llb.joke.R;
import com.llb.subway.model.bean.PostListItem;

import java.util.ArrayList;
import java.util.List;

public class ForumHomeActivity extends AppCompatActivity implements ForumHomeContract.View{
    private Context mContext;
    private String url = "";
    private RecyclerView recyclerView = null;
    private List<PostListItem> postListData = null;
    private ForumHomeAdapter adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currentPage = 0;
    private ForumHomeContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_home_activity);
        url = getIntent().getStringExtra("url");
        initView();
    }
    private void initView() {
        presenter = new ForumHomePresenter(this);
        recyclerView = (RecyclerView) findViewById(R.id.post_list);
        postListData = new ArrayList<>();
        adapter = new ForumHomeAdapter(this, R.layout.item_forum_home_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_swipeRefreshLayout);
        adapter.setOnItemClickListener(new ForumHomeAdapter.OnItemClickListener() {
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

        presenter.getPostListData();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void parsePostListData(PostListItem response) {
//        PostListItem.Builder builder= new PostListItem().new Builder();
//        BaseActivity.postListItems = builder.parse(response);
        Toast.makeText(this, "数据解析完了", Toast.LENGTH_SHORT).show();

    }
}
