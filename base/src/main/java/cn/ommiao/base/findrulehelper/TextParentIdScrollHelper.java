package cn.ommiao.base.findrulehelper;

import android.view.View;

import cn.ommiao.base.R;
import cn.ommiao.base.entity.order.Order;
import cn.ommiao.base.entity.order.UiInfo;

public class TextParentIdScrollHelper extends BaseFindRuleHelper {

    @Override
    protected int getLayoutId() {
        return R.layout.layout_uiinfo_text_parent_id_scroll;
    }

    @Override
    public String isDataValid(View view) {
        if(!isEditTextFilled(view, R.id.et_text)){
            return "未录入控件文字";
        } else if(!isEditTextFilled(view, R.id.et_parent_id)){
            return "未录入父控件Id";
        }
        return DATA_VALID;
    }

    @Override
    protected void saveToOrder(View view, Order order) {
        order.uiInfo = new UiInfo();
        order.uiInfo.text = getEditTextContent(view, R.id.et_text);
        order.uiInfo.parent = new UiInfo();
        order.uiInfo.parent.id = getEditTextContent(view, R.id.et_parent_id);
    }

}
