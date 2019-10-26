package cn.ommiao.base.findrulehelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.entity.order.FindRule;

public class IdHelper extends BaseFindRuleHelper {

    @Override
    protected FindRule getFindRule() {
        return FindRule.ID;
    }

    @Override
    public UiSelector bindUiSelector(@NonNull UiSelector uiSelector, String value) {
        return uiSelector.resourceId(value);
    }

    @Override
    public BySelector bindBySelector(@Nullable BySelector bySelector, String value) {
        return bySelector == null ? By.res(value) : bySelector.res(value);
    }

}
