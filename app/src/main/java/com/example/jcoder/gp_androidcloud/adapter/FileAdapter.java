package com.example.jcoder.gp_androidcloud.adapter;


import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.enity.FileList;

import java.util.List;

/**
 * Created by JCoder on 2018/4/16.
 */

public class FileAdapter extends BaseQuickAdapter<FileList,BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemLongClickListener {


    public FileAdapter(int layoutResId, @Nullable List<FileList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileList item) {
        helper.setText(R.id.text, item.getFileName());

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
