package com.dream.llb.subway.view.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dream.llb.subway.model.bean.ForumListItem;
import com.dream.llb.subway.model.bean.PostListItem;

public class BaseActivity extends AppCompatActivity {
    public static ForumListItem forumListItems;
    public static PostListItem postListItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
