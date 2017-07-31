package com.huluxia.widget.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class InnerPullToRefreshListView extends PullToRefreshListView {
    public View bzP;
    public ScrollView bzQ;
    int bzR;

    public InnerPullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.bzQ == null) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case 0:
                this.bzR = (int) ev.getY();
                setParentScrollAble(false);
                break;
            case 1:
            case 3:
                setParentScrollAble(true);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.bzQ != null && ev.getAction() == 2) {
            int y = (int) ev.getY();
            if (this.bzR < y) {
                if (getFirstVisiblePosition() == 0) {
                    this.bzP.setVisibility(0);
                }
                setParentScrollAble(false);
            } else if (this.bzR > y) {
                if (getFirstVisiblePosition() > 1) {
                    this.bzP.setVisibility(8);
                }
                setParentScrollAble(false);
            }
            this.bzR = y;
        }
        return super.onTouchEvent(ev);
    }

    private void setParentScrollAble(boolean flag) {
        this.bzQ.requestDisallowInterceptTouchEvent(!flag);
    }

    public void setParentScrollView(ScrollView v) {
        this.bzQ = v;
    }

    public void setParentHeader(View v) {
        this.bzP = v;
    }
}
