package com.MCWorld.framework.base.utils;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class ListScrollDistanceListener implements OnScrollListener {
    private int mFirstVisibleBottom;
    private int mFirstVisibleHeight;
    private int mFirstVisibleItem;
    private int mFirstVisibleTop;
    private boolean mListScrollStarted;
    private ScrollDistanceListener mScrollDistanceListener;
    private int mTotalScrollDistance;

    public interface ScrollDistanceListener {
        void onScrollDistanceChanged(int i, int i2);
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (view.getCount() != 0) {
            switch (scrollState) {
                case 0:
                    this.mListScrollStarted = false;
                    return;
                case 1:
                    View firstChild = view.getChildAt(0);
                    this.mFirstVisibleItem = view.getFirstVisiblePosition();
                    this.mFirstVisibleTop = firstChild.getTop();
                    this.mFirstVisibleBottom = firstChild.getBottom();
                    this.mFirstVisibleHeight = firstChild.getHeight();
                    this.mListScrollStarted = true;
                    this.mTotalScrollDistance = 0;
                    return;
                default:
                    return;
            }
        }
    }

    public int getTotalScrollDistance() {
        return this.mTotalScrollDistance;
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount != 0 && this.mListScrollStarted) {
            int delta;
            View firstChild = view.getChildAt(0);
            int firstVisibleTop = firstChild.getTop();
            int firstVisibleBottom = firstChild.getBottom();
            int firstVisibleHeight = firstChild.getHeight();
            if (firstVisibleItem > this.mFirstVisibleItem) {
                this.mFirstVisibleTop += this.mFirstVisibleHeight;
                delta = firstVisibleTop - this.mFirstVisibleTop;
            } else if (firstVisibleItem < this.mFirstVisibleItem) {
                this.mFirstVisibleBottom -= this.mFirstVisibleHeight;
                delta = firstVisibleBottom - this.mFirstVisibleBottom;
            } else {
                delta = firstVisibleBottom - this.mFirstVisibleBottom;
            }
            this.mTotalScrollDistance += delta;
            if (this.mScrollDistanceListener != null) {
                this.mScrollDistanceListener.onScrollDistanceChanged(delta, this.mTotalScrollDistance);
            }
            this.mFirstVisibleTop = firstVisibleTop;
            this.mFirstVisibleBottom = firstVisibleBottom;
            this.mFirstVisibleHeight = firstVisibleHeight;
            this.mFirstVisibleItem = firstVisibleItem;
        }
    }

    public void setScrollDistanceListener(ScrollDistanceListener listener) {
        this.mScrollDistanceListener = listener;
    }
}
