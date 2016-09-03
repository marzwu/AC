package com.gizwits.airpurifier.activity.advanced;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.views.RoseBoxSeekBarView;
import com.uh.all.airpurifier.R;

public class RoseboxFragment extends Fragment implements OnClickListener {
    private AdvancedActivity advancedActivity;
    private Button btnReset;
    private Dialog dialog = null;
    private int lv = 1000;
    private RoseBoxSeekBarView mRoseBoxSeekBarView;
    private Dialog resetDialog;
    private TextView tvStatu;

    public RoseboxFragment(AdvancedActivity advancedActivity) {
        this.advancedActivity = advancedActivity;
    }

    public void onClick(View view) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.resetDialog = DialogManager.getResetRoseboxDialog(getActivity(), new OnClickListener() {
            public void onClick(View view) {
                if (RoseboxFragment.this.resetDialog != null) {
                    RoseboxFragment.this.resetDialog.dismiss();
                }
            }
        });
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_rosebox_layout, null);
        this.mRoseBoxSeekBarView = (RoseBoxSeekBarView) inflate.findViewById(R.id.roseCircleSeekbar);
        this.tvStatu = (TextView) inflate.findViewById(R.id.statues);
        this.btnReset = (Button) inflate.findViewById(R.id.reset_btn);
        this.btnReset.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                RoseboxFragment.this.dialog = DialogManager.getResetRoseboxDialog(RoseboxFragment.this.advancedActivity, new OnClickListener() {
                    public void onClick(View view) {
                        RoseboxFragment.this.advancedActivity.resetRosebox();
                        RoseboxFragment.this.dialog.dismiss();
                    }
                });
                RoseboxFragment.this.dialog.show();
            }
        });
        return inflate;
    }

    public void onResume() {
        super.onResume();
        if (this.lv != 1000) {
            updateStatus(this.lv);
        }
    }

    public void setCurrent(int i) {
        this.lv = i;
    }

    public void updateStatus(int i) {
        switch (i) {
            case 0:
                this.tvStatu.setText("失效停机");
                this.tvStatu.setTextColor(getResources().getColor(R.color.rb_text_error));
                this.mRoseBoxSeekBarView.setRoseboxErr(true);
                break;
            default:
                this.tvStatu.setText("正常");
                this.tvStatu.setTextColor(getResources().getColor(R.color.rb_text_normal));
                this.mRoseBoxSeekBarView.setRoseboxErr(false);
                break;
        }
        this.mRoseBoxSeekBarView.setPercent(i);
    }
}
