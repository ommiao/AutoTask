package cn.ommiao.autotask.ui.adapter;

import android.text.Editable;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Set;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.ui.listener.SimpleTextWatcher;
import cn.ommiao.base.entity.order.FindRule;
import cn.ommiao.base.entity.order.Order;

public class OrderListAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {

    public static final String PAYLOAD_TITLE = "payload_title";
    public static final String PAYLOAD_FIND_RULE = "payload_find_rule";
    public static final String PAYLOAD_FIND_RULE_PARENT = "payload_find_rule_parent";
    public static final String PAYLOAD_ACTION = "payload_action";
    public static final String PAYLOAD_NOT_FOUND_EVENT = "payload_not_found_event";
    public static final String PAYLOAD_UIINFO_P_SHOW = "payload_uiinfo_p_show";

    public OrderListAdapter(int layoutResId, @Nullable List<Order> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Order order) {
        int pos = holder.getAdapterPosition() - getHeaderLayoutCount();
        int no = pos + 1;
        holder.setText(R.id.tv_order_title, "指令" + no);
        holder.setText(R.id.tv_action, order.action.getDescription());
        holder.setText(R.id.tv_not_found_event, order.notFoundEvent.getDescription());
        holder.setText(R.id.et_repeat_times, String.valueOf(order.repeatTimes));
        holder.setText(R.id.et_delay, String.valueOf(order.delay));
        EditText etRepeatTimes = holder.getView(R.id.et_repeat_times);
        etRepeatTimes.addTextChangedListener(new SimpleTextWatcher(){
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    order.repeatTimes = Integer.parseInt(editable.toString());
                } catch (Exception e){
                    e.printStackTrace();
                    order.repeatTimes = 1;
                }
            }
        });
        EditText etDelay = holder.getView(R.id.et_delay);
        etDelay.addTextChangedListener(new SimpleTextWatcher(){
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    order.delay = Long.parseLong(editable.toString());
                } catch (Exception e){
                    e.printStackTrace();
                    order.delay = 0;
                }
            }
        });
        holder.setText(R.id.tv_find_rule, getFindRuleDescription(order.uiInfo.findRules.keySet()));
        if(!order.action.isGlobalAction()){
            holder.setGone(R.id.ll_not_found_event, true);
            holder.setGone(R.id.tv_uiinfo_parent, true);
            holder.setGone(R.id.ll_uiinfo_area, true);
            LinearLayout llUiinfo = holder.getView(R.id.ll_uiinfo);
            llUiinfo.removeAllViews();
            for (FindRule findRule : order.uiInfo.findRules.keySet()) {
                if(order.uiInfo.views.get(findRule) == null){
                    order.uiInfo.views.put(findRule, findRule.getFindRuleHelper().getFindRuleView(holder.itemView.getContext()));
                }
                findRule.getFindRuleHelper().setData(order.uiInfo.views.get(findRule), order.uiInfo.findRules.get(findRule));
                if(order.uiInfo.views.get(findRule).getParent() != null && order.uiInfo.viewGroup != null){
                    order.uiInfo.viewGroup.removeView(order.uiInfo.views.get(findRule));
                }
                llUiinfo.addView(order.uiInfo.views.get(findRule));
            }
            order.uiInfo.viewGroup = llUiinfo;
            if(order.uiInfo.parent != null){
                holder.setText(R.id.tv_uiinfo_parent, "取消录入父控件信息");
                holder.setText(R.id.tv_find_rule_p, getFindRuleDescription(order.uiInfo.parent.findRules.keySet()));
                holder.setGone(R.id.ll_uiinfo_p_area, true);

                LinearLayout llUiinfoP = holder.getView(R.id.ll_uiinfo_p);
                llUiinfoP.removeAllViews();
                for (FindRule findRule : order.uiInfo.parent.findRules.keySet()) {
                    if(order.uiInfo.parent.views.get(findRule) == null){
                        order.uiInfo.parent.views.put(findRule, findRule.getFindRuleHelper().getFindRuleView(holder.itemView.getContext()));
                    }
                    findRule.getFindRuleHelper().setData(order.uiInfo.parent.views.get(findRule), order.uiInfo.parent.findRules.get(findRule));
                    if(order.uiInfo.parent.views.get(findRule).getParent() != null && order.uiInfo.parent.viewGroup != null){
                        order.uiInfo.parent.viewGroup.removeView(order.uiInfo.parent.views.get(findRule));
                    }
                    llUiinfoP.addView(order.uiInfo.parent.views.get(findRule));
                }
                order.uiInfo.parent.viewGroup = llUiinfoP;

            } else {
                holder.setText(R.id.tv_uiinfo_parent, "录入父控件信息");
                holder.setGone(R.id.ll_uiinfo_p_area, false);
            }
        } else {
            holder.setGone(R.id.ll_not_found_event, false);
            holder.setGone(R.id.tv_uiinfo_parent, false);
            holder.setGone(R.id.ll_uiinfo_area, false);
            holder.setGone(R.id.ll_uiinfo_p_area, false);
        }

        holder.addOnClickListener(R.id.iv_remove_order, R.id.tv_action, R.id.tv_find_rule, R.id.tv_find_rule_p, R.id.tv_not_found_event, R.id.tv_uiinfo_parent);
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
            if(!order.action.isGlobalAction()){
                holder.setGone(R.id.ll_not_found_event, true);
                holder.setGone(R.id.tv_uiinfo_parent, true);
                holder.setGone(R.id.ll_uiinfo_area, true);
                if(order.uiInfo.parent != null){
                    holder.setGone(R.id.ll_uiinfo_p_area, true);
                } else {
                    holder.setGone(R.id.ll_uiinfo_p_area, false);
                }
            } else {
                holder.setGone(R.id.ll_not_found_event, false);
                holder.setGone(R.id.tv_uiinfo_parent, false);
                holder.setGone(R.id.ll_uiinfo_area, false);
                holder.setGone(R.id.ll_uiinfo_p_area, false);
            }
        }
        if(payload.contains(PAYLOAD_FIND_RULE)){
            holder.setText(R.id.tv_find_rule, getFindRuleDescription(order.uiInfo.findRules.keySet()));
            LinearLayout llUiinfo = holder.getView(R.id.ll_uiinfo);
            llUiinfo.removeAllViews();
            for (FindRule findRule : order.uiInfo.findRules.keySet()) {
                if(order.uiInfo.views.get(findRule) == null){
                    order.uiInfo.views.put(findRule, findRule.getFindRuleHelper().getFindRuleView(holder.itemView.getContext()));
                }
                if(order.uiInfo.views.get(findRule).getParent() != null && order.uiInfo.viewGroup != null){
                    order.uiInfo.viewGroup.removeView(order.uiInfo.views.get(findRule));
                }
                llUiinfo.addView(order.uiInfo.views.get(findRule));
            }
            order.uiInfo.viewGroup = llUiinfo;
        }
        if(payload.contains(PAYLOAD_UIINFO_P_SHOW)){
            if(order.uiInfo.parent != null){
                holder.setText(R.id.tv_uiinfo_parent, "取消录入父控件信息");
                holder.setText(R.id.tv_find_rule_p, getFindRuleDescription(order.uiInfo.parent.findRules.keySet()));
                holder.setGone(R.id.ll_uiinfo_p_area, true);
                payload += ", " + PAYLOAD_FIND_RULE_PARENT;
            } else {
                holder.setGone(R.id.ll_uiinfo_p_area, false);
                holder.setText(R.id.tv_uiinfo_parent, "录入父控件信息");
            }
        }
        if(payload.contains(PAYLOAD_FIND_RULE_PARENT)){
            holder.setText(R.id.tv_find_rule_p, getFindRuleDescription(order.uiInfo.parent.findRules.keySet()));
            LinearLayout llUiinfoP = holder.getView(R.id.ll_uiinfo_p);
            llUiinfoP.removeAllViews();
            for (FindRule findRule : order.uiInfo.parent.findRules.keySet()) {
                if(order.uiInfo.parent.views.get(findRule) == null){
                    order.uiInfo.parent.views.put(findRule, findRule.getFindRuleHelper().getFindRuleView(holder.itemView.getContext()));
                }
                if(order.uiInfo.parent.views.get(findRule).getParent() != null && order.uiInfo.parent.viewGroup != null){
                    order.uiInfo.parent.viewGroup.removeView(order.uiInfo.parent.views.get(findRule));
                }
                llUiinfoP.addView(order.uiInfo.parent.views.get(findRule));
            }
            order.uiInfo.parent.viewGroup = llUiinfoP;
        }
        if(payload.contains(PAYLOAD_NOT_FOUND_EVENT)){
            holder.setText(R.id.tv_not_found_event, order.notFoundEvent.getDescription());
        }
    }

    private String getFindRuleDescription(Set<FindRule> findRules){
        StringBuilder builder = new StringBuilder();
        for (FindRule findRule : findRules) {
            builder.append(", ").append(findRule.getDescription());
        }
        return builder.toString().length() > 0 ? builder.toString().substring(1) : "";
    }

}
