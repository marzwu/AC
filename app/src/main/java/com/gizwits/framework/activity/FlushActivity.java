package com.gizwits.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cgt.control.ActivityManager;
import com.gizwits.framework.activity.account.LoginActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.framework.utils.StringUtils;
import com.uh.all.airpurifier.R;
import com.xpg.common.system.IntentUtils;

public class FlushActivity extends BaseActivity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_flush);
        new ActivityManager().applyKitKatTranslucency(this, -16737980);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (StringUtils.isEmpty(FlushActivity.this.setmanager.getToken())) {
                    IntentUtils.getInstance().startActivity(FlushActivity.this, LoginActivity.class);
                } else {
                    Intent intent = new Intent(FlushActivity.this, DeviceListActivity.class);
                    intent.putExtra("autoLogin", true);
                    FlushActivity.this.startActivity(intent);
                }
                FlushActivity.this.finish();
            }
        }, 1000);
    }
}
