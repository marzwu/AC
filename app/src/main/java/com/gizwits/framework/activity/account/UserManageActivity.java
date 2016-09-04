package com.gizwits.framework.activity.account;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.utils.DialogManager;
import com.marz.ac.v1.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.ui.utils.ToastUtils;

public class UserManageActivity extends BaseActivity implements OnClickListener {
    private Button btnChange;
    private Button btnLogout;
    private Dialog dialog;
    private ImageView ivBack;
    private TextView tvName;

    private void initEvents() {
        this.btnChange.setOnClickListener(this);
        this.btnLogout.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);
    }

    private void initViews() {
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.tvName = (TextView) findViewById(R.id.tvName);
        this.btnChange = (Button) findViewById(R.id.btnChange);
        this.btnLogout = (Button) findViewById(R.id.btnLogout);
        this.tvName.setText(this.setmanager.getUserName());
    }

    public void onBackPressed() {
        finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack /*2131165197*/:
                onBackPressed();
                return;
            case R.id.btnChange /*2131165364*/:
                IntentUtils.getInstance().startActivity(this, ChangePswActivity.class);
                return;
            case R.id.btnLogout /*2131165365*/:
                if (this.dialog == null) {
                    this.dialog = DialogManager.getLogoutDialog(this, new OnClickListener() {
                        public void onClick(View view) {
                            UserManageActivity.this.setmanager.setToken("");
                            DialogManager.dismissDialog(UserManageActivity.this, UserManageActivity.this.dialog);
                            ToastUtils.showShort(UserManageActivity.this, "注销成功");
                            IntentUtils.getInstance().startActivity(UserManageActivity.this, LoginActivity.class);
                            UserManageActivity.this.finish();
                        }
                    });
                }
                DialogManager.showDialog(this, this.dialog);
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_user_manage);
        initViews();
        initEvents();
    }
}
