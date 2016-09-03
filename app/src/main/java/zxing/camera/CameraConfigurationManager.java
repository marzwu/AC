package zxing.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class CameraConfigurationManager {
    private static final double MAX_ASPECT_DISTORTION = 0.15d;
    private static final int MIN_PREVIEW_PIXELS = 153600;
    private static final String TAG = "CameraConfiguration";
    private Point cameraResolution;
    private final Context context;
    private Point screenResolution;

    public CameraConfigurationManager(Context context) {
        this.context = context;
    }

    private Point findBestPreviewSizeValue(Parameters parameters, Point point) {
        Collection supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        if (supportedPreviewSizes == null) {
            Log.w(TAG, "Device returned no supported preview sizes; using default");
            Size previewSize = parameters.getPreviewSize();
            return new Point(previewSize.width, previewSize.height);
        }
        Size size;
        List<Size> arrayList = new ArrayList(supportedPreviewSizes);
        Collections.sort(arrayList, new Comparator<Size>() {
            public int compare(Size size, Size size2) {
                int i = size.height * size.width;
                int i2 = size2.height * size2.width;
                return i2 < i ? -1 : i2 > i ? 1 : 0;
            }
        });
        if (Log.isLoggable(TAG, 4)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Size size2 : arrayList) {
                stringBuilder.append(size2.width).append('x').append(size2.height).append(' ');
            }
            Log.i(TAG, "Supported preview sizes: " + stringBuilder);
        }
        double d = ((double) point.x) / ((double) point.y);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            size2 = (Size) it.next();
            int i = size2.width;
            int i2 = size2.height;
            if (i * i2 < MIN_PREVIEW_PIXELS) {
                it.remove();
            } else {
                Object obj = i < i2 ? 1 : null;
                int i3 = obj != null ? i2 : i;
                int i4 = obj != null ? i : i2;
                if (Math.abs((((double) i3) / ((double) i4)) - d) > MAX_ASPECT_DISTORTION) {
                    it.remove();
                } else if (i3 == point.x && i4 == point.y) {
                    Point point2 = new Point(i, i2);
                    Log.i(TAG, "Found preview size exactly matching screen size: " + point2);
                    return point2;
                }
            }
        }
        if (arrayList.isEmpty()) {
            previewSize = parameters.getPreviewSize();
            point2 = new Point(previewSize.width, previewSize.height);
            Log.i(TAG, "No suitable preview sizes, using default: " + point2);
            return point2;
        }
        size2 = (Size) arrayList.get(0);
        Point point3 = new Point(size2.width, size2.height);
        Log.i(TAG, "Using largest suitable preview size: " + point3);
        return point3;
    }

    @SuppressLint({"NewApi"})
    private Point getDisplaySize(Display display) {
        Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError e) {
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point;
    }

    public Point getCameraResolution() {
        return this.cameraResolution;
    }

    public Point getScreenResolution() {
        return this.screenResolution;
    }

    public void initFromCameraParameters(Camera camera) {
        Parameters parameters = camera.getParameters();
        Display defaultDisplay = ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        this.screenResolution = getDisplaySize(defaultDisplay);
        Log.i(TAG, "Screen resolution: " + this.screenResolution);
        Point point2 = new Point();
        point2.x = this.screenResolution.x;
        point2.y = this.screenResolution.y;
        if (this.screenResolution.x < this.screenResolution.y) {
            point2.x = this.screenResolution.y;
            point2.y = this.screenResolution.x;
        }
        this.cameraResolution = findBestPreviewSizeValue(parameters, point2);
        Log.i(TAG, "Camera resolution x: " + this.cameraResolution.x);
        Log.i(TAG, "Camera resolution y: " + this.cameraResolution.y);
    }

    public void setDesiredCameraParameters(Camera camera, boolean z) {
        Parameters parameters = camera.getParameters();
        if (parameters == null) {
            Log.w(TAG, "Device error: no camera parameters are available. Proceeding without configuration.");
            return;
        }
        Log.i(TAG, "Initial camera parameters: " + parameters.flatten());
        if (z) {
            Log.w(TAG, "In camera config safe mode -- most settings will not be honored");
        }
        parameters.setPreviewSize(this.cameraResolution.x, this.cameraResolution.y);
        camera.setParameters(parameters);
        Size previewSize = camera.getParameters().getPreviewSize();
        if (!(previewSize == null || (this.cameraResolution.x == previewSize.width && this.cameraResolution.y == previewSize.height))) {
            Log.w(TAG, "Camera said it supported preview size " + this.cameraResolution.x + 'x' + this.cameraResolution.y + ", but after setting it, preview size is " + previewSize.width + 'x' + previewSize.height);
            this.cameraResolution.x = previewSize.width;
            this.cameraResolution.y = previewSize.height;
        }
        camera.setDisplayOrientation(90);
    }
}
