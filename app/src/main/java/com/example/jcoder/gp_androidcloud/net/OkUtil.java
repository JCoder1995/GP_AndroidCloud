package com.example.jcoder.gp_androidcloud.net;


import android.util.Log;

import com.example.jcoder.gp_androidcloud.bean.FileList;
import com.example.jcoder.gp_androidcloud.bean.UserBean;
import com.example.jcoder.gp_androidcloud.callbck.JsonCallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
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

    private static String ip ="http://115.159.209.140:8080/GP_BackStage";
    private static String userLoginUrl = ip+"/user/login";
    private static String userRegisterUrl = ip+"/user/register";
    private static String userInfo = ip+"/user/getUser";

    private static String getFileList = ip+"/file/getFileList";
    private static String getFileFolderList = ip+"/file/getFileFolderList";
    private static String postFileChangeList = ip+"/file/postFileChangeList";
    private static String searchFileList = ip+"/file/searchFileList";
    private static String AddFolder = ip+"/file/AddFolder";


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
    //查找文件信息
    public static <T> void searchFileList(String userName,String fileParent, JsonCallback<T> callback){
        Log.d("OkGoUtil", "查找用户文件");
        OkGo.<T>post(searchFileList)
                .params("uid",userName)
                .params("query",fileParent)
                .execute(callback);
    }
    //获取用户文件夹
    public static <T> void postFileFolderList(String userName,String fileParent, JsonCallback<T> callback){
        Log.d("OkGoUtil", "获取用户文件");
        OkGo.<T>post(getFileFolderList)
                .params("uid",userName)
                .params("fid",fileParent)
                .execute(callback);
    }
    //
    public static <T> void postFileChangeList(String fid,String file,String type, JsonCallback<T> callback){
        Log.d("OkGoUtil", "获取用户文件");
        OkGo.<T>post(postFileChangeList)
                .params("fid",fid)
                .params("file",file)
                .params("type",type)
                .execute(callback);
    }
    public static <T> void AddFolder(String uid,String fid,String name, JsonCallback<T> callback){
        Log.d("OkGoUtil", "新建文件夹");
        OkGo.<T>post(AddFolder)
                .params("uid",uid)
                .params("fid",fid)
                .params("name",name)
                .execute(callback);
    }



}
