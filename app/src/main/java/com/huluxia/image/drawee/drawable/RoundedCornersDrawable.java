package com.huluxia.image.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;
import java.util.Arrays;

public class RoundedCornersDrawable extends g implements k {
    private final Path BM = new Path();
    private boolean BS = false;
    @VisibleForTesting
    final float[] BV = new float[8];
    private float Cg = 0.0f;
    private int Ch = 0;
    private float Ci = 0.0f;
    private final Path Cj = new Path();
    private final float[] Cn = new float[8];
    @VisibleForTesting
    Type Cp = Type.OVERLAY_COLOR;
    private int Cq = 0;
    private final RectF Cr = new RectF();
    @VisibleForTesting
    final Paint mPaint = new Paint(1);

    public enum Type {
        OVERLAY_COLOR,
        CLIPPING
    }

    public RoundedCornersDrawable(Drawable drawable) {
        super((Drawable) Preconditions.checkNotNull(drawable));
    }

    public void a(Type type) {
        this.Cp = type;
        invalidateSelf();
    }

    public void ab(boolean isCircle) {
        this.BS = isCircle;
        kl();
        invalidateSelf();
    }

    public boolean ke() {
        return this.BS;
    }

    public void setRadius(float radius) {
        Arrays.fill(this.Cn, radius);
        kl();
        invalidateSelf();
    }

    public void a(float[] radii) {
        if (radii == null) {
            Arrays.fill(this.Cn, 0.0f);
        } else {
            boolean z;
            if (radii.length == 8) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "radii should have exactly 8 values");
            System.arraycopy(radii, 0, this.Cn, 0, 8);
        }
        kl();
        invalidateSelf();
    }

    public float[] kf() {
        return this.Cn;
    }

    public void bQ(int overlayColor) {
        this.Cq = overlayColor;
        invalidateSelf();
    }

    public int kn() {
        return this.Cq;
    }

    public void a(int color, float width) {
        this.Ch = color;
        this.Cg = width;
        kl();
        invalidateSelf();
    }

    public int kg() {
        return this.Ch;
    }

    public float kh() {
        return this.Cg;
    }

    public void g(float padding) {
        this.Ci = padding;
        kl();
        invalidateSelf();
    }

    public float ki() {
        return this.Ci;
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        kl();
    }

    private void kl() {
        this.BM.reset();
        this.Cj.reset();
        this.Cr.set(getBounds());
        this.Cr.inset(this.Ci, this.Ci);
        if (this.BS) {
            this.BM.addCircle(this.Cr.centerX(), this.Cr.centerY(), Math.min(this.Cr.width(), this.Cr.height()) / 2.0f, Direction.CW);
        } else {
            this.BM.addRoundRect(this.Cr, this.Cn, Direction.CW);
        }
        this.Cr.inset(-this.Ci, -this.Ci);
        this.Cr.inset(this.Cg / 2.0f, this.Cg / 2.0f);
        if (this.BS) {
            this.Cj.addCircle(this.Cr.centerX(), this.Cr.centerY(), Math.min(this.Cr.width(), this.Cr.height()) / 2.0f, Direction.CW);
        } else {
            for (int i = 0; i < this.BV.length; i++) {
                this.BV[i] = (this.Cn[i] + this.Ci) - (this.Cg / 2.0f);
            }
            this.Cj.addRoundRect(this.Cr, this.BV, Direction.CW);
        }
        this.Cr.inset((-this.Cg) / 2.0f, (-this.Cg) / 2.0f);
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        switch (this.Cp) {
            case CLIPPING:
                int saveCount = canvas.save();
                this.BM.setFillType(FillType.EVEN_ODD);
                canvas.clipPath(this.BM);
                super.draw(canvas);
                canvas.restoreToCount(saveCount);
                break;
            case OVERLAY_COLOR:
                super.draw(canvas);
                this.mPaint.setColor(this.Cq);
                this.mPaint.setStyle(Style.FILL);
                this.BM.setFillType(FillType.INVERSE_EVEN_ODD);
                canvas.drawPath(this.BM, this.mPaint);
                if (this.BS) {
                    float paddingH = (((float) (bounds.width() - bounds.height())) + this.Cg) / 2.0f;
                    float paddingV = (((float) (bounds.height() - bounds.width())) + this.Cg) / 2.0f;
                    if (paddingH > 0.0f) {
                        canvas.drawRect((float) bounds.left, (float) bounds.top, ((float) bounds.left) + paddingH, (float) bounds.bottom, this.mPaint);
                        canvas.drawRect(((float) bounds.right) - paddingH, (float) bounds.top, (float) bounds.right, (float) bounds.bottom, this.mPaint);
                    }
                    if (paddingV > 0.0f) {
                        canvas.drawRect((float) bounds.left, (float) bounds.top, (float) bounds.right, ((float) bounds.top) + paddingV, this.mPaint);
                        canvas.drawRect((float) bounds.left, ((float) bounds.bottom) - paddingV, (float) bounds.right, (float) bounds.bottom, this.mPaint);
                        break;
                    }
                }
                break;
        }
        if (this.Ch != 0) {
            this.mPaint.setStyle(Style.STROKE);
            this.mPaint.setColor(this.Ch);
            this.mPaint.setStrokeWidth(this.Cg);
            this.BM.setFillType(FillType.EVEN_ODD);
            canvas.drawPath(this.Cj, this.mPaint);
        }
    }
}
