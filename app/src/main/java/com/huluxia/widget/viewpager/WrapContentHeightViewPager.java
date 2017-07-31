package com.huluxia.widget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class WrapContentHeightViewPager extends ViewPager {
    private int bFL = 1;

    public WrapContentHeightViewPager(Context context) {
        super(context);
    }

    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, 0));
            int h = child.getMeasuredHeight() * this.bFL;
            if (h > height) {
                height = h;
            }
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, 1073741824));
    }

    private int a(int measureSpec, View view) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == 1073741824) {
            return specSize;
        }
        if (view != null) {
            result = view.getMeasuredHeight();
        }
        if (specMode == Integer.MIN_VALUE) {
            return Math.min(result, specSize);
        }
        return result;
    }

    public void setShowHighMultiple(int showHighMultiple) {
        this.bFL = showHighMultiple;
    }
}
