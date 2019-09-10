package cn.ommiao.autotask.ui.common;

import android.app.Activity;
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
import androidx.fragment.app.FragmentManager;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FragmentCustomDialogBinding;
import cn.ommiao.autotask.ui.base.BaseActivity;


public class CustomDialogFragment extends DialogFragment {

    private FragmentCustomDialogBinding mBinding;
    private BaseActivity mActivity;

    private String title = "提示", content = "", leftBtn = "确定", rightBtn = "取消";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_dialog, container, false);
        mBinding.tvTitle.setText(title);
        mBinding.tipsContent.setText(content);
        mBinding.btnLeft.setText(leftBtn);
        mBinding.btnRight.setText(rightBtn);
        if(onLeftClickListener == null){
            mBinding.btnLeft.setVisibility(View.GONE);
            mBinding.btnRight.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        if(onLeftClickListener != null){
            mBinding.btnLeft.setOnClickListener(view -> {
                dismiss();
                onLeftClickListener.onLeftClick();
            });
        } else {
            mBinding.btnLeft.setOnClickListener(view -> dismiss());
        }
        if(onRightClickListener != null){
            mBinding.btnRight.setOnClickListener(view -> {
                dismiss();
                onRightClickListener.onRightClick();
            });
        } else {
            mBinding.btnRight.setOnClickListener(view -> dismiss());
        }
        return mBinding.getRoot();
    }

    public CustomDialogFragment title(String title){
        this.title = title;
        return this;
    }

    public CustomDialogFragment content(String content){
        this.content = content;
        return this;
    }

    public CustomDialogFragment leftBtn(String leftBtn){
        this.leftBtn = leftBtn;
        return this;
    }

    public CustomDialogFragment rightBtn(String rightBtn){
        this.rightBtn = rightBtn;
        return this;
    }

    public CustomDialogFragment onLeftClick(OnLeftClickListener leftClickListener){
        onLeftClickListener = leftClickListener;
        return this;
    }

    public CustomDialogFragment onRightClick(OnRightClickListener rightClickListener){
        onRightClickListener = rightClickListener;
        return this;
    }

    public void show(FragmentManager fragmentManager){
        show(fragmentManager, CustomDialogFragment.class.getSimpleName());
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
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        attributes.width = screenWidth - getResources().getDimensionPixelSize(R.dimen.dialog_margin) * 2;
        window.setAttributes(attributes);
    }

    private OnLeftClickListener onLeftClickListener;
    private OnRightClickListener onRightClickListener;

    public interface OnLeftClickListener{
        void onLeftClick();
    }

    public interface OnRightClickListener{
        void onRightClick();
    }

}
