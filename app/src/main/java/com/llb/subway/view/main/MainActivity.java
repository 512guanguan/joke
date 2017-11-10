package com.llb.subway.view.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

import com.llb.common.http.RetrofitServiceManager;
import com.llb.joke.R;
import com.llb.joke.view.OnFragmentInteractionListener;
import com.llb.subway.view.base.BaseActivity;
import com.llb.subway.view.home_fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnFragmentInteractionListener {
    private ViewPager viewPager;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainViewPagerAdapter pagerAdapter;
    private BottomNavigationView bottomNavigationView;
//    private List<Fragment> jokeFragemnts;
//    private List<Fragment> pixabayFragments;
    private List<Fragment> homeFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
//        jokeFragemnts = new ArrayList<>();
//        jokeFragemnts.add(new JokeFirstFragment());
//        jokeFragemnts.add(new JokeSecondFragment());

//        pixabayFragments = new ArrayList<>();
//        pixabayFragments.add(new PixabayFirstFragment());
//        pixabayFragments.add(new PixabaySecondFragment());

        homeFragments = new ArrayList<>();
        homeFragments.add(new HomeFragment());
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.joke_first_fragment, jokeFragemnts.get(0));
//        fragmentTransaction.add(R.id.joke_second_fragment, jokeFragemnts.get(1));
//        fragmentTransaction.add(R.id.pixabay_first_fragment, pixabayFragments.get(0));
//        fragmentTransaction.add(R.id.pixabay_second_fragment, pixabayFragments.get(1));
//        fragmentTransaction.commit();

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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();
                RetrofitServiceManager.clearInstance();
                switch (id){
                    case R.id.nav_pixabay_image:
//                        pagerAdapter.addFragments(pixabayFragments);
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.hide(jokeFragemnts.get(0));
//                    fragmentTransaction.hide(jokeFragemnts.get(1));
                        break;
                    case R.id.nav_wechat:
                        break;
                    case R.id.nav_bbs:
                        pagerAdapter.addFragments(homeFragments);
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
                    viewPager.setCurrentItem(0,true);
                } else if (id == R.id.nav_bottom_second) {
                    viewPager.setCurrentItem(1,true);
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

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, "implement OnFragmentInteractionListener", Toast.LENGTH_SHORT);
    }
}