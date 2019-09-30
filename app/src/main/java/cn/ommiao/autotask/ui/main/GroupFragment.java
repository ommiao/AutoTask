package cn.ommiao.autotask.ui.main;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FooterOrderListBinding;
import cn.ommiao.autotask.databinding.FragmentGroupBinding;
import cn.ommiao.autotask.databinding.HeaderOrderListBinding;
import cn.ommiao.autotask.ui.adapter.OrderListAdapter;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.autotask.ui.common.CustomDialogFragment;
import cn.ommiao.autotask.util.ToastUtil;
import cn.ommiao.base.entity.order.Action;
import cn.ommiao.base.entity.order.FindRule;
import cn.ommiao.base.entity.order.Group;
import cn.ommiao.base.entity.order.NotFoundEvent;
import cn.ommiao.base.entity.order.Order;
import cn.ommiao.base.entity.order.UiInfo;
import cn.ommiao.base.findrulehelper.BaseFindRuleHelper;
import cn.ommiao.base.util.StringUtil;

import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_ACTION;
import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_FIND_RULE;
import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_FIND_RULE_PARENT;
import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_NOT_FOUND_EVENT;
import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_TITLE;
import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_UIINFO_P_SHOW;

public class GroupFragment extends BaseFragment<FragmentGroupBinding, MainViewModel> implements BaseQuickAdapter.OnItemChildClickListener {

    private Group group;

    private OrderListAdapter adapter;
    private HeaderOrderListBinding headerOrderListBinding;

    public GroupFragment(){

    }

    public GroupFragment(Group group){
        this.group = group;
    }

    public void saveData(){
        String rTimes = headerOrderListBinding.etRepeatTimes.getText().toString().trim();
        if(StringUtil.isEmpty(rTimes)){
            rTimes = "1";
        }
        group.repeatTimes = Integer.parseInt(rTimes);
    }

    public boolean saveAllData(){
        saveData();
        for (int i = 0; i < group.orders.size(); i++) {
            Order order = group.orders.get(i);
            if(order.action.isGlobalAction()){
                order.uiInfo.findRules.clear();
                order.uiInfo.parent = null;
            }
            for (FindRule findRule : order.uiInfo.findRules.keySet()) {
                View view = order.uiInfo.views.get(findRule);
                if(view == null){
                    continue;
                }
                String validMsg = findRule.getFindRuleHelper().isDataValid(view);
                if(!BaseFindRuleHelper.DATA_VALID.equals(validMsg)){
                    ToastUtil.shortToast(group.groupName + "中指令" + (i + 1) + "控件信息录入错误：" + validMsg);
                    return false;
                }
                findRule.getFindRuleHelper().save(view, order.uiInfo);
            }
            if(order.uiInfo.parent != null){
                for (FindRule findRule : order.uiInfo.parent.findRules.keySet()) {
                    View view = order.uiInfo.parent.views.get(findRule);
                    if(view == null){
                        continue;
                    }
                    String validMsg = findRule.getFindRuleHelper().isDataValid(view);
                    if(!BaseFindRuleHelper.DATA_VALID.equals(validMsg)){
                        ToastUtil.shortToast(group.groupName + "中指令" + (i + 1) + "父控件信息录入错误：" + validMsg);
                        return false;
                    }
                    findRule.getFindRuleHelper().save(view, order.uiInfo.parent);
                }
            }
        }
        return true;
    }

    @Override
    protected void init() {
        if(group.orders == null){
            group.orders = new ArrayList<>();
        }
    }

    @Override
    protected void initViews() {
        @SuppressLint("InflateParams")
        View header = LayoutInflater.from(mContext).inflate(R.layout.header_order_list, null);
        headerOrderListBinding = DataBindingUtil.bind(header);
        assert headerOrderListBinding != null;
        headerOrderListBinding.tvGroupTitle.setText(group.groupName);
        headerOrderListBinding.etRepeatTimes.setText(String.valueOf(group.repeatTimes));
        headerOrderListBinding.ivRemoveGroup.setOnClickListener(view -> {
            if(onGroupRemoveListener != null){
                onGroupRemoveListener.onGroupRemove(group);
            }
        });
        mBinding.rvOrders.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new OrderListAdapter(R.layout.item_order_list, group.orders);
        adapter.addHeaderView(header);
        View footer = LayoutInflater.from(mContext).inflate(R.layout.footer_order_list, null);
        FooterOrderListBinding footerOrderListBinding = DataBindingUtil.bind(footer);
        assert footerOrderListBinding != null;
        footerOrderListBinding.ivAddOrder.setOnClickListener(view -> {
            group.addOrder(getNewOrder());
            adapter.notifyItemInserted(group.orders.size());
        });
        adapter.addFooterView(footer);
        adapter.setOnItemChildClickListener(this);
        mBinding.rvOrders.setAdapter(adapter);
    }

