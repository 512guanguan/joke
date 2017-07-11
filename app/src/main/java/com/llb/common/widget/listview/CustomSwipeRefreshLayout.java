package com.llb.common.widget.listview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * TODO 未经测试的半成品
 * Created by Derrick on 2017/7/11.
 * http://blog.csdn.net/u012814441/article/details/49253761
 */

public class CustomSwipeRefreshLayout extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {
    private Context mContext;
    private CustomSwipeRefreshModel mCustomSwipeRefreshModel;
    private CustomSwipeRefreshListener mCustomSwipeRefreshListener;
    private ListView mListView;
    /**
     * 是否是下拉刷新
     **/
    private boolean isLoadMore = false;
    /**
     * 底部“加载更多”容器
     **/
    private RelativeLayout container;
    /**
     * “加载更多”按钮TextView
     **/
    private TextView loadMoreTextView;
    /**
     * “加载更多”进度框
     **/
    private ProgressBar progressBar;

    public CustomSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOnRefreshListener(this);
        if (this.mCustomSwipeRefreshModel == null) {
            this.mCustomSwipeRefreshModel = new CustomSwipeRefreshModel();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //了解一下控件绘制流程
        if (mListView == null) {
            int count = getChildCount();
            Log.i("llb", "onLayout-> getChildCount = " + count);
            //获取子类布局控件
            if (count > 0) {
                View view = getChildAt(0);
                if (view instanceof RecyclerView) {
                    mListView = (ListView) view;
                    //判断用户是否设置过“加载更多”监听事件
                    if (this.mCustomSwipeRefreshListener != null) {
                        setLoadMoreLayout();
                    }
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        if (this.mCustomSwipeRefreshListener != null && !isLoadMore) { // 下拉刷新
            this.mCustomSwipeRefreshListener.onRefresh();
        } else if (this.mCustomSwipeRefreshListener != null && isLoadMore) {
            this.mCustomSwipeRefreshListener.loadMore();
        } else {
            setRefreshing(false); // 不刷新
        }
    }

    public void setCustomSwipeRefreshModel(CustomSwipeRefreshModel mCustomSwipeRefreshModel) {
        this.mCustomSwipeRefreshModel = mCustomSwipeRefreshModel;
    }

    public void setCustomSwipeRefreshListener(CustomSwipeRefreshListener mCustomSwipeRefreshListener) {
        this.mCustomSwipeRefreshListener = mCustomSwipeRefreshListener;
    }

    public void setLoadMoreLayout() {
        if (mCustomSwipeRefreshModel == null) {
            mCustomSwipeRefreshModel = new CustomSwipeRefreshModel();
        }
        // 初始化一个container
        container = new RelativeLayout(mContext);
        loadMoreTextView = new TextView(mContext);
        progressBar = new ProgressBar(mContext);

        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRefreshing() && mCustomSwipeRefreshListener != null) {
                    isLoadMore = true;
                    loadMoreTextView.setText(mCustomSwipeRefreshModel.getOnRefreshText());
                    //将隐藏的ProGressBar显示出来
                    progressBar.setVisibility(View.VISIBLE);
                    mCustomSwipeRefreshListener.loadMore();
                } else {
                    // showtoast();
                }
            }
        });
        container.setPadding(mCustomSwipeRefreshModel.getLeftPadding(), mCustomSwipeRefreshModel.getTopPadding(), mCustomSwipeRefreshModel.getRightPadding(), mCustomSwipeRefreshModel.getBottomPadding());
        container.setBackgroundColor(mCustomSwipeRefreshModel.getContainerBackgroundColor());

        loadMoreTextView.setText(mCustomSwipeRefreshModel.getPullUpText());
        loadMoreTextView.setTextColor(mCustomSwipeRefreshModel.getTextColor());
        loadMoreTextView.setTextSize(mCustomSwipeRefreshModel.getTextSize());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        loadMoreTextView.setLayoutParams(lp);
        container.addView(loadMoreTextView);

        //设置进度框的大小
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(mCustomSwipeRefreshModel.getProgressBarWidth(), mCustomSwipeRefreshModel.getProgressBarHeight());
        lp1.addRule(RelativeLayout.LEFT_OF, 1);
        lp1.addRule(RelativeLayout.CENTER_VERTICAL);
        progressBar.setLayoutParams(lp1);
        container.addView(progressBar);
        progressBar.setVisibility(View.GONE);

        if (mListView != null) {
            mListView.addFooterView(container);
        }
    }

    /**
     * 设置结束加载文本
     */
    public void setFinishLoadMoreText() {
        loadMoreTextView.setText(mCustomSwipeRefreshModel.getFinishRefreshText());
        //重新隐藏进度框
        progressBar.setVisibility(View.GONE);
        //将加载更多的标记设为false（加载完毕）
        isLoadMore = false;
    }
}
