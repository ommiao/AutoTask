package cn.ommiao.base.entity.order;

import android.view.View;
import android.view.ViewGroup;

import cn.ommiao.base.entity.JavaBean;

public class Order extends JavaBean {

    public static final int INFINITE = 0;

    public FindRule findRule;

    public Action action;

    public int repeatTimes;

    public long delay;

    public UiInfo uiInfo;

    public NotFoundEvent notFoundEvent;

    public Order alternate;

    public long timeout;

    public transient View uiInfoView;

    public transient ViewGroup uiInfoParent;

}
