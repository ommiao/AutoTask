package cn.ommiao.base.findrulehelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.entity.order.FindRule;

public class DescriptionContainsHelper extends DescriptionHelper {

    @Override
    protected FindRule getFindRule() {
        return FindRule.DESCRIPTION_CONTAINS;
    }

    @Override
    public UiSelector bindUiSelector(@NonNull UiSelector uiSelector, String value) {
        return uiSelector.descriptionContains(value);
    }

    @Override
    public BySelector bindBySelector(@Nullable BySelector bySelector, String value) {
        return bySelector == null ? By.descContains(value) : bySelector.descContains(value);
    }

}
