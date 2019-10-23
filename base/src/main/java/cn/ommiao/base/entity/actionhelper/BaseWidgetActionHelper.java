package cn.ommiao.base.entity.actionhelper;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;

public abstract class BaseWidgetActionHelper extends BaseActionHelper {

    @Override
    protected int getActionType() {
        return WIDGET;
    }

    @Override
    public void performGlobalAction(UiDevice uiDevice) {

    }
}
