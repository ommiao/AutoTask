package cn.ommiao.base.entity.order;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.findrulehelper.BaseFindRuleHelper;
import cn.ommiao.base.findrulehelper.ClassNameHelper;
import cn.ommiao.base.findrulehelper.ClassNameMatchesHelper;
import cn.ommiao.base.findrulehelper.DescriptionContainsHelper;
import cn.ommiao.base.findrulehelper.DescriptionHelper;
import cn.ommiao.base.findrulehelper.DescriptionMatchesHelper;
import cn.ommiao.base.findrulehelper.IdHelper;
import cn.ommiao.base.findrulehelper.IdMatchesHelper;
import cn.ommiao.base.findrulehelper.TextContainsHelper;
import cn.ommiao.base.findrulehelper.TextHelper;
import cn.ommiao.base.findrulehelper.TextMatchesHelper;

public enum FindRule {

    ID("控件ID", new IdHelper()),
    ID_MATCHES("控件ID匹配", new IdMatchesHelper()),
    DESCRIPTION("控件描述", new DescriptionHelper()),
    DESCRIPTION_CONTAINS("控件描述包含", new DescriptionContainsHelper()),
    DESCRIPTION_MATCHES("控件描述匹配", new DescriptionMatchesHelper()),
    TEXT("控件文字", new TextHelper()),
    TEXT_CONTAINS("控件文字包含", new TextContainsHelper()),
    TEXT_MATCHES("控件文字匹配", new TextMatchesHelper()),
    CLASSNAME("控件类名", new ClassNameHelper()),
    CLASSNAME_MATCHES("控件类名匹配", new ClassNameMatchesHelper());

    private String description;
    private BaseFindRuleHelper findRuleHelper;

    FindRule(String description, BaseFindRuleHelper findRuleHelper){
        this.description = description;
        this.findRuleHelper = findRuleHelper;
    }

    public BaseFindRuleHelper getFindRuleHelper() {
        return findRuleHelper;
    }

    public String getDescription() {
        return description;
    }

    public UiSelector bindSelector(@NonNull UiSelector uiSelector, String value){
        return findRuleHelper.bindUiSelector(uiSelector, value);
    }

    public BySelector bindSelector(@Nullable BySelector bySelector, String value){
        return findRuleHelper.bindBySelector(bySelector, value);
    }

}
