package com.example.jcoder.gp_androidcloud.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.adapter.FragmentViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileTranSportActivity extends FragmentActivity  implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    //加载布局绑定监听事件
    @BindView(R.id.fileTranSportViewPager) ViewPager mViewPager;
    @BindView((R.id.navigation)) BottomNavigationView mBottomNavigationView;
    @BindView((R.id.file_transport_activity_toolbar)) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_tran_sport);
        //ButterKnife 绑定控件
        ButterKnife.bind(this);
        initToolbar();
        initListener();
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //执行返回方法onBackPressed()
                onBackPressed();
            }
        });
    }

    private void initListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mViewPager.addOnPageChangeListener(this);
        //为viewpager设置adapter
        mViewPager.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager()));
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.navigation_home:
                mViewPager.setCurrentItem(0);
                return true;//返回true,否则tab按钮不变色,未被选中
            case R.id.navigation_dashboard:
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_notifications:
                mViewPager.setCurrentItem(2);
                return true;

            default:
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position) {
            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
                break;
            case 2:
                mBottomNavigationView.setSelectedItemId(R.id.navigation_notifications);
                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}