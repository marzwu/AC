package com.gizwits.framework.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gizwits.framework.widget.ArrayWheelAdapter;
import com.gizwits.framework.widget.WheelView;
import com.uh.all.airpurifier.R;

public class DialogManager {

    public interface OnTimingChosenListener {
        void timingChosen(int i);
    }

    class AnonymousClass10 implements OnClickListener {
        private final /* synthetic */ Activity val$ctx;
        private final /* synthetic */ Dialog val$dialog;

        AnonymousClass10(Activity activity, Dialog dialog) {
            this.val$ctx = activity;
            this.val$dialog = dialog;
        }

        public void onClick(View view) {
            DialogManager.dismissDialog(this.val$ctx, this.val$dialog);
        }
    }

    class AnonymousClass11 extends Dialog {
        AnonymousClass11(Context context, int i) {
            super(context, i);
        }
    }

    class AnonymousClass12 implements OnClickListener {
        private final /* synthetic */ Activity val$ctx;
        private final /* synthetic */ Dialog val$dialog;

        AnonymousClass12(Activity activity, Dialog dialog) {
            this.val$ctx = activity;
            this.val$dialog = dialog;
        }

        public void onClick(View view) {
            DialogManager.dismissDialog(this.val$ctx, this.val$dialog);
        }
    }

    class AnonymousClass13 extends Dialog {
        AnonymousClass13(Context context, int i) {
            super(context, i);
        }
    }

    class AnonymousClass14 implements OnClickListener {
        private final /* synthetic */ Activity val$ctx;
        private final /* synthetic */ Dialog val$dialog;

        AnonymousClass14(Activity activity, Dialog dialog) {
            this.val$ctx = activity;
            this.val$dialog = dialog;
        }

        public void onClick(View view) {
            DialogManager.dismissDialog(this.val$ctx, this.val$dialog);
        }
    }

    class AnonymousClass15 implements OnClickListener {
        private final /* synthetic */ Activity val$ctx;
        private final /* synthetic */ Dialog val$dialog;
        private final /* synthetic */ int[] val$hour;
        private final /* synthetic */ OnTimingChosenListener val$l;
        private final /* synthetic */ WheelView val$wheelveiew;

        AnonymousClass15(WheelView wheelView, OnTimingChosenListener onTimingChosenListener, int[] iArr, Activity activity, Dialog dialog) {
            this.val$wheelveiew = wheelView;
            this.val$l = onTimingChosenListener;
            this.val$hour = iArr;
            this.val$ctx = activity;
            this.val$dialog = dialog;
        }

        public void onClick(View view) {
            this.val$l.timingChosen(this.val$hour[this.val$wheelveiew.getCurrentItem()]);
            DialogManager.dismissDialog(this.val$ctx, this.val$dialog);
        }
    }

    class AnonymousClass16 extends Dialog {
        AnonymousClass16(Context context, int i) {
            super(context, i);
        }
    }

    class AnonymousClass17 implements OnClickListener {
        private final /* synthetic */ Activity val$ctx;
        private final /* synthetic */ Dialog val$dialog;

        AnonymousClass17(Activity activity, Dialog dialog) {
            this.val$ctx = activity;
            this.val$dialog = dialog;
        }

        public void onClick(View view) {
            DialogManager.dismissDialog(this.val$ctx, this.val$dialog);
        }
    }

    class AnonymousClass18 implements OnClickListener {
        private final /* synthetic */ Activity val$ctx;
        private final /* synthetic */ Dialog val$dialog;
        private final /* synthetic */ int[] val$hour;
        private final /* synthetic */ OnTimingChosenListener val$l;
        private final /* synthetic */ WheelView val$wheelveiew;

        AnonymousClass18(WheelView wheelView, OnTimingChosenListener onTimingChosenListener, int[] iArr, Activity activity, Dialog dialog) {
            this.val$wheelveiew = wheelView;
            this.val$l = onTimingChosenListener;
            this.val$hour = iArr;
            this.val$ctx = activity;
            this.val$dialog = dialog;
        }

        public void onClick(View view) {
            this.val$l.timingChosen(this.val$hour[this.val$wheelveiew.getCurrentItem()]);
            DialogManager.dismissDialog(this.val$ctx, this.val$dialog);
        }
    }

    class AnonymousClass19 extends Dialog {
        AnonymousClass19(Context context, int i) {
            super(context, i);
        }

