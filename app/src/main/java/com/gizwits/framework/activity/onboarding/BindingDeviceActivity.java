package com.gizwits.framework.activity.onboarding;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.marz.ac.v1.R;
import com.xpg.common.system.IntentUtils;

public class BindingDeviceActivity extends BaseActivity implements OnClickListener {
    private Button btnRetry;
    private String did = "";
    Handler handler = new Handler() {

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1:
                    IntentUtils.getInstance().startActivity(BindingDeviceActivity.this, DeviceListActivity.class);
                    BindingDeviceActivity.this.finish();
                    return;
                case 2:
                    BindingDeviceActivity.this.llStartConfig.setVisibility(8);
                    BindingDeviceActivity.this.llConfigFailed.setVisibility(0);
                    return;
                default:
                    return;
            }
        }
    };
    private LinearLayout llConfigFailed;
    private LinearLayout llStartConfig;
    private TextView tvPress;

    private enum handler_key {
        BIND_SUCCESS,
        BIND_FAILED
    }

    private void bindDevice() {
        this.mCenter.cBindDevice(this.setmanager.getUid(), this.setmanager.getToken(), this.did, null, "");
    }

    private void goBack() {
        IntentUtils.getInstance().startActivity(this, SearchDeviceActivity.class);
        finish();
    }

    private void initDatas() {
        if (getIntent() != null) {
            this.did = getIntent().getStringExtra("did");
        }
    }

    private void initEvents() {
        this.tvPress.setOnClickListener(this);
        this.btnRetry.setOnClickListener(this);
    }

    private void initViews() {
        this.llConfigFailed = (LinearLayout) findViewById(R.id.llConfigFailed);
        this.llStartConfig = (LinearLayout) findViewById(R.id.llStartConfig);
        this.tvPress = (TextView) findViewById(R.id.tvPress);
        this.btnRetry = (Button) findViewById(R.id.btnRetry);
        this.llStartConfig.setVisibility(0);
        this.llConfigFailed.setVisibility(8);
        this.tvPress.getPaint().setFlags(8);
    }

    protected void didBindDevice(int i, String str, String str2) {
        if (i == 0) {
            this.handler.sendEmptyMessage(handler_key.BIND_SUCCESS.ordinal());
        } else {
            this.handler.sendEmptyMessage(handler_key.BIND_FAILED.ordinal());
        }
    }

    public void onBackPressed() {
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRetry /*2131165208*/:
                goBack();
                return;
            case R.id.tvPress /*2131165269*/:
                this.handler.sendEmptyMessage(handler_key.BIND_FAILED.ordinal());
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_binding);
        initViews();
        initEvents();
        initDatas();
        bindDevice();
    }
}
