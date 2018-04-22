package com.example.jcoder.gp_androidcloud.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jcoder.gp_androidcloud.R;

import com.lzy.okserver.OkDownload;
import com.lzy.okserver.task.XExecutor;

public class TransportActivity extends AppCompatActivity implements XExecutor.OnAllTaskEndListener {


    private OkDownload okDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        okDownload = OkDownload.getInstance();

        //设置全局下载变量
        String downLoadPath = Environment.getExternalStorageDirectory().getPath()+"/download";
        okDownload.setFolder(downLoadPath);
        okDownload.getThreadPool().setCorePoolSize(3);
        okDownload.addOnAllTaskEndListener(this);

    }

    @Override
    public void onAllTaskEnd() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        okDownload.removeOnAllTaskEndListener(this);
    }
}
