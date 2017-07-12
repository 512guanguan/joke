package com.llb.joke.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.llb.common.widget.recyclerview.CommonAdapter;
import com.llb.common.widget.recyclerview.CommonAdapter.OnItemClickListener;
import com.llb.config.Config;
import com.llb.joke.BR;
import com.llb.joke.R;
import com.llb.joke.model.JokeLoader;
import com.llb.joke.model.bean.GetLatestJokeRequest;
import com.llb.joke.model.bean.JokeResponse.JokeData;
import com.llb.pixabay.view.PixabayMainActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button btn_fetch;
    private RecyclerView recyclerView = null;
    private List<JokeData> jokedata = null;
    private CommonAdapter<JokeData> adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currentPage = 0;
    private int pageSize = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.show_list);
        jokedata = new ArrayList<>();
        adapter = new CommonAdapter<>(this,R.layout.joke_list_item,BR.jokeData);
        adapter.setData(jokedata);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            public void onScrollStateChanged(RecyclerView recyclerView, int newState){
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("llb", "onScrollStateChanged newState=" + newState);
            }
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);
                Log.i("llb", "onScrolled dx=" + dx + "  dy = " + dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(layoutManager.findLastVisibleItemPosition() + 1  == adapter.getItemCount()) {
                    // 是否正在下拉刷新
                    if(swipeRefreshLayout.isRefreshing()){
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    // 触发上拉刷新
                    if(!adapter.isLoadingMore()) {
//                        adapter.setLoadingMore(true);
                        Log.i("llb", "触发上拉刷新");
                        loadMoreData(currentPage, pageSize);
                    }
                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                Log.i("llb", "setOnRefreshListener()");
                fetchData();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                Log.i("llb", "swipeRefreshLayout.post -> run()");
            }
        });

        btn_fetch = (Button) findViewById(R.id.btn_fetch);
        btn_fetch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData();
            }
        });
        loadMoreData(currentPage, pageSize);

    }
    public void fetchData() {
        Toast.makeText(this,"到头啦！！", Toast.LENGTH_SHORT).show();
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
            for (JokeData data: response.result.data) {
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



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pixabay_image) {
            Intent intent = new Intent();
            intent.setClass(this, PixabayMainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_wechat) {

        } else if (id == R.id.nav_funny_image) {

        } else if (id == R.id.nav_bbs) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
