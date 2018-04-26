package com.example.jcoder.gp_androidcloud.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.base.BaseActivity;
import com.example.jcoder.gp_androidcloud.model.FileTranSport;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;

import java.util.List;

import butterknife.BindView;

public class DownLoadActivity extends BaseActivity{

    private static final int REQUEST_PERMISSION_STORAGE = 0x01;
    private List<FileTranSport> apks;
    private OkDownload okDownload;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.targetFolder)
    TextView folder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<FileTranSport> fileTranSports;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);
        //设置默认下载路径
        OkDownload.getInstance().setFolder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/");
        //设置同时下载个数
        OkDownload.getInstance().getThreadPool().setCorePoolSize(3);


        //从数据库中恢复数据
        List<Progress> progressList = DownloadManager.getInstance().getAll();
        OkDownload.restore(progressList);

        //检查写入许可
        checkSDCardPermission();
    }

    /** 检查SD卡权限 */
    protected void checkSDCardPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //获取权限
            } else {
                showToast("权限被禁止，无法下载文件！");
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
