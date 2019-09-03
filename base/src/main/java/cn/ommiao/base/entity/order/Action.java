package cn.ommiao.base.entity.order;

public enum Action {

      HOME("回到桌面")
    , BACK("全局返回")
    , CLICK("点击控件")
    , CLICK_POSITION("点击坐标")
    , FORCE_STOP("强制关闭应用")
    ;

      private String description;

      Action(String description){
          this.description = description;
      }

    public String getDescription() {
        return description;
    }
}
