package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiDevice;

import cn.ommiao.base.entity.order.ExecuteParam;

public class ClickPositionActionHelper extends BaseGlobalActionHelper {

    @Override
    public boolean performGlobalAction(UiDevice uiDevice) {
        ExecuteParam.Point point = order.executeParam.POSITION;
        return uiDevice.click(point.x, point.y);
    }

}
