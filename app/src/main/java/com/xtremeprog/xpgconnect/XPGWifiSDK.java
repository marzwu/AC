package com.xtremeprog.xpgconnect;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.util.Log;

import com.xtremeprog.xpgconnect.SmartLinkManipulator.ConnectCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class XPGWifiSDK {
    protected static Context mContext;
    private static XPGWifiSDK mInstance = new XPGWifiSDK();
    private static XPGWifiSDKListener mListener;
    private static GWifiSDKListener sdkListener = new GWifiSDKListener() {
        public void onBindDevice(String str, int i, String str2) {
            Log.i("XPGWifiSDK", "Success callback didBindDevice, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                XPGWifiSDK.mListener.didBindDevice(i, str2, str);
            }
        }

        public void onChangeUserEmail(int i, String str) {
            Log.i("XPGWifiSDK", "Success callback didChangeUserEmail, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                XPGWifiSDK.mListener.didChangeUserEmail(i, str);
            }
        }

        public void onChangeUserPassword(int i, String str) {
            Log.i("XPGWifiSDK", "Success callback didChangeUserPassword, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                XPGWifiSDK.mListener.didChangeUserPassword(i, str);
            }
        }

        public void onChangeUserPhone(int i, String str) {
            Log.i("XPGWifiSDK", "Success callback didChangeUserPhone, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                XPGWifiSDK.mListener.didChangeUserPhone(i, str);
            }
        }

        public void onDiscovered(int i, GWifiDeviceList gWifiDeviceList) {
            Log.i("XPGWifiSDK", "Success callback didDiscovered, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null") + ", device num = " + gWifiDeviceList.GetCount());
            if (XPGWifiSDK.mListener != null) {
                List arrayList = new ArrayList();
                for (int i2 = 0; ((long) i2) < gWifiDeviceList.GetCount(); i2++) {
                    if (GWifiDeviceType.GWifiDeviceTypeCenterControl.swigValue() == gWifiDeviceList.GetItem(i2).getDevType()) {
                        arrayList.add(new XPGWifiCentralControlDevice(gWifiDeviceList.GetItem(i2)));
                    } else {
                        arrayList.add(new XPGWifiDevice(gWifiDeviceList.GetItem(i2)));
                    }
                }
                XPGWifiSDK.mListener.didDiscovered(i, arrayList);
            }
        }

        public void onGetSSIDList(GWifiSSIDList gWifiSSIDList, int i) {
            Log.i("XPGWifiSDK", "Success callback didGetSSIDList, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                List arrayList = new ArrayList();
                if (gWifiSSIDList != null) {
                    for (int i2 = 0; ((long) i2) < gWifiSSIDList.GetCount(); i2++) {
                        arrayList.add(new XPGWifiSSID(gWifiSSIDList.GetItem(i2)));
                    }
                }
                XPGWifiSDK.mListener.didGetSSIDList(i, arrayList);
            }
        }

        public void onRegisterUser(int i, String str, String str2, String str3) {
            Log.i("XPGWifiSDK", "Success callback didRegisterUser, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                XPGWifiSDK.mListener.didRegisterUser(i, str, str2, str3);
            }
        }

        public void onRequestSendVerifyCode(int i, String str) {
            Log.i("XPGWifiSDK", "Success callback didRequestSendVerifyCode, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                XPGWifiSDK.mListener.didRequestSendVerifyCode(i, str);
            }
        }

        public void onSetAirLink(GWifiDevice gWifiDevice) {
            Log.i("XPGWifiSDK", "Success callback didSetDeviceWifi, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                XPGWifiSDK.mListener.didSetDeviceWifi(0, new XPGWifiDevice(gWifiDevice));
                XPGWifiSDK.smartLinkDone = true;
            }
        }

        public void onTransUser(int i, String str) {
            Log.i("XPGWifiSDK", "Success callback didTransUser, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                XPGWifiSDK.mListener.didTransUser(i, str);
            }
        }

        public void onUnbindDevice(String str, int i, String str2) {
            Log.i("XPGWifiSDK", "Success callback didUnbindDevice, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                XPGWifiSDK.mListener.didUnbindDevice(i, str2, str);
            }
        }

        public void onUpdateProduct(String str, int i) {
            Log.i("XPGWifiSDK", "Success callback didUpdateProduct, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                XPGWifiSDK.mListener.didUpdateProduct(i, str);
            }
        }

        public void onUserLogin(int i, String str, String str2, String str3) {
            Log.i("XPGWifiSDK", "Success callback didUserLogin, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                XPGWifiSDK.mListener.didUserLogin(i, str, str2, str3);
            }
        }

        public void onUserLogout(int i, String str) {
            Log.i("XPGWifiSDK", "Success callback didUserLogout, XPGWifiSDKListener " + (XPGWifiSDK.mListener == null ? "= null" : "!= null"));
            if (XPGWifiSDK.mListener != null) {
                XPGWifiSDK.mListener.didUserLogout(i, str);
            }
        }
    };
    private static boolean smartLinkDone = false;
    private static SmartLinkManipulator smartLinkHF;
    private static ConnectCallBack smartLinkHFCallback = new ConnectCallBack() {
        public void onConnect(ModuleInfo moduleInfo) {
            Log.d("XPG", "SmartLinkHF get device info, mac:" + moduleInfo.getMac() + ",ip:" + moduleInfo.getModuleIP() + ",id:" + moduleInfo.getMid());
        }

        public void onConnectOk() {
            Log.d("XPG", "SmartLinkHF success!");
            XPGWifiSDK.smartLinkDone = true;
        }

        public void onConnectTimeOut() {
            Log.e("XPG", "SmartLinkHF timeout!");
        }
    };
    private static int wifiConfigCount = 0;
    private static int wifiConfigInterval = 500;
    private static String wifiConfigKey = null;
    private static String wifiConfigSSID = null;
    private static Thread wifiConfigThread = null;
    private static int wifiConfigTimeout = 30;

    public enum XPGWifiConfigureMode {
        XPGWifiConfigureModeSoftAP,
        XPGWifiConfigureModeAirLink
    }

    public enum XPGWifiLogLevel {
        XPGWifiLogLevelError,
        XPGWifiLogLevelWarning,
        XPGWifiLogLevelAll
    }

    public enum XPGWifiThirdAccountType {
        XPGWifiThirdAccountTypeBAIDU,
        XPGWifiThirdAccountTypeSINA,
        XPGWifiThirdAccountTypeQQ
    }

    private XPGWifiSDK() {
    }

    private static String getCurentWifiSSID(Context context) {
        return context != null ? ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getSSID() : "";
    }

    private String getPhoneID() {
        return Secure.getString(mContext.getContentResolver(), "android_id");
    }

    private boolean isSoftAPMode() {
        String curentWifiSSID = getCurentWifiSSID(mContext);
        if (curentWifiSSID == null) {
            return false;
        }
        for (int i = 0; i < GWifiConfig.sharedInstance().GetSoftAPCount(); i++) {
            String GetSoftAPSSID = GWifiConfig.sharedInstance().GetSoftAPSSID(i);
            if (curentWifiSSID.startsWith(GetSoftAPSSID) || curentWifiSSID.startsWith("\"" + GetSoftAPSSID)) {
                return true;
            }
        }
        return false;
    }

    private void setDeviceWifiInside(String str, String str2, XPGWifiConfigureMode xPGWifiConfigureMode, int i) {
        wifiConfigCount = 0;
        if (i > 30) {
            wifiConfigTimeout = i;
        } else {
            wifiConfigTimeout = 30;
        }
        if (str == null || str2 == null) {
            mListener.didSetDeviceWifi(GWifiErrorCode.GWifiError_INVALID_PARAM.swigValue(), null);
            return;
        }
        wifiConfigSSID = str;
        wifiConfigKey = str2;
        if (wifiConfigThread != null && wifiConfigThread.isAlive()) {
            mListener.didSetDeviceWifi(GWifiErrorCode.GWifiError_THREAD_BUSY.swigValue(), null);
        } else if (XPGWifiConfigureMode.XPGWifiConfigureModeSoftAP == xPGWifiConfigureMode) {
            smartLinkDone = false;
            if (isSoftAPMode()) {
                wifiConfigThread = new Thread(new Runnable() {
                    public void run() {
                        int i = 0;
                        for (int i2 = 0; i2 < (XPGWifiSDK.wifiConfigTimeout * 1000) / XPGWifiSDK.wifiConfigInterval && XPGWifiSDK.this.isSoftAPMode(); i2++) {
                            if (i2 % 6 == 0) {
                                GWifiSDK.sharedInstance().SetSSID(XPGWifiSDK.wifiConfigSSID, XPGWifiSDK.wifiConfigKey);
                            }
                            try {
                                Thread.sleep((long) XPGWifiSDK.wifiConfigInterval);
                                XPGWifiSDK.wifiConfigCount = XPGWifiSDK.wifiConfigCount + 1;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        while (i < ((XPGWifiSDK.wifiConfigTimeout * 1000) / XPGWifiSDK.wifiConfigInterval) - XPGWifiSDK.wifiConfigCount && !XPGWifiSDK.smartLinkDone) {
                            try {
                                Thread.sleep((long) XPGWifiSDK.wifiConfigInterval);
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                            i++;
                        }
                        if (!XPGWifiSDK.smartLinkDone) {
                            XPGWifiSDK.mListener.didSetDeviceWifi(GWifiErrorCode.GWifiError_CONFIGURE_TIMEOUT.swigValue(), null);
                        }
                    }
                });
                wifiConfigThread.start();
                return;
            }
            mListener.didSetDeviceWifi(GWifiErrorCode.GWifiError_NOT_IN_SOFTAPMODE.swigValue(), null);
        } else {
            smartLinkDone = false;
            GWifiSDK.sharedInstance().SetAirLink(str, str2, (long) (i - 20));
            wifiConfigThread = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < (XPGWifiSDK.wifiConfigTimeout * 1000) / XPGWifiSDK.wifiConfigInterval && !XPGWifiSDK.smartLinkDone; i++) {
                        try {
                            Thread.sleep((long) XPGWifiSDK.wifiConfigInterval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!XPGWifiSDK.smartLinkDone) {
                        if (XPGWifiSDK.smartLinkHF != null) {
                            XPGWifiSDK.smartLinkHF.StopConnection();
                        }
                        XPGWifiSDK.mListener.didSetDeviceWifi(GWifiErrorCode.GWifiError_CONFIGURE_TIMEOUT.swigValue(), null);
                    }
                }
            });
            wifiConfigThread.start();
            smartLinkHF = SmartLinkManipulator.getInstence(mContext);
            smartLinkHF.setConnection(str, str2);
            smartLinkHF.Startconnection(smartLinkHFCallback);
        }
    }

    public static XPGWifiSDK sharedInstance() {
        return mInstance;
    }

    public void addGroup(String str, String str2, String str3, String str4, List<ConcurrentHashMap<String, String>> list) {
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0 || str3 == null || str3.length() == 0 || str4 == null || str4.length() == 0) {
            mListener.didGetGroups(-20, null);
            return;
        }
        try {
            JSONArray jSONArray;
            Object obj;
            JSONArray groupConfigJson = XPGWifiGroup.getGroupConfigJson(str);
            if (groupConfigJson.length() == 0) {
                jSONArray = new JSONArray();
                obj = "0";
            } else {
                jSONArray = groupConfigJson;
                String stringBuilder = new StringBuilder(String.valueOf(Integer.valueOf(groupConfigJson.getJSONObject(groupConfigJson.length() - 1).getString("gid")).intValue() + 1)).toString();
            }
            JSONArray jSONArray2 = new JSONArray();
            if (list != null) {
                for (ConcurrentHashMap concurrentHashMap : list) {
                    if (concurrentHashMap.containsKey("did") && concurrentHashMap.containsKey("sdid")) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("did", concurrentHashMap.get("did"));
                        jSONObject.put("sdid", concurrentHashMap.get("sdid"));
                        jSONArray2.put(jSONObject);
                    }
                }
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("gid", obj);
            jSONObject2.put("productKey", str3);
            jSONObject2.put("groupName", str4);
            jSONObject2.put("devices", jSONArray2);
            jSONArray.put(jSONObject2);
            XPGWifiGroup.writeToFile(jSONArray.toString(), XPGWifiGroup.getGroupConfigFilePath(str));
            List arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                jSONObject3.put("uid", str);
                arrayList.add(new XPGWifiGroup(jSONObject3));
            }
            mListener.didGetGroups(0, arrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void bindDevice(String str, String str2, String str3, String str4, String str5) {
        GWifiSDK.sharedInstance().BindDevice(str, str2, str3, str4, str5);
    }

    public void changeUserEmail(String str, String str2) {
        GWifiSDK.sharedInstance().ChangeUserEmail(str, str2);
    }

    public void changeUserPassword(String str, String str2, String str3) {
        GWifiSDK.sharedInstance().ChangeUserPassword(str, str2, str3);
    }

    public void changeUserPasswordByCode(String str, String str2, String str3) {
        GWifiSDK.sharedInstance().changeUserPasswordWithCode(str, str2, str3);
    }

    public void changeUserPasswordByEmail(String str) {
        GWifiSDK.sharedInstance().changeUserPasswordWithEmail(str);
    }

    public void changeUserPhone(String str, String str2, String str3) {
        GWifiSDK.sharedInstance().ChangeUserPhone(str, str2, str3);
    }

    public void editGroup(String str, String str2, String str3, String str4, List<ConcurrentHashMap<String, String>> list) {
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0 || str3 == null || str3.length() == 0 || str2.length() == 0 || str4 == null || str4.length() == 0) {
            mListener.didGetGroups(-20, null);
            return;
        }
        JSONArray groupConfigJson = XPGWifiGroup.getGroupConfigJson(str);
        int i = 0;
        while (i < groupConfigJson.length()) {
            try {
                if (groupConfigJson.getJSONObject(i).getString("gid").equals(str3)) {
                    JSONObject jSONObject = groupConfigJson.getJSONObject(i);
                    jSONObject.put("groupName", str4);
                    groupConfigJson.put(i, groupConfigJson);
                    JSONArray jSONArray = new JSONArray();
                    if (list != null) {
                        for (ConcurrentHashMap concurrentHashMap : list) {
                            if (concurrentHashMap.containsKey("did") && concurrentHashMap.containsKey("sdid")) {
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("did", concurrentHashMap.get("did"));
                                jSONObject2.put("sdid", concurrentHashMap.get("sdid"));
                                jSONArray.put(jSONObject2);
                            }
                        }
                    }
                    jSONObject.put("devices", jSONArray);
                    groupConfigJson.put(i, jSONObject);
                    XPGWifiGroup.writeToFile(groupConfigJson.toString(), XPGWifiGroup.getGroupConfigFilePath(str));
                    List arrayList = new ArrayList();
                    for (int i2 = 0; i2 < groupConfigJson.length(); i2++) {
                        jSONObject = groupConfigJson.getJSONObject(i2);
                        jSONObject.put("uid", str);
                        arrayList.add(new XPGWifiGroup(jSONObject));
                    }
                    mListener.didGetGroups(0, arrayList);
                    return;
                }
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mListener.didGetGroups(-1, null);
    }

    public void getBoundDevices(String str, String str2, String... strArr) {
        int i = 0;
        if (strArr == null || strArr.length <= 0) {
            GWifiConfig.sharedInstance().EnableProductFilter(false);
        } else {
            GWifiConfig.sharedInstance().EnableProductFilter(true);
            int length = strArr.length;
            while (i < length) {
                GWifiConfig.sharedInstance().RegisterProductKey(strArr[i]);
                i++;
            }
        }
        GWifiSDK.sharedInstance().GetBoundDevices(str, str2);
    }

    public void getGroups(String str, String str2, String... strArr) {
        int i = 0;
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            mListener.didGetGroups(-20, null);
            return;
        }
        List arrayList = new ArrayList();
        if (strArr != null && strArr.length > 0) {
            int length = strArr.length;
            int i2 = 0;
            while (i2 < length) {
                String str3 = strArr[i2];
                if (str3 == null || str3.length() <= 0) {
                    arrayList.clear();
                    break;
                } else {
                    arrayList.add(str3);
                    i2++;
                }
            }
        }
        if (strArr == null || arrayList.size() != 0) {
            try {
                List arrayList2 = new ArrayList();
                JSONArray groupConfigJson = XPGWifiGroup.getGroupConfigJson(str);
                while (i < groupConfigJson.length()) {
                    String str3 = groupConfigJson.getJSONObject(i).getString("productKey");
                    if (strArr == null || arrayList.contains(str3)) {
                        JSONObject jSONObject = groupConfigJson.getJSONObject(i);
                        jSONObject.put("uid", str);
                        arrayList2.add(new XPGWifiGroup(jSONObject));
                    }
                    i++;
                }
                mListener.didGetGroups(0, arrayList2);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        mListener.didGetGroups(-20, null);
    }

    public void getSSIDList() {
        GWifiSDK.sharedInstance().GetSSIDList();
    }

    public String getVersion() {
        return GWifiSDK.sharedInstance().getVersion();
    }

    @Deprecated
    public void registerSSID(String... strArr) {
        if (strArr != null && strArr.length > 0) {
            GWifiConfig.sharedInstance().ClearSSIDs();
            for (String RegisterSSID : strArr) {
                GWifiConfig.sharedInstance().RegisterSSID(RegisterSSID);
            }
        }
    }

    public void registerUser(String str, String str2) {
        GWifiSDK.sharedInstance().RegisterNormalUser(str, str2);
    }

    public void registerUserByEmail(String str, String str2) {
        GWifiSDK.sharedInstance().RegisterEmailUser(str, str2);
    }

    public void registerUserByPhoneAndCode(String str, String str2, String str3) {
        GWifiSDK.sharedInstance().RegisterPhoneUser(str, str2, str3);
    }

    public void removeGroup(String str, String str2, String str3) {
        int i = 0;
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0 || str3 == null || str3.length() == 0) {
            mListener.didGetGroups(-20, null);
            return;
        }
        JSONArray groupConfigJson = XPGWifiGroup.getGroupConfigJson(str);
        int i2 = 0;
        while (i2 < groupConfigJson.length()) {
            try {
                if (groupConfigJson.getJSONObject(i2).getString("gid").equals(str3)) {
                    JSONArray removeJSONArray = XPGWifiGroup.removeJSONArray(groupConfigJson, i2);
                    XPGWifiGroup.writeToFile(removeJSONArray.toString(), XPGWifiGroup.getGroupConfigFilePath(str));
                    List arrayList = new ArrayList();
                    while (i < removeJSONArray.length()) {
                        JSONObject jSONObject = removeJSONArray.getJSONObject(i);
                        jSONObject.put("uid", str);
                        arrayList.add(new XPGWifiGroup(jSONObject));
                        i++;
                    }
                    mListener.didGetGroups(0, arrayList);
                    return;
                }
                i2++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mListener.didGetGroups(-1, null);
    }

    public void requestSendVerifyCode(String str) {
        GWifiSDK.sharedInstance().RequestSendVerifyCode(str);
    }

    @Deprecated
    public void setDeviceWifi(String str, String str2, XPGWifiConfigureMode xPGWifiConfigureMode, int i) {
        setDeviceWifiInside(str, str2, xPGWifiConfigureMode, i);
    }

    public void setDeviceWifi(String str, String str2, XPGWifiConfigureMode xPGWifiConfigureMode, String str3, int i) {
        if (XPGWifiConfigureMode.XPGWifiConfigureModeSoftAP == xPGWifiConfigureMode && str3 != null && str3.length() > 0) {
            GWifiConfig.sharedInstance().ClearSSIDs();
            GWifiConfig.sharedInstance().RegisterSSID(str3);
        }
        setDeviceWifiInside(str, str2, xPGWifiConfigureMode, i);
    }

    public void setListener(XPGWifiSDKListener xPGWifiSDKListener) {
        mListener = xPGWifiSDKListener;
        Log.d("XPGWifiSDK", "SetListener success:" + xPGWifiSDKListener);
    }

    public void setLogLevel(XPGWifiLogLevel xPGWifiLogLevel, String str, boolean z) {
        if (mInstance == null) {
            Log.e("XPGWifiSDK", "Please startWithAppID first!");
            return;
        }
        if (XPGWifiLogLevel.XPGWifiLogLevelError == xPGWifiLogLevel) {
            GWifiSDK.SetLogLevel(GWifiLogLevel.GWifiLogLevelError);
        } else if (XPGWifiLogLevel.XPGWifiLogLevelWarning == xPGWifiLogLevel) {
            GWifiSDK.SetLogLevel(GWifiLogLevel.GWifiLogLevelWarning);
        } else {
            GWifiSDK.SetLogLevel(GWifiLogLevel.GWifiLogLevelAll);
        }
        if (str != null) {
            GWifiSDK.SetLogFile("/sdcard/" + str);
        }
        GWifiSDK.SetPrintDataLevel(z);
        Log.d("XPGWifiSDK", "SetLog success!");
    }

    public void startWithAppID(Context context, String str) {
        if (context == null) {
            Log.e("XPGWifiSDK", "StartWithAppID error, the context is null!");
            return;
        }
        mContext = context;
        GWifiConfig.sharedInstance().SetAppID(str);
        GWifiConfig.sharedInstance().SetDebug(true);
        GWifiConfig.sharedInstance().SetProductPath(context.getFilesDir() + "/XPGWifiSDK/Devices");
        GWifiSDK.sharedInstance().setListener(sdkListener);
        Log.d("XPGWifiSDK", "StartWithAppID " + str + " success!");
    }

    public void transAnonymousUserToNormalUser(String str, String str2, String str3) {
        GWifiSDK.sharedInstance().TransAnonymousUserToNormalUser(str, str2, str3);
    }

    public void transAnonymousUserToPhoneUser(String str, String str2, String str3, String str4) {
        GWifiSDK.sharedInstance().TransAnonymousUserToPhoneUser(str, str2, str3, str4);
    }

    public void unbindDevice(String str, String str2, String str3, String str4) {
        GWifiSDK.sharedInstance().UnbindDevice(str, str2, str3, str4);
    }

    public void updateDeviceFromServer(String str) {
        GWifiConfig.sharedInstance().DownloadProduct(str);
    }

    public void userLoginAnonymous() {
        GWifiSDK.sharedInstance().RegisterAnonymousUser(getPhoneID());
    }

    public void userLoginWithThirdAccountType(XPGWifiThirdAccountType xPGWifiThirdAccountType, String str, String str2) {
        GWifiSDK.sharedInstance().RegisterThirdAccountUser(XPGWifiThirdAccountType.XPGWifiThirdAccountTypeSINA == xPGWifiThirdAccountType ? 1 : 0, str, str2);
    }

    public void userLoginWithUserName(String str, String str2) {
        GWifiSDK.sharedInstance().UserLogin(str, str2);
    }

    public void userLogout(String str) {
        GWifiSDK.sharedInstance().UserLogout(str);
    }
}
