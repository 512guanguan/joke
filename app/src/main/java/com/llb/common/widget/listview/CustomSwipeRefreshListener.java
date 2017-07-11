package com.llb.common.widget.listview;

/**
 * Created by Derrick on 2017/7/11.
 */

public interface CustomSwipeRefreshListener {
    public void onRefresh(); // 正在刷新
    public void loadMore(); // 上拉刷新
}
