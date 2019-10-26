package cn.ommiao.base.entity.order;

public class ExecuteParam {

    public Point POSITION;

    public String TARGET_PACKAGE;

    public String START_ACTIVITY;

    public String INPUT_DATA;

    public long WIDGET_NOT_FOUND_TIMEOUT;

    public long WAIT_FOR_EXISTS_TIMEOUT;

    public long WAIT_UNTIL_GONE_TIMEOUT;

    public long STOP_GROUP_TIMEOUT;

    public ScrollType SCROLL_TO_ORIENTATION;

    public int SCROLL_MAX_SWIPES = 10;

    public double SCROLL_DEAD_ZONE_PERCENTAGE = 0.1;

    public int SCROLL_STEPS = 55;

    public class Point{
        public int x, y;
    }

}
