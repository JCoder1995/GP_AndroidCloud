package com.example.jcoder.gp_androidcloud.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.utility.UserSharedHelper;

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

     //   Toast.makeText(LauncherActivity.this,"用户名为空",Toast.LENGTH_SHORT).show();
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

        }
    }

    public void userLogin(String userName,String Password){

    }
    }