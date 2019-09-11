package cn.ommiao.base.findrulehelper;

import android.view.View;

import cn.ommiao.base.R;
import cn.ommiao.base.entity.order.Order;
import cn.ommiao.base.entity.order.UiInfo;

public class DescriptionHelper extends BaseFindRuleHelper {

    @Override
    protected int getLayoutId() {
        return R.layout.layout_uiinfo_description;
    }

    @Override
    public String isDataValid(View view) {
        if(!isEditTextFilled(view, R.id.et_description)){
            return "未录入控件描述";
        }
        return DATA_VALID;
    }

    @Override
    public void saveToOrder(View view, Order order) {
        order.uiInfo = new UiInfo();
        order.uiInfo.description = getEditTextContent(view, R.id.et_description);
    }

    @Override
    public void setData(View view, Order order) {
        getEditText(view, R.id.et_description).setText(order.uiInfo.description);
    }

}