        public void dismiss() {
            super.dismiss();
        }
    }

    class AnonymousClass1 extends Dialog {
        AnonymousClass1(Context context, int i) {
            super(context, i);
        }
    }

    class AnonymousClass20 implements OnClickListener {
        private final /* synthetic */ Dialog val$dialog;

        AnonymousClass20(Dialog dialog) {
            this.val$dialog = dialog;
        }

        public void onClick(View view) {
            this.val$dialog.dismiss();
        }
    }

    class AnonymousClass2 implements OnClickListener {
        private final /* synthetic */ Activity val$ctx;
        private final /* synthetic */ Dialog val$dialog;

        AnonymousClass2(Activity activity, Dialog dialog) {
            this.val$ctx = activity;
            this.val$dialog = dialog;
        }

        public void onClick(View view) {
            DialogManager.dismissDialog(this.val$ctx, this.val$dialog);
        }
    }

    class AnonymousClass3 extends Dialog {
        AnonymousClass3(Context context, int i) {
            super(context, i);
        }
    }

    class AnonymousClass4 extends Dialog {
        AnonymousClass4(Context context, int i) {
            super(context, i);
        }
    }

    class AnonymousClass5 implements OnClickListener {
        private final /* synthetic */ Activity val$ctx;
        private final /* synthetic */ Dialog val$dialog;

        AnonymousClass5(Activity activity, Dialog dialog) {
            this.val$ctx = activity;
            this.val$dialog = dialog;
        }

        public void onClick(View view) {
            DialogManager.dismissDialog(this.val$ctx, this.val$dialog);
        }
    }

    class AnonymousClass6 extends Dialog {
        AnonymousClass6(Context context, int i) {
            super(context, i);
        }
    }

    class AnonymousClass7 implements OnClickListener {
        private final /* synthetic */ Activity val$ctx;
        private final /* synthetic */ Dialog val$dialog;

        AnonymousClass7(Activity activity, Dialog dialog) {
            this.val$ctx = activity;
            this.val$dialog = dialog;
        }

        public void onClick(View view) {
            DialogManager.dismissDialog(this.val$ctx, this.val$dialog);
        }
    }

    class AnonymousClass8 extends Dialog {
        AnonymousClass8(Context context, int i) {
            super(context, i);
        }
    }

    class AnonymousClass9 extends Dialog {
        AnonymousClass9(Context context, int i) {
            super(context, i);
        }
    }

    public static void dismissDialog(Activity activity, Dialog dialog) {
        if (dialog != null && dialog.isShowing() && activity != null && !activity.isFinishing()) {
            dialog.dismiss();
        }
    }

