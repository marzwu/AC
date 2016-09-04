package com.gizwits.airpurifier.activity.advanced;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.marz.ac.v1.R;

import java.lang.reflect.Method;

public class SensitivityFragment extends Fragment {
    private AdvancedActivity activity;
    private float position;
    private SeekBar sensitive_seek;
    private TextView showLevel_tv;

    public SensitivityFragment(AdvancedActivity advancedActivity) {
        this.activity = advancedActivity;
    }

    @SuppressLint({"NewApi"})
    private void handleTips(SeekBar seekBar) {
        int i;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        try {
            Method[] methods = Class.forName(SeekBar.class.getName()).getMethods();
            for (Method name : methods) {
                if ("getThumb".equals(name.getName())) {
                    i = 1;
                    break;
                }
            }
            i = 0;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            i = 0;
        }
        if (i != 0) {
            Rect bounds = seekBar.getThumb().getBounds();
            int progress = seekBar.getProgress();
            if (progress == 0) {
                seekBar.setProgress(1);
                this.showLevel_tv.setText("一档");
            } else if (progress == 1) {
                this.showLevel_tv.setText("一档");
            } else if (progress == 2) {
                this.showLevel_tv.setText("二档");
            } else if (progress == 3) {
                this.showLevel_tv.setText("三档");
            } else if (progress == 4) {
                this.showLevel_tv.setText("四档");
            }
            layoutParams.leftMargin = (bounds.width() / 4) + bounds.left;
            if (layoutParams.leftMargin <= 0) {
                layoutParams.leftMargin = (int) this.position;
            } else {
                this.position = (float) layoutParams.leftMargin;
            }
            this.showLevel_tv.setLayoutParams(layoutParams);
        }
    }

    public void changeSensi(int i) {
        if (this.sensitive_seek != null) {
            this.sensitive_seek.setProgress(i);
        }
    }

    public void initSeekBar() {
        this.sensitive_seek.setEnabled(true);
        this.sensitive_seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                SensitivityFragment.this.handleTips(seekBar);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if (progress == 0) {
                    SensitivityFragment.this.activity.sendSensitivityLv(1);
                } else {
                    SensitivityFragment.this.activity.sendSensitivityLv(progress);
                }
            }
        });
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_sensitivity_layout, null);
        this.sensitive_seek = (SeekBar) inflate.findViewById(R.id.sensitive_seek);
        this.showLevel_tv = (TextView) inflate.findViewById(R.id.showLevel_tv);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        if (this.position == 0.0f) {
            this.position = (float) (i / 4);
        }
        initSeekBar();
        changeSensi(0);
        return inflate;
    }

    public void onResume() {
        super.onResume();
        handleTips(this.sensitive_seek);
    }
}
