package com.dream.llb.subway.view.forum_home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.OnItemClickListener;
import com.dream.llb.subway.common.util.SharedPreferencesUtil;
import com.dream.llb.subway.common.widget.SimpleDividerItemDecoration;
import com.dream.llb.subway.model.api.SubwayURL;
import com.dream.llb.subway.model.bean.PostListItem;
import com.dream.llb.subway.view.base.BaseApplication;
import com.dream.llb.subway.view.base.base_activity.BaseActivity;
import com.dream.llb.subway.view.edit_post.EditPostActivity;
import com.dream.llb.subway.view.login.LoginActivity;
import com.dream.llb.subway.view.post_detail.PostDetailActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ForumHomeActivity extends BaseActivity implements ForumHomeContract.View, View.OnClickListener {
    private Context mContext;
    private String url = "";
    private RecyclerView recyclerView = null;
    //    private List<PostListItem> postListData = null;
    private ForumHomeAdapter adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currentPage = 1;
    private ForumHomeContract.Presenter presenter;
    private String subjectName;
    private String subjectID;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_home_activity);
        mContext = this;
        url = getIntent().getStringExtra("url");
        subjectName = getIntent().getStringExtra("subjectName");
        subjectID = getIntent().getStringExtra("subjectID");

        if (!TextUtils.isEmpty(subjectName) && !TextUtils.isEmpty(subjectID)) {
            SharedPreferencesUtil.put(this, "currentSubjectName", subjectName);
            SharedPreferencesUtil.put(this, "currentSubjectID", subjectID);
        } else {
            subjectName = (String) SharedPreferencesUtil.get(this, SharedPreferencesUtil.currentSubjectName, "地铁族");
            subjectID = (String) SharedPreferencesUtil.get(this, SharedPreferencesUtil.currentSubjectID, "");
        }
        initView();
    }

    private void initView() {
        initHeadView();
        headTitleTV.setText(subjectName);
        headRightTV.setOnClickListener(this);
        headLeftIcon.setOnClickListener(this);
        mAdView = (AdView) findViewById(R.id.adView);
//        mAdView.setAdUnitId(Constants.ADMOB_FORUM_BOTTOM_ID);
//        mAdView.setAdSize(AdSize.BANNER);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        presenter = new ForumHomePresenter(this);
        recyclerView = (RecyclerView) findViewById(R.id.post_list);
//        postListData = new ArrayList<>();
        adapter = new ForumHomeAdapter(this, R.layout.item_forum_home_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext, R.drawable.item_horizontal_divider, 3));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.forum_home_swipeRefreshLayout);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Log.i("llb", "onItemClick position=" + position);
//                Toast.makeText(mContext, "onItemClick position=" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, PostDetailActivity.class);
                intent.putExtra("url", SubwayURL.SUBWAY_BASE + BaseActivity.postListItems.postList.get(position).postUrl);
                mContext.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Log.i("llb", "onItemLongClick position=" + position);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Log.i("llb", "onScrollStateChanged newState=" + newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int lastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    if (visibleItemCount > 0 && lastVisiblePosition >= totalItemCount - 1 && !adapter.isLoadingMore) {
                        adapter.setLoadingMore(true);
                        presenter.loadMoreData(currentPage);
//                        Log.i("llb", "onScrollStateChanged 底部啦");
//                        Log.i("llb", "lastVisiblePosition=" + lastVisiblePosition + " visibleItemCount=" + visibleItemCount + " totalItemCount=" + totalItemCount);
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
                presenter.refreshPage();
            }
        });
//        if(BaseActivity.postListItems !=null && BaseActivity.postListItems.postList.size()>0){
//            adapter.setData(BaseActivity.postListItems.postList);//使用内存缓存即可
//        }else {
        BaseActivity.postListItems = null;//清空
        presenter.refreshPage();
//        }
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void onFinishLoadMore(PostListItem response) {
//        Toast.makeText(this, "数据解析完了", Toast.LENGTH_SHORT).show();
        adapter.setLoadingMore(false);
        if (response instanceof PostListItem) {
            currentPage++;
            adapter.setMoreData(response.postList);
        }
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
    public void onFinishRefresh(PostListItem response) {
//        Toast.makeText(this, "数据解析完了", Toast.LENGTH_SHORT).show();
        adapter.setData(response.postList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headRightTV:
                //TODO　测试，跳转去发表帖子入口
                if(BaseApplication.isLogin){
                    Intent intentTest = new Intent(mContext, EditPostActivity.class);
                    intentTest.putExtra("referer", url);
                    intentTest.putExtra("pageURL", SubwayURL.SUBWAY_EDIT_PAGE.replace("FID", subjectID));
                    startActivity(intentTest);
                }else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.headLeftIcon:
                finish();
                break;
        }
    }
}
