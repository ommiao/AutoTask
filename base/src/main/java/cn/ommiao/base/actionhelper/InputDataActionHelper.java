package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;


public class InputDataActionHelper extends BaseWidgetActionHelper {

    @Override
    public void performWidgetAction(UiObject uiObject, UiObject2 uiObject2) throws UiObjectNotFoundException {
        uiObject.setText(order.executeParam.INPUT_DATA);
    }

}
