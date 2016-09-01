package com.cgt.control;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
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

public class New_New_activity_control extends BaseActivity implements OnClickListener {
    Dialog FixDialog = null;
    private int GetStatueTimeOut = 30000;
    private final String TAG = "AirPurActivity";
    private ArrayList<DeviceAlarm> alarmList;
    Animation am = null;
    private ImageView auto_iv;
    private LinearLayout auto_ll;
    private TextView auto_text;
    private Button back_btn;
    private RelativeLayout back_layout;
    private ImageView childLockO_iv;
    private ImageView childLock_iv;
    private RelativeLayout childLock_layout;
    private int current_degree = 0;
    private ConcurrentHashMap<String, Object> deviceDataMap;
    private LinearLayout functions_layout;
    Handler handler = new Handler() {
        private static /* synthetic */ int[] $SWITCH_TABLE$com$cgt$control$New_New_activity_control$handler_key;

        static /* synthetic */ int[] $SWITCH_TABLE$com$cgt$control$New_New_activity_control$handler_key() {
            int[] iArr = $SWITCH_TABLE$com$cgt$control$New_New_activity_control$handler_key;
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
                $SWITCH_TABLE$com$cgt$control$New_New_activity_control$handler_key = iArr;
            }
            return iArr;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (AnonymousClass1.$SWITCH_TABLE$com$cgt$control$New_New_activity_control$handler_key()[handler_key.values()[message.what].ordinal()]) {
                case 1:
                    if (New_New_activity_control.this.statuMap != null && New_New_activity_control.this.statuMap.size() > 0) {
                        New_New_activity_control.this.handler.removeMessages(handler_key.GET_STATUE_TIMEOUT.ordinal());
                        New_New_activity_control.this.changeSpeedBg(Integer.parseInt(New_New_activity_control.this.statuMap.get(JsonKeys.FAN_SPEED).toString()));
                        New_New_activity_control.this.setChildLock(((Boolean) New_New_activity_control.this.statuMap.get(JsonKeys.Child_Lock)).booleanValue());
                        New_New_activity_control.this.setSwitch(((Boolean) New_New_activity_control.this.statuMap.get(JsonKeys.ON_OFF)).booleanValue());
                        int parseInt = Integer.parseInt(New_New_activity_control.this.statuMap.get("time_on").toString());
                        System.out.println("定时关机   " + parseInt);
                        New_New_activity_control.this.setTimingOff(parseInt);
                        parseInt = Integer.parseInt(New_New_activity_control.this.statuMap.get(JsonKeys.Air_Quality).toString());
                        New_New_activity_control.this.updateBackgound(parseInt);
                        New_New_activity_control.this.updateTips(parseInt);
                        DialogManager.dismissDialog(New_New_activity_control.this, New_New_activity_control.this.progressDialogRefreshing);
                        New_New_activity_control.this.setUVBG(((Boolean) New_New_activity_control.this.statuMap.get(JsonKeys.UV)).booleanValue());
                        New_New_activity_control.this.setNegativeBG(((Boolean) New_New_activity_control.this.statuMap.get("fulizi")).booleanValue());
                        New_New_activity_control.this.setTemp(Integer.parseInt(New_New_activity_control.this.statuMap.get(JsonKeys.TEMP).toString()));
                        New_New_activity_control.this.setHumidity(Integer.parseInt(New_New_activity_control.this.statuMap.get(JsonKeys.Humidity).toString()));
                        New_New_activity_control.this.setAutoBG(((Boolean) New_New_activity_control.this.statuMap.get(JsonKeys.AUTO)).booleanValue());
                        New_New_activity_control.this.setSleepBG(((Boolean) New_New_activity_control.this.statuMap.get(JsonKeys.SLEEP)).booleanValue());
                        if (New_New_activity_control.this.statuMap.containsKey(JsonKeys.Lvxin)) {
                            New_New_activity_control.this.setWaterBG(((Boolean) New_New_activity_control.this.statuMap.get(JsonKeys.Lvxin)).booleanValue());
                            return;
                        }
                        return;
                    }
                    return;
                case 2:
                    Iterator it = New_New_activity_control.this.alarmList.iterator();
                    while (it.hasNext()) {
                        DeviceAlarm deviceAlarm = (DeviceAlarm) it.next();
                        if (!New_New_activity_control.this.isAlarmList.contains(deviceAlarm.getDesc())) {
                            New_New_activity_control.this.isAlarmList.add(deviceAlarm.getDesc());
                            New_New_activity_control.this.isAlarm = false;
                        }
                    }
                    New_New_activity_control.this.isAlarmList.clear();
                    it = New_New_activity_control.this.alarmList.iterator();
                    while (it.hasNext()) {
                        New_New_activity_control.this.isAlarmList.add(((DeviceAlarm) it.next()).getDesc());
                    }
                    if (New_New_activity_control.this.alarmList == null || New_New_activity_control.this.alarmList.size() <= 0) {
                        New_New_activity_control.this.setTipsLayoutVisiblity(false, 0);
                        return;
                    }
                    if (!New_New_activity_control.this.isAlarm) {
                        if (New_New_activity_control.this.mFaultDialog == null) {
                            New_New_activity_control.this.mFaultDialog = DialogManager.getDeviceErrirDialog(New_New_activity_control.this, "设备故障", new OnClickListener() {
                                public void onClick(View view) {
                                    New_New_activity_control.this.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:10086")));
                                    New_New_activity_control.this.mFaultDialog.dismiss();
                                    New_New_activity_control.this.mFaultDialog = null;
                                }
                            });
                        }
                        New_New_activity_control.this.mFaultDialog.show();
                        New_New_activity_control.this.isAlarm = true;
                    }
                    New_New_activity_control.this.setTipsLayoutVisiblity(true, New_New_activity_control.this.alarmList.size());
                    return;
                case 3:
                    DialogManager.dismissDialog(New_New_activity_control.this, New_New_activity_control.this.progressDialogRefreshing);
                    DialogManager.dismissDialog(New_New_activity_control.this, New_New_activity_control.this.mFaultDialog);
                    DialogManager.showDialog(New_New_activity_control.this, New_New_activity_control.this.mDisconnectDialog);
                    return;
                case 4:
                    try {
                        if (New_New_activity_control.this.deviceDataMap.get("data") != null) {
                            Log.i("info", (String) New_New_activity_control.this.deviceDataMap.get("data"));
                            New_New_activity_control.this.inputDataToMaps(New_New_activity_control.this.statuMap, (String) New_New_activity_control.this.deviceDataMap.get("data"));
                        }
                        New_New_activity_control.this.alarmList.clear();
                        if (New_New_activity_control.this.deviceDataMap.get("alters") != null) {
                            Log.i("info", (String) New_New_activity_control.this.deviceDataMap.get("alters"));
                            New_New_activity_control.this.inputAlarmToList((String) New_New_activity_control.this.deviceDataMap.get("alters"));
                        }
                        if (New_New_activity_control.this.deviceDataMap.get("faults") != null) {
                            Log.i("info", (String) New_New_activity_control.this.deviceDataMap.get("faults"));
                            New_New_activity_control.this.inputAlarmToList((String) New_New_activity_control.this.deviceDataMap.get("faults"));
                        }
                        New_New_activity_control.this.handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
                        New_New_activity_control.this.handler.sendEmptyMessage(handler_key.ALARM.ordinal());
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                case 5:
                    New_New_activity_control.this.mCenter.cGetStatus(New_New_activity_control.mXpgWifiDevice);
                    return;
                case 6:
                    New_New_activity_control.this.handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
                    return;
                case 8:
                    New_New_activity_control.this.handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    New_New_activity_control.this.refreshMainControl();
                    return;
                case 9:
                    New_New_activity_control.this.handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    New_New_activity_control.this.handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
                    return;
                case 10:
                    New_New_activity_control.this.isTimeOut = true;
                    New_New_activity_control.this.handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
                    return;
                default:
                    return;
            }
        }
    };
    private ImageView homeQualityResult_iv;
    private TextView homeQualityResult_tv;
    private boolean isAlarm;
    private ArrayList<String> isAlarmList;
    private boolean isClick;
    private boolean isTimeOut = false;
    private ImageView ivTitleLeft;
    private ImageView ivTitleRight;
    private int last_pm = 0;
    private LinearInterpolator lin;
    private Dialog mDisconnectDialog;
    private Dialog mFaultDialog;
    float mL = 0.0f;
    float mR = 0.0f;
    float mW = 0.0f;
    float mW100 = 0.0f;
    private RelativeLayout main_layout;
    private ActivityManager manager;
    private ImageView negative_iv;
    private TextView pm25_tv;
    private TextView pm25_tv2;
    private ImageView pm_bg;
    Dialog powerDialog = null;
    private ProgressDialog progressDialogRefreshing;
    private TextView qualityLightO_tv;
    private RelativeLayout rlAlarmTips;
    private TextView shidu_tv;
    private ImageView silent_iv;
    private LinearLayout silent_ll;
    private TextView sleep_text;
    private int speed = 0;
    private ConcurrentHashMap<String, Object> statuMap;
    private int target_degree = 0;
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
    private ImageView water_iv;
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

