package com.example.jcoder.gp_androidcloud.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jcoder.gp_androidcloud.fragment.FileTranSportDownloadFragment;
import com.example.jcoder.gp_androidcloud.fragment.FileTranSportOverFragment;
import com.example.jcoder.gp_androidcloud.fragment.FileTranSportUploadFragment;

/**
 * Created by JCoder on 2018/4/24.
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter{

    //由于页面已经固定,故这里把Adapter需要的fragment提前创建
    private Fragment[] mFragments = new Fragment[]{new FileTranSportUploadFragment(), new FileTranSportDownloadFragment(), new FileTranSportOverFragment()};

    public FragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() { return 3; }

}
