package com.MCWorld.framework.base.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.RelativeLayout;

public class WindowInsetRelativeLayout extends RelativeLayout {
    private int[] mInsets;

    public WindowInsetRelativeLayout(Context context) {
        super(context);
        this.mInsets = new int[4];
    }

    public WindowInsetRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInsets = new int[4];
    }

    public WindowInsetRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mInsets = new int[4];
    }

    @TargetApi(21)
    public WindowInsetRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mInsets = new int[4];
    }

    public final WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (VERSION.SDK_INT < 20) {
            return insets;
        }
        this.mInsets[0] = insets.getSystemWindowInsetLeft();
        this.mInsets[1] = insets.getSystemWindowInsetTop();
        this.mInsets[2] = insets.getSystemWindowInsetRight();
        return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(0, 0, 0, insets.getSystemWindowInsetBottom()));
    }

    protected final boolean fitSystemWindows(Rect insets) {
        if (VERSION.SDK_INT == 19) {
            this.mInsets[0] = insets.left;
            this.mInsets[1] = insets.top;
            this.mInsets[2] = insets.right;
            insets.left = 0;
            insets.top = 0;
            insets.right = 0;
        }
        return super.fitSystemWindows(insets);
    }
}
