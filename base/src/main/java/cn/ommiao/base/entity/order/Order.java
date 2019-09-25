package cn.ommiao.base.entity.order;

import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import cn.ommiao.base.entity.JavaBean;

public class Order extends JavaBean {

    public static final int INFINITE = 0;

    public Action action;

    public int repeatTimes;

    public long delay;

    public UiInfo uiInfo = new UiInfo();

    public NotFoundEvent notFoundEvent;

    public Order alternate;

    public long timeout;

    public HashMap<ExecuteParam, String> executeParams = new HashMap<>();

    public transient View uiInfoView;

    public transient ViewGroup uiInfoParent;

    public String getParamValue(ExecuteParam param){
        return executeParams.get(param);
    }

}
