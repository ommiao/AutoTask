package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiDevice;

public class PressEnterActionHelper extends BaseInjectEventActionHelper {

    @Override
    protected boolean injectEvent(UiDevice uiDevice) {
        return uiDevice.pressEnter();
    }

    @Override
    protected String getExceptionMessage() {
        return "点击Enter失败";
    }

}
