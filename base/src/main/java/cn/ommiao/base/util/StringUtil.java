package cn.ommiao.base.util;

public class StringUtil {

    public static boolean isEmptyOrSpace(String s){
        if(s == null){
            return true;
        }
        return s.trim().length() == 0;
    }

    public static boolean isEmpty(String s){
        if(s == null){
            return true;
        }
        return s.length() == 0;
    }

}
