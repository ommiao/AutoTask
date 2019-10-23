package cn.ommiao.base.entity.actionhelper;

import androidx.test.uiautomator.UiDevice;

public class PressHomeActionHelper extends BaseGlobalActionHelper {

    @Override
    public void performGlobalAction(UiDevice uiDevice) throws SecurityException{
        uiDevice.pressHome();
    }

}
