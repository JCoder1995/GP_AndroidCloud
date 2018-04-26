package com.example.jcoder.gp_androidcloud.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.adapter.UploadAdapter;
import com.example.jcoder.gp_androidcloud.base.BaseActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okserver.OkUpload;
import com.lzy.okserver.task.XExecutor;
import com.lzy.okserver.upload.UploadTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UploadActivity extends BaseActivity implements XExecutor.OnAllTaskEndListener{

    private OkUpload okUpload;
    private UploadAdapter adapter;

    private List<UploadTask<?>> tasks;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.deleteAll)
    Button delete;
    @BindView(R.id.upload) Button upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_list);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("uploadPhoto");
        ArrayList<String> object = (ArrayList<String>) args.getSerializable("uploadPhoto");
        Log.e("asdsadsadsadas",object.toString());

        okUpload = OkUpload.getInstance();
        okUpload.getThreadPool().setCorePoolSize(1);

        adapter = new UploadAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        okUpload.addOnAllTaskEndListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        okUpload.removeOnAllTaskEndListener(this);
        adapter.unRegister();
    }

    @Override
    public void onAllTaskEnd() {
        showToast("所有任务已经上传完成");
    }
}
