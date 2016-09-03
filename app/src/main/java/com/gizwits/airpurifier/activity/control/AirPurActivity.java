package com.gizwits.airpurifier.activity.control;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gizwits.airpurifier.activity.advanced.AdvancedActivity;
import com.gizwits.framework.Interface.OnDialogOkClickListenner;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.account.UserManageActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.framework.activity.device.DeviceManageListActivity;
import com.gizwits.framework.activity.help.AboutActivity;
import com.gizwits.framework.activity.help.HelpActivity;
import com.gizwits.framework.adapter.MenuDeviceAdapter;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.entity.AdvanceType;
import com.gizwits.framework.entity.DeviceAlarm;
import com.gizwits.framework.utils.DensityUtil;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.utils.DialogManager.OnTimingChosenListener;
import com.gizwits.framework.utils.PxUtil;
import com.gizwits.framework.webservice.GetPMService;
import com.gizwits.framework.webservice.LocationService;
import com.gizwits.framework.widget.SlidingMenu;
import com.gizwits.framework.widget.SlidingMenu.SlidingMenuListener;
import com.uh.all.airpurifier.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.DateUtil;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class AirPurActivity extends BaseActivity implements OnClickListener, OnTouchListener, SlidingMenuListener {
    private static SlidingMenu mView;
    private int GetStatueTimeOut = 30000;
    private int LoginTimeOut = 5000;
    private final String TAG = "AirPurActivity";
    private ArrayList<DeviceAlarm> alarmList;
    private ImageView auto_iv;
    private Button back_btn;
    private RelativeLayout back_layout;
    private ImageView bottom_push;
    private ImageView childLockO_iv;
    private LinearLayout childLockO_ll;
    private ImageView childLock_iv;
    private int currentOn = 0;
    private ConcurrentHashMap<String, Object> deviceDataMap;
    private LinearLayout functions_layout;
    Handler handler = new Handler() {
        private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$airpurifier$activity$control$AirPurActivity$handler_key;

        static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$airpurifier$activity$control$AirPurActivity$handler_key() {
            int[] iArr = $SWITCH_TABLE$com$gizwits$airpurifier$activity$control$AirPurActivity$handler_key;
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
                $SWITCH_TABLE$com$gizwits$airpurifier$activity$control$AirPurActivity$handler_key = iArr;
            }
            return iArr;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (AnonymousClass3.$SWITCH_TABLE$com$gizwits$airpurifier$activity$control$AirPurActivity$handler_key()[handler_key.values()[message.what].ordinal()]) {
                case 1:
                    break;
                case 2:
                    if (!AirPurActivity.mView.isOpen()) {
                        Iterator it = AirPurActivity.this.alarmList.iterator();
                        while (it.hasNext()) {
                            DeviceAlarm deviceAlarm = (DeviceAlarm) it.next();
                            if (!AirPurActivity.this.isAlarmList.contains(deviceAlarm.getDesc())) {
                                AirPurActivity.this.isAlarmList.add(deviceAlarm.getDesc());
                                AirPurActivity.this.isAlarm = false;
                            }
                        }
                        AirPurActivity.this.isAlarmList.clear();
                        it = AirPurActivity.this.alarmList.iterator();
                        while (it.hasNext()) {
                            AirPurActivity.this.isAlarmList.add(((DeviceAlarm) it.next()).getDesc());
                        }
                        if (AirPurActivity.this.alarmList == null || AirPurActivity.this.alarmList.size() <= 0) {
                            AirPurActivity.this.setTipsLayoutVisiblity(false, 0);
                            return;
                        }
                        if (!AirPurActivity.this.isAlarm) {
                            if (AirPurActivity.this.mFaultDialog == null) {
                                AirPurActivity.this.mFaultDialog = DialogManager.getDeviceErrirDialog(AirPurActivity.this, "设备故障", new OnClickListener() {
                                    public void onClick(View view) {
                                        AirPurActivity.this.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:10086")));
                                        AirPurActivity.this.mFaultDialog.dismiss();
                                        AirPurActivity.this.mFaultDialog = null;
                                    }
                                });
                            }
                            AirPurActivity.this.mFaultDialog.show();
                            AirPurActivity.this.isAlarm = true;
                        }
                        AirPurActivity.this.setTipsLayoutVisiblity(true, AirPurActivity.this.alarmList.size());
                        return;
                    }
                    return;
                case 3:
                    if (!AirPurActivity.mView.isOpen()) {
                        DialogManager.dismissDialog(AirPurActivity.this, AirPurActivity.this.progressDialogRefreshing);
                        DialogManager.dismissDialog(AirPurActivity.this, AirPurActivity.this.mFaultDialog);
                        DialogManager.showDialog(AirPurActivity.this, AirPurActivity.this.mDisconnectDialog);
                        return;
                    }
                    return;
                case 4:
                    try {
                        if (AirPurActivity.this.deviceDataMap.get("data") != null) {
                            Log.i("info", (String) AirPurActivity.this.deviceDataMap.get("data"));
                            AirPurActivity.this.inputDataToMaps(AirPurActivity.this.statuMap, (String) AirPurActivity.this.deviceDataMap.get("data"));
                        }
                        AirPurActivity.this.alarmList.clear();
                        if (AirPurActivity.this.deviceDataMap.get("alters") != null) {
                            Log.i("info", (String) AirPurActivity.this.deviceDataMap.get("alters"));
                            AirPurActivity.this.inputAlarmToList((String) AirPurActivity.this.deviceDataMap.get("alters"));
                        }
                        if (AirPurActivity.this.deviceDataMap.get("faults") != null) {
                            Log.i("info", (String) AirPurActivity.this.deviceDataMap.get("faults"));
                            AirPurActivity.this.inputAlarmToList((String) AirPurActivity.this.deviceDataMap.get("faults"));
                        }
                        AirPurActivity.this.handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
                        AirPurActivity.this.handler.sendEmptyMessage(handler_key.ALARM.ordinal());
                        break;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        break;
                    }
                case 5:
                    AirPurActivity.this.mCenter.cGetStatus(AirPurActivity.mXpgWifiDevice);
                    return;
                case 6:
                    AirPurActivity.this.handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
                    return;
                case 8:
                    AirPurActivity.this.handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    AirPurActivity.this.refreshMainControl();
                    return;
                case 9:
                    AirPurActivity.this.handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    AirPurActivity.this.handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
                    return;
                case 10:
                    AirPurActivity.this.isTimeOut = true;
                    AirPurActivity.this.handler.sendEmptyMessage(handler_key.DISCONNECTED.ordinal());
                    return;
                default:
                    return;
            }
            if (!AirPurActivity.mView.isOpen() && AirPurActivity.this.statuMap != null && AirPurActivity.this.statuMap.size() > 0) {
                AirPurActivity.this.handler.removeMessages(handler_key.GET_STATUE_TIMEOUT.ordinal());
                AirPurActivity.this.changeRUNmodeBg(Integer.parseInt(AirPurActivity.this.statuMap.get(JsonKeys.FAN_SPEED).toString()));
                AirPurActivity.this.setChildLock(((Boolean) AirPurActivity.this.statuMap.get(JsonKeys.Child_Lock)).booleanValue());
                AirPurActivity.this.setIndicatorLight(((Boolean) AirPurActivity.this.statuMap.get(JsonKeys.LED)).booleanValue());
                AirPurActivity.this.setPlasma(((Boolean) AirPurActivity.this.statuMap.get("fulizi")).booleanValue());
                AirPurActivity.this.setSwitch(((Boolean) AirPurActivity.this.statuMap.get(JsonKeys.ON_OFF)).booleanValue());
                int minCastToHour = DateUtil.minCastToHour(Integer.parseInt(AirPurActivity.this.statuMap.get("time_on").toString()));
                if (DateUtil.minCastToHourMore(Integer.parseInt(AirPurActivity.this.statuMap.get("time_on").toString())) != 0) {
                    minCastToHour = DateUtil.minCastToHour(Integer.parseInt(AirPurActivity.this.statuMap.get("time_on").toString())) + 1;
                }
                AirPurActivity.this.setTimingOn(minCastToHour);
                minCastToHour = DateUtil.minCastToHour(Integer.parseInt(AirPurActivity.this.statuMap.get("time_on").toString()));
                if (DateUtil.minCastToHourMore(Integer.parseInt(AirPurActivity.this.statuMap.get("time_on").toString())) != 0) {
                    minCastToHour = DateUtil.minCastToHour(Integer.parseInt(AirPurActivity.this.statuMap.get("time_on").toString())) + 1;
                }
                AirPurActivity.this.setTimingOff(minCastToHour);
                AirPurActivity.this.updateBackgound(AirPurActivity.this.statuMap.get(JsonKeys.Air_Quality).toString());
                minCastToHour = AirPurActivity.this.statuMap.get(JsonKeys.Air_Quality).toString().equals("1") ? 5 : AirPurActivity.this.statuMap.get(JsonKeys.Air_Quality).toString().equals("2") ? 9 : AirPurActivity.this.statuMap.get(JsonKeys.Air_Quality).toString().equals("3") ? 15 : 0;
                minCastToHour *= 8;
                if (minCastToHour > 100) {
                    minCastToHour = 100;
                }
                AirPurActivity.this.updateTips(((float) (100 - minCastToHour)) * AirPurActivity.this.mW100);
                DialogManager.dismissDialog(AirPurActivity.this, AirPurActivity.this.progressDialogRefreshing);
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
    private ListView lvDevice;
    private MenuDeviceAdapter mAdapter;
    private Dialog mDisconnectDialog;
    private Dialog mFaultDialog;
    float mL = 0.0f;
    float mR = 0.0f;
    float mW = 0.0f;
    float mW100 = 0.0f;
    private RelativeLayout main_layout;
    private OnDialogOkClickListenner okTimingOnClickListenner = new OnDialogOkClickListenner() {
        public void callBack(Object obj) {
            AirPurActivity.this.currentOn = ((Integer) obj).intValue();
        }

        public void onClick(View view) {
            AirPurActivity.this.timeDialog.dismiss();
            AirPurActivity.this.setTimingOn(AirPurActivity.this.currentOn);
        }
    };
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
                        AirPurActivity.this.troggleBottom();
                        this.isProgressing = true;
                        break;
                    }
            }
            return true;
        }
    };
    private TextView outdoorQuality_tv;
    private ImageView palasmaO_iv;
    private LinearLayout palasmaO_ll;
    private ImageView plasma_iv;
    private TextView pm10_tv;
    private TextView pm25_tv;
    Dialog powerDialog = null;
    private ProgressDialog progressDialogRefreshing;
    private ImageView push_iv;
    private ImageView qualityLightO_iv;
    private LinearLayout qualityLightO_ll;
    private ImageView qualityLight_iv;
    private RelativeLayout rlAlarmTips;
    private ImageView silent_iv;
    private ScrollView slMenu;
    private ImageView standar_iv;
    private ConcurrentHashMap<String, Object> statuMap;
    private ImageView strong_iv;
    private Dialog timeDialog;
    private int timingOff;
    private ImageView timingOff_iv;
    private LinearLayout timingOff_ll;
    private TextView timingOff_tv;
    private int timingOn;
    private LinearLayout timingOn_layout;
    private TextView timingOn_tv;
    private RelativeLayout turnOff_layout;
    private ImageView turnOn_iv;
    private TextView tvAlarmTipsCount;
    private TextView tvTitle;

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

    private void backToMain() {
        mXpgWifiDevice = this.mAdapter.getItem(this.mAdapter.getChoosedPos());
        if (!mXpgWifiDevice.isConnected()) {
            loginDevice(mXpgWifiDevice);
            DialogManager.showDialog(this, this.progressDialogRefreshing);
        }
        refreshMainControl();
    }

    public static Bitmap getView() {
        Bitmap createBitmap = Bitmap.createBitmap(mView.getWidth(), mView.getHeight(), Config.ARGB_8888);
        mView.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    private void initQualityTips() {
        this.homeQualityResult_iv.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                float width = (float) AirPurActivity.this.homeQualityResult_iv.getWidth();
                float right = (float) AirPurActivity.this.homeQualityResult_iv.getRight();
                AirPurActivity.this.mL = ((float) AirPurActivity.this.homeQualityResult_iv.getLeft()) + (width / 50.0f);
                AirPurActivity.this.mR = right - (width / 10.0f);
                AirPurActivity.this.mW = AirPurActivity.this.mR - AirPurActivity.this.mL;
                AirPurActivity.this.mW100 = AirPurActivity.this.mW / 100.0f;
            }
        });
    }

    private void initUI() {
        this.main_layout = (RelativeLayout) findViewById(R.id.main_layout);
        this.timingOn_layout = (LinearLayout) findViewById(R.id.timingOn_layout);
        this.timingOn_layout.setOnClickListener(this);
        this.timingOn_tv = (TextView) findViewById(R.id.timingOn_tv);
        this.turnOff_layout = (RelativeLayout) findViewById(R.id.turnOff_layout);
        this.turnOn_iv = (ImageView) findViewById(R.id.turnOn_iv);
        this.turnOn_iv.setOnClickListener(this);
        mView = (SlidingMenu) findViewById(R.id.mView);
        this.homeQualityTip_iv = (ImageView) findViewById(R.id.homeQualityTipArrow_iv);
        this.back_btn = (Button) findViewById(R.id.back_btn);
        this.back_btn.setOnTouchListener(this);
        this.back_layout = (RelativeLayout) findViewById(R.id.back_layout);
        this.push_iv = (ImageView) findViewById(R.id.push_iv);
        this.push_iv.setOnClickListener(this);
        this.homeQualityResult_tv = (TextView) findViewById(R.id.homeQualityResult_tv);
        this.homeQualityResult_iv = (ImageView) findViewById(R.id.homeQualityResult_iv);
        this.functions_layout = (LinearLayout) findViewById(R.id.functions_layout);
        this.plasma_iv = (ImageView) findViewById(R.id.plasama_iv);
        this.childLock_iv = (ImageView) findViewById(R.id.childLock_iv);
        this.qualityLight_iv = (ImageView) findViewById(R.id.qualityLight_iv);
        this.outdoorQuality_tv = (TextView) findViewById(R.id.outdoorQuality_tv);
        this.pm25_tv = (TextView) findViewById(R.id.pm25_tv);
        this.pm10_tv = (TextView) findViewById(R.id.pm10_tv);
        this.bottom_push = (ImageView) findViewById(R.id.bottom_push);
        this.bottom_push.setOnTouchListener(this.onTouchListener);
        this.palasmaO_iv = (ImageView) findViewById(R.id.plasmaO_iv);
        this.palasmaO_ll = (LinearLayout) findViewById(R.id.plasmaO_ll);
        this.palasmaO_ll.setOnClickListener(this);
        this.childLockO_iv = (ImageView) findViewById(R.id.childLockO_iv);
        this.childLockO_ll = (LinearLayout) findViewById(R.id.childLockO_ll);
        this.childLockO_ll.setOnClickListener(this);
        this.qualityLightO_iv = (ImageView) findViewById(R.id.qualityLightO_iv);
        this.qualityLightO_ll = (LinearLayout) findViewById(R.id.qualityLightO_ll);
        this.qualityLightO_ll.setOnClickListener(this);
        this.timingOff_tv = (TextView) findViewById(R.id.timingOff_tv);
        this.timingOff_iv = (ImageView) findViewById(R.id.timingOff_iv);
        this.timingOff_ll = (LinearLayout) findViewById(R.id.timingOff_ll);
        this.timingOff_ll.setOnClickListener(this);
        this.auto_iv = (ImageView) findViewById(R.id.auto_iv);
        this.auto_iv.setOnClickListener(this);
        this.standar_iv = (ImageView) findViewById(R.id.standar_iv);
        this.standar_iv.setOnClickListener(this);
        this.strong_iv = (ImageView) findViewById(R.id.strong_iv);
        this.strong_iv.setOnClickListener(this);
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
        this.mAdapter = new MenuDeviceAdapter(this, bindlist);
        this.lvDevice = (ListView) findViewById(R.id.lvDevice);
        this.lvDevice.setAdapter(this.mAdapter);
        this.slMenu = (ScrollView) findViewById(R.id.slMenu);
        this.progressDialogRefreshing = new ProgressDialog(this);
        this.progressDialogRefreshing.setMessage("正在更新状态,请稍后。");
        this.progressDialogRefreshing.setCancelable(false);
        this.mDisconnectDialog = DialogManager.getDisconnectDialog(this, new OnClickListener() {
            public void onClick(View view) {
                DialogManager.dismissDialog(AirPurActivity.this, AirPurActivity.this.mDisconnectDialog);
                IntentUtils.getInstance().startActivity(AirPurActivity.this, DeviceListActivity.class);
                AirPurActivity.this.finish();
            }
        });
        this.lvDevice.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (AirPurActivity.this.mAdapter.getItem(i).isOnline()) {
                    if (AirPurActivity.this.mAdapter.getChoosedPos() != i) {
                        AirPurActivity.this.isAlarmList.clear();
                        AirPurActivity.this.mAdapter.setChoosedPos(i);
                        AirPurActivity.mXpgWifiDevice = (XPGWifiDevice) AirPurActivity.bindlist.get(i);
                    }
                    AirPurActivity.mView.toggle();
                }
            }
        });
        mView.setSlidingMenuListener(this);
        initQualityTips();
        refreshMenu();
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

    private void loginDevice(XPGWifiDevice xPGWifiDevice) {
        mXpgWifiDevice = xPGWifiDevice;
        mXpgWifiDevice.setListener(this.deviceListener);
        mXpgWifiDevice.login(this.setmanager.getUid(), this.setmanager.getToken());
        this.isTimeOut = false;
        this.handler.sendEmptyMessageDelayed(handler_key.LOGIN_TIMEOUT.ordinal(), (long) this.LoginTimeOut);
    }

    private void refreshMainControl() {
        mXpgWifiDevice.setListener(this.deviceListener);
        DialogManager.showDialog(this, this.progressDialogRefreshing);
        this.handler.sendEmptyMessageDelayed(handler_key.GET_STATUE_TIMEOUT.ordinal(), (long) this.GetStatueTimeOut);
        this.handler.sendEmptyMessage(handler_key.GET_STATUE.ordinal());
    }

    private void refreshMenu() {
        initBindList();
        this.mAdapter.setChoosedPos(-1);
        for (int i = 0; i < bindlist.size(); i++) {
            if (((XPGWifiDevice) bindlist.get(i)).getDid().equalsIgnoreCase(mXpgWifiDevice.getDid())) {
                this.mAdapter.setChoosedPos(i);
            }
        }
        if (this.mAdapter.getChoosedPos() == -1) {
            this.mAdapter.setChoosedPos(0);
            mXpgWifiDevice = this.mAdapter.getItem(0);
        }
        this.mAdapter.notifyDataSetChanged();
        this.lvDevice.setLayoutParams(new LayoutParams(-2, DensityUtil.dip2px(this, (float) (this.mAdapter.getCount() * 50))));
    }

    private void setTipsLayoutVisiblity(boolean z, int i) {
        this.rlAlarmTips.setVisibility(z ? 0 : 8);
        this.tvAlarmTipsCount.setText(new StringBuilder(String.valueOf(i)).toString());
    }

    private void updateBackgound(String str) {
        if (str.equals("0")) {
            this.main_layout.setBackgroundResource(R.drawable.good_bg);
            this.homeQualityResult_tv.setText("优");
        } else if (str.equals("1")) {
            this.main_layout.setBackgroundResource(R.drawable.liang_bg);
            this.homeQualityResult_tv.setText("良");
        } else if (str.equals("2")) {
            this.main_layout.setBackgroundResource(R.drawable.middle_bg);
            this.homeQualityResult_tv.setText("中");
        } else if (str.equals("3")) {
            this.main_layout.setBackgroundResource(R.drawable.bad_bg);
            this.homeQualityResult_tv.setText("差");
        }
    }

    private void updateTips(final float f) {
        runOnUiThread(new Runnable() {
            public void run() {
                AirPurActivity.this.homeQualityTip_iv.setX(AirPurActivity.this.mL + f);
            }
        });
    }

    public void CloseFinish() {
        backToMain();
    }

    public void OpenFinish() {
    }

    public void bottomFucTrogg() {
        this.push_iv.setVisibility(0);
    }

    public void changeRUNmodeBg(int i) {
        reAll();
        if (i == 2) {
            setSilentAnimation();
        }
        if (i == 1) {
            setStandarAnimation();
        }
        if (i == 0) {
            setStrongAnimation();
        }
        if (i == 3) {
            setAutoAnimation();
        }
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

    public void getPm(String str) {
        new GetPMService() {
            public void onFailed() {
                Toast.makeText(AirPurActivity.this, "PM2.5获取失败", 0).show();
            }

            public void onSuccess(JSONObject jSONObject) {
                try {
                    Log.e(JsonKeys.Air_Quality, jSONObject.toString());
                    JSONObject jSONObject2 = jSONObject.getJSONObject("result");
                    AirPurActivity.this.pm25_tv.setText(jSONObject2.getString("pm2_5").split("\\.")[0]);
                    AirPurActivity.this.pm10_tv.setText(jSONObject2.getString("pm10").split("\\.")[0]);
                    int i = jSONObject2.getInt("aqi");
                    Log.e("aqi", i);
                    if (i > 0 && i <= 50) {
                        AirPurActivity.this.outdoorQuality_tv.setText("优");
                    } else if (50 < i && i <= 100) {
                        AirPurActivity.this.outdoorQuality_tv.setText("良");
                    } else if (101 >= i || i > 150) {
                        AirPurActivity.this.outdoorQuality_tv.setText("差");
                    } else {
                        AirPurActivity.this.outdoorQuality_tv.setText("中");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.GetWeather(str);
    }

    public void initCity() {
        new LocationService() {
            public void onFailed() {
                Toast.makeText(AirPurActivity.this, "城市定位失败", 0).show();
            }

            public void onSuccess(JSONObject jSONObject) {
                try {
                    String[] split = jSONObject.getString("address").split("\\|");
                    Log.e("city-json", jSONObject.toString());
                    AirPurActivity.this.getPm(split[2]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.startLocation();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        this.isClick = false;
    }

    public void onBackPressed() {
        if (mView.isOpen()) {
            mView.toggle();
            return;
        }
        if (mXpgWifiDevice != null && mXpgWifiDevice.isConnected()) {
            this.mCenter.cDisconnect(mXpgWifiDevice);
            DisconnectOtherDevice();
        }
        finish();
    }

    public void onClick(View view) {
        int i = 24;
        if (!mView.isOpen()) {
            OnTimingChosenListener anonymousClass8;
            switch (view.getId()) {
                case R.id.ivMenu /*2131165213*/:
                    this.slMenu.scrollTo(0, 0);
                    mView.toggle();
                    return;
                case R.id.ivPower /*2131165215*/:
                    this.powerDialog = DialogManager.getPowerOffDialog(this, new OnClickListener() {
                        public void onClick(View view) {
                            AirPurActivity.this.mCenter.cSwitchOn(AirPurActivity.mXpgWifiDevice, false);
                            AirPurActivity.this.setSwitch(false);
                            AirPurActivity.this.powerDialog.dismiss();
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
                    this.mCenter.cSetSpeed(mXpgWifiDevice, 2);
                    changeRUNmodeBg(2);
                    return;
                case R.id.standar_iv /*2131165234*/:
                    this.mCenter.cSetSpeed(mXpgWifiDevice, 1);
                    changeRUNmodeBg(1);
                    return;
                case R.id.strong_iv /*2131165235*/:
                    this.mCenter.cSetSpeed(mXpgWifiDevice, 0);
                    changeRUNmodeBg(0);
                    return;
                case R.id.auto_iv /*2131165236*/:
                    this.mCenter.cSetSpeed(mXpgWifiDevice, 3);
                    changeRUNmodeBg(3);
                    return;
                case R.id.push_iv /*2131165240*/:
                    troggleBottom();
                    return;
                case R.id.plasmaO_ll /*2131165242*/:
                    if (this.palasmaO_ll.getTag().toString() == "0") {
                        this.mCenter.cSwitchPlasma(mXpgWifiDevice, false);
                        setPlasma(false);
                        return;
                    }
                    this.mCenter.cSwitchPlasma(mXpgWifiDevice, true);
                    setPlasma(true);
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
                case R.id.qualityLightO_ll /*2131165248*/:
                    if (this.qualityLightO_ll.getTag().toString() == "0") {
                        this.mCenter.cLED(mXpgWifiDevice, false);
                        setIndicatorLight(false);
                        return;
                    }
                    this.mCenter.cLED(mXpgWifiDevice, true);
                    setIndicatorLight(true);
                    return;
                case R.id.timingOff_ll /*2131165251*/:
                    anonymousClass8 = new OnTimingChosenListener() {
                        public void timingChosen(int i) {
                            AirPurActivity.this.mCenter.cCountDownOff(AirPurActivity.mXpgWifiDevice, DateUtil.hourCastToMin(i));
                            AirPurActivity.this.timingOff = i;
                            if (i == 0) {
                                AirPurActivity.this.timingOff_tv.setText("定时关机");
                                AirPurActivity.this.timingOff_iv.setImageResource(R.drawable.icon_4);
                                return;
                            }
                            AirPurActivity.this.timingOff_tv.setText(new StringBuilder(String.valueOf(i)).append("小时").toString());
                            AirPurActivity.this.timingOff_iv.setImageResource(R.drawable.icon_4_2);
                        }
                    };
                    if (this.timingOff != 0) {
                        i = this.timingOff - 1;
                    }
                    DialogManager.getWheelTimingDialog(this, anonymousClass8, " 定时关机", i).show();
                    return;
                case R.id.turnOn_iv /*2131165255*/:
                    this.mCenter.cSwitchOn(mXpgWifiDevice, true);
                    setSwitch(true);
                    return;
                case R.id.timingOn_layout /*2131165256*/:
                    anonymousClass8 = new OnTimingChosenListener() {
                        public void timingChosen(int i) {
                            AirPurActivity.this.mCenter.cCountDownOn(AirPurActivity.mXpgWifiDevice, DateUtil.hourCastToMin(i));
                            AirPurActivity.this.timingOn = i;
                            if (i != 0) {
                                AirPurActivity.this.timingOn_tv.setText(new StringBuilder(String.valueOf(i)).append("小时").toString());
                            } else {
                                AirPurActivity.this.timingOn_tv.setText("定时开机");
                            }
                        }
                    };
                    if (this.timingOn != 0) {
                        i = this.timingOn - 1;
                    }
                    DialogManager.getWheelTimingDialog(this, anonymousClass8, " 定时开机", i).show();
                    return;
                default:
                    return;
            }
        }
    }

    public void onClickSlipBar(View view) {
        switch (view.getId()) {
            case R.id.rlDevice /*2131165344*/:
                IntentUtils.getInstance().startActivity(this, DeviceManageListActivity.class);
                return;
            case R.id.rlAccount /*2131165347*/:
                IntentUtils.getInstance().startActivity(this, UserManageActivity.class);
                return;
            case R.id.rlFunction /*2131165349*/:
                IntentUtils.getInstance().startActivity(this, AdvancedActivity.class);
                return;
            case R.id.rlCount /*2131165351*/:
                IntentUtils.getInstance().startActivity(this, CurveActivity.class);
                return;
            case R.id.rlHelp /*2131165353*/:
                IntentUtils.getInstance().startActivity(this, HelpActivity.class);
                return;
            case R.id.rlAbout /*2131165355*/:
                IntentUtils.getInstance().startActivity(this, AboutActivity.class);
                return;
            case R.id.btnDeviceList /*2131165357*/:
                this.mCenter.cDisconnect(mXpgWifiDevice);
                DisconnectOtherDevice();
                IntentUtils.getInstance().startActivity(this, DeviceListActivity.class);
                finish();
                return;
            default:
                return;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_airpur_control);
        initUI();
        initCity();
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
        if (mView.isOpen()) {
            refreshMenu();
        } else if (!this.mDisconnectDialog.isShowing()) {
            refreshMainControl();
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.back_btn && motionEvent.getAction() == 0) {
            troggleBottom();
        }
        return true;
    }

    public void reAll() {
        this.silent_iv.setBackgroundResource(R.drawable.icon_sleep_not_select);
        this.standar_iv.setBackgroundResource(R.drawable.icon_standard_not_select);
        this.strong_iv.setBackgroundResource(R.drawable.icon_strong_not_select);
        this.auto_iv.setBackgroundResource(R.drawable.icon_intelligence_not_select);
    }

    public void setAutoAnimation() {
        this.auto_iv.setBackgroundResource(R.drawable.icon_intelligence_select);
    }

    public void setChildLock(boolean z) {
        if (z) {
            this.childLockO_iv.setImageResource(R.drawable.icon_2_2);
            this.childLockO_ll.setTag("0");
            this.childLock_iv.setImageResource(R.drawable.lock_select);
            return;
        }
        this.childLockO_iv.setImageResource(R.drawable.icon_2);
        this.childLockO_ll.setTag("1");
        this.childLock_iv.setImageResource(R.drawable.lock_not_select);
    }

    public void setIndicatorLight(boolean z) {
        if (z) {
            this.qualityLightO_iv.setImageResource(R.drawable.icon_1_2);
            this.qualityLightO_ll.setTag("0");
            this.qualityLight_iv.setImageResource(R.drawable.quality_select);
            return;
        }
        this.qualityLightO_iv.setImageResource(R.drawable.icon_1);
        this.qualityLightO_ll.setTag("1");
        this.qualityLight_iv.setImageResource(R.drawable.quality_not_select);
    }

    public void setPlasma(boolean z) {
        if (z) {
            this.palasmaO_iv.setImageResource(R.drawable.icon_3_2);
            this.palasmaO_ll.setTag("0");
            this.plasma_iv.setImageResource(R.drawable.anion_select);
            return;
        }
        this.palasmaO_iv.setImageResource(R.drawable.icon_3);
        this.palasmaO_ll.setTag("1");
        this.plasma_iv.setImageResource(R.drawable.anion_not_select);
    }

    public void setSilentAnimation() {
        this.silent_iv.setBackgroundResource(R.drawable.icon_sleep_select);
    }

    public void setStandarAnimation() {
        this.standar_iv.setBackgroundResource(R.drawable.icon_standard_select);
    }

    public void setStrongAnimation() {
        this.strong_iv.setBackgroundResource(R.drawable.icon_strong_select);
    }

    public void setSwitch(boolean z) {
        if (z) {
            this.turnOff_layout.setVisibility(8);
            return;
        }
        this.turnOff_layout.setVisibility(0);
        reAll();
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

    public void setTimingOn(int i) {
        this.timingOn = i;
        if (i != 0) {
            this.timingOn_tv.setText(new StringBuilder(String.valueOf(i)).append("小时").toString());
        } else {
            this.timingOn_tv.setText("定时开机");
        }
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
