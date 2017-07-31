package com.huluxia.framework.base.widget.pager;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ScrollEnabledViewPager extends ViewPager {
    private boolean isPagingEnabled;
    private View mNotScroll;

    public ScrollEnabledViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isPagingEnabled = true;
        this.isPagingEnabled = true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.isPagingEnabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.mNotScroll != null) {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            int[] location = new int[2];
            this.mNotScroll.getLocationOnScreen(location);
            if (new Rect(location[0], location[1], location[0] + this.mNotScroll.getWidth(), location[1] + this.mNotScroll.getHeight()).contains(x, y)) {
                this.isPagingEnabled = false;
            }
        }
        if (this.isPagingEnabled) {
            return super.onInterceptTouchEvent(event);
        }
        if (this.isPagingEnabled || event.getAction() != 1) {
            return false;
        }
        this.isPagingEnabled = true;
        return false;
    }

    public void setNotScrollId(int viewId) {
        this.mNotScroll = findViewById(viewId);
        if (this.mNotScroll != null) {
        }
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
}
