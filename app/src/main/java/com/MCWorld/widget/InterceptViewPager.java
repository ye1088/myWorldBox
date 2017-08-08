package com.MCWorld.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;
import com.MCWorld.framework.base.widget.pager.SelectedViewPager;

public class InterceptViewPager extends SelectedViewPager {
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mTouchSlop;

    public InterceptViewPager(Context context) {
        super(context);
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
    }

    public InterceptViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
    }

    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v == this || !(v instanceof ViewPager)) {
            if (v != this && (v instanceof HorizontalScrollView)) {
                if (ViewCompat.canScrollHorizontally(v, -1) && dx > 0) {
                    return false;
                }
                if (ViewCompat.canScrollHorizontally(v, 1) && dx < 0) {
                    return false;
                }
            }
            return super.canScroll(v, checkV, dx, x, y);
        } else if (((ViewPager) v).getCurrentItem() == 0 && dx > 0) {
            return false;
        } else {
            if (((ViewPager) v).getAdapter() != null) {
                boolean scrollEnd;
                if (((((ViewPager) v).getAdapter().getCount() - 1) - ((ViewPager) v).getCurrentItem()) - ((int) (1.0f / ((ViewPager) v).getAdapter().getPageWidth(0))) < 0) {
                    scrollEnd = true;
                } else {
                    scrollEnd = false;
                }
                if (scrollEnd && dx < 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
