package com.yolohealth.lunngmonitor;

import android.app.Application;
import android.content.Context;

public class LungMonitorApp extends Application {
    public static Context context;
    private static LungMonitorApp sInstance;

    public static LungMonitorApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        context = this;

    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
