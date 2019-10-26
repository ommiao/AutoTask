package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;

import cn.ommiao.base.entity.order.ScrollType;

public abstract class BaseScrollToActionHelper extends BaseWidgetActionHelper {

    protected enum ScrollToWhere {
        END, BEGINNING
    }

    @Override
    public void performWidgetAction(UiObject uiObject, UiObject2 uiObject2) throws UiObjectNotFoundException {
        UiScrollable uiScrollable = new UiScrollable(uiObject.getSelector());
        double deadZonePct = order.executeParam.SCROLL_DEAD_ZONE_PERCENTAGE;
        if(deadZonePct > 1D){
            deadZonePct = 0.9;
        }
        uiScrollable.setSwipeDeadZonePercentage(deadZonePct);
        if(order.executeParam.SCROLL_TO_ORIENTATION == ScrollType.HORIZONTAL){
            uiScrollable.setAsHorizontalList();
        }
        switch (getScrollToWhere()){
            default:
            case END:
                uiScrollable.scrollToEnd(order.executeParam.SCROLL_MAX_SWIPES, order.executeParam.SCROLL_STEPS);
                break;
            case BEGINNING:
                uiScrollable.scrollToBeginning(order.executeParam.SCROLL_MAX_SWIPES, order.executeParam.SCROLL_STEPS);
                break;
        }
    }

    protected abstract ScrollToWhere getScrollToWhere();

}
