package cn.ommiao.autotask.ui.main;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FragmentTaskSaveDialogBinding;
import cn.ommiao.autotask.util.ToastUtil;
import cn.ommiao.base.util.StringUtil;

public class TaskSaveDialogFragment extends DialogFragment {

    private FragmentTaskSaveDialogBinding mBinding;
    private String taskName, taskDescription;

    public TaskSaveDialogFragment(){

    }

    public TaskSaveDialogFragment(String taskName, String taskDescription){
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_save_dialog, container, false);
        initViews();
        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.etTaskName.setText(taskName);
        mBinding.etTaskDescription.setText(taskDescription);
        mBinding.ivClose.setOnClickListener(view -> {
            if(onTaskSaveListener != null){
                onTaskSaveListener.onTaskSaveCancel();
            }
            dismiss();
        });
        mBinding.tvConfirm.setOnClickListener(view -> {
            if(isDataChecked()){
                if(onTaskSaveListener != null){
                    onTaskSaveListener.onTaskSaveConfirm(taskName, taskDescription);
                }
                dismiss();
            }
        });
    }

    private boolean isDataChecked() {
        taskName = mBinding.etTaskName.getText().toString().trim();
        taskDescription = mBinding.etTaskDescription.getText().toString().trim();
        if(StringUtil.isEmptyOrSpace(taskName)){
            ToastUtil.shortToast("请输入任务名称");
            return false;
        }
        if(StringUtil.isEmptyOrSpace(taskDescription)){
            ToastUtil.shortToast("请输入任务描述");
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        setCancelable(false);
        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setWindowAnimations(R.style.dialog_enter_exit);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        attributes.height = screenWidth - getResources().getDimensionPixelSize(R.dimen.dialog_margin) * 2;
        attributes.width = screenWidth - getResources().getDimensionPixelSize(R.dimen.dialog_margin) * 2;
        window.setAttributes(attributes);
    }

    private OnTaskSaveListener onTaskSaveListener;

    public void setOnTaskSaveListener(OnTaskSaveListener onTaskSaveListener) {
        this.onTaskSaveListener = onTaskSaveListener;
    }

    public interface OnTaskSaveListener {
        void onTaskSaveConfirm(String taskName, String taskDescription);
        void onTaskSaveCancel();
    }
}
