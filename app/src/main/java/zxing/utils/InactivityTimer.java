package zxing.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.util.Log;

public class InactivityTimer {
    private static final long INACTIVITY_DELAY_MS = 300000;
    private static final String TAG = InactivityTimer.class.getSimpleName();
    private Activity activity;
    private AsyncTask<Object, Object, Object> inactivityTask;
    private BroadcastReceiver powerStatusReceiver = new PowerStatusReceiver();
    private boolean registered = false;

    private class InactivityAsyncTask extends AsyncTask<Object, Object, Object> {
        private InactivityAsyncTask() {
        }

        protected Object doInBackground(Object... objArr) {
            try {
                Thread.sleep(InactivityTimer.INACTIVITY_DELAY_MS);
                Log.i(InactivityTimer.TAG, "Finishing activity due to inactivity");
                InactivityTimer.this.activity.finish();
            } catch (InterruptedException e) {
            }
            return null;
        }
    }

    private class PowerStatusReceiver extends BroadcastReceiver {
        private PowerStatusReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                if ((intent.getIntExtra("plugged", -1) <= 0 ? 1 : null) != null) {
                    InactivityTimer.this.onActivity();
                } else {
                    InactivityTimer.this.cancel();
                }
            }
        }
    }

    public InactivityTimer(Activity activity) {
        this.activity = activity;
        onActivity();
    }

    private void cancel() {
        synchronized (this) {
            AsyncTask asyncTask = this.inactivityTask;
            if (asyncTask != null) {
                asyncTask.cancel(true);
                this.inactivityTask = null;
            }
        }
    }

    @SuppressLint({"NewApi"})
    public void onActivity() {
        synchronized (this) {
            cancel();
            this.inactivityTask = new InactivityAsyncTask();
            if (VERSION.SDK_INT >= 11) {
                this.inactivityTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
            } else {
                this.inactivityTask.execute(new Object[0]);
            }
        }
    }

    public void onPause() {
        synchronized (this) {
            cancel();
            if (this.registered) {
                this.activity.unregisterReceiver(this.powerStatusReceiver);
                this.registered = false;
            } else {
                Log.w(TAG, "PowerStatusReceiver was never registered?");
            }
        }
    }

    public void onResume() {
        synchronized (this) {
            if (this.registered) {
                Log.w(TAG, "PowerStatusReceiver was already registered?");
            } else {
                this.activity.registerReceiver(this.powerStatusReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                this.registered = true;
            }
            onActivity();
        }
    }

    public void shutdown() {
        cancel();
    }
}