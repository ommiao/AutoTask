package cn.ommiao.autotask.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.entity.TaskFile;

public class TaskFileAdapter extends BaseQuickAdapter<TaskFile, BaseViewHolder> {

    public static final String PAYLOAD_SELECTED = "selected";

    public TaskFileAdapter(int layoutResId, @Nullable List<TaskFile> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, TaskFile taskFile) {
        TextView tvFileName = holder.getView(R.id.tv_file_name);
        tvFileName.setText(taskFile.getPath());
        tvFileName.setSelected(true);
        ImageView ivSelected = holder.getView(R.id.iv_selected);
        ivSelected.setVisibility(taskFile.isSelected() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(payloads.size() == 0){
            super.onBindViewHolder(holder, position, payloads);
        } else {
            String payload = (String) payloads.get(0);
            TaskFile taskFile = mData.get(position);
            if(payload.contains(PAYLOAD_SELECTED)){
                ImageView ivSelected = holder.getView(R.id.iv_selected);
                ivSelected.setVisibility(taskFile.isSelected() ? View.VISIBLE : View.INVISIBLE);
            }
        }
    }
}
