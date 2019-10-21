package cn.ommiao.autotask.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.ommiao.autotask.R;
import cn.ommiao.base.entity.order.Task;

public class TaskListAdapter extends BaseQuickAdapter<Task, BaseViewHolder> {

    public TaskListAdapter(int layoutResId, @Nullable List<Task> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Task task) {
        baseViewHolder.setText(R.id.tv_task_name, task.taskName);
        baseViewHolder.setText(R.id.tv_task_desc, task.taskDescription);
        baseViewHolder.addOnClickListener(R.id.fl_start, R.id.fl_edit, R.id.fl_delete);
    }
}
