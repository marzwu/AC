package com.gizwits.framework.activity.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.onboarding.SearchDeviceActivity;
import com.gizwits.framework.widget.MyInputFilter;
import com.marz.ac.v1.R;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends BaseActivity implements OnClickListener {
    private Button btnGetCode;
    private Button btnReGetCode;
    private Button btnSure;
    ProgressDialog dialog;
    private EditText etInputCode;
    private EditText etInputPsw;
    private EditText etName;
    Handler handler = new Handler() {

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1:
                    RegisterActivity registerActivity = RegisterActivity.this;
                    registerActivity.secondleft--;
                    if (RegisterActivity.this.secondleft <= 0) {
                        RegisterActivity.this.timer.cancel();
                        RegisterActivity.this.btnReGetCode.setEnabled(true);
                        RegisterActivity.this.btnReGetCode.setText("重新获取验证码");
                        RegisterActivity.this.btnReGetCode.setBackgroundResource(R.drawable.button_blue_short);
                        return;
                    }
                    RegisterActivity.this.btnReGetCode.setText(new StringBuilder(String.valueOf(RegisterActivity.this.secondleft)).append("秒后\n重新获取").toString());
                    return;
                case 2:
                    ToastUtils.showShort(RegisterActivity.this, (String) message.obj);
                    RegisterActivity.this.dialog.cancel();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isRegister", true);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(RegisterActivity.this, SearchDeviceActivity.class);
                    RegisterActivity.this.startActivity(intent);
                    RegisterActivity.this.finish();
                    return;
                case 3:
                    ToastUtils.showShort(RegisterActivity.this, (String) message.obj);
                    RegisterActivity.this.dialog.cancel();
                    return;
                default:
                    return;
            }
        }
    };
    private boolean isEmail = false;
    private ImageView ivBack;
    private ImageView ivStep;
    private LinearLayout llInputCode;
    private LinearLayout llInputPsw;
    int secondleft = 60;
    private ToggleButton tbPswFlag;
    Timer timer;
    private TextView tvPhoneSwitch;
    private TextView tvTips;
    private Message message;

    private enum handler_key {
        TICK_TIME,
        REG_SUCCESS,
        TOAST
    }

    private enum ui_statue {
        DEFAULT,
        PHONE,
        EMAIL
    }

    private void doRegister() {
        String trim;
        String editable;
        if (this.isEmail) {
            trim = this.etName.getText().toString().trim();
            editable = this.etInputPsw.getText().toString();
            if (!trim.contains("@")) {
                Toast.makeText(this, "邮箱格式不正确", 0).show();
                return;
            } else if (editable.contains(" ")) {
                Toast.makeText(this, "密码不能有空格", 0).show();
                return;
            } else if (editable.length() < 6 || editable.length() > 16) {
                Toast.makeText(this, "密码长度应为6~16", 0).show();
                return;
            } else {
                this.mCenter.cRegisterMailUser(trim, editable);
                this.dialog.show();
                return;
            }
        }
        trim = this.etName.getText().toString().trim();
        editable = this.etInputCode.getText().toString().trim();
        String editable2 = this.etInputPsw.getText().toString();
        if (trim.length() != 11) {
            Toast.makeText(this, "电话号码格式不正确", 0).show();
        } else if (editable.length() == 0) {
            Toast.makeText(this, "请输入验证码", 0).show();
        } else if (editable2.contains(" ")) {
            Toast.makeText(this, "密码不能有空格", 0).show();
        } else if (editable2.length() < 6 || editable2.length() > 16) {
            Toast.makeText(this, "密码长度应为6~16", 0).show();
        } else {
            this.mCenter.cRegisterPhoneUser(trim, editable, editable2);
            Log.e("Register", "phone=" + trim + ";code=" + editable + ";password=" + editable2);
            this.dialog.show();
        }
    }

    private void initEvents() {
        this.btnGetCode.setOnClickListener(this);
        this.btnReGetCode.setOnClickListener(this);
        this.btnSure.setOnClickListener(this);
        this.tvPhoneSwitch.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);
        this.tbPswFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    RegisterActivity.this.etInputPsw.setInputType(145);
                } else {
                    RegisterActivity.this.etInputPsw.setInputType(129);
                }
            }
        });
    }

    private void initViews() {
        this.tvTips = (TextView) findViewById(R.id.tvTips);
        this.tvPhoneSwitch = (TextView) findViewById(R.id.tvPhoneSwitch);
        this.etName = (EditText) findViewById(R.id.etName);
        this.etInputCode = (EditText) findViewById(R.id.etInputCode);
        this.etInputPsw = (EditText) findViewById(R.id.etInputPsw);
        this.btnGetCode = (Button) findViewById(R.id.btnGetCode);
        this.btnReGetCode = (Button) findViewById(R.id.btnReGetCode);
        this.btnSure = (Button) findViewById(R.id.btnSure);
        this.llInputCode = (LinearLayout) findViewById(R.id.llInputCode);
        this.llInputPsw = (LinearLayout) findViewById(R.id.llInputPsw);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.ivStep = (ImageView) findViewById(R.id.ivStep);
        this.tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
        toogleUI(ui_statue.DEFAULT);
        this.dialog = new ProgressDialog(this);
        this.dialog.setMessage("处理中，请稍候...");
        MyInputFilter myInputFilter = new MyInputFilter();
        this.etInputPsw.setFilters(new InputFilter[]{myInputFilter});
    }

    private void sendVerifyCode(String str) {
        this.dialog.show();
        this.btnReGetCode.setEnabled(false);
        this.btnReGetCode.setBackgroundResource(R.drawable.button_gray_short);
        this.secondleft = 60;
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            public void run() {
                RegisterActivity.this.handler.sendEmptyMessage(handler_key.TICK_TIME.ordinal());
            }
        }, 1000, 1000);
        this.mCenter.cRequestSendVerifyCode(str);
    }

    private void toogleUI(ui_statue com_gizwits_framework_activity_account_RegisterActivity_ui_statue) {
        if (com_gizwits_framework_activity_account_RegisterActivity_ui_statue == ui_statue.DEFAULT) {
            this.llInputCode.setVisibility(8);
            this.llInputPsw.setVisibility(8);
            this.btnSure.setVisibility(8);
            this.btnGetCode.setVisibility(0);
            this.etName.setHint("手机号");
            this.etName.setText("");
            this.etName.setInputType(2);
            this.tvPhoneSwitch.setText("邮箱注册");
            this.tvTips.setVisibility(8);
        } else if (com_gizwits_framework_activity_account_RegisterActivity_ui_statue == ui_statue.PHONE) {
            this.llInputCode.setVisibility(0);
            this.llInputPsw.setVisibility(0);
            this.btnSure.setVisibility(0);
            this.btnGetCode.setVisibility(8);
            this.etName.setHint("手机号");
            this.etName.setInputType(2);
            this.tvPhoneSwitch.setText("邮箱注册");
            this.tvTips.setVisibility(8);
        } else {
            this.llInputCode.setVisibility(8);
            this.btnGetCode.setVisibility(8);
            this.llInputPsw.setVisibility(0);
            this.btnSure.setVisibility(0);
            this.etName.setHint("邮箱");
            this.etName.setText("");
            this.etName.setInputType(1);
            this.tvPhoneSwitch.setText("手机注册");
            this.tvTips.setVisibility(0);
        }
    }

    protected void didRegisterUser(int i, String str, String str2, String str3) {
        Log.i("error message uid token", new StringBuilder(String.valueOf(i)).append(" ").append(str).append(" ").append(str2).append(" ").append(str3).toString());
        if (str2.equals("") || str3.equals("")) {
            Message message = new Message();
            message.what = handler_key.TOAST.ordinal();
            message.obj = str;
            this.handler.sendMessage(message);
            return;
        }
        message = new Message();
        message.what = handler_key.REG_SUCCESS.ordinal();
        message.obj = "注册成功";
        this.handler.sendMessage(message);
        this.setmanager.setUserName(this.etName.getText().toString().trim());
        this.setmanager.setPassword(this.etInputPsw.getText().toString().trim());
        this.setmanager.setUid(str2);
        this.setmanager.setToken(str3);
    }

    protected void didRequestSendVerifyCode(int i, String str) {
        Log.i("error message ", new StringBuilder(String.valueOf(i)).append(" ").append(str).toString());
        if (i == 0) {
            Message message = new Message();
            message.what = handler_key.TOAST.ordinal();
            message.obj = "发送成功";
            this.handler.sendMessage(message);
            return;
        }
        message = new Message();
        message.what = handler_key.TOAST.ordinal();
        message.obj = str;
        this.handler.sendMessage(message);
    }

    public void onClick(View view) {
        String trim;
        switch (view.getId()) {
            case R.id.ivBack /*2131165197*/:
                onBackPressed();
                return;
            case R.id.btnGetCode /*2131165317*/:
                trim = this.etName.getText().toString().trim();
                if (StringUtils.isEmpty(trim) || trim.length() != 11) {
                    ToastUtils.showShort((Context) this, "请输入正确的手机号码。");
                    return;
                }
                toogleUI(ui_statue.PHONE);
                sendVerifyCode(trim);
                return;
            case R.id.btnReGetCode /*2131165324*/:
                trim = this.etName.getText().toString().trim();
                if (StringUtils.isEmpty(trim) || trim.length() != 11) {
                    ToastUtils.showShort((Context) this, "请输入正确的手机号码。");
                    return;
                }
                toogleUI(ui_statue.PHONE);
                sendVerifyCode(trim);
                return;
            case R.id.btnSure /*2131165325*/:
                doRegister();
                return;
            case R.id.tvPhoneSwitch /*2131165337*/:
                if (this.isEmail) {
                    toogleUI(ui_statue.DEFAULT);
                    this.isEmail = false;
                    return;
                }
                toogleUI(ui_statue.EMAIL);
                this.isEmail = true;
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        initViews();
        initEvents();
    }
}
