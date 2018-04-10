package com.example.jcoder.gp_androidcloud.bean;

/**
 * Created by JCoder on 2018/4/9.
 */

public class UserBean {
        public int code;
        public String msg;

        public UserBean toResponseBean() {
                UserBean userBean = new UserBean();
                userBean.code = code;
                userBean.msg = msg;
                return userBean;
        }
}
