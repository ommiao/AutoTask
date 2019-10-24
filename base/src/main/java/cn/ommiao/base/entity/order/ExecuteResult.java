package cn.ommiao.base.entity.order;

import cn.ommiao.base.entity.JavaBean;

public class ExecuteResult extends JavaBean {

    public String taskId;

    public String taskName;

    public boolean success;

    public String startTime, endTime;

    public String errorReason;

}
