package com.example.jcoder.gp_androidcloud.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.Task.LoginTask;
import com.example.jcoder.gp_androidcloud.bean.UserBean;
import com.example.jcoder.gp_androidcloud.callbck.JsonCallback;
import com.example.jcoder.gp_androidcloud.net.OkUtil;
import com.example.jcoder.gp_androidcloud.utility.UserSharedHelper;
import com.lzy.okgo.model.Response;

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
      //  getConnect();
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
        email = data.get("username");
        psw = data.get("password");
        Log.e("errorInformation",email+psw);

        //如果用户信息为空 则进入登陆界面
        if (email == ""||psw=="") {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        } else {
            LoginTask loginTask = new LoginTask(email,psw);
            loginTask.setLoginCallBack(new LoginTask.LoginCallBack() {
                @Override
                public void setBoolean(Boolean status) {
                    Log.e("LauncherActivity",String.valueOf(status));
                    if (status){
                         Toast.makeText(LauncherActivity.this,getString(R.string.connect_user_success),Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                         intent.putExtra("username",email);
                         startActivity(intent);
                         finish();
                    }
                 else {
                     userSharedHelper.delete();
                     Toast.makeText(LauncherActivity.this,getString(R.string.connect_user_error),Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);
                     startActivity(intent);
                     finish();
                 }
                }
            });
            loginTask.execute();
        }
    }

    private void getConnect(){
        OkUtil.postRequest(new JsonCallback<UserBean>() {
            @Override
            public void onSuccess(Response<UserBean> response) {
                Toast.makeText(LauncherActivity.this,getString(R.string.connect_Success),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Response<UserBean> response) {
                Toast.makeText(LauncherActivity.this,getString(R.string.connect_Error),Toast.LENGTH_SHORT).show();
            }
        });
     }

    }