package cn.ommiao.base.entity.order;

public class ExecuteParam {

    public Point POSITION;

    public String TARGET_PACKAGE;

    public String START_ACTIVITY;

    public String INPUT_DATA;

    public long WIDGET_NOT_FOUND_TIMEOUT;

    public long WAIT_FOR_EXISTS_TIMEOUT;

    public long WAIT_UNTIL_GONE_TIMEOUT;

    public class Point{
        public int x, y;
    }

}
