package com.gizwits.framework.activity.device;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.onboarding.SearchDeviceActivity;
import com.gizwits.framework.adapter.ManageListAdapter;
import com.marz.ac.v1.R;
import com.xpg.common.system.IntentUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import java.util.List;

public class DeviceManageListActivity extends BaseActivity implements OnClickListener {
    private List<XPGWifiDevice> devices;
    private ImageView ivAdd;
    private ImageView ivBack;
    private ListView lvDevices;
    ManageListAdapter mAdapter;

    private void initEvents() {
        this.lvDevices.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                XPGWifiDevice xPGWifiDevice = (XPGWifiDevice) DeviceManageListActivity.bindlist.get(i);
                Intent intent = new Intent(DeviceManageListActivity.this, DeviceManageDetailActivity.class);
                intent.putExtra("mac", xPGWifiDevice.getMacAddress());
                intent.putExtra("did", xPGWifiDevice.getDid());
                DeviceManageListActivity.this.startActivity(intent);
            }
        });
        this.ivBack.setOnClickListener(this);
        this.ivAdd.setOnClickListener(this);
    }

    private void initViews() {
        this.ivAdd = (ImageView) findViewById(R.id.ivAdd);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.lvDevices = (ListView) findViewById(R.id.lvDevices);
        this.mAdapter = new ManageListAdapter(this, bindlist);
        this.lvDevices.setAdapter(this.mAdapter);
    }

    public void onBackPressed() {
        Object obj = 1;
        for (XPGWifiDevice isOnline : bindlist) {
            if (isOnline.isOnline()) {
                obj = null;
            }
        }
        if (obj != null) {
            IntentUtils.getInstance().startActivity(this, DeviceListActivity.class);
        } else {
            finish();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack /*2131165197*/:
                onBackPressed();
                return;
            case R.id.ivAdd /*2131165307*/:
                IntentUtils.getInstance().startActivity(this, SearchDeviceActivity.class);
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_manage_device_list);
        initViews();
        initEvents();
    }

    public void onResume() {
        super.onResume();
        initBindList();
        this.mAdapter.notifyDataSetChanged();
    }
}
