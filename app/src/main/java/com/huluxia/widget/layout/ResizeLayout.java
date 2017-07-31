package com.huluxia.widget.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ResizeLayout extends LinearLayout {
    private a bzO;

    public interface a {
        void A(int i, int i2, int i3, int i4);
    }

    public void setOnResizeListener(a l) {
        this.bzO = l;
    }

    public ResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.bzO != null) {
            this.bzO.A(w, h, oldw, oldh);
        }
    }
}
