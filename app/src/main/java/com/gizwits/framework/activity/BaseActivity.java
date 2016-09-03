package com.gizwits.framework.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gizwits.framework.sdk.CmdCenter;
import com.gizwits.framework.sdk.SettingManager;
import com.gizwits.framework.utils.Historys;
import com.uh.all.airpurifier.R;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.xtremeprog.xpgconnect.XPGWifiDeviceListener;
import com.xtremeprog.xpgconnect.XPGWifiSDKListener;
import com.xtremeprog.xpgconnect.XPGWifiSSID;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BaseActivity extends Activity {
    protected static List<XPGWifiDevice> bindlist = new ArrayList();
    protected static List<XPGWifiDevice> deviceslist = new ArrayList();
    protected static XPGWifiDevice mXpgWifiDevice;
    protected XPGWifiDeviceListener deviceListener = new XPGWifiDeviceListener() {
        public void didDeviceOnline(XPGWifiDevice xPGWifiDevice, boolean z) {
            BaseActivity.this.didDeviceOnline(xPGWifiDevice, z);
        }

        public void didDisconnected(XPGWifiDevice xPGWifiDevice) {
            BaseActivity.this.didDisconnected(xPGWifiDevice);
        }

        public void didLogin(XPGWifiDevice xPGWifiDevice, int i) {
            BaseActivity.this.didLogin(xPGWifiDevice, i);
        }

        public void didReceiveData(XPGWifiDevice xPGWifiDevice, ConcurrentHashMap<String, Object> concurrentHashMap, int i) {
            BaseActivity.this.didReceiveData(xPGWifiDevice, concurrentHashMap, i);
        }
    };
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            BaseActivity.this.isExit = false;
        }
    };
    private boolean isExit = false;
    protected CmdCenter mCenter;
    private XPGWifiSDKListener sdkListener = new XPGWifiSDKListener() {
        public void didBindDevice(int i, String str, String str2) {
            BaseActivity.this.didBindDevice(i, str, str2);
        }

        public void didChangeUserEmail(int i, String str) {
            BaseActivity.this.didChangeUserEmail(i, str);
        }

        public void didChangeUserPassword(int i, String str) {
            BaseActivity.this.didChangeUserPassword(i, str);
        }

        public void didChangeUserPhone(int i, String str) {
            BaseActivity.this.didChangeUserPhone(i, str);
        }

        public void didDiscovered(int i, List<XPGWifiDevice> list) {
            BaseActivity.this.didDiscovered(i, list);
        }

        public void didGetSSIDList(int i, List<XPGWifiSSID> list) {
            BaseActivity.this.didGetSSIDList(i, list);
        }

        public void didRegisterUser(int i, String str, String str2, String str3) {
            BaseActivity.this.didRegisterUser(i, str, str2, str3);
        }

        public void didRequestSendVerifyCode(int i, String str) {
            BaseActivity.this.didRequestSendVerifyCode(i, str);
        }

        public void didSetDeviceWifi(int i, XPGWifiDevice xPGWifiDevice) {
            BaseActivity.this.didSetDeviceWifi(i, xPGWifiDevice);
        }

        public void didUnbindDevice(int i, String str, String str2) {
            BaseActivity.this.didUnbindDevice(i, str, str2);
        }

        public void didUserLogin(int i, String str, String str2, String str3) {
            BaseActivity.this.didUserLogin(i, str, str2, str3);
        }

        public void didUserLogout(int i, String str) {
            BaseActivity.this.didUserLogout(i, str);
        }
    };
    protected SettingManager setmanager;

    public static XPGWifiDevice findDeviceByMac(String str, String str2) {
        Log.i("count", new StringBuilder(String.valueOf(deviceslist.size())).toString());
        for (int i = 0; i < deviceslist.size(); i++) {
            XPGWifiDevice xPGWifiDevice = (XPGWifiDevice) deviceslist.get(i);
            if (xPGWifiDevice != null) {
                Log.i("deivcemac", xPGWifiDevice.getMacAddress());
                if (xPGWifiDevice != null && xPGWifiDevice.getMacAddress().equals(str) && xPGWifiDevice.getDid().equals(str2)) {
                    return xPGWifiDevice;
                }
            }
        }
        return null;
    }

    private void hideSoftInput(IBinder iBinder) {
        if (iBinder != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(iBinder, 2);
        }
    }

    private boolean isShouldHideInput(View view, MotionEvent motionEvent) {
        if (view != null && (view instanceof EditText)) {
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            int height = view.getHeight() + i2;
            int width = view.getWidth() + i;
            if (motionEvent.getX() <= ((float) i) || motionEvent.getX() >= ((float) width) || motionEvent.getY() <= ((float) i2) || motionEvent.getY() >= ((float) height)) {
                return true;
            }
        }
        return false;
    }

    protected void didBindDevice(int i, String str, String str2) {
    }

    protected void didChangeUserEmail(int i, String str) {
    }

    protected void didChangeUserPassword(int i, String str) {
    }

    protected void didChangeUserPhone(int i, String str) {
    }

    protected void didDeviceOnline(XPGWifiDevice xPGWifiDevice, boolean z) {
    }

    protected void didDisconnected(XPGWifiDevice xPGWifiDevice) {
    }

    protected void didDiscovered(int i, List<XPGWifiDevice> list) {
    }

    protected void didGetSSIDList(int i, List<XPGWifiSSID> list) {
    }

    protected void didLogin(XPGWifiDevice xPGWifiDevice, int i) {
    }

    protected void didReceiveData(XPGWifiDevice xPGWifiDevice, ConcurrentHashMap<String, Object> concurrentHashMap, int i) {
    }

    protected void didRegisterUser(int i, String str, String str2, String str3) {
    }

    protected void didRequestSendVerifyCode(int i, String str) {
    }

    protected void didSetDeviceWifi(int i, XPGWifiDevice xPGWifiDevice) {
    }

    protected void didUnbindDevice(int i, String str, String str2) {
    }

    protected void didUserLogin(int i, String str, String str2, String str3) {
    }

    protected void didUserLogout(int i, String str) {
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            View currentFocus = getCurrentFocus();
            if (isShouldHideInput(currentFocus, motionEvent)) {
                hideSoftInput(currentFocus.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void exit() {
        if (this.isExit) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            intent.setFlags(268435456);
            startActivity(intent);
            Historys.exit();
            return;
        }
        this.isExit = true;
        Toast.makeText(getApplicationContext(), getString(R.string.tip_exit), Toast.LENGTH_SHORT).show();
        this.handler.sendEmptyMessageDelayed(0, 2000);
    }

    protected void initBindList() {
        if (bindlist != null && bindlist.size() > 0) {
            bindlist.clear();
        }
        for (XPGWifiDevice xPGWifiDevice : deviceslist) {
            if (xPGWifiDevice.isBind(this.setmanager.getUid())) {
                bindlist.add(xPGWifiDevice);
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setmanager = new SettingManager(getApplicationContext());
        this.mCenter = CmdCenter.getInstance(getApplicationContext());
        this.mCenter.getXPGWifiSDK().setListener(this.sdkListener);
        Historys.put(this);
    }

    public void onResume() {
        super.onResume();
        this.mCenter.getXPGWifiSDK().setListener(this.sdkListener);
    }
}
