package com.xtremeprog.xpgconnect;

public class GWifiDevice {
    protected boolean swigCMemOwn;
    private long swigCPtr;

    protected GWifiDevice(long j, boolean z) {
        this.swigCMemOwn = z;
        this.swigCPtr = j;
    }

    protected static long getCPtr(GWifiDevice gWifiDevice) {
        return gWifiDevice == null ? 0 : gWifiDevice.swigCPtr;
    }

    public void BindDevice(String str, String str2, String str3, String str4) {
        GConnectJNI.GWifiDevice_BindDevice(this.swigCPtr, this, str, str2, str3, str4);
    }

    public boolean Connect() {
        return GConnectJNI.GWifiDevice_Connect(this.swigCPtr, this);
    }

    public boolean ConnectToLAN() {
        return GConnectJNI.GWifiDevice_ConnectToLAN(this.swigCPtr, this);
    }

    public boolean ConnectToMQTT() {
        return GConnectJNI.GWifiDevice_ConnectToMQTT(this.swigCPtr, this);
    }

    public boolean Disconnect() {
        return GConnectJNI.GWifiDevice_Disconnect(this.swigCPtr, this);
    }

    public String GetBindingRemark() {
        return GConnectJNI.GWifiDevice_GetBindingRemark(this.swigCPtr, this);
    }

    public int GetConnID() {
        return GConnectJNI.GWifiDevice_GetConnID(this.swigCPtr, this);
    }

    public String GetDid() {
        return GConnectJNI.GWifiDevice_GetDid(this.swigCPtr, this);
    }

    public void GetHardwareInfo() {
        GConnectJNI.GWifiDevice_GetHardwareInfo(this.swigCPtr, this);
    }

    public String GetIPAddress() {
        return GConnectJNI.GWifiDevice_GetIPAddress(this.swigCPtr, this);
    }

    public String GetMacAddress() {
        return GConnectJNI.GWifiDevice_GetMacAddress(this.swigCPtr, this);
    }

    public String GetPasscode() {
        return GConnectJNI.GWifiDevice_GetPasscode(this.swigCPtr, this);
    }

    public boolean GetPasscodeFromDevice() {
        return GConnectJNI.GWifiDevice_GetPasscodeFromDevice(this.swigCPtr, this);
    }

    public String GetProductKey() {
        return GConnectJNI.GWifiDevice_GetProductKey(this.swigCPtr, this);
    }

    public String GetProductName() {
        return GConnectJNI.GWifiDevice_GetProductName(this.swigCPtr, this);
    }

    public String GetRemark() {
        return GConnectJNI.GWifiDevice_GetRemark(this.swigCPtr, this);
    }

    public String GetUI() {
        return GConnectJNI.GWifiDevice_GetUI(this.swigCPtr, this);
    }

    public boolean IsBind(String str) {
        return GConnectJNI.GWifiDevice_IsBind(this.swigCPtr, this, str);
    }

    public boolean IsConnected() {
        return GConnectJNI.GWifiDevice_IsConnected(this.swigCPtr, this);
    }

    public boolean IsControl() {
        return GConnectJNI.GWifiDevice_IsControl(this.swigCPtr, this);
    }

    public boolean IsEqualToDevice(GWifiDevice gWifiDevice) {
        return GConnectJNI.GWifiDevice_IsEqualToDevice(this.swigCPtr, this, getCPtr(gWifiDevice), gWifiDevice);
    }

    public boolean IsLAN() {
        return GConnectJNI.GWifiDevice_IsLAN(this.swigCPtr, this);
    }

    public boolean IsOnline() {
        return GConnectJNI.GWifiDevice_IsOnline(this.swigCPtr, this);
    }

    public boolean IsValidDevice() {
        return GConnectJNI.GWifiDevice_IsValidDevice(this.swigCPtr, this);
    }

    public void Login() {
        GConnectJNI.GWifiDevice_Login__SWIG_1(this.swigCPtr, this);
    }

    public void Login(String str, String str2) {
        GConnectJNI.GWifiDevice_Login__SWIG_0(this.swigCPtr, this, str, str2);
    }

