package com.example.jcoder.gp_androidcloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.adapter.UploadAdapter;
import com.example.jcoder.gp_androidcloud.base.BaseActivity;
import com.example.jcoder.gp_androidcloud.model.FileTranSport;
import com.lzy.okserver.OkUpload;
import com.lzy.okserver.task.XExecutor;
import com.lzy.okserver.upload.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UploadActivity extends BaseActivity implements XExecutor.OnAllTaskEndListener{

    @BindView(R.id.upload_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.upload_deleteAll)
    Button delete;
    @BindView(R.id.upload_begin) Button upload;

    private UploadAdapter adapter;
    private OkUpload okUpload;
    private List<UploadTask<?>> tasks;

    private String uid;
    private int fid;

    private List<String> fileListName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_list);


        okUpload = OkUpload.getInstance();
        okUpload.getThreadPool().setCorePoolSize(1);

        adapter = new UploadAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getIntentFromMainActivity();

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


    public void getIntentFromMainActivity() {

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("uploadPhoto");
        Bundle args1 = intent.getBundleExtra("uploadPhoto");
        fileListName = new ArrayList<String>();

        uid =intent.getStringExtra("uid");
        String fidFromActivity=getIntent().getStringExtra("fid");
        Log.e("wqewrgsdg",fidFromActivity);
        fid =Integer.parseInt(fidFromActivity);
        Log.e("wqewrgsdg",String.valueOf(fid));

        ArrayList<String> uploadPhoto = (ArrayList<String>) args.getSerializable("uploadPhoto");
        ArrayList<String> uploadDoc = (ArrayList<String>) args1.getSerializable("uploadPhoto");

        if (uploadPhoto!=null){
            List<FileTranSport> fileTranSports = new ArrayList<>();
            for (int i =0;i<uploadPhoto.size();i++){
                File file = new File(uploadPhoto.get(i));
                FileTranSport fileTranSport = new FileTranSport();
                fileListName.add(file.getName());
                fileTranSport.setName(file.getName());
                fileTranSport.setUrl(file.getAbsolutePath());
                fileTranSport.setSize(file.length());
                fileTranSport.setType(6);
                fileTranSports.add(fileTranSport);
            }
            Log.e("sadsadsadas",fileListName.toString());

            tasks = adapter.updateData(fileTranSports,uid,fid,fileListName);
        }
        else {
            // showToast("没有数据");
        }
    }

    @OnClick(R.id.upload_begin)
    public void upload_begin(View view){
        if (tasks == null) {
            showToast("请先选择图片");
            return;
        }
        for (UploadTask<?> task : tasks) {
            task.start();
        }
    }

    }

