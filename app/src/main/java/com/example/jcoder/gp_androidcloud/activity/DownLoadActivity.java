package com.example.jcoder.gp_androidcloud.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.adapter.DownloadAdapter;
import com.example.jcoder.gp_androidcloud.base.BaseActivity;
import com.example.jcoder.gp_androidcloud.base.DividerItemDecoration;
import com.example.jcoder.gp_androidcloud.model.FileTranSport;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.task.XExecutor;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DownLoadActivity extends BaseActivity implements XExecutor.OnAllTaskEndListener{

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private DownloadAdapter adapter;
    private OkDownload okDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);

        okDownload = OkDownload.getInstance();
        adapter = new DownloadAdapter(this);
        adapter.updateData(DownloadAdapter.TYPE_ALL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        okDownload.addOnAllTaskEndListener(this);
    }


    @Override
    public void onAllTaskEnd() {
        Toast.makeText(this,"所有下载任务已结束",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        okDownload.removeOnAllTaskEndListener(this);
        adapter.unRegister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.down_deleteAll)
    public void removeAll(View view) {
        okDownload.removeAll();
        adapter.updateData(DownloadAdapter.TYPE_ALL);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.down_stopAll)
    public void pauseAll(View view) {
        okDownload.pauseAll();
    }

    @OnClick(R.id.down_startAll)
    public void startAll(View view) {
        okDownload.startAll();
    }
}

