package com.example.jcoder.gp_androidcloud.Task;

import android.os.AsyncTask;
import android.util.Log;

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
    private int status[] = new int[1] ;

    public LoginTask(String mUsername, String mPassword) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        OkUtil.postLogin(mUsername, mPassword, new JsonCallback<UserBean>() {
            @Override
            public void onSuccess(Response<UserBean> response) {
                status[0] = response.body().code;
                Log.e("response", String.valueOf(status[0]));
            }
        });
        try {
            Thread.sleep(2000);

            if ( status[0]== -1){
                Log.e("response_status","-1");
                return false;
            }

            else if (status[0] == 0){
                Log.e("response_status","0");
                return true;
            }

            else if (status[0] == 1){
                Log.e("response_status","1");
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
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
