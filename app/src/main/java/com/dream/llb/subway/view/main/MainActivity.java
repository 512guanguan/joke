package com.dream.llb.subway.view.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dream.llb.common.Constants;
import com.dream.llb.common.http.RetrofitServiceManager;
import com.dream.llb.joke.view.OnFragmentInteractionListener;
import com.dream.llb.subway.R;
import com.dream.llb.subway.model.api.SubwayURL;
import com.dream.llb.subway.view.about.AboutActivity;
import com.dream.llb.subway.view.base.BaseApplication;
import com.dream.llb.subway.view.base.base_activity.BaseActivity;
import com.dream.llb.subway.view.edit_post.EditPostActivity;
import com.dream.llb.subway.view.home_fragment.HomeFragment;
import com.dream.llb.subway.view.login.LoginActivity;
import com.dream.llb.subway.view.notice_msg.NoticeMsgActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnFragmentInteractionListener {
    private Context mContext;
    private ViewPager viewPager;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainViewPagerAdapter pagerAdapter;
    private BottomNavigationView bottomNavigationView;
    //    private List<Fragment> jokeFragemnts;
//    private List<Fragment> pixabayFragments;
    private List<Fragment> homeFragments;
    private MenuItem loginMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initADMob();
        fragmentManager = getSupportFragmentManager();

        // 1. 实例化BroadcastReceiver子类 &  IntentFilter
        receiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        // 2. 设置接收广播的类型
        intentFilter.addAction(Constants.BROADCAST_LOGIN_ACTION);
        intentFilter.addAction(Constants.BROADCAST_LOGOUT_ACTION);
        // 3. 动态注册：调用Context的registerReceiver（）方法
        registerReceiver(receiver, intentFilter);

        homeFragments = new ArrayList<>();
        homeFragments.add(new HomeFragment());
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.joke_first_fragment, jokeFragemnts.get(0));
//        fragmentTransaction.add(R.id.joke_second_fragment, jokeFragemnts.get(1));
//        fragmentTransaction.add(R.id.pixabay_first_fragment, pixabayFragments.get(0));
//        fragmentTransaction.add(R.id.pixabay_second_fragment, pixabayFragments.get(1));
//        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("地铁族");
        toolbar.setSubtitle("畅想城市的美好未来");
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        headerView.findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(mContext, headerView);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();
                RetrofitServiceManager.clearInstance();
                switch (id) {
                    case R.id.nav_my_notice:
                        if(BaseApplication.isLogin){
                            Intent intent = new Intent(mContext, NoticeMsgActivity.class);
                            mContext.startActivity(intent);
                        }else {
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            mContext.startActivity(intent);
                        }

                        break;
                    case R.id.nav_about:
                        Intent intent = new Intent(mContext, AboutActivity.class);
                        mContext.startActivity(intent);
                        break;
                    default:
                        break;
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_bottom_first) {
                    viewPager.setCurrentItem(0, true);
                } else if (id == R.id.nav_bottom_second) {
                    viewPager.setCurrentItem(1, true);
                } else {

                }
                return true;
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        pagerAdapter = new MainViewPagerAdapter(fragmentManager);
        pagerAdapter.addFragments(homeFragments);
        viewPager.setAdapter(pagerAdapter);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void initLoginMenuItem(MenuItem menuItem) {
        if (BaseApplication.isLogin) {
            menuItem.setTitle("退出");
        } else {
            menuItem.setTitle("登录");
        }
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
        loginMenuItem = menu.getItem(0);
        initLoginMenuItem(menu.getItem(0));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            doLogin(mContext, viewPager.getRootView());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
//        Toast.makeText(this, "implement OnFragmentInteractionListener", Toast.LENGTH_SHORT);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.BROADCAST_LOGIN_ACTION.equals(intent.getAction())
                    || Constants.BROADCAST_LOGOUT_ACTION.equals(intent.getAction())) {
                initLoginMenuItem(loginMenuItem);
            }
        }
    }
}
