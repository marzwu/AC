package com.gizwits.airpurifier.activity.control;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.gizwits.framework.activity.BaseActivity;
import com.uh.all.airpurifier.R;

public class CurveActivity extends BaseActivity {
    private ImageView ivBack;

    public void onBackPressed() {
        finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_curve);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.ivBack.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CurveActivity.this.onBackPressed();
            }
        });
    }
}
