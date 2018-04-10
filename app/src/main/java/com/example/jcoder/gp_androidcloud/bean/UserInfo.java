package com.example.jcoder.gp_androidcloud.bean;

/**
 * Created by JCoder on 2018/4/10.
 */

public class UserInfo {

    public String id;
    public String userName;
    public String nickName;
    public String phone;

    public UserInfo toUserInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.id=id;
        userInfo.userName=userName;
        userInfo.nickName=nickName;
        userInfo.phone=phone;
        return toUserInfo();
    }
}
