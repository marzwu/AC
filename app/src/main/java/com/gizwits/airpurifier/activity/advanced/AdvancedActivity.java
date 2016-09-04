package com.gizwits.airpurifier.activity.advanced;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.entity.AdvanceType;
import com.gizwits.framework.entity.DeviceAlarm;
import com.marz.ac.v1.R;
import com.xpg.common.useful.DateUtil;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class AdvancedActivity extends BaseActivity implements OnClickListener {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$airpurifier$activity$advanced$AdvancedActivity$CurrentView;
    private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$entity$AdvanceType;
    private String TAG = "AdvancedActivity";
    private AlarmFragment alarmFragment;
    private ArrayList<DeviceAlarm> alarmList;
    private Button alarm_btn;
    private CurrentView currentFragment;
    private ConcurrentHashMap<String, Object> deviceDataMap;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    Handler handler = new Handler() {

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1:
                    break;
                case 2:
                    AdvancedActivity.this.alarmFragment.addInfos(AdvancedActivity.this.alarmList);
                    return;
                case 3:
                    AdvancedActivity.this.mCenter.cDisconnect(AdvancedActivity.mXpgWifiDevice);
                    return;
                case 4:
                    try {
                        if (AdvancedActivity.this.deviceDataMap.get("data") != null) {
                            Log.i("info", (String) AdvancedActivity.this.deviceDataMap.get("data"));
                            AdvancedActivity.this.inputDataToMaps(AdvancedActivity.this.statuMap, (String) AdvancedActivity.this.deviceDataMap.get("data"));
                        }
                        AdvancedActivity.this.alarmList.clear();
                        if (AdvancedActivity.this.deviceDataMap.get("alters") != null) {
                            Log.i("info", (String) AdvancedActivity.this.deviceDataMap.get("alters"));
                            AdvancedActivity.this.inputAlarmToList((String) AdvancedActivity.this.deviceDataMap.get("alters"));
                        }
                        if (AdvancedActivity.this.deviceDataMap.get("faults") != null) {
                            Log.i("info", (String) AdvancedActivity.this.deviceDataMap.get("faults"));
                            AdvancedActivity.this.inputAlarmToList((String) AdvancedActivity.this.deviceDataMap.get("faults"));
                        }
                        AdvancedActivity.this.handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
                        AdvancedActivity.this.handler.sendEmptyMessage(handler_key.ALARM.ordinal());
                        break;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        break;
                    }
                case 5:
                    AdvancedActivity.this.mCenter.cGetStatus(AdvancedActivity.mXpgWifiDevice);
                    return;
                default:
                    return;
            }
            if (AdvancedActivity.this.statuMap != null && AdvancedActivity.this.statuMap.size() > 0) {
                AdvancedActivity.this.sensitivityFragment.changeSensi(Integer.parseInt(AdvancedActivity.this.statuMap.get(JsonKeys.Air_Sensitivity).toString()));
                if (AdvancedActivity.this.currentFragment == CurrentView.rosebox) {
                    AdvancedActivity.this.roseboxFragment.updateStatus(Integer.parseInt(AdvancedActivity.this.statuMap.get(JsonKeys.Filter_Life).toString()));
                }
                AdvancedActivity.this.roseboxFragment.setCurrent(Integer.parseInt(AdvancedActivity.this.statuMap.get(JsonKeys.Filter_Life).toString()));
            }
        }
    };
    private ImageView ivLeft;
    private RoseboxFragment roseboxFragment;
    private Button rosebox_btn;
    private SensitivityFragment sensitivityFragment;
    private Button sensitivity_btn;
    private ConcurrentHashMap<String, Object> statuMap;
    private TextView title_tv;

    private enum CurrentView {
        sensitivity,
        rosebox,
        alarm
    }

    private enum handler_key {
        UPDATE_UI,
        ALARM,
        DISCONNECTED,
        RECEIVED,
        GET_STATUE
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$airpurifier$activity$advanced$AdvancedActivity$CurrentView() {
        int[] iArr = $SWITCH_TABLE$com$gizwits$airpurifier$activity$advanced$AdvancedActivity$CurrentView;
        if (iArr == null) {
            iArr = new int[CurrentView.values().length];
            try {
                iArr[CurrentView.alarm.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[CurrentView.rosebox.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[CurrentView.sensitivity.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$com$gizwits$airpurifier$activity$advanced$AdvancedActivity$CurrentView = iArr;
        }
        return iArr;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$entity$AdvanceType() {
        int[] iArr = $SWITCH_TABLE$com$gizwits$framework$entity$AdvanceType;
        if (iArr == null) {
            iArr = new int[AdvanceType.values().length];
            try {
                iArr[AdvanceType.alarm.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[AdvanceType.rosebox.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[AdvanceType.sensivity.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$com$gizwits$framework$entity$AdvanceType = iArr;
        }
        return iArr;
    }

    private void changeView(CurrentView currentView) {
        this.currentFragment = currentView;
        switch ($SWITCH_TABLE$com$gizwits$airpurifier$activity$advanced$AdvancedActivity$CurrentView()[currentView.ordinal()]) {
            case 1:
                this.fragmentTransaction = this.fragmentManager.beginTransaction();
                this.fragmentTransaction.replace(R.id.content_layout, this.sensitivityFragment);
                this.fragmentTransaction.commit();
                break;
            case 2:
                this.fragmentTransaction = this.fragmentManager.beginTransaction();
                this.fragmentTransaction.replace(R.id.content_layout, this.roseboxFragment);
                this.fragmentTransaction.commit();
                break;
            case 3:
                this.fragmentTransaction = this.fragmentManager.beginTransaction();
                this.fragmentTransaction.replace(R.id.content_layout, this.alarmFragment);
                this.fragmentTransaction.commit();
                break;
        }
        if (currentView == CurrentView.alarm) {
            this.alarm_btn.setBackgroundResource(R.drawable.adv_bg1);
            this.alarm_btn.setTextColor(getResources().getColor(R.color.white));
        } else {
            this.alarm_btn.setBackgroundResource(R.drawable.adv_bg2);
            this.alarm_btn.setTextColor(getResources().getColor(R.color.gray));
        }
        if (currentView == CurrentView.rosebox) {
            this.rosebox_btn.setBackgroundResource(R.drawable.adv_bg1);
            this.rosebox_btn.setTextColor(getResources().getColor(R.color.white));
        } else {
            this.rosebox_btn.setBackgroundResource(R.drawable.adv_bg2);
            this.rosebox_btn.setTextColor(getResources().getColor(R.color.gray));
        }
        if (currentView == CurrentView.sensitivity) {
            this.sensitivity_btn.setBackgroundResource(R.drawable.adv_bg1);
            this.sensitivity_btn.setTextColor(getResources().getColor(R.color.white));
            return;
        }
        this.sensitivity_btn.setBackgroundResource(R.drawable.adv_bg2);
        this.sensitivity_btn.setTextColor(getResources().getColor(R.color.gray));
    }

    private void initFragment() {
        this.fragmentManager = getFragmentManager();
        this.sensitivityFragment = new SensitivityFragment(this);
        this.roseboxFragment = new RoseboxFragment(this);
        this.alarmFragment = new AlarmFragment(this);
    }

    private void initUI() {
        this.title_tv = (TextView) findViewById(R.id.ivTitle);
        this.title_tv.setText("高级功能");
        this.ivLeft = (ImageView) findViewById(R.id.ivBack);
        this.ivLeft.setOnClickListener(this);
        this.sensitivity_btn = (Button) findViewById(R.id.sensitivity_btn);
        this.sensitivity_btn.setOnClickListener(this);
        this.rosebox_btn = (Button) findViewById(R.id.rosebox_btn);
        this.rosebox_btn.setOnClickListener(this);
        this.alarm_btn = (Button) findViewById(R.id.alarm_btn);
        this.alarm_btn.setOnClickListener(this);
    }

    private void inputAlarmToList(String str) throws JSONException {
        Log.i("revjson", str);
        Iterator keys = new JSONObject(str).keys();
        while (keys.hasNext()) {
            String obj = keys.next().toString();
            Log.i("revjson", "action=" + obj);
            this.alarmList.add(new DeviceAlarm(DateUtil.getDateCN(new Date()), obj));
        }
        this.handler.sendEmptyMessage(handler_key.ALARM.ordinal());
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
                    Log.i(this.TAG, "Key:" + obj2 + ";value" + obj3);
                }
            }
        }
        this.handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
    }

    protected void didReceiveData(XPGWifiDevice xPGWifiDevice, ConcurrentHashMap<String, Object> concurrentHashMap, int i) {
        this.deviceDataMap = concurrentHashMap;
        this.handler.sendEmptyMessage(handler_key.RECEIVED.ordinal());
    }

    public void onBackPressed() {
        finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack /*2131165197*/:
                onBackPressed();
                return;
            case R.id.sensitivity_btn /*2131165367*/:
                changeView(CurrentView.sensitivity);
                return;
            case R.id.rosebox_btn /*2131165368*/:
                changeView(CurrentView.rosebox);
                return;
            case R.id.alarm_btn /*2131165369*/:
                changeView(CurrentView.alarm);
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.advanced_layout);
        mXpgWifiDevice.setListener(this.deviceListener);
        this.statuMap = new ConcurrentHashMap();
        this.alarmList = new ArrayList();
        initUI();
        initFragment();
    }

    public void onResume() {
        super.onResume();
        AdvanceType advanceType = (AdvanceType) getIntent().getSerializableExtra("advanced_set");
        this.mCenter.cGetStatus(mXpgWifiDevice);
        if (advanceType != null) {
            switch ($SWITCH_TABLE$com$gizwits$framework$entity$AdvanceType()[advanceType.ordinal()]) {
                case 3:
                    changeView(CurrentView.alarm);
                    return;
                default:
                    changeView(CurrentView.sensitivity);
                    return;
            }
        }
        changeView(CurrentView.sensitivity);
    }

    public void resetRosebox() {
        this.mCenter.cResetLife(mXpgWifiDevice);
    }

    public void sendSensitivityLv(int i) {
        this.mCenter.cAirSensitivity(mXpgWifiDevice, i);
    }
}
