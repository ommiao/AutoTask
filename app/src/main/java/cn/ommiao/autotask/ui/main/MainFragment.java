package cn.ommiao.autotask.ui.main;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.MainFragmentBinding;
import cn.ommiao.autotask.ui.adapter.TaskListAdapter;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.base.entity.order.Task;

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
        adapter.openLoadAnimation();
        adapter.isFirstOnly(false);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        mBinding.rvTask.setAdapter(adapter);
//        mBinding.rvTask.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                int pos = parent.getChildLayoutPosition(view);
//                if(pos != 0 && pos != tasks.size() && pos != tasks.size() + 1){
//                    outRect.bottom = getResources().getDimensionPixelSize(R.dimen.stack_height);
//                }
//            }
//        });
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
        switch (view.getId()){
            case R.id.fab_start:
                Toast.makeText(mContext, tasks.get(position).taskName + "start", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_delete:
                Toast.makeText(mContext, tasks.get(position).taskName + "delete", Toast.LENGTH_SHORT).show();
                tasks.remove(position);
                adapter.notifyItemRemoved(position + 1);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        Toast.makeText(mContext, tasks.get(position).taskName, Toast.LENGTH_SHORT).show();
    }
}
