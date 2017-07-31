package com.huluxia.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonTextImage extends Button {
    public ButtonTextImage(Context context) {
        super(context);
        setGravity(19);
    }

    public ButtonTextImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        setGravity(19);
    }

    public ButtonTextImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setGravity(19);
    }

    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawable = drawables[0];
            if (drawable != null) {
                a(canvas, drawable);
            }
        }
        super.onDraw(canvas);
    }

    private void a(Canvas canvas, Drawable drawable) {
        FontMetrics fm = getPaint().getFontMetrics();
        canvas.translate(((float) (getWidth() / 2)) - (((((float) drawable.getIntrinsicWidth()) + getPaint().measureText(getText().toString())) + ((float) getCompoundDrawablePadding())) / 2.0f), 0.0f);
    }
}
