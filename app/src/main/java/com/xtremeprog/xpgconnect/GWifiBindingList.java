package com.xtremeprog.xpgconnect;

public class GWifiBindingList {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public GWifiBindingList() {
        this(GConnectJNI.new_GWifiBindingList(), true);
    }

    protected GWifiBindingList(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(GWifiBindingList gWifiBindingList) {
        return gWifiBindingList == null ? 0 : gWifiBindingList.swigCPtr;
    }

    public long GetCount() {
        return GConnectJNI.GWifiBindingList_GetCount(this.swigCPtr, this);
    }

    public GWifiBinding GetItem(int i) {
        long GWifiBindingList_GetItem = GConnectJNI.GWifiBindingList_GetItem(this.swigCPtr, this, i);
        return GWifiBindingList_GetItem == 0 ? null : new GWifiBinding(GWifiBindingList_GetItem, false);
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_GWifiBindingList(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }
}
