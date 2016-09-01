package com.xtremeprog.xpgconnect;

public class Vector_deviceData {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public Vector_deviceData() {
        this(GConnectJNI.new_Vector_deviceData__SWIG_0(), true);
    }

    public Vector_deviceData(long j) {
        this(GConnectJNI.new_Vector_deviceData__SWIG_1(j), true);
    }

    protected Vector_deviceData(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(Vector_deviceData vector_deviceData) {
        return vector_deviceData == null ? 0 : vector_deviceData.swigCPtr;
    }

    public void add(deviceData_st com_xtremeprog_xpgconnect_deviceData_st) {
        GConnectJNI.Vector_deviceData_add(this.swigCPtr, this, deviceData_st.getCPtr(com_xtremeprog_xpgconnect_deviceData_st), com_xtremeprog_xpgconnect_deviceData_st);
    }

    public long capacity() {
        return GConnectJNI.Vector_deviceData_capacity(this.swigCPtr, this);
    }

    public void clear() {
        GConnectJNI.Vector_deviceData_clear(this.swigCPtr, this);
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_Vector_deviceData(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }

    public deviceData_st get(int i) {
        return new deviceData_st(GConnectJNI.Vector_deviceData_get(this.swigCPtr, this, i), false);
    }

    public boolean isEmpty() {
        return GConnectJNI.Vector_deviceData_isEmpty(this.swigCPtr, this);
    }

    public void reserve(long j) {
        GConnectJNI.Vector_deviceData_reserve(this.swigCPtr, this, j);
    }

    public void set(int i, deviceData_st com_xtremeprog_xpgconnect_deviceData_st) {
        GConnectJNI.Vector_deviceData_set(this.swigCPtr, this, i, deviceData_st.getCPtr(com_xtremeprog_xpgconnect_deviceData_st), com_xtremeprog_xpgconnect_deviceData_st);
    }

    public long size() {
        return GConnectJNI.Vector_deviceData_size(this.swigCPtr, this);
    }
}
