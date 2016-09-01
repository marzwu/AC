package com.xtremeprog.xpgconnect;

public class Vector_string {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public Vector_string() {
        this(GConnectJNI.new_Vector_string__SWIG_0(), true);
    }

    public Vector_string(long j) {
        this(GConnectJNI.new_Vector_string__SWIG_1(j), true);
    }

    protected Vector_string(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(Vector_string vector_string) {
        return vector_string == null ? 0 : vector_string.swigCPtr;
    }

    public void add(String str) {
        GConnectJNI.Vector_string_add(this.swigCPtr, this, str);
    }

    public long capacity() {
        return GConnectJNI.Vector_string_capacity(this.swigCPtr, this);
    }

    public void clear() {
        GConnectJNI.Vector_string_clear(this.swigCPtr, this);
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_Vector_string(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }

    public String get(int i) {
        return GConnectJNI.Vector_string_get(this.swigCPtr, this, i);
    }

    public boolean isEmpty() {
        return GConnectJNI.Vector_string_isEmpty(this.swigCPtr, this);
    }

    public void reserve(long j) {
        GConnectJNI.Vector_string_reserve(this.swigCPtr, this, j);
    }

    public void set(int i, String str) {
        GConnectJNI.Vector_string_set(this.swigCPtr, this, i, str);
    }

    public long size() {
        return GConnectJNI.Vector_string_size(this.swigCPtr, this);
    }
}
