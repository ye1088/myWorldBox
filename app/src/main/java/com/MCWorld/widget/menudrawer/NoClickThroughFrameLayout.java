package com.MCWorld.widget.menudrawer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

class NoClickThroughFrameLayout extends BuildLayerFrameLayout {
    public NoClickThroughFrameLayout(Context context) {
        super(context);
    }

    public NoClickThroughFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoClickThroughFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