    private void initUI() {
        this.childLock_layout = (RelativeLayout) findViewById(R.id.childLock_layout);
        this.childLock_iv = (ImageView) findViewById(R.id.childLock_iv);
        this.childLock_iv.setOnClickListener(this);
        this.auto_text = (TextView) findViewById(R.id.auto_text);
        this.sleep_text = (TextView) findViewById(R.id.sleep_text);
        this.main_layout = (RelativeLayout) findViewById(R.id.main_layout);
        this.pm_bg = (ImageView) findViewById(R.id.pm_set);
        this.auto_ll = (LinearLayout) findViewById(R.id.auto_ll);
        this.silent_ll = (LinearLayout) findViewById(R.id.silent_ll);
        this.auto_ll.setOnClickListener(this);
        this.silent_ll.setOnClickListener(this);
        this.turnOff_layout = (RelativeLayout) findViewById(R.id.turnOff_layout);
        this.turnOn_iv = (ImageView) findViewById(R.id.turnOn_iv);
        this.turnOn_iv.setOnClickListener(this);
        this.back_btn = (Button) findViewById(R.id.back_btn);
        this.back_layout = (RelativeLayout) findViewById(R.id.back_layout);
        this.ivTitleRight = (ImageView) findViewById(R.id.ivPower);
        this.homeQualityResult_tv = (TextView) findViewById(R.id.homeQualityResult_tv);
        this.homeQualityResult_iv = (ImageView) findViewById(R.id.homeQualityResult_iv);
        this.functions_layout = (LinearLayout) findViewById(R.id.functions_layout);
        this.water_iv = (ImageView) findViewById(R.id.water_iv);
        this.water_iv.setOnClickListener(this);
        this.ivTitleRight.setOnClickListener(this);
        this.temp_tv = (TextView) findViewById(R.id.temp_tv);
        this.pm25_tv2 = (TextView) findViewById(R.id.pm25_tv2);
        this.pm25_tv = (TextView) findViewById(R.id.pm25_tv);
        this.shidu_tv = (TextView) findViewById(R.id.shidu_tv);
        this.childLockO_iv = (ImageView) findViewById(R.id.childLockO_iv);
        this.childLockO_iv.setOnClickListener(this);
        this.qualityLightO_tv = (TextView) findViewById(R.id.qualityLightO_tv);
        this.wind_speed_iv = (ImageView) findViewById(R.id.wind_speed_iv);
        this.wind_speed_ll = (LinearLayout) findViewById(R.id.wind_speed_ll);
        this.wind_speed_ll.setOnClickListener(this);
        this.timingOff_tv = (TextView) findViewById(R.id.timingOff_tv);
        this.timingOff_iv = (ImageView) findViewById(R.id.timingOff_iv);
        this.timingOff_ll = (LinearLayout) findViewById(R.id.timingOff_ll);
        this.timingOff_ll.setOnClickListener(this);
        this.auto_iv = (ImageView) findViewById(R.id.auto_iv);
        this.uv_iv = (ImageView) findViewById(R.id.uv_iv);
        this.uv_iv.setOnClickListener(this);
        this.negative_iv = (ImageView) findViewById(R.id.negative_iv);
        this.negative_iv.setOnClickListener(this);
        this.silent_iv = (ImageView) findViewById(R.id.silent_iv);
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
                DialogManager.dismissDialog(New_New_activity_control.this, New_New_activity_control.this.mDisconnectDialog);
                IntentUtils.getInstance().startActivity(New_New_activity_control.this, DeviceListActivity.class);
                New_New_activity_control.this.finish();
            }
        });
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
        this.pm25_tv2.setText(new StringBuilder(String.valueOf(i)).toString());
        if (i < 0 || i > 50) {
            if (50 < i && i <= 100) {
                this.pm25_tv.setTextColor(-15238973);
                this.homeQualityResult_tv.setTextColor(-15238973);
                this.homeQualityResult_tv.setText("良");
            } else if (101 >= i || i > 150) {
                this.pm25_tv.setTextColor(-3244253);
                this.homeQualityResult_tv.setTextColor(-3244253);
                this.homeQualityResult_tv.setText("差");
            } else {
                this.pm25_tv.setTextColor(-5200093);
                this.homeQualityResult_tv.setTextColor(-5200093);
                this.homeQualityResult_tv.setText("中");
            }
        } else if (i == 0) {
            this.pm25_tv.setText("---");
            this.pm25_tv2.setText("---");
            this.pm25_tv.setTextColor(-15160752);
            this.homeQualityResult_tv.setTextColor(-15160752);
            this.homeQualityResult_tv.setText("预热");
        } else {
            this.pm25_tv.setTextColor(-15160752);
            this.homeQualityResult_tv.setTextColor(-15160752);
            this.homeQualityResult_tv.setText("优");
        }
    }

    private void updateTips(final int i) {
        this.target_degree = (((i - this.last_pm) * 9) / 25) + this.current_degree;
        System.out.println("角度  " + this.current_degree + "   " + this.target_degree);
        runOnUiThread(new Runnable() {
            public void run() {
                New_New_activity_control.this.am = new RotateAnimation((float) New_New_activity_control.this.current_degree, (float) New_New_activity_control.this.target_degree, 1, 0.5f, 1, 0.5f);
                New_New_activity_control.this.am.setDuration(500);
                New_New_activity_control.this.am.setInterpolator(New_New_activity_control.this.lin);
                New_New_activity_control.this.am.setFillAfter(true);
                New_New_activity_control.this.pm_bg.setAnimation(New_New_activity_control.this.am);
                New_New_activity_control.this.am.start();
            }
        });
        this.am.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                New_New_activity_control.this.last_pm = i;
                New_New_activity_control.this.current_degree = New_New_activity_control.this.target_degree;
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
    }

    public void changeSpeedBg(int i) {
        this.speed = i;
        this.qualityLightO_tv.setText((i + 1) + "档");
        this.qualityLightO_tv.setTextColor(-14112501);
    }

    protected void didDisconnected(XPGWifiDevice xPGWifiDevice) {
        if (xPGWifiDevice.getDid().equalsIgnoreCase(mXpgWifiDevice.getDid())) {
            this.handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
        }
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
                        New_New_activity_control.this.mCenter.cSwitchOn(New_New_activity_control.mXpgWifiDevice, false);
                        New_New_activity_control.this.setSwitch(false);
                        New_New_activity_control.this.powerDialog.dismiss();
                    }
                });
                this.powerDialog.show();
                return;
            case R.id.rlAlarmTips /*2131165216*/:
                Intent intent = new Intent(this, AdvancedActivity.class);
                intent.putExtra("advanced_set", AdvanceType.alarm);
                startActivity(intent);
                return;
            case R.id.childLock_iv /*2131165227*/:
                this.mCenter.cChildLock(mXpgWifiDevice, false);
                setChildLock(false);
                return;
            case R.id.push_iv /*2131165240*/:
                troggleBottom();
                return;
            case R.id.childLockO_iv /*2131165246*/:
                this.mCenter.cChildLock(mXpgWifiDevice, true);
                setChildLock(true);
                return;
            case R.id.timingOff_ll /*2131165251*/:
                DialogManager.getWheelTimingDialog(this, new OnTimingChosenListener() {
                    public void timingChosen(int i) {
                        New_New_activity_control.this.mCenter.cCountDownOff(New_New_activity_control.mXpgWifiDevice, i);
                        New_New_activity_control.this.timingOff = i;
                        if (i == 0) {
                            New_New_activity_control.this.timingOff_tv.setText("定时关机");
                            New_New_activity_control.this.timingOff_iv.setImageResource(R.drawable.icon_4);
                            return;
                        }
                        New_New_activity_control.this.timingOff_tv.setText(new StringBuilder(String.valueOf(i)).append("小时").toString());
                        New_New_activity_control.this.timingOff_iv.setImageResource(R.drawable.icon_4_2);
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
                        New_New_activity_control.this.mCenter.cSetSpeed(New_New_activity_control.mXpgWifiDevice, i);
                    }
                }, " 风速调节", this.speed).show();
                return;
            case R.id.water_iv /*2131165300*/:
                this.FixDialog = DialogManager.getFixDialog(this, new OnClickListener() {
                    public void onClick(View view) {
                        New_New_activity_control.this.FixDialog.dismiss();
                    }
                });
                this.FixDialog.show();
                return;
            case R.id.auto_ll /*2131165301*/:
                if (this.auto_iv.getTag().toString().equals("0")) {
                    setAutoBG(true);
                    this.mCenter.cAuto(mXpgWifiDevice, true);
                    return;
                }
                setAutoBG(false);
                this.mCenter.cAuto(mXpgWifiDevice, false);
                return;
            case R.id.silent_ll /*2131165303*/:
                if (this.silent_iv.getTag().toString().equals("0")) {
                    setSleepBG(true);
                    this.mCenter.cSetSleep(mXpgWifiDevice, true);
                    return;
                }
                setSleepBG(false);
                this.mCenter.cSetSleep(mXpgWifiDevice, false);
                return;
            default:
                return;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_control_new_new);
        this.manager = new ActivityManager();
        this.manager.applyKitKatTranslucency(this, -1579033);
        initUI();
        this.lin = new LinearInterpolator();
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

    public void setAutoBG(boolean z) {
        if (z) {
            this.auto_text.setTextColor(-14112501);
            this.auto_iv.setTag("1");
            this.auto_iv.setBackgroundResource(R.drawable.icon_intelligence_select);
            return;
        }
        this.auto_text.setTextColor(-11316397);
        this.auto_iv.setTag("0");
        this.auto_iv.setBackgroundResource(R.drawable.icon_intelligence_not_select);
    }

    public void setChildLock(boolean z) {
        if (z) {
            this.childLockO_iv.setImageResource(R.drawable.icon_2_2);
            this.childLockO_iv.setTag("0");
            this.childLock_layout.setVisibility(0);
            return;
        }
        this.childLockO_iv.setImageResource(R.drawable.icon_2);
        this.childLockO_iv.setTag("1");
        this.childLock_layout.setVisibility(8);
    }

    public void setHumidity(int i) {
        this.shidu_tv.setText(new StringBuilder(String.valueOf(i)).append("%").toString());
    }

    public void setNegativeBG(boolean z) {
        if (z) {
            this.negative_iv.setTag("1");
            this.negative_iv.setImageResource(R.drawable.icon_strong_select);
            return;
        }
        this.negative_iv.setTag("0");
        this.negative_iv.setImageResource(R.drawable.icon_strong_not_select);
    }

    public void setSleepBG(boolean z) {
        if (z) {
            this.sleep_text.setTextColor(-14112501);
            this.silent_iv.setTag("1");
            this.silent_iv.setBackgroundResource(R.drawable.icon_sleep_select);
            return;
        }
        this.sleep_text.setTextColor(-11316397);
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
            this.timingOff_tv.setTextColor(-11316397);
            this.timingOff_tv.setText("定时关机");
            this.timingOff_iv.setBackgroundResource(R.drawable.icon_4);
            return;
        }
        this.timingOff_tv.setTextColor(-14112501);
        this.timingOff_tv.setText(new StringBuilder(String.valueOf(i)).append("小时").toString());
        this.timingOff_iv.setBackgroundResource(R.drawable.icon_4_2);
    }

    public void setUVBG(boolean z) {
        if (z) {
            this.uv_iv.setTag("1");
            this.uv_iv.setImageResource(R.drawable.icon_standard_select);
            return;
        }
        this.uv_iv.setTag("0");
        this.uv_iv.setImageResource(R.drawable.icon_standard_not_select);
    }

    public void setWaterBG(boolean z) {
        if (z) {
            this.water_iv.setTag("1");
            this.water_iv.setImageResource(R.drawable.water_green);
            return;
        }
        this.water_iv.setTag("0");
        this.water_iv.setImageResource(R.drawable.water_gray);
    }

    public void troggleBottom() {
        LayoutParams layoutParams = (LayoutParams) this.functions_layout.getLayoutParams();
        if (layoutParams.bottomMargin == 0) {
            layoutParams.bottomMargin = PxUtil.dip2px(this, -81.0f);
            this.back_layout.setVisibility(8);
        } else {
            layoutParams.bottomMargin = 0;
            this.back_layout.setVisibility(0);
        }
        this.functions_layout.setLayoutParams(layoutParams);
    }
}
