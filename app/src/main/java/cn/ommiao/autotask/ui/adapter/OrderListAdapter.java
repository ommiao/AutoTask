package cn.ommiao.autotask.ui.adapter;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.ommiao.autotask.R;
import cn.ommiao.base.entity.order.FindRule;
import cn.ommiao.base.entity.order.Order;

public class OrderListAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {

    public OrderListAdapter(int layoutResId, @Nullable List<Order> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Order order) {
        int pos = baseViewHolder.getAdapterPosition() - getHeaderLayoutCount();
        int no = pos + 1;
        baseViewHolder.setText(R.id.tv_order_title, "指令" + no);
        baseViewHolder.setText(R.id.tv_find_rule, order.findRule.getDescription());
        baseViewHolder.setText(R.id.tv_action, order.action.getDescription());
        if(order.uiInfoView == null){
            order.uiInfoView = order.findRule.getFindRuleHelper().getUiInfoView(baseViewHolder.itemView.getContext());
        }
        LinearLayout llUiInfo = baseViewHolder.getView(R.id.ll_uiinfo);
        if(llUiInfo.getChildCount() == 2){
            llUiInfo.removeViewAt(1);
        }
        llUiInfo.addView(order.uiInfoView);

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
