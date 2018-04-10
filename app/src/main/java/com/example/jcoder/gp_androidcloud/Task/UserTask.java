package com.example.jcoder.gp_androidcloud.Task;

import android.os.AsyncTask;

/**
 * Created by JCoder on 2018/4/10.
 */

public class UserTask extends AsyncTask<Void,Void,Object>{

    private final String mUsername;

    public UserTask(String mUsername) {
        this.mUsername = mUsername;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return null;
    }
}
