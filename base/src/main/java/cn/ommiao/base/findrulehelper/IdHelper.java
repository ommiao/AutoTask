package cn.ommiao.base.findrulehelper;

import android.view.View;

import cn.ommiao.base.R;
import cn.ommiao.base.entity.order.Order;
import cn.ommiao.base.entity.order.UiInfo;

public class IdHelper extends BaseFindRuleHelper {

    @Override
    protected int getLayoutId() {
        return R.layout.layout_uiinfo_id;
    }

    @Override
    public String isDataValid(View view) {
        if(!isEditTextFilled(view, R.id.et_id)){
            return "未录入控件Id";
        }
        return DATA_VALID;
    }

    @Override
    public void saveToOrder(View view, Order order) {
        order.uiInfo = new UiInfo();
        order.uiInfo.id = getEditTextContent(view, R.id.et_id);
    }

    @Override
    public void setData(View view, Order order) {
        getEditText(view, R.id.et_id).setText(order.uiInfo.id);
    }

}
