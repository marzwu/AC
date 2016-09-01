package com.gizwits.framework.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.v4.util.LruCache;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.gizwits.framework.utils.UIUtils;
import com.uh.all.airpurifier.R;

public class RoseBoxSeekBarView extends View {
    private static final String TAG = "RoseBoxSeekBarView";
    private static Context mContext;
    private int bigfonSize;
    private int chinesefonSize;
    private Bitmap circleBgErr;
    private Bitmap circleBgNormal;
    private int circleCx;
    private int circleCy;
    private Paint circlePaint;
    private int circleRadius;
    private RectF circleRectf;
    private Bitmap circleRingErr;
    private Bitmap circleRingNormal;
    private int circleWidth;
    private PaintFlagsDrawFilter drawFilter;
    private boolean isRoseboxErr;
    private LruCache<String, Bitmap> mMemoryCache;
    private Resources mResources;
    private int percent;
    private Paint percentNumPaint;
    private Paint percentTipPaint;
    private Paint progressPaint;
    private Rect rect1;
    private RectF rectf1;
    private Paint ringPaint;
    private int smallfonSize;
    private Paint tipsTextPaint;
    private int viewHeight;
    private int viewWidth;
    private Xfermode xfermode;

    public RoseBoxSeekBarView(Context context) {
        this(context, null);
    }

    public RoseBoxSeekBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoseBoxSeekBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.percent = 100;
        this.isRoseboxErr = false;
        mContext = context;
        this.mResources = context.getApplicationContext().getResources();
        this.drawFilter = new PaintFlagsDrawFilter(0, 3);
        this.mMemoryCache = new LruCache<String, Bitmap>(((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8) {
            protected int sizeOf(String str, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    private void addBitmapToMemoryCache(String str, Bitmap bitmap) {
        if (getBitmapFromMemCache(str) == null) {
            this.mMemoryCache.put(str, bitmap);
        }
    }

    private void drawBg(Canvas canvas) {
        if (this.isRoseboxErr) {
            canvas.drawBitmap(this.circleBgErr, this.rect1, this.rectf1, this.circlePaint);
        } else {
            canvas.drawBitmap(this.circleBgNormal, this.rect1, this.rectf1, this.circlePaint);
        }
    }

    private void drawPercentNum(Canvas canvas) {
        if (this.percent == 100) {
            canvas.drawText(new StringBuilder(String.valueOf(this.percent)).toString(), (float) (this.circleCx - (this.smallfonSize / 2)), (float) (this.circleCy + (this.bigfonSize / 2)), this.percentNumPaint);
        } else {
            canvas.drawText(new StringBuilder(String.valueOf(this.percent)).toString(), (float) this.circleCx, (float) (this.circleCy + (this.bigfonSize / 2)), this.percentNumPaint);
        }
    }

    private void drawProgressCircle(Canvas canvas, float f) {
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) this.viewWidth, (float) this.viewHeight, null, 31);
        if (this.isRoseboxErr) {
            canvas.drawBitmap(this.circleRingErr, this.rect1, this.rectf1, this.circlePaint);
        } else {
            canvas.drawBitmap(this.circleRingNormal, this.rect1, this.rectf1, this.circlePaint);
        }
        this.circlePaint.setXfermode(this.xfermode);
        float f2 = (f / 100.0f) * 360.0f;
        canvas.drawArc(this.circleRectf, 90.0f + f2, 360.0f - f2, false, this.circlePaint);
        this.circlePaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
    }

    private void drawTipsText(Canvas canvas) {
        canvas.drawText("当前滤网寿命为", (float) (this.circleCx - ((int) (0.3d * ((double) this.chinesefonSize)))), (float) (this.circleCy - (this.bigfonSize / 2)), this.tipsTextPaint);
        canvas.drawText("%", (float) (this.circleCx + ((int) (((double) this.circleRadius) / 2.5d))), (float) (this.circleCy + (this.smallfonSize / 3)), this.percentTipPaint);
    }

    private Bitmap getBitmapFromMemCache(String str) {
        return (Bitmap) this.mMemoryCache.get(str);
    }

    private int getDegresFromTemplate(int i) {
        return (i < 30 || i > 63) ? ((i - 30) * 8) - 270 : ((i - 30) * 8) + 90;
    }

    private int getTemplateFromDegree(float f) {
        return f > 90.0f ? (((int) (f - 90.0f)) / 8) + 30 : (f > 90.0f || f < 88.0f) ? (((int) (270.0f + f)) / 8) + 30 : 75;
    }

    private void initPaint() {
        this.xfermode = new PorterDuffXfermode(Mode.CLEAR);
        this.circlePaint = new Paint();
        this.circlePaint.setAntiAlias(true);
        this.circlePaint.setColor(this.mResources.getColor(R.color.circle_color));
        this.circlePaint.setStyle(Style.STROKE);
        this.circlePaint.setStrokeWidth((float) this.circleWidth);
        this.circleRectf = new RectF();
        this.circleRectf.left = (float) ((this.circleCx - this.circleRadius) + (this.circleWidth / 2));
        this.circleRectf.top = (float) ((this.circleCy - this.circleRadius) + (this.circleWidth / 2));
        this.circleRectf.right = (float) (((this.circleRadius * 2) + (this.circleCx - this.circleRadius)) - (this.circleWidth / 2));
        this.circleRectf.bottom = (float) (((this.circleRadius * 2) + (this.circleCy - this.circleRadius)) - (this.circleWidth / 2));
        this.progressPaint = new Paint();
        this.progressPaint.setAntiAlias(true);
        this.progressPaint.setColor(-256);
        this.progressPaint.setStyle(Style.STROKE);
        this.progressPaint.setStrokeWidth((float) (this.circleWidth / 2));
        this.tipsTextPaint = new TextPaint(1);
        this.tipsTextPaint.setTextSize((float) this.chinesefonSize);
        this.tipsTextPaint.setColor(-1);
        this.tipsTextPaint.setTextAlign(Align.CENTER);
        this.percentNumPaint = new TextPaint(1);
        this.percentNumPaint.setTextSize((float) this.bigfonSize);
        this.percentNumPaint.setColor(-1);
        this.percentNumPaint.setTextAlign(Align.CENTER);
        this.percentTipPaint = new TextPaint(1);
        this.percentTipPaint.setTextSize((float) this.smallfonSize);
        this.percentTipPaint.setColor(-1);
        this.percentTipPaint.setTextAlign(Align.CENTER);
    }

    private Bitmap loadBitmap(int i, int i2) {
        Bitmap bitmapFromMemCache = getBitmapFromMemCache(String.valueOf(i));
        if (bitmapFromMemCache != null) {
            return bitmapFromMemCache;
        }
        bitmapFromMemCache = UIUtils.decodeSampledBitmapFromResource(mContext, this.mResources, i, i2, i2);
        addBitmapToMemoryCache(String.valueOf(i), bitmapFromMemCache);
        return bitmapFromMemCache;
    }

    private Bitmap loadBitmap(int i, int i2, int i3, int i4, int i5) {
        Bitmap bitmapFromMemCache = getBitmapFromMemCache(String.valueOf(i));
        if (bitmapFromMemCache != null) {
            return bitmapFromMemCache;
        }
        Bitmap decodeSampledBitmapFromResource = UIUtils.decodeSampledBitmapFromResource(mContext, this.mResources, i, i2, i3);
        bitmapFromMemCache = UIUtils.resizeImage(decodeSampledBitmapFromResource, i4, i5);
        addBitmapToMemoryCache(String.valueOf(i), bitmapFromMemCache);
        decodeSampledBitmapFromResource.recycle();
        return bitmapFromMemCache;
    }

    public float angel(float f, float f2) {
        float sqrt = (float) Math.sqrt((double) (((((float) this.circleCx) - f) * (((float) this.circleCx) - f)) + ((((float) this.circleCy) - f2) * (((float) this.circleCy) - f2))));
        float abs = Math.abs(f2 - ((float) this.circleCy));
        float abs2 = Math.abs(f - ((float) this.circleCx));
        return (float) (((double) (((float) Math.acos((double) ((((abs2 * abs2) + (sqrt * sqrt)) - (abs * abs)) / (sqrt * (abs2 * 2.0f))))) * 180.0f)) / 3.141592653589793d);
    }

    public float finalangel(float f, float f2, float f3) {
        return (f >= ((float) this.circleCx) || f2 >= ((float) this.circleCy)) ? (f != ((float) this.circleCx) || f2 >= ((float) this.circleCy)) ? (f <= ((float) this.circleCx) || f2 >= ((float) this.circleCy)) ? (f <= ((float) this.circleCx) || f2 != ((float) this.circleCy)) ? (f >= ((float) this.circleCx) || f2 != ((float) this.circleCy)) ? (f <= ((float) this.circleCx) || f2 <= ((float) this.circleCy)) ? 180.0f - f3 : f3 < 1.0f ? 360.0f : f3 : 180.0f : 360.0f : 360.0f - f3 : 270.0f : f3 + 180.0f;
    }

    public int getFontHeight(float f) {
        Paint paint = new Paint();
        paint.setTextSize(f);
        FontMetrics fontMetrics = paint.getFontMetrics();
        return ((int) Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent))) + 2;
    }

