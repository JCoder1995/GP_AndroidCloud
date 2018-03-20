package com.example.jcoder.gp_androidcloud.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.enity.FileList;

import java.util.List;

/**
 * Created by JCoder on 2018/3/19.
 */

public class FileListAdapter extends BaseQuickAdapter<FileList,BaseViewHolder>{

    public FileListAdapter() {
        super(R.layout.file_list_show);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileList item) {
    }

}
