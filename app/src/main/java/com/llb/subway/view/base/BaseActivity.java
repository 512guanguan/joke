package com.llb.subway.view.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.llb.subway.model.bean.ForumListItem;

import java.util.List;

public class BaseActivity extends AppCompatActivity {
    public static List<ForumListItem> forumListItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
