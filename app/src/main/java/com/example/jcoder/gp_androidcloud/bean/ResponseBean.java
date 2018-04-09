package com.example.jcoder.gp_androidcloud.bean;

import java.io.Serializable;

/**
 * Created by ccb on 2017/10/11.
 *
 */


public class ResponseBean<T> implements Serializable {

    public int Code;
    public String Msg;
    public T Result;


}