package cn.ommiao.base.entity.order;

import java.util.ArrayList;

import cn.ommiao.base.entity.JavaBean;

public class Task extends JavaBean {

    public String taskId;
    public String taskName, taskDescription;
    public ArrayList<Group> groups;
    public String taskVersion;
    public boolean startMain = true;

}
