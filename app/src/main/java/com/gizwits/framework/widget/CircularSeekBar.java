package com.gizwits.framework.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import com.uh.all.airpurifier.R;
import com.xpg.common.device.DensityUtils;

public class CircularSeekBar extends View {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$widget$CircularSeekBar$lockX;
    private boolean CALLED_FROM_ANGLE = false;
    private boolean IS_PRESSED = false;
    private boolean SHOW_SEEKBAR = true;
    private float adjustmentFactor = 100.0f;
    private int angle = 0;
    private int barWidth = 8;
    private float bottom;
    private Paint circleColor = new Paint();
    private Paint circleRing = new Paint();
    private float cx;
    private float cy;
    private float dx;
    private float dy;
    private int height;
    private Paint innerColor = new Paint();
    private float innerRadius;
    private float left;
    private Context mContext;
    private OnSeekContinueChangeListener mContinueListener;
    private OnSeekChangeListener mListener = new OnSeekChangeListener() {
        public void onProgressChange(CircularSeekBar circularSeekBar, int i) {
        }
    };
    private ScrollView mScrollView;
    private float markPointX;
    private float markPointY;
    private int maxProgress = 100;
    private lockX myLock = lockX.LockLeft;
    private float outerRadius;
    private int progress;
    private Bitmap progressMark;
    private Bitmap progressMarkPressed;
    private int progressPercent;
    private RectF rect = new RectF();
    private float right;
    private int startAngle = 270;
    private float startPointX;
    private float startPointY;
    private float top;
    private int width;

    public interface OnSeekChangeListener {
        void onProgressChange(CircularSeekBar circularSeekBar, int i);
    }

    public interface OnSeekContinueChangeListener {
        void onProgressContinueChange(CircularSeekBar circularSeekBar, int i);
    }

