package cn.ommiao.base.findrulehelper;

import android.view.View;

import cn.ommiao.base.R;
import cn.ommiao.base.entity.order.Order;

public class DeviceHelper extends BaseFindRuleHelper {

    @Override
    protected int getLayoutId() {
        return R.layout.layout_uiinfo_device;
    }

    @Override
    public String isDataValid(View view) {
        return DATA_VALID;
    }

    @Override
    protected void saveToOrder(View view, Order order) {

    }
}
