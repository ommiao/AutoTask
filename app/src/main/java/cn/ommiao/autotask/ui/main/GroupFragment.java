package cn.ommiao.autotask.ui.main;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;

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

import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_ACTION;
import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_FIND_RULE;
import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_NOT_FOUND_EVENT;
import static cn.ommiao.autotask.ui.adapter.OrderListAdapter.PAYLOAD_TITLE;

public class GroupFragment extends BaseFragment<FragmentGroupBinding, MainViewModel> implements BaseQuickAdapter.OnItemChildClickListener {

    private Group group;

    private OrderListAdapter adapter;
    private HeaderOrderListBinding headerOrderListBinding;
    private FooterOrderListBinding footerOrderListBinding;

    public GroupFragment(Group group){
        this.group = group;
    }

    @Override
    protected void initViews() {
        @SuppressLint("InflateParams")
        View header = LayoutInflater.from(mContext).inflate(R.layout.header_order_list, null);
        headerOrderListBinding = DataBindingUtil.bind(header);
        assert headerOrderListBinding != null;
        headerOrderListBinding.tvGroupTitle.setText(group.groupName);
        headerOrderListBinding.etRepeatTimes.setText(String.valueOf(group.repeatTimes));
        mBinding.rvOrders.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new OrderListAdapter(R.layout.item_order_list, group.orders);
        adapter.addHeaderView(header);
        View footer = LayoutInflater.from(mContext).inflate(R.layout.footer_order_list, null);
        footerOrderListBinding = DataBindingUtil.bind(footer);
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
        order.findRule = FindRule.ID;
        order.action = Action.CLICK;
        order.uiInfo = new UiInfo();
        order.uiInfo.id = "id";
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
        EnumSelectorFragment<Action> fragment = new EnumSelectorFragment<>(Action.class);
        fragment.setOnEnumSelectorListener(action -> {
            group.orders.get(index).action = action;
            adapter.notifyItemChanged(index + adapter.getHeaderLayoutCount(), PAYLOAD_ACTION);
        });
        fragment.show(getChildFragmentManager(), EnumSelectorFragment.class.getSimpleName());
    }

    private void showFindRuleSelector(int index){
        EnumSelectorFragment<FindRule> fragment = new EnumSelectorFragment<>(FindRule.class);
        fragment.setOnEnumSelectorListener(findRule -> {
            Order order = group.orders.get(index);
            if(!order.action.isGlobalAction() && findRule == FindRule.DEVICE){
                showNotAllowedFindRuleDialog();
                return;
            }
            order.findRule = findRule;
            adapter.notifyItemChanged(index + adapter.getHeaderLayoutCount(), PAYLOAD_FIND_RULE);
        });
        fragment.show(getChildFragmentManager(), EnumSelectorFragment.class.getSimpleName());
    }

    private void showNotFoundEventSelector(int index){
        EnumSelectorFragment<NotFoundEvent> fragment = new EnumSelectorFragment<>(NotFoundEvent.class);
        fragment.setOnEnumSelectorListener(notFoundEvent -> {
            group.orders.get(index).notFoundEvent = notFoundEvent;
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
}
