package cn.ommiao.base.actionhelper;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;

public abstract class BaseSwipeWidgetActionHelper extends BaseWidgetActionHelper {

    protected enum SwipeOrientation {
        UP, DOWN, LEFT, RIGHT
    }

    @Override
    public void performWidgetAction(UiObject uiObject, UiObject2 uiObject2) throws UiObjectNotFoundException {
        int steps = order.executeParam.SCROLL_STEPS;
        switch (getOrientation()){
            default:
            case UP:
                uiObject.swipeUp(steps);
                break;
            case DOWN:
                uiObject.swipeDown(steps);
                break;
            case LEFT:
                uiObject.swipeLeft(steps);
                break;
            case RIGHT:
                uiObject.swipeRight(steps);
                break;
        }
    }

    protected abstract SwipeOrientation getOrientation();
}
