package com.gizwits.framework.utils;

import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SystemResource {
    private static String APP_NAME = null;
    private static String DATA_ROOT_DIRECTORY = null;
    private static final String DEFAULT_APP_DATA = "AOSHeater";
    private static final int DEFAULT_THREADPOOL_SIZE = 2;
    private static String IMAGE_RELATIVE_DIRECTORY = "/image";
    private static SystemResource instance = null;
    private Context applicationContext = null;
    private ExecutorService executorService = null;
    private Options opts = null;

    public static Context getApplicationContext() {
        return getInstance().applicationContext;
    }

    public static ExecutorService getExecutorService() {
        if (getInstance().executorService == null) {
            synchronized (SystemResource.class) {
                try {
                    if (instance.executorService == null) {
                        getInstance().initThreadPool(2);
                    }
                } catch (Throwable th) {
                    while (true) {
                        Class cls = SystemResource.class;
                    }
                }
            }
        }
        return instance.executorService;
    }

    public static String getImageDir() {
        initDir(APP_NAME);
        return DATA_ROOT_DIRECTORY + IMAGE_RELATIVE_DIRECTORY;
    }

    private static SystemResource getInstance() {
        if (instance == null) {
            synchronized (SystemResource.class) {
                try {
                    if (instance == null) {
                        instance = new SystemResource();
                    }
                } catch (Throwable th) {
                    while (true) {
                        Class cls = SystemResource.class;
                    }
                }
            }
        }
        return instance;
    }

    public static void init(String str, int i) {
        if (StringUtils.isEmpty(str)) {
            APP_NAME = DEFAULT_APP_DATA;
        } else {
            APP_NAME = str;
        }
        DATA_ROOT_DIRECTORY = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/").append(APP_NAME).toString();
        initDir(str);
        getInstance().initThreadPool(i);
    }

    public static boolean initDir(String str) {
        if (StringUtils.isEmpty(str)) {
            APP_NAME = DEFAULT_APP_DATA;
        } else {
            APP_NAME = str;
        }
        DATA_ROOT_DIRECTORY = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/").append(APP_NAME).toString();
        File file = new File(DATA_ROOT_DIRECTORY + IMAGE_RELATIVE_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
        }
        return true;
    }

    private void initThreadPool(int i) {
        if (i == 0) {
            i = 2;
        }
        getInstance().executorService = Executors.newFixedThreadPool(i);
    }

    public static void recycle() {
        if (instance != null) {
            instance.executorService = null;
            instance.applicationContext = null;
            instance.opts = null;
        }
        instance = null;
    }

    public static void setApplicationContext(Context context) {
        getInstance().applicationContext = context;
    }

    public static void setBitmapFactoryOptions(Options options) {
        getInstance().opts = options;
    }
}
