package com.MCWorld.widget;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.MCWorld.bbs.b.c;
import com.simple.colorful.d;

public class HtImageView extends ImageView {
    public HtImageView(Context context) {
        super(context);
    }

    public HtImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void bA(Context context) {
        int brightness = d.s(context, c.valBrightness);
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1.0f, 0.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 1.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 1.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        }
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        bA(getContext());
    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        bA(getContext());
    }
}
