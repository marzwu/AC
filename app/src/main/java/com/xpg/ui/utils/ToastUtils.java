package com.xpg.ui.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static Toast toast = null;

    public static void showLong(Context context, int i) {
        if (toast == null) {
            toast = Toast.makeText(context, i, 1);
        } else {
            toast.setDuration(1);
            toast.setText(i);
        }
        toast.show();
    }

    public static void showLong(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, 1);
        } else {
            toast.setDuration(1);
            toast.setText(str);
        }
        toast.show();
    }

    public static void showShort(Context context, int i) {
        if (toast == null) {
            toast = Toast.makeText(context, i, 0);
        } else {
            toast.setDuration(0);
            toast.setText(i);
        }
        toast.show();
    }

    public static void showShort(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, 0);
        } else {
            toast.setDuration(0);
            toast.setText(str);
        }
        toast.show();
    }
}
