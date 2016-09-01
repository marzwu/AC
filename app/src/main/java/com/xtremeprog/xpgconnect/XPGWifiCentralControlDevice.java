package com.xtremeprog.xpgconnect;

public class XPGWifiCentralControlDevice extends XPGWifiDevice {
    protected XPGWifiCentralControlDevice(GWifiDevice gWifiDevice) {
        super(gWifiDevice);
    }

    public void addSubDevice() {
        write("{\"cmd\":11}");
    }

    public void deleteSubDevice(String str) {
        write("{\"cmd\":12,\"subDid\":" + str + "}");
    }

    public void getSubDevices() {
        write("{\"cmd\":13}");
    }
}
