package com.dream.llb.subway.view.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dream.llb.common.Constants;
import com.dream.llb.joke.view.OnFragmentInteractionListener;
import com.dream.llb.subway.R;
import com.dream.llb.subway.common.OnItemClickListener;
import com.dream.llb.subway.common.widget.SimpleDividerItemDecoration;
import com.dream.llb.subway.model.api.SubwayURL;
import com.dream.llb.subway.model.bean.HomePageResponse;
import com.dream.llb.subway.view.base.base_activity.BaseActivity;
import com.dream.llb.subway.view.base.BaseApplication;
import com.dream.llb.subway.view.forum_home.ForumHomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements HomeContract.View {
    private Context mContext;
    private OnFragmentInteractionListener mListener;
    private View view;
    private RecyclerView recyclerView = null;
    private List<HomePageResponse> postListData = null;
    private HomeAdapter adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currentPage = 0;
    private int pageSize = 10;
    private HomeContract.Presenter presenter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            this.mContext = this.getActivity();
            view = inflater.inflate(R.layout.home_fragment, container, false);
            initView();
        }
        return view;
    }

    private void initView() {
        presenter = new HomePresenter(this);
//        Log.i("llb", "onCreateView");
        recyclerView = (RecyclerView) view.findViewById(R.id.show_list);
        postListData = new ArrayList<>();
        adapter = new HomeAdapter(this.getActivity(), R.layout.item_subway_home_list);
//        adapter.setData(postListData);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext, R.drawable.item_horizontal_divider, 3));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));//这里用线性显示 类似于listview
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_swipeRefreshLayout);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Log.i("llb", "onItemClick adapter.getItemId(position)=" + adapter.getItemId(position));
//                Toast.makeText(mContext, "onItemClick position=" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ForumHomeActivity.class);
                intent.putExtra("subjectID", BaseActivity.homePageResponse.forumInformations.get(position).subjectID);
                intent.putExtra("subjectName", BaseActivity.homePageResponse.forumInformations.get(position).subjectName);
                intent.putExtra("url", SubwayURL.SUBWAY_BASE + BaseActivity.homePageResponse.forumInformations.get(position).subjectUrl);
                mContext.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Log.i("llb", "onItemLongClick position=" + position);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Log.i("llb", "onScrollStateChanged newState=" + newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.i("llb", "onScrolled dx=" + dx + "  dy = " + dy);
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (recyclerView.computeVerticalScrollOffset() + recyclerView.computeHorizontalScrollExtent() >= recyclerView.computeVerticalScrollRange()) {
//                    Log.i("llb", "onScrolled 底部啦");
                    // 是否正在下拉刷新
                    if (swipeRefreshLayout.isRefreshing()) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    // 首页没有上拉刷新
//                    if (!adapter.isLoadingMore()) {
////                        adapter.setLoadingMore(true);
//                        Log.i("llb", "触发上拉刷新");
////                        getForumListData();
//                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Log.i("llb", "setOnRefreshListener()");
                presenter.refreshPage();
            }
        });
        if (BaseActivity.homePageResponse != null && BaseActivity.homePageResponse.forumInformations.size() > 0) {
            adapter.setData(BaseActivity.homePageResponse.forumInformations);//使用内存缓存即可
        } else {
            presenter.refreshPage();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void hideProgressDialog() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgressDialog() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onFinishRefresh(HomePageResponse response) {
//        Toast.makeText(this.getActivity(), "数据解析完了", Toast.LENGTH_SHORT).show();
        adapter.setData(response.forumInformations);
        if (!TextUtils.isEmpty(response.accountInfoUrls.logOutUrl)) {
            BaseApplication.isLogin = true;
            Intent intent = new Intent();
            intent.setAction(Constants.BROADCAST_LOGIN_ACTION);
            intent.putExtra("status", "login");
            mContext.sendBroadcast(intent);
        }
    }
}
