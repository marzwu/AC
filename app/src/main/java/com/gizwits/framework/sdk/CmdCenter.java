package com.gizwits.framework.sdk;

import android.content.Context;
import android.util.Log;

import com.gizwits.framework.config.Configs;
import com.gizwits.framework.config.JsonKeys;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.xtremeprog.xpgconnect.XPGWifiSDK;
import com.xtremeprog.xpgconnect.XPGWifiSDK.XPGWifiConfigureMode;

import org.json.JSONException;
import org.json.JSONObject;

public class CmdCenter {
    private static final String TAG = "CmdCenter";
    private static CmdCenter mCenter;
    private static XPGWifiSDK xpgWifiGCC;
    private SettingManager mSettingManager;

    private CmdCenter(Context context) {
        if (mCenter == null) {
            init(context);
        }
    }

    public static CmdCenter getInstance(Context context) {
        if (mCenter == null) {
            mCenter = new CmdCenter(context);
        }
        return mCenter;
    }

    private void init(Context context) {
        this.mSettingManager = new SettingManager(context);
        xpgWifiGCC = XPGWifiSDK.sharedInstance();
    }

    public void cAirSensitivity(XPGWifiDevice xPGWifiDevice, int i) {
        cWrite(xPGWifiDevice, JsonKeys.Air_Sensitivity, Integer.valueOf(i));
        getStatus(xPGWifiDevice);
    }

    public void cAuto(XPGWifiDevice xPGWifiDevice, boolean z) {
        cWrite(xPGWifiDevice, JsonKeys.AUTO, Boolean.valueOf(z));
        getStatus(xPGWifiDevice);
    }

    public void cBindDevice(String str, String str2, String str3, String str4, String str5) {
        xpgWifiGCC.bindDevice(str, str2, str3, str4, str5);
    }

    public void cChangePassworfByEmail(String str) {
        xpgWifiGCC.changeUserPasswordByEmail(str);
    }

    public void cChangeUserPassword(String str, String str2, String str3) {
        xpgWifiGCC.changeUserPassword(str, str2, str3);
    }

    public void cChangeUserPasswordWithCode(String str, String str2, String str3) {
        xpgWifiGCC.changeUserPasswordByCode(str, str2, str3);
    }

    public void cChildLock(XPGWifiDevice xPGWifiDevice, boolean z) {
        cWrite(xPGWifiDevice, JsonKeys.Child_Lock, Boolean.valueOf(z));
        getStatus(xPGWifiDevice);
    }

    public void cCountDownOff(XPGWifiDevice xPGWifiDevice, int i) {
        cWrite(xPGWifiDevice, "time_on", Integer.valueOf(i));
        getStatus(xPGWifiDevice);
    }

    public void cCountDownOn(XPGWifiDevice xPGWifiDevice, int i) {
        cWrite(xPGWifiDevice, "time_on", Integer.valueOf(i));
        getStatus(xPGWifiDevice);
    }

    public void cDisconnect(XPGWifiDevice xPGWifiDevice) {
        xPGWifiDevice.disconnect();
    }

    public void cGetBoundDevices(String str, String str2) {
        xpgWifiGCC.getBoundDevices(str, str2, Configs.PRODUCT_KEY);
    }

    public void cGetStatus(XPGWifiDevice xPGWifiDevice) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("cmd", 2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        xPGWifiDevice.write(jSONObject.toString());
    }

    public void cLED(XPGWifiDevice xPGWifiDevice, boolean z) {
        cWrite(xPGWifiDevice, JsonKeys.LED, Boolean.valueOf(z));
        getStatus(xPGWifiDevice);
    }

    public void cLogin(String str, String str2) {
        xpgWifiGCC.userLoginWithUserName(str, str2);
    }

    public void cLoginAnonymousUser() {
        xpgWifiGCC.userLoginAnonymous();
    }

    public void cLogout() {
        Log.e(TAG, "cLogout:uesrid=" + this.mSettingManager.getUid());
        xpgWifiGCC.userLogout(this.mSettingManager.getUid());
    }

    public void cRegisterMailUser(String str, String str2) {
        xpgWifiGCC.registerUserByEmail(str, str2);
    }

    public void cRegisterPhoneUser(String str, String str2, String str3) {
        xpgWifiGCC.registerUserByPhoneAndCode(str, str3, str2);
    }

    public void cRequestSendVerifyCode(String str) {
        xpgWifiGCC.requestSendVerifyCode(str);
    }

    public void cResetLife(XPGWifiDevice xPGWifiDevice) {
        cWrite(xPGWifiDevice, JsonKeys.Filter_Life, Integer.valueOf(100));
        getStatus(xPGWifiDevice);
    }

    public void cSetAirLink(String str, String str2) {
        xpgWifiGCC.setDeviceWifi(str, str2, XPGWifiConfigureMode.XPGWifiConfigureModeAirLink, 60);
    }

    public void cSetSleep(XPGWifiDevice xPGWifiDevice, boolean z) {
        cWrite(xPGWifiDevice, JsonKeys.SLEEP, Boolean.valueOf(z));
        getStatus(xPGWifiDevice);
    }

    public void cSetSoftAp(String str, String str2) {
        xpgWifiGCC.setDeviceWifi(str, str2, XPGWifiConfigureMode.XPGWifiConfigureModeSoftAP, 30);
    }

    public void cSetSpeed(XPGWifiDevice xPGWifiDevice, int i) {
        cWrite(xPGWifiDevice, JsonKeys.FAN_SPEED, Integer.valueOf(i));
        getStatus(xPGWifiDevice);
    }

    public void cSetUV(XPGWifiDevice xPGWifiDevice, boolean z) {
        cWrite(xPGWifiDevice, JsonKeys.UV, Boolean.valueOf(z));
        getStatus(xPGWifiDevice);
    }

    public void cSwitchOn(XPGWifiDevice xPGWifiDevice, boolean z) {
        cWrite(xPGWifiDevice, JsonKeys.ON_OFF, Boolean.valueOf(z));
        getStatus(xPGWifiDevice);
    }

    public void cSwitchPlasma(XPGWifiDevice xPGWifiDevice, boolean z) {
        cWrite(xPGWifiDevice, "fulizi", Boolean.valueOf(z));
        getStatus(xPGWifiDevice);
    }

    public void cUnbindDevice(String str, String str2, String str3, String str4) {
        xpgWifiGCC.unbindDevice(str, str2, str3, str4);
    }

    public void cUpdateRemark(String str, String str2, String str3, String str4, String str5) {
        xpgWifiGCC.bindDevice(str, str2, str3, str4, str5);
    }

    public void cWrite(XPGWifiDevice xPGWifiDevice, String str, Object obj) {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("cmd", 1);
            jSONObject2.put(str, obj);
            jSONObject.put(JsonKeys.KEY_ACTION, jSONObject2);
            Log.i("sendjson", jSONObject.toString());
            xPGWifiDevice.write(jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getStatus(XPGWifiDevice xPGWifiDevice) {
        cGetStatus(xPGWifiDevice);
    }

    public XPGWifiSDK getXPGWifiSDK() {
        return xpgWifiGCC;
    }
}
