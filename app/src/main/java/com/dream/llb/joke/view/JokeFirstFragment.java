package com.dream.llb.joke.view;

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

import com.dream.llb.common.widget.recyclerview.CommonAdapter;
import com.dream.llb.common.widget.recyclerview.CommonAdapter.OnItemClickListener;
import com.dream.llb.config.Config;
import com.dream.llb.joke.model.JokeLoader;
import com.dream.llb.joke.model.bean.GetLatestJokeRequest;
import com.dream.llb.joke.model.bean.JokeResponse.JokeData;
import com.dream.llb.subway.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JokeFirstFragment} factory method to
 * create an instance of this fragment.
 */
public class JokeFirstFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private View view;
    private RecyclerView recyclerView = null;
    private List<JokeData> jokedata = null;
    private CommonAdapter<JokeData> adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currentPage = 0;
    private int pageSize = 50;

    public JokeFirstFragment() {
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
        Log.i("llb", "onCreateView");
        recyclerView = (RecyclerView) view.findViewById(R.id.show_list);
        jokedata = new ArrayList<>();
        adapter = new CommonAdapter<>(this.getActivity(), R.layout.item_joke_list,0);
        adapter.setData(jokedata);

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
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("llb", "swipeRefreshLayout.post -> run()");
//            }
//        });

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
//        Map<String, String> requestParams = new HashMap<>();
//        requestParams.put("key", Config.KEY);
//        requestParams.put("page", String.valueOf(currentPage));
//        requestParams.put("pagesize", String.valueOf(pageSize));
//        requestParams.put("time", String.valueOf(ApiUtils.getTime()).substring(0,10));
//        requestParams.put("sort", "desc");
        GetLatestJokeRequest getLatestJokeRequest = new GetLatestJokeRequest();
        getLatestJokeRequest.key = Config.KEY;
        getLatestJokeRequest.page = String.valueOf(currentPage);
        getLatestJokeRequest.pagesize = String.valueOf(pageSize);

        adapter.setLoadingMore(true);
        new JokeLoader().getLatestJoke(getLatestJokeRequest).subscribe((response) -> {
            for (JokeData data : response.result.data) {
                jokedata.add(data);
            }
            adapter.setData(jokedata);
            adapter.notifyDataSetChanged();
            adapter.notifyItemRemoved(adapter.getItemCount()); //底部刷新移除footerView
            this.currentPage++;
            Log.d("llb", response.result.data[0].content);
        }, (Throwable e) -> {
            Log.d("llb", "error " + e.getMessage());
            adapter.setLoadingMore(false);
        }, () -> {
            Log.d("llb", "completed");
            adapter.setLoadingMore(false);
        });
    }
}
