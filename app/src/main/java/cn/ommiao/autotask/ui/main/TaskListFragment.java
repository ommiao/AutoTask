package cn.ommiao.autotask.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.UUID;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FragmentTaskListBinding;
import cn.ommiao.autotask.entity.TaskData;
import cn.ommiao.autotask.task.Client;
import cn.ommiao.autotask.ui.adapter.TaskListAdapter;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.autotask.ui.common.CustomDialogFragment;
import cn.ommiao.autotask.util.AppDatabase;
import cn.ommiao.autotask.util.AppExecutors;
import cn.ommiao.autotask.util.ToastUtil;
import cn.ommiao.autotask.util.UiUtil;
import cn.ommiao.base.entity.order.Task;
import cn.ommiao.base.util.FileUtil;
import cn.ommiao.base.util.StringUtil;

import static cn.ommiao.autotask.util.Constant.AUTO_TASK_DIR;
import static cn.ommiao.base.util.Constant.URI_EXECUTE_RESULT;

public class TaskListFragment extends BaseFragment<FragmentTaskListBinding, MainViewModel> implements BaseQuickAdapter.OnItemChildClickListener, TaskImportFragment.OnTaskImportListener {

    private static final int MSG_CLIENT_CONNECTED = 1;

    private static final int MSG_CLIENT_DISCONNECTED = 2;

    private static final int MSG_EXECUTE_FINISHED = 3;

    private ArrayList<Task> tasks = new ArrayList<>();

    private TaskListAdapter adapter;

    private boolean loaded = false;

    private Client client;

    private ImageView icon;

    private boolean isServerRun = false;

    private boolean run = true;

    private MyHandler handler = new MyHandler(this);

    private TaskResultContentObserver taskResultContentObserver;

    private String lastExecuteResultEndTime;

    @Override
    public void onTaskSelected(String taskPath) {
        File file = new File(taskPath);
        if(file.exists()){
            String fileString = FileUtil.readTxtFromFile(taskPath);
            Task task = Task.fromJson(fileString, Task.class);
            if(task == null || task.groups == null || task.groups.size() == 0){
                new CustomDialogFragment().content("任务文件无效，请检查。")
                        .rightBtn("确定")
                        .show(getFragmentManager());
            } else {
                task.taskId = UUID.randomUUID().toString();
                tasks.add(task);
                adapter.notifyItemInserted(tasks.size() - 1 + adapter.getHeaderLayoutCount());
                TaskData taskData = new TaskData();
                taskData.setTaskId(task.taskId);
                taskData.setTaskName(task.taskName);
                taskData.setTaskDescription(task.taskDescription);
                if(!StringUtil.isEmptyOrSpace(task.taskVersion)){
                    taskData.setTaskVersion(task.taskVersion);
                }
                taskData.setTaskString(task.toJson());
                AppExecutors.getDiskIO().execute(() -> AppDatabase.getTaskDatabase().taskDao().insertTaskData(taskData));
            }
        }
    }

    @Override
    public void onCanceled() {

    }

    static class MyHandler extends Handler {

        private WeakReference<TaskListFragment> mFragment;

        private MyHandler(TaskListFragment fragment) {
            this.mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            TaskListFragment fragment = mFragment.get();
            if (fragment != null && msg.what == MSG_CLIENT_CONNECTED){
                if(!fragment.isServerRun){
                    fragment.isServerRun = true;
                    fragment.switchToSuccess();
                }
                removeMessages(MSG_CLIENT_DISCONNECTED);
                sendEmptyMessageDelayed(MSG_CLIENT_DISCONNECTED,2000);
            }
            if (fragment != null && msg.what == MSG_CLIENT_DISCONNECTED && fragment.isServerRun){
                fragment.isServerRun = false;
                fragment.switchToFail();
            }
            if(fragment != null && msg.what == MSG_EXECUTE_FINISHED){
                fragment.showResult();
            }
        }
    }

