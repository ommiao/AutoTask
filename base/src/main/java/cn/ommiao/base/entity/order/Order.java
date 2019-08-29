package cn.ommiao.base.entity.order;

import cn.ommiao.base.entity.JavaBean;

public class Order extends JavaBean {

    public FindRule findRule;

    public Action action;

    public int repeatTimes;

    public long delay;

    public UiInfo uiInfo;

    public String notFoundEvent;

    public Order alternate;

}
