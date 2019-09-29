package cn.ommiao.autotask.ui.main;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Locale;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FragmentTaskAddBinding;
import cn.ommiao.autotask.ui.adapter.GroupPagerAdapter;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.autotask.ui.common.CustomDialogFragment;
import cn.ommiao.base.entity.order.Group;
import cn.ommiao.base.entity.order.Task;

public class TaskAddFragment extends BaseFragment<FragmentTaskAddBinding, MainViewModel> implements GroupFragment.OnGroupRemoveListener, ViewPager.OnPageChangeListener {

    private Task task;

    private ArrayList<GroupFragment> fragments = new ArrayList<>();
    private GroupPagerAdapter adapter;

    private boolean saveFlag = true;

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
            fragment.setOnGroupRemoveListener(this);
            fragments.add(fragment);
        }
    }

    @Override
    protected void initViews() {
        mBinding.getRoot().postDelayed(statusBarSetter, getResources().getInteger(R.integer.task_add_animation_time));
        mBinding.ivBack.setOnClickListener(view -> {
            onBackPressed();
        });
        adapter = new GroupPagerAdapter(getChildFragmentManager(), fragments);
        mBinding.vpGroup.setAdapter(adapter);
        mBinding.ivAddGroup.setOnClickListener(view -> addNewGroup());
        mBinding.ivSaveTask.setOnClickListener(view -> saveTask());
    }

    private void addNewGroup() {
        if(task.groups.size() >= 15){
            new CustomDialogFragment()
                    .title("提示")
                    .content("指令组达到上限，请勿继续添加。")
                    .rightBtn("确定")
                    .onRightClick(() -> {

                    })
                    .show(getChildFragmentManager());
            return;
        }
        Group group = new Group();
        group.groupName = String.format(Locale.CHINA, "指令组%d", task.groups.size() + 1);
        group.repeatTimes = 1;
        group.orders = new ArrayList<>();
        task.groups.add(group);
        fragments.get(mBinding.vpGroup.getCurrentItem()).saveData();
        GroupFragment fragment = new GroupFragment(group);
        fragment.setOnGroupRemoveListener(this);
        fragments.add(fragment);
        adapter.notifyDataSetChanged();
        mBinding.vpGroup.setCurrentItem(fragments.size() - 1);
        mBinding.vpGroup.addOnPageChangeListener(this);
    }

    private void saveTask(){
        if(isPreChecked()){
            TaskSaveDialogFragment saveDialogFragment = new TaskSaveDialogFragment();
            saveDialogFragment.setOnTaskSaveListener(new TaskSaveDialogFragment.OnTaskSaveListener() {
                @Override
                public void onTaskSaveConfirm(String taskName, String taskDescription) {
                    task.taskName = taskName;
                    task.taskDescription = taskDescription;
                    Logger.d(task.toJson());
                    mViewModel.addNewTask(task);
                    ImmersionBar.with(TaskAddFragment.this).statusBarDarkFont(true).init();
                    popBackStack();
                }

                @Override
                public void onTaskSaveCancel() {

                }
            });
            saveDialogFragment.show(getChildFragmentManager(), TaskSaveDialogFragment.class.getSimpleName());
        }
    }

    private boolean isPreChecked() {
        for (int i = 0; i < task.groups.size(); i++) {
            Group group = task.groups.get(i);
            if(group.orders == null || group.orders.size() == 0){
                String msg = group.groupName + "不包含任何指令，请检查。";
                new CustomDialogFragment()
                        .title("提示")
                        .content(msg)
                        .rightBtn("确定")
                        .onRightClick(() -> {

                        })
                        .show(getChildFragmentManager());
                return false;
            } else {
                if(!fragments.get(i).saveAllData()){
                    return false;
                }
            }
        }
        return true;
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

    @Override
    public void onGroupRemove(Group group) {
        if(fragments.size() <= 1){
            new CustomDialogFragment()
                    .title("提示")
                    .content("当前仅剩一组命令，不可删除。")
                    .rightBtn("确定")
                    .onRightClick(() -> {

                    })
                    .show(getChildFragmentManager());
            return;
        }
        int index = getIndexByGroup(group);
        task.groups.remove(index);
        fragments.remove(index);
        adapter.notifyDataSetChanged();
    }

    private int getIndexByGroup(Group group){
        int index = -1;
        boolean changeFlag = false;
        for (int i = 0; i < task.groups.size(); i++) {
            if(task.groups.get(i) == group){
                index = i;
                changeFlag = true;
                continue;
            }
            if(changeFlag){
                Group changeGroup = task.groups.get(i);
                changeGroup.groupName = String.format(Locale.CHINA, "指令组%d", i);
            }
        }
        return index;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(saveFlag){
            Logger.d("onPageScrolled -> " + "save data " + position);
            for (GroupFragment fragment : fragments) {
                fragment.saveData();
            }
            saveFlag = false;
        }
    }

    @Override
    public void onPageSelected(int position) {
        Logger.d("onPageSelected -> " + position);
        saveFlag = true;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
