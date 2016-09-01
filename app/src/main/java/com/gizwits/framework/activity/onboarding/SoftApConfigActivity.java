package com.gizwits.framework.activity.onboarding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.uh.all.airpurifier.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.NetworkUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import java.util.Timer;
import java.util.TimerTask;

public class SoftApConfigActivity extends BaseActivity implements OnClickListener {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$SoftApConfigActivity$UI_STATE;
    private UI_STATE UiStateNow;
    private Button btnNext;
    private Button btnOK;
    private Button btnRetry;
    private EditText etInputPsw;
    Handler handler = new Handler() {
        private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$SoftApConfigActivity$handler_key;

        static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$SoftApConfigActivity$handler_key() {
            int[] iArr = $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$SoftApConfigActivity$handler_key;
            if (iArr == null) {
                iArr = new int[handler_key.values().length];
                try {
                    iArr[handler_key.CHANGE_WIFI.ordinal()] = 2;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[handler_key.CONFIG_FAILED.ordinal()] = 4;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[handler_key.CONFIG_SUCCESS.ordinal()] = 3;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[handler_key.TICK_TIME.ordinal()] = 1;
                } catch (NoSuchFieldError e4) {
                }
                $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$SoftApConfigActivity$handler_key = iArr;
            }
            return iArr;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (AnonymousClass1.$SWITCH_TABLE$com$gizwits$framework$activity$onboarding$SoftApConfigActivity$handler_key()[handler_key.values()[message.what].ordinal()]) {
                case 1:
                    SoftApConfigActivity softApConfigActivity = SoftApConfigActivity.this;
                    softApConfigActivity.secondleft--;
                    if (SoftApConfigActivity.this.secondleft <= 0) {
                        SoftApConfigActivity.this.timer.cancel();
                        sendEmptyMessage(handler_key.CONFIG_FAILED.ordinal());
                        return;
                    }
                    SoftApConfigActivity.this.tvTick.setText(new StringBuilder(String.valueOf(SoftApConfigActivity.this.secondleft)).toString());
                    return;
                case 2:
                    SoftApConfigActivity.this.showLayout(UI_STATE.PswInput);
                    return;
                case 3:
                    SoftApConfigActivity.this.showLayout(UI_STATE.ResultSuccess);
                    return;
                case 4:
                    SoftApConfigActivity.this.showLayout(UI_STATE.ResultFailed);
                    return;
                default:
                    return;
            }
        }
    };
    private ImageView ivBack;
    private ImageView ivStep;
    private LinearLayout llConfig;
    private LinearLayout llConfigFailed;
    private LinearLayout llConfigSuccess;
    private LinearLayout llConnectAp;
    private LinearLayout llInsertPsw;
    ConnectChangeBroadcast mChangeBroadcast = new ConnectChangeBroadcast();
    int secondleft = 30;
    private String strPsw;
    private String strSsid;
    private ToggleButton tbPswFlag;
    private Timer timer;
    private TextView tvSsid;
    private TextView tvTick;
    private TextView tvpsw;

