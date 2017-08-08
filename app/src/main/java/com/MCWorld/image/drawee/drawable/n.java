package com.MCWorld.image.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.MCWorld.framework.base.utils.Objects;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.drawee.drawable.o.c;

/* compiled from: ScaleTypeDrawable */
public class n extends g {
    @VisibleForTesting
    Matrix BH;
    @VisibleForTesting
    int BI = 0;
    @VisibleForTesting
    int BJ = 0;
    @VisibleForTesting
    c Ct;
    @VisibleForTesting
    Object Cu;
    @VisibleForTesting
    PointF Cv = null;
    private Matrix mTempMatrix = new Matrix();

    public n(Drawable drawable, c scaleType) {
        super((Drawable) Preconditions.checkNotNull(drawable));
        this.Ct = scaleType;
    }

    public Drawable g(Drawable newDelegate) {
        Drawable previousDelegate = super.g(newDelegate);
        jZ();
        return previousDelegate;
    }

    public c ko() {
        return this.Ct;
    }

    public void a(c scaleType) {
        if (!Objects.equal(this.Ct, scaleType)) {
            this.Ct = scaleType;
            this.Cu = null;
            jZ();
            invalidateSelf();
        }
    }

    public PointF kp() {
        return this.Cv;
    }

    public void a(PointF focusPoint) {
        if (!Objects.equal(this.Cv, focusPoint)) {
            if (this.Cv == null) {
                this.Cv = new PointF();
            }
            this.Cv.set(focusPoint);
            jZ();
            invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        jY();
        if (this.BH != null) {
            int saveCount = canvas.save();
            canvas.clipRect(getBounds());
            canvas.concat(this.BH);
            super.draw(canvas);
            canvas.restoreToCount(saveCount);
            return;
        }
        super.draw(canvas);
    }

    protected void onBoundsChange(Rect bounds) {
        jZ();
    }

    private void jY() {
        boolean underlyingChanged;
        boolean scaleTypeChanged = false;
        if (this.Ct instanceof o$l) {
            Object state = ((o$l) this.Ct).getState();
            if (state == null || !state.equals(this.Cu)) {
                scaleTypeChanged = true;
            } else {
                scaleTypeChanged = false;
            }
            this.Cu = state;
        }
        if (this.BI == getCurrent().getIntrinsicWidth() && this.BJ == getCurrent().getIntrinsicHeight()) {
            underlyingChanged = false;
        } else {
            underlyingChanged = true;
        }
        if (underlyingChanged || scaleTypeChanged) {
            jZ();
        }
    }

    @VisibleForTesting
    void jZ() {
        float f = 0.5f;
        Drawable underlyingDrawable = getCurrent();
        Rect bounds = getBounds();
        int viewWidth = bounds.width();
        int viewHeight = bounds.height();
        int underlyingWidth = underlyingDrawable.getIntrinsicWidth();
        this.BI = underlyingWidth;
        int underlyingHeight = underlyingDrawable.getIntrinsicHeight();
        this.BJ = underlyingHeight;
        if (underlyingWidth <= 0 || underlyingHeight <= 0) {
            underlyingDrawable.setBounds(bounds);
            this.BH = null;
        } else if (underlyingWidth == viewWidth && underlyingHeight == viewHeight) {
            underlyingDrawable.setBounds(bounds);
            this.BH = null;
        } else if (this.Ct == c.CE) {
            underlyingDrawable.setBounds(bounds);
            this.BH = null;
        } else {
            underlyingDrawable.setBounds(0, 0, underlyingWidth, underlyingHeight);
            c cVar = this.Ct;
            Matrix matrix = this.mTempMatrix;
            float f2 = this.Cv != null ? this.Cv.x : 0.5f;
            if (this.Cv != null) {
                f = this.Cv.y;
            }
            cVar.a(matrix, bounds, underlyingWidth, underlyingHeight, f2, f);
            this.BH = this.mTempMatrix;
        }
    }

    public void a(Matrix transform) {
        b(transform);
        jY();
        if (this.BH != null) {
            transform.preConcat(this.BH);
        }
    }
}
