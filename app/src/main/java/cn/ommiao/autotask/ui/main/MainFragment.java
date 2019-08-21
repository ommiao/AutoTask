package cn.ommiao.autotask.ui.main;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.MainFragmentBinding;
import cn.ommiao.autotask.ui.adapter.TaskListAdapter;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.base.entity.order.Task;

public class MainFragment extends BaseFragment<MainFragmentBinding, MainViewModel> {

    private ArrayList<Task> tasks = new ArrayList<>();
    private TaskListAdapter adapter;

    @Override
    protected void initViews() {
        mBinding.rv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new TaskListAdapter(R.layout.item_task_list, tasks);
        mBinding.rv.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mViewModel.getTasks().observe(this, tasks -> {
            this.tasks.addAll(tasks);
            adapter.notifyDataSetChanged();
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
}
