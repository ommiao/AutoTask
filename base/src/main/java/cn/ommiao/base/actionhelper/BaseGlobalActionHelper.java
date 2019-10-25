package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;

public abstract class BaseGlobalActionHelper extends BaseActionHelper {

    @Override
    protected int getActionType() {
        return GLOBAL;
    }

    @Override
    public void performWidgetAction(UiObject uiObject, UiObject2 uiObject2) {

    }

}
