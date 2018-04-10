package com.example.jcoder.gp_androidcloud.Task;

import android.os.AsyncTask;

import com.example.jcoder.gp_androidcloud.bean.UserBean;
import com.example.jcoder.gp_androidcloud.callbck.JsonCallback;
import com.example.jcoder.gp_androidcloud.net.OkUtil;
import com.lzy.okgo.model.Response;

/**
 * Created by JCoder on 2018/4/10.
 */

public class LoginTask extends AsyncTask<Void,Void,Boolean>{

    private final String mUsername;
    private final String mPassword;
    private LoginCallBack loginCallBack;

    public LoginTask(String mUsername, String mPassword) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        final int[] status = new int[1];
        OkUtil.postLogin(mUsername, mPassword, new JsonCallback<UserBean>() {
            @Override
            public void onSuccess(Response<UserBean> response) {
                status[0] = response.body().code;
            }
        });
        if (status[0] ==0)return true;
        else return  false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

        if (aBoolean) loginCallBack.setBoolean(true);
        else loginCallBack.setBoolean(false);

    }

    public void setLoginCallBack(LoginCallBack loginCallBack) {
        this.loginCallBack = loginCallBack;
    }
    public interface LoginCallBack {
        void setBoolean(Boolean status);
    }
}
