package com.MCWorld.image.drawee.drawable;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;

/* compiled from: ForwardingDrawable */
public class g extends Drawable implements Callback, c, q, r {
    private static final Matrix BG = new Matrix();
    private Drawable BF;
    protected r Bc;
    private final d Bd = new d();

    public g(Drawable drawable) {
        this.BF = drawable;
        e.a(this.BF, this, this);
    }

    public Drawable g(Drawable newDelegate) {
        Drawable previousDelegate = h(newDelegate);
        invalidateSelf();
        return previousDelegate;
    }

    protected Drawable h(Drawable newDelegate) {
        Drawable previousDelegate = this.BF;
        e.a(previousDelegate, null, null);
        e.a(newDelegate, null, null);
        e.a(newDelegate, this.Bd);
        e.b(newDelegate, this);
        e.a(newDelegate, this, this);
        this.BF = newDelegate;
        return previousDelegate;
    }

    public int getOpacity() {
        return this.BF.getOpacity();
    }

    public void setAlpha(int alpha) {
        this.Bd.setAlpha(alpha);
        this.BF.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.Bd.setColorFilter(colorFilter);
        this.BF.setColorFilter(colorFilter);
    }

    public void setDither(boolean dither) {
        this.Bd.setDither(dither);
        this.BF.setDither(dither);
    }

    public void setFilterBitmap(boolean filterBitmap) {
        this.Bd.setFilterBitmap(filterBitmap);
        this.BF.setFilterBitmap(filterBitmap);
    }

    public boolean setVisible(boolean visible, boolean restart) {
        super.setVisible(visible, restart);
        return this.BF.setVisible(visible, restart);
    }

    protected void onBoundsChange(Rect bounds) {
        this.BF.setBounds(bounds);
    }

    public boolean isStateful() {
        return this.BF.isStateful();
    }

    protected boolean onStateChange(int[] state) {
        return this.BF.setState(state);
    }

    protected boolean onLevelChange(int level) {
        return this.BF.setLevel(level);
    }

    public void draw(Canvas canvas) {
        this.BF.draw(canvas);
    }

    public int getIntrinsicWidth() {
        return this.BF.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        return this.BF.getIntrinsicHeight();
    }

    public boolean getPadding(Rect padding) {
        return this.BF.getPadding(padding);
    }

    public Drawable mutate() {
        this.BF.mutate();
        return this;
    }

    public Drawable getCurrent() {
        return this.BF;
    }

    public Drawable d(Drawable newDrawable) {
        return g(newDrawable);
    }

    public Drawable getDrawable() {
        return getCurrent();
    }

    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        scheduleSelf(what, when);
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        unscheduleSelf(what);
    }

    public void a(r transformCallback) {
        this.Bc = transformCallback;
    }

    protected void b(Matrix transform) {
        if (this.Bc != null) {
            this.Bc.a(transform);
        } else {
            transform.reset();
        }
    }

    public void a(Matrix transform) {
        b(transform);
    }

    public void a(RectF bounds) {
        if (this.Bc != null) {
            this.Bc.a(bounds);
        } else {
            bounds.set(getBounds());
        }
    }

    public void b(RectF outBounds) {
        b(BG);
        outBounds.set(getBounds());
        BG.mapRect(outBounds);
    }

    @TargetApi(21)
    public void setHotspot(float x, float y) {
        this.BF.setHotspot(x, y);
    }
}
