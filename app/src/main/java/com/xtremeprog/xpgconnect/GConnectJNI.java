package com.xtremeprog.xpgconnect;

public class GConnectJNI {
    static {
        try {
            System.loadLibrary("XPGConnect");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("JNI error: " + e);
            System.exit(1);
        }
        swig_module_init();
    }

    public static final native long GWifiBindingList_GetCount(long j, GWifiBindingList gWifiBindingList);

    public static final native long GWifiBindingList_GetItem(long j, GWifiBindingList gWifiBindingList, int i);

    public static final native String GWifiBinding_szDid_get(long j, GWifiBinding gWifiBinding);

    public static final native void GWifiBinding_szDid_set(long j, GWifiBinding gWifiBinding, String str);

    public static final native String GWifiBinding_szMac_get(long j, GWifiBinding gWifiBinding);

    public static final native void GWifiBinding_szMac_set(long j, GWifiBinding gWifiBinding, String str);

    public static final native String GWifiBinding_szPasscode_get(long j, GWifiBinding gWifiBinding);

    public static final native void GWifiBinding_szPasscode_set(long j, GWifiBinding gWifiBinding, String str);

    public static final native boolean GWifiConfig_AddProduct(long j, GWifiConfig gWifiConfig, String str, String str2);

    public static final native void GWifiConfig_ClearSSIDs(long j, GWifiConfig gWifiConfig);

    public static final native void GWifiConfig_DownloadProduct(long j, GWifiConfig gWifiConfig, String str);

    public static final native void GWifiConfig_EnableProductFilter(long j, GWifiConfig gWifiConfig, boolean z);

    public static final native int GWifiConfig_GetAirlinkPort(long j, GWifiConfig gWifiConfig);

    public static final native String GWifiConfig_GetAppID(long j, GWifiConfig gWifiConfig);

    public static final native String GWifiConfig_GetOpenAPIDomain(long j, GWifiConfig gWifiConfig);

    public static final native int GWifiConfig_GetOpenAPIPort(long j, GWifiConfig gWifiConfig);

    public static final native int GWifiConfig_GetOpenAPISSLPort(long j, GWifiConfig gWifiConfig);

    public static final native String GWifiConfig_GetSiteDomain(long j, GWifiConfig gWifiConfig);

    public static final native int GWifiConfig_GetSitePort(long j, GWifiConfig gWifiConfig);

    public static final native int GWifiConfig_GetSoftAPCount(long j, GWifiConfig gWifiConfig);

    public static final native String GWifiConfig_GetSoftAPSSID(long j, GWifiConfig gWifiConfig, int i);

    public static final native int GWifiConfig_GetTCPPort(long j, GWifiConfig gWifiConfig);

    public static final native int GWifiConfig_GetUDPPort(long j, GWifiConfig gWifiConfig);

    public static final native void GWifiConfig_RegisterProductKey(long j, GWifiConfig gWifiConfig, String str);

    public static final native void GWifiConfig_RegisterSSID(long j, GWifiConfig gWifiConfig, String str);

    public static final native void GWifiConfig_SetAppID(long j, GWifiConfig gWifiConfig, String str);

    public static final native void GWifiConfig_SetDebug(long j, GWifiConfig gWifiConfig, boolean z);

    public static final native boolean GWifiConfig_SetProductPath(long j, GWifiConfig gWifiConfig, String str);

    public static final native void GWifiConfig_SetSwitchService(long j, GWifiConfig gWifiConfig, int i);

    public static final native long GWifiConfig_sharedInstance();

    public static final native long GWifiDeviceList_GetCount(long j, GWifiDeviceList gWifiDeviceList);

    public static final native long GWifiDeviceList_GetItem(long j, GWifiDeviceList gWifiDeviceList, int i);

    public static final native void GWifiDeviceListener_change_ownership(GWifiDeviceListener gWifiDeviceListener, long j, boolean z);

    public static final native void GWifiDeviceListener_director_connect(GWifiDeviceListener gWifiDeviceListener, long j, boolean z, boolean z2);

    public static final native void GWifiDeviceListener_onDeviceLog(long j, GWifiDeviceListener gWifiDeviceListener, short s, String str, String str2, String str3, String str4, int i, String str5);

    public static final native void GWifiDeviceListener_onDeviceLogSwigExplicitGWifiDeviceListener(long j, GWifiDeviceListener gWifiDeviceListener, short s, String str, String str2, String str3, String str4, int i, String str5);

    public static final native void GWifiDeviceListener_onDeviceOnline(long j, GWifiDeviceListener gWifiDeviceListener, boolean z);

    public static final native void GWifiDeviceListener_onDeviceOnlineSwigExplicitGWifiDeviceListener(long j, GWifiDeviceListener gWifiDeviceListener, boolean z);

