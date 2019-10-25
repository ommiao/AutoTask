package cn.ommiao.base.actionhelper;

public class ForceStopActionHelper extends BaseShellCommandActionHelper {

    private String targetPackage;

    @Override
    protected String getShellCommand() {
        targetPackage = order.executeParam.TARGET_PACKAGE;
        return "am force-stop " + targetPackage;
    }


    @Override
    protected String getExceptionMessage() {
        return "停止运行" + targetPackage + "失败";
    }

    @Override
    protected boolean isSuccess(String result) {
        return true;
    }

}