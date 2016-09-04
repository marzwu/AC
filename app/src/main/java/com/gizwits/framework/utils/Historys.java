package com.gizwits.framework.utils;

import android.app.Activity;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;

public class Historys {
    private static List<Activity> activityList = null;
    private static Historys instance = null;

    public Historys() {
        activityList = new ArrayList();
    }

    public static void exit() {
        if (activityList == null) {
            return;
        }
        int i = activityList.size();
        for (int j = 0; ; j++) {
            if (j >= i) {
                activityList.clear();
                Process.killProcess(Process.myPid());
                System.exit(0);
                return;
            }
            finish((Activity) activityList.get(j));
        }
    }

    private static void finish(Activity paramActivity) {
        if (paramActivity == null) {
            return;
        }
        try {
            paramActivity.finish();
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    private static Historys getInstance() {
        if (instance == null) {
        }
        try {
            if (instance == null) {
                instance = new Historys();
            }
            return instance;
        } finally {
        }
    }

    public static void put(final Activity activity) {
        if (Historys.activityList == null) {
            getInstance();
        }
        SystemResource.getExecutorService().execute(new Runnable() {

            @Override
            public void run() {
                Historys.activityList.add(activity);
                final ArrayList<Activity> list = new ArrayList<Activity>();
                for (int size = Historys.activityList.size(), i = 0; i < size; ++i) {
                    final Activity activity = Historys.activityList.get(i);
                    if (activity != null && activity.isFinishing()) {
                        list.add(activity);
                    }
                }
                Historys.activityList.removeAll(list);
            }
        });
    }

    public static void recycle() {
        if (activityList != null) {
            activityList.clear();
            activityList = null;
        }
        instance = null;
    }
}