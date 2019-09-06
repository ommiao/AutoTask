package cn.ommiao.autotask.ui.main;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FragmentGroupBinding;
import cn.ommiao.autotask.databinding.HeaderGroupListBinding;
import cn.ommiao.autotask.ui.adapter.OrderListAdapter;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.base.entity.order.Group;

public class GroupFragment extends BaseFragment<FragmentGroupBinding, MainViewModel> {

    private Group group;

    private OrderListAdapter adapter;
    private HeaderGroupListBinding headerGroupListBinding;

    public GroupFragment(Group group){
        this.group = group;
    }

    @Override
    protected void initViews() {
        @SuppressLint("InflateParams")
        View header = LayoutInflater.from(mContext).inflate(R.layout.header_group_list, null);
        headerGroupListBinding = DataBindingUtil.bind(header);
        assert headerGroupListBinding != null;
        headerGroupListBinding.tvGroupTitle.setText(group.groupName);
        headerGroupListBinding.etRepeatTimes.setText(String.valueOf(group.repeatTimes));
        mBinding.rvOrders.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new OrderListAdapter(R.layout.item_order_list, group.orders);
        adapter.addHeaderView(header);
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
}
