package com.example.jcoder.gp_androidcloud.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.enity.FileList;
import com.example.jcoder.gp_androidcloud.utility.CustomHelper;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.leon.lfilepickerlibrary.LFilePicker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,TakePhoto.TakeResultListener,InvokeListener {
     private static final String TAG = MainActivity.class.getName();
     private TakePhoto takePhoto;
     private InvokeParam invokeParam;
     private Button uploadPhoto;
     //文件显示相关
     private RecyclerView recyclerView;
     private List<String> listData;
     private LayoutInflater inflater;
     private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //加载TakePhoto框架
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        //获取拍照布局
      //  View contentView= LayoutInflater.from(this).inflate(R.layout.common_layout,null);

        setContentView(R.layout.activity_main);
        //设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置可滑动按钮 开启导航栏
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //加载导航栏布局
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //上传图片
        uploadPhoto= findViewById(R.id.btnPickBySelect);


        initFileView();
        initIData();
    }

    //获取TakePhoto实例
    @Override
    public void onSaveInstanceState(Bundle outPersistentState) {
        getTakePhoto().onSaveInstanceState(outPersistentState);
        super.onSaveInstanceState(outPersistentState);
    }

    //返回结果集
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }


    //监听返回键 如果导航栏在开启状态 自动回退导航栏
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //加载Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Menu选择
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        //Menu选择
         /*
        if (id == R.id  .action_settings) {
            return true;
        }
       */

         if(id == R.id.upload_file){
             int REQUESTCODE_FROM_ACTIVITY = 1000;
             new LFilePicker()
                     .withActivity(MainActivity.this)
                     .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                     .withStartPath("/storage/emulated/0/Download")//指定初始显示路径
                     .withIsGreater(false)//过滤文件大小 小于指定大小的文件
                     .withFileSize(500 * 1024)//指定文件大小为500K
                     .start();
         }
         else if (id == R.id.menu_upload_file){
             //模拟按钮点击
             uploadPhoto.performClick();
         }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_folder) {

        } else if (id == R.id.nav_collection) {

        } else if (id == R.id.nav_lately) {

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG,"takeSuccess：" + result.getImage().getCompressPath());
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }



    //加载文件显示
    private void initFileView(){
        recyclerView = findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(R.layout.file_list_show, null);
        recyclerView.setAdapter(myAdapter);
        inflater = getLayoutInflater();
    }

    //初始化数据
    private void initIData() {
        listData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            listData.add("index is =" + i);
        }
        myAdapter.addData(listData);
        myAdapter.notifyDataSetChanged();
    }


    //文件Adapter
    public class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        MyAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final String item) {

            helper.getView(R.id.checkbox).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this,"点击了CheckBox"+item,Toast.LENGTH_SHORT).show();

                }
            });

            helper.getView(R.id.right_menu_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "收藏"+item, Toast.LENGTH_SHORT).show();
                    EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);

                    easySwipeMenuLayout.resetStatus();
                }
            });
            helper.getView(R.id.content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "setOnClickListener", Toast.LENGTH_SHORT).show();

                }
            });

        }

    }
}
