package com.huluxia.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/* compiled from: HlxImageSpan */
public class c extends ImageSpan {
    public c(Context arg0, int arg1) {
        super(arg0, arg1);
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, FontMetricsInt fm) {
        Rect rect = getDrawable().getBounds();
        if (fm != null) {
            FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;
            int top = (drHeight / 2) - (fontHeight / 4);
            int bottom = (drHeight / 2) + (fontHeight / 4);
            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    }

    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getDrawable();
        canvas.save();
        canvas.translate(x, (float) ((((bottom - top) - b.getBounds().bottom) / 2) + top));
        b.draw(canvas);
        canvas.restore();
    }
}
