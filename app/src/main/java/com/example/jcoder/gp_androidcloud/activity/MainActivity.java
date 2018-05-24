package com.example.jcoder.gp_androidcloud.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.Task.UserTask;
import com.example.jcoder.gp_androidcloud.adapter.FileAdapter;
import com.example.jcoder.gp_androidcloud.base.DividerItemDecoration;
import com.example.jcoder.gp_androidcloud.bean.FileList;
import com.example.jcoder.gp_androidcloud.bean.UserInfo;
import com.example.jcoder.gp_androidcloud.callbck.JsonCallback;
import com.example.jcoder.gp_androidcloud.listener.LogDownloadListener;
import com.example.jcoder.gp_androidcloud.net.OkUtil;
import com.example.jcoder.gp_androidcloud.utility.SmoothCheckBox;
import com.example.jcoder.gp_androidcloud.utility.UserSharedHelper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.task.XExecutor;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.models.sort.SortingTypes;
import droidninja.filepicker.utils.Orientation;

import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, XExecutor.OnAllTaskEndListener {
    private Button uploadPhoto;
    //下载返回码
    private static final int REQUEST_PERMISSION_STORAGE = 0x01;

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

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

    //文件最大数量
    private int MAX_ATTACHMENT_COUNT = 10;

    public static final int RC_PHOTO_PICKER_PERM = 123;
    public static final int RC_FILE_PICKER_PERM = 321;
    private static final int CUSTOM_REQUEST_CODE = 532;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private ArrayList<String> docPaths = new ArrayList<>();

    //定义下拉刷新
    private EasyRefreshLayout mEasyRefreshLayout;

    //定义RecyclerView
    private RecyclerView mRecyclerView;

    //定义uid fid
    private String  uid;
    private int  fid=0;

    //定义用户目录
    private ArrayList<Integer> fileSystem = new ArrayList<Integer>();

    //文件adapter
    private FileAdapter fileAdapter;
    private ArrayList<FileList> fileLists = new ArrayList<FileList>();

    //文件多选
    private CheckBox smoothCheckBox;

    private ArrayList<FileList> fileListsDownload = new ArrayList<FileList>();

    //用户数据
    private ArrayList<Map<String,String>> file = new ArrayList<Map<String,String>>();

    @BindView(R.id.download)
    LinearLayout linearLayout_downLoad;
    @BindView(R.id.move)
    LinearLayout linearLayout_move;
    @BindView(R.id.copy)
    LinearLayout linearLayout_copy;
    @BindView(R.id.delete)
    LinearLayout linearLayout_delete;
    @BindView(R.id.cancel)
    LinearLayout linearLayout_cancel;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //获取多选
        smoothCheckBox = (CheckBox) LayoutInflater.from(MainActivity.this).inflate(R.layout.file_list_main,null).findViewById(R.id.file_smooth_checkbox);

        fileSystem.add(0);
        //获取SharedPreferences
        mContext = getApplicationContext();
        userSharedHelper = new UserSharedHelper(mContext);
        //获取登陆用户
        mUsername = getIntentUserInfo();

        //设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //获取隐藏布局
        popLayout =(LinearLayout)findViewById(R.id.popLayout);
        //下拉刷新 处理
        initEasyRefreshLayout();
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
        getUserInfo(mUsername);
        //初始化RecyclerView
        initRecyclerView();
        setTitle("我的网盘");
        downLoadFileList();
        searchViewLinstener();
    }

    private void searchViewLinstener() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //查找操作
                searchInformation(uid,query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }
    //用户查找信息
    private void searchInformation(String uid, String query) {
        OkUtil.searchFileList(uid, query, new JsonCallback<String>() {
            @Override
            public void onSuccess(Response<String> response) {
                JsonArray filesList = analysisJson(response);
                fileLists = getFilesList(filesList);
                fileAdapter = new FileAdapter(R.layout.file_list_main,fileLists);
                mRecyclerView.setAdapter(fileAdapter);
                fileAdapterClick();
                mEasyRefreshLayout.refreshComplete();
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outPersistentState) {
        super.onSaveInstanceState(outPersistentState);
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
        }

    //加载Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    //Menu选择
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


         if (id ==R.id.menu_upload_doc) {
             pickDocClicked();
         }

         else if (id == R.id.menu_upload_photo){
             pickPhotoClicked();
         }
         else if (id ==R.id.menu_upload_folder){
             addFolder();
         }
        /* else if (id == R.id.menu_upload_video){
         }*/
        return super.onOptionsItemSelected(item);
    }

    //对于左侧导航栏进行
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upload) {
            Intent intent = new Intent(MainActivity.this,UploadActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_download){
            Intent intent = new Intent(MainActivity.this,DownLoadActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_manage) {
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
    //初始化数据
    public void getUserInfo(final String username){
        userTask = new UserTask(username);

        userTask.setUserInfoCallBack(new UserTask.UserCallBack() {
            @Override
            public void setUser(UserInfo userInfo) {
                //Bug 修复
                uid =userInfo.id;
                nickName= userInfo.nickName;
                userInfoNickName.setText(nickName);
                userInfoUsername.setText(userInfo.userName);
                mEasyRefreshLayout.refreshComplete();
                userSharedHelper.save(username,userInfo.passWord,userInfo.id,userInfo.phone,userInfo.nickName);
                initFileListView(uid,String.valueOf(fid));

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

    //用户选择图片上传权限获取
    public void pickPhotoClicked() {
        if (EasyPermissions.hasPermissions(this, FilePickerConst.PERMISSIONS_FILE_PICKER)) {
            onPickPhoto();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo_picker),
                    RC_PHOTO_PICKER_PERM, FilePickerConst.PERMISSIONS_FILE_PICKER);
            pickPhotoClicked();
        }

    }

    //用户选择文档上传权限获取
    public void pickDocClicked() {
        if (EasyPermissions.hasPermissions(this, FilePickerConst.PERMISSIONS_FILE_PICKER)) {
            onPickDoc();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_photo_picker),
                    RC_PHOTO_PICKER_PERM, FilePickerConst.PERMISSIONS_FILE_PICKER);
            pickDocClicked();
        }

    }

    //选择图片
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
                    .showFolderView(false)
                    .enableSelectAll(true)
                    .enableImagePicker(true)
                    .setCameraPlaceholder(R.drawable.custom_camera)
                    .withOrientation(Orientation.UNSPECIFIED)
                    .pickPhoto(this, CUSTOM_REQUEST_CODE);
        }
    }

    //选择文档
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

    //初始化RecyclerView
    private void initRecyclerView(){
        mRecyclerView = (RecyclerView)findViewById(R.id.main_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //第一次加载文件 默认根目录
    private void  initFileListView(String uid, String parent){
        OkUtil.postFileList(uid, parent, new JsonCallback<String>() {
            @Override
            public void onSuccess(Response<String> response) {

                JsonArray filesList = analysisJson(response);
                fileLists = getFilesList(filesList);
                fileAdapter = new FileAdapter(R.layout.file_list_main,fileLists);
                mRecyclerView.setAdapter(fileAdapter);
                fileAdapterClick();
                mEasyRefreshLayout.refreshComplete();
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

    //下拉刷新文件处理
    private void initEasyRefreshLayout(){
        //获取下拉刷新
        mEasyRefreshLayout=(EasyRefreshLayout)findViewById(R.id.easy_refresh_layout);
        mEasyRefreshLayout.setLoadMoreModel(LoadModel.NONE);
        mEasyRefreshLayout.autoRefresh();
        mEasyRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
            }
            @Override
            public void onRefreshing() {
            Log.e("MainActivity","onRefreshing");
            refreshList();
            }
        });
    }

    //获取返回的文件
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CUSTOM_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    photoPaths = new ArrayList<>();
                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                    intentActivity("uploadPhoto",photoPaths,uid,fid);
                }
                break;

            case FilePickerConst.REQUEST_CODE_DOC:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    docPaths = new ArrayList<>();
                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                    intentActivity("uploadDoc",docPaths,uid,fid);
                }
                break;
            default:
                break;
        }
    }

    public void intentActivity(String upLoadName,ArrayList arrayList,String uid,int fid){
        Intent intent = new Intent(this,UploadActivity.class);
        Bundle bundlePhoto= new Bundle();
        bundlePhoto.putSerializable(upLoadName,(Serializable)arrayList);
        intent.putExtra(upLoadName,bundlePhoto);
        intent.putExtra("uid",uid);
        intent.putExtra("fid",fid+"");
        startActivity(intent);
    }

    public void intentFileOperationActivity(ArrayList<FileList> FileListOperation ,String uid,int fid,String type){
        Intent intent = new Intent(MainActivity.this,FileOperationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("FileOpera",(Serializable)FileListOperation);
        intent.putExtra("FileOpera",bundle);
        intent.putExtra("uid",uid);
        intent.putExtra("fid",fid+"");
        intent.putExtra("type",type);
        startActivity(intent);
    }

    //文件更新
    public void refreshList(){
        popLayout.setVisibility(View.INVISIBLE);
        initFileListView(uid,String.valueOf(fid));
    }

    private void fileAdapterClick() {

        fileAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                //这里处理点击事件
                switch (((FileList) adapter.getItem(position)).filetype){
                    case 0:
                        fid = ((FileList) adapter.getItem(position)).fid;
                        fileSystem.add(fid);
                        refreshList();
                        break;
                    case 6:
                        photoPreview(((FileList) adapter.getItem(position)).name,((FileList) adapter.getItem(position)).filepath);
                        //加载图片
                        break;
                    default:
                        Intent intent = new Intent(MainActivity.this,WebActivity.class);
                        intent.putExtra("FilePath",((FileList) adapter.getItem(position)).filepath);
                        startActivity(intent);
                }

            }
        });
        fileAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
              //
                //  Toast.makeText(MainActivity.this, "onItemLongClick" + position, Toast.LENGTH_SHORT).show();
                fileListsDownload.add((FileList) adapter.getItem(position));
                delete();
                return true;
            }
        });
        fileAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                popLayout.setVisibility(View.VISIBLE);
                boolean isExistence =true;
                Log.e("1212","click"+position);
                if (fileListsDownload.size()==0){
                    Log.e("1212","first_add"+((FileList) adapter.getItem(position)).name);
                    fileListsDownload.add((FileList) adapter.getItem(position));
                }
                else {
                    for (int i =0;i< fileListsDownload.size();i++){
                        if ( fileListsDownload.get(i).name.equals(((FileList) adapter.getItem(position)).name)){
                            Log.e("1212","remove"+((FileList) adapter.getItem(position)).name);
                            fileListsDownload.remove(i);
                            if (fileListsDownload.size()==0)popLayout.setVisibility(View.INVISIBLE);
                            isExistence =true;
                            break;
                        }
                        else{
                            isExistence=false;
                        }
                    }
                    if (isExistence==false){
                        Log.e("1212","add"+((FileList) adapter.getItem(position)).name);
                        fileListsDownload.add((FileList) adapter.getItem(position));
                    }
                }
                Log.e("1212","fileListsDownloadSize="+fileListsDownload.size());
            }
        });
        fileAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, "onItemChildLongClick" +fileLists.get(position).filetype, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        smoothCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("123","4"+smoothCheckBox.isChecked());
            }
        });
    }

    @OnClick(R.id.cancel)
    public void cancel(){
        popLayout.setVisibility(View.INVISIBLE);
        fileListsDownload.clear();
        refreshList();
    }

    @OnClick(R.id.download)
    public void download(View view){
        for (FileList fileList :fileListsDownload){
            //这里只是演示，表示请求可以传参，怎么传都行，和okgo使用方法一样
            //这里第一个参数是tag，代表下载任务的唯一标识，传任意字符串都行，需要保证唯一,我这里用url作为了tag
            //这里只是演示，表示请求可以传参，怎么传都行，和okgo使用方法一样
            PostRequest<File> request = OkGo.<File>post(fileList.filepath);

            OkDownload.request(fileList.filepath, request)//
                    .priority(fileList.priority)//
                    .extra1(fileList)//
                    .save()//
                    .register(new LogDownloadListener())//
                    .start();
        }
        Toast.makeText(this,"已加入下载队列中",Toast.LENGTH_SHORT).show();
        popLayout.setVisibility(View.INVISIBLE);
        fileListsDownload.clear();
        refreshList();
    }

    @OnClick(R.id.copy)
    public void copy(){
        intentFileOperationActivity(fileListsDownload,uid,fid,"copy");
        fileListsDownload.clear();
        refreshList();
    }

    @OnClick(R.id.move)
    public void move(){
        intentFileOperationActivity(fileListsDownload,uid,fid,"move");
        fileListsDownload.clear();
        refreshList();
    }

    @OnClick(R.id.delete)
    public void delete(){
        final String json;
        for (FileList fileList :fileListsDownload){
            Map<String , String> map = new HashMap<String, String>();
            map.put("fid",String.valueOf(fileList.fid));
            file.add(map);
        }
        Gson gson = new Gson();
        json = gson.toJson(file);

        new AlertDialog.Builder(this)
                .setTitle("确认删除吗")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    refreshList();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        OkUtil.postFileChangeList(String.valueOf(fid), json, "delete", new JsonCallback<Object>() {
                            @Override
                            public void onSuccess(Response<Object> response) {
                                Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                                refreshList();
                                fileListsDownload.clear();
                            }
                        });

                    }
                }).create().show();
    }

    private void downLoadFileList() {
        //设置默认下载路径
        OkDownload.getInstance().setFolder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/");
        //设置同时瞎子啊个数
        OkDownload.getInstance().getThreadPool().setCorePoolSize(3);
        //从数据库中恢复数据
        List<Progress> progressList = DownloadManager.getInstance().getAll();
        OkDownload.restore(progressList);
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
                Toast.makeText(this,"权限被禁止，无法下载文件！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAllTaskEnd() {
        Toast.makeText(this,"所有下载任务已结束",Toast.LENGTH_SHORT).show();
    }

    public void photoPreview(String FileName,String FilePath){

       View view=(View)LayoutInflater.from(MainActivity.this).inflate(R.layout.photopreview,null);
       ImageView imageView = (ImageView)view.findViewById(R.id.photoView);
        Glide.with(MainActivity.this).load(FilePath).thumbnail( 0.2f ).into(imageView);
        AlertDialog.Builder buidler = new AlertDialog.Builder(MainActivity.this);
                buidler.setTitle(FileName)
                        .setView(view)
                        .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                         @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                        }
                       }).create().show();
    }

    public void addFolder(){
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("新建文件夹")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        refreshList();
                    }
                })
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e("MainActivity",editText.getText().toString());
                        Log.e("MainActivity",uid);
                        Log.e("MainActivity", String.valueOf(fid));
                        OkUtil.AddFolder(uid, String.valueOf(fid), editText.getText().toString(), new JsonCallback<Object>() {
                            @Override
                            public void onSuccess(Response<Object> response) {
                                refreshList();
                            }
                        });

                    }
                }).create().show();
    }

}
