package com.gizwits.framework.activity.account;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cgt.control.ActivityManager;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.widget.MyInputFilter;
import com.uh.all.airpurifier.R;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

public class ChangePswActivity extends BaseActivity implements OnClickListener {
    private Button btnConfirm;
    private EditText etPswNew;
    private EditText etPswOld;
    Handler handler = new Handler() {
        private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$account$ChangePswActivity$handler_key;

        static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$activity$account$ChangePswActivity$handler_key() {
            int[] iArr = $SWITCH_TABLE$com$gizwits$framework$activity$account$ChangePswActivity$handler_key;
            if (iArr == null) {
                iArr = new int[handler_key.values().length];
                try {
                    iArr[handler_key.CHANGE_FAIL.ordinal()] = 3;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[handler_key.CHANGE_SUCCESS.ordinal()] = 2;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[handler_key.CLOSE.ordinal()] = 1;
                } catch (NoSuchFieldError e3) {
                }
                $SWITCH_TABLE$com$gizwits$framework$activity$account$ChangePswActivity$handler_key = iArr;
            }
            return iArr;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (AnonymousClass1.$SWITCH_TABLE$com$gizwits$framework$activity$account$ChangePswActivity$handler_key()[handler_key.values()[message.what].ordinal()]) {
                case 1:
                    ChangePswActivity.this.finish();
                    return;
                case 2:
                    ChangePswActivity.this.setmanager.setPassword(ChangePswActivity.this.newPsw);
                    ChangePswActivity.this.tvResult.setText(R.string.change_success);
                    ChangePswActivity.this.rlResult.setVisibility(0);
                    ChangePswActivity.this.handler.sendEmptyMessageDelayed(handler_key.CLOSE.ordinal(), 1000);
                    return;
                case 3:
                    ChangePswActivity.this.tvResult.setText(R.string.change_fail);
                    ChangePswActivity.this.rlResult.setVisibility(0);
                    return;
                default:
                    return;
            }
        }
    };
    private ImageView ivBack;
    private Dialog mDialog;
    private String newPsw;
    private String oldPsw;
    private RelativeLayout rlResult;
    private ToggleButton tbPswFlag;
    private TextView tvResult;

    private enum handler_key {
        CLOSE,
        CHANGE_SUCCESS,
        CHANGE_FAIL
    }

    private void changePsw(String str, String str2) {
        this.mCenter.cChangeUserPassword(this.setmanager.getToken(), str, str2);
    }

    private void initEvents() {
        this.ivBack.setOnClickListener(this);
        this.btnConfirm.setOnClickListener(this);
        this.rlResult.setOnClickListener(this);
        this.tbPswFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    ChangePswActivity.this.etPswOld.setInputType(145);
                    ChangePswActivity.this.etPswNew.setInputType(145);
                    return;
                }
                ChangePswActivity.this.etPswOld.setInputType(129);
                ChangePswActivity.this.etPswNew.setInputType(129);
            }
        });
    }

    private void initViews() {
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.etPswOld = (EditText) findViewById(R.id.etPswOld);
        this.etPswNew = (EditText) findViewById(R.id.etPswNew);
        this.tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
        this.btnConfirm = (Button) findViewById(R.id.btnConfirm);
        this.tvResult = (TextView) findViewById(R.id.tvResult);
        this.rlResult = (RelativeLayout) findViewById(R.id.rlResult);
        MyInputFilter myInputFilter = new MyInputFilter();
        this.etPswOld.setFilters(new InputFilter[]{myInputFilter});
        this.etPswNew.setFilters(new InputFilter[]{myInputFilter});
        this.mDialog = DialogManager.getPswChangeDialog(this, new OnClickListener() {
            public void onClick(View view) {
                if (NetworkUtils.isNetworkConnected(ChangePswActivity.this)) {
                    ChangePswActivity.this.changePsw(ChangePswActivity.this.oldPsw, ChangePswActivity.this.newPsw);
                    DialogManager.dismissDialog(ChangePswActivity.this, ChangePswActivity.this.mDialog);
                    return;
                }
                ToastUtils.showShort(ChangePswActivity.this, "网络未连接");
            }
        });
    }

    protected void didChangeUserPassword(int i, String str) {
        if (i == 0) {
            this.handler.sendEmptyMessage(handler_key.CHANGE_SUCCESS.ordinal());
        } else {
            this.handler.sendEmptyMessage(handler_key.CHANGE_FAIL.ordinal());
        }
    }

    public void onBackPressed() {
        finish();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.ivBack || this.rlResult.getVisibility() != 0) {
            switch (view.getId()) {
                case R.id.ivBack /*2131165197*/:
                    onBackPressed();
                    return;
                case R.id.btnConfirm /*2131165283*/:
                    if (NetworkUtils.isNetworkConnected(this)) {
                        this.oldPsw = this.etPswOld.getText().toString();
                        this.newPsw = this.etPswNew.getText().toString();
                        if (StringUtils.isEmpty(this.oldPsw)) {
                            ToastUtils.showShort((Context) this, "请输入旧的密码");
                            return;
                        } else if (StringUtils.isEmpty(this.newPsw)) {
                            ToastUtils.showShort((Context) this, "请输入新的密码");
                            return;
                        } else if (this.newPsw.length() < 6 || this.newPsw.length() > 16) {
                            Toast.makeText(this, "密码长度应为6~16", 0).show();
                            return;
                        } else {
                            this.mDialog.show();
                            return;
                        }
                    }
                    ToastUtils.showShort((Context) this, "网络未连接");
                    return;
                default:
                    return;
            }
        }
        this.rlResult.setVisibility(8);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_change_pwd);
        new ActivityManager().applyKitKatTranslucency(this, -16725413);
        initViews();
        initEvents();
    }
}
