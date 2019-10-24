package cn.ommiao.base.entity.order;

public enum ExceptionEvent {

      ERROR("报告异常，停止运行")
    , IGNORE_ORDER("忽略指定的指令（s）")
    , IGNORE_GROUP("忽略指定的指令组（s）")
    , RETRY("重试n次")
    ;

      private String description;

    ExceptionEvent(String description){
          this.description = description;
      }

    public String getDescription() {
        return description;
    }

}
