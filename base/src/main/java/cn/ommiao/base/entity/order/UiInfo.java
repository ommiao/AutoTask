package cn.ommiao.base.entity.order;

import java.util.HashMap;

import cn.ommiao.base.entity.JavaBean;

public class UiInfo extends JavaBean {

    public UiInfoParent parent;

    public HashMap<FindRule, String> findRules = new HashMap<>();

    public String getParamValue(FindRule findRule){
        return findRules.get(findRule);
    }

}