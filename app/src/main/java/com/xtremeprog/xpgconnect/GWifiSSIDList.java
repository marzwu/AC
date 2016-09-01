package com.xtremeprog.xpgconnect;

public class GWifiSSIDList {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public GWifiSSIDList() {
        this(GConnectJNI.new_GWifiSSIDList(), true);
    }

    protected GWifiSSIDList(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(GWifiSSIDList gWifiSSIDList) {
        return gWifiSSIDList == null ? 0 : gWifiSSIDList.swigCPtr;
    }

    public long GetCount() {
        return GConnectJNI.GWifiSSIDList_GetCount(this.swigCPtr, this);
    }

    public GWifiSSID GetItem(int i) {
        long GWifiSSIDList_GetItem = GConnectJNI.GWifiSSIDList_GetItem(this.swigCPtr, this, i);
        return GWifiSSIDList_GetItem == 0 ? null : new GWifiSSID(GWifiSSIDList_GetItem, false);
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_GWifiSSIDList(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }
}
