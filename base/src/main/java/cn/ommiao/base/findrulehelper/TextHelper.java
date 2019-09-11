package cn.ommiao.base.findrulehelper;

import android.view.View;

import cn.ommiao.base.R;
import cn.ommiao.base.entity.order.Order;
import cn.ommiao.base.entity.order.UiInfo;

public class TextHelper extends BaseFindRuleHelper {

    @Override
    protected int getLayoutId() {
        return R.layout.layout_uiinfo_text;
    }

    @Override
    public String isDataValid(View view) {
        if(!isEditTextFilled(view, R.id.et_text)){
            return "未录入控件文字";
        }
        return DATA_VALID;
    }

    @Override
    public void saveToOrder(View view, Order order) {
        order.uiInfo = new UiInfo();
        order.uiInfo.text = getEditTextContent(view, R.id.et_text);
    }

    @Override
    public void setData(View view, Order order) {
        getEditText(view, R.id.et_text).setText(order.uiInfo.text);
    }

}
