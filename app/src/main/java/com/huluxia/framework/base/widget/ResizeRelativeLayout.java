package com.huluxia.framework.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class ResizeRelativeLayout extends RelativeLayout {
    private OnSizeChangedListener listener;

    public void setListener(OnSizeChangedListener l) {
        this.listener = l;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (this.listener != null) {
            this.listener.onSizeChanged(w, h, oldw, oldh);
        }
    }

    public ResizeRelativeLayout(Context context) {
        super(context);
    }

    public ResizeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizeRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
