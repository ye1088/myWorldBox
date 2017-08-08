package com.MCWorld.widget.caseview.highlight;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.PopupWindow;
import com.MCWorld.widget.caseview.target.b;

/* compiled from: RoundedRectHighLight */
public class d implements c {
    protected int bvO;
    protected int bvP;

    public d(int radiusX, int radiusY) {
        this.bvO = radiusX;
        this.bvP = radiusY;
    }

    public void a(PopupWindow window, b target, Canvas canvas, Paint paint) {
        if (target != null) {
            RectF bounds = target.a(window);
            if (bounds != null) {
                canvas.drawRoundRect(bounds, (float) this.bvO, (float) this.bvP, paint);
            }
        }
    }
}
