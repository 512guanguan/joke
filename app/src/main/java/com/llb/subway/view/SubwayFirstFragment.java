package com.llb.subway.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.llb.common.widget.recyclerview.CommonAdapter;
import com.llb.common.widget.recyclerview.CommonAdapter.OnItemClickListener;
import com.llb.config.Config;
import com.llb.joke.BR;
import com.llb.joke.R;
import com.llb.joke.model.JokeLoader;
import com.llb.joke.model.bean.GetLatestJokeRequest;
import com.llb.joke.model.bean.JokeResponse.JokeData;
import com.llb.joke.view.OnFragmentInteractionListener;
import com.llb.subway.model.bean.PostListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubwayFirstFragment} factory method to
 * create an instance of this fragment.
 */
public class SubwayFirstFragment extends Fragment implements SubwayFirstContract.View{
    private OnFragmentInteractionListener mListener;
    private View view;
    private RecyclerView recyclerView = null;
    private List<PostListItem> postListData = null;
    private CommonAdapter<PostListItem> adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currentPage = 0;
    private int pageSize = 10;
    private SubwayFirstContract.Presenter presenter;

    public SubwayFirstFragment() {
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
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_joke_first, container, false);
            initView();
        }
        return view;
    }

    private void initView() {
        presenter = new SubwayFirstPresenter();
        Log.i("llb", "onCreateView");
        recyclerView = (RecyclerView) view.findViewById(R.id.show_list);
        postListData = new ArrayList<>();
        adapter = new CommonAdapter<>(this.getActivity(), R.layout.item_subway_post_list, BR.subwayData);
        adapter.setData(postListData);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));//这里用线性显示 类似于listview
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("llb", "onItemClick position=" + position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.i("llb", "onItemLongClick position=" + position);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("llb", "onScrollStateChanged newState=" + newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i("llb", "onScrolled dx=" + dx + "  dy = " + dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.findLastVisibleItemPosition() + 1 == adapter.getItemCount()) {
                    // 是否正在下拉刷新
                    if (swipeRefreshLayout.isRefreshing()) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    // 触发上拉刷新
                    if (!adapter.isLoadingMore()) {
//                        adapter.setLoadingMore(true);
                        Log.i("llb", "触发上拉刷新");
                        loadMoreData(currentPage, pageSize);
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("llb", "setOnRefreshListener()");
                fetchData();
            }
        });

        loadMoreData(currentPage, pageSize);
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

    public void fetchData() {
        Toast.makeText(this.getActivity(), "到头啦！！", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
        return;
    }

    public void loadMoreData(int currentPage, int pageSize) {
        presenter.getPostListData();
    }

    @Override
    public void parsePostListData() {

    }
}
