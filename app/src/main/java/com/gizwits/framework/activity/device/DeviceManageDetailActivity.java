package com.gizwits.framework.activity.device;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.utils.StringUtils;
import com.uh.all.airpurifier.R;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.ui.utils.ToastUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import java.util.List;

public class DeviceManageDetailActivity extends BaseActivity implements OnClickListener {
    private Button btnDelDevice;
    private EditText etName;
    Handler handler = new Handler() {
        private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$device$DeviceManageDetailActivity$handler_key;

        static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$device$DeviceManageDetailActivity$handler_key() {
            int[] iArr = $SWITCH_TABLE$com$gizwits$framework$activity$device$DeviceManageDetailActivity$handler_key;
            if (iArr == null) {
                iArr = new int[handler_key.values().length];
                try {
                    iArr[handler_key.CHANGE_FAIL.ordinal()] = 2;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[handler_key.CHANGE_SUCCESS.ordinal()] = 1;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[handler_key.DELETE_FAIL.ordinal()] = 4;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[handler_key.DELETE_SUCCESS.ordinal()] = 3;
                } catch (NoSuchFieldError e4) {
                }
                try {
                    iArr[handler_key.GET_BOUND.ordinal()] = 5;
                } catch (NoSuchFieldError e5) {
                }
                $SWITCH_TABLE$com$gizwits$framework$activity$device$DeviceManageDetailActivity$handler_key = iArr;
            }
            return iArr;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (AnonymousClass1.$SWITCH_TABLE$com$gizwits$framework$activity$device$DeviceManageDetailActivity$handler_key()[handler_key.values()[message.what].ordinal()]) {
                case 1:
                    DialogManager.dismissDialog(DeviceManageDetailActivity.this, DeviceManageDetailActivity.this.progressDialog);
                    ToastUtils.showShort(DeviceManageDetailActivity.this, "修改成功！");
                    DeviceManageDetailActivity.this.finish();
                    return;
                case 2:
                    DialogManager.dismissDialog(DeviceManageDetailActivity.this, DeviceManageDetailActivity.this.progressDialog);
                    ToastUtils.showShort(DeviceManageDetailActivity.this, "修改失败:" + message.obj.toString());
                    return;
                case 3:
                    DialogManager.dismissDialog(DeviceManageDetailActivity.this, DeviceManageDetailActivity.this.progressDialog);
                    ToastUtils.showShort(DeviceManageDetailActivity.this, "删除成功！");
                    DeviceManageDetailActivity.this.finish();
                    return;
                case 4:
                    DialogManager.dismissDialog(DeviceManageDetailActivity.this, DeviceManageDetailActivity.this.progressDialog);
                    ToastUtils.showShort(DeviceManageDetailActivity.this, "删除失败:" + message.obj.toString());
                    return;
                case 5:
                    DeviceManageDetailActivity.this.mCenter.cGetBoundDevices(DeviceManageDetailActivity.this.setmanager.getUid(), DeviceManageDetailActivity.this.setmanager.getToken());
                    return;
                default:
                    return;
            }
        }
    };
    private boolean isChange = true;
    private ImageView ivBack;
    private ImageView ivTick;
    private ProgressDialog progressDialog;
    private TextView tvDate;
    private TextView tvDeviceCode;
    private TextView tvDeviceType;
    private TextView tvPlace;
    private Dialog unbindDialog;
    private XPGWifiDevice xpgWifiDevice;

    private enum handler_key {
        CHANGE_SUCCESS,
        CHANGE_FAIL,
        DELETE_SUCCESS,
        DELETE_FAIL,
        GET_BOUND
    }