    public static Dialog getDeviceErrirDialog(Activity activity, String str, OnClickListener onClickListener) {
        Dialog anonymousClass11 = new AnonymousClass11(activity, R.style.noBackgroundDialog);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_alarm_for_conditioner, null);
        Button button = (Button) inflate.findViewById(R.id.fault_left_btn);
        Button button2 = (Button) inflate.findViewById(R.id.fault_right_btn);
        ((TextView) inflate.findViewById(R.id.fault_content)).setText(str);
        button.setOnClickListener(new AnonymousClass12(activity, anonymousClass11));
        button2.setOnClickListener(onClickListener);
        anonymousClass11.requestWindowFeature(1);
        anonymousClass11.setCanceledOnTouchOutside(false);
        anonymousClass11.setCancelable(false);
        anonymousClass11.setContentView(inflate);
        return anonymousClass11;
    }

    public static Dialog getDisconnectDialog(Activity activity, OnClickListener onClickListener) {
        Dialog anonymousClass3 = new AnonymousClass3(activity, R.style.noBackgroundDialog);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_disconnect, null);
        ((Button) inflate.findViewById(R.id.btn_confirm)).setOnClickListener(onClickListener);
        anonymousClass3.requestWindowFeature(1);
        anonymousClass3.setCanceledOnTouchOutside(false);
        anonymousClass3.setCancelable(false);
        anonymousClass3.setContentView(inflate);
        return anonymousClass3;
    }

    public static Dialog getFixDialog(Activity activity, OnClickListener onClickListener) {
        Dialog anonymousClass8 = new AnonymousClass8(activity, R.style.noBackgroundDialog);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_fix, null);
        ((Button) inflate.findViewById(R.id.btn_confirm)).setOnClickListener(onClickListener);
        anonymousClass8.requestWindowFeature(1);
        anonymousClass8.setCanceledOnTouchOutside(false);
        anonymousClass8.setCancelable(false);
        anonymousClass8.setContentView(inflate);
        return anonymousClass8;
    }

    public static Dialog getLogoutDialog(Activity activity, OnClickListener onClickListener) {
        Dialog anonymousClass1 = new AnonymousClass1(activity, R.style.noBackgroundDialog);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_logout, null);
        Button button = (Button) inflate.findViewById(R.id.right_btn);
        ((Button) inflate.findViewById(R.id.left_btn)).setOnClickListener(new AnonymousClass2(activity, anonymousClass1));
        button.setOnClickListener(onClickListener);
        anonymousClass1.requestWindowFeature(1);
        anonymousClass1.setCanceledOnTouchOutside(false);
        anonymousClass1.setCancelable(false);
        anonymousClass1.setContentView(inflate);
        return anonymousClass1;
    }

    public static Dialog getNoNetworkDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.noBackgroundDialog);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_no_network, null);
        dialog.requestWindowFeature(1);
        dialog.setContentView(inflate);
        return dialog;
    }

    public static Dialog getPowerOffDialog(Activity activity, OnClickListener onClickListener) {
        Dialog anonymousClass6 = new AnonymousClass6(activity, R.style.noBackgroundDialog);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_power_off, null);
        Button button = (Button) inflate.findViewById(R.id.right_btn);
        ((Button) inflate.findViewById(R.id.left_btn)).setOnClickListener(new AnonymousClass7(activity, anonymousClass6));
        button.setOnClickListener(onClickListener);
        anonymousClass6.requestWindowFeature(1);
        anonymousClass6.setCanceledOnTouchOutside(false);
        anonymousClass6.setCancelable(false);
        anonymousClass6.setContentView(inflate);
        return anonymousClass6;
    }

    public static Dialog getPswChangeDialog(Activity activity, OnClickListener onClickListener) {
        Dialog anonymousClass9 = new AnonymousClass9(activity, R.style.noBackgroundDialog);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_psw_change, null);
        Button button = (Button) inflate.findViewById(R.id.right_btn);
        ((Button) inflate.findViewById(R.id.left_btn)).setOnClickListener(new AnonymousClass10(activity, anonymousClass9));
        button.setOnClickListener(onClickListener);
        anonymousClass9.requestWindowFeature(1);
        anonymousClass9.setCanceledOnTouchOutside(false);
        anonymousClass9.setCancelable(false);
        anonymousClass9.setContentView(inflate);
        return anonymousClass9;
    }

    public static Dialog getResetRoseboxDialog(Activity activity, OnClickListener onClickListener) {
        Dialog anonymousClass19 = new AnonymousClass19(activity, R.style.dialog_orderDetail);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_reset_rosebox, null);
        Button button = (Button) inflate.findViewById(R.id.right_btn);
        ((Button) inflate.findViewById(R.id.left_btn)).setOnClickListener(new AnonymousClass20(anonymousClass19));
        button.setOnClickListener(onClickListener);
        anonymousClass19.requestWindowFeature(1);
        anonymousClass19.setCanceledOnTouchOutside(false);
        anonymousClass19.setCancelable(false);
        anonymousClass19.setContentView(inflate);
        return anonymousClass19;
    }

    public static Dialog getUnbindDialog(Activity activity, OnClickListener onClickListener) {
        Dialog anonymousClass4 = new AnonymousClass4(activity, R.style.noBackgroundDialog);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_unbind, null);
        Button button = (Button) inflate.findViewById(R.id.right_btn);
        ((Button) inflate.findViewById(R.id.left_btn)).setOnClickListener(new AnonymousClass5(activity, anonymousClass4));
        button.setOnClickListener(onClickListener);
        anonymousClass4.requestWindowFeature(1);
        anonymousClass4.setCanceledOnTouchOutside(false);
        anonymousClass4.setCancelable(false);
        anonymousClass4.setContentView(inflate);
        return anonymousClass4;
    }

    public static Dialog getWheelSpeedDialog(Activity activity, OnTimingChosenListener onTimingChosenListener, String str, int i) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.widthPixels;
        String[] strArr = new String[]{"  1 ", "  2 ", "  3 ", "  4 "};
        int[] iArr = new int[4];
        iArr[1] = 1;
        iArr[2] = 2;
        iArr[3] = 3;
        Dialog anonymousClass16 = new AnonymousClass16(activity, R.style.noBackgroundDialog);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_choose_timing_conditioner, null);
        ((TextView) inflate.findViewById(R.id.wifiSSID_tv)).setText(str);
        Button button = (Button) inflate.findViewById(R.id.confi_btn);
        Button button2 = (Button) inflate.findViewById(R.id.cancel_btn);
        WheelView wheelView = (WheelView) inflate.findViewById(R.id.wheel_view_timing);
        if (i2 <= 540) {
            wheelView.setTEXT_SIZE(30);
            wheelView.setADDITIONAL_ITEM_HEIGHT(60);
            wheelView.setADDITIONAL_ITEMS_SPACE(5);
        }
        wheelView.setAdapter(new ArrayWheelAdapter(strArr));
        wheelView.setCyclic(true);
        wheelView.setLabel("档");
        wheelView.setCurrentItem(i);
        button2.setOnClickListener(new AnonymousClass17(activity, anonymousClass16));
        button.setOnClickListener(new AnonymousClass18(wheelView, onTimingChosenListener, iArr, activity, anonymousClass16));
        anonymousClass16.requestWindowFeature(1);
        anonymousClass16.setCanceledOnTouchOutside(false);
        anonymousClass16.setCancelable(false);
        anonymousClass16.setContentView(inflate);
        return anonymousClass16;
    }

    public static Dialog getWheelTimingDialog(Activity activity, OnTimingChosenListener onTimingChosenListener, String str, int i) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.widthPixels;
        String[] strArr = new String[]{"  1 ", "  2 ", "  3 ", "  4 ", "  5 ", "  6 ", "  7 ", "  8 ", "  9 ", "  10 ", "  11 ", "  12 ", "  13 ", "  14 ", "  15 ", "  16 ", "  17 ", "  18 ", "  19 ", "  20 ", "  21 ", "  22 ", "  23 ", "  24 ", "关闭"};
        int[] iArr = new int[25];
        iArr[0] = 1;
        iArr[1] = 2;
        iArr[2] = 3;
        iArr[3] = 4;
        iArr[4] = 5;
        iArr[5] = 6;
        iArr[6] = 7;
        iArr[7] = 8;
        iArr[8] = 9;
        iArr[9] = 10;
        iArr[10] = 11;
        iArr[11] = 12;
        iArr[12] = 13;
        iArr[13] = 14;
        iArr[14] = 15;
        iArr[15] = 16;
        iArr[16] = 17;
        iArr[17] = 18;
        iArr[18] = 19;
        iArr[19] = 20;
        iArr[20] = 21;
        iArr[21] = 22;
        iArr[22] = 23;
        iArr[23] = 24;
        Dialog anonymousClass13 = new AnonymousClass13(activity, R.style.noBackgroundDialog);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.dialog_choose_timing_conditioner, null);
        ((TextView) inflate.findViewById(R.id.wifiSSID_tv)).setText(str);
        Button button = (Button) inflate.findViewById(R.id.confi_btn);
        Button button2 = (Button) inflate.findViewById(R.id.cancel_btn);
        WheelView wheelView = (WheelView) inflate.findViewById(R.id.wheel_view_timing);
        if (i2 <= 540) {
            wheelView.setTEXT_SIZE(30);
            wheelView.setADDITIONAL_ITEM_HEIGHT(60);
            wheelView.setADDITIONAL_ITEMS_SPACE(5);
        }
        wheelView.setAdapter(new ArrayWheelAdapter(strArr));
        wheelView.setCyclic(true);
        wheelView.setLabel("小时");
        wheelView.setCurrentItem(i);
        button2.setOnClickListener(new AnonymousClass14(activity, anonymousClass13));
        button.setOnClickListener(new AnonymousClass15(wheelView, onTimingChosenListener, iArr, activity, anonymousClass13));
        anonymousClass13.requestWindowFeature(1);
        anonymousClass13.setCanceledOnTouchOutside(false);
        anonymousClass13.setCancelable(false);
        anonymousClass13.setContentView(inflate);
        return anonymousClass13;
    }

    public static void showDialog(Context context, Dialog dialog) {
        if (dialog != null && !dialog.isShowing() && context != null && !((Activity) context).isFinishing()) {
            dialog.show();
        }
    }
}
