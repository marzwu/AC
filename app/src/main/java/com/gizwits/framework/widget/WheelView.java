package com.gizwits.framework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.ExploreByTouchHelper;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.marz.ac.v1.R;

import java.util.LinkedList;
import java.util.List;

public class WheelView extends View {
    private static final int DEF_VISIBLE_ITEMS = 5;
    private static final int ITEMS_TEXT_COLOR = -16777216;
    private static final int LABEL_OFFSET = 8;
    private static final int MIN_DELTA_FOR_SCROLLING = 1;
    private static final int PADDING = 10;
    private static final int SCROLLING_DURATION = 400;
    private static final int VALUE_TEXT_COLOR = -268435456;
    private int ADDITIONAL_ITEMS_SPACE = 10;
    private int ADDITIONAL_ITEM_HEIGHT = 80;
    private final int ITEM_OFFSET = (this.TEXT_SIZE / 5);
    private final int MESSAGE_JUSTIFY = 1;
    private final int MESSAGE_SCROLL = 0;
    private int TEXT_SIZE = 50;
    private WheelAdapter adapter = null;
    private Handler animationHandler = new Handler() {
        public void handleMessage(Message message) {
            WheelView.this.scroller.computeScrollOffset();
            int currY = WheelView.this.scroller.getCurrY();
            int access$10 = WheelView.this.lastScrollY - currY;
            WheelView.this.lastScrollY = currY;
            if (access$10 != 0) {
                WheelView.this.doScroll(access$10);
            }
            if (Math.abs(currY - WheelView.this.scroller.getFinalY()) < 1) {
                WheelView.this.scroller.getFinalY();
                WheelView.this.scroller.forceFinished(true);
            }
            if (!WheelView.this.scroller.isFinished()) {
                WheelView.this.animationHandler.sendEmptyMessage(message.what);
            } else if (message.what == 0) {
                WheelView.this.justify();
            } else {
                WheelView.this.finishScrolling();
            }
        }
    };
    private Drawable centerDrawable;
    private List<OnWheelChangedListener> changingListeners = new LinkedList();
    private int currentItem = 0;
    private GestureDetector gestureDetector;
    private SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
        public boolean onDown(MotionEvent motionEvent) {
            if (!WheelView.this.isScrollingPerformed) {
                return false;
            }
            WheelView.this.scroller.forceFinished(true);
            WheelView.this.clearMessages();
            return true;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            WheelView.this.lastScrollY = (WheelView.this.currentItem * WheelView.this.getItemHeight()) + WheelView.this.scrollingOffset;
            int itemsCount = WheelView.this.isCyclic ? Integer.MAX_VALUE : WheelView.this.adapter.getItemsCount() * WheelView.this.getItemHeight();
            WheelView.this.scroller.fling(0, WheelView.this.lastScrollY, 0, ((int) (-f2)) / 2, 0, 0, WheelView.this.isCyclic ? -itemsCount : 0, itemsCount);
            WheelView.this.setNextMessage(0);
            return true;
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            WheelView.this.startScrolling();
            WheelView.this.doScroll((int) (-f2));
            return true;
        }
    };
    boolean isCyclic = false;
    private boolean isScrollingPerformed;
    private int itemHeight = 0;
    private StaticLayout itemsLayout;
    private TextPaint itemsPaint;
    private int itemsWidth = 0;
    private String label;
    private StaticLayout labelLayout;
    private int labelWidth = 0;
    private int lastScrollY;
    private Scroller scroller;
    private List<OnWheelScrollListener> scrollingListeners = new LinkedList();
    private int scrollingOffset;
    private StaticLayout valueLayout;
    private TextPaint valuePaint;
    private int visibleItems = 5;

    public WheelView(Context context) {
        super(context);
        initData(context);
    }

    public WheelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initData(context);
    }

    public WheelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initData(context);
    }

    private String buildText(boolean z) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = (this.visibleItems / 2) + 1;
        int i2 = this.currentItem - i;
        while (i2 <= this.currentItem + i) {
            if (z || i2 != this.currentItem) {
                String textItem = getTextItem(i2);
                if (textItem != null) {
                    stringBuilder.append(textItem);
                }
            }
            if (i2 < this.currentItem + i) {
                stringBuilder.append("\n");
            }
            i2++;
        }
        return stringBuilder.toString();
    }

    private int calculateLayoutWidth(int i, int i2) {
        initResourcesIfNecessary();
        int maxTextLength = getMaxTextLength();
        if (maxTextLength > 0) {
            this.itemsWidth = (int) (((float) maxTextLength) * Math.ceil(Layout.getDesiredWidth("0", this.itemsPaint)));
        } else {
            this.itemsWidth = 0;
        }
        this.itemsWidth += this.ADDITIONAL_ITEMS_SPACE;
        this.labelWidth = 0;
        if (this.label != null && this.label.length() > 0) {
            this.labelWidth = (int) Math.ceil(Layout.getDesiredWidth(this.label, this.valuePaint));
        }
        if (i2 == 1073741824) {
            maxTextLength = 1;
        } else {
            maxTextLength = (this.itemsWidth + this.labelWidth) + 20;
            if (this.labelWidth > 0) {
                maxTextLength += 8;
            }
            maxTextLength = Math.max(maxTextLength, getSuggestedMinimumWidth());
            if (i2 != ExploreByTouchHelper.INVALID_ID || i >= maxTextLength) {
                i = maxTextLength;
                maxTextLength = 0;
            } else {
                maxTextLength = 1;
            }
        }
        if (maxTextLength != 0) {
            maxTextLength = (i - 8) - 20;
            if (maxTextLength <= 0) {
                this.labelWidth = 0;
                this.itemsWidth = 0;
            }
            if (this.labelWidth > 0) {
                this.itemsWidth = (int) ((((double) this.itemsWidth) * ((double) maxTextLength)) / ((double) (this.itemsWidth + this.labelWidth)));
                this.labelWidth = maxTextLength - this.itemsWidth;
            } else {
                this.itemsWidth = maxTextLength + 8;
            }
        }
        if (this.itemsWidth > 0) {
            createLayouts(this.itemsWidth, this.labelWidth);
        }
        return i;
    }

    private void clearMessages() {
        this.animationHandler.removeMessages(0);
        this.animationHandler.removeMessages(1);
    }

    private void createLayouts(int i, int i2) {
        if (this.itemsLayout == null || this.itemsLayout.getWidth() > i) {
            this.itemsLayout = new StaticLayout(buildText(this.isScrollingPerformed), this.itemsPaint, i, i2 > 0 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_CENTER, 1.0f, (float) this.ADDITIONAL_ITEM_HEIGHT, false);
        } else {
            this.itemsLayout.increaseWidthTo(i);
        }
        if (!this.isScrollingPerformed && (this.valueLayout == null || this.valueLayout.getWidth() > i)) {
            CharSequence item = getAdapter() != null ? getAdapter().getItem(this.currentItem) : null;
            if (item == null) {
                item = "";
            }
            this.valueLayout = new StaticLayout(item, this.valuePaint, i, i2 > 0 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_CENTER, 1.0f, (float) this.ADDITIONAL_ITEM_HEIGHT, false);
        } else if (this.isScrollingPerformed) {
            this.valueLayout = null;
        } else {
            this.valueLayout.increaseWidthTo(i);
        }
        if (i2 <= 0) {
            return;
        }
        if (this.labelLayout == null || this.labelLayout.getWidth() > i2) {
            this.labelLayout = new StaticLayout(this.label, this.valuePaint, i2, Alignment.ALIGN_NORMAL, 1.0f, (float) this.ADDITIONAL_ITEM_HEIGHT, false);
        } else {
            this.labelLayout.increaseWidthTo(i2);
        }
    }

    private void doScroll(int i) {
        this.scrollingOffset += i;
        int itemHeight = this.scrollingOffset / getItemHeight();
        int i2 = this.currentItem - itemHeight;
        if (this.isCyclic && this.adapter.getItemsCount() > 0) {
            while (i2 < 0) {
                i2 += this.adapter.getItemsCount();
            }
            i2 %= this.adapter.getItemsCount();
        } else if (!this.isScrollingPerformed) {
            i2 = Math.min(Math.max(i2, 0), this.adapter.getItemsCount() - 1);
        } else if (i2 < 0) {
            itemHeight = this.currentItem;
            i2 = 0;
        } else if (i2 >= this.adapter.getItemsCount()) {
            itemHeight = (this.currentItem - this.adapter.getItemsCount()) + 1;
            i2 = this.adapter.getItemsCount() - 1;
        }
        int i3 = this.scrollingOffset;
        if (i2 != this.currentItem) {
            setCurrentItem(i2, false);
        } else {
            invalidate();
        }
        this.scrollingOffset = i3 - (getItemHeight() * itemHeight);
        if (this.scrollingOffset > getHeight()) {
            this.scrollingOffset = (this.scrollingOffset % getHeight()) + getHeight();
        }
    }

    private void drawCenterRect(Canvas canvas) {
        int height = getHeight() / 2;
        int itemHeight = getItemHeight() / 2;
        this.centerDrawable.setBounds(0, height - itemHeight, getWidth(), height + itemHeight);
        this.centerDrawable.draw(canvas);
    }

    private void drawItems(Canvas canvas) {
        canvas.save();
        canvas.translate(0.0f, (float) ((-this.itemsLayout.getLineTop(1)) + this.scrollingOffset));
        this.itemsPaint.setColor(-16777216);
        this.itemsPaint.drawableState = getDrawableState();
        this.itemsLayout.draw(canvas);
        canvas.restore();
    }

    private void drawValue(Canvas canvas) {
        this.valuePaint.setColor(VALUE_TEXT_COLOR);
        this.valuePaint.drawableState = getDrawableState();
        Rect rect = new Rect();
        this.itemsLayout.getLineBounds(this.visibleItems / 2, rect);
        if (this.labelLayout != null) {
            canvas.save();
            canvas.translate((float) (this.itemsLayout.getWidth() + 8), (float) rect.top);
            this.labelLayout.draw(canvas);
            canvas.restore();
        }
        if (this.valueLayout != null) {
            canvas.save();
            canvas.translate(0.0f, (float) (rect.top + this.scrollingOffset));
            this.valueLayout.draw(canvas);
            canvas.restore();
        }
    }

    private int getDesiredHeight(Layout layout) {
        return layout == null ? 0 : Math.max(((getItemHeight() * this.visibleItems) - (this.ITEM_OFFSET * 2)) - this.ADDITIONAL_ITEM_HEIGHT, getSuggestedMinimumHeight());
    }

    private int getItemHeight() {
        if (this.itemHeight != 0) {
            return this.itemHeight;
        }
        if (this.itemsLayout == null || this.itemsLayout.getLineCount() <= 2) {
            return getHeight() / this.visibleItems;
        }
        this.itemHeight = this.itemsLayout.getLineTop(2) - this.itemsLayout.getLineTop(1);
        return this.itemHeight;
    }

    private int getMaxTextLength() {
        WheelAdapter adapter = getAdapter();
        if (adapter != null) {
            int maximumLength = adapter.getMaximumLength();
            if (maximumLength > 0) {
                return maximumLength;
            }
            String str = null;
            for (int max = Math.max(this.currentItem - (this.visibleItems / 2), 0); max < Math.min(this.currentItem + this.visibleItems, adapter.getItemsCount()); max++) {
                String item = adapter.getItem(max);
                if (item != null && (str == null || str.length() < item.length())) {
                    str = item;
                }
            }
            if (str != null) {
                return str.length();
            }
        }
        return 0;
    }

    private String getTextItem(int i) {
        if (!(this.adapter == null || this.adapter.getItemsCount() == 0)) {
            int itemsCount = this.adapter.getItemsCount();
            if ((i >= 0 && i < itemsCount) || this.isCyclic) {
                while (i < 0) {
                    i += itemsCount;
                }
                return this.adapter.getItem(i % itemsCount);
            }
        }
        return null;
    }

    private void initData(Context context) {
        this.gestureDetector = new GestureDetector(context, this.gestureListener);
        this.gestureDetector.setIsLongpressEnabled(false);
        this.scroller = new Scroller(context);
    }

    private void initResourcesIfNecessary() {
        if (this.itemsPaint == null) {
            this.itemsPaint = new TextPaint(33);
            this.itemsPaint.setTextSize((float) this.TEXT_SIZE);
        }
        if (this.valuePaint == null) {
            this.valuePaint = new TextPaint(37);
            this.valuePaint.setTextSize((float) this.TEXT_SIZE);
            this.valuePaint.setShadowLayer(0.1f, 0.0f, 0.1f, -4144960);
        }
        if (this.centerDrawable == null) {
            this.centerDrawable = getContext().getResources().getDrawable(R.drawable.wheel_val);
        }
    }

    private void invalidateLayouts() {
        this.itemsLayout = null;
        this.valueLayout = null;
        this.scrollingOffset = 0;
    }

    private void justify() {
        if (this.adapter == null) {
            return;
        }
        this.lastScrollY = 0;
        int scrollingOffset = this.scrollingOffset;
        final int itemHeight = this.getItemHeight();
        boolean b;
        if (scrollingOffset > 0) {
            if (this.currentItem < this.adapter.getItemsCount()) {
                b = true;
            } else {
                b = false;
            }
        } else if (this.currentItem > 0) {
            b = true;
        } else {
            b = false;
        }
        if ((this.isCyclic || b) && Math.abs((float) scrollingOffset) > itemHeight / 2.0f) {
            if (scrollingOffset < 0) {
                scrollingOffset += itemHeight + 1;
            } else {
                scrollingOffset -= itemHeight + 1;
            }
        }
        if (Math.abs(scrollingOffset) > 1) {
            this.scroller.startScroll(0, 0, 0, scrollingOffset, 400);
            this.setNextMessage(1);
            return;
        }
        this.finishScrolling();
    }

    private void setNextMessage(int i) {
        clearMessages();
        this.animationHandler.sendEmptyMessage(i);
    }

    private void startScrolling() {
        if (!this.isScrollingPerformed) {
            this.isScrollingPerformed = true;
            notifyScrollingListenersAboutStart();
        }
    }

    public void addChangingListener(OnWheelChangedListener onWheelChangedListener) {
        this.changingListeners.add(onWheelChangedListener);
    }

    public void addScrollingListener(OnWheelScrollListener onWheelScrollListener) {
        this.scrollingListeners.add(onWheelScrollListener);
    }

    void finishScrolling() {
        if (this.isScrollingPerformed) {
            notifyScrollingListenersAboutEnd();
            this.isScrollingPerformed = false;
        }
        invalidateLayouts();
        invalidate();
    }

    public WheelAdapter getAdapter() {
        return this.adapter;
    }

    public int getCurrentItem() {
        return this.currentItem;
    }

    public String getLabel() {
        return this.label;
    }

    public int getVisibleItems() {
        return this.visibleItems;
    }

    public boolean isCyclic() {
        return this.isCyclic;
    }

    protected void notifyChangingListeners(int i, int i2) {
        for (OnWheelChangedListener onChanged : this.changingListeners) {
            onChanged.onChanged(this, i, i2);
        }
    }

    protected void notifyScrollingListenersAboutEnd() {
        for (OnWheelScrollListener onScrollingFinished : this.scrollingListeners) {
            onScrollingFinished.onScrollingFinished(this);
        }
    }

    protected void notifyScrollingListenersAboutStart() {
        for (OnWheelScrollListener onScrollingStarted : this.scrollingListeners) {
            onScrollingStarted.onScrollingStarted(this);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.itemsLayout == null) {
            if (this.itemsWidth == 0) {
                calculateLayoutWidth(getWidth(), 1073741824);
            } else {
                createLayouts(this.itemsWidth, this.labelWidth);
            }
        }
        if (this.itemsWidth > 0) {
            canvas.save();
            canvas.translate(10.0f, (float) (-this.ITEM_OFFSET));
            drawItems(canvas);
            drawValue(canvas);
            canvas.restore();
        }
    }

    protected void onMeasure(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        size = calculateLayoutWidth(size, mode);
        if (mode2 != 1073741824) {
            mode = getDesiredHeight(this.itemsLayout);
            size2 = mode2 == ExploreByTouchHelper.INVALID_ID ? Math.min(mode, size2) : mode;
        }
        setMeasuredDimension(size, size2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!(getAdapter() == null || this.gestureDetector.onTouchEvent(motionEvent) || motionEvent.getAction() != 1)) {
            justify();
        }
        return true;
    }

    public void removeChangingListener(OnWheelChangedListener onWheelChangedListener) {
        this.changingListeners.remove(onWheelChangedListener);
    }

    public void removeScrollingListener(OnWheelScrollListener onWheelScrollListener) {
        this.scrollingListeners.remove(onWheelScrollListener);
    }

    public void scroll(int i, int i2) {
        this.scroller.forceFinished(true);
        this.lastScrollY = this.scrollingOffset;
        int itemHeight = i * getItemHeight();
        this.scroller.startScroll(0, this.lastScrollY, 0, itemHeight - this.lastScrollY, i2);
        setNextMessage(0);
        startScrolling();
    }

    public void setADDITIONAL_ITEMS_SPACE(int i) {
        this.ADDITIONAL_ITEMS_SPACE = i;
    }

    public void setADDITIONAL_ITEM_HEIGHT(int i) {
        this.ADDITIONAL_ITEM_HEIGHT = i;
    }

    public void setAdapter(WheelAdapter wheelAdapter) {
        this.adapter = wheelAdapter;
        invalidateLayouts();
        invalidate();
    }

    public void setCurrentItem(int i) {
        setCurrentItem(i, false);
    }

    public void setCurrentItem(int i, boolean z) {
        if (this.adapter != null && this.adapter.getItemsCount() != 0) {
            if (i < 0 || i >= this.adapter.getItemsCount()) {
                if (this.isCyclic) {
                    while (i < 0) {
                        i += this.adapter.getItemsCount();
                    }
                    i %= this.adapter.getItemsCount();
                } else {
                    return;
                }
            }
            if (i == this.currentItem) {
                return;
            }
            if (z) {
                scroll(i - this.currentItem, SCROLLING_DURATION);
                return;
            }
            invalidateLayouts();
            int i2 = this.currentItem;
            this.currentItem = i;
            notifyChangingListeners(i2, this.currentItem);
            invalidate();
        }
    }

    public void setCyclic(boolean z) {
        this.isCyclic = z;
        invalidate();
        invalidateLayouts();
    }

    public void setInterpolator(Interpolator interpolator) {
        this.scroller.forceFinished(true);
        this.scroller = new Scroller(getContext(), interpolator);
    }

    public void setLabel(String str) {
        if (this.label == null || !this.label.equals(str)) {
            this.label = str;
            this.labelLayout = null;
            invalidate();
        }
    }

    public void setTEXT_SIZE(int i) {
        this.TEXT_SIZE = i;
    }

    public void setVisibleItems(int i) {
        this.visibleItems = i;
        invalidate();
    }
}
