package com.example.jcoder.gp_androidcloud.model;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by JCoder on 2018/4/26.
 */

public class FileTranSport implements Serializable {
    private static final long serialVersionUID = 2072893447591548402L;

    public String name;  //文件名称
    public String url;   //文件路径
    public long size;    //文件大小
    public int type;     //文件类型

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setType(int type) {
        this.type = type;
    }
}
