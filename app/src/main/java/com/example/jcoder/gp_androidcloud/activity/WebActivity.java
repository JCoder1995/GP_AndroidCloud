package com.example.jcoder.gp_androidcloud.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.jcoder.gp_androidcloud.R;
import butterknife.ButterKnife;


public class WebActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
    }
}