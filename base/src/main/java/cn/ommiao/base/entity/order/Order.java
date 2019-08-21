package cn.ommiao.base.entity.order;

import cn.ommiao.base.entity.JavaBean;

public class Order extends JavaBean {

    public String findRule;
    public String action;
    public int repeatTimes;

    public long delay;

    public UiInfo uiInfo;

    public String notFoundEvent;

}
