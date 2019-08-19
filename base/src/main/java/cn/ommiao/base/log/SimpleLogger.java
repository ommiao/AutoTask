package cn.ommiao.base.log;

import com.orhanobut.logger.AndroidLogAdapter;

import cn.ommiao.base.BuildConfig;

public class SimpleLogger {
    public static void initLogger(){
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}
