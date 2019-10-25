package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiDevice;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import cn.ommiao.base.util.StringUtil;

public class AmStartActionHelper extends BaseShellCommandActionHelper {

    private String targetPackage;

    @Override
    protected String getShellCommand() {
        return "am start -n " + targetPackage + "/" + order.executeParam.START_ACTIVITY;
    }

    @Override
    protected String getExceptionMessage() {
        return "启动" + targetPackage + "失败";
    }

    @Override
    protected boolean isSuccess(String result) {
        return true;
    }

    @Override
    protected boolean preChecked(UiDevice uiDevice) throws IOException {
        targetPackage = order.executeParam.TARGET_PACKAGE;
        String shellCommand = "pm list packages";
        Logger.d(shellCommand);
        String result = uiDevice.executeShellCommand(shellCommand);
        return !StringUtil.isEmptyOrSpace(result) && result.contains(targetPackage);
    }

    @Override
    protected String preCheckedExceptionMessage() {
        return "未安装App -> " + targetPackage;
    }
}
