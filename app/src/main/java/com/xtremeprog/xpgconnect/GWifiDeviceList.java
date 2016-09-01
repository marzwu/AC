package com.xtremeprog.xpgconnect;

public class GWifiDeviceList {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public GWifiDeviceList() {
        this(GConnectJNI.new_GWifiDeviceList(), true);
    }

    protected GWifiDeviceList(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(GWifiDeviceList gWifiDeviceList) {
        return gWifiDeviceList == null ? 0 : gWifiDeviceList.swigCPtr;
    }

    public long GetCount() {
        return GConnectJNI.GWifiDeviceList_GetCount(this.swigCPtr, this);
    }

    public GWifiDevice GetItem(int i) {
        long GWifiDeviceList_GetItem = GConnectJNI.GWifiDeviceList_GetItem(this.swigCPtr, this, i);
        return GWifiDeviceList_GetItem == 0 ? null : new GWifiDevice(GWifiDeviceList_GetItem, false);
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_GWifiDeviceList(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }
}
