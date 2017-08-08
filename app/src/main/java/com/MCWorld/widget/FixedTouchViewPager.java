package com.MCWorld.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.MCWorld.framework.base.log.HLog;

public class FixedTouchViewPager extends ViewPager {
    public FixedTouchViewPager(Context context) {
        super(context);
    }

    public FixedTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        boolean z = false;
        try {
            z = super.onTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            HLog.error(this, "onTouchEvent fix touch viewpager error happens, ev = " + ev, new Object[z]);
        }
        return z;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean z = false;
        try {
            z = super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            HLog.error(this, "onInterceptTouchEvent fix touch viewpager error happens, ev= " + ev, new Object[z]);
        }
        return z;
    }
}
