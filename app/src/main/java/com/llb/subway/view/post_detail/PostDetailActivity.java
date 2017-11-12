package com.llb.subway.view.post_detail;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.llb.joke.R;
import com.llb.subway.model.bean.PostDetailResponse;

public class PostDetailActivity extends AppCompatActivity implements PostDetailContract.View{
    private Context mContext;
    private String url = "";
    private PostDetailContract.Presenter presenter;

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

        presenter.getPostDetailData(url);
    }

    @Override
    public void setPostDetailData(PostDetailResponse response) {
        Toast.makeText(this, "数据解析完了", Toast.LENGTH_SHORT).show();
    }
}
