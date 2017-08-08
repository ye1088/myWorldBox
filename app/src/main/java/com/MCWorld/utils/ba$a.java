package com.MCWorld.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import java.lang.ref.WeakReference;

/* compiled from: UtilsTopic */
public class ba$a extends ImageSpan {
    private static final char[] bnm = new char[]{'…'};
    private static final char[] bnn = new char[]{'‥'};
    private WeakReference<Drawable> bno;
    private Context ctx;

    public ba$a(Context context, int resourceId) {
        super(context, resourceId);
        this.ctx = context;
    }

    public ba$a(Context context, Drawable d) {
        super(d);
        this.ctx = context;
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, FontMetricsInt fm) {
        Rect rect = a(paint).getBounds();
        if (fm != null) {
            FontMetricsInt fontMetrics = new FontMetricsInt();
            paint.getFontMetricsInt(fontMetrics);
            fm.ascent = fontMetrics.ascent;
            fm.descent = fontMetrics.descent;
            fm.top = fontMetrics.top;
            fm.bottom = fontMetrics.bottom;
        }
        return rect.right;
    }

    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        String subS = text.toString().substring(start, end);
        if (bnm[0] == subS.charAt(0) || bnn[0] == subS.charAt(0)) {
            canvas.save();
            canvas.drawText(subS, x, (float) y, paint);
            canvas.restore();
            return;
        }
        Drawable d = a(paint);
        canvas.save();
        FontMetricsInt fontMetrics = new FontMetricsInt();
        paint.getFontMetricsInt(fontMetrics);
        Rect rect = d.getBounds();
        canvas.translate(x, (float) (y + fontMetrics.ascent));
        d.draw(canvas);
        canvas.restore();
    }

    private Drawable a(Paint paint) {
        WeakReference<Drawable> wr = this.bno;
        Drawable d = null;
        if (wr != null) {
            d = (Drawable) wr.get();
        }
        if (d == null) {
            d = getDrawable();
            if (d instanceof ba$b) {
                ba$b text = (ba$b) d;
                text.setTextSize((float) at.dipToPx(this.ctx, 8));
                int right = at.dipToPx(this.ctx, at.pxToDip(this.ctx, text.MI()) + 8);
                text.setBounds(new Rect(0, at.dipToPx(this.ctx, 1), right - at.dipToPx(this.ctx, 2), paint.getFontMetricsInt(null) - at.dipToPx(this.ctx, 3)));
            } else {
                int dip2 = at.dipToPx(this.ctx, 2);
                d.setBounds(new Rect(dip2, dip2, paint.getFontMetricsInt(null) - dip2, paint.getFontMetricsInt(null) - dip2));
            }
            this.bno = new WeakReference(d);
        }
        return d;
    }
}
