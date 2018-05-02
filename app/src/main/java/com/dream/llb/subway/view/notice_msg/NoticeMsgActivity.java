package com.dream.llb.subway.view.notice_msg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.OnItemClickListener;
import com.dream.llb.subway.common.widget.SimpleDividerItemDecoration;
import com.dream.llb.subway.model.api.SubwayURL;
import com.dream.llb.subway.model.bean.NoticeMsgResponse;
import com.dream.llb.subway.view.base.base_activity.BaseActivity;
import com.dream.llb.subway.view.forum_home.ForumHomeAdapter;
import com.dream.llb.subway.view.forum_home.ForumHomeContract;
import com.dream.llb.subway.view.forum_home.ForumHomePresenter;
import com.dream.llb.subway.view.post_detail.PostDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class NoticeMsgActivity extends BaseActivity implements NoticeMsgContract.View {
    private Context mContext;
    private RecyclerView recyclerView = null;
    private NoticeMsgResponse response;
    private NoticeMsgAdapter adapter = null;
    private NoticeMsgContract.Presenter presenter;
    private TextView noContentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_msg);
        mContext = this;
        initView();
    }

    private void initView() {
        initHeadView();
        headTitleTV.setText("消息提醒");
        headRightTV.setVisibility(View.GONE);
        presenter = new NoticeMsgPresenter(this);
        noContentTv = (TextView) findViewById(R.id.noContentTv);
        recyclerView = (RecyclerView) findViewById(R.id.msg_list);
        adapter = new NoticeMsgAdapter(this, R.layout.item_notice_msg_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext, R.drawable.item_horizontal_divider, 3));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Log.i("llb", "onItemClick position=" + position);
//                Toast.makeText(mContext, "onItemClick position=" + position, Toast.LENGTH_SHORT).show();
                String url = response.postIDList.get(position);
                if(!TextUtils.isEmpty(url)){
                    Intent intent = new Intent(mContext, PostDetailActivity.class);
                    intent.putExtra("url", SubwayURL.SUBWAY_POST_DETAIL.replace("TID", response.postIDList.get(position)));
                    mContext.startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Log.i("llb", "onItemLongClick position=" + position);
            }
        });
        presenter.getNoticeData(SubwayURL.SUBWAY_NOTICE_MSG_READ, SubwayURL.SUBWAY_NOTICE_MSG_UNREAD);
    }

    @Override
    public void setNoticeData(NoticeMsgResponse response) {
        if(response.noticeList.size()>0){
            noContentTv.setVisibility(View.GONE);
            adapter.setData(response.noticeList);
            this.response = response;
        }else {
            noContentTv.setVisibility(View.VISIBLE);
        }
    }
}
