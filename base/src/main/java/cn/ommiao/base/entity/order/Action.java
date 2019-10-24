package cn.ommiao.base.entity.order;

import cn.ommiao.base.entity.actionhelper.AmStartActionHelper;
import cn.ommiao.base.entity.actionhelper.BaseActionHelper;
import cn.ommiao.base.entity.actionhelper.ClickPositionActionHelper;
import cn.ommiao.base.entity.actionhelper.ClickWidgetActionHelper;
import cn.ommiao.base.entity.actionhelper.ForceStopActionHelper;
import cn.ommiao.base.entity.actionhelper.InputDataActionHelper;
import cn.ommiao.base.entity.actionhelper.PressBackActionHelper;
import cn.ommiao.base.entity.actionhelper.PressEnterActionHelper;
import cn.ommiao.base.entity.actionhelper.PressHomeActionHelper;
import cn.ommiao.base.entity.actionhelper.PressMenuActionHelper;

public enum Action {

      PRESS_HOME("回到桌面", new PressHomeActionHelper())
    , PRESS_BACK("全局返回", new PressBackActionHelper())
    , PRESS_MENU("呼出菜单", new PressMenuActionHelper())
    , PRESS_ENTER("点击回车", new PressEnterActionHelper())
    , CLICK_WIDGET("点击控件", new ClickWidgetActionHelper())
    , CLICK_POSITION("点击坐标", new ClickPositionActionHelper())
    , LAUNCH_APP("打开应用", new AmStartActionHelper())
    , FORCE_STOP("强制关闭应用", new ForceStopActionHelper())
    , INPUT_DATA("填写数据", new InputDataActionHelper())
    ;

      private String description;
      private BaseActionHelper actionHelper;

      Action(String description, BaseActionHelper actionHelper){
          this.description = description;
          this.actionHelper = actionHelper;
      }


    public String getDescription() {
        return description;
    }

    public Action getEnum() {
        return this;
    }

    public BaseActionHelper getActionHelper() {
        return actionHelper;
    }
}
