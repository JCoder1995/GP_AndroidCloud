package com.example.jcoder.gp_androidcloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.adapter.FileAdapter;
import com.example.jcoder.gp_androidcloud.bean.FileList;
import com.example.jcoder.gp_androidcloud.callbck.JsonCallback;
import com.example.jcoder.gp_androidcloud.net.OkUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileOperationActivity extends AppCompatActivity {

    @BindView(R.id.file_recycler)
    RecyclerView mRecyclerView;
    private String uid;
    private int fid=0;

    //文件adapter
    private FileAdapter fileAdapter;
    private ArrayList<FileList> fileLists = new ArrayList<FileList>();

    //定义用户目录
    private ArrayList<Integer> fileSystem = new ArrayList<Integer>();

    public ArrayList<FileList> FileOperation;
    //判断用户是复制还是移动
    private String mMoveOrCopy;
    //用户数据
    private ArrayList<Map<String,String>> file = new ArrayList<Map<String,String>>();
    //用户json
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_operation);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Snackbar.make(view, "正在操作", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                OkUtil.postFileChangeList(String.valueOf(fid), json, mMoveOrCopy, new JsonCallback<String>() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("FileOperationActivity",response.body().toString());
                        Snackbar.make(view, "操作成功", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        finish();
                    }
                });

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntentFromMainActivity();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initFileListView(uid,String.valueOf(fid));
        fileSystem.add(0);
    }

    //第一次加载文件 默认根目录
    private void  initFileListView(String uid, String parent){
        OkUtil.postFileFolderList(uid, parent, new JsonCallback<String>() {
            @Override
            public void onSuccess(Response<String> response) {
                JsonArray filesList = analysisJson(response);
                fileLists = getFilesList(filesList);
                fileAdapter = new FileAdapter(R.layout.file_list_main,fileLists);
                mRecyclerView.setAdapter(fileAdapter);
                fileAdapterClick();
            }
        });
    }
    //分析后台传过来的数据
    private JsonArray analysisJson(Response<String> response){
        JsonArray jsonArray1 = new JsonArray();
        if (response!=null){
            //Json的解析类对象
            JsonParser parser = new JsonParser();
            //将JSON的String 转成一个JsonArray对象
            JsonArray jsonArray = parser.parse(response.body().toString()).getAsJsonArray();
            for (int i = 0; i<jsonArray.size();i++){
                JsonObject jsonObject = (JsonObject) jsonArray.get(i);
                JsonObject jsonObject1 = (JsonObject) jsonObject.get("attrs");
                jsonArray1.add(jsonObject1);
            }
        }
        return jsonArray1;
    }

    //获取文件列表
    private ArrayList<FileList> getFilesList(JsonArray jsonElements) {

        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(jsonElements.toString()).getAsJsonArray();

        Gson gson = new Gson();
        ArrayList<FileList> filesBeanList = new ArrayList<>();

        //加强for循环遍历JsonArray
        for (JsonElement file : jsonArray) {
            //使用GSON，直接转成Bean对象
            FileList fileList = gson.fromJson(file, FileList.class);
            filesBeanList.add(fileList);
        }
        return  filesBeanList;
    }

    //1是复制2是移动
    private void getIntentFromMainActivity() {
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("FileOpera");
        uid = intent.getStringExtra("uid");
        FileOperation = (ArrayList<FileList>)args.getSerializable("FileOpera");
        mMoveOrCopy = intent.getStringExtra("type");

        for (FileList fileList :FileOperation){
            Map<String , String> map = new HashMap<String, String>();
                map.put("fid",String.valueOf(fileList.fid));
                file.add(map);
        }
        Gson gson = new Gson();
        json = gson.toJson(file);
        Log.e("FileOperationActivity",json);
    }

    private void fileAdapterClick() {
        fileAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //这里处理点击事件
                switch (((FileList) adapter.getItem(position)).filetype){
                    case 0:
                        fid = ((FileList) adapter.getItem(position)).fid;
                        Log.e("FileOperationActivity",String.valueOf(fid));
                        Log.e("FileOperationActivity",String.valueOf(fileSystem.size()));
                        fileSystem.add(fid);
                        refreshList();
                        break;
                }
            }
        });

    }

    //文件更新
    public void refreshList(){
        initFileListView(uid,String.valueOf(fid));
    }

    //监听返回键 如果导航栏在开启状态 自动回退导航栏
    @Override
    public void onBackPressed() {
        if (fileSystem.size()>1){
            fid =fileSystem.get(fileSystem.size()-2);
            refreshList();
            fileSystem.remove(fileSystem.size()-1);
        }
        else {
                finish();
        }
    }

}
