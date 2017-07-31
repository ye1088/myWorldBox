package com.huluxia.widget.menudrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;

public class StaticDrawer extends MenuDrawer {

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] bAD = new int[Position.values().length];

        static {
            try {
                bAD[Position.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                bAD[Position.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                bAD[Position.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                bAD[Position.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public StaticDrawer(Context context) {
        super(context);
    }

    public StaticDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StaticDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void a(Context context, AttributeSet attrs, int defStyle) {
        super.a(context, attrs, defStyle);
        super.addView(this.bBk, -1, new LayoutParams(-1, -1));
        super.addView(this.bBl, -1, new LayoutParams(-1, -1));
        this.bBH = true;
    }

    protected void g(Canvas canvas) {
    }

    protected void me(int offsetPixels) {
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        int height = b - t;
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
            case 1:
                this.bBk.layout(0, 0, this.bBm, height);
                this.bBl.layout(this.bBm, 0, width, height);
                return;
            case 2:
                this.bBk.layout(width - this.bBm, 0, width, height);
                this.bBl.layout(0, 0, width - this.bBm, height);
                return;
            case 3:
                this.bBk.layout(0, 0, width, this.bBm);
                this.bBl.layout(0, this.bBm, width, height);
                return;
            case 4:
                this.bBk.layout(0, height - this.bBm, width, height);
                this.bBl.layout(0, 0, width, height - this.bBm);
                return;
            default:
                return;
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == 0 || heightMode == 0) {
            throw new IllegalStateException("Must measure with an exact size");
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
            case 1:
            case 2:
                int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, 1073741824);
                int menuWidth = this.bBm;
                int menuWidthMeasureSpec = MeasureSpec.makeMeasureSpec(menuWidth, 1073741824);
                this.bBl.measure(MeasureSpec.makeMeasureSpec(width - menuWidth, 1073741824), childHeightMeasureSpec);
                this.bBk.measure(menuWidthMeasureSpec, childHeightMeasureSpec);
                break;
            case 3:
            case 4:
                int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, 1073741824);
                int menuHeight = this.bBm;
                int menuHeightMeasureSpec = MeasureSpec.makeMeasureSpec(menuHeight, 1073741824);
                this.bBl.measure(childWidthMeasureSpec, MeasureSpec.makeMeasureSpec(height - menuHeight, 1073741824));
                this.bBk.measure(childWidthMeasureSpec, menuHeightMeasureSpec);
                break;
        }
        setMeasuredDimension(width, height);
    }

    public void dk(boolean animate) {
    }

    public void dl(boolean animate) {
    }

    public void dm(boolean animate) {
    }

    public boolean isMenuVisible() {
        return true;
    }

    public void setMenuSize(int size) {
        this.bBm = size;
        requestLayout();
        invalidate();
    }

    public void setOffsetMenuEnabled(boolean offsetMenu) {
    }

    public boolean getOffsetMenuEnabled() {
        return false;
    }

    public void peekDrawer() {
    }

    public void bL(long delay) {
    }

    public void k(long startDelay, long delay) {
    }

    public void setHardwareLayerEnabled(boolean enabled) {
    }

    public int getTouchMode() {
        return 0;
    }

    public void setTouchMode(int mode) {
    }

    public void setTouchBezelSize(int size) {
    }

    public int getTouchBezelSize() {
        return 0;
    }

    public void setSlideDrawable(int drawableRes) {
    }

    public void setSlideDrawable(Drawable drawable) {
    }

    public void setupUpIndicator(Activity activity) {
    }

    public void setDrawerIndicatorEnabled(boolean enabled) {
    }

    public boolean isDrawerIndicatorEnabled() {
        return false;
    }
}
