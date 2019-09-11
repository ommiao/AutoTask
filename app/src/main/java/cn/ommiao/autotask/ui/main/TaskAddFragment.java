package cn.ommiao.autotask.ui.main;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FragmentTaskAddBinding;
import cn.ommiao.autotask.ui.adapter.GroupPagerAdapter;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.base.entity.order.Group;
import cn.ommiao.base.entity.order.Task;

public class TaskAddFragment extends BaseFragment<FragmentTaskAddBinding, MainViewModel> {

    private Task task;

    private ArrayList<GroupFragment> fragments = new ArrayList<>();

    public TaskAddFragment(){

    }

    public TaskAddFragment(Task task){
        this.task = task;
    }

    private Runnable statusBarSetter = () -> {
        ImmersionBar.with(this).statusBarView(mBinding.vStatusBar).keyboardEnable(true).statusBarDarkFont(false).init();
    };

    @Override
    protected void init() {
        if(task.groups == null){
            task.groups = new ArrayList<>();
        }
        for (Group group : task.groups){
            GroupFragment fragment = new GroupFragment(group);
            fragments.add(fragment);
        }
    }

    @Override
    protected void initViews() {
        mBinding.getRoot().postDelayed(statusBarSetter, getResources().getInteger(R.integer.task_add_animation_time));
        mBinding.ivBack.setOnClickListener(view -> {
            onBackPressed();
        });
        mBinding.vpGroup.setAdapter(new GroupPagerAdapter(getChildFragmentManager(), fragments));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBinding.getRoot().removeCallbacks(statusBarSetter);
    }

    @Override
    protected Class<MainViewModel> classOfViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_add;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(mContext, R.anim.task_add_enter);
        } else {
            return AnimationUtils.loadAnimation(mContext, R.anim.task_add_exit);
        }
    }

    @Override
    public boolean listenBackPressed() {
        return true;
    }

    @Override
    public void onBackPressed() {
        ImmersionBar.with(this).statusBarDarkFont(true).init();
        popBackStack();
    }
}