    enum lockX {
        UnLock,
        LockLeft,
        LockRight
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$gizwits$framework$widget$CircularSeekBar$lockX() {
        int[] iArr = $SWITCH_TABLE$com$gizwits$framework$widget$CircularSeekBar$lockX;
        if (iArr == null) {
            iArr = new int[lockX.values().length];
            try {
                iArr[lockX.LockLeft.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[lockX.LockRight.ordinal()] = 3;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[lockX.UnLock.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$com$gizwits$framework$widget$CircularSeekBar$lockX = iArr;
        }
        return iArr;
    }

    public CircularSeekBar(Context context) {
        super(context);
        this.circleColor.setColor(Color.parseColor("#ff33b5e5"));
        this.innerColor.setColor(-1);
        this.circleRing.setColor(-7829368);
        this.circleColor.setAntiAlias(true);
        this.innerColor.setAntiAlias(true);
        this.circleRing.setAntiAlias(true);
        this.circleColor.setStrokeWidth(5.0f);
        this.innerColor.setStrokeWidth(5.0f);
        this.circleRing.setStrokeWidth(5.0f);
        this.circleColor.setStyle(Style.FILL);
        this.mContext = context;
        initDrawable();
    }

    public CircularSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.circleColor.setColor(Color.parseColor("#ff33b5e5"));
        this.innerColor.setColor(-1);
        this.circleRing.setColor(-7829368);
        this.circleColor.setAntiAlias(true);
        this.innerColor.setAntiAlias(true);
        this.circleRing.setAntiAlias(true);
        this.circleColor.setStrokeWidth(5.0f);
        this.innerColor.setStrokeWidth(5.0f);
        this.circleRing.setStrokeWidth(5.0f);
        this.circleColor.setStyle(Style.FILL);
        this.mContext = context;
        initDrawable();
    }

    public CircularSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.circleColor.setColor(Color.parseColor("#ff33b5e5"));
        this.innerColor.setColor(-1);
        this.circleRing.setColor(-7829368);
        this.circleColor.setAntiAlias(true);
        this.innerColor.setAntiAlias(true);
        this.circleRing.setAntiAlias(true);
        this.circleColor.setStrokeWidth(5.0f);
        this.innerColor.setStrokeWidth(5.0f);
        this.circleRing.setStrokeWidth(5.0f);
        this.circleColor.setStyle(Style.FILL);
        this.mContext = context;
        initDrawable();
    }

    private void moved(float f, float f2, boolean z) {
        if (isMarkPointRange(f, f2)) {
            float sqrt = (float) Math.sqrt(Math.pow((double) (f - this.cx), 2.0d) + Math.pow((double) (f2 - this.cy), 2.0d));
            if (sqrt >= this.outerRadius + this.adjustmentFactor || sqrt <= this.innerRadius - this.adjustmentFactor || z) {
                if (this.mListener != null) {
                    this.mListener.onProgressChange(this, getProgress());
                }
                this.IS_PRESSED = false;
                invalidate();
                return;
            }
            this.IS_PRESSED = true;
            this.markPointX = (float) (((double) this.cx) + (((double) this.outerRadius) * Math.cos(Math.atan2((double) (f - this.cx), (double) (this.cy - f2)) - 1.5707963267948966d)));
            this.markPointY = (float) (((double) this.cy) + (((double) this.outerRadius) * Math.sin(Math.atan2((double) (f - this.cx), (double) (this.cy - f2)) - 1.5707963267948966d)));
            sqrt = (float) (((double) ((float) (Math.toDegrees(Math.atan2((double) (f - this.cx), (double) (this.cy - f2))) + 360.0d))) % 360.0d);
            if (sqrt < 0.0f) {
                sqrt = (float) (((double) sqrt) + 6.283185307179586d);
            }
            setAngle(Math.round(sqrt));
            invalidate();
        }
    }

    private void setParentScrollAble(boolean z) {
        if (this.mScrollView != null) {
            this.mScrollView.requestDisallowInterceptTouchEvent(!z);
        }
    }

    public void ShowSeekBar() {
        this.SHOW_SEEKBAR = true;
        postInvalidate();
    }

    public void drawMarkerAtProgress(Canvas canvas) {
        if (this.IS_PRESSED) {
            canvas.drawBitmap(this.progressMarkPressed, this.dx, this.dy, null);
        } else {
            canvas.drawBitmap(this.progressMark, this.dx, this.dy, null);
        }
    }

    public float getAdjustmentFactor() {
        return this.adjustmentFactor;
    }

    public int getAngle() {
        return this.angle;
    }

    public int getBarWidth() {
        return this.barWidth;
    }

    public int getMaxProgress() {
        return this.maxProgress;
    }

    public int getProgress() {
        return this.progress;
    }

    public int getProgressPercent() {
        return this.progressPercent;
    }

    public OnSeekChangeListener getSeekBarChangeListener() {
        return this.mListener;
    }

    public OnSeekContinueChangeListener getSeekContinueChangeListener() {
        return this.mContinueListener;
    }

    public float getXFromAngle() {
        int width = this.progressMark.getWidth();
        int width2 = this.progressMarkPressed.getWidth();
        if (width <= width2) {
            width = width2;
        }
        return this.markPointX - ((float) (width / 2));
    }

    public float getYFromAngle() {
        int height = this.progressMark.getHeight();
        int height2 = this.progressMarkPressed.getHeight();
        if (height <= height2) {
            height = height2;
        }
        return this.markPointY - ((float) (height / 2));
    }

    public void hideSeekBar() {
        this.SHOW_SEEKBAR = false;
        postInvalidate();
    }

    public void initDrawable() {
        this.progressMark = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.icon_regulate);
        this.progressMarkPressed = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.icon_regulate);
    }

