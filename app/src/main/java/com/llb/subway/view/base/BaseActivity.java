package com.llb.subway.view.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.llb.subway.model.bean.ForumListItem;
import com.llb.subway.model.bean.PostListItem;

import java.util.List;

public class BaseActivity extends AppCompatActivity {
    public static List<ForumListItem> forumListItems;
    public static PostListItem postListItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
