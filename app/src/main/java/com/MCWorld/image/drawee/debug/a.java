package com.MCWorld.image.drawee.debug;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import javax.annotation.Nullable;

/* compiled from: DebugControllerOverlayDrawable */
public class a extends Drawable {
    @VisibleForTesting
    static final int AA = 1728026624;
    @VisibleForTesting
    static final int AB = 1727284022;
    private static final float AC = 0.1f;
    private static final float AE = 0.5f;
    private static final int AF = -26624;
    private static final int AG = -1;
    private static final int AH = 2;
    private static final int AI = 40;
    private static final int AJ = 12;
    private static final int AK = 8;
    private static final int AL = 10;
    private static final int AN = 6;
    private static final int AO = 8;
    private static final String Ay = "none";
    @VisibleForTesting
    static final int Az = 1716301648;
    private String AP;
    private int AQ;
    private int AR;
    private int AS;
    private String AT;
    private int AU;
    private int AV;
    private int AW = 80;
    private int AX;
    private int AY;
    private int AZ;
    private int Ba;
    private int Bb;
    private final Paint mPaint = new Paint(1);

    public a() {
        reset();
    }

    public void reset() {
        this.AQ = -1;
        this.AR = -1;
        this.AS = -1;
        this.AU = -1;
        this.AV = -1;
        this.AT = null;
        bz(null);
        invalidateSelf();
    }

    public void bC(int textGravity) {
        this.AW = textGravity;
        invalidateSelf();
    }

    public void bz(@Nullable String controllerId) {
        if (controllerId == null) {
            controllerId = "none";
        }
        this.AP = controllerId;
        invalidateSelf();
    }

    public void o(int widthPx, int heightPx) {
        this.AQ = widthPx;
        this.AR = heightPx;
        invalidateSelf();
    }

    public void p(int frameCount, int loopCount) {
        this.AU = frameCount;
        this.AV = loopCount;
        invalidateSelf();
    }

    public void bD(int imageSizeBytes) {
        this.AS = imageSizeBytes;
    }

    public void bA(@Nullable String imageFormat) {
        this.AT = imageFormat;
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        a(bounds, 6, 8);
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth(2.0f);
        this.mPaint.setColor(AF);
        canvas.drawRect((float) bounds.left, (float) bounds.top, (float) bounds.right, (float) bounds.bottom, this.mPaint);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(q(this.AQ, this.AR));
        canvas.drawRect((float) bounds.left, (float) bounds.top, (float) bounds.right, (float) bounds.bottom, this.mPaint);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setStrokeWidth(0.0f);
        this.mPaint.setColor(-1);
        this.Ba = this.AX;
        this.Bb = this.AY;
        a(canvas, "ID: %s", this.AP);
        a(canvas, "D: %getMCVersion%d", Integer.valueOf(bounds.width()), Integer.valueOf(bounds.height()));
        a(canvas, "I: %getMCVersion%d", Integer.valueOf(this.AQ), Integer.valueOf(this.AR));
        a(canvas, "I: %d KiB", Integer.valueOf(this.AS / 1024));
        if (this.AT != null) {
            a(canvas, "i format: %s", this.AT);
        }
        if (this.AU > 0) {
            a(canvas, "anim: f %d, show_toast %d", Integer.valueOf(this.AU), Integer.valueOf(this.AV));
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return -3;
    }

    private void a(Rect bounds, int numberOfLines, int maxLineLengthEm) {
        int textSizePx = Math.min(40, Math.max(12, Math.min(bounds.width() / maxLineLengthEm, bounds.height() / numberOfLines)));
        this.mPaint.setTextSize((float) textSizePx);
        this.AZ = textSizePx + 8;
        if (this.AW == 80) {
            this.AZ *= -1;
        }
        this.AX = bounds.left + 10;
        this.AY = this.AW == 80 ? bounds.bottom - 10 : (bounds.top + 10) + 12;
    }

    private void a(Canvas canvas, String text, @Nullable Object... args) {
        if (args == null) {
            canvas.drawText(text, (float) this.Ba, (float) this.Bb, this.mPaint);
        } else {
            canvas.drawText(String.format(text, args), (float) this.Ba, (float) this.Bb, this.mPaint);
        }
        this.Bb += this.AZ;
    }

    @VisibleForTesting
    int q(int imageWidth, int imageHeight) {
        int drawableWidth = getBounds().width();
        int drawableHeight = getBounds().height();
        if (drawableWidth == 0 || drawableHeight == 0 || imageWidth == 0 || imageHeight == 0) {
            return AB;
        }
        float scaledImageWidthThresholdOk = ((float) drawableWidth) * AC;
        float scaledImageWidthThresholdNotOk = ((float) drawableWidth) * 0.5f;
        float scaledImageHeightThresholdOk = ((float) drawableHeight) * AC;
        float scaledImageHeightThresholdNotOk = ((float) drawableHeight) * 0.5f;
        int absWidthDifference = Math.abs(imageWidth - drawableWidth);
        int absHeightDifference = Math.abs(imageHeight - drawableHeight);
        if (((float) absWidthDifference) < scaledImageWidthThresholdOk && ((float) absHeightDifference) < scaledImageHeightThresholdOk) {
            return Az;
        }
        if (((float) absWidthDifference) >= scaledImageWidthThresholdNotOk || ((float) absHeightDifference) >= scaledImageHeightThresholdNotOk) {
            return AB;
        }
        return AA;
    }
}
