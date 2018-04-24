package com.example.jcoder.gp_androidcloud.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.fragment.FileTranSportDownloadFragment;
import com.example.jcoder.gp_androidcloud.fragment.FileTranSportOverFragment;
import com.example.jcoder.gp_androidcloud.fragment.FileTranSportUploadFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileTranSportActivity extends FragmentActivity {

    @BindView(R.id.fileTranSportViewPager) ViewPager mViewPager;
    @BindView((R.id.navigation)) BottomNavigationView navigation;

    private FileTranSportUploadFragment mileTranSportUploadFragment;
    private FileTranSportDownloadFragment mFileTranSportDownloadFragment;
    private FileTranSportOverFragment   mFileTranSportOverFragment;
    private Fragment[] fragments;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_tran_sport);
        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
