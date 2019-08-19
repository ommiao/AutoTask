package cn.ommiao.autotask.core;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import java.io.File;

import cn.ommiao.base.log.SimpleLogger;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initCrashHandler();
        SimpleLogger.initLogger();
        initFileDir();
    }

    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init();
    }

    public static Context getContext() {
        return context;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initFileDir() {
        File orderFile = getExternalFilesDir("order");
        if (orderFile != null){
            File file = new File(orderFile.getAbsolutePath());
            if(!file.exists()){
                file.mkdirs();
            }
        }
        File crashFile = getExternalFilesDir("crash");
        if (crashFile != null){
            File file = new File(crashFile.getAbsolutePath());
            if(!file.exists()){
                file.mkdirs();
            }
        }
    }
}
