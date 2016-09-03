package zxing.utils;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.google.zxing.Result;
import com.uh.all.airpurifier.R;

import zxing.CaptureActivity;
import zxing.camera.CameraManager;
import zxing.decoding.DecodeThread;

public class CaptureActivityHandler extends Handler {
    private final CaptureActivity activity;
    private final CameraManager cameraManager;
    private final DecodeThread decodeThread;
    private State state = State.SUCCESS;

    private enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    public CaptureActivityHandler(CaptureActivity captureActivity, CameraManager cameraManager, int i) {
        this.activity = captureActivity;
        this.decodeThread = new DecodeThread(captureActivity, i);
        this.decodeThread.start();
        this.cameraManager = cameraManager;
        cameraManager.startPreview();
        restartPreviewAndDecode();
    }

    private void restartPreviewAndDecode() {
        if (this.state == State.SUCCESS) {
            this.state = State.PREVIEW;
            this.cameraManager.requestPreviewFrame(this.decodeThread.getHandler(), R.id.decode);
        }
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case R.id.decode_failed /*2131165186*/:
                this.state = State.PREVIEW;
                this.cameraManager.requestPreviewFrame(this.decodeThread.getHandler(), R.id.decode);
                return;
            case R.id.decode_succeeded /*2131165187*/:
                this.state = State.SUCCESS;
                this.activity.handleDecode((Result) message.obj, message.getData());
                return;
            case R.id.restart_preview /*2131165192*/:
                restartPreviewAndDecode();
                return;
            case R.id.return_scan_result /*2131165193*/:
                this.activity.setResult(-1, (Intent) message.obj);
                this.activity.finish();
                return;
            default:
                return;
        }
    }

    public void quitSynchronously() {
        this.state = State.DONE;
        this.cameraManager.stopPreview();
        Message.obtain(this.decodeThread.getHandler(), R.id.quit).sendToTarget();
        try {
            this.decodeThread.join(500);
        } catch (InterruptedException e) {
        }
        removeMessages(R.id.decode_succeeded);
        removeMessages(R.id.decode_failed);
    }
}
