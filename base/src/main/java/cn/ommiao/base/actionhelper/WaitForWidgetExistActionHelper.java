package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;

import cn.ommiao.base.exception.WaitTooLongException;

public class WaitForWidgetExistActionHelper extends BaseWidgetActionHelper {

    @Override
    public void performWidgetAction(UiObject uiObject, UiObject2 uiObject2) throws WaitTooLongException {
        long timeout = order.executeParam.WAIT_FOR_EXISTS_TIMEOUT;
        if(timeout <= 1000){
            timeout = 1000;
        }
        boolean exists = uiObject.waitForExists(timeout);
        if(!exists){
            throw new WaitTooLongException("Wait too long, target does not appear: " + uiObject.getSelector().toString());
        }
    }

}