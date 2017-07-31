package com.baidu.mapapi;

import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

final class r implements p {
    private o a = new o(Config.ARGB_4444);
    private Drawable b = null;

    public static void a(Drawable drawable, Drawable drawable2) {
        Rect bounds = drawable2.getBounds();
        int height = (int) (((float) bounds.height()) * 0.5f);
        int width = (int) (((double) (((float) bounds.width()) * 0.9f)) * 0.5d);
        drawable.setBounds(bounds.left + width, bounds.top + height, width + bounds.right, bounds.bottom + height);
    }

    public final Drawable a(Drawable drawable) {
        this.b = drawable;
        this.a.a(this.b.getIntrinsicWidth(), this.b.getIntrinsicHeight());
        this.a.a(this);
        this.b = null;
        return new BitmapDrawable(this.a.b());
    }

    public final void a(Canvas canvas) {
        this.b.setColorFilter(2130706432, Mode.SRC_IN);
        canvas.skew(-0.9f, 0.0f);
        canvas.scale(1.0f, 0.5f);
        this.b.draw(canvas);
        this.b.clearColorFilter();
    }
}
