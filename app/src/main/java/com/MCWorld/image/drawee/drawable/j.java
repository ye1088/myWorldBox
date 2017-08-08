package com.MCWorld.image.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* compiled from: ProgressBarDrawable */
public class j extends Drawable {
    private final Path BM = new Path();
    private final RectF BN = new RectF();
    private int BO = 20;
    private int BP = 0;
    private boolean BQ = false;
    private boolean BR = false;
    private int mBackgroundColor = Integer.MIN_VALUE;
    private int mColor = -2147450625;
    private int mPadding = 10;
    private final Paint mPaint = new Paint(1);
    private int mRadius = 0;

    public void setColor(int color) {
        if (this.mColor != color) {
            this.mColor = color;
            invalidateSelf();
        }
    }

    public int getColor() {
        return this.mColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        if (this.mBackgroundColor != backgroundColor) {
            this.mBackgroundColor = backgroundColor;
            invalidateSelf();
        }
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public void bN(int padding) {
        if (this.mPadding != padding) {
            this.mPadding = padding;
            invalidateSelf();
        }
    }

    public boolean getPadding(Rect padding) {
        padding.set(this.mPadding, this.mPadding, this.mPadding, this.mPadding);
        return this.mPadding != 0;
    }

    public void bO(int barWidth) {
        if (this.BO != barWidth) {
            this.BO = barWidth;
            invalidateSelf();
        }
    }

    public int ka() {
        return this.BO;
    }

    public void Z(boolean hideWhenZero) {
        this.BQ = hideWhenZero;
    }

    public boolean kb() {
        return this.BQ;
    }

    public void bP(int radius) {
        if (this.mRadius != radius) {
            this.mRadius = radius;
            invalidateSelf();
        }
    }

    public int kc() {
        return this.mRadius;
    }

    public void aa(boolean isVertical) {
        if (this.BR != isVertical) {
            this.BR = isVertical;
            invalidateSelf();
        }
    }

    public boolean kd() {
        return this.BR;
    }

    protected boolean onLevelChange(int level) {
        this.BP = level;
        invalidateSelf();
        return true;
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
    }

    public int getOpacity() {
        return e.bG(this.mPaint.getColor());
    }

    public void draw(Canvas canvas) {
        if (!this.BQ || this.BP != 0) {
            if (this.BR) {
                b(canvas, 10000, this.mBackgroundColor);
                b(canvas, this.BP, this.mColor);
                return;
            }
            a(canvas, 10000, this.mBackgroundColor);
            a(canvas, this.BP, this.mColor);
        }
    }

    private void a(Canvas canvas, int level, int color) {
        Rect bounds = getBounds();
        int xpos = bounds.left + this.mPadding;
        int ypos = (bounds.bottom - this.mPadding) - this.BO;
        this.BN.set((float) xpos, (float) ypos, (float) (xpos + (((bounds.width() - (this.mPadding * 2)) * level) / 10000)), (float) (this.BO + ypos));
        a(canvas, color);
    }

    private void b(Canvas canvas, int level, int color) {
        Rect bounds = getBounds();
        int xpos = bounds.left + this.mPadding;
        int ypos = bounds.top + this.mPadding;
        this.BN.set((float) xpos, (float) ypos, (float) (this.BO + xpos), (float) (ypos + (((bounds.height() - (this.mPadding * 2)) * level) / 10000)));
        a(canvas, color);
    }

    private void a(Canvas canvas, int color) {
        this.mPaint.setColor(color);
        this.mPaint.setStyle(Style.FILL_AND_STROKE);
        this.BM.reset();
        this.BM.setFillType(FillType.EVEN_ODD);
        this.BM.addRoundRect(this.BN, (float) Math.min(this.mRadius, this.BO / 2), (float) Math.min(this.mRadius, this.BO / 2), Direction.CW);
        canvas.drawPath(this.BM, this.mPaint);
    }
}
