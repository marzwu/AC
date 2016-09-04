package com.gizwits.framework.activity.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cgt.control.ActivityManager;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.widget.MyInputFilter;
import com.marz.ac.v1.R;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

public class ForgetPswActivity extends BaseActivity implements OnClickListener {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$account$ForgetPswActivity$ui_statu;
    private Button btnEmailReset;
    private Button btnGetCode;
    private Button btnPhoneReset;
    private Button btnReGetCode;
    private Button btnSure;
    private Button btnSureEmail;
    ProgressDialog dialog;
    private EditText etInputCode;
    private EditText etInputEmail;
    private EditText etInputPsw;
    private EditText etName;
    Handler handler = new Handler() {

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1:
                    ForgetPswActivity forgetPswActivity = ForgetPswActivity.this;
                    forgetPswActivity.secondleft--;
                    if (ForgetPswActivity.this.secondleft <= 0) {
                        ForgetPswActivity.this.timer.cancel();
                        ForgetPswActivity.this.btnReGetCode.setEnabled(true);
                        ForgetPswActivity.this.btnReGetCode.setText(R.string.forget_password_get_verifycode2);
                        ForgetPswActivity.this.btnReGetCode.setBackgroundResource(R.drawable.button_blue_short);
                        return;
                    }
                    ForgetPswActivity.this.btnReGetCode.setText(new StringBuilder(String.valueOf(ForgetPswActivity.this.secondleft)).append(ForgetPswActivity.this.getResources().getText(R.string.forget_password_get_verifycode3)).toString());
                    return;
                case 2:
                    ForgetPswActivity.this.finish();
                    return;
                case 3:
                    ForgetPswActivity.this.tvDialog.setText((String) message.obj);
                    ForgetPswActivity.this.rlDialog.setVisibility(0);
                    ForgetPswActivity.this.dialog.cancel();
                    return;
                default:
                    return;
            }
        }
    };
    private ImageView ivBack;
    private LinearLayout llInputMenu;
    private LinearLayout llInputPhone;
    private RelativeLayout rlDialog;
    private RelativeLayout rlInputEmail;
    int secondleft = 60;
    private ui_statu statuNow;
    private ToggleButton tbPswFlag;
    Timer timer;
    private TextView tvDialog;

    private enum handler_key {
        TICK_TIME,
        CHANGE_SUCCESS,
        TOAST
    }

    private enum ui_statu {
        DEFAULT,
        PHONE,
        EMAIL
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$account$ForgetPswActivity$ui_statu() {
        int[] iArr = $SWITCH_TABLE$com$gizwits$framework$activity$account$ForgetPswActivity$ui_statu;
        if (iArr == null) {
            iArr = new int[ui_statu.values().length];
            try {
                iArr[ui_statu.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[ui_statu.EMAIL.ordinal()] = 3;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[ui_statu.PHONE.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$com$gizwits$framework$activity$account$ForgetPswActivity$ui_statu = iArr;
        }
        return iArr;
    }

    private void doChangePsw() {
        String trim = this.etName.getText().toString().trim();
        String trim2 = this.etInputCode.getText().toString().trim();
        String editable = this.etInputPsw.getText().toString();
        if (trim.length() != 11) {
            Toast.makeText(this, "电话号码格式不正确", 0).show();
        } else if (trim2.length() == 0) {
            Toast.makeText(this, "请输入验证码", 0).show();
        } else if (editable.contains(" ")) {
            Toast.makeText(this, "密码不能有空格", 0).show();
        } else if (editable.length() < 6 || editable.length() > 16) {
            Toast.makeText(this, "密码长度应为6~16", 0).show();
        } else {
            this.mCenter.cChangeUserPasswordWithCode(trim, trim2, editable);
            this.dialog.show();
        }
    }

    private void getEmail(String str) {
        this.mCenter.cChangePassworfByEmail(str);
        this.dialog.show();
    }

    private void initEvents() {
        this.rlDialog.setOnClickListener(this);
        this.btnGetCode.setOnClickListener(this);
        this.btnReGetCode.setOnClickListener(this);
        this.btnSureEmail.setOnClickListener(this);
        this.btnSure.setOnClickListener(this);
        this.btnPhoneReset.setOnClickListener(this);
        this.btnEmailReset.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);
        this.tbPswFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    ForgetPswActivity.this.etInputPsw.setInputType(145);
                } else {
                    ForgetPswActivity.this.etInputPsw.setInputType(129);
                }
            }
        });
    }

    private void initViews() {
        this.tvDialog = (TextView) findViewById(R.id.tvDialog);
        this.etName = (EditText) findViewById(R.id.etName);
        this.etInputCode = (EditText) findViewById(R.id.etInputCode);
        this.etInputPsw = (EditText) findViewById(R.id.etInputPsw);
        this.etInputEmail = (EditText) findViewById(R.id.etInputEmail);
        this.btnGetCode = (Button) findViewById(R.id.btnGetCode);
        this.btnReGetCode = (Button) findViewById(R.id.btnReGetCode);
        this.btnSure = (Button) findViewById(R.id.btnSure);
        this.btnSureEmail = (Button) findViewById(R.id.btnSureEmail);
        this.btnPhoneReset = (Button) findViewById(R.id.btnPhoneReset);
        this.btnEmailReset = (Button) findViewById(R.id.btnEmailReset);
        this.llInputMenu = (LinearLayout) findViewById(R.id.llInputMenu);
        this.llInputPhone = (LinearLayout) findViewById(R.id.llInputPhone);
        this.rlInputEmail = (RelativeLayout) findViewById(R.id.rlInputEmail);
        this.rlDialog = (RelativeLayout) findViewById(R.id.rlDialog);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
        toogleUI(ui_statu.DEFAULT);
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
                ForgetPswActivity.this.handler.sendEmptyMessage(handler_key.TICK_TIME.ordinal());
            }
        }, 1000, 1000);
        this.mCenter.cRequestSendVerifyCode(str);
    }

    private void toogleUI(ui_statu com_gizwits_framework_activity_account_ForgetPswActivity_ui_statu) {
        this.statuNow = com_gizwits_framework_activity_account_ForgetPswActivity_ui_statu;
        switch ($SWITCH_TABLE$com$gizwits$framework$activity$account$ForgetPswActivity$ui_statu()[com_gizwits_framework_activity_account_ForgetPswActivity_ui_statu.ordinal()]) {
            case 1:
                this.llInputMenu.setVisibility(0);
                this.llInputPhone.setVisibility(8);
                this.rlInputEmail.setVisibility(8);
                this.etInputCode.setText("");
                this.etInputEmail.setText("");
                this.etInputPsw.setText("");
                this.etName.setText("");
                return;
            case 2:
                this.llInputMenu.setVisibility(8);
                this.llInputPhone.setVisibility(0);
                this.rlInputEmail.setVisibility(8);
                return;
            case 3:
                this.llInputMenu.setVisibility(8);
                this.llInputPhone.setVisibility(8);
                this.rlInputEmail.setVisibility(0);
                return;
            default:
                return;
        }
    }

    protected void didChangeUserPassword(int i, String str) {
        Message message;
        if (i == 0) {
            message = new Message();
            message.what = handler_key.TOAST.ordinal();
            if (this.statuNow == ui_statu.EMAIL) {
                Drawable drawable = getResources().getDrawable(R.drawable.slib_tick);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                this.tvDialog.setCompoundDrawables(drawable, null, null, null);
                message.obj = "已发送至您的邮箱，\n请登录邮箱查收！";
            } else {
                message.obj = "设置成功";
            }
            this.handler.sendMessage(message);
            this.handler.sendEmptyMessageDelayed(handler_key.CHANGE_SUCCESS.ordinal(), 2000);
        } else {
            message = new Message();
            message.what = handler_key.TOAST.ordinal();
            message.obj = str;
            this.handler.sendMessage(message);
        }
        super.didChangeUserPassword(i, str);
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
        Message message = new Message();
        message.what = handler_key.TOAST.ordinal();
        message.obj = str;
        this.handler.sendMessage(message);
    }

    public void onBackPressed() {
        if (this.rlDialog.getVisibility() == 0) {
            this.rlDialog.setVisibility(8);
            return;
        }
        switch ($SWITCH_TABLE$com$gizwits$framework$activity$account$ForgetPswActivity$ui_statu()[this.statuNow.ordinal()]) {
            case 1:
                finish();
                return;
            case 2:
            case 3:
                toogleUI(ui_statu.DEFAULT);
                return;
            default:
                return;
        }
    }

    public void onClick(View view) {
        String trim;
        switch (view.getId()) {
            case R.id.ivBack /*2131165197*/:
                onBackPressed();
                return;
            case R.id.btnPhoneReset /*2131165319*/:
                toogleUI(ui_statu.PHONE);
                return;
            case R.id.btnEmailReset /*2131165320*/:
                toogleUI(ui_statu.EMAIL);
                return;
            case R.id.btnReGetCode /*2131165324*/:
                if (NetworkUtils.isNetworkConnected(this)) {
                    trim = this.etName.getText().toString().trim();
                    if (StringUtils.isEmpty(trim) || trim.length() != 11) {
                        ToastUtils.showShort((Context) this, "请输入正确的手机号码。");
                        return;
                    } else {
                        sendVerifyCode(trim);
                        return;
                    }
                }
                ToastUtils.showShort((Context) this, "网络未连接");
                return;
            case R.id.btnSure /*2131165325*/:
                if (NetworkUtils.isNetworkConnected(this)) {
                    doChangePsw();
                    return;
                } else {
                    ToastUtils.showShort((Context) this, "网络未连接");
                    return;
                }
            case R.id.btnSureEmail /*2131165327*/:
                if (NetworkUtils.isNetworkConnected(this)) {
                    trim = this.etInputEmail.getText().toString().trim();
                    if (StringUtils.isEmpty(trim) || !trim.contains("@")) {
                        ToastUtils.showShort((Context) this, "请输入正确的账号。");
                        return;
                    } else {
                        getEmail(trim);
                        return;
                    }
                }
                ToastUtils.showShort((Context) this, "网络未连接");
                return;
            case R.id.rlDialog /*2131165329*/:
                this.rlDialog.setVisibility(8);
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_forget_reset);
        new ActivityManager().applyKitKatTranslucency(this, -16725413);
        initViews();
        initEvents();
    }
}
