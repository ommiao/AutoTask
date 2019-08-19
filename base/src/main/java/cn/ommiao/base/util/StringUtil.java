package cn.ommiao.base.util;

public class StringUtil {

    public static boolean isEmpty(String s){
        if (s == null){
            return true;
        }
        return s.trim().length() == 0;
    }

}
