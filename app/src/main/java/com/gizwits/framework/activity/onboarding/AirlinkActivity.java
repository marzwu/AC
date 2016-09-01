package com.gizwits.framework.activity.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.uh.all.airpurifier.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.StringUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import java.util.Timer;
import java.util.TimerTask;

public class AirlinkActivity extends BaseActivity implements OnClickListener {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AirlinkActivity$UI_STATE;
    private UI_STATE UiStateNow;
    private Button btnConfig;
    private Button btnRetry;
    private Button btnSoftap;
    Handler handler = new Handler() {
        private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AirlinkActivity$handler_key;

        static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AirlinkActivity$handler_key() {
            int[] iArr = $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AirlinkActivity$handler_key;
            if (iArr == null) {
                iArr = new int[handler_key.values().length];
                try {
                    iArr[handler_key.CONFIG_FAILED.ordinal()] = 3;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[handler_key.CONFIG_SUCCESS.ordinal()] = 2;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[handler_key.TICK_TIME.ordinal()] = 1;
                } catch (NoSuchFieldError e3) {
                }
                $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AirlinkActivity$handler_key = iArr;
            }
            return iArr;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (AnonymousClass1.$SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AirlinkActivity$handler_key()[handler_key.values()[message.what].ordinal()]) {
                case 1:
                    AirlinkActivity airlinkActivity = AirlinkActivity.this;
                    airlinkActivity.secondleft--;
                    if (AirlinkActivity.this.secondleft <= 0) {
                        AirlinkActivity.this.timer.cancel();
                        sendEmptyMessage(handler_key.CONFIG_FAILED.ordinal());
                        return;
                    }
                    AirlinkActivity.this.tvTick.setText(new StringBuilder(String.valueOf(AirlinkActivity.this.secondleft)).toString());
                    return;
                case 2:
                    IntentUtils.getInstance().startActivity(AirlinkActivity.this, DeviceListActivity.class);
                    AirlinkActivity.this.finish();
                    return;
                case 3:
                    AirlinkActivity.this.showLayout(UI_STATE.Result);
                    return;
                default:
                    return;
            }
        }
    };
    private ImageView ivBack;
    private LinearLayout llConfigFailed;
    private LinearLayout llConfiging;
    private LinearLayout llStartConfig;
    int secondleft = 60;
    private String strPsw;
    private String strSSid;
    private Timer timer;
    private TextView tvTick;

    private enum UI_STATE {
        Ready,
        Setting,
        Result
    }

    private enum handler_key {
        TICK_TIME,
        CONFIG_SUCCESS,
        CONFIG_FAILED
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AirlinkActivity$UI_STATE() {
        int[] iArr = $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AirlinkActivity$UI_STATE;
        if (iArr == null) {
            iArr = new int[UI_STATE.values().length];
            try {
                iArr[UI_STATE.Ready.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[UI_STATE.Result.ordinal()] = 3;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[UI_STATE.Setting.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AirlinkActivity$UI_STATE = iArr;
        }
        return iArr;
    }

    private void initData() {
        if (getIntent() != null) {
            if (!StringUtils.isEmpty(getIntent().getStringExtra("ssid"))) {
                this.strSSid = getIntent().getStringExtra("ssid");
            }
            if (StringUtils.isEmpty(getIntent().getStringExtra("psw"))) {
                this.strPsw = "";
            } else {
                this.strPsw = getIntent().getStringExtra("psw");
            }
        }
    }

    private void initEvents() {
        this.btnConfig.setOnClickListener(this);
        this.btnRetry.setOnClickListener(this);
        this.btnSoftap.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);
    }

    private void initViews() {
        this.btnConfig = (Button) findViewById(R.id.btnConfig);
        this.btnRetry = (Button) findViewById(R.id.btnRetry);
        this.btnSoftap = (Button) findViewById(R.id.btnSoftap);
        this.tvTick = (TextView) findViewById(R.id.tvTick);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.llStartConfig = (LinearLayout) findViewById(R.id.llStartConfig);
        this.llConfiging = (LinearLayout) findViewById(R.id.llConfiging);
        this.llConfigFailed = (LinearLayout) findViewById(R.id.llConfigFailed);
        showLayout(UI_STATE.Ready);
    }

    private void showLayout(UI_STATE ui_state) {
        this.UiStateNow = ui_state;
        switch ($SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AirlinkActivity$UI_STATE()[ui_state.ordinal()]) {
            case 1:
                this.llStartConfig.setVisibility(0);
                this.llConfiging.setVisibility(8);
                this.llConfigFailed.setVisibility(8);
                this.ivBack.setVisibility(0);
                return;
            case 2:
                this.llStartConfig.setVisibility(8);
                this.llConfiging.setVisibility(0);
                this.llConfigFailed.setVisibility(8);
                this.ivBack.setVisibility(8);
                return;
            case 3:
                this.llConfigFailed.setVisibility(0);
                this.llConfiging.setVisibility(8);
                this.llStartConfig.setVisibility(8);
                this.ivBack.setVisibility(0);
                return;
            default:
                return;
        }
    }

    private void startAirlink() {
        this.secondleft = 60;
        this.tvTick.setText(new StringBuilder(String.valueOf(this.secondleft)).toString());
        showLayout(UI_STATE.Setting);
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            public void run() {
                AirlinkActivity.this.handler.sendEmptyMessage(handler_key.TICK_TIME.ordinal());
            }
        }, 1000, 1000);
        this.mCenter.cSetAirLink(this.strSSid, this.strPsw);
    }

    protected void didSetDeviceWifi(int i, XPGWifiDevice xPGWifiDevice) {
        if (i == 0) {
            this.handler.sendEmptyMessage(handler_key.CONFIG_SUCCESS.ordinal());
        } else {
            this.handler.sendEmptyMessage(handler_key.CONFIG_FAILED.ordinal());
        }
    }

    public void onBackPressed() {
        switch ($SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AirlinkActivity$UI_STATE()[this.UiStateNow.ordinal()]) {
            case 1:
                startActivity(new Intent(this, AutoConfigActivity.class));
                finish();
                return;
            case 3:
                startActivity(new Intent(this, SearchDeviceActivity.class));
                finish();
                return;
            default:
                return;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack /*2131165197*/:
                onBackPressed();
                return;
            case R.id.btnConfig /*2131165203*/:
                startAirlink();
                return;
            case R.id.btnRetry /*2131165208*/:
                onBackPressed();
                return;
            case R.id.btnSoftap /*2131165209*/:
                Intent intent = new Intent(this, SoftApConfigActivity.class);
                intent.putExtra("ssid", this.strSSid);
                intent.putExtra("psw", this.strPsw);
                startActivity(intent);
                finish();
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_airlink);
        initViews();
        initEvents();
        initData();
    }
}
