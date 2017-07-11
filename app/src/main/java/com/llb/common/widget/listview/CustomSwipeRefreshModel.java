package com.llb.common.widget.listview;

import android.graphics.Color;

/**
 * Created by Derrick on 2017/7/11
 * 用来设置整个SwipeRefreshLayout控件参数的Model，可提供给用户直接修改。
 */

public class CustomSwipeRefreshModel {
    private float textSize = 14;
    private String finishRefreshText = "加载更多";
    private String onRefreshText = "正在加载...";
    private String pullDownText = "正在加载，请稍后再试...";
    private String pullUpText = "正在刷新，请稍后再试...";
    //设置进度框的大小
    private int progressBarWidth = 50;
    private int progressBarHeight = 50;
    private int containerBackgroundColor = Color.parseColor("#e8ebee");
    private int textColor = Color.GRAY;
    private int leftPadding = 20;
    private int topPadding = 20;
    private int rightPadding = 20;
    private int bottomPadding = 20;

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public String getFinishRefreshText() {
        return finishRefreshText;
    }

    public void setFinishRefreshText(String finishRefreshText) {
        this.finishRefreshText = finishRefreshText;
    }

    public String getOnRefreshText() {
        return onRefreshText;
    }

    public void setOnRefreshText(String onRefreshText) {
        this.onRefreshText = onRefreshText;
    }

    public String getPullDownText() {
        return pullDownText;
    }

    public void setPullDownText(String pullDownText) {
        this.pullDownText = pullDownText;
    }

    public String getPullUpText() {
        return pullUpText;
    }

    public void setPullUpText(String pullUpText) {
        this.pullUpText = pullUpText;
    }

    public int getProgressBarWidth() {
        return progressBarWidth;
    }

    public void setProgressBarWidth(int progressBarWidth) {
        this.progressBarWidth = progressBarWidth;
    }

    public int getProgressBarHeight() {
        return progressBarHeight;
    }

    public void setProgressBarHeight(int progressBarHeight) {
        this.progressBarHeight = progressBarHeight;
    }

    public int getContainerBackgroundColor() {
        return containerBackgroundColor;
    }

    public void setContainerBackgroundColor(int containerBackgroundColor) {
        this.containerBackgroundColor = containerBackgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getLeftPadding() {
        return leftPadding;
    }

    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    public int getTopPadding() {
        return topPadding;
    }

    public void setTopPadding(int topPadding) {
        this.topPadding = topPadding;
    }

    public int getRightPadding() {
        return rightPadding;
    }

    public void setRightPadding(int rightPadding) {
        this.rightPadding = rightPadding;
    }

    public int getBottomPadding() {
        return bottomPadding;
    }

    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }
}
