package com.gizwits.framework.activity.help;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.gizwits.framework.activity.BaseActivity;
import com.uh.all.airpurifier.R;

public class HelpActivity extends BaseActivity implements OnClickListener {
    private Button AppHelp;
    private Button DeviceHelp;
    private ImageView ivBack;

    private void initEvents() {
        this.ivBack.setOnClickListener(this);
        this.AppHelp.setOnClickListener(this);
        this.DeviceHelp.setOnClickListener(this);
    }

    private void initViews() {
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.AppHelp = (Button) findViewById(R.id.AppHelp);
        this.DeviceHelp = (Button) findViewById(R.id.DeviceHelp);
    }

    public void onBackPressed() {
        finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack /*2131165197*/:
                onBackPressed();
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_help);
        initViews();
        initEvents();
    }
}
