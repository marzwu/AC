package zxing.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.util.Log;

import com.gizwits.framework.config.JsonKeys;

import java.util.ArrayList;
import java.util.Collection;

public class AutoFocusManager implements AutoFocusCallback {
    private static final long AUTO_FOCUS_INTERVAL_MS = 2000;
    private static final Collection<String> FOCUS_MODES_CALLING_AF = new ArrayList(2);
    private static final String TAG = AutoFocusManager.class.getSimpleName();
    private final Camera camera;
    private boolean focusing;
    private AsyncTask<?, ?, ?> outstandingTask;
    private boolean stopped;
    private final boolean useAutoFocus;

    private final class AutoFocusTask extends AsyncTask<Object, Object, Object> {
        private AutoFocusTask() {
        }

        protected Object doInBackground(Object... objArr) {
            try {
                Thread.sleep(AutoFocusManager.AUTO_FOCUS_INTERVAL_MS);
            } catch (InterruptedException e) {
            }
            AutoFocusManager.this.start();
            return null;
        }
    }

    static {
        FOCUS_MODES_CALLING_AF.add(JsonKeys.AUTO);
        FOCUS_MODES_CALLING_AF.add("macro");
    }

    public AutoFocusManager(Context context, Camera camera) {
        this.camera = camera;
        String focusMode = camera.getParameters().getFocusMode();
        this.useAutoFocus = FOCUS_MODES_CALLING_AF.contains(focusMode);
        Log.i(TAG, "Current focus mode '" + focusMode + "'; use auto focus? " + this.useAutoFocus);
        start();
    }

    @SuppressLint({"NewApi"})
    private void autoFocusAgainLater() {
        synchronized (this) {
            if (!this.stopped && this.outstandingTask == null) {
                AsyncTask autoFocusTask = new AutoFocusTask();
                try {
                    if (VERSION.SDK_INT >= 11) {
                        autoFocusTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
                    } else {
                        autoFocusTask.execute(new Object[0]);
                    }
                    this.outstandingTask = autoFocusTask;
                } catch (Throwable e) {
                    Log.w(TAG, "Could not request auto focus", e);
                }
            }
        }
    }

    private void cancelOutstandingTask() {
        synchronized (this) {
            if (this.outstandingTask != null) {
                if (this.outstandingTask.getStatus() != Status.FINISHED) {
                    this.outstandingTask.cancel(true);
                }
                this.outstandingTask = null;
            }
        }
    }

    public void onAutoFocus(boolean z, Camera camera) {
        synchronized (this) {
            this.focusing = false;
            autoFocusAgainLater();
        }
    }

    public void start() {
        synchronized (this) {
            if (this.useAutoFocus) {
                this.outstandingTask = null;
                if (!(this.stopped || this.focusing)) {
                    try {
                        this.camera.autoFocus(this);
                        this.focusing = true;
                    } catch (Throwable e) {
                        Log.w(TAG, "Unexpected exception while focusing", e);
                        autoFocusAgainLater();
                    }
                }
            }
        }
    }

    public void stop() {
        synchronized (this) {
            this.stopped = true;
            if (this.useAutoFocus) {
                cancelOutstandingTask();
                try {
                    this.camera.cancelAutoFocus();
                } catch (Throwable e) {
                    Log.w(TAG, "Unexpected exception while cancelling focusing", e);
                }
            }
        }
    }
}
