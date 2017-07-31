package com.huluxia.widget.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

@SuppressLint({"NewApi"})
public class InnerScrollViewExtend extends ScrollView {
    public View bzP;
    public ScrollView bzQ;
    int bzR;
    private int bzS = 0;
    int bzT = 10;
    int bzU;

    public InnerScrollViewExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void resume() {
        overScrollBy(0, -this.bzS, 0, getScrollY(), 0, getScrollRange(), 0, 0, true);
        this.bzS = 0;
    }

    public void v(View targetView) {
        int delatY = (targetView.getTop() - this.bzT) - getScrollY();
        this.bzS = delatY;
        overScrollBy(0, delatY, 0, getScrollY(), 0, getScrollRange(), 0, 0, true);
    }

    private int getScrollRange() {
        if (getChildCount() > 0) {
            return Math.max(0, getChildAt(0).getHeight() - getHeight());
        }
        return 0;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.bzQ == null) {
            return super.onInterceptTouchEvent(ev);
        }
        if (ev.getAction() == 0) {
            this.bzR = (int) ev.getY();
            setParentScrollAble(false);
            return super.onInterceptTouchEvent(ev);
        }
        if (ev.getAction() == 1) {
            setParentScrollAble(true);
        } else if (ev.getAction() == 2) {
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        View child = getChildAt(0);
        if (this.bzQ != null) {
            if (ev.getAction() == 2) {
                int height = child.getMeasuredHeight() - getMeasuredHeight();
                int scrollY = getScrollY();
                float yDistance = (float) Math.abs(scrollY);
                float xDistance = (float) Math.abs(getScrollX());
                int y = (int) ev.getY();
                if (this.bzR < y) {
                    if (scrollY <= 0) {
                        setParentScrollAble(true);
                        return false;
                    }
                    setParentScrollAble(false);
                } else if (this.bzR > y) {
                    if (yDistance > 2.0f * xDistance) {
                        if (scrollY < height) {
                            setParentScrollAble(true);
                            return false;
                        }
                        setParentScrollAble(false);
                    } else if (scrollY < height) {
                        setParentScrollAble(false);
                    } else {
                        setParentScrollAble(true);
                        return false;
                    }
                }
                this.bzR = y;
            } else if (ev.getAction() == 1) {
                this.bzU = (int) ev.getY();
                if (this.bzR < this.bzU) {
                    this.bzP.setVisibility(0);
                } else if (this.bzR > this.bzU) {
                    this.bzP.setVisibility(8);
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    private void setParentScrollAble(boolean flag) {
        this.bzQ.requestDisallowInterceptTouchEvent(!flag);
    }

    public void setParentView(ScrollView v) {
        this.bzQ = v;
    }

    public void setParentHeader(View v) {
        this.bzP = v;
    }
}
