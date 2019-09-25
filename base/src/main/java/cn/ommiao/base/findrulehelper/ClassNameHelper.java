package cn.ommiao.base.findrulehelper;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.R;
import cn.ommiao.base.entity.order.FindRule;

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
    public void setData(View view, String value) {
        setEditTextContent(view, R.id.et_classname, value);
    }

    @Override
    protected String getValueFromView(View view) {
        return getEditTextContent(view, R.id.et_classname);
    }

    @Override
    protected FindRule getFindRule() {
        return FindRule.CLASSNAME;
    }

    @Override
    public UiSelector bindUiSelector(@NonNull UiSelector uiSelector, String value) {
        return uiSelector.className(value);
    }

    @Override
    public BySelector bindBySelector(@NonNull BySelector bySelector, String value) {
        return bySelector.clazz(value);
    }

}
