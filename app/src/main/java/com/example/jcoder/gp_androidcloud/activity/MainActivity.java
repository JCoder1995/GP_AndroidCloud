package com.example.jcoder.gp_androidcloud.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.Task.UserTask;
import com.example.jcoder.gp_androidcloud.bean.UserInfo;
import com.example.jcoder.gp_androidcloud.utility.UserSharedHelper;

import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.models.sort.SortingTypes;
import droidninja.filepicker.utils.Orientation;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
     //获取当前Activity名称
    private static final String TAG = MainActivity.class.getName();
    private Button uploadPhoto;

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    //定义文件模块
    private RecyclerView mRecyclerView;

    private LinearLayout popLayout;
    private ImageView back;

    //用户信息
    private String mUsername; //用户名
    private String nickName;  //真实姓名

    //定义UserTask
    private UserTask userTask;

    //用户存储
    private UserSharedHelper userSharedHelper;
    private Context mContext;

    //用户信息更新
    private ImageView userInfoImageView;
    private TextView userInfoNickName;
    private TextView userInfoUsername;
    //
    public static final int RC_PHOTO_PICKER_PERM = 123;

    //文件最大数量
    private int MAX_ATTACHMENT_COUNT = 10;
    private static final int CUSTOM_REQUEST_CODE = 532;

    private ArrayList<String> photoPaths = new ArrayList<>();
    private ArrayList<String> docPaths = new ArrayList<>();


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取SharedPreferences
        mContext = getApplicationContext();
        userSharedHelper = new UserSharedHelper(mContext);
        //获取登陆用户
        mUsername = getIntentUserInfo();
        getUserInfo(mUsername);
        //设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //获取隐藏布局
        popLayout =(LinearLayout)findViewById(R.id.popLayout);

        //布局可见
        //popLayout.setVisibility(View.VISIBLE);

        //设置可滑动按钮 开启导航栏
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //加载导航栏布局
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //获取NavigationView header中的控件
        setNavHead(navigationView);

        //文件查看
        mRecyclerView=(RecyclerView)findViewById(R.id.main_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setTitle("我的网盘");

    }

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

         if (id ==R.id.menu_upload_folder) {

         }
         else if (id == R.id.menu_upload_video){

         }
         else if (id == R.id.menu_upload_photo){
             pickPhotoClicked();
         }
         else if (id == R.id.menu_upload_doc){
             pickDocClicked();
         }

        return super.onOptionsItemSelected(item);
    }

    //对于左侧导航栏进行
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
        //    userSharedHelper.delete();
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //获取登陆用户名
    public String getIntentUserInfo(){
        Intent intent = getIntent();
        return intent.getStringExtra("username");
    }

    //获取用户信息 并存储到SharedPreferences
    public void getUserInfo(final String username){
        userTask = new UserTask(username);
        userTask.setUserInfoCallBack(new UserTask.UserCallBack() {
            @Override
            public void setUser(UserInfo userInfo) {
                nickName= userInfo.nickName;
                userInfoNickName.setText(nickName);
                userInfoUsername.setText(userInfo.userName);
                Log.e("nickName",nickName);

                userSharedHelper.save(username,userInfo.passWord,userInfo.id,userInfo.phone,userInfo.nickName);
            }
        });
        userTask.execute();
    }

    //设置用户名 真实姓名
    public void setNavHead(NavigationView navigationView){
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        //加载 用户头像 用户姓名 用户登录名
        userInfoImageView = headerView.findViewById(R.id.userInfoImageView);
        userInfoNickName = headerView.findViewById(R.id.userInfoNickName);
        userInfoUsername = headerView.findViewById(R.id.userInfoUsername);
    }

    //用户选择图片上传
    public void pickPhotoClicked() {
        if (EasyPermissions.hasPermissions(this, FilePickerConst.PERMISSIONS_FILE_PICKER)) {
            onPickPhoto();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo_picker),
                    RC_PHOTO_PICKER_PERM, FilePickerConst.PERMISSIONS_FILE_PICKER);
        }
    }

    //用户选择文档上传
    public void pickDocClicked() {
        if (EasyPermissions.hasPermissions(this, FilePickerConst.PERMISSIONS_FILE_PICKER)) {
            onPickDoc();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo_picker),
                    RC_PHOTO_PICKER_PERM, FilePickerConst.PERMISSIONS_FILE_PICKER);
        }
    }


    public void onPickPhoto() {
        int maxCount = MAX_ATTACHMENT_COUNT - docPaths.size();
        if ((docPaths.size() + photoPaths.size()) == MAX_ATTACHMENT_COUNT) {
            Toast.makeText(this, "Cannot select more than " + MAX_ATTACHMENT_COUNT + " items",
                    Toast.LENGTH_SHORT).show();
        } else {
            FilePickerBuilder.getInstance()
                    .setMaxCount(maxCount)
                    .setSelectedFiles(photoPaths)
                    .setActivityTheme(R.style.LibAppTheme)
                    .enableVideoPicker(false)
                    .enableSelectAll(true)
                    .setActivityTitle("选择图片")
                    .enableImagePicker(true)
                    .setCameraPlaceholder(R.drawable.custom_camera)
                    .withOrientation(Orientation.UNSPECIFIED)
                    .pickPhoto(this,CUSTOM_REQUEST_CODE);
        }
    }

    public void onPickDoc() {
        String[] zips   = { ".zip", ".rar" };
        String[] pdfs   = { ".pdf" };
        String[] docs   = { ".doc", ".docx", ".dot", ".dotx" };
        String[] excels = { ".xls",".xlsx"};
        String[] ppts   = { ".ppt",".pptx"};

        int maxCount = MAX_ATTACHMENT_COUNT - photoPaths.size();
        if ((docPaths.size() + photoPaths.size()) == MAX_ATTACHMENT_COUNT) {
            Toast.makeText(this, "Cannot select more than " + MAX_ATTACHMENT_COUNT + " items",
                    Toast.LENGTH_SHORT).show();
        } else {
            FilePickerBuilder.getInstance()
                    .setMaxCount(10)
                    .setSelectedFiles(docPaths)
                    .setActivityTheme(R.style.LibAppTheme)
                    .setActivityTitle("选择文件")
                    .addFileSupport("ZIP", zips)
                    .addFileSupport("PDF", pdfs)
                    .addFileSupport("DOC",docs)
                    .addFileSupport("XLS",excels)
                    .addFileSupport("PPT",ppts)
                    .enableDocSupport(false)
                    .sortDocumentsBy(SortingTypes.name)
                    .withOrientation(Orientation.UNSPECIFIED)
                    .pickFile(this);
        }
    }

}
