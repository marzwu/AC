package com.gizwits.framework.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.gizwits.framework.utils.DensityUtil;
import com.nineoldandroids.view.ViewHelper;

public class SlidingMenu extends HorizontalScrollView {
    private boolean isOpen;
    private int mHalfMenuWidth;
    private SlidingMenuListener mListener;
    private ViewGroup mMenu;
    private int mMenuRightPadding;
    private int mMenuWidth;
    private int mScreenWidth;
    private final int num;
    private boolean once;

    public interface SlidingMenuListener {
        void CloseFinish();

        void OpenFinish();
    }

    public SlidingMenu(Context context) {
        this(context, null, 0);
    }

    public SlidingMenu(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingMenu(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.num = 60;
        this.mScreenWidth = getScreenWidth(context);
        this.mMenuRightPadding = DensityUtil.dip2px(context, 60.0f);
    }

    private static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private void updateState() {
        if (this.isOpen) {
            scrollTo(0, 0);
        } else {
            scrollTo(this.mMenuWidth, 0);
        }
    }

    public void closeMenu() {
        if (this.isOpen) {
            smoothScrollTo(this.mMenuWidth, 0);
            this.isOpen = false;
            if (this.mListener != null) {
                this.mListener.CloseFinish();
            }
        }
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.isOpen && motionEvent.getX() > ((float) this.mMenuWidth)) {
            toggle();
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            scrollTo(this.mMenuWidth, 0);
            this.once = true;
        }
    }

    protected void onMeasure(int i, int i2) {
        if (!this.once) {
            LinearLayout linearLayout = (LinearLayout) getChildAt(0);
            this.mMenu = (ViewGroup) linearLayout.getChildAt(0);
            this.mMenuWidth = this.mScreenWidth - this.mMenuRightPadding;
            this.mHalfMenuWidth = this.mMenuWidth / 2;
            this.mMenu.getLayoutParams().width = this.mMenuWidth;
            for (int i3 = 1; i3 < linearLayout.getChildCount(); i3++) {
                ((ViewGroup) linearLayout.getChildAt(i3)).getLayoutParams().width = this.mScreenWidth;
            }
        }
        updateState();
        super.onMeasure(i, i2);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        this.isOpen = bundle.getBoolean("isOpen");
        updateState();
        super.onRestoreInstanceState(bundle.getParcelable("android_state"));
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        Parcelable bundle = new Bundle();
        ((Bundle)bundle).putBoolean("isOpen", this.isOpen);
        ((Bundle)bundle).putParcelable("android_state", onSaveInstanceState);
        return bundle;
    }

    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        ViewHelper.setTranslationX(this.mMenu, ((1.0f * ((float) i)) / ((float) this.mMenuWidth)) * ((float) this.mMenuWidth));
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.isOpen) {
            return true;
        }
        switch (motionEvent.getAction()) {
            case 1:
                if (getScrollX() > this.mHalfMenuWidth) {
                    this.isOpen = true;
                    closeMenu();
                    return true;
                }
                this.isOpen = false;
                openMenu();
                return true;
            default:
                return super.onTouchEvent(motionEvent);
        }
    }

    public void openMenu() {
        if (!this.isOpen) {
            smoothScrollTo(0, 0);
            this.isOpen = true;
            if (this.mListener != null) {
                this.mListener.OpenFinish();
            }
        }
    }

    public void setSlidingMenuListener(SlidingMenuListener slidingMenuListener) {
        this.mListener = slidingMenuListener;
    }

    public void toggle() {
        if (this.isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }
}
