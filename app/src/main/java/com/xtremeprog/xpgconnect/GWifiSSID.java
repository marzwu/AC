package com.xtremeprog.xpgconnect;

public class GWifiSSID {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public GWifiSSID() {
        this(GConnectJNI.new_GWifiSSID(), true);
    }

    protected GWifiSSID(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(GWifiSSID gWifiSSID) {
        return gWifiSSID == null ? 0 : gWifiSSID.swigCPtr;
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_GWifiSSID(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }

    public short getRssi() {
        return GConnectJNI.GWifiSSID_rssi_get(this.swigCPtr, this);
    }

    public String getSsid() {
        return GConnectJNI.GWifiSSID_ssid_get(this.swigCPtr, this);
    }

    public void setRssi(short s) {
        GConnectJNI.GWifiSSID_rssi_set(this.swigCPtr, this, s);
    }

    public void setSsid(String str) {
        GConnectJNI.GWifiSSID_ssid_set(this.swigCPtr, this, str);
    }
}
