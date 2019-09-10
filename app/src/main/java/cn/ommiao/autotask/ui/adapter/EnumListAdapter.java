package cn.ommiao.autotask.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.ommiao.autotask.R;
import cn.ommiao.base.entity.order.BaseEnum;

public class EnumListAdapter<E extends Enum> extends BaseQuickAdapter<BaseEnum<E>, BaseViewHolder> {

    public EnumListAdapter(int layoutResId, @Nullable List<BaseEnum<E>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, BaseEnum<E> baseEnum) {
        baseViewHolder.setText(R.id.tv_enum_description, baseEnum.getDescription());
        baseViewHolder.addOnClickListener(R.id.tv_enum_description);
    }
}
