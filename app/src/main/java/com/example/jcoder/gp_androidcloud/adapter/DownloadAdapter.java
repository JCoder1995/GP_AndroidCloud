
package com.example.jcoder.gp_androidcloud.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jcoder.gp_androidcloud.R;
import com.example.jcoder.gp_androidcloud.bean.FileList;
import com.example.jcoder.gp_androidcloud.listener.LogDownloadListener;
import com.example.jcoder.gp_androidcloud.model.FileTranSport;
import com.example.jcoder.gp_androidcloud.ui.NumberProgressBar;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;

import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    public static final int TYPE_ALL = 0;
    public static final int TYPE_FINISH = 1;
    public static final int TYPE_ING = 2;

    private List<DownloadTask> values;
    private NumberFormat numberFormat;
    private LayoutInflater inflater;
    private Context context;
    private int type;

    public DownloadAdapter(Context context) {
        this.context = context;
        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateData(int type) {
        //这里是将数据库的数据恢复
        this.type = type;
        if (type == TYPE_ALL) values = OkDownload.restore(DownloadManager.getInstance().getAll());
        if (type == TYPE_FINISH) values = OkDownload.restore(DownloadManager.getInstance().getFinished());
        if (type == TYPE_ING) values = OkDownload.restore(DownloadManager.getInstance().getDownloading());
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_download_manager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DownloadTask task = values.get(position);
        String tag = createTag(task);
        task.register(new ListDownloadListener(tag, holder))//
                .register(new LogDownloadListener());
        holder.setTag(tag);
        holder.setTask(task);
        holder.bind();
        holder.refresh(task.progress);
    }

    public void unRegister() {
        Map<String, DownloadTask> taskMap = OkDownload.getInstance().getTaskMap();
        for (DownloadTask task : taskMap.values()) {
            task.unRegister(createTag(task));
        }
    }

    private String createTag(DownloadTask task) {
        return type + "_" + task.progress.tag;
    }

    @Override
    public int getItemCount() {
        return values == null ? 0 : values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.priority)
        TextView priority;
        @BindView(R.id.downloadSize)
        TextView downloadSize;
        @BindView(R.id.tvProgress)
        TextView tvProgress;
        @BindView(R.id.netSpeed)
        TextView netSpeed;
        @BindView(R.id.pbProgress)
        NumberProgressBar pbProgress;
        @BindView(R.id.start)
        Button download;
        private DownloadTask task;
        private String tag;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setTask(DownloadTask task) {
            this.task = task;
        }

        public void bind() {
            Progress progress = task.progress;
            FileList fileList = (FileList) progress.extra1;
            if (fileList != null) {
                //这里判断下载文件的图片
             //   Glide.with(context).load(apk.iconUrl).error(R.mipmap.ic_launcher).into(icon);
            //    Glide.with(context).load(null).into(icon);
                name.setText(fileList.name);
                priority.setText(String.format("优先级：%s", progress.priority));
            } else {
                name.setText(progress.fileName);
            }
        }

        public void refresh(Progress progress) {
            String currentSize = Formatter.formatFileSize(context, progress.currentSize);
            String totalSize = Formatter.formatFileSize(context, progress.totalSize);
            downloadSize.setText(currentSize + "/" + totalSize);
            priority.setText(String.format("优先级：%s", progress.priority));
            switch (progress.status) {
                case Progress.NONE:
                    netSpeed.setText("停止");
                    download.setText("下载");
                    break;
                case Progress.PAUSE:
                    netSpeed.setText("暂停中");
                    download.setText("继续");
                    break;
                case Progress.ERROR:
                    netSpeed.setText("下载出错");
                    download.setText("出错");
                    break;
                case Progress.WAITING:
                    netSpeed.setText("等待中");
                    download.setText("等待");
                    break;
                case Progress.FINISH:
                    netSpeed.setText("下载完成");
                    download.setText("完成");
                    break;
                case Progress.LOADING:
                    String speed = Formatter.formatFileSize(context, progress.speed);
                    netSpeed.setText(String.format("%s/s", speed));
                    download.setText("暂停");
                    break;
            }
            tvProgress.setText(numberFormat.format(progress.fraction));
            pbProgress.setMax(10000);
            pbProgress.setProgress((int) (progress.fraction * 10000));
        }

        @OnClick(R.id.start)
        public void start() {
            Progress progress = task.progress;
            switch (progress.status) {
                case Progress.PAUSE:
                case Progress.NONE:
                case Progress.ERROR:
                    task.start();
                    break;
                case Progress.LOADING:
                    task.pause();
                    break;
                case Progress.FINISH:
                    break;
            }
            refresh(progress);
        }

        @OnClick(R.id.remove)
        public void remove() {
            task.remove(true);
            updateData(type);
        }

        @OnClick(R.id.restart)
        public void restart() {
            task.restart();
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }
    }

    private class ListDownloadListener extends DownloadListener {

        private ViewHolder holder;

        ListDownloadListener(Object tag, ViewHolder holder) {
            super(tag);
            this.holder = holder;
        }

        @Override
        public void onStart(Progress progress) {
        }

        @Override
        public void onProgress(Progress progress) {
            if (tag == holder.getTag()) {
                holder.refresh(progress);
            }
        }

        @Override
        public void onError(Progress progress) {
            Throwable throwable = progress.exception;
            if (throwable != null) throwable.printStackTrace();
        }

        @Override
        public void onFinish(File file, Progress progress) {
            Toast.makeText(context, "下载完成:" + progress.filePath, Toast.LENGTH_SHORT).show();
            updateData(type);
        }

        @Override
        public void onRemove(Progress progress) {
        }
    }
}
