package cn.ommiao.autotask.ui.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FragmentTaskImportBinding;
import cn.ommiao.autotask.entity.TaskFile;
import cn.ommiao.autotask.ui.adapter.TaskFileAdapter;
import cn.ommiao.autotask.ui.base.BaseActivity;
import cn.ommiao.autotask.util.Constant;
import cn.ommiao.autotask.util.ToastUtil;

import static cn.ommiao.autotask.util.Constant.AUTO_TASK_DIR;
import static cn.ommiao.autotask.util.Constant.TASK_FILE_SUFFIX;


public class TaskImportFragment extends DialogFragment implements BaseQuickAdapter.OnItemClickListener {

    private static final String INVALID_PATH = "Invalid Path";

    private FragmentTaskImportBinding mBinding;
    private BaseActivity mActivity;

    private TaskFileAdapter adapter;
    private ArrayList<TaskFile> list = new ArrayList<>();

    private String taskPathSelected = INVALID_PATH;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_import, container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.rvFiles.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new TaskFileAdapter(R.layout.item_task_import, list);
        adapter.setOnItemClickListener(this);
        @SuppressLint("InflateParams")
        View empty = LayoutInflater.from(mActivity).inflate(R.layout.empty_task_import, null);
        adapter.setEmptyView(empty);
        mBinding.rvFiles.setAdapter(adapter);
        mBinding.btnRight.setOnClickListener(view -> {
            dismiss();
            if(onTaskImportListener != null){
                onTaskImportListener.onCanceled();
            }
        });
        mBinding.btnLeft.setOnClickListener(view -> {
            if(onTaskImportListener != null){
                if(INVALID_PATH.equals(taskPathSelected)){
                    ToastUtil.shortToast("请选择任务文件");
                } else {
                    dismiss();
                    onTaskImportListener.onTaskSelected(Constant.AUTO_TASK_DIR + "/" + taskPathSelected);
                }
            } else {
                dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        File autoTaskDir = new File(AUTO_TASK_DIR);
        if(autoTaskDir.exists()){
            String[] filePaths = autoTaskDir.list();
            if(filePaths != null){
                for (String filePath : filePaths) {
                    if(filePath.endsWith(TASK_FILE_SUFFIX)){
                        TaskFile taskFile = new TaskFile();
                        taskFile.setPath(filePath);
                        taskFile.setSelected(false);
                        list.add(taskFile);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void show(FragmentManager fragmentManager){
        show(fragmentManager, TaskImportFragment.class.getSimpleName());
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @Override
    public void onStart() {
        super.onStart();
        setCancelable(false);
        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        assert window != null;
        window.setWindowAnimations(R.style.dialog_enter_exit);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        attributes.width = screenWidth - getResources().getDimensionPixelSize(R.dimen.dialog_margin) * 2;
        attributes.height = screenHeight / 3 * 2;
        window.setAttributes(attributes);
    }

    private OnTaskImportListener onTaskImportListener;

    public void setOnTaskImportListener(OnTaskImportListener onTaskImportListener) {
        this.onTaskImportListener = onTaskImportListener;
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        TaskFile taskFile = list.get(i);
        if(!taskPathSelected.equals(taskFile.getPath())){
            int oldIndex = getIndexSelected(taskPathSelected);
            if(oldIndex != -1){
                list.get(oldIndex).setSelected(false);
                adapter.notifyItemChanged(oldIndex, TaskFileAdapter.PAYLOAD_SELECTED);
            }
            taskPathSelected = taskFile.getPath();
            taskFile.setSelected(true);
            adapter.notifyItemChanged(i, TaskFileAdapter.PAYLOAD_SELECTED);
        }
    }

    private int getIndexSelected(String path){
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if(path.equals(list.get(i).getPath())){
                index = i;
            }
        }
        return index;
    }

    public interface OnTaskImportListener{
        void onTaskSelected(String taskPath);
        void onCanceled();
    }


}
