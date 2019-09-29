package cn.ommiao.base.findrulehelper;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.entity.order.FindRule;

public class DescriptionMatchesHelper extends DescriptionHelper {

    @Override
    protected FindRule getFindRule() {
        return FindRule.DESCRIPTION_MATCHES;
    }

    @Override
    public UiSelector bindUiSelector(@NonNull UiSelector uiSelector, String value) {
        return uiSelector.descriptionMatches(value);
    }

}