    public static final native void GWifiDeviceListener_onDisconnected(long j, GWifiDeviceListener gWifiDeviceListener);

    public static final native void GWifiDeviceListener_onDisconnectedSwigExplicitGWifiDeviceListener(long j, GWifiDeviceListener gWifiDeviceListener);

    public static final native void GWifiDeviceListener_onDiscovered(long j, GWifiDeviceListener gWifiDeviceListener, int i, long j2, GWifiDeviceList gWifiDeviceList);

    public static final native void GWifiDeviceListener_onDiscoveredSwigExplicitGWifiDeviceListener(long j, GWifiDeviceListener gWifiDeviceListener, int i, long j2, GWifiDeviceList gWifiDeviceList);

    public static final native void GWifiDeviceListener_onLogin(long j, GWifiDeviceListener gWifiDeviceListener, int i);

    public static final native void GWifiDeviceListener_onLoginSwigExplicitGWifiDeviceListener(long j, GWifiDeviceListener gWifiDeviceListener, int i);

    public static final native void GWifiDeviceListener_onQueryHardwareInfo(long j, GWifiDeviceListener gWifiDeviceListener, int i, long j2, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct);

    public static final native void GWifiDeviceListener_onQueryHardwareInfoSwigExplicitGWifiDeviceListener(long j, GWifiDeviceListener gWifiDeviceListener, int i, long j2, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct);

    public static final native void GWifiDeviceListener_onReceiveAlertsAndFaultsInfo(long j, GWifiDeviceListener gWifiDeviceListener, long j2, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo, long j3, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo2, byte[] bArr);

    public static final native void GWifiDeviceListener_onReceiveAlertsAndFaultsInfoSwigExplicitGWifiDeviceListener(long j, GWifiDeviceListener gWifiDeviceListener, long j2, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo, long j3, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo2, byte[] bArr);

    public static final native boolean GWifiDeviceListener_onReceiveData(long j, GWifiDeviceListener gWifiDeviceListener, String str, long j2, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo, long j3, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo2, byte[] bArr, int i);

    public static final native boolean GWifiDeviceListener_onReceiveDataSwigExplicitGWifiDeviceListener(long j, GWifiDeviceListener gWifiDeviceListener, String str, long j2, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo, long j3, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo2, byte[] bArr, int i);

    public static final native void GWifiDeviceListener_onSetSwitcher(long j, GWifiDeviceListener gWifiDeviceListener, int i);

    public static final native void GWifiDeviceListener_onSetSwitcherSwigExplicitGWifiDeviceListener(long j, GWifiDeviceListener gWifiDeviceListener, int i);

    public static final native int GWifiDeviceTypeCenterControl_get();

    public static final native int GWifiDeviceTypeNormal_get();

    public static final native int GWifiDeviceTypeSub_get();

    public static final native void GWifiDevice_BindDevice(long j, GWifiDevice gWifiDevice, String str, String str2, String str3, String str4);

    public static final native boolean GWifiDevice_Connect(long j, GWifiDevice gWifiDevice);

    public static final native boolean GWifiDevice_ConnectToLAN(long j, GWifiDevice gWifiDevice);

    public static final native boolean GWifiDevice_ConnectToMQTT(long j, GWifiDevice gWifiDevice);

