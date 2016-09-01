package com.gizwits.framework.activity.device;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import com.cgt.control.ActivityManager;
import com.cgt.control.Constant;
import com.cgt.control.DownloadService;
import com.cgt.control.DownloadService.DownloadBinder;
import com.cgt.control.New_New_activity_control;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.account.LoginActivity;
import com.gizwits.framework.activity.onboarding.BindingDeviceActivity;
import com.gizwits.framework.activity.onboarding.SearchDeviceActivity;
import com.gizwits.framework.adapter.DeviceListAdapter;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.widget.RefreshableListView;
import com.gizwits.framework.widget.RefreshableListView.OnRefreshListener;
import com.uh.all.airpurifier.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.ui.utils.ToastUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class DeviceListActivity extends BaseActivity implements OnClickListener, OnItemClickListener, OnItemLongClickListener {
    private static final String TAG = "DeviceListActivity";
    private int LoginDeviceTimeOut = 2000;
    private DownloadBinder binder;
    private ICallbackResult callback = new ICallbackResult() {
        public void OnBackResult(Object obj) {
            if ("finish".equals(obj)) {
                DeviceListActivity.this.finish();
            }
        }
    };
    ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            DeviceListActivity.this.binder = (DownloadBinder) iBinder;
            System.out.println("服务启动!!!");
            DeviceListActivity.this.isBinded = true;
            DeviceListActivity.this.binder.addCallback(DeviceListActivity.this.callback);
            DeviceListActivity.this.binder.start();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            DeviceListActivity.this.isBinded = false;
        }
    };
    private DeviceListAdapter deviceListAdapter;
    private Dialog dialog;
    Handler handler = new Handler() {
        private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$device$DeviceListActivity$handler_key;

        static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$device$DeviceListActivity$handler_key() {
            int[] iArr = $SWITCH_TABLE$com$gizwits$framework$activity$device$DeviceListActivity$handler_key;
            if (iArr == null) {
                iArr = new int[handler_key.values().length];
                try {
                    iArr[handler_key.DELETE_FAIL.ordinal()] = 9;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[handler_key.DELETE_SUCCESS.ordinal()] = 8;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[handler_key.EXIT.ordinal()] = 6;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[handler_key.FOUND.ordinal()] = 5;
                } catch (NoSuchFieldError e4) {
                }
                try {
                    iArr[handler_key.LOGIN_FAIL.ordinal()] = 3;
                } catch (NoSuchFieldError e5) {
                }
                try {
                    iArr[handler_key.LOGIN_START.ordinal()] = 1;
                } catch (NoSuchFieldError e6) {
                }
                try {
                    iArr[handler_key.LOGIN_SUCCESS.ordinal()] = 2;
                } catch (NoSuchFieldError e7) {
                }
                try {
                    iArr[handler_key.LOGIN_TIMEOUT.ordinal()] = 4;
                } catch (NoSuchFieldError e8) {
                }
                try {
                    iArr[handler_key.UPDATA.ordinal()] = 7;
                } catch (NoSuchFieldError e9) {
                }
                $SWITCH_TABLE$com$gizwits$framework$activity$device$DeviceListActivity$handler_key = iArr;
            }
            return iArr;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (AnonymousClass1.$SWITCH_TABLE$com$gizwits$framework$activity$device$DeviceListActivity$handler_key()[handler_key.values()[message.what].ordinal()]) {
                case 2:
                    DialogManager.dismissDialog(DeviceListActivity.this, DeviceListActivity.this.progressDialog);
                    IntentUtils.getInstance().startActivity(DeviceListActivity.this, New_New_activity_control.class);
                    break;
                case 3:
                    DialogManager.dismissDialog(DeviceListActivity.this, DeviceListActivity.this.progressDialog);
                    ToastUtils.showShort(DeviceListActivity.this, "连接超时，请重试");
                    break;
                case 4:
                    DialogManager.dismissDialog(DeviceListActivity.this, DeviceListActivity.this.progressDialog);
                    ToastUtils.showShort(DeviceListActivity.this, "连接超时，请重试");
                    DeviceListActivity.this.getList();
                    break;
                case 5:
                    DeviceListActivity.this.lvDevices.completeRefreshing();
                    break;
                case 6:
                    DeviceListActivity.this.isExit = false;
                    break;
                case MotionEventCompat.ACTION_HOVER_MOVE /*7*/:
                    int intValue = ((Integer) message.obj).intValue();
                    try {
                        PackageInfo packageInfo = DeviceListActivity.this.getPackageManager().getPackageInfo(DeviceListActivity.this.getPackageName(), 0);
                        String str = packageInfo.versionName;
                        int i = packageInfo.versionCode;
                        System.out.println("currentVersionCode:" + i + " webVersion:" + intValue);
                        if (intValue > i) {
                            DeviceListActivity.this.showUpdateDialog();
                            break;
                        }
                    } catch (NameNotFoundException e) {
                        e.printStackTrace();
                        break;
                    }
                    break;
                case 8:
                    DialogManager.dismissDialog(DeviceListActivity.this, DeviceListActivity.this.progressDialog);
                    ToastUtils.showShort(DeviceListActivity.this, "删除成功！");
                    DeviceListActivity.this.finish();
                    break;
                case 9:
                    DialogManager.dismissDialog(DeviceListActivity.this, DeviceListActivity.this.progressDialog);
                    ToastUtils.showShort(DeviceListActivity.this, "删除失败:" + message.obj.toString());
                    break;
            }
            DeviceListActivity.this.deviceListAdapter.changeDatas(DeviceListActivity.deviceslist);
        }
    };
    private boolean isBinded;
    private boolean isExit = false;
    private ImageView ivAdd;
    private ImageView ivLogout;
    private RefreshableListView lvDevices;
    private ConnecteChangeBroadcast mChangeBroadcast = new ConnecteChangeBroadcast();
    private ProgressDialog progressDialog;
    private XPGWifiDevice tempDevice;
    private Dialog unbindDialog;

    public interface ICallbackResult {
        void OnBackResult(Object obj);
    }

    public class ConnecteChangeBroadcast extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                DeviceListActivity.this.getList();
            }
        }
    }

    private enum handler_key {
        LOGIN_START,
        LOGIN_SUCCESS,
        LOGIN_FAIL,
        LOGIN_TIMEOUT,
        FOUND,
        EXIT,
        UPDATA,
        DELETE_SUCCESS,
        DELETE_FAIL
    }

    private void getList() {
        this.mCenter.cGetBoundDevices(this.setmanager.getUid(), this.setmanager.getToken());
    }

    private void initEvents() {
        this.ivLogout.setOnClickListener(this);
        this.ivAdd.setOnClickListener(this);
        this.lvDevices.setOnItemClickListener(this);
    }

    private void initViews() {
        this.ivLogout = (ImageView) findViewById(R.id.ivLogout);
        this.ivAdd = (ImageView) findViewById(R.id.ivAdd);
        this.lvDevices = (RefreshableListView) findViewById(R.id.lvDevices);
        this.unbindDialog = DialogManager.getUnbindDialog(this, this);
        this.deviceListAdapter = new DeviceListAdapter(this, deviceslist);
        this.lvDevices.setAdapter(this.deviceListAdapter);
        this.lvDevices.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh(RefreshableListView refreshableListView) {
                DeviceListActivity.this.getList();
            }
        });
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("连接中，请稍候。");
        this.progressDialog.setCancelable(false);
    }

    private void loginDevice(XPGWifiDevice xPGWifiDevice) {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("连接中，请稍候。");
        this.progressDialog.setCancelable(false);
        DialogManager.showDialog(this, this.progressDialog);
        mXpgWifiDevice = xPGWifiDevice;
        mXpgWifiDevice.setListener(this.deviceListener);
        if (mXpgWifiDevice.isConnected()) {
            this.handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
            return;
        }
        this.handler.sendEmptyMessageDelayed(handler_key.LOGIN_TIMEOUT.ordinal(), (long) this.LoginDeviceTimeOut);
        mXpgWifiDevice.login(this.setmanager.getUid(), this.setmanager.getToken());
    }

    public void checkUpdate() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    HttpResponse execute = new DefaultHttpClient().execute(new HttpGet(Constant.UPDATE_URL));
                    System.out.println("httpResponse.getStatusLine().getStatusCode():" + execute.getStatusLine().getStatusCode());
                    if (execute.getStatusLine().getStatusCode() == 200) {
                        String entityUtils = EntityUtils.toString(execute.getEntity());
                        System.out.println("strResult=" + entityUtils);
                        int i = new JSONObject(entityUtils).getInt("version");
                        Message message = new Message();
                        message.obj = Integer.valueOf(i);
                        message.what = handler_key.UPDATA.ordinal();
                        DeviceListActivity.this.handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    protected void didDisconnected(XPGWifiDevice xPGWifiDevice) {
        if (mXpgWifiDevice.getDid().equals(xPGWifiDevice.getDid())) {
            this.handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
        }
    }

    protected void didDiscovered(int i, List<XPGWifiDevice> list) {
        deviceslist = list;
        this.handler.sendEmptyMessage(handler_key.FOUND.ordinal());
    }

    protected void didLogin(XPGWifiDevice xPGWifiDevice, int i) {
        this.handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
        if (i == 0) {
            mXpgWifiDevice = xPGWifiDevice;
            this.handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
            return;
        }
        this.handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
    }

    protected void didUnbindDevice(int i, String str, String str2) {
        Message message = new Message();
        if (i == 0) {
            this.progressDialog.dismiss();
            deviceslist.remove(this.tempDevice);
            this.handler.sendEmptyMessage(handler_key.FOUND.ordinal());
            return;
        }
        message.what = handler_key.DELETE_FAIL.ordinal();
        message.obj = str;
        this.handler.sendMessage(message);
    }

    protected void didUserLogin(int i, String str, String str2, String str3) {
        if (!str2.isEmpty() && !str3.isEmpty()) {
            this.setmanager.setUid(str2);
            this.setmanager.setToken(str3);
        }
    }

    public void onBackPressed() {
        exit();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLogout /*2131165306*/:
                if (this.dialog == null) {
                    this.dialog = DialogManager.getLogoutDialog(this, new OnClickListener() {
                        public void onClick(View view) {
                            DeviceListActivity.this.setmanager.setToken("");
                            DeviceListActivity.this.setmanager.setUserName("");
                            DeviceListActivity.this.setmanager.setPassword("");
                            DeviceListActivity.this.setmanager.setUid("");
                            DialogManager.dismissDialog(DeviceListActivity.this, DeviceListActivity.this.dialog);
                            ToastUtils.showShort(DeviceListActivity.this, "注销成功");
                            IntentUtils.getInstance().startActivity(DeviceListActivity.this, LoginActivity.class);
                            DeviceListActivity.this.finish();
                        }
                    });
                }
                DialogManager.showDialog(this, this.dialog);
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
        setContentView(R.layout.activity_devicelist);
        new ActivityManager().applyKitKatTranslucency(this, -16725413);
        initViews();
        initEvents();
        if (getIntent() != null && getIntent().getBooleanExtra("autoLogin", false)) {
            this.mCenter.cLogin(this.setmanager.getUserName(), this.setmanager.getPassword());
        }
        checkUpdate();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        System.out.println("onItemClick:" + i);
        XPGWifiDevice deviceByPosition = this.deviceListAdapter.getDeviceByPosition(i);
        if (deviceByPosition != null) {
            if (deviceByPosition.isLAN()) {
                if (deviceByPosition.isBind(this.setmanager.getUid())) {
                    Log.i(TAG, "本地登陆设备:mac=" + deviceByPosition.getPasscode() + ";ip=" + deviceByPosition.getIPAddress() + ";did=" + deviceByPosition.getDid() + ";passcode=" + deviceByPosition.getPasscode());
                    loginDevice(deviceByPosition);
                    return;
                }
                Log.i(TAG, "绑定设备:mac=" + deviceByPosition.getMacAddress() + ";ip=" + deviceByPosition.getIPAddress() + ";did=" + deviceByPosition.getDid() + ";passcode=" + deviceByPosition.getPasscode());
                Intent intent = new Intent(this, BindingDeviceActivity.class);
                intent.putExtra("mac", deviceByPosition.getMacAddress());
                intent.putExtra("did", deviceByPosition.getDid());
                startActivity(intent);
            } else if (deviceByPosition.isOnline()) {
                Log.i(TAG, "远程登陆设备:mac=" + deviceByPosition.getPasscode() + ";ip=" + deviceByPosition.getIPAddress() + ";did=" + deviceByPosition.getDid() + ";passcode=" + deviceByPosition.getPasscode());
                loginDevice(deviceByPosition);
            } else {
                Log.i(TAG, "离线设备:mac=" + deviceByPosition.getPasscode() + ";ip=" + deviceByPosition.getIPAddress() + ";did=" + deviceByPosition.getDid() + ";passcode=" + deviceByPosition.getPasscode());
            }
        }
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
        System.out.println("onItemLongClick:" + i);
        this.tempDevice = this.deviceListAdapter.getDeviceByPosition(i - 1);
        if (this.tempDevice != null) {
            Builder builder = new Builder(this);
            builder.setIcon(R.drawable.ic_launcher);
            builder.setItems(new String[]{"设备命名", "设备删除"}, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case 0:
                            Intent intent = new Intent(DeviceListActivity.this, DeviceManageDetailActivity.class);
                            intent.putExtra("mac", DeviceListActivity.this.tempDevice.getMacAddress());
                            intent.putExtra("did", DeviceListActivity.this.tempDevice.getDid());
                            DeviceListActivity.this.startActivity(intent);
                            return;
                        case 1:
                            if (!DeviceListActivity.this.tempDevice.isLAN()) {
                                DeviceListActivity.this.unbindDialog = DialogManager.getUnbindDialog(DeviceListActivity.this, new OnClickListener() {
                                    public void onClick(View view) {
                                        if (NetworkUtils.isNetworkConnected(DeviceListActivity.this)) {
                                            DialogManager.dismissDialog(DeviceListActivity.this, DeviceListActivity.this.unbindDialog);
                                            DeviceListActivity.this.progressDialog = new ProgressDialog(DeviceListActivity.this);
                                            DeviceListActivity.this.progressDialog.setMessage("删除中，请稍候。");
                                            DeviceListActivity.this.progressDialog.setCancelable(false);
                                            DialogManager.showDialog(DeviceListActivity.this, DeviceListActivity.this.progressDialog);
                                            DeviceListActivity.this.mCenter.cUnbindDevice(DeviceListActivity.this.setmanager.getUid(), DeviceListActivity.this.setmanager.getToken(), DeviceListActivity.this.tempDevice.getDid(), DeviceListActivity.this.tempDevice.getPasscode());
                                            return;
                                        }
                                        ToastUtils.showShort(DeviceListActivity.this, "网络未连接");
                                    }
                                });
                                DeviceListActivity.this.unbindDialog.show();
                                return;
                            } else if (DeviceListActivity.this.tempDevice.isBind(DeviceListActivity.this.setmanager.getUid())) {
                                DeviceListActivity.this.unbindDialog = DialogManager.getUnbindDialog(DeviceListActivity.this, new OnClickListener() {
                                    public void onClick(View view) {
                                        if (NetworkUtils.isNetworkConnected(DeviceListActivity.this)) {
                                            DialogManager.dismissDialog(DeviceListActivity.this, DeviceListActivity.this.unbindDialog);
                                            DeviceListActivity.this.progressDialog = new ProgressDialog(DeviceListActivity.this);
                                            DeviceListActivity.this.progressDialog.setMessage("删除中，请稍候。");
                                            DeviceListActivity.this.progressDialog.setCancelable(false);
                                            DialogManager.showDialog(DeviceListActivity.this, DeviceListActivity.this.progressDialog);
                                            DeviceListActivity.this.mCenter.cUnbindDevice(DeviceListActivity.this.setmanager.getUid(), DeviceListActivity.this.setmanager.getToken(), DeviceListActivity.this.tempDevice.getDid(), DeviceListActivity.this.tempDevice.getPasscode());
                                            return;
                                        }
                                        ToastUtils.showShort(DeviceListActivity.this, "网络未连接");
                                    }
                                });
                                DeviceListActivity.this.unbindDialog.show();
                                return;
                            } else {
                                return;
                            }
                        default:
                            return;
                    }
                }
            });
            builder.show();
        }
        return true;
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(this.mChangeBroadcast);
    }

    public void onResume() {
        super.onResume();
        this.deviceListAdapter.changeDatas(new ArrayList());
        this.lvDevices.refreshDrawableState();
        if (getIntent().getBooleanExtra("isbind", false)) {
            this.mCenter.cBindDevice(this.setmanager.getUid(), this.setmanager.getToken(), getIntent().getStringExtra("did"), getIntent().getStringExtra("passcode"), "");
        } else {
            getList();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(this.mChangeBroadcast, intentFilter);
        this.lvDevices.completeRefreshing();
        this.lvDevices.setOnItemLongClickListener(this);
    }

    public void showUpdateDialog() {
        Builder builder = new Builder(this);
        builder.setTitle("检测到新版本");
        builder.setMessage("是否下载更新?");
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(DeviceListActivity.this, DownloadService.class);
                DeviceListActivity.this.startService(intent);
                DeviceListActivity.this.bindService(intent, DeviceListActivity.this.conn, 1);
                dialogInterface.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
