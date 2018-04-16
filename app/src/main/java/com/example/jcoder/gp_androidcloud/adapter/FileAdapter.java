package com.example.jcoder.gp_androidcloud.adapter;


import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
        super(R.layout.file_list_main, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileList item) {
        helper.addOnClickListener(R.id.file_iv);
        switch (item.getFileType()){
            case 0:
                helper.setImageResource(R.id.file_iv,R.drawable.icon_file_unknown);
                helper.setText(R.id.file_type_tv,"文件夹");
                break;
            case 1:
                Glide.with(mContext).load(item.getFilePath()).into((ImageView) helper.getView(R.id.file_iv));
                helper.setText(R.id.file_type_tv,"");
        }
        helper.setText(R.id.file_name_tv,item.getFileName());
        helper.setText(R.id.file_time,item.getFileUpdateTime());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
