package cn.ommiao.base.findrulehelper;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.entity.order.FindRule;

public class TextMatchesHelper extends TextHelper {

    @Override
    protected FindRule getFindRule() {
        return FindRule.TEXT_MATCHES;
    }

    @Override
    public UiSelector bindUiSelector(@NonNull UiSelector uiSelector, String value) {
        return uiSelector.textMatches(value);
    }

}
