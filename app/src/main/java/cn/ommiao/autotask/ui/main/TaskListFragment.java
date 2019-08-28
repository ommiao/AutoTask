package cn.ommiao.autotask.ui.main;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FragmentTaskListBinding;
import cn.ommiao.autotask.task.Client;
import cn.ommiao.autotask.ui.adapter.TaskListAdapter;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.autotask.util.UiUtil;
import cn.ommiao.base.entity.order.Task;
import cn.ommiao.base.util.FileUtil;
import cn.ommiao.base.util.OrderUtil;

public class TaskListFragment extends BaseFragment<FragmentTaskListBinding, MainViewModel> implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

    private ArrayList<Task> tasks = new ArrayList<>();

    private TaskListAdapter adapter;

    private boolean loaded = false;

    private Client client;

    private ImageView icon;

    private boolean isServerRun = false;

    private boolean run = true;

    private MyHandler handler = new MyHandler(this);

    static class MyHandler extends Handler {

        private WeakReference<TaskListFragment> mFragment;

        private MyHandler(TaskListFragment fragment) {
            this.mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            TaskListFragment fragment = mFragment.get();
            if (fragment != null && msg.what == 1){
                if(!fragment.isServerRun){
                    fragment.isServerRun = true;
                    fragment.switchToSuccess();
                }
                removeMessages(2);
                sendEmptyMessageDelayed(2,2000);
            }
            if (fragment != null && msg.what == 2 && fragment.isServerRun){
                fragment.isServerRun = false;
                fragment.switchToFail();
            }
        }
    }

    @Override
    protected void init() {
        client = new Client(message -> {
            if(Client.ALIVE.equals(message)){
                handler.sendEmptyMessage(1);
            }
        });
        new HeartThread().start();
    }

    @SuppressLint("InflateParams")
    @Override
    protected void initViews() {
        mBinding.rvTask.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new TaskListAdapter(R.layout.item_task_list, tasks);
        View header = LayoutInflater.from(mContext).inflate(R.layout.header_task_list, null);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) header.findViewById(R.id.v_status_bar).getLayoutParams();
        icon = header.findViewById(R.id.iv_status);
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        View footer = LayoutInflater.from(mContext).inflate(R.layout.footer_task_list, null);
        adapter.addHeaderView(header);
        adapter.addFooterView(footer);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        mBinding.rvTask.setAdapter(adapter);
        mBinding.fabAdd.setOnClickListener(view -> {
            startFragmentTaskAdd();
        });
    }

    private void startFragmentTaskAdd() {
        TaskAddFragment fragment = new TaskAddFragment();
        assert getFragmentManager() != null;
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("FragmentTaskList")
                .commit();
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
        return R.layout.fragment_task_list;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        switch (view.getId()){
            case R.id.fl_start:
                FileUtil.writeTask(OrderUtil.readOrders(mContext));
                client.send(Client.RUN_TEST);
                break;
            case R.id.fl_edit:
                break;
            case R.id.fl_delete:
                tasks.remove(position);
                adapter.notifyItemRemoved(position + 1);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        Toast.makeText(mContext, tasks.get(position).taskName, Toast.LENGTH_SHORT).show();
    }

    private void switchToSuccess(){
        switchPicture(true);
    }

    private void switchToFail(){
        switchPicture(false);
    }

    private void switchPicture(final boolean success){
        Animation shrink = new ScaleAnimation(1f, 0f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrink.setDuration(250);
        shrink.setInterpolator(new AccelerateDecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(success){
                    icon.setImageResource(R.drawable.icon_success);
                } else {
                    icon.setImageResource(R.drawable.icon_fail);
                }
                UiUtil.scaleAnimation(icon, 0f, 1f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        icon.startAnimation(shrink);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        run = false;
        client.close();
    }

    class HeartThread extends Thread{
        @Override
        public void run() {
            while (run){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.send(Client.HEART_BEAT);
            }
        }
    }
}