    public static final native boolean GWifiDevice_Disconnect(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_GetBindingRemark(long j, GWifiDevice gWifiDevice);

    public static final native int GWifiDevice_GetConnID(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_GetDid(long j, GWifiDevice gWifiDevice);

    public static final native void GWifiDevice_GetHardwareInfo(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_GetIPAddress(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_GetMacAddress(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_GetPasscode(long j, GWifiDevice gWifiDevice);

    public static final native boolean GWifiDevice_GetPasscodeFromDevice(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_GetProductKey(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_GetProductName(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_GetRemark(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_GetUI(long j, GWifiDevice gWifiDevice);

    public static final native boolean GWifiDevice_IsBind(long j, GWifiDevice gWifiDevice, String str);

    public static final native boolean GWifiDevice_IsConnected(long j, GWifiDevice gWifiDevice);

    public static final native boolean GWifiDevice_IsControl(long j, GWifiDevice gWifiDevice);

    public static final native boolean GWifiDevice_IsEqualToDevice(long j, GWifiDevice gWifiDevice, long j2, GWifiDevice gWifiDevice2);

    public static final native boolean GWifiDevice_IsLAN(long j, GWifiDevice gWifiDevice);

    public static final native boolean GWifiDevice_IsOnline(long j, GWifiDevice gWifiDevice);

    public static final native boolean GWifiDevice_IsValidDevice(long j, GWifiDevice gWifiDevice);

    public static final native void GWifiDevice_Login__SWIG_0(long j, GWifiDevice gWifiDevice, String str, String str2);

    public static final native void GWifiDevice_Login__SWIG_1(long j, GWifiDevice gWifiDevice);

    public static final native void GWifiDevice_SetConnID(long j, GWifiDevice gWifiDevice, int i);

    public static final native boolean GWifiDevice_SetLogParam(long j, GWifiDevice gWifiDevice, int i, boolean z);

    public static final native void GWifiDevice_UnbindDevice(long j, GWifiDevice gWifiDevice, String str, String str2, String str3);

    public static final native String GWifiDevice_getAddr(long j, GWifiDevice gWifiDevice);

    public static final native int GWifiDevice_getDevType(long j, GWifiDevice gWifiDevice);

    public static final native boolean GWifiDevice_getDisabled(long j, GWifiDevice gWifiDevice);

    public static final native boolean GWifiDevice_getLogined(long j, GWifiDevice gWifiDevice);

    public static final native boolean GWifiDevice_getLogining(long j, GWifiDevice gWifiDevice);

    public static final native int GWifiDevice_getSubDid(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_getSubProductKey(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_getSubProductName(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_getToken(long j, GWifiDevice gWifiDevice);

    public static final native String GWifiDevice_getUid(long j, GWifiDevice gWifiDevice);

    public static final native long GWifiDevice_listener_get(long j, GWifiDevice gWifiDevice);

    public static final native void GWifiDevice_listener_set(long j, GWifiDevice gWifiDevice, long j2, GWifiDeviceListener gWifiDeviceListener);

    public static final native void GWifiDevice_setAddr(long j, GWifiDevice gWifiDevice, String str);

    public static final native void GWifiDevice_setBindingRemark(long j, GWifiDevice gWifiDevice, String str);

    public static final native void GWifiDevice_setDevType(long j, GWifiDevice gWifiDevice, int i);

    public static final native void GWifiDevice_setDisabled(long j, GWifiDevice gWifiDevice, boolean z);

    public static final native void GWifiDevice_setLogined(long j, GWifiDevice gWifiDevice, boolean z);

    public static final native void GWifiDevice_setLogining(long j, GWifiDevice gWifiDevice, boolean z);

    public static final native void GWifiDevice_setRemark(long j, GWifiDevice gWifiDevice, String str);

    public static final native void GWifiDevice_setSubDid(long j, GWifiDevice gWifiDevice, int i);

    public static final native void GWifiDevice_setSubOnline(long j, GWifiDevice gWifiDevice, int i);

    public static final native void GWifiDevice_setSubProductKey(long j, GWifiDevice gWifiDevice, String str);

    public static final native void GWifiDevice_setToken(long j, GWifiDevice gWifiDevice, String str);

    public static final native void GWifiDevice_setUid(long j, GWifiDevice gWifiDevice, String str);

    public static final native int GWifiDevice_write(long j, GWifiDevice gWifiDevice, String str);

    public static final native int GWifiDevice_write_bytes(long j, GWifiDevice gWifiDevice, byte[] bArr);

    public static final native int GWifiError_CONFIGURE_SENDFAILED_get();

    public static final native int GWifiError_CONFIGURE_TIMEOUT_get();

    public static final native int GWifiError_CONNECTION_CLOSED_get();

    public static final native int GWifiError_CONNECTION_ERROR_get();

    public static final native int GWifiError_CONNECTION_ID_get();

    public static final native int GWifiError_CONNECTION_NO_GATEWAY_get();

    public static final native int GWifiError_CONNECTION_POOL_FULLED_get();

    public static final native int GWifiError_CONNECTION_REFUSED_get();

    public static final native int GWifiError_CONNECT_TIMEOUT_get();

    public static final native int GWifiError_DISCOVERY_MISMATCH_get();

    public static final native int GWifiError_GENERAL_get();

    public static final native int GWifiError_GET_PASSCODE_FAIL_get();

    public static final native int GWifiError_HTTP_FAIL_get();

    public static final native int GWifiError_INSUFFIENT_MEM_get();

    public static final native int GWifiError_INVALID_PARAM_get();

    public static final native int GWifiError_INVALID_VERSION_get();

    public static final native int GWifiError_LOGIN_FAIL_get();

    public static final native int GWifiError_MQTT_FAIL_get();

    public static final native int GWifiError_NONE_get();

    public static final native int GWifiError_NOT_IMPLEMENTED_get();

    public static final native int GWifiError_NOT_IN_SOFTAPMODE_get();

    public static final native int GWifiError_NULL_CLIENT_ID_get();

    public static final native int GWifiError_PACKET_CHECKSUM_get();

    public static final native int GWifiError_PACKET_DATALEN_get();

    public static final native int GWifiError_SET_SOCK_OPT_get();

    public static final native int GWifiError_THREAD_BUSY_get();

    public static final native int GWifiError_THREAD_CREATE_get();

    public static final native int GWifiError_UNRECOGNIZED_DATA_get();

    public static final native int GWifiLogLevelError_get();

    public static final native int GWifiLoginError_CONTROL_ENABLED_get();

    public static final native String GWifiQueryHardwareInfoStruct_firmwareId_get(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct);

    public static final native void GWifiQueryHardwareInfoStruct_firmwareId_set(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct, String str);

    public static final native String GWifiQueryHardwareInfoStruct_firmwareVer_get(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct);

    public static final native void GWifiQueryHardwareInfoStruct_firmwareVer_set(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct, String str);

    public static final native String GWifiQueryHardwareInfoStruct_mcuHardVer_get(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct);

    public static final native void GWifiQueryHardwareInfoStruct_mcuHardVer_set(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct, String str);

    public static final native String GWifiQueryHardwareInfoStruct_mcuSoftVer_get(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct);

    public static final native void GWifiQueryHardwareInfoStruct_mcuSoftVer_set(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct, String str);

    public static final native String GWifiQueryHardwareInfoStruct_p0Ver_get(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct);

    public static final native void GWifiQueryHardwareInfoStruct_p0Ver_set(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct, String str);

    public static final native String GWifiQueryHardwareInfoStruct_productKey_get(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct);

    public static final native void GWifiQueryHardwareInfoStruct_productKey_set(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct, String str);

    public static final native String GWifiQueryHardwareInfoStruct_wifiHardVer_get(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct);

    public static final native void GWifiQueryHardwareInfoStruct_wifiHardVer_set(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct, String str);

    public static final native String GWifiQueryHardwareInfoStruct_wifiSoftVer_get(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct);

    public static final native void GWifiQueryHardwareInfoStruct_wifiSoftVer_set(long j, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct, String str);

    public static final native String GWifiReceiveInfo_name_get(long j, GWifiReceiveInfo gWifiReceiveInfo);

    public static final native void GWifiReceiveInfo_name_set(long j, GWifiReceiveInfo gWifiReceiveInfo, String str);

    public static final native int GWifiReceiveInfo_value_get(long j, GWifiReceiveInfo gWifiReceiveInfo);

    public static final native void GWifiReceiveInfo_value_set(long j, GWifiReceiveInfo gWifiReceiveInfo, int i);

    public static final native void GWifiSDKListener_change_ownership(GWifiSDKListener gWifiSDKListener, long j, boolean z);

    public static final native void GWifiSDKListener_director_connect(GWifiSDKListener gWifiSDKListener, long j, boolean z, boolean z2);

    public static final native void GWifiSDKListener_onBindDevice(long j, GWifiSDKListener gWifiSDKListener, String str, int i, String str2);

    public static final native void GWifiSDKListener_onBindDeviceSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, String str, int i, String str2);

    public static final native long GWifiSDKListener_onCalculateCRC(long j, GWifiSDKListener gWifiSDKListener, byte[] bArr);

    public static final native long GWifiSDKListener_onCalculateCRCSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, byte[] bArr);

    public static final native void GWifiSDKListener_onChangeUserEmail(long j, GWifiSDKListener gWifiSDKListener, int i, String str);

    public static final native void GWifiSDKListener_onChangeUserEmailSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, int i, String str);

    public static final native void GWifiSDKListener_onChangeUserPassword(long j, GWifiSDKListener gWifiSDKListener, int i, String str);

    public static final native void GWifiSDKListener_onChangeUserPasswordSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, int i, String str);

    public static final native void GWifiSDKListener_onChangeUserPhone(long j, GWifiSDKListener gWifiSDKListener, int i, String str);

    public static final native void GWifiSDKListener_onChangeUserPhoneSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, int i, String str);

    public static final native void GWifiSDKListener_onDiscovered(long j, GWifiSDKListener gWifiSDKListener, int i, long j2, GWifiDeviceList gWifiDeviceList);

    public static final native void GWifiSDKListener_onDiscoveredSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, int i, long j2, GWifiDeviceList gWifiDeviceList);

    public static final native void GWifiSDKListener_onGetDeviceHistoryData(long j, GWifiSDKListener gWifiSDKListener, int i, String str, long j2, Vector_deviceData vector_deviceData);

    public static final native void GWifiSDKListener_onGetDeviceHistoryDataSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, int i, String str, long j2, Vector_deviceData vector_deviceData);

    public static final native void GWifiSDKListener_onGetDeviceInfo(long j, GWifiSDKListener gWifiSDKListener, int i, String str, String str2, String str3, String str4, String str5, String str6, int i2, int i3);

    public static final native void GWifiSDKListener_onGetDeviceInfoSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, int i, String str, String str2, String str3, String str4, String str5, String str6, int i2, int i3);

    public static final native void GWifiSDKListener_onGetSSIDList(long j, GWifiSDKListener gWifiSDKListener, long j2, GWifiSSIDList gWifiSSIDList, int i);

    public static final native void GWifiSDKListener_onGetSSIDListSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, long j2, GWifiSSIDList gWifiSSIDList, int i);

    public static final native void GWifiSDKListener_onRegisterUser(long j, GWifiSDKListener gWifiSDKListener, int i, String str, String str2, String str3);

    public static final native void GWifiSDKListener_onRegisterUserSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, int i, String str, String str2, String str3);

    public static final native void GWifiSDKListener_onRequestSendVerifyCode(long j, GWifiSDKListener gWifiSDKListener, int i, String str);

    public static final native void GWifiSDKListener_onRequestSendVerifyCodeSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, int i, String str);

    public static final native void GWifiSDKListener_onSetAirLink(long j, GWifiSDKListener gWifiSDKListener, long j2, GWifiDevice gWifiDevice);

    public static final native void GWifiSDKListener_onSetAirLinkSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, long j2, GWifiDevice gWifiDevice);

