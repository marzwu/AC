package com.gizwits.framework.utils;

import android.app.Activity;
import android.os.Process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Historys {
    private static List<Activity> activityList = null;
    private static Historys instance = null;

    class AnonymousClass1 implements Runnable {
        private final /* synthetic */ Activity val$activity;

        AnonymousClass1(Activity activity) {
            this.val$activity = activity;
        }

        public void run() {
            Historys.activityList.add(this.val$activity);
            Collection arrayList = new ArrayList();
            int size = Historys.activityList.size();
            for (int i = 0; i < size; i++) {
                Activity activity = (Activity) Historys.activityList.get(i);
                if (activity != null && activity.isFinishing()) {
                    arrayList.add(activity);
                }
            }
            Historys.activityList.removeAll(arrayList);
        }
    }

    public Historys() {
        activityList = new ArrayList();
    }

    public static void exit() {
        if (activityList != null) {
            int size = activityList.size();
            for (int i = 0; i < size; i++) {
                finish((Activity) activityList.get(i));
            }
            activityList.clear();
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }

    private static void finish(Activity activity) {
        if (activity != null) {
            try {
                activity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Historys getInstance() {
        if (instance == null) {
            synchronized (Historys.class) {
                try {
                    if (instance == null) {
                        instance = new Historys();
                    }
                } catch (Throwable th) {
                    while (true) {
                        Class cls = Historys.class;
                    }
                }
            }
        }
        return instance;
    }

    public static void put(Activity activity) {
        if (activityList == null) {
            getInstance();
        }
        SystemResource.getExecutorService().execute(new AnonymousClass1(activity));
    }

    public static void recycle() {
        if (activityList != null) {
            activityList.clear();
            activityList = null;
        }
        instance = null;
    }
}
