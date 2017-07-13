package com.llb.pixabay.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.llb.common.widget.recyclerview.CommonAdapter.OnItemClickListener;
import com.llb.config.Config;
import com.llb.joke.BR;
import com.llb.joke.R;
import com.llb.joke.view.OnFragmentInteractionListener;
import com.llb.pixabay.model.PixabayLoader;
import com.llb.pixabay.model.bean.SearchImagesRequest;
import com.llb.pixabay.model.bean.SearchImagesResponse.HitImages;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class PixabayFirstFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PixabayAdapter adapter;
    private ArrayList<HitImages> hitsImages;
    private int currentPage = 1;
    private int pageSize = 50;
    public PixabayFirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_pixabay_first, container, false);
            initView();
        }
        return view;
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.pixabay_recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.pixabay_swipe_refresh);

        hitsImages = new ArrayList<>();
        adapter = new PixabayAdapter(this.getActivity(), R.layout.item_pixabay_list, BR.hitImages);
        adapter.setData(hitsImages);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,RecyclerView.VERTICAL));//这里用线性显示 类似于listview
        recyclerView.setAdapter(adapter);

        loadMoreData(currentPage, pageSize);

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
        SearchImagesRequest searchImagesRequest = new SearchImagesRequest();
        searchImagesRequest.key = Config.PIXABAY_KEY1;
        searchImagesRequest.q = URLEncoder.encode("girl+sexy");
        searchImagesRequest.image_type = "photo";
        searchImagesRequest.page = String.valueOf(currentPage);
        searchImagesRequest.per_page = String.valueOf(pageSize);

        adapter.setLoadingMore(true);
        new PixabayLoader().searchImage(searchImagesRequest).subscribe((response) -> {
            hitsImages.addAll(response.hits);
            adapter.setData(hitsImages);
            adapter.notifyDataSetChanged();
            adapter.notifyItemRemoved(adapter.getItemCount()); //底部刷新移除footerView
            this.currentPage++;
            Log.d("llb", response.hits.get(0).pageURL);
        }, (Throwable e) -> {
            Log.d("llb", "error " + e.getMessage());
            adapter.setLoadingMore(false);
        }, () -> {
            Log.d("llb", "completed");
            adapter.setLoadingMore(false);
        });
    }
}
