package cn.ommiao.autotask.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.entity.SectionOfOrder;
import cn.ommiao.base.entity.order.FindRule;
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
        if(sectionOfOrder.uiInfoView == null){
            sectionOfOrder.uiInfoView = order.findRule.getFindRuleHelper().getUiInfoView(baseViewHolder.itemView.getContext());
        }
        LinearLayout llUiInfo = baseViewHolder.getView(R.id.ll_uiinfo);
        if(llUiInfo.getChildCount() == 2){
            llUiInfo.removeViewAt(1);
        }
        llUiInfo.addView(sectionOfOrder.uiInfoView);

        LinearLayout llNotFoundEvent = baseViewHolder.getView(R.id.ll_not_found_event);
        if(order.findRule != FindRule.DEVICE){
            llNotFoundEvent.setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tv_not_found_event, order.notFoundEvent.getDescription());
        } else {
            llNotFoundEvent.setVisibility(View.GONE);
        }

        baseViewHolder.setText(R.id.et_repeat_times, String.valueOf(order.repeatTimes));

        baseViewHolder.setText(R.id.et_delay, String.valueOf(order.delay));

    }

}
