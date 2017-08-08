package com.MCWorld.widget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class InnerViewPager extends ViewPager {
    private int bFA = 0;
    public ListView bFE;
    int bFF;
    private boolean bFG = true;
    private AutoViewPagerAdapter bFH;

    public InnerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.bFE == null) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case 0:
                this.bFF = (int) ev.getX();
                this.bFH = (AutoViewPagerAdapter) getAdapter();
                if (this.bFH != null) {
                    this.bFA = this.bFH.getCurrentItem();
                    this.bFH.f(false, false);
                    break;
                }
                break;
            case 1:
                this.bFH = (AutoViewPagerAdapter) getAdapter();
                if (this.bFH != null) {
                    this.bFH.setCurrentItem(this.bFA);
                    this.bFH.f(true, false);
                    break;
                }
                break;
            case 2:
                break;
            case 3:
                this.bFH = (AutoViewPagerAdapter) getAdapter();
                if (this.bFH != null) {
                    this.bFH.setCurrentItem(this.bFA);
                    this.bFH.f(true, false);
                    break;
                }
                break;
            default:
                this.bFH = (AutoViewPagerAdapter) getAdapter();
                if (this.bFH != null) {
                    this.bFH.setCurrentItem(this.bFA);
                    this.bFH.f(true, false);
                    break;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.bFE != null) {
            switch (ev.getAction()) {
                case 0:
                    this.bFG = false;
                    break;
                case 1:
                    this.bFG = true;
                    break;
                case 2:
                    int x = (int) ev.getX();
                    if (this.bFF != x) {
                        setParentScrollAble(false);
                    }
                    this.bFF = x;
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    private void setParentScrollAble(boolean flag) {
        this.bFE.requestDisallowInterceptTouchEvent(!flag);
    }

    public void setParentView(ListView v) {
        this.bFE = v;
    }

    public void setCurrentItem(int index) {
        if (this.bFG) {
            super.setCurrentItem(index);
        }
    }
}
