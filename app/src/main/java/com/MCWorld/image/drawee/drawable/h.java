package com.MCWorld.image.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.MCWorld.framework.base.utils.Preconditions;

/* compiled from: MatrixDrawable */
public class h extends g {
    private Matrix BH;
    private int BI = 0;
    private int BJ = 0;
    private Matrix mMatrix;

    public h(Drawable drawable, Matrix matrix) {
        super((Drawable) Preconditions.checkNotNull(drawable));
        this.mMatrix = matrix;
    }

    public Drawable g(Drawable newDelegate) {
        Drawable previousDelegate = super.g(newDelegate);
        jZ();
        return previousDelegate;
    }

    public Matrix getMatrix() {
        return this.mMatrix;
    }

    public void setMatrix(Matrix matrix) {
        this.mMatrix = matrix;
        jZ();
        invalidateSelf();
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
        super.onBoundsChange(bounds);
        jZ();
    }

    private void jY() {
        if (this.BI != getCurrent().getIntrinsicWidth() || this.BJ != getCurrent().getIntrinsicHeight()) {
            jZ();
        }
    }

    private void jZ() {
        Drawable underlyingDrawable = getCurrent();
        Rect bounds = getBounds();
        int underlyingWidth = underlyingDrawable.getIntrinsicWidth();
        this.BI = underlyingWidth;
        int underlyingHeight = underlyingDrawable.getIntrinsicHeight();
        this.BJ = underlyingHeight;
        if (underlyingWidth <= 0 || underlyingHeight <= 0) {
            underlyingDrawable.setBounds(bounds);
            this.BH = null;
            return;
        }
        underlyingDrawable.setBounds(0, 0, underlyingWidth, underlyingHeight);
        this.BH = this.mMatrix;
    }

    public void a(Matrix transform) {
        super.a(transform);
        if (this.BH != null) {
            transform.preConcat(this.BH);
        }
    }
}