    public int getPercent() {
        return this.percent;
    }

    public void initParams(int i, int i2) {
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.viewWidth = i;
        this.viewHeight = i2;
        this.circleCx = i >> 1;
        this.circleCy = i2 >> 1;
        this.circleRadius = this.circleCx;
        this.bigfonSize = this.circleRadius / 2;
        this.smallfonSize = this.circleRadius / 4;
        this.chinesefonSize = this.bigfonSize / 5;
        this.circleWidth = this.circleRadius / 2;
        this.circleBgNormal = loadBitmap(R.drawable.rb_circle_bg_normal, i);
        this.circleBgErr = loadBitmap(R.drawable.rb_circle_bg_err, i);
        this.circleRingNormal = loadBitmap(R.drawable.rb_circle_ring_normal, i);
        this.circleRingErr = loadBitmap(R.drawable.rb_circle_ring_err, i);
        this.rect1 = new Rect(0, 0, this.circleBgNormal.getWidth(), this.circleBgNormal.getHeight());
        this.rectf1 = new RectF((float) (this.circleCx - ((int) (((double) (i / 2)) * 0.9d))), (float) (this.circleCy - ((int) (((double) (i / 2)) * 0.9d))), (float) (this.circleCx + ((int) (((double) (i / 2)) * 0.9d))), (float) (this.circleCy + ((int) (((double) (i / 2)) * 0.9d))));
        new Matrix().postScale(0.5f, 0.5f);
        initPaint();
    }

    public boolean isRoseboxErr() {
        return this.isRoseboxErr;
    }

    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(this.drawFilter);
        drawBg(canvas);
        drawTipsText(canvas);
        drawPercentNum(canvas);
        drawProgressCircle(canvas, (float) this.percent);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        initParams(i3 - i, i4 - i2);
    }

    public void setPercent(int i) {
        this.percent = i;
        postInvalidate();
    }

    public void setRoseboxErr(boolean z) {
        this.isRoseboxErr = z;
        postInvalidate();
    }
}
