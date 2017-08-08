package com.MCWorld.utils;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

/* compiled from: UtilsTopic */
class ba$b extends Drawable {
    private TextPaint bnp = new TextPaint(1);
    private float bnq;
    private int bnr;
    private String mText;
    private int mTextColor = -1;

    public ba$b(String text, int backgroupColor) {
        this.mText = text;
        this.bnr = backgroupColor;
    }

    public void setTextSize(float size) {
        this.bnq = size;
    }

    public void setTextColor(int color) {
        this.mTextColor = color;
    }

    public float MI() {
        this.bnp.setTextSize(this.bnq);
        return this.bnp.measureText(this.mText);
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        this.bnp.reset();
        this.bnp.setColor(this.bnr);
        canvas.translate((float) bounds.left, (float) bounds.top);
        canvas.drawRoundRect(new RectF(bounds), 2.0f, 2.0f, this.bnp);
        this.bnp.reset();
        this.bnp.setTextSize(this.bnq);
        this.bnp.setColor(this.mTextColor);
        this.bnp.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, 1));
        canvas.clipRect(bounds);
        canvas.translate((float) bounds.left, (float) bounds.top);
        FontMetricsInt fontMetrics = this.bnp.getFontMetricsInt();
        int baseline = (bounds.top + ((((bounds.bottom - bounds.top) - fontMetrics.bottom) + fontMetrics.top) / 2)) - fontMetrics.top;
        baseline = ((bounds.top + ((bounds.bottom - bounds.top) / 2)) - ((fontMetrics.descent - fontMetrics.ascent) / 2)) - fontMetrics.ascent;
        this.bnp.setTextAlign(Align.CENTER);
        canvas.drawText(this.mText, 0, this.mText.length(), (float) bounds.centerX(), (float) baseline, this.bnp);
        canvas.restore();
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return 0;
    }
}
