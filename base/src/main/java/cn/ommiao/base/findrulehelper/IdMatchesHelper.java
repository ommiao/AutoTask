package cn.ommiao.base.findrulehelper;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.entity.order.FindRule;

public class IdMatchesHelper extends IdHelper {

    @Override
    protected FindRule getFindRule() {
        return FindRule.ID_MATCHES;
    }

    @Override
    public UiSelector bindUiSelector(@NonNull UiSelector uiSelector, String value) {
        return uiSelector.resourceIdMatches(value);
    }

}
