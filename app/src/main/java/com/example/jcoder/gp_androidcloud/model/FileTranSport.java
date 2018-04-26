package com.example.jcoder.gp_androidcloud.model;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by JCoder on 2018/4/26.
 */

public class FileTranSport implements Serializable {
    private static final long serialVersionUID = 2072893447591548402L;

    public String name;
    public String url;
    public int type;
    public int priority;

    public FileTranSport(){
        Random random = new Random();
        priority = random.nextInt(100);
    }
}
