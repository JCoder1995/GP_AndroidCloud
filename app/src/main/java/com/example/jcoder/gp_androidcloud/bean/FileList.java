package com.example.jcoder.gp_androidcloud.bean;

import java.util.Date;

public class FileList {
    //"attrs":{"fid":46,"parent":0,"uid":3,"s_ctime":"Apr 16, 2018 9:30:42 PM","name":"新建文件夹","c_ctime":"Apr 16, 2018 9:30:42 PM
    public int fid;
    public int parent;
    public Date date;
    public String fileName;
    public int filrType;

    public FileList toFileList(){
        FileList fileList = new FileList();
        fileList.fid = fid;
        fileList.parent = parent;
        fileList.fileName = fileName;
        fileList.filrType = filrType;
        return  toFileList();
    }
}
