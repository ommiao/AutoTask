package cn.ommiao.base.findrulehelper;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.entity.order.FindRule;

public class TextContainsHelper extends TextHelper {

    @Override
    protected FindRule getFindRule() {
        return FindRule.TEXT_CONTAINS;
    }

    @Override
    public UiSelector bindUiSelector(@NonNull UiSelector uiSelector, String value) {
        return uiSelector.textContains(value);
    }

    @Override
    public BySelector bindBySelector(@NonNull BySelector bySelector, String value) {
        return bySelector.textContains(value);
    }
}
