package com.dream.llb.subway.view.show_image;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.dream.llb.subway.R;
import com.dream.llb.subway.view.base.base_activity.BaseActivity;
import com.squareup.picasso.Picasso;

public class ShowImageActivity extends BaseActivity implements View.OnClickListener {
    private PhotoView photoView;
    private ImageView closeIV;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        setContentView(R.layout.activity_show_image);
        initHeadView();
        headTitleTV.setText("浏览图片");
        headRightTV.setVisibility(View.GONE);
        headLeftIcon.setOnClickListener(this);
        photoView = (PhotoView) findViewById(R.id.photoView);
        photoView.enable();
        loadImage();
        closeIV = (ImageView) findViewById(R.id.closeIV);
        closeIV.setOnClickListener(this);
    }

    /**
     * 加载网络图片
     */
    private void loadImage() {
        Picasso.with(this).load(url).into(photoView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.closeIV:
                finish();
                break;
            case R.id.headLeftIcon:
                finish();
                break;
        }
    }
}
