package com.gizwits.framework.activity.help;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;

import com.gizwits.framework.activity.BaseActivity;
import com.marz.ac.v1.R;

public class AboutActivity extends BaseActivity {
    private ImageView ivAbout;
    private ImageView ivBack;
    private ScrollView svAbout;

    private void initViews() {
        this.svAbout = (ScrollView) findViewById(R.id.svAbout);
        this.ivAbout = (ImageView) findViewById(R.id.ivAbout);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.ivBack.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AboutActivity.this.onBackPressed();
            }
        });
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.about);
        this.ivAbout.setImageBitmap(decodeResource);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float f = (float) displayMetrics.widthPixels;
        if (f < ((float) decodeResource.getWidth())) {
            this.svAbout.setLayoutParams(new LayoutParams((int) f, (int) (((float) decodeResource.getHeight()) * (f / ((float) decodeResource.getWidth())))));
        }
    }

    public void onBackPressed() {
        finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_about);
        initViews();
    }

    public void onResume() {
        super.onResume();
    }
}
