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
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FragmentEnumSelectorBinding;
import cn.ommiao.autotask.ui.adapter.EnumListAdapter;
import cn.ommiao.autotask.util.ToastUtil;
import cn.ommiao.base.entity.order.BaseEnum;

public class EnumSelectorFragment<E extends Enum> extends DialogFragment {

    private Class<E> enumClass;
    private FragmentEnumSelectorBinding mBinding;
    private ArrayList<BaseEnum<E>> enums = new ArrayList<>();
    private HashSet<E> selectedEnums = new HashSet<>();

    public EnumSelectorFragment(){

    }

    public EnumSelectorFragment(Class<E> enumClass, E selectedEnum){
        this.enumClass = enumClass;
        this.selectedEnums.add(selectedEnum);
    }

    public EnumSelectorFragment(Class<E> enumClass, HashSet<E> selectedEnums){
        this.enumClass = enumClass;
        this.selectedEnums = selectedEnums;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_enum_selector, container, false);
        initViews();
        return mBinding.getRoot();
    }

    @SuppressWarnings("unchecked")
    private void initViews() {
        mBinding.ivClose.setOnClickListener(view -> dismiss());
        Enum[] enumConstants = enumClass.getEnumConstants();
        assert enumConstants != null;
        BaseEnum<E> first = (BaseEnum<E>) enumConstants[0];
        String title = "请选择" + first.getTitle();
        for (Enum enumConstant : enumConstants) {
            BaseEnum<E> eBaseEnum = (BaseEnum<E>) enumConstant;
            enums.add(eBaseEnum);
        }
        mBinding.tvConfigTitle.setText(title);
        EnumListAdapter adapter = new EnumListAdapter(R.layout.item_enum_list, enums, selectedEnums);
        mBinding.rvEnum.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvEnum.setAdapter(adapter);
        mBinding.tvConfirm.setOnClickListener(view -> {
            if(selectedEnums.size() == 0){
                ToastUtil.shortToast("请至少选择一项" + first.getTitle());
            } else {
                onEnumSelectorListener.onEnumSelected(selectedEnums);
                dismiss();
            }
        });
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

    private OnEnumSelectorListener<E> onEnumSelectorListener;

    public void setOnEnumSelectorListener(OnEnumSelectorListener<E> onEnumSelectorListener) {
        this.onEnumSelectorListener = onEnumSelectorListener;
    }

    public interface OnEnumSelectorListener<E>{
        void onEnumSelected(Set<E> baseEnumSet);
    }
}
