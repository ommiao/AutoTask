package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;

import cn.ommiao.base.exception.WaitTooLongException;

public class WaitUntilWidgetGoneActionHelper extends BaseWidgetActionHelper {

    @Override
    public void performWidgetAction(UiObject uiObject, UiObject2 uiObject2) throws WaitTooLongException {
        long timeout = order.executeParam.WAIT_UNTIL_GONE_TIMEOUT;
        if(timeout <= 1000){
            timeout = 1000;
        }
        boolean gone = uiObject.waitUntilGone(timeout);
        if(!gone){
            throw new WaitTooLongException("Wait too long, target does not gone: " + uiObject.getSelector().toString());
        }
    }

}