package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiDevice;

import cn.ommiao.base.exception.InjectEventException;

public abstract class BaseInjectEventActionHelper extends BaseGlobalActionHelper {

    @Override
    public boolean performGlobalAction(UiDevice uiDevice) throws InjectEventException {
        boolean success = injectEvent(uiDevice);
        if(!success){
            throw new InjectEventException(getExceptionMessage());
        }
        return true;
    }

    protected abstract boolean injectEvent(UiDevice uiDevice);
    protected abstract String getExceptionMessage();

}
