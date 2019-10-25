package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiDevice;

public abstract class BaseWidgetActionHelper extends BaseActionHelper {

    @Override
    protected int getActionType() {
        return WIDGET;
    }

    @Override
    public boolean performGlobalAction(UiDevice uiDevice) {
        return false;
    }
}
