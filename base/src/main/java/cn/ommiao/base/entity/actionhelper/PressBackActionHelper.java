package cn.ommiao.base.entity.actionhelper;

import androidx.test.uiautomator.UiDevice;

public class PressBackActionHelper extends BaseGlobalActionHelper {

    @Override
    public void performGlobalAction(UiDevice uiDevice){
        uiDevice.pressBack();
    }

}
