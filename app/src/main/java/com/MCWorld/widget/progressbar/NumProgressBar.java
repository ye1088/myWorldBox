package com.MCWorld.widget.progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.MCWorld.utils.at;

public class NumProgressBar extends ProgressBar {
    Paint mPaint;
    String text;

    public NumProgressBar(Context context) {
        super(context);
        bK(context);
    }

    public NumProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        bK(context);
    }

    public NumProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        bK(context);
    }

    public synchronized void setProgress(int progress) {
        setText(progress);
        super.setProgress(progress);
    }

    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        canvas.drawText(this.text, (float) ((getWidth() / 2) - rect.centerX()), (float) ((getHeight() / 2) - rect.centerY()), this.mPaint);
    }

    private void bK(Context context) {
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.rgb(129, 129, 129));
        this.mPaint.setTextSize((float) at.sp2px(context, 8.0f));
    }

    private void setText(int progress) {
        this.text = String.valueOf(progress) + "/" + String.valueOf(getMax());
    }
}
