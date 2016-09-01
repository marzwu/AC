package com.gizwits.framework.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.uh.all.airpurifier.R;

public class RefreshableListView extends ListView {
    private static final int HEADER_HEIGHT_DP = 62;
    private static final int NORMAL = 1;
    private static final int REFRESH = 0;
    private ImageView mArrow = null;
    private boolean mArrowUp = false;
    private boolean mFlag = false;
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            int access$0;
            super.handleMessage(message);
            switch (message.what) {
                case 0:
                    access$0 = RefreshableListView.this.mHeaderHeight;
                    break;
                case 1:
                    access$0 = 0;
                    break;
                default:
                    access$0 = 0;
                    break;
            }
            if (message.arg1 >= access$0) {
                RefreshableListView.this.setHeaderHeight(message.arg1);
                access$0 = (message.arg1 - access$0) / 10;
                if (access$0 == 0) {
                    RefreshableListView.this.mHandler.sendMessage(RefreshableListView.this.mHandler.obtainMessage(message.what, message.arg1 - 1, 0));
                } else {
                    RefreshableListView.this.mHandler.sendMessage(RefreshableListView.this.mHandler.obtainMessage(message.what, message.arg1 - access$0, 0));
                }
            }
        }
    };
    private View mHeaderContainer = null;
    private int mHeaderHeight = 0;
    private View mHeaderView = null;
    private int mHistoricalTop = 0;
    private float mHistoricalY = 0.0f;
    private int mInitialHeight = 0;
    private boolean mIsRefreshing = false;
    private OnRefreshListener mListener = null;
    private ProgressBar mProgress = null;
    private TextView mText = null;
    private float mY = 0.0f;

    public interface OnRefreshListener {
        void onRefresh(RefreshableListView refreshableListView);
    }

    public RefreshableListView(Context context) {
        super(context);
        initialize();
    }

    public RefreshableListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize();
    }

    public RefreshableListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialize();
    }

    private void initialize() {
        this.mHeaderContainer = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.messagelist_head, null);
        this.mHeaderView = this.mHeaderContainer.findViewById(R.id.refreshable_list_header);
        this.mArrow = (ImageView) this.mHeaderContainer.findViewById(R.id.refreshable_list_arrow);
        this.mProgress = (ProgressBar) this.mHeaderContainer.findViewById(R.id.refreshable_list_progress);
        this.mText = (TextView) this.mHeaderContainer.findViewById(R.id.refreshable_list_text);
        addHeaderView(this.mHeaderContainer);
        this.mHeaderHeight = (int) (62.0f * getContext().getResources().getDisplayMetrics().density);
        setHeaderHeight(0);
    }

    private void rotateArrow() {
        Drawable drawable = this.mArrow.getDrawable();
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.save();
        canvas.rotate(180.0f, ((float) canvas.getWidth()) / 2.0f, ((float) canvas.getHeight()) / 2.0f);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        canvas.restore();
        this.mArrow.setImageBitmap(createBitmap);
    }

    private void setHeaderHeight(int i) {
        if (i <= 1) {
            this.mHeaderView.setVisibility(8);
        } else {
            this.mHeaderView.setVisibility(0);
        }
        LayoutParams layoutParams = (AbsListView.LayoutParams) this.mHeaderContainer.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new AbsListView.LayoutParams(-1, -2);
        }
        layoutParams.height = i;
        this.mHeaderContainer.setLayoutParams(layoutParams);
        layoutParams = (LinearLayout.LayoutParams) this.mHeaderView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LinearLayout.LayoutParams(-1, -2);
        }
        layoutParams.topMargin = (-this.mHeaderHeight) + i;
        this.mHeaderView.setLayoutParams(layoutParams);
        if (!this.mIsRefreshing) {
            if (i > this.mHeaderHeight && !this.mArrowUp) {
                this.mArrow.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate));
                this.mText.setText("刷新数据");
                rotateArrow();
                this.mArrowUp = true;
            } else if (i < this.mHeaderHeight && this.mArrowUp) {
                this.mArrow.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate));
                this.mText.setText("下拉刷新");
                rotateArrow();
                this.mArrowUp = false;
            }
        }
    }

    private void startRefreshing() {
        this.mArrow.setVisibility(4);
        this.mProgress.setVisibility(0);
        this.mText.setText("加载...");
        this.mIsRefreshing = true;
        if (this.mListener != null) {
            this.mListener.onRefresh(this);
        }
    }

    public void completeRefreshing() {
        this.mProgress.setVisibility(4);
        this.mArrow.setVisibility(0);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, this.mHeaderHeight, 0));
        this.mIsRefreshing = false;
        invalidateViews();
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (motionEvent.getAction() == 2 && getFirstVisiblePosition() == 0) {
            float y = motionEvent.getY() - this.mHistoricalY;
            int y2 = (((int) (motionEvent.getY() - this.mY)) / 2) + this.mInitialHeight;
            if (y2 < 0) {
                y2 = z;
            }
            if (Math.abs(this.mY - motionEvent.getY()) > ((float) ViewConfiguration.get(getContext()).getScaledTouchSlop())) {
                if (y > 0.0f) {
                    if (getChildAt(z).getTop() == 0) {
                        setHeaderHeight(y2);
                        motionEvent.setAction(3);
                        this.mFlag = z;
                    }
                } else if (y < 0.0f && getChildAt(z).getTop() == 0) {
                    setHeaderHeight(y2);
                    if (!(getChildAt(1) == null || getChildAt(1).getTop() > 1 || this.mFlag)) {
                        motionEvent.setAction(z);
                        this.mFlag = true;
                    }
                }
            }
            this.mHistoricalY = motionEvent.getY();
        }
        try {
            z = super.dispatchTouchEvent(motionEvent);
        } catch (Exception e) {
        }
        return z;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                this.mHandler.removeMessages(0);
                this.mHandler.removeMessages(1);
                float y = motionEvent.getY();
                this.mHistoricalY = y;
                this.mY = y;
                if (this.mHeaderContainer.getLayoutParams() != null) {
                    this.mInitialHeight = this.mHeaderContainer.getLayoutParams().height;
                    break;
                }
                break;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 1:
                if (this.mIsRefreshing) {
                    this.mHandler.sendMessage(this.mHandler.obtainMessage(0, (((int) (motionEvent.getY() - this.mY)) / 2) + this.mInitialHeight, 0));
                } else if (this.mArrowUp) {
                    startRefreshing();
                    this.mHandler.sendMessage(this.mHandler.obtainMessage(0, (((int) (motionEvent.getY() - this.mY)) / 2) + this.mInitialHeight, 0));
                } else if (getChildAt(0).getTop() == 0) {
                    this.mHandler.sendMessage(this.mHandler.obtainMessage(1, (((int) (motionEvent.getY() - this.mY)) / 2) + this.mInitialHeight, 0));
                }
                this.mFlag = false;
                break;
            case 2:
                this.mHistoricalTop = getChildAt(0).getTop();
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean performItemClick(View view, int i, long j) {
        return i == 0 ? true : super.performItemClick(view, i - 1, j);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mListener = onRefreshListener;
    }
}
