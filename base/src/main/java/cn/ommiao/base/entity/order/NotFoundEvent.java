package cn.ommiao.base.entity.order;

public enum NotFoundEvent implements BaseEnum<NotFoundEvent>{

      ERROR("报告异常，停止运行")
    , IGNORE("忽略该条，继续执行")
    , IGNORE_GROUP("忽略该组，继续执行")
    , RETRY("重试一次")
    ;

      private String description;

      NotFoundEvent(String description){
          this.description = description;
      }

    @Override
    public String getTitle() {
        return "控件未找到时事件";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public NotFoundEvent getEnum() {
        return this;
    }
}
