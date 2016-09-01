package com.xtremeprog.xpgconnect;

public class GWifiSDK {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    protected GWifiSDK(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    public static String GetLogText() {
        return GConnectJNI.GWifiSDK_GetLogText();
    }

    public static void SetLogCacheCount(long j) {
        GConnectJNI.GWifiSDK_SetLogCacheCount(j);
    }

    public static void SetLogFile(String str) {
        GConnectJNI.GWifiSDK_SetLogFile(str);
    }

    public static void SetLogLevel(GWifiLogLevel gWifiLogLevel) {
        GConnectJNI.GWifiSDK_SetLogLevel(gWifiLogLevel.swigValue());
    }

    public static void SetPrintDataLevel(boolean z) {
        GConnectJNI.GWifiSDK_SetPrintDataLevel(z);
    }

    protected static long getCPtr(GWifiSDK gWifiSDK) {
        return gWifiSDK == null ? 0 : gWifiSDK.swigCPtr;
    }

    public static GWifiSDK sharedInstance() {
        long GWifiSDK_sharedInstance = GConnectJNI.GWifiSDK_sharedInstance();
        return GWifiSDK_sharedInstance == 0 ? null : new GWifiSDK(GWifiSDK_sharedInstance, false);
    }

    public void BindDevice(String str, String str2, String str3, String str4, String str5) {
        GConnectJNI.GWifiSDK_BindDevice(this.swigCPtr, this, str, str2, str3, str4, str5);
    }

    public void ChangeUserEmail(String str, String str2) {
        GConnectJNI.GWifiSDK_ChangeUserEmail(this.swigCPtr, this, str, str2);
    }

    public void ChangeUserPassword(String str, String str2, String str3) {
        GConnectJNI.GWifiSDK_ChangeUserPassword(this.swigCPtr, this, str, str2, str3);
    }

    public void ChangeUserPhone(String str, String str2, String str3) {
        GConnectJNI.GWifiSDK_ChangeUserPhone(this.swigCPtr, this, str, str2, str3);
    }

    public void GetBoundDevices(String str, String str2) {
        GConnectJNI.GWifiSDK_GetBoundDevices(this.swigCPtr, this, str, str2);
    }

    public void GetDeviceHistoryData(String str, String str2, int i, int i2, int i3, int i4, int i5, int i6) {
        GConnectJNI.GWifiSDK_GetDeviceHistoryData(this.swigCPtr, this, str, str2, i, i2, i3, i4, i5, i6);
    }

    public void GetDeviceInfo(String str, String str2, String str3) {
        GConnectJNI.GWifiSDK_GetDeviceInfo(this.swigCPtr, this, str, str2, str3);
    }

    public void GetSSIDList() {
        GConnectJNI.GWifiSDK_GetSSIDList(this.swigCPtr, this);
    }

    public void RegisterAnonymousUser(String str) {
        GConnectJNI.GWifiSDK_RegisterAnonymousUser(this.swigCPtr, this, str);
    }

    public void RegisterEmailUser(String str, String str2) {
        GConnectJNI.GWifiSDK_RegisterEmailUser(this.swigCPtr, this, str, str2);
    }

    public void RegisterNormalUser(String str, String str2) {
        GConnectJNI.GWifiSDK_RegisterNormalUser(this.swigCPtr, this, str, str2);
    }

    public void RegisterPhoneUser(String str, String str2, String str3) {
        GConnectJNI.GWifiSDK_RegisterPhoneUser(this.swigCPtr, this, str, str2, str3);
    }

    public void RegisterThirdAccountUser(int i, String str, String str2) {
        GConnectJNI.GWifiSDK_RegisterThirdAccountUser(this.swigCPtr, this, i, str, str2);
    }

    public void RequestSendVerifyCode(String str) {
        GConnectJNI.GWifiSDK_RequestSendVerifyCode(this.swigCPtr, this, str);
    }

    public boolean SetAirLink(String str, String str2, long j) {
        return GConnectJNI.GWifiSDK_SetAirLink(this.swigCPtr, this, str, str2, j);
    }

    public void SetAppID(String str) {
        GConnectJNI.GWifiSDK_SetAppID(this.swigCPtr, this, str);
    }

    public boolean SetSSID(String str, String str2) {
        return GConnectJNI.GWifiSDK_SetSSID(this.swigCPtr, this, str, str2);
    }

    public void TransAnonymousUserToNormalUser(String str, String str2, String str3) {
        GConnectJNI.GWifiSDK_TransAnonymousUserToNormalUser(this.swigCPtr, this, str, str2, str3);
    }

    public void TransAnonymousUserToPhoneUser(String str, String str2, String str3, String str4) {
        GConnectJNI.GWifiSDK_TransAnonymousUserToPhoneUser(this.swigCPtr, this, str, str2, str3, str4);
    }

    public void UnbindDevice(String str, String str2, String str3, String str4) {
        GConnectJNI.GWifiSDK_UnbindDevice(this.swigCPtr, this, str, str2, str3, str4);
    }

    public void UserLogin(String str, String str2) {
        GConnectJNI.GWifiSDK_UserLogin(this.swigCPtr, this, str, str2);
    }

    public void UserLogout(String str) {
        GConnectJNI.GWifiSDK_UserLogout(this.swigCPtr, this, str);
    }

    public void changeUserPasswordWithCode(String str, String str2, String str3) {
        GConnectJNI.GWifiSDK_changeUserPasswordWithCode(this.swigCPtr, this, str, str2, str3);
    }

    public void changeUserPasswordWithEmail(String str) {
        GConnectJNI.GWifiSDK_changeUserPasswordWithEmail(this.swigCPtr, this, str);
    }

    public void delete() {
        synchronized (this) {
            if (this.swigCPtr != 0) {
                if (this.swigCMemOwn) {
                    this.swigCMemOwn = false;
                    throw new UnsupportedOperationException("C++ destructor does not have public access");
                }
                this.swigCPtr = 0;
            }
        }
    }

    public GWifiSDKListener getListener() {
        long GWifiSDK_listener_get = GConnectJNI.GWifiSDK_listener_get(this.swigCPtr, this);
        return GWifiSDK_listener_get == 0 ? null : new GWifiSDKListener(GWifiSDK_listener_get, false);
    }

    public String getVersion() {
        return GConnectJNI.GWifiSDK_getVersion(this.swigCPtr, this);
    }

    public void setListener(GWifiSDKListener gWifiSDKListener) {
        GConnectJNI.GWifiSDK_listener_set(this.swigCPtr, this, GWifiSDKListener.getCPtr(gWifiSDKListener), gWifiSDKListener);
    }
}
