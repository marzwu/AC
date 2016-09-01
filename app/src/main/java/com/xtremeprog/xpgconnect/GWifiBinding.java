package com.xtremeprog.xpgconnect;

public class GWifiBinding {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public GWifiBinding() {
        this(GConnectJNI.new_GWifiBinding(), true);
    }

    protected GWifiBinding(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(GWifiBinding gWifiBinding) {
        return gWifiBinding == null ? 0 : gWifiBinding.swigCPtr;
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_GWifiBinding(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }

    public String getSzDid() {
        return GConnectJNI.GWifiBinding_szDid_get(this.swigCPtr, this);
    }

    public String getSzMac() {
        return GConnectJNI.GWifiBinding_szMac_get(this.swigCPtr, this);
    }

    public String getSzPasscode() {
        return GConnectJNI.GWifiBinding_szPasscode_get(this.swigCPtr, this);
    }

    public void setSzDid(String str) {
        GConnectJNI.GWifiBinding_szDid_set(this.swigCPtr, this, str);
    }

    public void setSzMac(String str) {
        GConnectJNI.GWifiBinding_szMac_set(this.swigCPtr, this, str);
    }

    public void setSzPasscode(String str) {
        GConnectJNI.GWifiBinding_szPasscode_set(this.swigCPtr, this, str);
    }
}
