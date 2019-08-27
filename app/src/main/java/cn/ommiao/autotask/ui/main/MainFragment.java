package cn.ommiao.autotask.ui.main;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.MainFragmentBinding;
import cn.ommiao.autotask.ui.adapter.TaskListAdapter;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.base.entity.order.Task;

import static cn.ommiao.autotask.ui.main.MainViewModel.dir;

public class MainFragment extends BaseFragment<MainFragmentBinding, MainViewModel> implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {


    private ArrayList<Task> tasks = new ArrayList<>();

    private TaskListAdapter adapter;

    private boolean loaded = false;

    @SuppressLint("InflateParams")
    @Override
    protected void initViews() {
        mBinding.rvTask.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new TaskListAdapter(R.layout.item_task_list, tasks);
        View header = LayoutInflater.from(mContext).inflate(R.layout.header_task_list, null);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) header.findViewById(R.id.v_status_bar).getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        View footer = LayoutInflater.from(mContext).inflate(R.layout.footer_task_list, null);
        adapter.addHeaderView(header);
        adapter.addFooterView(footer);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        mBinding.rvTask.setAdapter(adapter);
        mBinding.fabAdd.setOnClickListener(view -> {
            Task taskB = new Task();
            taskB.taskId = "B";
            taskB.taskName = "微信";
            taskB.coverPath = dir + "2.jpg";
            taskB.taskDescription = "This is description of Task B.";
            tasks.add(taskB);
            adapter.notifyItemInserted(tasks.size() + 1);
            mBinding.rvTask.postDelayed(() -> {
                mBinding.rvTask.smoothScrollToPosition(tasks.size() + 1);
            }, 300);
        });
    }


    @Override
    protected void initData() {
        mViewModel.getTasks().observe(this, tasks -> {
            if(!loaded){
                this.tasks.addAll(tasks);
                adapter.notifyDataSetChanged();
                loaded = true;
            }
        });
    }

    @Override
    protected Class<MainViewModel> classOfViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        Toast.makeText(mContext, tasks.get(position).taskName, Toast.LENGTH_SHORT).show();
    }
}
