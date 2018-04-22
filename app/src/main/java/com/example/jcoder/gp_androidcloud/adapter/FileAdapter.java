package com.example.jcoder.gp_androidcloud.adapter;


import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.bean.FileList;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JCoder on 2018/4/16.
 */

public class FileAdapter extends BaseQuickAdapter<FileList,BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemLongClickListener {


    public FileAdapter(int layoutResId, @Nullable ArrayList<FileList> data) {
        super(R.layout.file_list_main, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileList item) {
        helper.addOnClickListener(R.id.file_iv);
        switch (item.filetype){
            case 0:
                helper.setImageResource(R.id.file_iv,R.drawable.icon_file_unknown);
                helper.setText(R.id.file_type_tv,"Folder");
                break;
            case 1:
                helper.setImageResource(R.id.file_iv,R.drawable.icon_file_doc);
                helper.setText(R.id.file_type_tv,"DOC");
                break;
            case 2:
                helper.setImageResource(R.id.file_iv,R.drawable.icon_file_unknown);
                helper.setText(R.id.file_type_tv,"ZIP");
                break;
            case 3:
                helper.setImageResource(R.id.file_iv,R.drawable.icon_file_ppt);
                helper.setText(R.id.file_type_tv,"PPT");
                break;

            case 4:
                helper.setImageResource(R.id.file_iv,R.drawable.icon_file_pdf);
                helper.setText(R.id.file_type_tv,"PDF");
                break;

            case 5:
                helper.setImageResource(R.id.file_iv,R.drawable.icon_file_xls);
                helper.setText(R.id.file_type_tv,"XLS");
                break;

            case 6:
                helper.setImageResource(R.id.file_iv,R.drawable.icon_file_unknown);
                helper.setText(R.id.file_type_tv,"Photo");
                break;

            case 7:
                helper.setImageResource(R.id.file_iv,R.drawable.icon_file_unknown);
                helper.setText(R.id.file_type_tv,"Music");
                break;

            case 8:
                helper.setImageResource(R.id.file_iv,R.drawable.icon_file_unknown);
                helper.setText(R.id.file_type_tv,"Video");
                break;
        }
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(item.s_ctime);
        helper.setText(R.id.file_name_tv,item.name);
        helper.setText(R.id.file_time,createdate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
