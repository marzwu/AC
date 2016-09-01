package com.xtremeprog.xpgconnect;

public class XPGWifiSSID {
    private GWifiSSID originSSID;

    protected XPGWifiSSID(GWifiSSID gWifiSSID) {
        this.originSSID = gWifiSSID;
    }

    public short getRssi() {
        return this.originSSID.getRssi();
    }

    public String getSsid() {
        return this.originSSID.getSsid();
    }
}
