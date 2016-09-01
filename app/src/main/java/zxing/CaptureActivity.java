package zxing;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.google.zxing.Result;
import com.uh.all.airpurifier.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.ui.utils.ToastUtils;
import zxing.camera.CameraManager;
import zxing.decoding.DecodeThread;
import zxing.utils.CaptureActivityHandler;
import zxing.utils.InactivityTimer;

public final class CaptureActivity extends BaseActivity implements Callback {
    private static final String TAG = CaptureActivity.class.getSimpleName();
    private Button btnCancel;
    private CameraManager cameraManager;
    private String did;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private boolean isHasSurface = false;
    private ImageView ivReturn;
    private Rect mCropRect = null;
    Handler mHandler = new Handler() {
        private static /* synthetic */ int[] $SWITCH_TABLE$zxing$CaptureActivity$handler_key;

        static /* synthetic */ int[] $SWITCH_TABLE$zxing$CaptureActivity$handler_key() {
            int[] iArr = $SWITCH_TABLE$zxing$CaptureActivity$handler_key;
            if (iArr == null) {
                iArr = new int[handler_key.values().length];
                try {
                    iArr[handler_key.FAILED.ordinal()] = 3;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[handler_key.START_BIND.ordinal()] = 1;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[handler_key.SUCCESS.ordinal()] = 2;
                } catch (NoSuchFieldError e3) {
                }
                $SWITCH_TABLE$zxing$CaptureActivity$handler_key = iArr;
            }
            return iArr;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (AnonymousClass1.$SWITCH_TABLE$zxing$CaptureActivity$handler_key()[handler_key.values()[message.what].ordinal()]) {
                case 1:
                    CaptureActivity.this.startBind(CaptureActivity.this.passcode, CaptureActivity.this.did);
                    return;
                case 2:
                    ToastUtils.showShort(CaptureActivity.this, "添加成功");
                    IntentUtils.getInstance().startActivity(CaptureActivity.this, DeviceListActivity.class);
                    CaptureActivity.this.finish();
                    return;
                case 3:
                    ToastUtils.showShort(CaptureActivity.this, "添加失败，请返回重试");
                    CaptureActivity.this.finish();
                    return;
                default:
                    return;
            }
        }
    };
    private String passcode;
    private String product_key;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView scanLine;
    private SurfaceView scanPreview = null;

    private enum handler_key {
        START_BIND,
        SUCCESS,
        FAILED
    }

