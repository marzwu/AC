package com.xtremeprog.xpgconnect;

public class Vector_GWifiReceiveInfo {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public Vector_GWifiReceiveInfo() {
        this(GConnectJNI.new_Vector_GWifiReceiveInfo__SWIG_0(), true);
    }

    public Vector_GWifiReceiveInfo(long j) {
        this(GConnectJNI.new_Vector_GWifiReceiveInfo__SWIG_1(j), true);
    }

    protected Vector_GWifiReceiveInfo(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(Vector_GWifiReceiveInfo vector_GWifiReceiveInfo) {
        return vector_GWifiReceiveInfo == null ? 0 : vector_GWifiReceiveInfo.swigCPtr;
    }

    public void add(GWifiReceiveInfo gWifiReceiveInfo) {
        GConnectJNI.Vector_GWifiReceiveInfo_add(this.swigCPtr, this, GWifiReceiveInfo.getCPtr(gWifiReceiveInfo), gWifiReceiveInfo);
    }

    public long capacity() {
        return GConnectJNI.Vector_GWifiReceiveInfo_capacity(this.swigCPtr, this);
    }

    public void clear() {
        GConnectJNI.Vector_GWifiReceiveInfo_clear(this.swigCPtr, this);
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_Vector_GWifiReceiveInfo(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }

    public GWifiReceiveInfo get(int i) {
        return new GWifiReceiveInfo(GConnectJNI.Vector_GWifiReceiveInfo_get(this.swigCPtr, this, i), false);
    }

    public boolean isEmpty() {
        return GConnectJNI.Vector_GWifiReceiveInfo_isEmpty(this.swigCPtr, this);
    }

    public void reserve(long j) {
        GConnectJNI.Vector_GWifiReceiveInfo_reserve(this.swigCPtr, this, j);
    }

    public void set(int i, GWifiReceiveInfo gWifiReceiveInfo) {
        GConnectJNI.Vector_GWifiReceiveInfo_set(this.swigCPtr, this, i, GWifiReceiveInfo.getCPtr(gWifiReceiveInfo), gWifiReceiveInfo);
    }

    public long size() {
        return GConnectJNI.Vector_GWifiReceiveInfo_size(this.swigCPtr, this);
    }
}
