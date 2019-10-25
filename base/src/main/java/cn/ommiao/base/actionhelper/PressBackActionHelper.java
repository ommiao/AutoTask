package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiDevice;

public class PressBackActionHelper extends BaseInjectEventActionHelper {

    @Override
    protected boolean injectEvent(UiDevice uiDevice) {
        return uiDevice.pressBack();
    }

    @Override
    protected String getExceptionMessage() {
        return "点击Back失败";
    }

}
