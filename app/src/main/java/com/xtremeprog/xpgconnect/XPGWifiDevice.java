package com.xtremeprog.xpgconnect;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class XPGWifiDevice {
    public static String XPGWifiDeviceHardwareFirmwareIdKey = "firmwareId";
    public static String XPGWifiDeviceHardwareFirmwareVerKey = "firmwareVer";
    public static String XPGWifiDeviceHardwareMCUHardVerKey = "mcuHardVer";
    public static String XPGWifiDeviceHardwareMCUSoftVerKey = "mcuSoftVer";
    public static String XPGWifiDeviceHardwareProductKey = "productKey";
    public static String XPGWifiDeviceHardwareWifiHardVerKey = "wifiHardVer";
    public static String XPGWifiDeviceHardwareWifiSoftVerKey = "wifiSoftVer";
    public static String XPGWifiDeviceLogBinaryKey = "binary";
    public static String XPGWifiDeviceLogContentKey = "content";
    public static String XPGWifiDeviceLogLevelKey = "level";
    public static String XPGWifiDeviceLogSourceKey = "source";
    public static String XPGWifiDeviceLogTagKey = "tag";
    public static String XPGWifiDeviceLogTimeKey = "time";
    private XPGWifiDevice device = this;
    private GWifiDeviceListener listener = new GWifiDeviceListener() {
        public void onDeviceLog(short s, String str, String str2, String str3, String str4, int i, String str5) {
            Log.i("XPGWifiSDK", "Success callback didUpdateDeviceLog, XPGWifiDeviceListener " + (XPGWifiDevice.this.mListener == null ? "= null" : "!= null"));
            if (XPGWifiDevice.this.mListener != null) {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                concurrentHashMap.put(XPGWifiDevice.XPGWifiDeviceLogLevelKey, new StringBuilder(String.valueOf(s)).toString());
                concurrentHashMap.put(XPGWifiDevice.XPGWifiDeviceLogTagKey, str);
                concurrentHashMap.put(XPGWifiDevice.XPGWifiDeviceLogSourceKey, str2);
                concurrentHashMap.put(XPGWifiDevice.XPGWifiDeviceLogContentKey, str3);
                concurrentHashMap.put(XPGWifiDevice.XPGWifiDeviceLogTimeKey, str5);
                XPGWifiDevice.this.mListener.didUpdateDeviceLog(XPGWifiDevice.this.device, concurrentHashMap);
            }
        }

        public void onDeviceOnline(boolean z) {
            Log.i("XPGWifiSDK", "Success callback didDeviceOnline, XPGWifiDeviceListener " + (XPGWifiDevice.this.mListener == null ? "= null" : "!= null"));
            if (XPGWifiDevice.this.mListener != null) {
                XPGWifiDevice.this.mListener.didDeviceOnline(XPGWifiDevice.this.device, z);
            }
        }

        public void onDisconnected() {
            Log.i("XPGWifiSDK", "Success callback didDisconnected, XPGWifiDeviceListener " + (XPGWifiDevice.this.mListener == null ? "= null" : "!= null"));
            if (XPGWifiDevice.this.mListener != null) {
                XPGWifiDevice.this.mListener.didDisconnected(XPGWifiDevice.this.device);
            }
        }

        public void onDiscovered(int i, GWifiDeviceList gWifiDeviceList) {
            Log.i("XPGWifiSDK", "Success callback didDiscovered, XPGWifiDeviceListener " + (XPGWifiDevice.this.mListener == null ? "= null" : "!= null") + ", device num = " + gWifiDeviceList.GetCount());
            if (XPGWifiDevice.this.mListener != null && XPGWifiDeviceType.XPGWifiDeviceTypeCenterControl == XPGWifiDevice.this.type()) {
                List arrayList = new ArrayList();
                for (int i2 = 0; ((long) i2) < gWifiDeviceList.GetCount(); i2++) {
                    if (GWifiDeviceType.GWifiDeviceTypeSub.swigValue() == gWifiDeviceList.GetItem(i2).getDevType()) {
                        arrayList.add(new XPGWifiSubDevice(gWifiDeviceList.GetItem(i2)));
                    }
                }
                ((XPGWifiCentralControlDeviceListener) XPGWifiDevice.this.mListener).didDiscovered(i, arrayList);
            }
        }

        public void onLogin(int i) {
            Log.i("XPGWifiSDK", "Success callback didLogin, XPGWifiDeviceListener " + (XPGWifiDevice.this.mListener == null ? "= null" : "!= null"));
            if (XPGWifiDevice.this.mListener != null) {
                XPGWifiDevice.this.mListener.didLogin(XPGWifiDevice.this.device, i);
            }
        }

        public void onQueryHardwareInfo(int i, GWifiQueryHardwareInfoStruct gWifiQueryHardwareInfoStruct) {
            Log.i("XPGWifiSDK", "Success callback didQueryHardwareInfo, XPGWifiDeviceListener " + (XPGWifiDevice.this.mListener == null ? "= null" : "!= null"));
            if (XPGWifiDevice.this.mListener != null) {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                concurrentHashMap.put(XPGWifiDevice.XPGWifiDeviceHardwareFirmwareIdKey, gWifiQueryHardwareInfoStruct.getFirmwareId());
                concurrentHashMap.put(XPGWifiDevice.XPGWifiDeviceHardwareFirmwareVerKey, gWifiQueryHardwareInfoStruct.getFirmwareVer());
                concurrentHashMap.put(XPGWifiDevice.XPGWifiDeviceHardwareMCUHardVerKey, gWifiQueryHardwareInfoStruct.getMcuHardVer());
                concurrentHashMap.put(XPGWifiDevice.XPGWifiDeviceHardwareMCUSoftVerKey, gWifiQueryHardwareInfoStruct.getMcuSoftVer());
                concurrentHashMap.put(XPGWifiDevice.XPGWifiDeviceHardwareProductKey, gWifiQueryHardwareInfoStruct.getProductKey());
                concurrentHashMap.put(XPGWifiDevice.XPGWifiDeviceHardwareWifiHardVerKey, gWifiQueryHardwareInfoStruct.getWifiHardVer());
                concurrentHashMap.put(XPGWifiDevice.XPGWifiDeviceHardwareWifiSoftVerKey, gWifiQueryHardwareInfoStruct.getWifiSoftVer());
                XPGWifiDevice.this.mListener.didQueryHardwareInfo(XPGWifiDevice.this.device, i, concurrentHashMap);
            }
        }

        public void onReceiveAlertsAndFaultsInfo(Vector_GWifiReceiveInfo vector_GWifiReceiveInfo, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo2, byte[] bArr) {
            Log.i("XPGWifiSDK", "Success callback didReceiveData, XPGWifiDeviceListener " + (XPGWifiDevice.this.mListener == null ? "= null" : "!= null"));
            if (XPGWifiDevice.this.mListener != null) {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                int i = 0;
                while (true) {
                    if (((long) i) >= vector_GWifiReceiveInfo.size()) {
                        break;
                    }
                    try {
                        Log.i(vector_GWifiReceiveInfo.get(i).getName(), new StringBuilder(String.valueOf(vector_GWifiReceiveInfo.get(i).getValue())).toString());
                        jSONObject.put(vector_GWifiReceiveInfo.get(i).getName(), vector_GWifiReceiveInfo.get(i).getValue());
                        i++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (vector_GWifiReceiveInfo.size() > 0) {
                    concurrentHashMap.put("alters", jSONObject.toString());
                }
                for (i = 0; ((long) i) < vector_GWifiReceiveInfo2.size(); i++) {
                    Log.i(vector_GWifiReceiveInfo2.get(i).getName(), new StringBuilder(String.valueOf(vector_GWifiReceiveInfo2.get(i).getValue())).toString());
                    jSONObject2.put(vector_GWifiReceiveInfo2.get(i).getName(), vector_GWifiReceiveInfo2.get(i).getValue());
                }
                if (vector_GWifiReceiveInfo2.size() > 0) {
                    concurrentHashMap.put("faults", jSONObject2.toString());
                }
                if (bArr != null) {
                    concurrentHashMap.put("binary", bArr);
                }
                XPGWifiDevice.this.mListener.didReceiveData(XPGWifiDevice.this.device, concurrentHashMap, 0);
            }
        }

        public boolean onReceiveData(String str, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo, Vector_GWifiReceiveInfo vector_GWifiReceiveInfo2, byte[] bArr, int i) {
            Log.i("XPGWifiSDK", "Success callback didReceiveData, XPGWifiDeviceListener " + (XPGWifiDevice.this.mListener == null ? "= null" : "!= null"));
            if (XPGWifiDevice.this.mListener != null) {
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                if (str != null) {
                    concurrentHashMap.put("data", str);
                }
                if (bArr != null) {
                    concurrentHashMap.put("binary", bArr);
                }
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                int i2 = 0;
                while (true) {
                    if (((long) i2) >= vector_GWifiReceiveInfo.size()) {
                        break;
                    }
                    try {
                        Log.i(vector_GWifiReceiveInfo.get(i2).getName(), new StringBuilder(String.valueOf(vector_GWifiReceiveInfo.get(i2).getValue())).toString());
                        jSONObject.put(vector_GWifiReceiveInfo.get(i2).getName(), vector_GWifiReceiveInfo.get(i2).getValue());
                        i2++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (vector_GWifiReceiveInfo.size() > 0) {
                    concurrentHashMap.put("alters", jSONObject.toString());
                }
                for (i2 = 0; ((long) i2) < vector_GWifiReceiveInfo2.size(); i2++) {
                    Log.i(vector_GWifiReceiveInfo2.get(i2).getName(), new StringBuilder(String.valueOf(vector_GWifiReceiveInfo2.get(i2).getValue())).toString());
                    jSONObject2.put(vector_GWifiReceiveInfo2.get(i2).getName(), vector_GWifiReceiveInfo2.get(i2).getValue());
                }
                if (vector_GWifiReceiveInfo2.size() > 0) {
                    concurrentHashMap.put("faults", jSONObject2.toString());
                }
                XPGWifiDevice.this.mListener.didReceiveData(XPGWifiDevice.this.device, concurrentHashMap, i);
            }
            return true;
        }

        public void onSetSwitcher(int i) {
            Log.i("XPGWifiSDK", "Success callback didSetSwitcher, XPGWifiDeviceListener " + (XPGWifiDevice.this.mListener == null ? "= null" : "!= null"));
            if (XPGWifiDevice.this.mListener != null) {
                XPGWifiDevice.this.mListener.didSetSwitcher(XPGWifiDevice.this.device, i);
            }
        }
    };
    private XPGWifiDeviceListener mListener;
    private GWifiDevice originDevice;

    public enum XPGWifiDeviceType {
        XPGWifiDeviceTypeNormal,
        XPGWifiDeviceTypeCenterControl
    }

    protected XPGWifiDevice(GWifiDevice gWifiDevice) {
        this.originDevice = gWifiDevice;
        gWifiDevice.setListener(this.listener);
    }

    public boolean disconnect() {
        return this.originDevice != null ? this.originDevice.Disconnect() : false;
    }

    public String getDid() {
        return this.originDevice != null ? this.originDevice.GetDid() : "";
    }

    public void getHardwareInfo() {
        if (this.originDevice != null) {
            this.originDevice.GetHardwareInfo();
        }
    }

    public String getIPAddress() {
        return this.originDevice != null ? this.originDevice.GetIPAddress() : "";
    }

    public String getMacAddress() {
        return this.originDevice != null ? this.originDevice.GetMacAddress() : "";
    }

    public String getPasscode() {
        return this.originDevice != null ? this.originDevice.GetPasscode() : "";
    }

    public String getProductKey() {
        return this.originDevice != null ? this.originDevice.GetProductKey() : "";
    }

    public String getProductName() {
        return this.originDevice != null ? this.originDevice.GetProductName() : "";
    }

    public String getRemark() {
        return this.originDevice != null ? this.originDevice.GetRemark() : "";
    }

    public String getUI() {
        return this.originDevice != null ? this.originDevice.GetUI() : "";
    }

    public boolean isBind(String str) {
        return this.originDevice != null ? this.originDevice.IsBind(str) : false;
    }

    public boolean isConnected() {
        return this.originDevice != null ? this.originDevice.getLogined() : false;
    }

    public boolean isDisabled() {
        return this.originDevice != null ? this.originDevice.getDisabled() : false;
    }

    public boolean isLAN() {
        return this.originDevice != null ? this.originDevice.IsLAN() : false;
    }

    public boolean isOnline() {
        return this.originDevice != null ? this.originDevice.IsOnline() : false;
    }

    public void login(String str, String str2) {
        if (this.originDevice != null && !this.originDevice.getLogining()) {
            this.originDevice.setLogining(true);
            this.originDevice.setLogined(false);
            this.originDevice.setToken(str2);
            if (!this.originDevice.Connect()) {
                this.mListener.didLogin(this.device, GWifiErrorCode.GWifiError_LOGIN_FAIL.swigValue());
            }
        }
    }

    public void setListener(XPGWifiDeviceListener xPGWifiDeviceListener) {
        this.mListener = xPGWifiDeviceListener;
    }

    protected boolean setLogParam(int i, boolean z) {
        return this.originDevice != null ? this.originDevice.SetLogParam(i, z) : false;
    }

    public XPGWifiDeviceType type() {
        return this.originDevice != null ? GWifiDeviceType.GWifiDeviceTypeCenterControl.swigValue() == this.originDevice.getDevType() ? XPGWifiDeviceType.XPGWifiDeviceTypeCenterControl : XPGWifiDeviceType.XPGWifiDeviceTypeNormal : XPGWifiDeviceType.XPGWifiDeviceTypeNormal;
    }

    public int write(String str) {
        return this.originDevice != null ? this.originDevice.write(str) : GWifiErrorCode.GWifiError_INVALID_PARAM.swigValue();
    }
}
