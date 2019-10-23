package cn.ommiao.base.entity.actionhelper;

import androidx.test.uiautomator.UiDevice;

import cn.ommiao.base.entity.order.ExecuteParam;

public class ClickPositionActionHelper extends BaseGlobalActionHelper {

    @Override
    public void performGlobalAction(UiDevice uiDevice) {
        String position = order.getParamValue(ExecuteParam.POSITION);
        int clickPosX = Integer.parseInt(position.split(",")[0]);
        int clickPosY = Integer.parseInt(position.split(",")[1]);
        uiDevice.click(clickPosX, clickPosY);
    }

}
