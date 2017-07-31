package com.huluxia.widget.caseview.highlight;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.PopupWindow;

/* compiled from: CornerRectHighLight */
public class b implements c {
    public void a(PopupWindow window, com.huluxia.widget.caseview.target.b target, Canvas canvas, Paint paint) {
        if (target != null) {
            RectF bounds = target.a(window);
            if (bounds != null) {
                int radius = (int) Math.min(bounds.width() / 2.0f, bounds.height() / 2.0f);
                canvas.drawRoundRect(bounds, (float) radius, (float) radius, paint);
            }
        }
    }
}
