package com.example.jcoder.gp_androidcloud.net;


import android.util.Log;

import com.example.jcoder.gp_androidcloud.callbck.JsonCallback;
import com.lzy.okgo.OkGo;

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

  private static String ip ="http://localhost";

  private static String userLoginUrl = ip+"/user/login";
    private static String userRegisterUrl = ip+"/user/register";

    // 测试网络联通类
    public static <T> void getRequets(String url, Object tag, Map<String, String> map, JsonCallback<T> callback) {
        // TODO: 2017/10/13  加密 时间戳等 请求日志打印
        Log.d("OkGoUtil", "method get");
        OkGo.<T>get(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }
    public static <T> void postRequest(String url, Object tag, Map<String, String> map, JsonCallback<T> callback) {
        // TODO: 2017/10/13  加密 时间戳等 请求日志打印
        Log.d("OkGoUtil", "method post");
        OkGo.<T>post(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }
    //用户登陆
    public static <T> void postLogin(String userName, String passWord, JsonCallback<T> callback){
        Log.d("OkGoUtil", "用户登陆");
        OkGo.<T>post(userLoginUrl)
                .params("username",userName)
                .params("passowrd",passWord)
                .execute(callback);
    }
    //用户注册
    public static <T> void postRegister(String userName, String passWord,String nickName,String phone, JsonCallback<T> callback){
        Log.d("OkGoUtil", "用户登陆");
        OkGo.<T>post(userRegisterUrl)
                .params("username",userName)
                .params("passowrd",passWord)
                .params("nickName",nickName)
                .params("phone",phone)
                .execute(callback);
    }

}
