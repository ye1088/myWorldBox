package com.MCWorld.widget.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridViewNotScroll extends GridView {
    public GridViewNotScroll(Context context) {
        super(context);
    }

    public GridViewNotScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewNotScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;
        if (getLayoutParams().height == -2) {
            heightSpec = MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE);
        } else {
            heightSpec = heightMeasureSpec;
        }
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