    @Override
    protected void init() {
        client = new Client(message -> {
            if(Client.ALIVE.equals(message)){
                handler.sendEmptyMessage(MSG_CLIENT_CONNECTED);
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
        header.findViewById(R.id.tv_title).setOnLongClickListener(view -> stopClient());
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        View footer = LayoutInflater.from(mContext).inflate(R.layout.footer_task_list, null);
        adapter.addHeaderView(header);
        adapter.addFooterView(footer);
        adapter.setOnItemChildClickListener(this);
        mBinding.rvTask.setAdapter(adapter);
        mBinding.fabAdd.setOnClickListener(view -> {
            showTaskImportFragment();
        });
    }

    private void showTaskImportFragment() {
        if(haveStoragePermissions()){
            TaskImportFragment taskImportFragment = new TaskImportFragment();
            taskImportFragment.setOnTaskImportListener(this);
            taskImportFragment.show(getFragmentManager());
        } else {
            showNoStoragePermissions();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!haveStoragePermissions()){
            new CustomDialogFragment().content("为了导入并存取任务数据，需要请求读写存储权限，本应用仅读写根目录/AutoTask文件夹。")
                    .rightBtn("确定")
                    .onRightClick(() -> {
                        AndPermission.with(mContext).runtime().permission(Permission.Group.STORAGE).onGranted(permissions -> {
                            if(permissions.size() == 2){
                                makeDir();
                            }
                        }).onDenied(permissions -> {
                            showNoStoragePermissions();
                        }).start();
                    }).show(getFragmentManager());
        } else {
            makeDir();
        }
        taskResultContentObserver = new TaskResultContentObserver(handler);
        mContext.getContentResolver().registerContentObserver(Uri.parse(URI_EXECUTE_RESULT), true, taskResultContentObserver);
    }

    private void showResult(){
        Logger.d("showResult");
        AppDatabase.getTaskDatabase().taskDao().getNewExecuteResult().observe(this, executeResultData -> {
            if(executeResultData != null && !executeResultData.getEndTime().equals(lastExecuteResultEndTime)){
                lastExecuteResultEndTime = executeResultData.getEndTime();
                String content = "任务[" + executeResultData.getTaskName() + "] 执行" + (executeResultData.isSuccess() ? "成功" : "失败") + ", 时间是" + executeResultData.getEndTime();
                ToastUtil.shortToast(content);
                Logger.d(content);
                Logger.d(executeResultData.toJson());
            }
        });
    }

    private void showNoStoragePermissions(){
        new CustomDialogFragment().content("未获取到读写存储权限，无法读写任务数据。")
                .rightBtn("确定")
                .onRightClick(() -> {
                    mContext.finish();
                }).show(getFragmentManager());
    }

    private boolean haveStoragePermissions(){
        return mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && mContext.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void makeDir(){
        File autoTaskDir = new File(AUTO_TASK_DIR);
        if(!autoTaskDir.exists()){
            autoTaskDir.mkdir();
        }
    }

    private boolean stopClient() {
        client.send(Client.STOP);
        return true;
    }

    @Override
    protected void initData() {
        mViewModel.getTasks().observe(mContext, tasks -> {
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
                if(isServerRun){
                    FileUtil.writeTask(tasks.get(position).toJson());
                    client.send(Client.RUN_TEST);
                } else {
                    new CustomDialogFragment()
                            .title("提示")
                            .content("Server is not running.")
                            .rightBtn("确定")
                            .show(getChildFragmentManager());
                }
                break;
            case R.id.fl_edit:

                break;
            case R.id.fl_delete:
                String taskId = tasks.get(position).taskId;
                tasks.remove(position);
                adapter.notifyItemRemoved(position + 1);
                AppExecutors.getDiskIO().execute(() -> {
                    AppDatabase.getTaskDatabase().taskDao().deleteTask(taskId);
                });
                break;
        }
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
        mContext.getContentResolver().unregisterContentObserver(taskResultContentObserver);
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

    public class TaskResultContentObserver extends ContentObserver{

        MyHandler myHandler;

        private TaskResultContentObserver(MyHandler handler) {
            super(handler);
            this.myHandler = handler;
        }

        @Override
        public void onChange(boolean selfChange) {
            myHandler.sendEmptyMessage(MSG_EXECUTE_FINISHED);
            Logger.d("TaskResultContentObserver onChange(boolean selfChange)");
        }

    }
}
