package com.example.jcoder.gp_androidcloud.net;


import android.util.Log;

import com.example.jcoder.gp_androidcloud.bean.FileList;
import com.example.jcoder.gp_androidcloud.bean.UserBean;
import com.example.jcoder.gp_androidcloud.callbck.JsonCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.Map;


/**
 * Created by ccb on 2017/10/12.
 * 网络框架二次封装
 */

public class OkUtil {
    /**
     *
     * @param url
     * @param tag
     * @param map
     * @param callback
     * @param <T>
     */

    private static String ip ="http://10.117.129.170:8081";
    private static String userLoginUrl = ip+"/user/login";
    private static String userRegisterUrl = ip+"/user/register";
    private static String userInfo = ip+"/user/getUser";

    private static String getFileList = ip+"/file/getFileList";

    // 测试网络联通类
    public static void postRequest(JsonCallback<UserBean> callback) {
        // TODO: 2017/10/13  加密 时间戳等 请求日志打印
        Log.d("OkGoUtil", "method post");
        OkGo.<UserBean>post(ip)
                .execute(callback);
    }
    //用户登陆
    public static <T> void postLogin(String userName, String passWord, JsonCallback<UserBean> callback){
        Log.d("OkGoUtil", "用户登陆");
        OkGo.<UserBean>post(userLoginUrl)
                .params("username",userName)
                .params("password",passWord)
                .execute(callback);
    }
    //用户注册
    public static <T> void postRegister(String userName, String passWord,String nickName,String phone, JsonCallback<T> callback){
        Log.d("OkGoUtil", "用户登陆");
        OkGo.<T>post(userRegisterUrl)
                .params("username",userName)
                .params("password",passWord)
                .params("nickName",nickName)
                .params("phone",phone)
                .execute(callback);
    }
    //获取用户信息
    public static <T> void postUser(String userName, JsonCallback<T> callback){
        Log.d("OkGoUtil", "获取用户信息");
        OkGo.<T>post(userInfo)
                .params("username",userName)
                .execute(callback);
    }

    //获取用户信息
    public static <T> void postFileList(String userName,String fileParent, JsonCallback<T> callback){
        Log.d("OkGoUtil", "获取用户文件");
        OkGo.<T>post(getFileList)
                .params("uid",userName)
                .params("fid",fileParent)
                .execute(callback);
    }


}
