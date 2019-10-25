package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiDevice;

public class PressHomeActionHelper extends BaseInjectEventActionHelper {

    @Override
    protected boolean injectEvent(UiDevice uiDevice) {
        return uiDevice.pressHome();
    }

    @Override
    protected String getExceptionMessage() {
        return "点击Home失败";
    }

}
