package cn.ommiao.base.findrulehelper;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.R;
import cn.ommiao.base.entity.order.FindRule;

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
    public void setData(View view, String value) {
        setEditTextContent(view, R.id.et_id, value);
    }

    @Override
    protected String getValueFromView(View view) {
        return getEditTextContent(view, R.id.et_id);
    }

    @Override
    protected FindRule getFindRule() {
        return FindRule.ID;
    }

    @Override
    public UiSelector bindUiSelector(@NonNull UiSelector uiSelector, String value) {
        return uiSelector.resourceId(value);
    }

    @Override
    public BySelector bindBySelector(@NonNull BySelector bySelector, String value) {
        return bySelector.res(value);
    }

}
