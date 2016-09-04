package com.gizwits.framework.activity.onboarding;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cgt.control.ActivityManager;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.framework.adapter.SearchListAdapter;
import com.gizwits.framework.utils.DialogManager;
import com.google.zxing.client.android.CaptureActivity;
import com.marz.ac.v1.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.NetworkUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import java.util.ArrayList;
import java.util.List;


public class SearchDeviceActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
    private SearchListAdapter adapter;
    private List<XPGWifiDevice> allDeviceList;
    private Button btnAddGokit;
    private Button btnAddQR;
    private List<XPGWifiDevice> deviceList;
    Handler handler = new Handler() {

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1:
                    SearchDeviceActivity.this.adapter.notifyDataSetChanged();
                    return;
                case 2:
                    if (SearchDeviceActivity.this.deviceList.size() > 0) {
                        SearchDeviceActivity.this.deviceList.clear();
                    }
                    if (SearchDeviceActivity.this.allDeviceList.size() > 0) {
                        for (XPGWifiDevice xPGWifiDevice : SearchDeviceActivity.this.allDeviceList) {
                            if (xPGWifiDevice.isLAN() && !xPGWifiDevice.isBind(SearchDeviceActivity.this.setmanager.getUid())) {
                                SearchDeviceActivity.this.deviceList.add(xPGWifiDevice);
                            }
                        }
                        SearchDeviceActivity.this.adapter.notifyDataSetChanged();
                        SearchDeviceActivity.this.lvDevices.setVisibility(0);
                        SearchDeviceActivity.this.tvTips.setVisibility(8);
                    } else {
                        SearchDeviceActivity.this.lvDevices.setVisibility(8);
                        SearchDeviceActivity.this.tvTips.setVisibility(0);
                    }
                    SearchDeviceActivity.this.loadingDialog.cancel();
                    return;
                case 3:
                    IntentUtils.getInstance().startActivity(SearchDeviceActivity.this, AutoConfigActivity.class);
                    return;
                default:
                    return;
            }
        }
    };
    private boolean isRegister = false;
    private boolean isWaitingWifi = false;
    private ImageView ivBack;
    private ProgressDialog loadingDialog;
    private ListView lvDevices;
    ConnecteChangeBroadcast mChangeBroadcast = new ConnecteChangeBroadcast();
    private Dialog noNetworkDialog;
    private TextView tvTips;

    public class ConnecteChangeBroadcast extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            boolean isWifiConnected = NetworkUtils.isWifiConnected(context);
            Log.i("networkchange", "change" + isWifiConnected);
            if (isWifiConnected && SearchDeviceActivity.this.isWaitingWifi && SearchDeviceActivity.this.noNetworkDialog.isShowing()) {
                DialogManager.dismissDialog(SearchDeviceActivity.this, SearchDeviceActivity.this.noNetworkDialog);
                SearchDeviceActivity.this.handler.sendEmptyMessage(handler_key.CHANGE_SUCCESS.ordinal());
            }
        }
    }

    private enum handler_key {
        FOUND_SUCCESS,
        FOUND_FINISH,
        CHANGE_SUCCESS
    }

    private void initEvents() {
        this.btnAddQR.setOnClickListener(this);
        this.btnAddGokit.setOnClickListener(this);
        this.lvDevices.setOnItemClickListener(this);
        this.ivBack.setOnClickListener(this);
    }

    private void initViews() {
        this.btnAddQR = (Button) findViewById(R.id.btnAddQR);
        this.btnAddGokit = (Button) findViewById(R.id.btnAddGokit);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.lvDevices = (ListView) findViewById(R.id.lvDevices);
        this.tvTips = (TextView) findViewById(R.id.tvTips);
        this.loadingDialog = new ProgressDialog(this);
        this.loadingDialog.setMessage("加载中，请稍候.");
        this.loadingDialog.setCancelable(false);
        this.deviceList = new ArrayList();
        this.adapter = new SearchListAdapter(this, this.deviceList);
        this.lvDevices.setAdapter(this.adapter);
        this.noNetworkDialog = DialogManager.getNoNetworkDialog(this);
        this.noNetworkDialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                SearchDeviceActivity.this.isWaitingWifi = false;
            }
        });
        this.allDeviceList = new ArrayList();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.isRegister = extras.getBoolean("isRegister");
        }
    }

    protected void didDiscovered(int i, List<XPGWifiDevice> list) {
        if (list.size() > 0) {
            this.allDeviceList = list;
        }
    }

    public void onBackPressed() {
        if (this.isRegister) {
            IntentUtils.getInstance().startActivity(this, DeviceListActivity.class);
            finish();
            return;
        }
        finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack /*2131165197*/:
                onBackPressed();
                return;
            case R.id.btnAddQR /*2131165339*/:
                if (NetworkUtils.isNetworkConnected(this)) {
                    IntentUtils.getInstance().startActivity(this, CaptureActivity.class);
                    return;
                } else {
                    Toast.makeText(this, R.string.please_connect_network, 0).show();
                    return;
                }
            case R.id.btnAddGokit /*2131165340*/:
                if (NetworkUtils.isWifiConnected(this)) {
                    IntentUtils.getInstance().startActivity(this, AutoConfigActivity.class);
                    finish();
                    return;
                }
                this.isWaitingWifi = true;
                DialogManager.showDialog(this, this.noNetworkDialog);
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_searchdevice);
        new ActivityManager().applyKitKatTranslucency(this, -16725413);
        initViews();
        initEvents();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        XPGWifiDevice xPGWifiDevice = (XPGWifiDevice) this.adapter.getItem(i);
        Intent intent = new Intent(this, BindingDeviceActivity.class);
        intent.putExtra("mac", xPGWifiDevice.getMacAddress());
        intent.putExtra("did", xPGWifiDevice.getDid());
        startActivity(intent);
        finish();
    }

    public void onPause() {
        super.onPause();
        unregisterReceiver(this.mChangeBroadcast);
    }

    public void onResume() {
        super.onResume();
        this.loadingDialog.show();
        this.mCenter.cGetBoundDevices(this.setmanager.getUid(), this.setmanager.getToken());
        this.handler.sendEmptyMessageDelayed(handler_key.FOUND_FINISH.ordinal(), 5000);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(this.mChangeBroadcast, intentFilter);
    }
}