    @Override
    protected Class<MainViewModel> classOfViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    public boolean listenBackPressed() {
        return true;
    }

    @Override
    public void onBackPressed() {
        assert getParentFragment() != null;
        ((TaskAddFragment)getParentFragment()).onBackPressed();
    }

    private Order getNewOrder(){
        Order order = new Order();
        order.repeatTimes = 1;
        order.action = Action.CLICK;
        order.uiInfo = new UiInfo();
        order.uiInfo.findRules.put(FindRule.ID, "");
        order.notFoundEvent = NotFoundEvent.ERROR;
        return order;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        switch (view.getId()){
            case R.id.iv_remove_order:
                group.orders.remove(i);
                adapter.notifyItemRemoved(i + adapter.getHeaderLayoutCount());
                adapter.notifyItemRangeChanged(i + adapter.getHeaderLayoutCount(), group.orders.size() - i, PAYLOAD_TITLE);
                break;
            case R.id.tv_action:
                showActionSelector(i);
                break;
            case R.id.tv_find_rule:
                if(!group.orders.get(i).action.isGlobalAction()){
                    showFindRuleSelector(i, false);
                }
                break;
            case R.id.tv_find_rule_p:
                showFindRuleSelector(i, true);
                break;
            case R.id.tv_not_found_event:
                showNotFoundEventSelector(i);
                break;
            case R.id.tv_uiinfo_parent:
                Order order = group.orders.get(i);
                if(order.uiInfo.parent == null){
                    order.uiInfo.parent = new UiInfo();
                    order.uiInfo.parent.findRules.put(FindRule.ID, "");
                } else {
                    order.uiInfo.parent = null;
                }
                adapter.notifyItemChanged(i + adapter.getHeaderLayoutCount(), PAYLOAD_UIINFO_P_SHOW);
                break;
        }
    }

    private void showActionSelector(int index){
        Order order = group.orders.get(index);
        EnumSelectorFragment<Action> fragment = new EnumSelectorFragment<>(Action.class, order.action);
        fragment.setOnEnumSelectorListener(actions -> {
            order.action = actions.iterator().next();
            adapter.notifyItemChanged(index + adapter.getHeaderLayoutCount(), PAYLOAD_ACTION);
        });
        fragment.show(getChildFragmentManager(), EnumSelectorFragment.class.getSimpleName());
    }

    private void showFindRuleSelector(int index, boolean isParent){
        Order order = group.orders.get(index);
        HashSet<FindRule> findRulesNow;
        if(isParent){
            findRulesNow = new HashSet<>(order.uiInfo.parent.findRules.keySet());
        } else {
            findRulesNow = new HashSet<>(order.uiInfo.findRules.keySet());
        }
        EnumSelectorFragment<FindRule> fragment = new EnumSelectorFragment<>(FindRule.class, findRulesNow);
        fragment.setOnEnumSelectorListener(findRules -> {
            HashMap<FindRule, String> findRulesMap = isParent ? order.uiInfo.parent.findRules : order.uiInfo.findRules;
            findRulesMap.clear();
            for (FindRule findRule : findRules) {
                findRulesMap.put(findRule, "");
            }
            adapter.notifyItemChanged(index + adapter.getHeaderLayoutCount(), isParent ? PAYLOAD_FIND_RULE_PARENT : PAYLOAD_FIND_RULE);
        });
        fragment.show(getChildFragmentManager(), EnumSelectorFragment.class.getSimpleName());
    }

    private void showNotFoundEventSelector(int index){
        Order order = group.orders.get(index);
        EnumSelectorFragment<NotFoundEvent> fragment = new EnumSelectorFragment<>(NotFoundEvent.class, order.notFoundEvent);
        fragment.setOnEnumSelectorListener(notFoundEvents -> {
            order.notFoundEvent = notFoundEvents.iterator().next();
            adapter.notifyItemChanged(index + adapter.getHeaderLayoutCount(), PAYLOAD_NOT_FOUND_EVENT);
        });
        fragment.show(getChildFragmentManager(), EnumSelectorFragment.class.getSimpleName());
    }

    private void showNotAllowedFindRuleDialog(){
        new CustomDialogFragment()
                .title("提示")
                .content("该执行动作必须查找控件，请重新选择。")
                .rightBtn("确定")
                .onRightClick(() -> {

                })
                .show(getChildFragmentManager());
    }

    private OnGroupRemoveListener onGroupRemoveListener;

    public void setOnGroupRemoveListener(OnGroupRemoveListener onGroupRemoveListener) {
        this.onGroupRemoveListener = onGroupRemoveListener;
    }

    public interface OnGroupRemoveListener{
        void onGroupRemove(Group group);
    }
}
