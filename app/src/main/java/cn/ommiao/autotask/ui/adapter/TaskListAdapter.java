package cn.ommiao.autotask.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.ommiao.autotask.R;
import cn.ommiao.base.entity.order.Task;
import cn.ommiao.base.util.StringUtil;

public class TaskListAdapter extends BaseQuickAdapter<Task, BaseViewHolder> {

    public TaskListAdapter(int layoutResId, @Nullable List<Task> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Task task) {
        ImageView iv = baseViewHolder.getView(R.id.iv);
        ImageView ivMask = baseViewHolder.getView(R.id.iv_mask);
        if(StringUtil.isEmptyOrSpace(task.coverPath)){
            Glide.with(iv).load(R.drawable.yhy).into(iv);
            ivMask.setVisibility(View.INVISIBLE);
            baseViewHolder.setTextColor(R.id.tv_task_name, Color.WHITE);
            baseViewHolder.setTextColor(R.id.tv_task_desc, Color.WHITE);
        } else {
            ivMask.setVisibility(View.VISIBLE);
            Glide.with(iv).load(task.coverPath).into(iv);
            if(!(task.taskCoverColor == 0 && task.taskNameColor == 0 && task.taskDescriptionColor == 0)){
                ivMask.setColorFilter(task.taskCoverColor);
                baseViewHolder.setTextColor(R.id.tv_task_name, task.taskNameColor);
                baseViewHolder.setTextColor(R.id.tv_task_desc, task.taskDescriptionColor);
            } else {
                ivMask.setColorFilter(Color.WHITE);
                baseViewHolder.setTextColor(R.id.tv_task_name, Color.GRAY);
                baseViewHolder.setTextColor(R.id.tv_task_desc, Color.GRAY);
            }
        }
        baseViewHolder.setText(R.id.tv_task_name, task.taskName);
        baseViewHolder.setText(R.id.tv_task_desc, task.taskDescription);
        baseViewHolder.addOnClickListener(R.id.fl_start, R.id.fl_edit, R.id.fl_delete);
    }
}
