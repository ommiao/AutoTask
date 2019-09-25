package cn.ommiao.base.findrulehelper;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.R;
import cn.ommiao.base.entity.order.FindRule;

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
    public void setData(View view, String value) {
        setEditTextContent(view, R.id.et_text, value);
    }

    @Override
    protected String getValueFromView(View view) {
        return getEditTextContent(view, R.id.et_text);
    }

    @Override
    protected FindRule getFindRule() {
        return FindRule.TEXT;
    }

    @Override
    public UiSelector bindUiSelector(@NonNull UiSelector uiSelector, String value) {
        return uiSelector.text(value);
    }

    @Override
    public BySelector bindBySelector(@NonNull BySelector bySelector, String value) {
        return bySelector.text(value);
    }

}
