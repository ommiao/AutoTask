package cn.ommiao.autotask.util;

import android.widget.Toast;

import cn.ommiao.autotask.core.App;

public class ToastUtil {

    public static void shortToast(String msg){
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