    private void initEvents() {
        this.btnDelDevice.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);
        this.ivTick.setOnClickListener(this);
    }

    private void initParams() {
        if (getIntent() != null) {
            this.xpgWifiDevice = BaseActivity.findDeviceByMac(getIntent().getStringExtra("mac"), getIntent().getStringExtra("did"));
            this.xpgWifiDevice.setListener(this.deviceListener);
        }
    }

    private void initViews() {
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.ivTick = (ImageView) findViewById(R.id.ivTick);
        this.tvDate = (TextView) findViewById(R.id.tvDate);
        this.tvPlace = (TextView) findViewById(R.id.tvPlace);
        this.tvDeviceType = (TextView) findViewById(R.id.tvDeviceType);
        this.tvDeviceCode = (TextView) findViewById(R.id.tvDeviceCode);
        this.etName = (EditText) findViewById(R.id.etName);
        this.btnDelDevice = (Button) findViewById(R.id.btnDelDevice);
        this.unbindDialog = DialogManager.getUnbindDialog(this, this);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setCancelable(false);
        if (this.xpgWifiDevice == null) {
            return;
        }
        if (StringUtils.isEmpty(this.xpgWifiDevice.getRemark())) {
            String macAddress = this.xpgWifiDevice.getMacAddress();
            int length = macAddress.length();
            this.etName.setText(new StringBuilder(String.valueOf(this.xpgWifiDevice.getProductName())).append(macAddress.substring(length - 4, length)).toString());
            return;
        }
        this.etName.setText(this.xpgWifiDevice.getRemark());
    }

    protected void didBindDevice(int i, String str, String str2) {
        Log.d("Device扫描结果", "error=" + i + ";errorMessage=" + str + ";did=" + str2);
        Message message = new Message();
        if (i == 0) {
            message.what = handler_key.GET_BOUND.ordinal();
            this.handler.sendMessage(message);
            return;
        }
        message.what = handler_key.CHANGE_FAIL.ordinal();
        message.obj = str;
        this.handler.sendMessage(message);
    }

    protected void didDiscovered(int i, List<XPGWifiDevice> list) {
        Log.d("onDiscovered", "Device count:" + list.size());
        deviceslist = list;
        Message message = new Message();
        if (message != null) {
            message.what = this.isChange ? handler_key.CHANGE_SUCCESS.ordinal() : handler_key.DELETE_SUCCESS.ordinal();
            this.handler.sendMessageDelayed(message, 1500);
        }
    }

    protected void didUnbindDevice(int i, String str, String str2) {
        Message message = new Message();
        if (i == 0) {
            this.isChange = true;
            message.what = handler_key.GET_BOUND.ordinal();
            this.handler.sendMessage(message);
            return;
        }
        message.what = handler_key.DELETE_FAIL.ordinal();
        message.obj = str;
        this.handler.sendMessage(message);
    }

    public void onBackPressed() {
        finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack /*2131165197*/:
                onBackPressed();
                return;
            case R.id.ivTick /*2131165309*/:
                if (!NetworkUtils.isNetworkConnected(this)) {
                    ToastUtils.showShort((Context) this, "网络未连接");
                    return;
                } else if (StringUtils.isEmpty(this.etName.getText().toString())) {
                    ToastUtils.showShort((Context) this, "请输入一个设备名称");
                    return;
                } else {
                    this.isChange = true;
                    this.progressDialog.setMessage("修改中，请稍候...");
                    DialogManager.showDialog(this, this.progressDialog);
                    this.mCenter.cUpdateRemark(this.setmanager.getUid(), this.setmanager.getToken(), this.xpgWifiDevice.getDid(), this.xpgWifiDevice.getPasscode(), this.etName.getText().toString());
                    return;
                }
            case R.id.btnDelDevice /*2131165315*/:
                DialogManager.showDialog(this, this.unbindDialog);
                return;
            case R.id.right_btn /*2131165393*/:
                if (NetworkUtils.isNetworkConnected(this)) {
                    this.isChange = false;
                    DialogManager.dismissDialog(this, this.unbindDialog);
                    this.progressDialog.setMessage("删除中，请稍候...");
                    DialogManager.showDialog(this, this.progressDialog);
                    this.mCenter.cUnbindDevice(this.setmanager.getUid(), this.setmanager.getToken(), this.xpgWifiDevice.getDid(), this.xpgWifiDevice.getPasscode());
                    return;
                }
                ToastUtils.showShort((Context) this, "网络未连接");
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_devices_info);
        initParams();
        initViews();
        initEvents();
    }
}
