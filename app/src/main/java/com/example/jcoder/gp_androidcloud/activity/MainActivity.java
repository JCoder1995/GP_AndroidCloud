package com.example.jcoder.gp_androidcloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jcoder.gp_androidcloud.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.leon.lfilepickerlibrary.LFilePicker;

import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
     //获取当前Activity名称
     private static final String TAG = MainActivity.class.getName();
     //获取TakePhoto
     private TakePhoto takePhoto;
     private InvokeParam invokeParam;
     private Button uploadPhoto;

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    private LinearLayout popLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //获取隐藏布局
        popLayout =(LinearLayout)findViewById(R.id.popLayout);

        popLayout.setVisibility(View.VISIBLE);

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
    }

    //获取TakePhoto实例
    @Override
    public void onSaveInstanceState(Bundle outPersistentState) {
        super.onSaveInstanceState(outPersistentState);
    }

    //监听返回键 如果导航栏在开启状态 自动回退导航栏
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                Toast.makeText(this, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
            }
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

}
