package com.dream.llb.subway.view.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dream.llb.subway.R;
import com.dream.llb.subway.model.bean.ForumListItem;
import com.dream.llb.subway.model.bean.PostListItem;

public class BaseActivity extends AppCompatActivity {
    public static ForumListItem forumListItems;
    public static PostListItem postListItems;
    protected TextView headTitleTV;
    protected TextView headRightTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    protected void initHeadView(){
        headTitleTV = (TextView) findViewById(R.id.headTitleTV);
        headRightTV = (TextView) findViewById(R.id.headRightTV);
    }
}
