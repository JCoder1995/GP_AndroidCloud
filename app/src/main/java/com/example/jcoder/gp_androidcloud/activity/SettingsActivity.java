package com.example.jcoder.gp_androidcloud.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.fragment.SettingFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();
    }
}
