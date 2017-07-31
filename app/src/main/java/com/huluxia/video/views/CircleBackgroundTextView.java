package com.huluxia.video.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

public class CircleBackgroundTextView extends TextView {
    private static final int bqk = 2;
    private Paint bql;
    private RectF bqm;

    public CircleBackgroundTextView(Context context) {
        this(context, null);
    }

    public CircleBackgroundTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleBackgroundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.bqm = new RectF();
        setGravity(17);
        this.bql = new Paint(1);
        this.bql.setColor(-16711936);
        this.bql.setStrokeWidth(2.0f);
        this.bql.setStyle(Style.STROKE);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.bqm.set(1.0f, 1.0f, ((float) canvas.getWidth()) - 1.0f, ((float) canvas.getHeight()) - 1.0f);
        canvas.drawOval(this.bqm, this.bql);
    }
}
