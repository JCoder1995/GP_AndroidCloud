package com.example.jcoder.gp_androidcloud.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class FileList implements Serializable {
    //"attrs":{"fid":46,"parent":0,"uid":3,"s_ctime":"Apr 16, 2018 9:30:42 PM","name":"新建文件夹","c_ctime":"Apr 16, 2018 9:30:42 PM
    public int fid;
    public int parent;
    public int uid;
    public int filetype;
    public Date s_ctime;
    public String name;
    public String size;
    public String filepath;
    public int priority;

    public FileList() {
        Random random = new Random();
        priority = random.nextInt(100);
    }
}
