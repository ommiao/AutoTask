package cn.ommiao.base.entity.actionhelper;

import androidx.test.uiautomator.UiDevice;

import java.io.IOException;

import cn.ommiao.base.entity.order.ExecuteParam;
import cn.ommiao.base.entity.order.Order;

public class ForceStopActionHelper extends BaseGlobalActionHelper {

    @Override
    public void performGlobalAction(UiDevice uiDevice) throws IOException {
        uiDevice.executeShellCommand("am force-stop " + order.getParamValue(ExecuteParam.TARGET_PACKAGE));
    }

}
