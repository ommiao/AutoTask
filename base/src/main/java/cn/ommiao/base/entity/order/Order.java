package cn.ommiao.base.entity.order;

import java.util.ArrayList;

import cn.ommiao.base.entity.JavaBean;

public class Order extends JavaBean {

    public static final int INFINITE = 0;

    public String orderId;

    public Action action;

    public int repeatTimes;

    public long delay;

    public UiInfo uiInfo = new UiInfo();

    public ExceptionEvent exceptionEvent;

    public Order alternate;

    public ExecuteParam executeParam = new ExecuteParam();

    public ExceptionParam exceptionParam = new ExceptionParam();

    public ArrayList<UserParam> userParams = new ArrayList<>();

}
