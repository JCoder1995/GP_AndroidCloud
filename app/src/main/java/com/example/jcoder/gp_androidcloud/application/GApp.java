package com.example.jcoder.gp_androidcloud.application;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * Created by JCoder on 2018/4/26.
 */

public class GApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
    }
}
