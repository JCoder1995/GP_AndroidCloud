package com.example.jcoder.gp_androidcloud.adapter;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.enity.FileList;
import com.example.jcoder.gp_androidcloud.util.ToastUtils;
import com.example.jcoder.gp_androidcloud.util.Utils;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.orhanobut.logger.Logger;

/**
 * Created by JCoder on 2018/3/26.
 */

public class PullToRefreshFileListAdapter extends BaseQuickAdapter<FileList,BaseViewHolder>{

    public PullToRefreshFileListAdapter() {
        super( R.layout.file_list_show, null);
    }

    @Override
    protected void convert(final BaseViewHolder helper, FileList item) {
        //添加子监听
        helper.addOnClickListener(R.id.file_list_select);

        helper.addOnClickListener(R.id.file_list_delete);
        helper.addOnClickListener(R.id.file_list_collect);

        helper.getView(R.id.file_list_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);
                easySwipeMenuLayout.resetStatus();
            }
        });

        helper.getView(R.id.file_list_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EasySwipeMenuLayout easySwipeMenuLayout = helper.getView(R.id.es);
                easySwipeMenuLayout.resetStatus();
            }
        });

    }
}
