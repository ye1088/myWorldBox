package com.huluxia.widget.progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.f;

public class ProgressBarRect extends ProgressBar {
    private Paint bDZ = null;
    private boolean bEd = false;

    public ProgressBarRect(Context context) {
        super(context);
    }

    public ProgressBarRect(Context context, AttributeSet attrs) {
        super(context, attrs);
        e(context, attrs);
    }

    public ProgressBarRect(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        e(context, attrs);
    }

    private void e(Context c, AttributeSet attrs) {
        this.bDZ = new Paint();
        int textSize = attrs.getAttributeIntValue(c.textSize, 15);
        int textColor = attrs.getAttributeIntValue(c.textColor, -16777216);
        textSize *= (int) c.getResources().getDisplayMetrics().density;
        this.bDZ.setAntiAlias(true);
        this.bDZ.setStyle(Style.FILL);
        this.bDZ.setColor(textColor);
        this.bDZ.setTextSize((float) textSize);
    }

    protected void onDraw(Canvas canvas) {
        super.setProgressDrawable(getResources().getDrawable(this.bEd ? f.style_prog_yellow : f.style_prog_green));
        super.onDraw(canvas);
    }

    public void setStop(boolean stop) {
        this.bEd = stop;
    }

    public void setTextSize(int size) {
        this.bDZ.setTextSize((float) size);
    }

    public void setMax(int max) {
        super.setMax(max);
    }

    public void setProgress(int cur) {
        super.setProgress(cur);
    }
}
