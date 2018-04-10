package com.example.jcoder.gp_androidcloud.exception;


import com.example.jcoder.gp_androidcloud.bean.UserBean;
import com.google.gson.Gson;

/**
 * Created by ccb on 2017/12/7.
 */

public class MyException extends IllegalStateException {

  private UserBean userBean;

    public MyException(String s) {
        super(s);
        userBean = new Gson().fromJson(s, UserBean.class);
    }

    public UserBean getErrorBean() {
        return userBean;
    }
}
