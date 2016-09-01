package com.gizwits.framework.utils;

import android.content.Context;

public class PxUtil {
    public static int dip2px(Context context, float f) {
        return (int) (0.5f + (context.getResources().getDisplayMetrics().density * f));
    }

    public static int px2dip(Context context, float f) {
        return (int) (0.5f + (f / context.getResources().getDisplayMetrics().density));
    }
}
