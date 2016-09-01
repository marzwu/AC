package zxing.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import java.io.IOException;
import zxing.camera.open.OpenCameraInterface;

public class CameraManager {
    private static final String TAG = CameraManager.class.getSimpleName();
    private AutoFocusManager autoFocusManager;
    private Camera camera;
    private final CameraConfigurationManager configManager;
    private final Context context;
    private boolean initialized;
    private final PreviewCallback previewCallback;
    private boolean previewing;
    private int requestedCameraId = -1;

    public CameraManager(Context context) {
        this.context = context;
        this.configManager = new CameraConfigurationManager(context);
        this.previewCallback = new PreviewCallback(this.configManager);
    }

    public void closeDriver() {
        synchronized (this) {
            if (this.camera != null) {
                this.camera.release();
                this.camera = null;
            }
        }
    }

    public Point getCameraResolution() {
        return this.configManager.getCameraResolution();
    }

    public Size getPreviewSize() {
        return this.camera != null ? this.camera.getParameters().getPreviewSize() : null;
    }

    public boolean isOpen() {
        boolean z;
        synchronized (this) {
            z = this.camera != null;
        }
        return z;
    }

    public void openDriver(SurfaceHolder surfaceHolder) throws IOException {
        synchronized (this) {
            Camera camera = this.camera;
            if (camera == null) {
                camera = this.requestedCameraId >= 0 ? OpenCameraInterface.open(this.requestedCameraId) : OpenCameraInterface.open();
                if (camera == null) {
                    throw new IOException();
                }
                this.camera = camera;
            }
            Camera camera2 = camera;
            camera2.setPreviewDisplay(surfaceHolder);
            if (!this.initialized) {
                this.initialized = true;
                this.configManager.initFromCameraParameters(camera2);
            }
            Parameters parameters = camera2.getParameters();
            String flatten = parameters == null ? null : parameters.flatten();
            try {
                this.configManager.setDesiredCameraParameters(camera2, false);
            } catch (RuntimeException e) {
                Log.w(TAG, "Camera rejected parameters. Setting only minimal safe-mode parameters");
                Log.i(TAG, "Resetting to saved camera params: " + flatten);
                if (flatten != null) {
                    Parameters parameters2 = camera2.getParameters();
                    parameters2.unflatten(flatten);
                    try {
                        camera2.setParameters(parameters2);
                        this.configManager.setDesiredCameraParameters(camera2, true);
                    } catch (RuntimeException e2) {
                        Log.w(TAG, "Camera rejected even safe-mode parameters! No configuration");
                    }
                }
            }
        }
    }

    public void requestPreviewFrame(Handler handler, int i) {
        synchronized (this) {
            Camera camera = this.camera;
            if (camera != null && this.previewing) {
                this.previewCallback.setHandler(handler, i);
                camera.setOneShotPreviewCallback(this.previewCallback);
            }
        }
    }

    public void setManualCameraId(int i) {
        synchronized (this) {
            this.requestedCameraId = i;
        }
    }

    public void startPreview() {
        synchronized (this) {
            Camera camera = this.camera;
            if (!(camera == null || this.previewing)) {
                camera.startPreview();
                this.previewing = true;
                this.autoFocusManager = new AutoFocusManager(this.context, this.camera);
            }
        }
    }

    public void stopPreview() {
        synchronized (this) {
            if (this.autoFocusManager != null) {
                this.autoFocusManager.stop();
                this.autoFocusManager = null;
            }
            if (this.camera != null && this.previewing) {
                this.camera.stopPreview();
                this.previewCallback.setHandler(null, 0);
                this.previewing = false;
            }
        }
    }
}
