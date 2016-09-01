package com.xpg.ui.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;

public class DialogUtils {
    public static void dismiss(Activity activity, Dialog dialog) {
        if (dialog != null && dialog.isShowing() && activity != null && !activity.isFinishing()) {
            dialog.dismiss();
        }
    }

    public static void dismiss(Activity activity, ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing() && activity != null && !activity.isFinishing()) {
            progressDialog.dismiss();
        }
    }

    public static ProgressDialog showTip(Activity activity, String str, String str2) {
        ProgressDialog show = ProgressDialog.show(activity, str, str2);
        show.setCancelable(true);
        return show;
    }
}
