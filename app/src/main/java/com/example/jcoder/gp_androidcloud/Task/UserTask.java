package com.example.jcoder.gp_androidcloud.Task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jcoder.gp_androidcloud.bean.UserInfo;
import com.example.jcoder.gp_androidcloud.callbck.JsonCallback;
import com.example.jcoder.gp_androidcloud.net.OkUtil;
import com.lzy.okgo.model.Response;

/**
 * Created by JCoder on 2018/4/10.
 */

public class UserTask extends AsyncTask<Void,Void,UserInfo>{

    private final String mUsername;
    private UserCallBack userCallBack;
    private UserInfo userInfo;

    public UserTask(String mUsername) {
        this.mUsername = mUsername;
    }

    @Override
    protected UserInfo doInBackground(Void... voids) {
        OkUtil.postUser(mUsername, new JsonCallback<UserInfo>() {
            @Override
            public void onSuccess(Response<UserInfo> response) {
               Log.e("UserInfo",response.body().toString());
                userInfo = new UserInfo();
                userInfo.id=response.body().id;
                userInfo.nickName= response.body().nickName;
                userInfo.userName = response.body().userName;
                userInfo.phone = response.body().phone;
                userInfo.passWord = response.body().passWord;
            }
        });
        try {
            Thread.sleep(2000);
            return userInfo;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(UserInfo userInfo) {
        userCallBack.setUser(userInfo);
    }

    public void setUserInfoCallBack(UserCallBack userCallBack){
        this.userCallBack =userCallBack;
    }

    public interface UserCallBack{
        void setUser(UserInfo userInfo);
    }
}