    public static final native void GWifiSDKListener_onTransUser(long j, GWifiSDKListener gWifiSDKListener, int i, String str);

    public static final native void GWifiSDKListener_onTransUserSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, int i, String str);

    public static final native void GWifiSDKListener_onUnbindDevice(long j, GWifiSDKListener gWifiSDKListener, String str, int i, String str2);

    public static final native void GWifiSDKListener_onUnbindDeviceSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, String str, int i, String str2);

    public static final native void GWifiSDKListener_onUpdateProduct(long j, GWifiSDKListener gWifiSDKListener, String str, int i);

    public static final native void GWifiSDKListener_onUpdateProductSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, String str, int i);

    public static final native void GWifiSDKListener_onUserLogin(long j, GWifiSDKListener gWifiSDKListener, int i, String str, String str2, String str3);

    public static final native void GWifiSDKListener_onUserLoginSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, int i, String str, String str2, String str3);

    public static final native void GWifiSDKListener_onUserLogout(long j, GWifiSDKListener gWifiSDKListener, int i, String str);

    public static final native void GWifiSDKListener_onUserLogoutSwigExplicitGWifiSDKListener(long j, GWifiSDKListener gWifiSDKListener, int i, String str);

    public static final native void GWifiSDK_BindDevice(long j, GWifiSDK gWifiSDK, String str, String str2, String str3, String str4, String str5);

    public static final native void GWifiSDK_ChangeUserEmail(long j, GWifiSDK gWifiSDK, String str, String str2);

    public static final native void GWifiSDK_ChangeUserPassword(long j, GWifiSDK gWifiSDK, String str, String str2, String str3);

    public static final native void GWifiSDK_ChangeUserPhone(long j, GWifiSDK gWifiSDK, String str, String str2, String str3);

    public static final native void GWifiSDK_GetBoundDevices(long j, GWifiSDK gWifiSDK, String str, String str2);

    public static final native void GWifiSDK_GetDeviceHistoryData(long j, GWifiSDK gWifiSDK, String str, String str2, int i, int i2, int i3, int i4, int i5, int i6);

    public static final native void GWifiSDK_GetDeviceInfo(long j, GWifiSDK gWifiSDK, String str, String str2, String str3);

    public static final native String GWifiSDK_GetLogText();

    public static final native void GWifiSDK_GetSSIDList(long j, GWifiSDK gWifiSDK);

    public static final native void GWifiSDK_RegisterAnonymousUser(long j, GWifiSDK gWifiSDK, String str);

    public static final native void GWifiSDK_RegisterEmailUser(long j, GWifiSDK gWifiSDK, String str, String str2);

    public static final native void GWifiSDK_RegisterNormalUser(long j, GWifiSDK gWifiSDK, String str, String str2);

    public static final native void GWifiSDK_RegisterPhoneUser(long j, GWifiSDK gWifiSDK, String str, String str2, String str3);

    public static final native void GWifiSDK_RegisterThirdAccountUser(long j, GWifiSDK gWifiSDK, int i, String str, String str2);

    public static final native void GWifiSDK_RequestSendVerifyCode(long j, GWifiSDK gWifiSDK, String str);

    public static final native boolean GWifiSDK_SetAirLink(long j, GWifiSDK gWifiSDK, String str, String str2, long j2);

    public static final native void GWifiSDK_SetAppID(long j, GWifiSDK gWifiSDK, String str);

    public static final native void GWifiSDK_SetLogCacheCount(long j);

    public static final native void GWifiSDK_SetLogFile(String str);

    public static final native void GWifiSDK_SetLogLevel(int i);

    public static final native void GWifiSDK_SetPrintDataLevel(boolean z);

    public static final native boolean GWifiSDK_SetSSID(long j, GWifiSDK gWifiSDK, String str, String str2);

    public static final native void GWifiSDK_TransAnonymousUserToNormalUser(long j, GWifiSDK gWifiSDK, String str, String str2, String str3);

    public static final native void GWifiSDK_TransAnonymousUserToPhoneUser(long j, GWifiSDK gWifiSDK, String str, String str2, String str3, String str4);

    public static final native void GWifiSDK_UnbindDevice(long j, GWifiSDK gWifiSDK, String str, String str2, String str3, String str4);

    public static final native void GWifiSDK_UserLogin(long j, GWifiSDK gWifiSDK, String str, String str2);

    public static final native void GWifiSDK_UserLogout(long j, GWifiSDK gWifiSDK, String str);

    public static final native void GWifiSDK_changeUserPasswordWithCode(long j, GWifiSDK gWifiSDK, String str, String str2, String str3);

    public static final native void GWifiSDK_changeUserPasswordWithEmail(long j, GWifiSDK gWifiSDK, String str);

    public static final native String GWifiSDK_getVersion(long j, GWifiSDK gWifiSDK);

    public static final native long GWifiSDK_listener_get(long j, GWifiSDK gWifiSDK);

    public static final native void GWifiSDK_listener_set(long j, GWifiSDK gWifiSDK, long j2, GWifiSDKListener gWifiSDKListener);

    public static final native long GWifiSDK_sharedInstance();

    public static final native long GWifiSSIDList_GetCount(long j, GWifiSSIDList gWifiSSIDList);

    public static final native long GWifiSSIDList_GetItem(long j, GWifiSSIDList gWifiSSIDList, int i);

    public static final native short GWifiSSID_rssi_get(long j, GWifiSSID gWifiSSID);

    public static final native void GWifiSSID_rssi_set(long j, GWifiSSID gWifiSSID, short s);

    public static final native String GWifiSSID_ssid_get(long j, GWifiSSID gWifiSSID);

    public static final native void GWifiSSID_ssid_set(long j, GWifiSSID gWifiSSID, String str);

    public static final native int GWifiThirdAccountTypeBAIDU_get();

    public static final native int GWifiUserTypeANONYMOUS_get();

    public static void SwigDirector_GWifiDeviceListener_onDeviceLog(GWifiDeviceListener gWifiDeviceListener, short s, String str, String str2, String str3, String str4, int i, String str5) {
        gWifiDeviceListener.onDeviceLog(s, str, str2, str3, str4, i, str5);
    }

    public static void SwigDirector_GWifiDeviceListener_onDeviceOnline(GWifiDeviceListener gWifiDeviceListener, boolean z) {
        gWifiDeviceListener.onDeviceOnline(z);
    }

    public static void SwigDirector_GWifiDeviceListener_onDisconnected(GWifiDeviceListener gWifiDeviceListener) {
        gWifiDeviceListener.onDisconnected();
    }

    public static void SwigDirector_GWifiDeviceListener_onDiscovered(GWifiDeviceListener gWifiDeviceListener, int i, long j) {
        gWifiDeviceListener.onDiscovered(i, j == 0 ? null : new GWifiDeviceList(j, false));
    }

    public static void SwigDirector_GWifiDeviceListener_onLogin(GWifiDeviceListener gWifiDeviceListener, int i) {
        gWifiDeviceListener.onLogin(i);
    }

    public static void SwigDirector_GWifiDeviceListener_onQueryHardwareInfo(GWifiDeviceListener gWifiDeviceListener, int i, long j) {
        gWifiDeviceListener.onQueryHardwareInfo(i, j == 0 ? null : new GWifiQueryHardwareInfoStruct(j, false));
    }

    public static void SwigDirector_GWifiDeviceListener_onReceiveAlertsAndFaultsInfo(GWifiDeviceListener gWifiDeviceListener, long j, long j2, byte[] bArr) {
        gWifiDeviceListener.onReceiveAlertsAndFaultsInfo(new Vector_GWifiReceiveInfo(j, false), new Vector_GWifiReceiveInfo(j2, false), bArr);
    }

    public static boolean SwigDirector_GWifiDeviceListener_onReceiveData(GWifiDeviceListener gWifiDeviceListener, String str, long j, long j2, byte[] bArr, int i) {
        return gWifiDeviceListener.onReceiveData(str, new Vector_GWifiReceiveInfo(j, false), new Vector_GWifiReceiveInfo(j2, false), bArr, i);
    }

    public static void SwigDirector_GWifiDeviceListener_onSetSwitcher(GWifiDeviceListener gWifiDeviceListener, int i) {
        gWifiDeviceListener.onSetSwitcher(i);
    }

    public static void SwigDirector_GWifiSDKListener_onBindDevice(GWifiSDKListener gWifiSDKListener, String str, int i, String str2) {
        gWifiSDKListener.onBindDevice(str, i, str2);
    }

    public static long SwigDirector_GWifiSDKListener_onCalculateCRC(GWifiSDKListener gWifiSDKListener, byte[] bArr) {
        return gWifiSDKListener.onCalculateCRC(bArr);
    }

    public static void SwigDirector_GWifiSDKListener_onChangeUserEmail(GWifiSDKListener gWifiSDKListener, int i, String str) {
        gWifiSDKListener.onChangeUserEmail(i, str);
    }

    public static void SwigDirector_GWifiSDKListener_onChangeUserPassword(GWifiSDKListener gWifiSDKListener, int i, String str) {
        gWifiSDKListener.onChangeUserPassword(i, str);
    }

    public static void SwigDirector_GWifiSDKListener_onChangeUserPhone(GWifiSDKListener gWifiSDKListener, int i, String str) {
        gWifiSDKListener.onChangeUserPhone(i, str);
    }

    public static void SwigDirector_GWifiSDKListener_onDiscovered(GWifiSDKListener gWifiSDKListener, int i, long j) {
        gWifiSDKListener.onDiscovered(i, j == 0 ? null : new GWifiDeviceList(j, false));
    }

    public static void SwigDirector_GWifiSDKListener_onGetDeviceHistoryData(GWifiSDKListener gWifiSDKListener, int i, String str, long j) {
        gWifiSDKListener.onGetDeviceHistoryData(i, str, new Vector_deviceData(j, false));
    }

    public static void SwigDirector_GWifiSDKListener_onGetDeviceInfo(GWifiSDKListener gWifiSDKListener, int i, String str, String str2, String str3, String str4, String str5, String str6, int i2, int i3) {
        gWifiSDKListener.onGetDeviceInfo(i, str, str2, str3, str4, str5, str6, i2, i3);
    }

    public static void SwigDirector_GWifiSDKListener_onGetSSIDList(GWifiSDKListener gWifiSDKListener, long j, int i) {
        gWifiSDKListener.onGetSSIDList(j == 0 ? null : new GWifiSSIDList(j, false), i);
    }

    public static void SwigDirector_GWifiSDKListener_onRegisterUser(GWifiSDKListener gWifiSDKListener, int i, String str, String str2, String str3) {
        gWifiSDKListener.onRegisterUser(i, str, str2, str3);
    }

    public static void SwigDirector_GWifiSDKListener_onRequestSendVerifyCode(GWifiSDKListener gWifiSDKListener, int i, String str) {
        gWifiSDKListener.onRequestSendVerifyCode(i, str);
    }

    public static void SwigDirector_GWifiSDKListener_onSetAirLink(GWifiSDKListener gWifiSDKListener, long j) {
        gWifiSDKListener.onSetAirLink(j == 0 ? null : new GWifiDevice(j, false));
    }

    public static void SwigDirector_GWifiSDKListener_onTransUser(GWifiSDKListener gWifiSDKListener, int i, String str) {
        gWifiSDKListener.onTransUser(i, str);
    }

    public static void SwigDirector_GWifiSDKListener_onUnbindDevice(GWifiSDKListener gWifiSDKListener, String str, int i, String str2) {
        gWifiSDKListener.onUnbindDevice(str, i, str2);
    }

    public static void SwigDirector_GWifiSDKListener_onUpdateProduct(GWifiSDKListener gWifiSDKListener, String str, int i) {
        gWifiSDKListener.onUpdateProduct(str, i);
    }

    public static void SwigDirector_GWifiSDKListener_onUserLogin(GWifiSDKListener gWifiSDKListener, int i, String str, String str2, String str3) {
        gWifiSDKListener.onUserLogin(i, str, str2, str3);
    }

    public static void SwigDirector_GWifiSDKListener_onUserLogout(GWifiSDKListener gWifiSDKListener, int i, String str) {
        gWifiSDKListener.onUserLogout(i, str);
    }

    public static final native void Vector_GWifiReceiveInfo_add(long j, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo, long j2, GWifiReceiveInfo gWifiReceiveInfo);

    public static final native long Vector_GWifiReceiveInfo_capacity(long j, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo);

    public static final native void Vector_GWifiReceiveInfo_clear(long j, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo);

    public static final native long Vector_GWifiReceiveInfo_get(long j, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo, int i);

    public static final native boolean Vector_GWifiReceiveInfo_isEmpty(long j, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo);

    public static final native void Vector_GWifiReceiveInfo_reserve(long j, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo, long j2);

    public static final native void Vector_GWifiReceiveInfo_set(long j, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo, int i, long j2, GWifiReceiveInfo gWifiReceiveInfo);

    public static final native long Vector_GWifiReceiveInfo_size(long j, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo);

    public static final native void Vector_deviceData_add(long j, Vector_deviceData vector_deviceData, long j2, deviceData_st com_xtremeprog_xpgconnect_deviceData_st);

    public static final native long Vector_deviceData_capacity(long j, Vector_deviceData vector_deviceData);

    public static final native void Vector_deviceData_clear(long j, Vector_deviceData vector_deviceData);

    public static final native long Vector_deviceData_get(long j, Vector_deviceData vector_deviceData, int i);

    public static final native boolean Vector_deviceData_isEmpty(long j, Vector_deviceData vector_deviceData);

    public static final native void Vector_deviceData_reserve(long j, Vector_deviceData vector_deviceData, long j2);

    public static final native void Vector_deviceData_set(long j, Vector_deviceData vector_deviceData, int i, long j2, deviceData_st com_xtremeprog_xpgconnect_deviceData_st);

    public static final native long Vector_deviceData_size(long j, Vector_deviceData vector_deviceData);

    public static final native void Vector_string_add(long j, Vector_string vector_string, String str);

    public static final native long Vector_string_capacity(long j, Vector_string vector_string);

    public static final native void Vector_string_clear(long j, Vector_string vector_string);

    public static final native String Vector_string_get(long j, Vector_string vector_string, int i);

    public static final native boolean Vector_string_isEmpty(long j, Vector_string vector_string);

    public static final native void Vector_string_reserve(long j, Vector_string vector_string, long j2);

    public static final native void Vector_string_set(long j, Vector_string vector_string, int i, String str);

    public static final native long Vector_string_size(long j, Vector_string vector_string);

    public static final native int XPG_DEVELOPMENT_get();

    public static final native int XPG_PRODUCTION_get();

    public static final native int XPG_QA_get();

    public static final native int XPG_TENCENT_get();

    public static final native void delete_GWifiBinding(long j);

    public static final native void delete_GWifiBindingList(long j);

    public static final native void delete_GWifiConfig(long j);

    public static final native void delete_GWifiDeviceList(long j);

    public static final native void delete_GWifiDeviceListener(long j);

    public static final native void delete_GWifiQueryHardwareInfoStruct(long j);

    public static final native void delete_GWifiReceiveInfo(long j);

    public static final native void delete_GWifiSDKListener(long j);

    public static final native void delete_GWifiSSID(long j);

    public static final native void delete_GWifiSSIDList(long j);

    public static final native void delete_Vector_GWifiReceiveInfo(long j);

    public static final native void delete_Vector_deviceData(long j);

    public static final native void delete_Vector_string(long j);

    public static final native void delete_deviceData_st(long j);

    public static final native int deviceData_st_attr_get(long j, deviceData_st com_xtremeprog_xpgconnect_deviceData_st);

    public static final native void deviceData_st_attr_set(long j, deviceData_st com_xtremeprog_xpgconnect_deviceData_st, int i);

    public static final native int deviceData_st_entity_get(long j, deviceData_st com_xtremeprog_xpgconnect_deviceData_st);

    public static final native void deviceData_st_entity_set(long j, deviceData_st com_xtremeprog_xpgconnect_deviceData_st, int i);

    public static final native int deviceData_st_ts_get(long j, deviceData_st com_xtremeprog_xpgconnect_deviceData_st);

    public static final native void deviceData_st_ts_set(long j, deviceData_st com_xtremeprog_xpgconnect_deviceData_st, int i);

    public static final native int deviceData_st_val_get(long j, deviceData_st com_xtremeprog_xpgconnect_deviceData_st);

    public static final native void deviceData_st_val_set(long j, deviceData_st com_xtremeprog_xpgconnect_deviceData_st, int i);

    public static final native long new_GWifiBinding();

    public static final native long new_GWifiBindingList();

    public static final native long new_GWifiConfig();

    public static final native long new_GWifiDeviceList();

    public static final native long new_GWifiDeviceListener();

    public static final native long new_GWifiQueryHardwareInfoStruct();

    public static final native long new_GWifiReceiveInfo();

    public static final native long new_GWifiSDKListener();

    public static final native long new_GWifiSSID();

    public static final native long new_GWifiSSIDList();

    public static final native long new_Vector_GWifiReceiveInfo__SWIG_0();

    public static final native long new_Vector_GWifiReceiveInfo__SWIG_1(long j);

    public static final native long new_Vector_deviceData__SWIG_0();

    public static final native long new_Vector_deviceData__SWIG_1(long j);

    public static final native long new_Vector_string__SWIG_0();

    public static final native long new_Vector_string__SWIG_1(long j);

    public static final native long new_deviceData_st();

    private static final native void swig_module_init();
}
