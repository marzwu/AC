package com.xtremeprog.xpgconnect;

public class GWifiReceiveInfo {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public GWifiReceiveInfo() {
        this(GConnectJNI.new_GWifiReceiveInfo(), true);
    }

    protected GWifiReceiveInfo(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(GWifiReceiveInfo gWifiReceiveInfo) {
        return gWifiReceiveInfo == null ? 0 : gWifiReceiveInfo.swigCPtr;
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_GWifiReceiveInfo(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }

    public String getName() {
        return GConnectJNI.GWifiReceiveInfo_name_get(this.swigCPtr, this);
    }

    public int getValue() {
        return GConnectJNI.GWifiReceiveInfo_value_get(this.swigCPtr, this);
    }

    public void setName(String str) {
        GConnectJNI.GWifiReceiveInfo_name_set(this.swigCPtr, this, str);
    }

    public void setValue(int i) {
        GConnectJNI.GWifiReceiveInfo_value_set(this.swigCPtr, this, i);
    }
}
