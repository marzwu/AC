package com.xtremeprog.xpgconnect;

public class deviceData_st {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public deviceData_st() {
        this(GConnectJNI.new_deviceData_st(), true);
    }

    protected deviceData_st(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(deviceData_st com_xtremeprog_xpgconnect_deviceData_st) {
        return com_xtremeprog_xpgconnect_deviceData_st == null ? 0 : com_xtremeprog_xpgconnect_deviceData_st.swigCPtr;
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_deviceData_st(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }

    public int getAttr() {
        return GConnectJNI.deviceData_st_attr_get(this.swigCPtr, this);
    }

    public int getEntity() {
        return GConnectJNI.deviceData_st_entity_get(this.swigCPtr, this);
    }

    public int getTs() {
        return GConnectJNI.deviceData_st_ts_get(this.swigCPtr, this);
    }

    public int getVal() {
        return GConnectJNI.deviceData_st_val_get(this.swigCPtr, this);
    }

    public void setAttr(int i) {
        GConnectJNI.deviceData_st_attr_set(this.swigCPtr, this, i);
    }

    public void setEntity(int i) {
        GConnectJNI.deviceData_st_entity_set(this.swigCPtr, this, i);
    }

    public void setTs(int i) {
        GConnectJNI.deviceData_st_ts_set(this.swigCPtr, this, i);
    }

    public void setVal(int i) {
        GConnectJNI.deviceData_st_val_set(this.swigCPtr, this, i);
    }
}
