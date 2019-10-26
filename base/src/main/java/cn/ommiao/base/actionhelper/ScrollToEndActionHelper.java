package cn.ommiao.base.actionhelper;

public class ScrollToEndActionHelper extends BaseScrollToActionHelper {


    @Override
    protected ScrollToWhere getScrollToWhere() {
        return ScrollToWhere.END;
    }
}
