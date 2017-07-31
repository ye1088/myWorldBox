package com.huluxia.image.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;

public class AutoRotateDrawable extends g implements b, Runnable {
    private static final int Bk = 360;
    private static final int Bl = 20;
    private int Bm;
    private boolean Bn;
    @VisibleForTesting
    float Bo;
    private boolean Bp;

    public /* synthetic */ Drawable jO() {
        return jL();
    }

    public AutoRotateDrawable(Drawable drawable, int interval) {
        this(drawable, interval, true);
    }

    public AutoRotateDrawable(Drawable drawable, int interval, boolean clockwise) {
        super((Drawable) Preconditions.checkNotNull(drawable));
        this.Bo = 0.0f;
        this.Bp = false;
        this.Bm = interval;
        this.Bn = clockwise;
    }

    public void reset() {
        this.Bo = 0.0f;
        this.Bp = false;
        unscheduleSelf(this);
        invalidateSelf();
    }

    public void Y(boolean clockwise) {
        this.Bn = clockwise;
    }

    public void draw(Canvas canvas) {
        int saveCount = canvas.save();
        Rect bounds = getBounds();
        int width = bounds.right - bounds.left;
        int height = bounds.bottom - bounds.top;
        float angle = this.Bo;
        if (!this.Bn) {
            angle = 360.0f - this.Bo;
        }
        canvas.rotate(angle, (float) (bounds.left + (width / 2)), (float) (bounds.top + (height / 2)));
        super.draw(canvas);
        canvas.restoreToCount(saveCount);
        jM();
    }

    public void run() {
        this.Bp = false;
        this.Bo += (float) jN();
        invalidateSelf();
    }

    public AutoRotateDrawable jL() {
        return new AutoRotateDrawable(e.f(getDrawable()), this.Bm, this.Bn);
    }

    private void jM() {
        if (!this.Bp) {
            this.Bp = true;
            scheduleSelf(this, SystemClock.uptimeMillis() + 20);
        }
    }

    private int jN() {
        return (int) ((20.0f / ((float) this.Bm)) * 360.0f);
    }
}
