package com.huluxia.framework.base.utils;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class SpeedScrollListener implements OnScrollListener {
    private static final int DEFAULT_FLYING_SPEED = 8;
    private static final int DEFAULT_FLYING_STOP_SPEED = 3;
    private final int mFlingSpeed;
    private final int mFlingStopSpeed;
    private long mPreviousEventTime;
    private int mPreviousFirstVisibleItem;
    private double mSpeed;

    public SpeedScrollListener() {
        this.mPreviousFirstVisibleItem = 0;
        this.mPreviousEventTime = 0;
        this.mSpeed = 0.0d;
        this.mFlingSpeed = 8;
        this.mFlingStopSpeed = 3;
    }

    public SpeedScrollListener(int mFlingSpeed, int mFlingStopSpeed) {
        this.mPreviousFirstVisibleItem = 0;
        this.mPreviousEventTime = 0;
        this.mSpeed = 0.0d;
        this.mFlingSpeed = mFlingSpeed;
        this.mFlingStopSpeed = mFlingStopSpeed;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case 0:
                onFlingStop();
                return;
            default:
                return;
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.mPreviousFirstVisibleItem != firstVisibleItem) {
            long currTime = System.currentTimeMillis();
            this.mSpeed = (1.0d / ((double) (currTime - this.mPreviousEventTime))) * 1000.0d;
            this.mPreviousFirstVisibleItem = firstVisibleItem;
            this.mPreviousEventTime = currTime;
            onSpeed(this.mSpeed);
            if (this.mSpeed > ((double) this.mFlingSpeed)) {
                onFling();
            }
            if (this.mSpeed < ((double) this.mFlingStopSpeed)) {
                onFlingStop();
            }
        }
    }

    public void onSpeed(double speed) {
    }

    public void onFling() {
    }

    public void onFlingStop() {
    }
}
