package com.example.jcoder.gp_androidcloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by JCoder on 2018/3/19.
 */

public class FileListAdapter extends BaseQuickAdapter<FileListAdapter,BaseViewHolder>{

    public FileListAdapter(int layoutResId, @Nullable List<FileListAdapter> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileListAdapter item) {

    }
}
