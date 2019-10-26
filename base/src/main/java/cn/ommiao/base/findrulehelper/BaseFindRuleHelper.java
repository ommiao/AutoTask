package cn.ommiao.base.findrulehelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.entity.order.FindRule;

public abstract class BaseFindRuleHelper {

    protected abstract FindRule getFindRule();

    public abstract UiSelector bindUiSelector(@NonNull UiSelector uiSelector, String value);
    public abstract BySelector bindBySelector(@Nullable BySelector bySelector, String value);

}
