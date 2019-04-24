package com.example.administer.houserenting_android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administer.houserenting_android.adapter.ViewPagerAdapter;
import com.example.administer.houserenting_android.view.MineFragment;
import com.example.administer.houserenting_android.view.RentFragment;
import com.example.administer.houserenting_android.view.RequestFragment;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView navigation;
    private ViewGroup searchBar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    searchBar.setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    searchBar.setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    searchBar.setVisibility(View.GONE);
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView(){
        navigation= (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = findViewById(R.id.viewpager);
        searchBar = findViewById(R.id.search_bar);
        initViewPager();
    }

    private void initViewPager(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(RentFragment.newInstance());
        adapter.addFragment(RequestFragment.newInstance());
        adapter.addFragment(MineFragment.newInstance());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                navigation.getMenu().getItem(i).setChecked(false);
                if (i==2){
                    searchBar.setVisibility(View.GONE);
                }else {
                    searchBar.setVisibility(View.VISIBLE);
                }
//                viewPager.setCurrentItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

}
