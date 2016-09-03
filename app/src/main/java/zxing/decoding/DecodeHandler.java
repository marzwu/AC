package zxing.decoding;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.common.HybridBinarizer;
import com.uh.all.airpurifier.R;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import zxing.CaptureActivity;

public class DecodeHandler extends Handler {
    private final CaptureActivity activity;
    private final MultiFormatReader multiFormatReader = new MultiFormatReader();
    private boolean running = true;

    public DecodeHandler(CaptureActivity captureActivity, Map<DecodeHintType, Object> map) {
        this.multiFormatReader.setHints(map);
        this.activity = captureActivity;
    }

    private static void bundleThumbnail(PlanarYUVLuminanceSource planarYUVLuminanceSource, Bundle bundle) {
        int[] renderThumbnail = planarYUVLuminanceSource.renderThumbnail();
        int thumbnailWidth = planarYUVLuminanceSource.getThumbnailWidth();
        Bitmap createBitmap = Bitmap.createBitmap(renderThumbnail, 0, thumbnailWidth, thumbnailWidth, planarYUVLuminanceSource.getThumbnailHeight(), Config.ARGB_8888);
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        createBitmap.compress(CompressFormat.JPEG, 50, byteArrayOutputStream);
        bundle.putByteArray(DecodeThread.BARCODE_BITMAP, byteArrayOutputStream.toByteArray());
    }

    private void decode(byte[] bArr, int i, int i2) {
        int i3;
        Object decodeWithState;
        Handler handler;
        Size previewSize = this.activity.getCameraManager().getPreviewSize();
        byte[] bArr2 = new byte[bArr.length];
        for (i3 = 0; i3 < previewSize.height; i3++) {
            for (int i4 = 0; i4 < previewSize.width; i4++) {
                bArr2[(((previewSize.height * i4) + previewSize.height) - i3) - 1] = bArr[(previewSize.width * i3) + i4];
            }
        }
        i3 = previewSize.width;
        previewSize.width = previewSize.height;
        previewSize.height = i3;
        PlanarYUVLuminanceSource buildLuminanceSource = buildLuminanceSource(bArr2, previewSize.width, previewSize.height);
        if (buildLuminanceSource != null) {
            try {
                decodeWithState = this.multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(buildLuminanceSource)));
            } catch (ReaderException e) {
                decodeWithState = null;
                handler = this.activity.getHandler();
                if (decodeWithState == null) {
                    if (handler != null) {
                        Message.obtain(handler, R.id.decode_failed).sendToTarget();
                    }
                } else if (handler != null) {
                    Message obtain = Message.obtain(handler, R.id.decode_succeeded, decodeWithState);
                    Bundle bundle = new Bundle();
                    bundleThumbnail(buildLuminanceSource, bundle);
                    obtain.setData(bundle);
                    obtain.sendToTarget();
                }
            } finally {
                buildLuminanceSource = this.multiFormatReader;
                buildLuminanceSource.reset();
            }
        } else {
            decodeWithState = null;
        }
        handler = this.activity.getHandler();
        if (decodeWithState == null) {
            if (handler != null) {
                Message obtain2 = Message.obtain(handler, R.id.decode_succeeded, decodeWithState);
                Bundle bundle2 = new Bundle();
                bundleThumbnail(buildLuminanceSource, bundle2);
                obtain2.setData(bundle2);
                obtain2.sendToTarget();
            }
        } else if (handler != null) {
            Message.obtain(handler, R.id.decode_failed).sendToTarget();
        }
    }

    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] bArr, int i, int i2) {
        Rect cropRect = this.activity.getCropRect();
        if (cropRect == null) {
            return null;
        }
        return new PlanarYUVLuminanceSource(bArr, i, i2, cropRect.left, cropRect.top, cropRect.width(), cropRect.height(), false);
    }

    public void handleMessage(Message message) {
        if (this.running) {
            switch (message.what) {
                case R.id.decode /*2131165185*/:
                    decode((byte[]) message.obj, message.arg1, message.arg2);
                    return;
                case R.id.quit /*2131165191*/:
                    this.running = false;
                    Looper.myLooper().quit();
                    return;
                default:
                    return;
            }
        }
    }
}
