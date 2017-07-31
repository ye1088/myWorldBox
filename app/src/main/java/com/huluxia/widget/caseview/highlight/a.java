package com.huluxia.widget.caseview.highlight;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.PopupWindow;
import com.huluxia.widget.caseview.target.b;

/* compiled from: CircleHighLight */
public class a implements c {
    public void a(PopupWindow window, b target, Canvas canvas, Paint paint) {
        if (target != null) {
            RectF bounds = target.a(window);
            if (bounds != null) {
                canvas.drawCircle(bounds.centerX(), bounds.centerY(), (float) ((int) (Math.max(bounds.width(), bounds.height()) / 2.0f)), paint);
            }
        }
    }
}
