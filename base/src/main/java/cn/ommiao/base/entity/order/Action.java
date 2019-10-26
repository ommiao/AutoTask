package cn.ommiao.base.entity.order;

import cn.ommiao.base.actionhelper.AmStartActionHelper;
import cn.ommiao.base.actionhelper.BaseActionHelper;
import cn.ommiao.base.actionhelper.ClickPositionActionHelper;
import cn.ommiao.base.actionhelper.ClickWidgetActionHelper;
import cn.ommiao.base.actionhelper.ForceStopActionHelper;
import cn.ommiao.base.actionhelper.InputDataActionHelper;
import cn.ommiao.base.actionhelper.PressBackActionHelper;
import cn.ommiao.base.actionhelper.PressEnterActionHelper;
import cn.ommiao.base.actionhelper.PressHomeActionHelper;
import cn.ommiao.base.actionhelper.PressMenuActionHelper;
import cn.ommiao.base.actionhelper.ScrollToBeginningActionHelper;
import cn.ommiao.base.actionhelper.ScrollToEndActionHelper;
import cn.ommiao.base.actionhelper.StopGroupIfExistActionHelper;
import cn.ommiao.base.actionhelper.StopGroupIfNotExistActionHelper;
import cn.ommiao.base.actionhelper.SwipeDownWidgetActionHelper;
import cn.ommiao.base.actionhelper.SwipeLeftWidgetActionHelper;
import cn.ommiao.base.actionhelper.SwipeRightWidgetActionHelper;
import cn.ommiao.base.actionhelper.SwipeUpWidgetActionHelper;
import cn.ommiao.base.actionhelper.WaitForWidgetExistActionHelper;
import cn.ommiao.base.actionhelper.WaitUntilWidgetGoneActionHelper;

public enum Action {

      PRESS_HOME("回到桌面", new PressHomeActionHelper())
    , PRESS_BACK("全局返回", new PressBackActionHelper())
    , PRESS_MENU("呼出菜单", new PressMenuActionHelper())
    , PRESS_ENTER("点击回车", new PressEnterActionHelper())
    , CLICK_WIDGET("点击控件", new ClickWidgetActionHelper())
    , WAIT_FOR_WIDGET_EXIST("等待控件出现", new WaitForWidgetExistActionHelper())
    , WAIT_UNTIL_WIDGET_GONE("等待控件消失", new WaitUntilWidgetGoneActionHelper())
    , STOP_GROUP_IF_EXIST("控件存在中断当前指令组", new StopGroupIfExistActionHelper())
    , STOP_GROUP_IF_NOT_EXIST("控件不存在中断当前指令组", new StopGroupIfNotExistActionHelper())
    , SCROLL_TO_END("滑动到底部", new ScrollToEndActionHelper())
    , SCROLL_TO_BEGINNING("滑动到开始", new ScrollToBeginningActionHelper())
    , CLICK_POSITION("点击坐标", new ClickPositionActionHelper())
    , LAUNCH_APP("打开应用", new AmStartActionHelper())
    , STOP_APP("强制关闭应用", new ForceStopActionHelper())
    , INPUT_DATA("填写数据", new InputDataActionHelper())
    , SWIPE_UP_WIDGET("上滑控件", new SwipeUpWidgetActionHelper())
    , SWIPE_DOWN_WIDGET("下滑控件", new SwipeDownWidgetActionHelper())
    , SWIPE_LEFT_WIDGET("左滑控件", new SwipeLeftWidgetActionHelper())
    , SWIPE_RIGHT_WIDGET("右滑控件", new SwipeRightWidgetActionHelper())
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
