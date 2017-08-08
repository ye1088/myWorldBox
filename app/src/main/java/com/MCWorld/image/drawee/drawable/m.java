package com.MCWorld.image.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import java.util.Arrays;

/* compiled from: RoundedColorDrawable */
public class m extends Drawable implements k {
    @VisibleForTesting
    final Path BM;
    private boolean BS;
    @VisibleForTesting
    final float[] BV;
    private float Cg;
    private int Ch;
    private float Ci;
    @VisibleForTesting
    final Path Cj;
    private final float[] Cn;
    private final RectF Co;
    private int mAlpha;
    private int mColor;
    @VisibleForTesting
    final Paint mPaint;

    public m(int color) {
        this.Cn = new float[8];
        this.BV = new float[8];
        this.mPaint = new Paint(1);
        this.BS = false;
        this.Cg = 0.0f;
        this.Ci = 0.0f;
        this.Ch = 0;
        this.BM = new Path();
        this.Cj = new Path();
        this.mColor = 0;
        this.Co = new RectF();
        this.mAlpha = 255;
        setColor(color);
    }

    public static m a(ColorDrawable colorDrawable) {
        return new m(colorDrawable.getColor());
    }

    public m(float[] radii, int color) {
        this(color);
        a(radii);
    }

    public m(float radius, int color) {
        this(color);
        setRadius(radius);
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        kl();
    }

    public void draw(Canvas canvas) {
        this.mPaint.setColor(e.r(this.mColor, this.mAlpha));
        this.mPaint.setStyle(Style.FILL);
        canvas.drawPath(this.BM, this.mPaint);
        if (this.Cg != 0.0f) {
            this.mPaint.setColor(e.r(this.Ch, this.mAlpha));
            this.mPaint.setStyle(Style.STROKE);
            this.mPaint.setStrokeWidth(this.Cg);
            canvas.drawPath(this.Cj, this.mPaint);
        }
    }

    public void ab(boolean isCircle) {
        this.BS = isCircle;
        kl();
        invalidateSelf();
    }

    public boolean ke() {
        return this.BS;
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

    public void setRadius(float radius) {
        Preconditions.checkArgument(radius >= 0.0f, "radius should be non negative");
        Arrays.fill(this.Cn, radius);
        kl();
        invalidateSelf();
    }

    public void setColor(int color) {
        if (this.mColor != color) {
            this.mColor = color;
            invalidateSelf();
        }
    }

    public int getColor() {
        return this.mColor;
    }

    public void a(int color, float width) {
        if (this.Ch != color) {
            this.Ch = color;
            invalidateSelf();
        }
        if (this.Cg != width) {
            this.Cg = width;
            kl();
            invalidateSelf();
        }
    }

    public int kg() {
        return this.Ch;
    }

    public float kh() {
        return this.Cg;
    }

    public void g(float padding) {
        if (this.Ci != padding) {
            this.Ci = padding;
            kl();
            invalidateSelf();
        }
    }

    public float ki() {
        return this.Ci;
    }

    public void setAlpha(int alpha) {
        if (alpha != this.mAlpha) {
            this.mAlpha = alpha;
            invalidateSelf();
        }
    }

    public int getAlpha() {
        return this.mAlpha;
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return e.bG(e.r(this.mColor, this.mAlpha));
    }

    private void kl() {
        this.BM.reset();
        this.Cj.reset();
        this.Co.set(getBounds());
        this.Co.inset(this.Cg / 2.0f, this.Cg / 2.0f);
        if (this.BS) {
            this.Cj.addCircle(this.Co.centerX(), this.Co.centerY(), Math.min(this.Co.width(), this.Co.height()) / 2.0f, Direction.CW);
        } else {
            for (int i = 0; i < this.BV.length; i++) {
                this.BV[i] = (this.Cn[i] + this.Ci) - (this.Cg / 2.0f);
            }
            this.Cj.addRoundRect(this.Co, this.BV, Direction.CW);
        }
        this.Co.inset((-this.Cg) / 2.0f, (-this.Cg) / 2.0f);
        this.Co.inset(this.Ci, this.Ci);
        if (this.BS) {
            this.BM.addCircle(this.Co.centerX(), this.Co.centerY(), Math.min(this.Co.width(), this.Co.height()) / 2.0f, Direction.CW);
        } else {
            this.BM.addRoundRect(this.Co, this.Cn, Direction.CW);
        }
        this.Co.inset(-this.Ci, -this.Ci);
    }
}
