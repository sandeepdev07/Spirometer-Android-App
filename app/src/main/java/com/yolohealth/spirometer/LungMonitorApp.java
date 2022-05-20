package com.yolohealth.spirometer;

import android.app.Application;
import android.content.Context;

import com.telit.terminalio.TIOManager;

public class LungMonitorApp extends Application {
    public static Context context;
    private static LungMonitorApp sInstance;

    public static final String PERIPHERAL_ID_NAME = "com.telit.tiosample.peripheralId";

    public static LungMonitorApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TIOManager.initialize(this.getApplicationContext());
        sInstance = this;
        context = this;

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        TIOManager.getInstance().done();
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