    public boolean isMarkPointRange(float f, float f2) {
        float dp2px = (float) DensityUtils.dp2px(getContext(), 60.0f);
        return f > this.markPointX - dp2px && f < this.markPointX + dp2px && f2 > this.markPointY - dp2px && f2 < dp2px + this.markPointY;
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(this.cx, this.cy, this.outerRadius, this.circleRing);
        canvas.drawArc(this.rect, (float) this.startAngle, (float) this.angle, true, this.circleColor);
        canvas.drawCircle(this.cx, this.cy, this.innerRadius, this.innerColor);
        if (this.SHOW_SEEKBAR) {
            setInitMarkToXY(getAngle());
            this.dx = getXFromAngle();
            this.dy = getYFromAngle();
            drawMarkerAtProgress(canvas);
        }
        super.onDraw(canvas);
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.barWidth = DensityUtils.dp2px(getContext(), 5.0f);
        this.width = getWidth();
        this.height = getHeight();
        int i3 = this.width > this.height ? this.height : this.width;
        this.cx = (float) (this.width / 2);
        this.cy = (float) (this.height / 2);
        this.outerRadius = (float) ((i3 / 2) - DensityUtils.dp2px(getContext(), 20.0f));
        this.innerRadius = this.outerRadius - ((float) this.barWidth);
        this.left = this.cx - this.outerRadius;
        this.right = this.cx + this.outerRadius;
        this.top = this.cy - this.outerRadius;
        this.bottom = this.cy + this.outerRadius;
        this.startPointX = this.cx;
        this.startPointY = this.cy - this.outerRadius;
        this.markPointX = this.startPointX;
        this.markPointY = this.startPointY;
        this.rect.set(this.left, this.top, this.right, this.bottom);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float dp2px = (float) DensityUtils.dp2px(getContext(), 60.0f);
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (y >= this.cy) {
            this.myLock = lockX.UnLock;
        }
        switch ($SWITCH_TABLE$com$gizwits$framework$widget$CircularSeekBar$lockX()[this.myLock.ordinal()]) {
            case 1:
                if (x > this.cx) {
                    this.myLock = lockX.LockLeft;
                }
                if (x < this.cx) {
                    this.myLock = lockX.LockRight;
                    break;
                }
                break;
            case 2:
                if (x <= this.cx) {
                    x = this.cx;
                    break;
                }
                break;
            case 3:
                if (x > this.cx) {
                    x = this.cx - 1.0f;
                    break;
                }
                break;
        }
        switch (motionEvent.getAction()) {
            case 0:
                if (x < this.markPointX + dp2px && x > this.markPointX - dp2px && y > this.markPointY - dp2px && y < dp2px + this.markPointY) {
                    setParentScrollAble(false);
                }
                moved(x, y, false);
                break;
            case 1:
                moved(x, y, true);
                break;
            case 2:
                moved(x, y, false);
                break;
            case 3:
                setParentScrollAble(true);
                moved(x, y, true);
                break;
        }
        return true;
    }

    public void setAdjustmentFactor(float f) {
        this.adjustmentFactor = f;
    }

    public void setAngle(int i) {
        this.angle = i;
        float f = (((float) this.angle) / 360.0f) * 100.0f;
        float maxProgress = (f / 100.0f) * ((float) getMaxProgress());
        setProgressPercent(Math.round(f));
        this.CALLED_FROM_ANGLE = true;
        setProgress(Math.round(maxProgress));
    }

    public void setBackGroundColor(int i) {
        this.innerColor.setColor(i);
    }

    public void setBarWidth(int i) {
        this.barWidth = i;
    }

    public void setInitMarkToXY(int i) {
        this.markPointX = (float) (((double) this.cx) + (((double) this.outerRadius) * Math.sin((((double) i) * 3.141592653589793d) / 180.0d)));
        this.markPointY = (float) (((double) this.cy) - (((double) this.outerRadius) * Math.cos((((double) i) * 3.141592653589793d) / 180.0d)));
        invalidate();
    }

    public void setMProgress(int i) {
        this.myLock = lockX.UnLock;
        int i2 = (i * 100) / this.maxProgress;
        setAngle((i2 * 360) / 100);
        setProgressPercent(i2);
    }

    public void setMaxProgress(int i) {
        this.maxProgress = i;
    }

    public void setProgress(int i) {
        if (this.progress != i) {
            this.progress = i;
            if (!this.CALLED_FROM_ANGLE) {
                int i2 = (this.progress * 100) / this.maxProgress;
                setAngle((i2 * 360) / 100);
                setProgressPercent(i2);
            }
            if (this.mContinueListener != null) {
                this.mContinueListener.onProgressContinueChange(this, getProgress());
            }
            this.CALLED_FROM_ANGLE = false;
        }
    }

    public void setProgressColor(int i) {
        this.circleColor.setColor(i);
    }

    public void setProgressPercent(int i) {
        this.progressPercent = i;
    }

    public void setRingBackgroundColor(int i) {
        this.circleRing.setColor(i);
    }

    public void setScrollViewInParent(ScrollView scrollView) {
        this.mScrollView = scrollView;
    }

    public void setSeekBarChangeListener(OnSeekChangeListener onSeekChangeListener) {
        this.mListener = onSeekChangeListener;
    }

    public void setSeekContinueChangeListener(OnSeekContinueChangeListener onSeekContinueChangeListener) {
        this.mContinueListener = onSeekContinueChangeListener;
    }
}
