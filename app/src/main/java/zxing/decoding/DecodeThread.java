package zxing.decoding;

import android.os.Handler;
import android.os.Looper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import zxing.CaptureActivity;

public class DecodeThread extends Thread {
    public static final int ALL_MODE = 768;
    public static final String BARCODE_BITMAP = "barcode_bitmap";
    public static final int BARCODE_MODE = 256;
    public static final int QRCODE_MODE = 512;
    private final CaptureActivity activity;
    private Handler handler;
    private final CountDownLatch handlerInitLatch = new CountDownLatch(1);
    private final Map<DecodeHintType, Object> hints = new EnumMap(DecodeHintType.class);

    public DecodeThread(CaptureActivity captureActivity, int i) {
        this.activity = captureActivity;
        Collection arrayList = new ArrayList();
        arrayList.addAll(EnumSet.of(BarcodeFormat.AZTEC));
        arrayList.addAll(EnumSet.of(BarcodeFormat.PDF_417));
        switch (i) {
            case 256:
                arrayList.addAll(DecodeFormatManager.getBarCodeFormats());
                break;
            case 512:
                arrayList.addAll(DecodeFormatManager.getQrCodeFormats());
                break;
            case ALL_MODE /*768*/:
                arrayList.addAll(DecodeFormatManager.getBarCodeFormats());
                arrayList.addAll(DecodeFormatManager.getQrCodeFormats());
                break;
        }
        this.hints.put(DecodeHintType.POSSIBLE_FORMATS, arrayList);
    }

    public Handler getHandler() {
        try {
            this.handlerInitLatch.await();
        } catch (InterruptedException e) {
        }
        return this.handler;
    }

    public void run() {
        Looper.prepare();
        this.handler = new DecodeHandler(this.activity, this.hints);
        this.handlerInitLatch.countDown();
        Looper.loop();
    }
}
