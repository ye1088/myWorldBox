package com.huluxia.widget.picviewer.touchgallery.TouchView;

import android.view.MotionEvent;

/* compiled from: WrapMotionEvent */
public class b {
    protected MotionEvent bDW;

    protected b(MotionEvent event) {
        this.bDW = event;
    }

    public static b f(MotionEvent event) {
        try {
            return new a(event);
        } catch (VerifyError e) {
            return new b(event);
        }
    }

    public int getAction() {
        return this.bDW.getAction();
    }

    public float getX() {
        return this.bDW.getX();
    }

    public float getX(int pointerIndex) {
        mn(pointerIndex);
        return getX();
    }

    public float getY() {
        return this.bDW.getY();
    }

    public float getY(int pointerIndex) {
        mn(pointerIndex);
        return getY();
    }

    public int getPointerCount() {
        return 1;
    }

    public int getPointerId(int pointerIndex) {
        mn(pointerIndex);
        return 0;
    }

    private void mn(int pointerIndex) {
        if (pointerIndex > 0) {
            throw new IllegalArgumentException("Invalid pointer index for Donut/Cupcake");
        }
    }
}
