package com.example.jcoder.gp_androidcloud.activity;

import android.os.Bundle;

import com.example.jcoder.gp_androidcloud.R;
import com.jph.takephoto.app.TakePhotoActivity;

public class SelectPhotoUploadActivity extends TakePhotoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo_upload);
        getTakePhoto();
    }
}
