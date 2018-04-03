package com.example.jcoder.gp_androidcloud.enity;

/**
 * Created by JCoder on 2018/3/26.
 */

public class User {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    private int fileId;
    private int fileParent;
    private String userName;
    private String userEmail;
    private String userPhone;

}
