package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiDevice;

public class PressMenuActionHelper extends BaseInjectEventActionHelper {

    @Override
    protected boolean injectEvent(UiDevice uiDevice) {
        return uiDevice.pressMenu();
    }

    @Override
    protected String getExceptionMessage() {
        return "点击Menu失败";
    }

}
