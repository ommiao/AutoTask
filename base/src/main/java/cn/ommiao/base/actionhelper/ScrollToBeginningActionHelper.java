package cn.ommiao.base.actionhelper;

public class ScrollToBeginningActionHelper extends BaseScrollToActionHelper {

    @Override
    protected ScrollToWhere getScrollToWhere() {
        return ScrollToWhere.BEGINNING;
    }
}
