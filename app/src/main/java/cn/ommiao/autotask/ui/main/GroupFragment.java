package cn.ommiao.autotask.ui.main;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashSet;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FooterOrderListBinding;
import cn.ommiao.autotask.databinding.FragmentGroupBinding;
import cn.ommiao.autotask.databinding.HeaderOrderListBinding;
import cn.ommiao.autotask.ui.adapter.OrderListAdapter;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.autotask.ui.common.CustomDialogFragment;
import cn.ommiao.base.entity.order.Action;
import cn.ommiao.base.entity.order.FindRule;
import cn.ommiao.base.entity.order.Group;
import cn.ommiao.base.entity.order.NotFoundEvent;
import cn.ommiao.base.entity.order.Order;
import cn.ommiao.base.entity.order.UiInfo;
import cn.ommiao.base.util.StringUtil;

import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_ACTION;
import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_FIND_RULE;
import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_NOT_FOUND_EVENT;
import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_TITLE;

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
        for (Order order : group.orders) {

        }
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
                    showFindRuleSelector(i);
                }
                break;
            case R.id.tv_not_found_event:
                showNotFoundEventSelector(i);
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

    private void showFindRuleSelector(int index){
        Order order = group.orders.get(index);
        HashSet<FindRule> findRules = new HashSet<>(order.uiInfo.findRules.keySet());
        EnumSelectorFragment<FindRule> fragment = new EnumSelectorFragment<>(FindRule.class, findRules);
        fragment.setOnEnumSelectorListener(findRule -> {
            adapter.notifyItemChanged(index + adapter.getHeaderLayoutCount(), PAYLOAD_FIND_RULE);
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
