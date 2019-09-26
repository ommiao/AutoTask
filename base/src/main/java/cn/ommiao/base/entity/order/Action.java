package cn.ommiao.base.entity.order;

public enum Action implements BaseEnum<Action> {

      HOME("回到桌面", true)
    , BACK("全局返回", true)
    , CLICK("点击控件", false)
    , CLICK_POSITION("点击坐标", true)
    , FORCE_STOP("强制关闭应用", true)
    ;

      private String description;
      private boolean isGlobalAction;

      Action(String description, boolean isGlobalAction){
          this.description = description;
          this.isGlobalAction = isGlobalAction;
      }


    @Override
    public String getTitle() {
        return "执行动作";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Action getEnum() {
        return this;
    }

    @Override
    public EnumGroup getEnumGroup() {
        return EnumGroup.ACTION;
    }

    public boolean isGlobalAction() {
        return isGlobalAction;
    }
}
