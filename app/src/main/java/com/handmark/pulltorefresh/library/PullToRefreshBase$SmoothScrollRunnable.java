package com.handmark.pulltorefresh.library;

import android.view.animation.Interpolator;
import com.handmark.pulltorefresh.library.internal.ViewCompat;

final class PullToRefreshBase$SmoothScrollRunnable implements Runnable {
    private boolean mContinueRunning = true;
    private int mCurrentY = -1;
    private final long mDuration;
    private final Interpolator mInterpolator;
    private PullToRefreshBase$OnSmoothScrollFinishedListener mListener;
    private final int mScrollFromY;
    private final int mScrollToY;
    private long mStartTime = -1;
    final /* synthetic */ PullToRefreshBase this$0;

    public PullToRefreshBase$SmoothScrollRunnable(PullToRefreshBase this$0, int fromY, int toY, long duration, PullToRefreshBase$OnSmoothScrollFinishedListener listener) {
        this.this$0 = this$0;
        this.mScrollFromY = fromY;
        this.mScrollToY = toY;
        this.mInterpolator = PullToRefreshBase.access$200(this$0);
        this.mDuration = duration;
        this.mListener = listener;
    }

    public void run() {
        if (this.mStartTime == -1) {
            this.mStartTime = System.currentTimeMillis();
        } else {
            this.mCurrentY = this.mScrollFromY - Math.round(((float) (this.mScrollFromY - this.mScrollToY)) * this.mInterpolator.getInterpolation(((float) Math.max(Math.min(((System.currentTimeMillis() - this.mStartTime) * 1000) / this.mDuration, 1000), 0)) / 1000.0f));
            this.this$0.setHeaderScroll(this.mCurrentY);
        }
        if (this.mContinueRunning && this.mScrollToY != this.mCurrentY) {
            ViewCompat.postOnAnimation(this.this$0, this);
        } else if (this.mListener != null) {
            this.mListener.onSmoothScrollFinished();
        }
    }

    public void stop() {
        this.mContinueRunning = false;
        this.this$0.removeCallbacks(this);
    }
}
