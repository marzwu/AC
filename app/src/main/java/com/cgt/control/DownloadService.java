package com.cgt.control;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import com.gizwits.framework.XpgApplication;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.framework.activity.device.DeviceListActivity.ICallbackResult;
import com.uh.all.airpurifier.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadService extends Service {
    private static final int NOTIFY_ID = 0;
    private static final String saveFileName = "/sdcard/updateApkDemo/Hoko_yumvon.apk";
    private static final String savePath = "/sdcard/updateApkDemo/";
    private XpgApplication app;
    private DownloadBinder binder;
    private ICallbackResult callback;
    private boolean canceled;
    private Thread downLoadThread;
    private int lastRate = 0;
    private Context mContext = this;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 0:
                    DownloadService.this.app.setDownload(false);
                    DownloadService.this.mNotificationManager.cancel(0);
                    DownloadService.this.installApk();
                    return;
                case 1:
                    int i = message.arg1;
                    DownloadService.this.app.setDownload(true);
                    if (i < 100) {
                        RemoteViews remoteViews = DownloadService.this.mNotification.contentView;
                        remoteViews.setTextViewText(R.id.tv_progress, new StringBuilder(String.valueOf(i)).append("%").toString());
                        remoteViews.setProgressBar(R.id.progressbar, 100, i, false);
                    } else {
                        System.out.println("下载完毕!!!!!!!!!!!");
                        DownloadService.this.mNotification.flags = 16;
                        DownloadService.this.mNotification.contentView = null;
                        Intent intent = new Intent(DownloadService.this.mContext, DeviceListActivity.class);
                        intent.putExtra("completed", "yes");
                        DownloadService.this.mNotification.setLatestEventInfo(DownloadService.this.mContext, "下载完成", "文件已下载完毕", PendingIntent.getActivity(DownloadService.this.mContext, 0, intent, 134217728));
                        DownloadService.this.serviceIsDestroy = true;
                        DownloadService.this.stopSelf();
                    }
                    DownloadService.this.mNotificationManager.notify(0, DownloadService.this.mNotification);
                    return;
                case 2:
                    DownloadService.this.app.setDownload(false);
                    DownloadService.this.mNotificationManager.cancel(0);
                    return;
                default:
                    return;
            }
        }
    };
    Notification mNotification;
    private NotificationManager mNotificationManager;
    private Runnable mdownApkRunnable = new Runnable() {
        public void run() {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(Constant.APK_URL).openConnection();
                httpURLConnection.connect();
                int contentLength = httpURLConnection.getContentLength();
                InputStream inputStream = httpURLConnection.getInputStream();
                File file = new File(DownloadService.savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(new File(DownloadService.saveFileName));
                byte[] bArr = new byte[1024];
                int i = 0;
                do {
                    int read = inputStream.read(bArr);
                    i += read;
                    DownloadService.this.progress = (int) (100.0f * (((float) i) / ((float) contentLength)));
                    Message obtainMessage = DownloadService.this.mHandler.obtainMessage();
                    obtainMessage.what = 1;
                    obtainMessage.arg1 = DownloadService.this.progress;
                    if (DownloadService.this.progress >= DownloadService.this.lastRate + 1) {
                        DownloadService.this.mHandler.sendMessage(obtainMessage);
                        DownloadService.this.lastRate = DownloadService.this.progress;
                        if (DownloadService.this.callback != null) {
                            DownloadService.this.callback.OnBackResult(Integer.valueOf(DownloadService.this.progress));
                        }
                    }
                    if (read <= 0) {
                        DownloadService.this.mHandler.sendEmptyMessage(0);
                        DownloadService.this.canceled = true;
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                } while (!DownloadService.this.canceled);
                fileOutputStream.close();
                inputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    };
    private int progress;
    private boolean serviceIsDestroy = false;

    public class DownloadBinder extends Binder {
        public void addCallback(ICallbackResult iCallbackResult) {
            DownloadService.this.callback = iCallbackResult;
        }

        public void cancel() {
            DownloadService.this.canceled = true;
        }

        public void cancelNotification() {
            DownloadService.this.mHandler.sendEmptyMessage(2);
        }

        public int getProgress() {
            return DownloadService.this.progress;
        }

        public boolean isCanceled() {
            return DownloadService.this.canceled;
        }

        public boolean serviceIsDestroy() {
            return DownloadService.this.serviceIsDestroy;
        }

        public void start() {
            if (DownloadService.this.downLoadThread == null || !DownloadService.this.downLoadThread.isAlive()) {
                DownloadService.this.progress = 0;
                DownloadService.this.setUpNotification();
                new Thread() {
                    public void run() {
                        DownloadService.this.startDownload();
                    }
                }.start();
            }
        }
    }

    private void downloadApk() {
        this.downLoadThread = new Thread(this.mdownApkRunnable);
        this.downLoadThread.start();
    }

    private void installApk() {
        File file = new File(saveFileName);
        if (file.exists()) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setFlags(268435456);
            intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
            this.mContext.startActivity(intent);
            this.callback.OnBackResult("finish");
        }
    }

    private void setUpNotification() {
        this.mNotification = new Notification(R.drawable.icon, "开始下载", System.currentTimeMillis());
        this.mNotification.flags = 2;
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.download_notification_layout);
        remoteViews.setTextViewText(R.id.name, "2131230720 正在下载...");
        remoteViews.setImageViewResource(R.id.image, R.drawable.d_icon);
        this.mNotification.contentView = remoteViews;
        this.mNotification.contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, DeviceListActivity.class), 134217728);
        this.mNotificationManager.notify(0, this.mNotification);
    }

    private void startDownload() {
        this.canceled = false;
        downloadApk();
    }

    public IBinder onBind(Intent intent) {
        System.out.println("是否执行了 onBind");
        return this.binder;
    }

    public void onCreate() {
        super.onCreate();
        this.binder = new DownloadBinder();
        this.mNotificationManager = (NotificationManager) getSystemService("notification");
        this.app = (XpgApplication) getApplication();
        System.out.println("onCreate----");
    }

    public void onDestroy() {
        super.onDestroy();
        System.out.println("downloadservice ondestroy");
        this.app.setDownload(false);
    }

    public void onRebind(Intent intent) {
        super.onRebind(intent);
        System.out.println("downloadservice onRebind");
    }

    public boolean onUnbind(Intent intent) {
        System.out.println("downloadservice onUnbind");
        return super.onUnbind(intent);
    }
}
