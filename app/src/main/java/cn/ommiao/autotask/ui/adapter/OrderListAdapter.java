package cn.ommiao.autotask.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.ommiao.autotask.R;
import cn.ommiao.base.entity.order.Order;

public class OrderListAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {

    public OrderListAdapter(int layoutResId, @Nullable List<Order> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Order order) {
        int pos = baseViewHolder.getLayoutPosition();
        baseViewHolder.setText(R.id.tv_order_title, "指令" + pos);
        baseViewHolder.setText(R.id.tv_find_rule, order.findRule.getDescription());
        baseViewHolder.setText(R.id.tv_action, order.action.getDescription());
    }

}
