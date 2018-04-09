package com.example.jcoder.gp_androidcloud.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.bean.UserBean;
import com.example.jcoder.gp_androidcloud.callbck.JsonCallback;
import com.example.jcoder.gp_androidcloud.enity.User;
import com.example.jcoder.gp_androidcloud.net.OkUtil;
import com.example.jcoder.gp_androidcloud.utility.UserSharedHelper;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

public class LauncherActivity extends AppCompatActivity {

    private UserSharedHelper userSharedHelper;
    private Context mContext;
    private String email;
    private String psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        hideToolbar();
        initActivity();
    }

    //隐藏标题栏
    private void hideToolbar() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }
    //启动Activity
    private void initActivity() {

        //获取用户信息
        mContext = getApplicationContext();
        userSharedHelper = new UserSharedHelper(mContext);
        Map<String, String> data = userSharedHelper.read();
        email = data.get("email");
        psw = data.get("psw");

        //如果用户信息为空 则进入登陆界面
        if (email == null||psw==null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        } else {
            OkUtil.postLogin(email, psw, new JsonCallback<Object>() {
                @Override
                public void onSuccess(Response<Object> response) {
                    Gson gson = new Gson();
                    UserBean userBean = gson.fromJson(String.valueOf(response), UserBean.class);
                }
            });
        }
    }

    }