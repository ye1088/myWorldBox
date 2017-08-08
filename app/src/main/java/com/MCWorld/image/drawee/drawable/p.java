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
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.utils.UtilsScreen;

/* compiled from: TextProgressDrawable */
public class p extends Drawable {
    private final Path BM = new Path();
    private final RectF BN = new RectF();
    private int BP = 0;
    private boolean BQ = false;
    private int CN = 14;
    private int CO = UtilsScreen.dipToPx(AppConfig.getInstance().getAppContext(), 6);
    private int CP = UtilsScreen.dipToPx(AppConfig.getInstance().getAppContext(), 6);
    private int mBackgroundColor = Integer.MIN_VALUE;
    private final Paint mPaint = new Paint(1);
    private int mRadius = 0;
    private int mTextColor = 16777215;

    public void setBackgroundColor(int backgroundColor) {
        if (this.mBackgroundColor != backgroundColor) {
            this.mBackgroundColor = backgroundColor;
            invalidateSelf();
        }
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public void setTextColor(int textColor) {
        if (this.mTextColor != textColor) {
            this.mTextColor = textColor;
            invalidateSelf();
        }
    }

    public void setTextSize(int textSize) {
        if (this.CN != textSize) {
            this.CN = textSize;
            invalidateSelf();
        }
    }

    public void bR(int paddingHorizontol) {
        if (this.CO != paddingHorizontol) {
            this.CO = paddingHorizontol;
            invalidateSelf();
        }
    }

    public void bS(int paddingVertical) {
        if (this.CP != paddingVertical) {
            this.CP = paddingVertical;
            invalidateSelf();
        }
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
            a(canvas, this.BP, this.mBackgroundColor, this.mTextColor);
        }
    }

    private void a(Canvas canvas, int level, int bgColor, int textColor) {
        String levelText = (level / 100) + "%";
        int textSize = UtilsScreen.sp2px(AppConfig.getInstance().getAppContext(), (float) this.CN);
        int textLength = textSize * levelText.length();
        Rect bounds = getBounds();
        this.mPaint.reset();
        this.mPaint.setColor(textColor);
        this.mPaint.setTextSize((float) textSize);
        this.mPaint.setStyle(Style.FILL_AND_STROKE);
        canvas.drawText(levelText, (float) (bounds.centerX() - (textLength / 2)), (float) (bounds.centerY() - (textSize / 2)), this.mPaint);
        this.mPaint.reset();
        this.BN.set((float) ((bounds.centerX() - (textLength / 2)) - this.CO), (float) ((bounds.centerY() - (textSize / 2)) - this.CP), (float) ((bounds.centerX() + (textLength / 2)) + this.CO), (float) ((bounds.centerY() + (textSize / 2)) + this.CP));
        this.mPaint.setColor(bgColor);
        this.mPaint.setStyle(Style.FILL_AND_STROKE);
        this.BM.reset();
        this.BM.setFillType(FillType.EVEN_ODD);
        this.BM.addRoundRect(this.BN, (float) this.mRadius, (float) this.mRadius, Direction.CW);
        canvas.drawPath(this.BM, this.mPaint);
    }
}
