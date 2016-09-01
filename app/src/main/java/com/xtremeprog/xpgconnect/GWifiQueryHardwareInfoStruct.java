package com.xtremeprog.xpgconnect;

public class GWifiQueryHardwareInfoStruct {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public GWifiQueryHardwareInfoStruct() {
        this(GConnectJNI.new_GWifiQueryHardwareInfoStruct(), true);
    }

    protected GWifiQueryHardwareInfoStruct(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct) {
        return gWifiQueryHardwareInfoStruct == null ? 0 : gWifiQueryHardwareInfoStruct.swigCPtr;
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_GWifiQueryHardwareInfoStruct(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }

    public String getFirmwareId() {
        return GConnectJNI.GWifiQueryHardwareInfoStruct_firmwareId_get(this.swigCPtr, this);
    }

    public String getFirmwareVer() {
        return GConnectJNI.GWifiQueryHardwareInfoStruct_firmwareVer_get(this.swigCPtr, this);
    }

    public String getMcuHardVer() {
        return GConnectJNI.GWifiQueryHardwareInfoStruct_mcuHardVer_get(this.swigCPtr, this);
    }

    public String getMcuSoftVer() {
        return GConnectJNI.GWifiQueryHardwareInfoStruct_mcuSoftVer_get(this.swigCPtr, this);
    }

    public String getP0Ver() {
        return GConnectJNI.GWifiQueryHardwareInfoStruct_p0Ver_get(this.swigCPtr, this);
    }

    public String getProductKey() {
        return GConnectJNI.GWifiQueryHardwareInfoStruct_productKey_get(this.swigCPtr, this);
    }

    public String getWifiHardVer() {
        return GConnectJNI.GWifiQueryHardwareInfoStruct_wifiHardVer_get(this.swigCPtr, this);
    }

    public String getWifiSoftVer() {
        return GConnectJNI.GWifiQueryHardwareInfoStruct_wifiSoftVer_get(this.swigCPtr, this);
    }

    public void setFirmwareId(String str) {
        GConnectJNI.GWifiQueryHardwareInfoStruct_firmwareId_set(this.swigCPtr, this, str);
    }

    public void setFirmwareVer(String str) {
        GConnectJNI.GWifiQueryHardwareInfoStruct_firmwareVer_set(this.swigCPtr, this, str);
    }

    public void setMcuHardVer(String str) {
        GConnectJNI.GWifiQueryHardwareInfoStruct_mcuHardVer_set(this.swigCPtr, this, str);
    }

    public void setMcuSoftVer(String str) {
        GConnectJNI.GWifiQueryHardwareInfoStruct_mcuSoftVer_set(this.swigCPtr, this, str);
    }

    public void setP0Ver(String str) {
        GConnectJNI.GWifiQueryHardwareInfoStruct_p0Ver_set(this.swigCPtr, this, str);
    }

    public void setProductKey(String str) {
        GConnectJNI.GWifiQueryHardwareInfoStruct_productKey_set(this.swigCPtr, this, str);
    }

    public void setWifiHardVer(String str) {
        GConnectJNI.GWifiQueryHardwareInfoStruct_wifiHardVer_set(this.swigCPtr, this, str);
    }

    public void setWifiSoftVer(String str) {
        GConnectJNI.GWifiQueryHardwareInfoStruct_wifiSoftVer_set(this.swigCPtr, this, str);
    }
}
