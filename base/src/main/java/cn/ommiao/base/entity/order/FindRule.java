package cn.ommiao.base.entity.order;

public enum FindRule {

      DEVICE("不找控件")
    , ID("控件ID")
    , DESCRIPTION("控件描述")
    , TEXT("控件文字")
    , CLASSNAME("控件类名")
    , TEXT_PARENT_ID_SCROLL("按照控件ID查找控件并滑动，并按照控件文字查找子控件")
    ;

    private String description;

    FindRule(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
