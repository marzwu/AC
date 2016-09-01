package com.xtremeprog.xpgconnect;

import java.util.concurrent.ConcurrentHashMap;

public class XPGWifiDeviceListener {
    public void didDeviceOnline(XPGWifiDevice xPGWifiDevice, boolean z) {
    }

    public void didDisconnected(XPGWifiDevice xPGWifiDevice) {
    }

    public void didLogin(XPGWifiDevice xPGWifiDevice, int i) {
    }

    public void didQueryHardwareInfo(XPGWifiDevice xPGWifiDevice, int i, ConcurrentHashMap<String, String> concurrentHashMap) {
    }

    public void didReceiveData(XPGWifiDevice xPGWifiDevice, ConcurrentHashMap<String, Object> concurrentHashMap, int i) {
    }

    protected void didSetSwitcher(XPGWifiDevice xPGWifiDevice, int i) {
    }

    public void didUpdateDeviceLog(XPGWifiDevice xPGWifiDevice, ConcurrentHashMap<String, Object> concurrentHashMap) {
    }
}
