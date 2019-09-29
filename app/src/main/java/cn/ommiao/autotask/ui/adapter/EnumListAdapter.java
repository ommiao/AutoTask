package cn.ommiao.autotask.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashSet;
import java.util.List;

import cn.ommiao.autotask.R;
import cn.ommiao.base.entity.order.BaseEnum;
import cn.ommiao.base.entity.order.EnumGroup;

public class EnumListAdapter<E extends Enum> extends BaseQuickAdapter<BaseEnum<E>, BaseViewHolder> implements BaseQuickAdapter.OnItemChildClickListener {

    public static final String PAYLOAD_SELECTED = "payload_selected";

    private HashSet<BaseEnum<E>> selectedEnums;

    public EnumListAdapter(int layoutResId, @Nullable List<BaseEnum<E>> data, HashSet<BaseEnum<E>> selectedEnums) {
        super(layoutResId, data);
        this.selectedEnums = selectedEnums;
        setOnItemChildClickListener(this);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, BaseEnum<E> baseEnum) {
        baseViewHolder.setText(R.id.tv_enum_description, baseEnum.getDescription());
        baseViewHolder.addOnClickListener(R.id.tv_enum_description);
        ImageView ivSelected = baseViewHolder.getView(R.id.iv_selected);
        if(selectedEnums.contains(baseEnum)){
            ivSelected.setVisibility(View.VISIBLE);
        } else {
            ivSelected.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads);
            return;
        }
        BaseEnum<E> baseEnum = mData.get(position);
        String payload = (String) payloads.get(0);
        if(payload.contains(PAYLOAD_SELECTED)){
            ImageView ivSelected = holder.getView(R.id.iv_selected);
            if(selectedEnums.contains(baseEnum)){
                ivSelected.setVisibility(View.VISIBLE);
            } else {
                ivSelected.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        BaseEnum<E> baseEnum = getItem(i);
        if(!selectedEnums.contains(baseEnum)){
            EnumGroup group = baseEnum.getEnumGroup();
            for (BaseEnum<E> eBaseEnum : mData) {
                if(eBaseEnum.getEnumGroup() == group){
                    selectedEnums.remove(eBaseEnum);
                    notifyItemChanged(getPosition(eBaseEnum), PAYLOAD_SELECTED);
                }
            }
            selectedEnums.add(baseEnum);
            notifyItemChanged(i, PAYLOAD_SELECTED);
        } else {
            selectedEnums.remove(baseEnum);
            notifyItemChanged(i, PAYLOAD_SELECTED);
        }
    }

    private int getPosition(BaseEnum<E> baseEnum){
        for (int i = 0; i < mData.size(); i++) {
            if(mData.get(i).equals(baseEnum)){
                return i;
            }
        }
        return -1;
    }
}
