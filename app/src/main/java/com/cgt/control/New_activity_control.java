package com.cgt.control;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gizwits.airpurifier.activity.advanced.AdvancedActivity;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.entity.AdvanceType;
import com.gizwits.framework.entity.DeviceAlarm;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.utils.DialogManager.OnTimingChosenListener;
import com.gizwits.framework.utils.PxUtil;
import com.uh.all.airpurifier.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.DateUtil;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class New_activity_control extends BaseActivity implements OnClickListener, OnTouchListener {
    private int GetStatueTimeOut = 30000;
    private final String TAG = "AirPurActivity";
    private ArrayList<DeviceAlarm> alarmList;
    private ImageView auto_iv;
    private Button back_btn;
    private RelativeLayout back_layout;
    private ImageView bottom_push;
    private ImageView childLockO_iv;
    private LinearLayout childLockO_ll;
    private ConcurrentHashMap<String, Object> deviceDataMap;
    private LinearLayout functions_layout;
    Handler handler = new Handler() {
        private static /* synthetic */ int[] $SWITCH_TABLE$com$cgt$control$New_activity_control$handler_key;

        static /* synthetic */ int[] $SWITCH_TABLE$com$cgt$control$New_activity_control$handler_key() {
            int[] iArr = $SWITCH_TABLE$com$cgt$control$New_activity_control$handler_key;
            if (iArr == null) {
                iArr = new int[handler_key.values().length];
                try {
                    iArr[handler_key.ALARM.ordinal()] = 2;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[handler_key.DISCONNECTED.ordinal()] = 3;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[handler_key.GET_STATUE.ordinal()] = 5;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[handler_key.GET_STATUE_TIMEOUT.ordinal()] = 6;
                } catch (NoSuchFieldError e4) {
                }
                try {
                    iArr[handler_key.LOGIN_FAIL.ordinal()] = 9;
                } catch (NoSuchFieldError e5) {
                }
                try {
                    iArr[handler_key.LOGIN_START.ordinal()] = 7;
                } catch (NoSuchFieldError e6) {
                }
                try {
                    iArr[handler_key.LOGIN_SUCCESS.ordinal()] = 8;
                } catch (NoSuchFieldError e7) {
                }
                try {
                    iArr[handler_key.LOGIN_TIMEOUT.ordinal()] = 10;
                } catch (NoSuchFieldError e8) {
                }
                try {
                    iArr[handler_key.RECEIVED.ordinal()] = 4;
                } catch (NoSuchFieldError e9) {
                }
                try {
                    iArr[handler_key.UPDATE_UI.ordinal()] = 1;
                } catch (NoSuchFieldError e10) {
                }
                $SWITCH_TABLE$com$cgt$control$New_activity_control$handler_key = iArr;
            }
            return iArr;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (AnonymousClass2.$SWITCH_TABLE$com$cgt$control$New_activity_control$handler_key()[handler_key.values()[message.what].ordinal()]) {
                case 1:
                    if (New_activity_control.this.statuMap != null && New_activity_control.this.statuMap.size() > 0) {
                        New_activity_control.this.handler.removeMessages(handler_key.GET_STATUE_TIMEOUT.ordinal());
                        New_activity_control.this.changeSpeedBg(Integer.parseInt(New_activity_control.this.statuMap.get(JsonKeys.FAN_SPEED).toString()));
                        New_activity_control.this.setChildLock(((Boolean) New_activity_control.this.statuMap.get(JsonKeys.Child_Lock)).booleanValue());
                        New_activity_control.this.setSwitch(((Boolean) New_activity_control.this.statuMap.get(JsonKeys.ON_OFF)).booleanValue());
                        int parseInt = Integer.parseInt(New_activity_control.this.statuMap.get("time_on").toString());
                        System.out.println("定时关机   " + parseInt);
                        New_activity_control.this.setTimingOff(parseInt);
                        parseInt = Integer.parseInt(New_activity_control.this.statuMap.get(JsonKeys.Air_Quality).toString());
                        New_activity_control.this.updateBackgound(parseInt);
                        parseInt = (50 >= parseInt || parseInt > 100) ? (100 >= parseInt || parseInt > 150) ? parseInt > 150 ? 15 : 0 : 9 : 5;
                        parseInt *= 8;
                        if (parseInt > 100) {
                            parseInt = 100;
                        }
                        New_activity_control.this.updateTips(((float) (100 - parseInt)) * New_activity_control.this.mW100);
                        DialogManager.dismissDialog(New_activity_control.this, New_activity_control.this.progressDialogRefreshing);
                        New_activity_control.this.setUVBG(((Boolean) New_activity_control.this.statuMap.get(JsonKeys.UV)).booleanValue());
                        New_activity_control.this.setNegativeBG(((Boolean) New_activity_control.this.statuMap.get("fulizi")).booleanValue());
                        New_activity_control.this.setTemp(Integer.parseInt(New_activity_control.this.statuMap.get(JsonKeys.TEMP).toString()));
                        New_activity_control.this.setHumidity(Integer.parseInt(New_activity_control.this.statuMap.get(JsonKeys.Humidity).toString()));
                        New_activity_control.this.setAutoBG(((Boolean) New_activity_control.this.statuMap.get(JsonKeys.AUTO)).booleanValue());
                        New_activity_control.this.setSleepBG(((Boolean) New_activity_control.this.statuMap.get(JsonKeys.SLEEP)).booleanValue());
                        return;
                    }
                    return;
                case 2:
                    Iterator it = New_activity_control.this.alarmList.iterator();
                    while (it.hasNext()) {
                        DeviceAlarm deviceAlarm = (DeviceAlarm) it.next();
                        if (!New_activity_control.this.isAlarmList.contains(deviceAlarm.getDesc())) {
                            New_activity_control.this.isAlarmList.add(deviceAlarm.getDesc());
                            New_activity_control.this.isAlarm = false;
                        }
                    }
                    New_activity_control.this.isAlarmList.clear();
                    it = New_activity_control.this.alarmList.iterator();
                    while (it.hasNext()) {
                        New_activity_control.this.isAlarmList.add(((DeviceAlarm) it.next()).getDesc());
                    }
                    if (New_activity_control.this.alarmList == null || New_activity_control.this.alarmList.size() <= 0) {
                        New_activity_control.this.setTipsLayoutVisiblity(false, 0);
                        return;
                    }
                    if (!New_activity_control.this.isAlarm) {
                        if (New_activity_control.this.mFaultDialog == null) {
                            New_activity_control.this.mFaultDialog = DialogManager.getDeviceErrirDialog(New_activity_control.this, "设备故障", new OnClickListener() {
                                public void onClick(View view) {
                                    New_activity_control.this.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:10086")));
                                    New_activity_control.this.mFaultDialog.dismiss();
                                    New_activity_control.this.mFaultDialog = null;
                                }
                            });
                        }
                        New_activity_control.this.mFaultDialog.show();
                        New_activity_control.this.isAlarm = true;
                    }
                    New_activity_control.this.setTipsLayoutVisiblity(true, New_activity_control.this.alarmList.size());
                    return;
                case 3:
                    DialogManager.dismissDialog(New_activity_control.this, New_activity_control.this.progressDialogRefreshing);
                    DialogManager.dismissDialog(New_activity_control.this, New_activity_control.this.mFaultDialog);
                    DialogManager.showDialog(New_activity_control.this, New_activity_control.this.mDisconnectDialog);
                    return;
                case 4:
                    try {
                        if (New_activity_control.this.deviceDataMap.get("data") != null) {
                            Log.i("info", (String) New_activity_control.this.deviceDataMap.get("data"));
                            New_activity_control.this.inputDataToMaps(New_activity_control.this.statuMap, (String) New_activity_control.this.deviceDataMap.get("data"));
                        }
                        New_activity_control.this.alarmList.clear();
                        if (New_activity_control.this.deviceDataMap.get("alters") != null) {
                            Log.i("info", (String) New_activity_control.this.deviceDataMap.get("alters"));
                            New_activity_control.this.inputAlarmToList((String) New_activity_control.this.deviceDataMap.get("alters"));
                        }
                        if (New_activity_control.this.deviceDataMap.get("faults") != null) {
                            Log.i("info", (String) New_activity_control.this.deviceDataMap.get("faults"));
                            New_activity_control.this.inputAlarmToList((String) New_activity_control.this.deviceDataMap.get("faults"));
                        }
                        New_activity_control.this.handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
                        New_activity_control.this.handler.sendEmptyMessage(handler_key.ALARM.ordinal());
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                case 5:
                    New_activity_control.this.mCenter.cGetStatus(New_activity_control.mXpgWifiDevice);
                    return;
                case 6:
                    New_activity_control.this.handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
                    return;
                case 8:
                    New_activity_control.this.handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    New_activity_control.this.refreshMainControl();
                    return;
                case 9:
                    New_activity_control.this.handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    New_activity_control.this.handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
                    return;
                case 10:
                    New_activity_control.this.isTimeOut = true;
                    New_activity_control.this.handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
                    return;
                default:
                    return;
            }
        }
    };
    private ImageView homeQualityResult_iv;
    private TextView homeQualityResult_tv;
    private ImageView homeQualityTip_iv;
    private boolean isAlarm;
    private ArrayList<String> isAlarmList;
    private boolean isClick;
    private boolean isTimeOut = false;
    private ImageView ivTitleLeft;
    private ImageView ivTitleRight;
    private Dialog mDisconnectDialog;
    private Dialog mFaultDialog;
    float mL = 0.0f;
    float mR = 0.0f;
    float mW = 0.0f;
    float mW100 = 0.0f;
    private RelativeLayout main_layout;
    private ActivityManager manager;
    private ImageView negative_iv;
    private OnTouchListener onTouchListener = new OnTouchListener() {
        private boolean isProgressing = false;
        private double yCurrent;
        private double yDown;

        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case 0:
                    this.isProgressing = false;
                    this.yDown = (double) motionEvent.getY();
                    break;
                case 2:
                    this.yCurrent = (double) motionEvent.getY();
                    if (Math.abs(this.yDown - this.yCurrent) > 40.0d && !this.isProgressing) {
                        New_activity_control.this.troggleBottom();
                        this.isProgressing = true;
                        break;
                    }
            }
            return true;
        }
    };
    private TextView pm25_tv;
    Dialog powerDialog = null;
    private ProgressDialog progressDialogRefreshing;
    private ImageView push_iv;
    private RelativeLayout rlAlarmTips;
    private TextView shidu_tv;
    private ImageView silent_iv;
    private int speed = 0;
    private ConcurrentHashMap<String, Object> statuMap;
    private TextView temp_tv;
    private Dialog timeDialog;
    private int timingOff;
    private ImageView timingOff_iv;
    private LinearLayout timingOff_ll;
    private TextView timingOff_tv;
    private int timingOn;
    private RelativeLayout turnOff_layout;
    private ImageView turnOn_iv;
    private TextView tvAlarmTipsCount;
    private TextView tvTitle;
    private ImageView uv_iv;
    private ImageView wind_speed_iv;
    private LinearLayout wind_speed_ll;

    private enum handler_key {
        UPDATE_UI,
        ALARM,
        DISCONNECTED,
        RECEIVED,
        GET_STATUE,
        GET_STATUE_TIMEOUT,
        LOGIN_START,
        LOGIN_SUCCESS,
        LOGIN_FAIL,
        LOGIN_TIMEOUT
    }

    private void DisconnectOtherDevice() {
        for (XPGWifiDevice xPGWifiDevice : bindlist) {
            if (xPGWifiDevice.isConnected() && !xPGWifiDevice.getDid().equalsIgnoreCase(mXpgWifiDevice.getDid())) {
                this.mCenter.cDisconnect(xPGWifiDevice);
            }
        }
    }

    private void initQualityTips() {
        this.homeQualityResult_iv.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                float width = (float) New_activity_control.this.homeQualityResult_iv.getWidth();
                float right = (float) New_activity_control.this.homeQualityResult_iv.getRight();
                New_activity_control.this.mL = ((float) New_activity_control.this.homeQualityResult_iv.getLeft()) + (width / 50.0f);
                New_activity_control.this.mR = right - (width / 10.0f);
                New_activity_control.this.mW = New_activity_control.this.mR - New_activity_control.this.mL;
                New_activity_control.this.mW100 = New_activity_control.this.mW / 100.0f;
            }
        });
    }

    private void initUI() {
        this.main_layout = (RelativeLayout) findViewById(R.id.main_layout);
        this.turnOff_layout = (RelativeLayout) findViewById(R.id.turnOff_layout);
        this.turnOn_iv = (ImageView) findViewById(R.id.turnOn_iv);
        this.turnOn_iv.setOnClickListener(this);
        this.homeQualityTip_iv = (ImageView) findViewById(R.id.homeQualityTipArrow_iv);
        this.back_btn = (Button) findViewById(R.id.back_btn);
        this.back_btn.setOnTouchListener(this);
        this.back_layout = (RelativeLayout) findViewById(R.id.back_layout);
        this.push_iv = (ImageView) findViewById(R.id.push_iv);
        this.push_iv.setOnClickListener(this);
        this.homeQualityResult_tv = (TextView) findViewById(R.id.homeQualityResult_tv);
        this.homeQualityResult_iv = (ImageView) findViewById(R.id.homeQualityResult_iv);
        this.functions_layout = (LinearLayout) findViewById(R.id.functions_layout);
        this.temp_tv = (TextView) findViewById(R.id.temp_tv);
        this.pm25_tv = (TextView) findViewById(R.id.pm25_tv);
        this.shidu_tv = (TextView) findViewById(R.id.shidu_tv);
        this.bottom_push = (ImageView) findViewById(R.id.bottom_push);
        this.bottom_push.setOnTouchListener(this.onTouchListener);
        this.childLockO_iv = (ImageView) findViewById(R.id.childLockO_iv);
        this.childLockO_ll = (LinearLayout) findViewById(R.id.childLockO_ll);
        this.childLockO_ll.setOnClickListener(this);
        this.wind_speed_iv = (ImageView) findViewById(R.id.wind_speed_iv);
        this.wind_speed_ll = (LinearLayout) findViewById(R.id.wind_speed_ll);
        this.wind_speed_ll.setOnClickListener(this);
        this.timingOff_tv = (TextView) findViewById(R.id.timingOff_tv);
        this.timingOff_iv = (ImageView) findViewById(R.id.timingOff_iv);
        this.timingOff_ll = (LinearLayout) findViewById(R.id.timingOff_ll);
        this.timingOff_ll.setOnClickListener(this);
        this.auto_iv = (ImageView) findViewById(R.id.auto_iv);
        this.auto_iv.setOnClickListener(this);
        this.uv_iv = (ImageView) findViewById(R.id.uv_iv);
        this.uv_iv.setOnClickListener(this);
        this.negative_iv = (ImageView) findViewById(R.id.negative_iv);
        this.negative_iv.setOnClickListener(this);
        this.silent_iv = (ImageView) findViewById(R.id.silent_iv);
        this.silent_iv.setOnClickListener(this);
        this.ivTitleRight = (ImageView) findViewById(R.id.ivPower);
        this.ivTitleRight.setOnClickListener(this);
        this.ivTitleLeft = (ImageView) findViewById(R.id.ivMenu);
        this.ivTitleLeft.setOnClickListener(this);
        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        this.tvTitle.setOnClickListener(this);
        this.tvAlarmTipsCount = (TextView) findViewById(R.id.tvAlarmTipsCount);
        this.rlAlarmTips = (RelativeLayout) findViewById(R.id.rlAlarmTips);
        this.rlAlarmTips.setOnClickListener(this);
        this.alarmList = new ArrayList();
        this.isAlarmList = new ArrayList();
        this.progressDialogRefreshing = new ProgressDialog(this);
        this.progressDialogRefreshing.setMessage("正在更新状态,请稍后。");
        this.progressDialogRefreshing.setCancelable(false);
        this.mDisconnectDialog = DialogManager.getDisconnectDialog(this, new OnClickListener() {
            public void onClick(View view) {
                DialogManager.dismissDialog(New_activity_control.this, New_activity_control.this.mDisconnectDialog);
                IntentUtils.getInstance().startActivity(New_activity_control.this, DeviceListActivity.class);
                New_activity_control.this.finish();
            }
        });
        initQualityTips();
        refreshMainControl();
    }

    private void inputAlarmToList(String str) throws JSONException {
        Log.i("revjson", str);
        Iterator keys = new JSONObject(str).keys();
        while (keys.hasNext()) {
            Log.i("revjson", "action");
            this.alarmList.add(new DeviceAlarm(DateUtil.getDateCN(new Date()), keys.next().toString()));
        }
        this.handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
    }

    private void inputDataToMaps(ConcurrentHashMap<String, Object> concurrentHashMap, String str) throws JSONException {
        Log.i("revjson", str);
        JSONObject jSONObject = new JSONObject(str);
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String obj = keys.next().toString();
            Log.i("revjson", "action=" + obj);
            if (!(obj.equals("cmd") || obj.equals("qos") || obj.equals("seq") || obj.equals("version"))) {
                JSONObject jSONObject2 = jSONObject.getJSONObject(obj);
                Log.i("revjson", "params=" + jSONObject2);
                Iterator keys2 = jSONObject2.keys();
                while (keys2.hasNext()) {
                    String obj2 = keys2.next().toString();
                    Object obj3 = jSONObject2.get(obj2);
                    concurrentHashMap.put(obj2, obj3);
                    Log.i("AirPurActivity", "Key:" + obj2 + ";value" + obj3);
                }
            }
        }
        this.handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
    }

    private void refreshMainControl() {
        mXpgWifiDevice.setListener(this.deviceListener);
        DialogManager.showDialog(this, this.progressDialogRefreshing);
        this.handler.sendEmptyMessageDelayed(handler_key.GET_STATUE_TIMEOUT.ordinal(), (long) this.GetStatueTimeOut);
        this.handler.sendEmptyMessage(handler_key.GET_STATUE.ordinal());
    }

    private void setTipsLayoutVisiblity(boolean z, int i) {
        this.rlAlarmTips.setVisibility(z ? 0 : 8);
        this.tvAlarmTipsCount.setText(new StringBuilder(String.valueOf(i)).toString());
    }

    private void updateBackgound(int i) {
        this.pm25_tv.setText(new StringBuilder(String.valueOf(i)).toString());
        if (i >= 0 && i <= 50) {
            this.manager.applyKitKatTranslucency(this, -11284874);
            this.main_layout.setBackgroundResource(R.drawable.liang_bg);
            this.homeQualityResult_tv.setText("优");
        } else if (50 < i && i <= 100) {
            this.manager.applyKitKatTranslucency(this, -15041593);
            this.main_layout.setBackgroundResource(R.drawable.good_bg);
            this.homeQualityResult_tv.setText("良");
        } else if (101 >= i || i > 150) {
            this.manager.applyKitKatTranslucency(this, -2254801);
            this.main_layout.setBackgroundResource(R.drawable.bad_bg);
            this.homeQualityResult_tv.setText("差");
        } else {
            this.manager.applyKitKatTranslucency(this, -5525227);
            this.main_layout.setBackgroundResource(R.drawable.middle_bg);
            this.homeQualityResult_tv.setText("中");
        }
    }

    private void updateTips(final float f) {
        runOnUiThread(new Runnable() {
            public void run() {
                New_activity_control.this.homeQualityTip_iv.setX(New_activity_control.this.mL + f);
            }
        });
    }

    public void bottomFucTrogg() {
        this.push_iv.setVisibility(0);
    }

    public void changeSpeedBg(int i) {
        this.speed = i;
    }

    protected void didDisconnected(XPGWifiDevice xPGWifiDevice) {
        super.didDisconnected(xPGWifiDevice);
    }

    protected void didLogin(XPGWifiDevice xPGWifiDevice, int i) {
        if (!this.isTimeOut) {
            if (i == 0) {
                this.handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
            } else {
                this.handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
            }
        }
    }

    protected void didReceiveData(XPGWifiDevice xPGWifiDevice, ConcurrentHashMap<String, Object> concurrentHashMap, int i) {
        Log.e("AirPurActivity", "didReceiveData");
        this.deviceDataMap = concurrentHashMap;
        this.handler.sendEmptyMessage(handler_key.RECEIVED.ordinal());
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        this.isClick = false;
    }

    public void onBackPressed() {
        if (mXpgWifiDevice != null && mXpgWifiDevice.isConnected()) {
            this.mCenter.cDisconnect(mXpgWifiDevice);
            DisconnectOtherDevice();
        }
        finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivMenu /*2131165213*/:
                finish();
                return;
            case R.id.ivPower /*2131165215*/:
                this.powerDialog = DialogManager.getPowerOffDialog(this, new OnClickListener() {
                    public void onClick(View view) {
                        New_activity_control.this.mCenter.cSwitchOn(New_activity_control.mXpgWifiDevice, false);
                        New_activity_control.this.setSwitch(false);
                        New_activity_control.this.powerDialog.dismiss();
                    }
                });
                this.powerDialog.show();
                return;
            case R.id.rlAlarmTips /*2131165216*/:
                Intent intent = new Intent(this, AdvancedActivity.class);
                intent.putExtra("advanced_set", AdvanceType.alarm);
                startActivity(intent);
                return;
            case R.id.silent_iv /*2131165233*/:
                if (this.silent_iv.getTag().toString().equals("0")) {
                    setSleepBG(true);
                    this.mCenter.cSetSleep(mXpgWifiDevice, true);
                } else {
                    setSleepBG(false);
                    this.mCenter.cSetSleep(mXpgWifiDevice, false);
                }
                changeSpeedBg(2);
                return;
            case R.id.auto_iv /*2131165236*/:
                if (this.auto_iv.getTag().toString().equals("0")) {
                    setAutoBG(true);
                    this.mCenter.cAuto(mXpgWifiDevice, true);
                } else {
                    setAutoBG(false);
                    this.mCenter.cAuto(mXpgWifiDevice, false);
                }
                changeSpeedBg(3);
                return;
            case R.id.push_iv /*2131165240*/:
                troggleBottom();
                return;
            case R.id.childLockO_ll /*2131165245*/:
                if (this.childLockO_ll.getTag().toString() == "0") {
                    this.mCenter.cChildLock(mXpgWifiDevice, false);
                    setChildLock(false);
                    return;
                }
                this.mCenter.cChildLock(mXpgWifiDevice, true);
                setChildLock(true);
                return;
            case R.id.timingOff_ll /*2131165251*/:
                DialogManager.getWheelTimingDialog(this, new OnTimingChosenListener() {
                    public void timingChosen(int i) {
                        New_activity_control.this.mCenter.cCountDownOff(New_activity_control.mXpgWifiDevice, i);
                        New_activity_control.this.timingOff = i;
                        if (i == 0) {
                            New_activity_control.this.timingOff_tv.setText("定时关机");
                            New_activity_control.this.timingOff_iv.setImageResource(R.drawable.icon_4);
                            return;
                        }
                        New_activity_control.this.timingOff_tv.setText(new StringBuilder(String.valueOf(i)).append("小时").toString());
                        New_activity_control.this.timingOff_iv.setImageResource(R.drawable.icon_4_2);
                    }
                }, " 定时关机", this.timingOff == 0 ? 24 : this.timingOff - 1).show();
                return;
            case R.id.turnOn_iv /*2131165255*/:
                this.mCenter.cSwitchOn(mXpgWifiDevice, true);
                setSwitch(true);
                return;
            case R.id.uv_iv /*2131165288*/:
                if (this.uv_iv.getTag().toString().equals("0")) {
                    setUVBG(true);
                    this.mCenter.cSetUV(mXpgWifiDevice, true);
                    return;
                }
                setUVBG(false);
                this.mCenter.cSetUV(mXpgWifiDevice, false);
                return;
            case R.id.negative_iv /*2131165289*/:
                if (this.negative_iv.getTag().toString().equals("0")) {
                    setNegativeBG(true);
                    this.mCenter.cSwitchPlasma(mXpgWifiDevice, true);
                    return;
                }
                setNegativeBG(false);
                this.mCenter.cSwitchPlasma(mXpgWifiDevice, false);
                return;
            case R.id.wind_speed_ll /*2131165290*/:
                DialogManager.getWheelSpeedDialog(this, new OnTimingChosenListener() {
                    public void timingChosen(int i) {
                        New_activity_control.this.mCenter.cSetSpeed(New_activity_control.mXpgWifiDevice, i);
                    }
                }, " 风速调节", this.speed).show();
                return;
            default:
                return;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_control_new);
        this.manager = new ActivityManager();
        this.manager.applyKitKatTranslucency(this, -16725413);
        initUI();
        this.statuMap = new ConcurrentHashMap();
        this.alarmList = new ArrayList();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        if (!this.mDisconnectDialog.isShowing()) {
            refreshMainControl();
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.back_btn && motionEvent.getAction() == 0) {
            troggleBottom();
        }
        return true;
    }

    public void setAutoBG(boolean z) {
        if (z) {
            this.auto_iv.setTag("1");
            this.auto_iv.setBackgroundResource(R.drawable.icon_intelligence_select);
            return;
        }
        this.auto_iv.setTag("0");
        this.auto_iv.setBackgroundResource(R.drawable.icon_intelligence_not_select);
    }

    public void setChildLock(boolean z) {
        if (z) {
            this.childLockO_iv.setImageResource(R.drawable.icon_2_2);
            this.childLockO_ll.setTag("0");
            return;
        }
        this.childLockO_iv.setImageResource(R.drawable.icon_2);
        this.childLockO_ll.setTag("1");
    }

    public void setHumidity(int i) {
        this.shidu_tv.setText(new StringBuilder(String.valueOf(i)).append("%").toString());
    }

    public void setNegativeBG(boolean z) {
        if (z) {
            this.negative_iv.setTag("1");
            this.negative_iv.setBackgroundResource(R.drawable.icon_strong_select);
            return;
        }
        this.negative_iv.setTag("0");
        this.negative_iv.setBackgroundResource(R.drawable.icon_strong_not_select);
    }

    public void setSleepBG(boolean z) {
        if (z) {
            this.silent_iv.setTag("1");
            this.silent_iv.setBackgroundResource(R.drawable.icon_sleep_select);
            return;
        }
        this.silent_iv.setTag("0");
        this.silent_iv.setBackgroundResource(R.drawable.icon_sleep_not_select);
    }

    public void setSwitch(boolean z) {
        if (z) {
            this.turnOff_layout.setVisibility(8);
        } else {
            this.turnOff_layout.setVisibility(0);
        }
    }

    public void setTemp(int i) {
        this.temp_tv.setText(new StringBuilder(String.valueOf(i)).append("℃").toString());
    }

    public void setTimingOff(int i) {
        this.timingOff = i;
        if (i == 0) {
            this.timingOff_tv.setText("定时关机");
            this.timingOff_iv.setImageResource(R.drawable.icon_4);
            return;
        }
        this.timingOff_tv.setText(new StringBuilder(String.valueOf(i)).append("小时").toString());
        this.timingOff_iv.setImageResource(R.drawable.icon_4_2);
    }

    public void setUVBG(boolean z) {
        if (z) {
            this.uv_iv.setTag("1");
            this.uv_iv.setBackgroundResource(R.drawable.icon_standard_select);
            return;
        }
        this.uv_iv.setTag("0");
        this.uv_iv.setBackgroundResource(R.drawable.icon_standard_not_select);
    }

    public void troggleBottom() {
        LayoutParams layoutParams = (LayoutParams) this.functions_layout.getLayoutParams();
        if (layoutParams.bottomMargin == 0) {
            layoutParams.bottomMargin = PxUtil.dip2px(this, -81.0f);
            this.back_layout.setVisibility(8);
            this.push_iv.setImageResource(R.drawable.arrow_1);
        } else {
            layoutParams.bottomMargin = 0;
            this.back_layout.setVisibility(0);
            this.push_iv.setImageResource(R.drawable.arrow_2);
        }
        this.functions_layout.setLayoutParams(layoutParams);
    }
}
