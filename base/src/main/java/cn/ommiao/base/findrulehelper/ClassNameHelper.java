package cn.ommiao.base.findrulehelper;

import android.view.View;

import cn.ommiao.base.R;
import cn.ommiao.base.entity.order.Order;
import cn.ommiao.base.entity.order.UiInfo;

public class ClassNameHelper extends BaseFindRuleHelper {

    @Override
    protected int getLayoutId() {
        return R.layout.layout_uiinfo_classname;
    }

    @Override
    public String isDataValid(View view) {
        if(!isEditTextFilled(view, R.id.et_classname)){
            return "未录入类名";
        }
        return DATA_VALID;
    }

    @Override
    public void saveToOrder(View view, Order order) {
        order.uiInfo = new UiInfo();
        order.uiInfo.className = getEditTextContent(view, R.id.et_classname);
    }

    @Override
    public void setData(View view, Order order) {
        getEditText(view, R.id.et_classname).setText(order.uiInfo.className);
    }

}
