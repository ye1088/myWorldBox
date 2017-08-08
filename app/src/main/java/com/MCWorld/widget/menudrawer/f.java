package com.MCWorld.widget.menudrawer;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;

/* compiled from: SlideDrawable */
public class f extends Drawable implements Callback {
    private Drawable bCf;
    private boolean bCg;
    private float mOffset;
    private final Rect mTmpRect = new Rect();

    public f(Drawable wrapped) {
        this.bCf = wrapped;
    }

    public void setOffset(float offset) {
        this.mOffset = offset;
        invalidateSelf();
    }

    public float PB() {
        return this.mOffset;
    }

    void dn(boolean isRtl) {
        this.bCg = isRtl;
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        this.bCf.copyBounds(this.mTmpRect);
        canvas.save();
        if (this.bCg) {
            canvas.translate((((float) this.mTmpRect.width()) * 0.33333334f) * this.mOffset, 0.0f);
        } else {
            canvas.translate((((float) this.mTmpRect.width()) * 0.33333334f) * (-this.mOffset), 0.0f);
        }
        this.bCf.draw(canvas);
        canvas.restore();
    }

    public void setChangingConfigurations(int configs) {
        this.bCf.setChangingConfigurations(configs);
    }

    public int getChangingConfigurations() {
        return this.bCf.getChangingConfigurations();
    }

    public void setDither(boolean dither) {
        this.bCf.setDither(dither);
    }

    public void setFilterBitmap(boolean filter) {
        this.bCf.setFilterBitmap(filter);
    }

    public void setAlpha(int alpha) {
        this.bCf.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.bCf.setColorFilter(cf);
    }

    public void setColorFilter(int color, Mode mode) {
        this.bCf.setColorFilter(color, mode);
    }

    public void clearColorFilter() {
        this.bCf.clearColorFilter();
    }

    public boolean isStateful() {
        return this.bCf.isStateful();
    }

    public boolean setState(int[] stateSet) {
        return this.bCf.setState(stateSet);
    }

    public int[] getState() {
        return this.bCf.getState();
    }

    public Drawable getCurrent() {
        return this.bCf.getCurrent();
    }

    public boolean setVisible(boolean visible, boolean restart) {
        return super.setVisible(visible, restart);
    }

    public int getOpacity() {
        return this.bCf.getOpacity();
    }

    public Region getTransparentRegion() {
        return this.bCf.getTransparentRegion();
    }

    protected boolean onStateChange(int[] state) {
        this.bCf.setState(state);
        return super.onStateChange(state);
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.bCf.setBounds(bounds);
    }

    public int getIntrinsicWidth() {
        return this.bCf.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        return this.bCf.getIntrinsicHeight();
    }

    public int getMinimumWidth() {
        return this.bCf.getMinimumWidth();
    }

    public int getMinimumHeight() {
        return this.bCf.getMinimumHeight();
    }

    public boolean getPadding(Rect padding) {
        return this.bCf.getPadding(padding);
    }

    public ConstantState getConstantState() {
        return super.getConstantState();
    }

    public void invalidateDrawable(Drawable who) {
        if (who == this.bCf) {
            invalidateSelf();
        }
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (who == this.bCf) {
            scheduleSelf(what, when);
        }
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (who == this.bCf) {
            unscheduleSelf(what);
        }
    }
}
