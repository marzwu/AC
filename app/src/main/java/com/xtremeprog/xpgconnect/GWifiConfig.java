package com.xtremeprog.xpgconnect;

public class GWifiConfig {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public GWifiConfig() {
        this(GConnectJNI.new_GWifiConfig(), true);
    }

    protected GWifiConfig(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(GWifiConfig gWifiConfig) {
        return gWifiConfig == null ? 0 : gWifiConfig.swigCPtr;
    }

    public static GWifiConfig sharedInstance() {
        long GWifiConfig_sharedInstance = GConnectJNI.GWifiConfig_sharedInstance();
        return GWifiConfig_sharedInstance == 0 ? null : new GWifiConfig(GWifiConfig_sharedInstance, false);
    }

    public boolean AddProduct(String str, String str2) {
        return GConnectJNI.GWifiConfig_AddProduct(this.swigCPtr, this, str, str2);
    }

    public void ClearSSIDs() {
        GConnectJNI.GWifiConfig_ClearSSIDs(this.swigCPtr, this);
    }

    public void DownloadProduct(String str) {
        GConnectJNI.GWifiConfig_DownloadProduct(this.swigCPtr, this, str);
    }

    public void EnableProductFilter(boolean z) {
        GConnectJNI.GWifiConfig_EnableProductFilter(this.swigCPtr, this, z);
    }

    public int GetAirlinkPort() {
        return GConnectJNI.GWifiConfig_GetAirlinkPort(this.swigCPtr, this);
    }

    public String GetAppID() {
        return GConnectJNI.GWifiConfig_GetAppID(this.swigCPtr, this);
    }

    public String GetOpenAPIDomain() {
        return GConnectJNI.GWifiConfig_GetOpenAPIDomain(this.swigCPtr, this);
    }

    public int GetOpenAPIPort() {
        return GConnectJNI.GWifiConfig_GetOpenAPIPort(this.swigCPtr, this);
    }

    public int GetOpenAPISSLPort() {
        return GConnectJNI.GWifiConfig_GetOpenAPISSLPort(this.swigCPtr, this);
    }

    public String GetSiteDomain() {
        return GConnectJNI.GWifiConfig_GetSiteDomain(this.swigCPtr, this);
    }

    public int GetSitePort() {
        return GConnectJNI.GWifiConfig_GetSitePort(this.swigCPtr, this);
    }

    public int GetSoftAPCount() {
        return GConnectJNI.GWifiConfig_GetSoftAPCount(this.swigCPtr, this);
    }

    public String GetSoftAPSSID(int i) {
        return GConnectJNI.GWifiConfig_GetSoftAPSSID(this.swigCPtr, this, i);
    }

    public int GetTCPPort() {
        return GConnectJNI.GWifiConfig_GetTCPPort(this.swigCPtr, this);
    }

    public int GetUDPPort() {
        return GConnectJNI.GWifiConfig_GetUDPPort(this.swigCPtr, this);
    }

    public void RegisterProductKey(String str) {
        GConnectJNI.GWifiConfig_RegisterProductKey(this.swigCPtr, this, str);
    }

    public void RegisterSSID(String str) {
        GConnectJNI.GWifiConfig_RegisterSSID(this.swigCPtr, this, str);
    }

    public void SetAppID(String str) {
        GConnectJNI.GWifiConfig_SetAppID(this.swigCPtr, this, str);
    }

    public void SetDebug(boolean z) {
        GConnectJNI.GWifiConfig_SetDebug(this.swigCPtr, this, z);
    }

    public boolean SetProductPath(String str) {
        return GConnectJNI.GWifiConfig_SetProductPath(this.swigCPtr, this, str);
    }

    public void SetSwitchService(int i) {
        GConnectJNI.GWifiConfig_SetSwitchService(this.swigCPtr, this, i);
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_GWifiConfig(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }
}
