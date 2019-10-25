package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiDevice;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import cn.ommiao.base.exception.ShellCommandException;

public abstract class BaseShellCommandActionHelper extends BaseGlobalActionHelper {

    @Override
    public boolean performGlobalAction(UiDevice uiDevice) throws IOException, ShellCommandException {
        if(preChecked(uiDevice)){
            String result = uiDevice.executeShellCommand(getShellCommand());
            Logger.d(result);
            boolean success = isSuccess(result);
            if(!success){
                throw new ShellCommandException(getExceptionMessage());
            }
            return true;
        } else {
            throw new ShellCommandException(preCheckedExceptionMessage());
        }
    }

    protected abstract String getShellCommand();

    protected boolean preChecked(UiDevice uiDevice) throws IOException{
        return true;
    }

    protected String preCheckedExceptionMessage(){
        return "命令参数有误";
    }

    protected abstract String getExceptionMessage();
    protected abstract boolean isSuccess(String result);

}
