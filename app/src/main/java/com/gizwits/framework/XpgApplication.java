package com.gizwits.framework;

import android.app.Application;

import com.gizwits.framework.config.Configs;
import com.xtremeprog.xpgconnect.XPGWifiSDK;

public class XpgApplication extends Application {
    private boolean isDownload;

    public boolean isDownload() {
        return this.isDownload;
    }

    public void onCreate() {
        super.onCreate();
        XPGWifiSDK.sharedInstance().startWithAppID(getApplicationContext(), Configs.APPID);
        XPGWifiSDK.sharedInstance().setLogLevel(Configs.LOG_LEVEL, Configs.LOG_FILE_NAME, true);
        this.isDownload = false;
    }

    public void setDownload(boolean z) {
        this.isDownload = z;
    }
}