    public class ConnectChangeBroadcast extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (NetworkUtils.isWifiConnected(context) && NetworkUtils.getCurentWifiSSID(SoftApConfigActivity.this).contains("XPG-GAgent")) {
                SoftApConfigActivity.this.handler.sendEmptyMessage(handler_key.CHANGE_WIFI.ordinal());
            }
        }
    }

    enum UI_STATE {
        SoftApReady,
        PswInput,
        Setting,
        ResultFailed,
        ResultSuccess
    }

    private enum handler_key {
        TICK_TIME,
        CHANGE_WIFI,
        CONFIG_SUCCESS,
        CONFIG_FAILED
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$SoftApConfigActivity$UI_STATE() {
        int[] iArr = $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$SoftApConfigActivity$UI_STATE;
        if (iArr == null) {
            iArr = new int[UI_STATE.values().length];
            try {
                iArr[UI_STATE.PswInput.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[UI_STATE.ResultFailed.ordinal()] = 4;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[UI_STATE.ResultSuccess.ordinal()] = 5;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[UI_STATE.Setting.ordinal()] = 3;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[UI_STATE.SoftApReady.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$SoftApConfigActivity$UI_STATE = iArr;
        }
        return iArr;
    }

    private void initDatas() {
        if (getIntent() != null) {
            this.strSsid = getIntent().getStringExtra("ssid");
            this.tvSsid.setText("Wi-Fi名称:" + this.strSsid);
        }
    }

    private void initEvents() {
        this.btnRetry.setOnClickListener(this);
        this.btnOK.setOnClickListener(this);
        this.btnNext.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);
        this.tbPswFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    SoftApConfigActivity.this.etInputPsw.setInputType(145);
                } else {
                    SoftApConfigActivity.this.etInputPsw.setInputType(129);
                }
            }
        });
    }

    private void initViews() {
        this.llConnectAp = (LinearLayout) findViewById(R.id.llConnectAp);
        this.llInsertPsw = (LinearLayout) findViewById(R.id.llInsertPsw);
        this.llConfig = (LinearLayout) findViewById(R.id.llConfiging);
        this.llConfigSuccess = (LinearLayout) findViewById(R.id.llConfigSuccess);
        this.llConfigFailed = (LinearLayout) findViewById(R.id.llConfigFailed);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.etInputPsw = (EditText) findViewById(R.id.etInputPsw);
        this.btnNext = (Button) findViewById(R.id.btnNext);
        this.btnOK = (Button) findViewById(R.id.btnOK);
        this.btnRetry = (Button) findViewById(R.id.btnRetry);
        this.tvpsw = (TextView) findViewById(R.id.tvpsw);
        this.ivStep = (ImageView) findViewById(R.id.ivStep);
        this.tvSsid = (TextView) findViewById(R.id.tvSsid);
        this.tvTick = (TextView) findViewById(R.id.tvTick);
        this.tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
        showLayout(UI_STATE.SoftApReady);
    }

    private void showLayout(UI_STATE ui_state) {
        this.UiStateNow = ui_state;
        switch ($SWITCH_TABLE$com$gizwits$framework$activity$onboarding$SoftApConfigActivity$UI_STATE()[ui_state.ordinal()]) {
            case 1:
                this.llConnectAp.setVisibility(0);
                this.llInsertPsw.setVisibility(8);
                this.llConfig.setVisibility(8);
                this.llConfigSuccess.setVisibility(8);
                this.llConfigFailed.setVisibility(8);
                this.ivBack.setVisibility(0);
                this.ivStep.setImageResource(R.drawable.step_devicelist);
                return;
            case 2:
                this.llConnectAp.setVisibility(8);
                this.llInsertPsw.setVisibility(0);
                this.llConfig.setVisibility(8);
                this.llConfigSuccess.setVisibility(8);
                this.llConfigFailed.setVisibility(8);
                this.ivBack.setVisibility(0);
                this.ivStep.setImageResource(R.drawable.step_inputpsw_2);
                return;
            case 3:
                this.llConnectAp.setVisibility(8);
                this.llInsertPsw.setVisibility(8);
                this.llConfig.setVisibility(0);
                this.llConfigSuccess.setVisibility(8);
                this.llConfigFailed.setVisibility(8);
                this.ivBack.setVisibility(8);
                this.ivStep.setImageResource(R.drawable.step_inputpsw_2);
                return;
            case 4:
                this.llConnectAp.setVisibility(8);
                this.llInsertPsw.setVisibility(8);
                this.llConfig.setVisibility(8);
                this.llConfigSuccess.setVisibility(8);
                this.llConfigFailed.setVisibility(0);
                this.ivBack.setVisibility(0);
                this.ivStep.setImageResource(R.drawable.step_inputpsw_2);
                return;
            case 5:
                this.llConnectAp.setVisibility(8);
                this.llInsertPsw.setVisibility(8);
                this.llConfig.setVisibility(8);
                this.llConfigSuccess.setVisibility(0);
                this.llConfigFailed.setVisibility(8);
                this.ivBack.setVisibility(0);
                this.ivStep.setImageResource(R.drawable.step_inputpsw_2);
                return;
            default:
                return;
        }
    }

    private void startConfig() {
        this.secondleft = 60;
        this.tvTick.setText(new StringBuilder(String.valueOf(this.secondleft)).toString());
        showLayout(UI_STATE.Setting);
        this.strPsw = this.etInputPsw.getText().toString();
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            public void run() {
                SoftApConfigActivity.this.handler.sendEmptyMessage(handler_key.TICK_TIME.ordinal());
            }
        }, 1000, 1000);
        this.mCenter.cSetSoftAp(this.strSsid, this.strPsw);
    }

    protected void didSetDeviceWifi(int i, XPGWifiDevice xPGWifiDevice) {
        if (i == 0) {
            this.handler.sendEmptyMessage(handler_key.CONFIG_SUCCESS.ordinal());
        } else {
            this.handler.sendEmptyMessage(handler_key.CONFIG_FAILED.ordinal());
        }
    }

    public void onBackPressed() {
        switch ($SWITCH_TABLE$com$gizwits$framework$activity$onboarding$SoftApConfigActivity$UI_STATE()[this.UiStateNow.ordinal()]) {
            case 1:
                startActivity(new Intent(this, SearchDeviceActivity.class));
                finish();
                return;
            case 2:
                showLayout(UI_STATE.SoftApReady);
                return;
            case 4:
                startActivity(new Intent(this, SearchDeviceActivity.class));
                finish();
                break;
            case 5:
                break;
            default:
                return;
        }
        finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack /*2131165197*/:
            case R.id.btnRetry /*2131165208*/:
                onBackPressed();
                return;
            case R.id.btnNext /*2131165268*/:
                startConfig();
                return;
            case R.id.btnOK /*2131165362*/:
                IntentUtils.getInstance().startActivity(this, DeviceListActivity.class);
                finish();
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_softap);
        initViews();
        initEvents();
        initDatas();
    }

    public void onPause() {
        super.onPause();
        unregisterReceiver(this.mChangeBroadcast);
    }

    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(this.mChangeBroadcast, intentFilter);
    }
}
