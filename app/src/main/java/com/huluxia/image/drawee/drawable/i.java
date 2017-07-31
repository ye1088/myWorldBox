package com.huluxia.image.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;

/* compiled from: OrientedDrawable */
public class i extends g {
    @VisibleForTesting
    final Matrix BK;
    private final RectF BL = new RectF();
    private final Matrix mTempMatrix = new Matrix();
    private int wN;

    public i(Drawable drawable, int rotationAngle) {
        super(drawable);
        Preconditions.checkArgument(rotationAngle % 90 == 0);
        this.BK = new Matrix();
        this.wN = rotationAngle;
    }

    public void draw(Canvas canvas) {
        if (this.wN <= 0) {
            super.draw(canvas);
            return;
        }
        int saveCount = canvas.save();
        canvas.concat(this.BK);
        super.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    public int getIntrinsicWidth() {
        return this.wN % 180 == 0 ? super.getIntrinsicWidth() : super.getIntrinsicHeight();
    }

    public int getIntrinsicHeight() {
        return this.wN % 180 == 0 ? super.getIntrinsicHeight() : super.getIntrinsicWidth();
    }

    protected void onBoundsChange(Rect bounds) {
        Drawable underlyingDrawable = getCurrent();
        if (this.wN > 0) {
            this.BK.setRotate((float) this.wN, (float) bounds.centerX(), (float) bounds.centerY());
            this.mTempMatrix.reset();
            this.BK.invert(this.mTempMatrix);
            this.BL.set(bounds);
            this.mTempMatrix.mapRect(this.BL);
            underlyingDrawable.setBounds((int) this.BL.left, (int) this.BL.top, (int) this.BL.right, (int) this.BL.bottom);
            return;
        }
        underlyingDrawable.setBounds(bounds);
    }

    public void a(Matrix transform) {
        b(transform);
        if (!this.BK.isIdentity()) {
            transform.preConcat(this.BK);
        }
    }
}
