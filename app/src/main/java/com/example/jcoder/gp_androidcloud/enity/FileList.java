package com.example.jcoder.gp_androidcloud.enity;

/**
 * Created by JCoder on 2018/3/19.
 */

public class FileList {
    private int fileId;
    private int fileParent;
    private String fileName;
    private String fileCreateTime;
    private String fileUpdateTime;
    private String fileNowNumber;

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getFileParent() {
        return fileParent;
    }

    public void setFileParent(int fileParent) {
        this.fileParent = fileParent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileCreateTime() {
        return fileCreateTime;
    }

    public void setFileCreateTime(String fileCreateTime) {
        this.fileCreateTime = fileCreateTime;
    }

    public String getFileUpdateTime() {
        return fileUpdateTime;
    }

    public void setFileUpdateTime(String fileUpdateTime) {
        this.fileUpdateTime = fileUpdateTime;
    }

    public String getFileNowNumber() {
        return fileNowNumber;
    }

    public void setFileNowNumber(String fileNowNumber) {
        this.fileNowNumber = fileNowNumber;
    }

}
