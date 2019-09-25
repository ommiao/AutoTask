package cn.ommiao.base.entity.order;

import androidx.annotation.NonNull;
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

public enum FindRule implements BaseEnum<FindRule>{

    ID(FindRuleGroup.ID, "控件ID", new IdHelper()),
    ID_MATCHES(FindRuleGroup.ID, "控件ID匹配", new IdMatchesHelper()),
    DESCRIPTION(FindRuleGroup.DESCRIPTION, "控件描述", new DescriptionHelper()),
    DESCRIPTION_CONTAINS(FindRuleGroup.DESCRIPTION, "控件描述包含", new DescriptionContainsHelper()),
    DESCRIPTION_MATCHES(FindRuleGroup.DESCRIPTION, "控件描述匹配", new DescriptionMatchesHelper()),
    TEXT(FindRuleGroup.TEXT, "控件文字", new TextHelper()),
    TEXT_CONTAINS(FindRuleGroup.TEXT, "控件文字包含", new TextContainsHelper()),
    TEXT_MATCHES(FindRuleGroup.TEXT, "控件文字匹配", new TextMatchesHelper()),
    CLASSNAME(FindRuleGroup.CLASSNAME, "控件类名", new ClassNameHelper()),
    CLASSNAME_MATCHES(FindRuleGroup.CLASSNAME, "控件类名匹配", new ClassNameMatchesHelper());

    private FindRuleGroup findRuleGroup;
    private String description;
    private BaseFindRuleHelper findRuleHelper;

    FindRule(FindRuleGroup findRuleGroup, String description, BaseFindRuleHelper findRuleHelper){
        this.findRuleGroup = findRuleGroup;
        this.description = description;
        this.findRuleHelper = findRuleHelper;
    }

    public BaseFindRuleHelper getFindRuleHelper() {
        return findRuleHelper;
    }

    public FindRuleGroup getFindRuleGroup() {
        return findRuleGroup;
    }

    @Override
    public String getTitle() {
        return "控件查找规则";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public FindRule getEnum() {
        return this;
    }

    public UiSelector bindSelector(@NonNull UiSelector uiSelector, String value){
        return findRuleHelper.bindUiSelector(uiSelector, value);
    }

    public BySelector bindSelector(@NonNull BySelector bySelector, String value){
        return findRuleHelper.bindBySelector(bySelector, value);
    }

    public enum FindRuleGroup {
        ID,
        DESCRIPTION,
        TEXT,
        CLASSNAME
    }

}
