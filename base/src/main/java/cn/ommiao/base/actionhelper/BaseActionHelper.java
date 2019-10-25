package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;

import java.io.IOException;

import cn.ommiao.base.entity.order.Order;
import cn.ommiao.base.exception.AbstractGlobalActionException;
import cn.ommiao.base.exception.AbstractWidgetActionException;

public abstract class BaseActionHelper {

    protected static final int GLOBAL = 0;
    protected static final int WIDGET = 1;

    protected Order order;

    public BaseActionHelper with(Order order){
        this.order = order;
        return this;
    }

    protected abstract int getActionType();

    public boolean isGlobalAction(){
        return GLOBAL == getActionType();
    }

    public abstract boolean performGlobalAction(UiDevice uiDevice) throws IOException, AbstractGlobalActionException;

    public abstract void performWidgetAction(UiObject uiObject, UiObject2 uiObject2) throws UiObjectNotFoundException, AbstractWidgetActionException;

}
