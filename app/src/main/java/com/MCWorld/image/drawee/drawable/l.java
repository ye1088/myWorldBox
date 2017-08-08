package com.MCWorld.image.drawee.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import javax.annotation.Nullable;

/* compiled from: RoundedBitmapDrawable */
public class l extends BitmapDrawable implements k, q {
    private final Path BM;
    private boolean BS;
    private boolean BT;
    private final float[] BU;
    @VisibleForTesting
    final float[] BV;
    @VisibleForTesting
    final RectF BW;
    @VisibleForTesting
    final RectF BX;
    @VisibleForTesting
    final RectF BY;
    @VisibleForTesting
    final RectF BZ;
    @Nullable
    private r Bc;
    @VisibleForTesting
    final Matrix Ca;
    @VisibleForTesting
    final Matrix Cb;
    @VisibleForTesting
    final Matrix Cc;
    @VisibleForTesting
    final Matrix Cd;
    @VisibleForTesting
    final Matrix Ce;
    @VisibleForTesting
    final Matrix Cf;
    private float Cg;
    private int Ch;
    private float Ci;
    private final Path Cj;
    private boolean Ck;
    private boolean Cl;
    private WeakReference<Bitmap> Cm;
    private final Paint mBorderPaint;
    private final Paint mPaint;

    public l(Resources res, Bitmap bitmap) {
        this(res, bitmap, null);
    }

    public l(Resources res, Bitmap bitmap, @Nullable Paint paint) {
        super(res, bitmap);
        this.BS = false;
        this.BT = false;
        this.BU = new float[8];
        this.BV = new float[8];
        this.BW = new RectF();
        this.BX = new RectF();
        this.BY = new RectF();
        this.BZ = new RectF();
        this.Ca = new Matrix();
        this.Cb = new Matrix();
        this.Cc = new Matrix();
        this.Cd = new Matrix();
        this.Ce = new Matrix();
        this.Cf = new Matrix();
        this.Cg = 0.0f;
        this.Ch = 0;
        this.Ci = 0.0f;
        this.BM = new Path();
        this.Cj = new Path();
        this.Ck = true;
        this.mPaint = new Paint();
        this.mBorderPaint = new Paint(1);
        this.Cl = true;
        if (paint != null) {
            this.mPaint.set(paint);
        }
        this.mPaint.setFlags(1);
        this.mBorderPaint.setStyle(Style.STROKE);
    }

    public static l a(Resources res, BitmapDrawable bitmapDrawable) {
        return new l(res, bitmapDrawable.getBitmap(), bitmapDrawable.getPaint());
    }

    public void ab(boolean isCircle) {
        this.BS = isCircle;
        this.Ck = true;
        invalidateSelf();
    }

    public boolean ke() {
        return this.BS;
    }

    public void setRadius(float radius) {
        boolean z = false;
        Preconditions.checkState(radius >= 0.0f);
        Arrays.fill(this.BU, radius);
        if (radius != 0.0f) {
            z = true;
        }
        this.BT = z;
        this.Ck = true;
        invalidateSelf();
    }

    public void a(float[] radii) {
        if (radii == null) {
            Arrays.fill(this.BU, 0.0f);
            this.BT = false;
        } else {
            boolean z;
            if (radii.length == 8) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "radii should have exactly 8 values");
            System.arraycopy(radii, 0, this.BU, 0, 8);
            this.BT = false;
            for (int i = 0; i < 8; i++) {
                int i2;
                boolean z2 = this.BT;
                if (radii[i] > 0.0f) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                this.BT = i2 | z2;
            }
        }
        this.Ck = true;
        invalidateSelf();
    }

    public float[] kf() {
        return this.BU;
    }

    public void a(int color, float width) {
        if (this.Ch != color || this.Cg != width) {
            this.Ch = color;
            this.Cg = width;
            this.Ck = true;
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
            this.Ck = true;
            invalidateSelf();
        }
    }

    public float ki() {
        return this.Ci;
    }

    public void a(@Nullable r transformCallback) {
        this.Bc = transformCallback;
    }

    public void setAlpha(int alpha) {
        if (alpha != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(alpha);
            super.setAlpha(alpha);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        super.setColorFilter(colorFilter);
    }

    public void draw(Canvas canvas) {
        if (kj()) {
            kk();
            kl();
            km();
            int saveCount = canvas.save();
            canvas.concat(this.Ce);
            canvas.drawPath(this.BM, this.mPaint);
            if (this.Cg > 0.0f) {
                this.mBorderPaint.setStrokeWidth(this.Cg);
                this.mBorderPaint.setColor(e.r(this.Ch, this.mPaint.getAlpha()));
                canvas.drawPath(this.Cj, this.mBorderPaint);
            }
            canvas.restoreToCount(saveCount);
            return;
        }
        super.draw(canvas);
    }

    @VisibleForTesting
    boolean kj() {
        return this.BS || this.BT || this.Cg > 0.0f;
    }

    private void kk() {
        if (this.Bc != null) {
            this.Bc.a(this.Cc);
            this.Bc.a(this.BW);
        } else {
            this.Cc.reset();
            this.BW.set(getBounds());
        }
        this.BY.set(0.0f, 0.0f, (float) getBitmap().getWidth(), (float) getBitmap().getHeight());
        this.BZ.set(getBounds());
        this.Ca.setRectToRect(this.BY, this.BZ, ScaleToFit.FILL);
        if (!(this.Cc.equals(this.Cd) && this.Ca.equals(this.Cb))) {
            this.Cl = true;
            this.Cc.invert(this.Ce);
            this.Cf.set(this.Cc);
            this.Cf.preConcat(this.Ca);
            this.Cd.set(this.Cc);
            this.Cb.set(this.Ca);
        }
        if (!this.BW.equals(this.BX)) {
            this.Ck = true;
            this.BX.set(this.BW);
        }
    }

    private void kl() {
        if (this.Ck) {
            this.Cj.reset();
            this.BW.inset(this.Cg / 2.0f, this.Cg / 2.0f);
            if (this.BS) {
                this.Cj.addCircle(this.BW.centerX(), this.BW.centerY(), Math.min(this.BW.width(), this.BW.height()) / 2.0f, Direction.CW);
            } else {
                for (int i = 0; i < this.BV.length; i++) {
                    this.BV[i] = (this.BU[i] + this.Ci) - (this.Cg / 2.0f);
                }
                this.Cj.addRoundRect(this.BW, this.BV, Direction.CW);
            }
            this.BW.inset((-this.Cg) / 2.0f, (-this.Cg) / 2.0f);
            this.BM.reset();
            this.BW.inset(this.Ci, this.Ci);
            if (this.BS) {
                this.BM.addCircle(this.BW.centerX(), this.BW.centerY(), Math.min(this.BW.width(), this.BW.height()) / 2.0f, Direction.CW);
            } else {
                this.BM.addRoundRect(this.BW, this.BU, Direction.CW);
            }
            this.BW.inset(-this.Ci, -this.Ci);
            this.BM.setFillType(FillType.WINDING);
            this.Ck = false;
        }
    }

    private void km() {
        Bitmap bitmap = getBitmap();
        if (this.Cm == null || this.Cm.get() != bitmap) {
            this.Cm = new WeakReference(bitmap);
            this.mPaint.setShader(new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP));
            this.Cl = true;
        }
        if (this.Cl) {
            this.mPaint.getShader().setLocalMatrix(this.Cf);
            this.Cl = false;
        }
    }
}