    public void SetConnID(int i) {
        GConnectJNI.GWifiDevice_SetConnID(this.swigCPtr, this, i);
    }

    public boolean SetLogParam(int i, boolean z) {
        return GConnectJNI.GWifiDevice_SetLogParam(this.swigCPtr, this, i, z);
    }

    public void UnbindDevice(String str, String str2, String str3) {
        GConnectJNI.GWifiDevice_UnbindDevice(this.swigCPtr, this, str, str2, str3);
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

    public String getAddr() {
        return GConnectJNI.GWifiDevice_getAddr(this.swigCPtr, this);
    }

    public int getDevType() {
        return GConnectJNI.GWifiDevice_getDevType(this.swigCPtr, this);
    }

    public boolean getDisabled() {
        return GConnectJNI.GWifiDevice_getDisabled(this.swigCPtr, this);
    }

    public GWifiDeviceListener getListener() {
        long GWifiDevice_listener_get = GConnectJNI.GWifiDevice_listener_get(this.swigCPtr, this);
        return GWifiDevice_listener_get == 0 ? null : new GWifiDeviceListener(GWifiDevice_listener_get, false);
    }

    public boolean getLogined() {
        return GConnectJNI.GWifiDevice_getLogined(this.swigCPtr, this);
    }

    public boolean getLogining() {
        return GConnectJNI.GWifiDevice_getLogining(this.swigCPtr, this);
    }

    public int getSubDid() {
        return GConnectJNI.GWifiDevice_getSubDid(this.swigCPtr, this);
    }

    public String getSubProductKey() {
        return GConnectJNI.GWifiDevice_getSubProductKey(this.swigCPtr, this);
    }

    public String getSubProductName() {
        return GConnectJNI.GWifiDevice_getSubProductName(this.swigCPtr, this);
    }

    public String getToken() {
        return GConnectJNI.GWifiDevice_getToken(this.swigCPtr, this);
    }

    public String getUid() {
        return GConnectJNI.GWifiDevice_getUid(this.swigCPtr, this);
    }

    public void setAddr(String str) {
        GConnectJNI.GWifiDevice_setAddr(this.swigCPtr, this, str);
    }

    public void setBindingRemark(String str) {
        GConnectJNI.GWifiDevice_setBindingRemark(this.swigCPtr, this, str);
    }

    public void setDevType(int i) {
        GConnectJNI.GWifiDevice_setDevType(this.swigCPtr, this, i);
    }

    public void setDisabled(boolean z) {
        GConnectJNI.GWifiDevice_setDisabled(this.swigCPtr, this, z);
    }

    public void setListener(GWifiDeviceListener gWifiDeviceListener) {
        GConnectJNI.GWifiDevice_listener_set(this.swigCPtr, this, GWifiDeviceListener.getCPtr(gWifiDeviceListener), gWifiDeviceListener);
    }

    public void setLogined(boolean z) {
        GConnectJNI.GWifiDevice_setLogined(this.swigCPtr, this, z);
    }

    public void setLogining(boolean z) {
        GConnectJNI.GWifiDevice_setLogining(this.swigCPtr, this, z);
    }

    public void setRemark(String str) {
        GConnectJNI.GWifiDevice_setRemark(this.swigCPtr, this, str);
    }

    public void setSubDid(int i) {
        GConnectJNI.GWifiDevice_setSubDid(this.swigCPtr, this, i);
    }

    public void setSubOnline(int i) {
        GConnectJNI.GWifiDevice_setSubOnline(this.swigCPtr, this, i);
    }

    public void setSubProductKey(String str) {
        GConnectJNI.GWifiDevice_setSubProductKey(this.swigCPtr, this, str);
    }

    public void setToken(String str) {
        GConnectJNI.GWifiDevice_setToken(this.swigCPtr, this, str);
    }

    public void setUid(String str) {
        GConnectJNI.GWifiDevice_setUid(this.swigCPtr, this, str);
    }

    public int write(String str) {
        return GConnectJNI.GWifiDevice_write(this.swigCPtr, this, str);
    }

    public int write_bytes(byte[] bArr) {
        return GConnectJNI.GWifiDevice_write_bytes(this.swigCPtr, this, bArr);
    }
}
