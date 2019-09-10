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

    public static final String PAYLOAD_TITLE = "payload_title";
    public static final String PAYLOAD_FIND_RULE = "payload_find_rule";
    public static final String PAYLOAD_ACTION = "payload_action";
    public static final String PAYLOAD_NOT_FOUND_EVENT = "payload_not_found_event";

    public OrderListAdapter(int layoutResId, @Nullable List<Order> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Order order) {
        int pos = holder.getAdapterPosition() - getHeaderLayoutCount();
        int no = pos + 1;
        holder.setText(R.id.tv_order_title, "指令" + no);
        holder.setText(R.id.tv_find_rule, order.findRule.getDescription());
        holder.setText(R.id.tv_action, order.action.getDescription());
        if(order.uiInfoView == null){
            order.uiInfoView = order.findRule.getFindRuleHelper().getUiInfoView(holder.itemView.getContext());
        }
        LinearLayout llUiInfo = holder.getView(R.id.ll_uiinfo);
        if(llUiInfo.getChildCount() == 2){
            llUiInfo.removeViewAt(1);
        }
        if(order.uiInfoView.getParent() != null && order.uiInfoParent != null){
            order.uiInfoParent.removeView(order.uiInfoView);
        }
        llUiInfo.addView(order.uiInfoView);
        order.uiInfoParent = llUiInfo;

        LinearLayout llNotFoundEvent = holder.getView(R.id.ll_not_found_event);
        if(order.findRule != FindRule.DEVICE){
            llNotFoundEvent.setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_not_found_event, order.notFoundEvent.getDescription());
        } else {
            llNotFoundEvent.setVisibility(View.GONE);
        }

        holder.setText(R.id.et_repeat_times, String.valueOf(order.repeatTimes));

        holder.setText(R.id.et_delay, String.valueOf(order.delay));

        holder.addOnClickListener(R.id.iv_remove_order, R.id.tv_action, R.id.tv_find_rule, R.id.tv_not_found_event);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads);
            return;
        }
        int dataPos = position - getHeaderLayoutCount();
        Order order = mData.get(dataPos);
        String payload = (String) payloads.get(0);
        if(payload.contains(PAYLOAD_TITLE)){
            int no = dataPos + 1;
            holder.setText(R.id.tv_order_title, "指令" + no);
        }
        if(payload.contains(PAYLOAD_ACTION)){
            holder.setText(R.id.tv_action, order.action.getDescription());
            if(order.action.isGlobalAction() && order.findRule != FindRule.DEVICE){
                order.findRule = FindRule.DEVICE;
                payload += "," + PAYLOAD_FIND_RULE;
            } else if(!order.action.isGlobalAction() && order.findRule == FindRule.DEVICE){
                order.findRule = FindRule.ID;
                payload += "," + PAYLOAD_FIND_RULE;
            }
        }
        if(payload.contains(PAYLOAD_FIND_RULE)){
            holder.setText(R.id.tv_find_rule, order.findRule.getDescription());
            order.uiInfoView = order.findRule.getFindRuleHelper().getUiInfoView(holder.itemView.getContext());
            LinearLayout llUiInfo = holder.getView(R.id.ll_uiinfo);
            if(llUiInfo.getChildCount() == 2){
                llUiInfo.removeViewAt(1);
            }
            llUiInfo.addView(order.uiInfoView);
            LinearLayout llNotFoundEvent = holder.getView(R.id.ll_not_found_event);
            if(order.findRule != FindRule.DEVICE){
                llNotFoundEvent.setVisibility(View.VISIBLE);
                holder.setText(R.id.tv_not_found_event, order.notFoundEvent.getDescription());
            } else {
                llNotFoundEvent.setVisibility(View.GONE);
            }
        }
        if(payload.contains(PAYLOAD_NOT_FOUND_EVENT)){
            holder.setText(R.id.tv_not_found_event, order.notFoundEvent.getDescription());
        }
    }

}
