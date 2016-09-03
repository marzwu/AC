package com.gizwits.framework.activity.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cgt.control.ActivityManager;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.uh.all.airpurifier.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

public class LoginActivity extends BaseActivity implements OnClickListener {
    private Button btnLogin;
    private Button btnRegister;
    private ProgressDialog dialog;
    private EditText etName;
    private EditText etPsw;
    Handler handler = new Handler() {
        private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$account$LoginActivity$handler_key;

        static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$account$LoginActivity$handler_key() {
            int[] iArr = $SWITCH_TABLE$com$gizwits$framework$activity$account$LoginActivity$handler_key;
            if (iArr == null) {
                iArr = new int[handler_key.values().length];
                try {
                    iArr[handler_key.LOGIN_FAIL.ordinal()] = 2;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[handler_key.LOGIN_SUCCESS.ordinal()] = 1;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[handler_key.LOGIN_TIMEOUT.ordinal()] = 3;
                } catch (NoSuchFieldError e3) {
                }
                $SWITCH_TABLE$com$gizwits$framework$activity$account$LoginActivity$handler_key = iArr;
            }
            return iArr;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (AnonymousClass1.$SWITCH_TABLE$com$gizwits$framework$activity$account$LoginActivity$handler_key()[handler_key.values()[message.what].ordinal()]) {
                case 1:
                    LoginActivity.this.handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    Toast.makeText(LoginActivity.this, "登录成功", 0).show();
                    LoginActivity.this.dialog.cancel();
                    IntentUtils.getInstance().startActivity(LoginActivity.this, DeviceListActivity.class);
                    LoginActivity.this.finish();
                    return;
                case 2:
                    LoginActivity.this.handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    Toast.makeText(LoginActivity.this, message.obj, 0).show();
                    LoginActivity.this.dialog.cancel();
                    return;
                case 3:
                    LoginActivity.this.handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    Toast.makeText(LoginActivity.this, "登陆超时", 0).show();
                    LoginActivity.this.dialog.cancel();
                    return;
                default:
                    return;
            }
        }
    };
    private TextView tvForgot;

    private enum handler_key {
        LOGIN_SUCCESS,
        LOGIN_FAIL,
        LOGIN_TIMEOUT
    }

    private void initEvents() {
        this.tvForgot.setOnClickListener(this);
        this.btnLogin.setOnClickListener(this);
        this.btnRegister.setOnClickListener(this);
    }

    private void initViews() {
        this.etName = (EditText) findViewById(R.id.etName);
        this.etPsw = (EditText) findViewById(R.id.etPsw);
        this.tvForgot = (TextView) findViewById(R.id.tvForgot);
        this.btnLogin = (Button) findViewById(R.id.btnLogin);
        this.btnRegister = (Button) findViewById(R.id.btnRegister);
        this.tvForgot.getPaint().setFlags(8);
        this.dialog = new ProgressDialog(this);
        this.dialog.setMessage("登录中，请稍候...");
        if (this.setmanager.getUserName() != null) {
            this.etName.setText(this.setmanager.getUserName());
        }
    }

    protected void didUserLogin(int i, String str, String str2, String str3) {
        if (str2.isEmpty() || str3.isEmpty()) {
            Message message = new Message();
            message.what = handler_key.LOGIN_FAIL.ordinal();
            message.obj = str;
            this.handler.sendMessage(message);
            return;
        }
        this.setmanager.setUserName(this.etName.getText().toString().trim());
        this.setmanager.setPassword(this.etPsw.getText().toString().trim());
        this.setmanager.setUid(str2);
        this.setmanager.setToken(str3);
        this.handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
    }

    public void onBackPressed() {
        exit();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvForgot /*2131165334*/:
                IntentUtils.getInstance().startActivity(this, ForgetPswActivity.class);
                return;
            case R.id.btnLogin /*2131165335*/:
                if (!NetworkUtils.isNetworkConnected(this)) {
                    ToastUtils.showShort((Context) this, "网络未连接");
                    return;
                } else if (StringUtils.isEmpty(this.etName.getText().toString())) {
                    Toast.makeText(this, "请输入用户名", 0).show();
                    return;
                } else if (StringUtils.isEmpty(this.etPsw.getText().toString())) {
                    Toast.makeText(this, "请输入密码", 0).show();
                    return;
                } else {
                    this.dialog.show();
                    String trim = this.etPsw.getText().toString().trim();
                    this.mCenter.cLogin(this.etName.getText().toString().trim(), trim);
                    this.handler.sendEmptyMessageDelayed(handler_key.LOGIN_TIMEOUT.ordinal(), 15000);
                    return;
                }
            case R.id.btnRegister /*2131165336*/:
                if (NetworkUtils.isNetworkConnected(this)) {
                    IntentUtils.getInstance().startActivity(this, RegisterActivity.class);
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        new ActivityManager().applyKitKatTranslucency(this, -16737980);
        initViews();
        initEvents();
    }
}