    private void displayFrameworkBugMessageAndExit() {
        Builder builder = new Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("相机打开出错，请稍后重试");
        builder.setPositiveButton("确定", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                CaptureActivity.this.finish();
            }
        });
        builder.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                CaptureActivity.this.finish();
            }
        });
        builder.show();
    }

    private String getParamFomeUrl(String str, String str2) {
        String substring = str.substring(str.indexOf(new StringBuilder(String.valueOf(str2)).append("=").toString()) + (str2.length() + 1));
        int indexOf = substring.indexOf("&");
        return indexOf == -1 ? substring : substring.substring(0, indexOf);
    }

    private int getStatusBarHeight() {
        try {
            Class cls = Class.forName("com.android.internal.R$dimen");
            return getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        } else if (this.cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
        } else {
            try {
                this.cameraManager.openDriver(surfaceHolder);
                if (this.handler == null) {
                    this.handler = new CaptureActivityHandler(this, this.cameraManager, DecodeThread.ALL_MODE);
                }
                initCrop();
            } catch (Throwable e) {
                Log.w(TAG, e);
                displayFrameworkBugMessageAndExit();
            } catch (Throwable e2) {
                Log.w(TAG, "Unexpected error initializing camera", e2);
                displayFrameworkBugMessageAndExit();
            }
        }
    }

    private void initCrop() {
        int i = this.cameraManager.getCameraResolution().y;
        int i2 = this.cameraManager.getCameraResolution().x;
        int[] iArr = new int[2];
        this.scanCropView.getLocationInWindow(iArr);
        int i3 = iArr[0];
        int statusBarHeight = iArr[1] - getStatusBarHeight();
        int width = this.scanCropView.getWidth();
        int height = this.scanCropView.getHeight();
        int width2 = this.scanContainer.getWidth();
        int height2 = this.scanContainer.getHeight();
        i3 = (i3 * i) / width2;
        statusBarHeight = (statusBarHeight * i2) / height2;
        this.mCropRect = new Rect(i3, statusBarHeight, ((i * width) / width2) + i3, ((i2 * height) / height2) + statusBarHeight);
    }

    private void startBind(String str, String str2) {
        this.mCenter.cBindDevice(this.setmanager.getUid(), this.setmanager.getToken(), str2, str, "");
    }

    protected void didBindDevice(int i, String str, String str2) {
        Log.d("扫描结果", "error=" + i + ";errorMessage=" + str + ";did=" + str2);
        if (i == 0) {
            this.mHandler.sendEmptyMessage(handler_key.SUCCESS.ordinal());
            return;
        }
        Message message = new Message();
        message.what = handler_key.FAILED.ordinal();
        message.obj = str;
        this.mHandler.sendMessage(message);
    }

    public CameraManager getCameraManager() {
        return this.cameraManager;
    }

    public Rect getCropRect() {
        return this.mCropRect;
    }

    public Handler getHandler() {
        return this.handler;
    }

    public void handleDecode(Result result, Bundle bundle) {
        String text = result.getText();
        Log.i("test", text);
        if ((text.contains("product_key=") & text.contains("did=")) == 0 || !text.contains("passcode=")) {
            this.handler = new CaptureActivityHandler(this, this.cameraManager, DecodeThread.ALL_MODE);
            return;
        }
        this.inactivityTimer.onActivity();
        this.product_key = getParamFomeUrl(text, "product_key");
        this.did = getParamFomeUrl(text, "did");
        this.passcode = getParamFomeUrl(text, "passcode");
        Log.i("passcode product_key did", this.passcode + " " + this.product_key + " " + this.did);
        ToastUtils.showShort((Context) this, "扫码成功");
        this.mHandler.sendEmptyMessage(handler_key.START_BIND.ordinal());
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_capture);
        this.scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        this.scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
        this.scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        this.scanLine = (ImageView) findViewById(R.id.capture_scan_line);
        this.inactivityTimer = new InactivityTimer(this);
        Animation translateAnimation = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, -1.0f, 2, 0.0f);
        translateAnimation.setDuration(4500);
        translateAnimation.setRepeatCount(-1);
        translateAnimation.setRepeatMode(1);
        this.scanLine.startAnimation(translateAnimation);
        this.btnCancel = (Button) findViewById(R.id.btn_cancel);
        this.ivReturn = (ImageView) findViewById(R.id.iv_return);
        View.OnClickListener anonymousClass2 = new View.OnClickListener() {
            public void onClick(View view) {
                CaptureActivity.this.finish();
            }
        };
        this.btnCancel.setOnClickListener(anonymousClass2);
        this.ivReturn.setOnClickListener(anonymousClass2);
    }

    protected void onDestroy() {
        this.inactivityTimer.shutdown();
        super.onDestroy();
    }

    protected void onPause() {
        if (this.handler != null) {
            this.handler.quitSynchronously();
            this.handler = null;
        }
        this.inactivityTimer.onPause();
        this.cameraManager.closeDriver();
        if (!this.isHasSurface) {
            this.scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.cameraManager = new CameraManager(getApplication());
        this.handler = null;
        if (this.isHasSurface) {
            initCamera(this.scanPreview.getHolder());
        } else {
            this.scanPreview.getHolder().addCallback(this);
        }
        this.inactivityTimer.onResume();
    }

    public void restartPreviewAfterDelay(long j) {
        if (this.handler != null) {
            this.handler.sendEmptyMessageDelayed(R.id.restart_preview, j);
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!this.isHasSurface) {
            this.isHasSurface = true;
            initCamera(surfaceHolder);
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.isHasSurface = false;
    }
}
