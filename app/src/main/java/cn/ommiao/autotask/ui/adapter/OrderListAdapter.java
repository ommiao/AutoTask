package cn.ommiao.autotask.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.entity.SectionOfOrder;
import cn.ommiao.base.entity.order.Group;
import cn.ommiao.base.entity.order.Order;

public class OrderListAdapter extends BaseSectionQuickAdapter<SectionOfOrder, BaseViewHolder> {

    public OrderListAdapter(int layoutResId, int sectionHeadResId, List<SectionOfOrder> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder baseViewHolder, SectionOfOrder sectionOfOrder) {
        Group group = sectionOfOrder.groupOfOrder;
        baseViewHolder.setText(R.id.tv_section_title, sectionOfOrder.header);
        baseViewHolder.setText(R.id.et_repeat_times, String.valueOf(group.repeatTimes));
        baseViewHolder.addOnClickListener(R.id.iv_add_order);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, SectionOfOrder sectionOfOrder) {
        Order order = sectionOfOrder.t;
        int pos = sectionOfOrder.getIndex(order) + 1;
        baseViewHolder.setText(R.id.tv_order_title, "指令" + pos);
        baseViewHolder.setText(R.id.tv_find_rule, order.findRule.getDescription());
        baseViewHolder.setText(R.id.tv_action, order.action.getDescription());
    }

}
