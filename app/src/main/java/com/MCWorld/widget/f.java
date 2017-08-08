package com.MCWorld.widget;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;

/* compiled from: ScrollDetector */
public abstract class f extends SimpleOnGestureListener implements OnTouchListener {
    private final int buj;
    private boolean buk;
    private final GestureDetector mDetector;
    private float mDownY;
    private boolean mIgnore;

    public abstract void GZ();

    public abstract void Ha();

    public void setIgnore(boolean ignore) {
        this.mIgnore = ignore;
    }

    public f(Context context) {
        this.mDetector = new GestureDetector(context, this);
        this.buj = bC(context);
    }

    protected int bC(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean onTouch(View v, MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return false;
    }

    public boolean onDown(MotionEvent e) {
        this.mDownY = e.getY();
        return false;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        boolean z = true;
        if (!this.mIgnore) {
            boolean z2;
            boolean z3 = this.buk;
            if (distanceY > 0.0f) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z3 != z2) {
                if (this.buk) {
                    z = false;
                }
                this.buk = z;
                this.mDownY = e2.getY();
            }
            float distance = this.mDownY - e2.getY();
            if (distance < ((float) (-this.buj))) {
                GZ();
            } else if (distance > ((float) this.buj)) {
                Ha();
            }
        }
        return false;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!this.mIgnore) {
            if (velocityY < 0.0f) {
                Ha();
            } else if (velocityY > 0.0f) {
                GZ();
            }
        }
        return false;
    }
}
