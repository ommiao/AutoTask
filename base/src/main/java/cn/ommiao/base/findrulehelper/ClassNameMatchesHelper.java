package cn.ommiao.base.findrulehelper;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.entity.order.FindRule;

public class ClassNameMatchesHelper extends ClassNameHelper {

    @Override
    protected FindRule getFindRule() {
        return FindRule.CLASSNAME_MATCHES;
    }

    @Override
    public UiSelector bindUiSelector(@NonNull UiSelector uiSelector, String value) {
        return uiSelector.classNameMatches(value);
    }
}
