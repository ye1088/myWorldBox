package com.huluxia.widget.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.RelativeLayout;

public class SquareLayout extends RelativeLayout implements OnPreDrawListener {
    public SquareLayout(Context context) {
        super(context);
        getViewTreeObserver().addOnPreDrawListener(this);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        getViewTreeObserver().addOnPreDrawListener(this);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getViewTreeObserver().addOnPreDrawListener(this);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public boolean onPreDraw() {
        return true;
    }
}
