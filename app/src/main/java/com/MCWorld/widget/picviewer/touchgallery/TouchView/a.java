package com.MCWorld.widget.picviewer.touchgallery.TouchView;

import android.view.MotionEvent;

/* compiled from: EclairMotionEvent */
public class a extends b {
    protected a(MotionEvent event) {
        super(event);
    }

    public float getX(int pointerIndex) {
        return this.bDW.getX(pointerIndex);
    }

    public float getY(int pointerIndex) {
        return this.bDW.getY(pointerIndex);
    }

    public int getPointerCount() {
        return this.bDW.getPointerCount();
    }

    public int getPointerId(int pointerIndex) {
        return this.bDW.getPointerId(pointerIndex);
    }
}
