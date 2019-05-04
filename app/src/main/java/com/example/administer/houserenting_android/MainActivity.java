package com.example.administer.houserenting_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administer.houserenting_android.adapter.ViewPagerAdapter;
import com.example.administer.houserenting_android.view.AddHouseActivity;
import com.example.administer.houserenting_android.view.MineFragment;
import com.example.administer.houserenting_android.view.RentFragment;
import com.example.administer.houserenting_android.view.RequestFragment;
import com.example.administer.houserenting_android.view.SearchActivity;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;//内容容器
    private BottomNavigationView navigation;//底部导航栏
    private ViewGroup searchBar;//搜索栏
    private FloatingActionButton addButton;//添加按钮

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    searchBar.setVisibility(View.VISIBLE);
                    addButton.setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    searchBar.setVisibility(View.VISIBLE);
                    addButton.setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    addButton.setVisibility(View.GONE);
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
        initListener();

    }

    //视图初始化
    private void initView(){
        navigation= (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = findViewById(R.id.viewpager);
        searchBar = findViewById(R.id.search_bar);
        addButton = findViewById(R.id.add_button);
        initViewPager();


    }

    private void initListener(){
        addButton.setOnClickListener(onClickListener);
        searchBar.setOnClickListener(onClickListener);
    }
    //监听初始化
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_button:
                    if (viewPager.getCurrentItem()==0){
                        startActivity(new Intent(getApplicationContext(),AddHouseActivity.class).putExtra("title","添加租房信息"));
                    }else {
                        startActivity(new Intent(getApplicationContext(),AddHouseActivity.class).putExtra("title","添加求租信息"));
                    }

                    break;
                case R.id.search_bar:
                    startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                    break;
            }
        }
    };

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
