package com.xtremeprog.xpgconnect;

public class GWifiDeviceListener {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public GWifiDeviceListener() {
        this(GConnectJNI.new_GWifiDeviceListener(), true);
        GConnectJNI.GWifiDeviceListener_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    protected GWifiDeviceListener(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(GWifiDeviceListener gWifiDeviceListener) {
        return gWifiDeviceListener == null ? 0 : gWifiDeviceListener.swigCPtr;
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_GWifiDeviceListener(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }

    public void onDeviceLog(short s, String str, String str2, String str3, String str4, int i, String str5) {
        if (getClass() == GWifiDeviceListener.class) {
            GConnectJNI.GWifiDeviceListener_onDeviceLog(this.swigCPtr, this, s, str, str2, str3, str4, i, str5);
        } else {
            GConnectJNI.GWifiDeviceListener_onDeviceLogSwigExplicitGWifiDeviceListener(this.swigCPtr, this, s, str, str2, str3, str4, i, str5);
        }
    }

    public void onDeviceOnline(boolean z) {
        if (getClass() == GWifiDeviceListener.class) {
            GConnectJNI.GWifiDeviceListener_onDeviceOnline(this.swigCPtr, this, z);
        } else {
            GConnectJNI.GWifiDeviceListener_onDeviceOnlineSwigExplicitGWifiDeviceListener(this.swigCPtr, this, z);
        }
    }

    public void onDisconnected() {
        if (getClass() == GWifiDeviceListener.class) {
            GConnectJNI.GWifiDeviceListener_onDisconnected(this.swigCPtr, this);
        } else {
            GConnectJNI.GWifiDeviceListener_onDisconnectedSwigExplicitGWifiDeviceListener(this.swigCPtr, this);
        }
    }

    public void onDiscovered(int i, GWifiDeviceList gWifiDeviceList) {
        if (getClass() == GWifiDeviceListener.class) {
            GConnectJNI.GWifiDeviceListener_onDiscovered(this.swigCPtr, this, i, GWifiDeviceList.getCPtr(gWifiDeviceList), gWifiDeviceList);
            return;
        }
        GConnectJNI.GWifiDeviceListener_onDiscoveredSwigExplicitGWifiDeviceListener(this.swigCPtr, this, i, GWifiDeviceList.getCPtr(gWifiDeviceList), gWifiDeviceList);
    }

    public void onLogin(int i) {
        if (getClass() == GWifiDeviceListener.class) {
            GConnectJNI.GWifiDeviceListener_onLogin(this.swigCPtr, this, i);
        } else {
            GConnectJNI.GWifiDeviceListener_onLoginSwigExplicitGWifiDeviceListener(this.swigCPtr, this, i);
        }
    }

    public void onQueryHardwareInfo(int i, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct) {
        if (getClass() == GWifiDeviceListener.class) {
            GConnectJNI.GWifiDeviceListener_onQueryHardwareInfo(this.swigCPtr, this, i, GWifiQueryHardwareInfoStruct.getCPtr(gWifiQueryHardwareInfoStruct), gWifiQueryHardwareInfoStruct);
            return;
        }
        GConnectJNI.GWifiDeviceListener_onQueryHardwareInfoSwigExplicitGWifiDeviceListener(this.swigCPtr, this, i, GWifiQueryHardwareInfoStruct.getCPtr(gWifiQueryHardwareInfoStruct), gWifiQueryHardwareInfoStruct);
    }

    public void onReceiveAlertsAndFaultsInfo(Vector_GWifiReceiveInfo vector_GWifiReceiveInfo, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo2, byte[] bArr) {
        if (getClass() == GWifiDeviceListener.class) {
            GConnectJNI.GWifiDeviceListener_onReceiveAlertsAndFaultsInfo(this.swigCPtr, this, Vector_GWifiReceiveInfo.getCPtr(vector_GWifiReceiveInfo), vector_GWifiReceiveInfo, Vector_GWifiReceiveInfo.getCPtr(vector_GWifiReceiveInfo2), vector_GWifiReceiveInfo2, bArr);
            return;
        }
        GConnectJNI.GWifiDeviceListener_onReceiveAlertsAndFaultsInfoSwigExplicitGWifiDeviceListener(this.swigCPtr, this, Vector_GWifiReceiveInfo.getCPtr(vector_GWifiReceiveInfo), vector_GWifiReceiveInfo, Vector_GWifiReceiveInfo.getCPtr(vector_GWifiReceiveInfo2), vector_GWifiReceiveInfo2, bArr);
    }

    public boolean onReceiveData(String str, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo2, byte[] bArr, int i) {
        if (getClass() == GWifiDeviceListener.class) {
            return GConnectJNI.GWifiDeviceListener_onReceiveData(this.swigCPtr, this, str, Vector_GWifiReceiveInfo.getCPtr(vector_GWifiReceiveInfo), vector_GWifiReceiveInfo, Vector_GWifiReceiveInfo.getCPtr(vector_GWifiReceiveInfo2), vector_GWifiReceiveInfo2, bArr, i);
        }
        return GConnectJNI.GWifiDeviceListener_onReceiveDataSwigExplicitGWifiDeviceListener(this.swigCPtr, this, str, Vector_GWifiReceiveInfo.getCPtr(vector_GWifiReceiveInfo), vector_GWifiReceiveInfo, Vector_GWifiReceiveInfo.getCPtr(vector_GWifiReceiveInfo2), vector_GWifiReceiveInfo2, bArr, i);
    }

    public void onSetSwitcher(int i) {
        if (getClass() == GWifiDeviceListener.class) {
            GConnectJNI.GWifiDeviceListener_onSetSwitcher(this.swigCPtr, this, i);
        } else {
            GConnectJNI.GWifiDeviceListener_onSetSwitcherSwigExplicitGWifiDeviceListener(this.swigCPtr, this, i);
        }
    }

    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        GConnectJNI.GWifiDeviceListener_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        GConnectJNI.GWifiDeviceListener_change_ownership(this, this.swigCPtr, true);
    }
}
