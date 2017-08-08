package com.MCWorld.widget.pulltorefresh;

import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

final class PullToRefreshListView$d implements Runnable {
    final /* synthetic */ PullToRefreshListView bEM;
    private boolean mContinueRunning = true;
    private int mCurrentY = -1;
    private long mDuration;
    private Interpolator mInterpolator = new DecelerateInterpolator();
    private int mScrollFromY;
    private int mScrollToY;
    private long mStartTime = -1;

    public PullToRefreshListView$d(PullToRefreshListView this$0, int fromY, int toY, long duration) {
        this.bEM = this$0;
        this.mScrollFromY = fromY;
        this.mScrollToY = toY;
        this.mDuration = duration;
    }

    public void run() {
        if (this.mStartTime == -1) {
            this.mStartTime = System.currentTimeMillis();
        } else {
            this.mCurrentY = this.mScrollFromY - Math.round(((float) (this.mScrollFromY - this.mScrollToY)) * this.mInterpolator.getInterpolation(((float) Math.max(Math.min(((System.currentTimeMillis() - this.mStartTime) * 1000) / this.mDuration, 1000), 0)) / 1000.0f));
            this.bEM.bEv.setScroll(this.mCurrentY);
        }
        if (this.mContinueRunning && this.mScrollToY != this.mCurrentY) {
            this.bEM.postDelayed(this, 16);
        }
    }

    public void stop() {
        this.mContinueRunning = false;
        this.bEM.removeCallbacks(this);
    }
}
