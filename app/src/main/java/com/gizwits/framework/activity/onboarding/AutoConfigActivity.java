package com.gizwits.framework.activity.onboarding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.gizwits.framework.activity.BaseActivity;
import com.uh.all.airpurifier.R;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

public class AutoConfigActivity extends BaseActivity implements OnClickListener {
    private Button btnNext;
    private EditText etInputPsw;
    Handler handler = new Handler() {
        private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AutoConfigActivity$handler_key;

        static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AutoConfigActivity$handler_key() {
            int[] iArr = $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AutoConfigActivity$handler_key;
            if (iArr == null) {
                iArr = new int[handler_key.values().length];
                try {
                    iArr[handler_key.CHANGE_WIFI.ordinal()] = 1;
                } catch (NoSuchFieldError e) {
                }
                $SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AutoConfigActivity$handler_key = iArr;
            }
            return iArr;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (AnonymousClass1.$SWITCH_TABLE$com$gizwits$framework$activity$onboarding$AutoConfigActivity$handler_key()[handler_key.values()[message.what].ordinal()]) {
                case 1:
                    AutoConfigActivity.this.strSsid = NetworkUtils.getCurentWifiSSID(AutoConfigActivity.this);
                    AutoConfigActivity.this.tvSsid.setText(new StringBuilder(String.valueOf(AutoConfigActivity.this.getString(R.string.wifi_name))).append(AutoConfigActivity.this.strSsid).toString());
                    return;
                default:
                    return;
            }
        }
    };
    private ImageView ivBack;
    ConnecteChangeBroadcast mChangeBroadcast = new ConnecteChangeBroadcast();
    private String strPsw;
    private String strSsid;
    private ToggleButton tbPswFlag;
    private TextView tvSsid;

    public class ConnecteChangeBroadcast extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            boolean isWifiConnected = NetworkUtils.isWifiConnected(context);
            Log.i("networkchange", "change" + isWifiConnected);
            if (isWifiConnected) {
                AutoConfigActivity.this.handler.sendEmptyMessage(handler_key.CHANGE_WIFI.ordinal());
            }
        }
    }

    private enum handler_key {
        CHANGE_WIFI
    }

    private void initEvents() {
        this.btnNext.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);
        this.tbPswFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    AutoConfigActivity.this.etInputPsw.setInputType(145);
                } else {
                    AutoConfigActivity.this.etInputPsw.setInputType(129);
                }
            }
        });
    }

    private void initViews() {
        this.tvSsid = (TextView) findViewById(R.id.tvSsid);
        this.etInputPsw = (EditText) findViewById(R.id.etInputPsw);
        this.tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
        this.btnNext = (Button) findViewById(R.id.btnNext);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
    }

    public void onBackPressed() {
        startActivity(new Intent(this, SearchDeviceActivity.class));
        finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack /*2131165197*/:
                onBackPressed();
                return;
            case R.id.btnNext /*2131165268*/:
                if (!NetworkUtils.isWifiConnected(this)) {
                    ToastUtils.showShort((Context) this, getString(R.string.wifi_first));
                    return;
                } else if (StringUtils.isEmpty(this.strSsid)) {
                    ToastUtils.showShort((Context) this, getString(R.string.wifi_first));
                    return;
                } else {
                    Intent intent = new Intent(this, AirlinkActivity.class);
                    intent.putExtra("ssid", this.strSsid);
                    this.strPsw = this.etInputPsw.getText().toString().trim();
                    if (StringUtils.isEmpty(this.strPsw)) {
                        intent.putExtra("psw", "");
                    } else {
                        intent.putExtra("psw", this.strPsw);
                    }
                    startActivity(intent);
                    finish();
                    return;
                }
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_autoconfig);
        initViews();
        initEvents();
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
        if (NetworkUtils.isWifiConnected(this)) {
            this.handler.sendEmptyMessage(handler_key.CHANGE_WIFI.ordinal());
        }
    }
}
