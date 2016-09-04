package com.gizwits.airpurifier.activity.control;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.adapter.AlarmListAdapter;
import com.gizwits.framework.entity.DeviceAlarm;
import com.marz.ac.v1.R;

import java.util.ArrayList;

public class AlarmListActicity extends BaseActivity implements OnClickListener {
    private ArrayList<DeviceAlarm> alarmList;
    private Button btnCall;
    private ImageView ivBack;
    private ListView lvList;
    private AlarmListAdapter mAdapter;

    private void initDatas() {
        this.alarmList = new ArrayList();
        this.alarmList = (ArrayList) getIntent().getSerializableExtra("alarm_list");
        this.mAdapter = new AlarmListAdapter(this, this.alarmList);
        this.lvList.setAdapter(this.mAdapter);
    }

    private void initEvents() {
        this.btnCall.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);
    }

    private void initViews() {
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.lvList = (ListView) findViewById(R.id.lvList);
        this.btnCall = (Button) findViewById(R.id.btnCall);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack /*2131165197*/:
                onBackPressed();
                return;
            case R.id.btnCall /*2131165263*/:
                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:10086")));
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_alarmlist);
        initViews();
        initEvents();
        initDatas();
    }
}
