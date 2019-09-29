package cn.ommiao.base.entity.order;

import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import cn.ommiao.base.entity.JavaBean;

public class UiInfo extends JavaBean {

    public UiInfo parent;

    public HashMap<FindRule, String> findRules = new HashMap<>();

    public transient HashMap<FindRule, View> views = new HashMap<>();

    public transient ViewGroup viewGroup;

    public String getParamValue(FindRule findRule){
        return findRules.get(findRule);
    }

}