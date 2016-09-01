package com.cgt.control;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build.VERSION;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class ActivityManager {
    @TargetApi(19)
    public void applyKitKatTranslucency(Activity activity, int i) {
        if (VERSION.SDK_INT >= 19) {
            Window window = activity.getWindow();
            LayoutParams attributes = window.getAttributes();
            attributes.flags = 67108864 | attributes.flags;
            window.setAttributes(attributes);
            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(activity);
            systemBarTintManager.setStatusBarTintEnabled(true);
            systemBarTintManager.setNavigationBarTintEnabled(true);
            systemBarTintManager.setTintColor(i);
        }
    }
}
