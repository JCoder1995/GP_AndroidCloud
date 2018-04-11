package com.example.jcoder.gp_androidcloud.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JCoder on 2018/3/26.
 */

public class UserSharedHelper {

    private Context mContext;

    public UserSharedHelper() {
    }

    public UserSharedHelper(Context mContext) {
        this.mContext = mContext;
    }
    //定义一个读取SP文件的方法
    public Map<String, String> read() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("username", sp.getString("username", ""));
        data.put("password", sp.getString("password", ""));
        return data;
    }
    //定义一个保存数据的方法
    public void save(String username, String password) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
        Toast.makeText(mContext, "用户信息已保存", Toast.LENGTH_SHORT).show();
    }
    //清楚用户数据
    public void delete(){
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

        SharedPreferences sp1 = mContext.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp1.edit();
        editor1.clear();
        editor1.commit();

    }
    public void save(String username, String password,String id,String phone,String nickName ){
        SharedPreferences sp = mContext.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("id", id);
        editor.putString("phone", phone);
        editor.putString("nickName", nickName);
        editor.commit();
    }

}
