package zxing.camera.open;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.Log;

public class OpenCameraInterface {
    private static final String TAG = OpenCameraInterface.class.getName();

    public static Camera open() {
        return open(-1);
    }

    public static Camera open(int i) {
        int numberOfCameras = Camera.getNumberOfCameras();
        if (numberOfCameras == 0) {
            Log.w(TAG, "No cameras!");
            return null;
        }
        int i2 = i >= 0 ? 1 : 0;
        if (i2 == 0) {
            i = 0;
            while (i < numberOfCameras) {
                CameraInfo cameraInfo = new CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == 0) {
                    break;
                }
                i++;
            }
        }
        if (i < numberOfCameras) {
            Log.i(TAG, "Opening camera #" + i);
            return Camera.open(i);
        } else if (i2 != 0) {
            Log.w(TAG, "Requested camera does not exist: " + i);
            return null;
        } else {
            Log.i(TAG, "No camera facing back; returning camera #0");
            return Camera.open(0);
        }
    }
}
