package com.xtremeprog.xpgconnect;

public class GWifiSDKListener {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    public GWifiSDKListener() {
        this(GConnectJNI.new_GWifiSDKListener(), true);
        GConnectJNI.GWifiSDKListener_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    protected GWifiSDKListener(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(GWifiSDKListener gWifiSDKListener) {
        return gWifiSDKListener == null ? 0 : gWifiSDKListener.swigCPtr;
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    GConnectJNI.delete_GWifiSDKListener(this.swigCPtr);
                }
                this.swigCPtr = 0;
            }
        }
    }

    protected void finalize() {
        delete();
    }

    public void onBindDevice(String str, int i, String str2) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onBindDevice(this.swigCPtr, this, str, i, str2);
        } else {
            GConnectJNI.GWifiSDKListener_onBindDeviceSwigExplicitGWifiSDKListener(this.swigCPtr, this, str, i, str2);
        }
    }

    public long onCalculateCRC(byte[] bArr) {
        return getClass() == GWifiSDKListener.class ? GConnectJNI.GWifiSDKListener_onCalculateCRC(this.swigCPtr, this, bArr) : GConnectJNI.GWifiSDKListener_onCalculateCRCSwigExplicitGWifiSDKListener(this.swigCPtr, this, bArr);
    }

    public void onChangeUserEmail(int i, String str) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onChangeUserEmail(this.swigCPtr, this, i, str);
        } else {
            GConnectJNI.GWifiSDKListener_onChangeUserEmailSwigExplicitGWifiSDKListener(this.swigCPtr, this, i, str);
        }
    }

    public void onChangeUserPassword(int i, String str) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onChangeUserPassword(this.swigCPtr, this, i, str);
        } else {
            GConnectJNI.GWifiSDKListener_onChangeUserPasswordSwigExplicitGWifiSDKListener(this.swigCPtr, this, i, str);
        }
    }

    public void onChangeUserPhone(int i, String str) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onChangeUserPhone(this.swigCPtr, this, i, str);
        } else {
            GConnectJNI.GWifiSDKListener_onChangeUserPhoneSwigExplicitGWifiSDKListener(this.swigCPtr, this, i, str);
        }
    }

    public void onDiscovered(int i, GWifiDeviceList gWifiDeviceList) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onDiscovered(this.swigCPtr, this, i, GWifiDeviceList.getCPtr(gWifiDeviceList), gWifiDeviceList);
            return;
        }
        GConnectJNI.GWifiSDKListener_onDiscoveredSwigExplicitGWifiSDKListener(this.swigCPtr, this, i, GWifiDeviceList.getCPtr(gWifiDeviceList), gWifiDeviceList);
    }

    public void onGetDeviceHistoryData(int i, String str, Vector_deviceData vector_deviceData) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onGetDeviceHistoryData(this.swigCPtr, this, i, str, Vector_deviceData.getCPtr(vector_deviceData), vector_deviceData);
            return;
        }
        GConnectJNI.GWifiSDKListener_onGetDeviceHistoryDataSwigExplicitGWifiSDKListener(this.swigCPtr, this, i, str, Vector_deviceData.getCPtr(vector_deviceData), vector_deviceData);
    }

    public void onGetDeviceInfo(int i, String str, String str2, String str3, String str4, String str5, String str6, int i2, int i3) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onGetDeviceInfo(this.swigCPtr, this, i, str, str2, str3, str4, str5, str6, i2, i3);
        } else {
            GConnectJNI.GWifiSDKListener_onGetDeviceInfoSwigExplicitGWifiSDKListener(this.swigCPtr, this, i, str, str2, str3, str4, str5, str6, i2, i3);
        }
    }

    public void onGetSSIDList(GWifiSSIDList gWifiSSIDList, int i) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onGetSSIDList(this.swigCPtr, this, GWifiSSIDList.getCPtr(gWifiSSIDList), gWifiSSIDList, i);
        } else {
            GConnectJNI.GWifiSDKListener_onGetSSIDListSwigExplicitGWifiSDKListener(this.swigCPtr, this, GWifiSSIDList.getCPtr(gWifiSSIDList), gWifiSSIDList, i);
        }
    }

    public void onRegisterUser(int i, String str, String str2, String str3) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onRegisterUser(this.swigCPtr, this, i, str, str2, str3);
        } else {
            GConnectJNI.GWifiSDKListener_onRegisterUserSwigExplicitGWifiSDKListener(this.swigCPtr, this, i, str, str2, str3);
        }
    }

    public void onRequestSendVerifyCode(int i, String str) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onRequestSendVerifyCode(this.swigCPtr, this, i, str);
        } else {
            GConnectJNI.GWifiSDKListener_onRequestSendVerifyCodeSwigExplicitGWifiSDKListener(this.swigCPtr, this, i, str);
        }
    }

    public void onSetAirLink(GWifiDevice gWifiDevice) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onSetAirLink(this.swigCPtr, this, GWifiDevice.getCPtr(gWifiDevice), gWifiDevice);
        } else {
            GConnectJNI.GWifiSDKListener_onSetAirLinkSwigExplicitGWifiSDKListener(this.swigCPtr, this, GWifiDevice.getCPtr(gWifiDevice), gWifiDevice);
        }
    }

    public void onTransUser(int i, String str) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onTransUser(this.swigCPtr, this, i, str);
        } else {
            GConnectJNI.GWifiSDKListener_onTransUserSwigExplicitGWifiSDKListener(this.swigCPtr, this, i, str);
        }
    }

    public void onUnbindDevice(String str, int i, String str2) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onUnbindDevice(this.swigCPtr, this, str, i, str2);
        } else {
            GConnectJNI.GWifiSDKListener_onUnbindDeviceSwigExplicitGWifiSDKListener(this.swigCPtr, this, str, i, str2);
        }
    }

    public void onUpdateProduct(String str, int i) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onUpdateProduct(this.swigCPtr, this, str, i);
        } else {
            GConnectJNI.GWifiSDKListener_onUpdateProductSwigExplicitGWifiSDKListener(this.swigCPtr, this, str, i);
        }
    }

    public void onUserLogin(int i, String str, String str2, String str3) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onUserLogin(this.swigCPtr, this, i, str, str2, str3);
        } else {
            GConnectJNI.GWifiSDKListener_onUserLoginSwigExplicitGWifiSDKListener(this.swigCPtr, this, i, str, str2, str3);
        }
    }

    public void onUserLogout(int i, String str) {
        if (getClass() == GWifiSDKListener.class) {
            GConnectJNI.GWifiSDKListener_onUserLogout(this.swigCPtr, this, i, str);
        } else {
            GConnectJNI.GWifiSDKListener_onUserLogoutSwigExplicitGWifiSDKListener(this.swigCPtr, this, i, str);
        }
    }

    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        GConnectJNI.GWifiSDKListener_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        GConnectJNI.GWifiSDKListener_change_ownership(this, this.swigCPtr, true);
    }
}
