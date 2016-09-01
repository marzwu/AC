package com.xpg.common.system;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import com.xpg.common.useful.StringUtils;

public class IntentUtils {
    private static IntentUtils instance = null;

    public static IntentUtils getInstance() {
        if (instance == null) {
            synchronized (IntentUtils.class) {
                try {
                    if (instance == null) {
                        instance = new IntentUtils();
                    }
                } catch (Throwable th) {
                    while (true) {
                        Class cls = IntentUtils.class;
                    }
                }
            }
        }
        return instance;
    }

    public static void recycle() {
        instance = null;
    }

    public boolean existPackage(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void gotoSendMMS(Context context, String str) {
        Intent intent = new Intent("android.intent.action.SENDTO", Uri.fromParts("mmsto", str, null));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public void gotoSystemSendMessage(Context context, String str, String str2) {
        Intent intent = StringUtils.isEmpty(str) ? new Intent("android.intent.action.SENDTO", Uri.parse("smsto:")) : new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + str));
        intent.setFlags(268435456);
        if (!StringUtils.isEmpty(str2)) {
            intent.putExtra("sms_body", str2);
        }
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public void share(Context context, String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", str);
        context.startActivity(Intent.createChooser(intent, "分享"));
    }

    public void startActivity(Context context, Class cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    public void startBrowser(String str, Context context) {
        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
    }

    public void startPackage(Context context, String str) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntentForPackage != null) {
            launchIntentForPackage.addFlags(131072);
            context.startActivity(launchIntentForPackage);
        }
    }

    public void startSystemBrowser(Context context, String str) {
        try {
            Uri parse = Uri.parse(str);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(parse);
            intent.setFlags(268435456);
            intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            context.startActivity(intent);
        } catch (Exception e) {
            startBrowser(str, context);
        }
    }
}
