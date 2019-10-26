package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;

import cn.ommiao.base.exception.StopGroupException;

public class StopGroupIfExistActionHelper extends BaseWidgetActionHelper {

    @Override
    public void performWidgetAction(UiObject uiObject, UiObject2 uiObject2) throws StopGroupException {
        boolean exist = uiObject.waitForExists(order.executeParam.STOP_GROUP_TIMEOUT);
        if(exist){
            throw new StopGroupException("Widget exist, stop group");
        }
    }

}
