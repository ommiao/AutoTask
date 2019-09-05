package cn.ommiao.base.entity.order;

import cn.ommiao.base.findrulehelper.BaseFindRuleHelper;
import cn.ommiao.base.findrulehelper.ClassNameHelper;
import cn.ommiao.base.findrulehelper.DescriptionHelper;
import cn.ommiao.base.findrulehelper.DeviceHelper;
import cn.ommiao.base.findrulehelper.IdHelper;
import cn.ommiao.base.findrulehelper.TextHelper;
import cn.ommiao.base.findrulehelper.TextParentIdScrollHelper;

public enum FindRule {

      DEVICE("不找控件", new DeviceHelper())
    , ID("控件ID", new IdHelper())
    , DESCRIPTION("控件描述", new DescriptionHelper())
    , TEXT("控件文字", new TextHelper())
    , TEXT_CONTAINS("控件文字包含", new TextHelper())
    , CLASSNAME("控件类名", new ClassNameHelper())
    , TEXT_PARENT_ID_SCROLL("先按控件ID查找父控件并滑动，同时按控件文字查找", new TextParentIdScrollHelper())
    ;

    private String description;
    private BaseFindRuleHelper findRuleHelper;

    FindRule(String description, BaseFindRuleHelper findRuleHelper){
        this.description = description;
        this.findRuleHelper = findRuleHelper;
    }

    public String getDescription() {
        return description;
    }

    public BaseFindRuleHelper getFindRuleHelper() {
        return findRuleHelper;
    }
}
