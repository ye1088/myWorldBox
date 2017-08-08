package com.MCWorld.widget.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.MCWorld.bbs.b.o;
import com.MCWorld.mcfloat.InstanceZones.e;

public class ProgressBarCircle extends View {
    private Paint bDX = new Paint();
    private Paint bDY = new Paint();
    private Paint bDZ = new Paint();
    private RectF bEa = new RectF();
    private int bEb;
    private int bEc;
    private int barColor = 0;
    private int mLayoutHeight;
    private int mLayoutWidth;
    private int padding = 0;
    private int rimColor = 0;
    private int rimWidth = 0;
    private int textColor = 0;
    private int textSize = 0;

    public ProgressBarCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, o.ProgressBarCircle);
        this.barColor = a.getColor(o.ProgressBarCircle_barColor, this.barColor);
        this.rimColor = a.getColor(o.ProgressBarCircle_rimColor, this.rimColor);
        this.textColor = a.getColor(o.ProgressBarCircle_textColor, this.textColor);
        this.padding = (int) a.getDimension(o.ProgressBarCircle_padding, (float) this.padding);
        this.rimWidth = (int) a.getDimension(o.ProgressBarCircle_rimWidth, (float) this.rimWidth);
        this.textSize = (int) a.getDimension(o.ProgressBarCircle_textSize, (float) this.textSize);
        a.recycle();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mLayoutWidth = getLayoutParams().width;
        this.mLayoutHeight = getLayoutParams().height;
        this.bEa = new RectF((float) (this.padding + this.rimWidth), (float) (this.padding + this.rimWidth), (float) ((this.mLayoutWidth - this.padding) - this.rimWidth), (float) ((this.mLayoutHeight - this.padding) - this.rimWidth));
        this.bDY.setColor(this.rimColor);
        this.bDY.setAntiAlias(true);
        this.bDY.setStyle(Style.STROKE);
        this.bDY.setStrokeWidth((float) this.rimWidth);
        this.bDX.setColor(this.barColor);
        this.bDX.setAntiAlias(true);
        this.bDX.setStyle(Style.STROKE);
        this.bDX.setStrokeWidth((float) this.rimWidth);
        this.bDZ.setColor(this.textColor);
        this.bDZ.setStyle(Style.FILL);
        this.bDZ.setAntiAlias(true);
        this.bDZ.setTextSize((float) this.textSize);
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int prog = 0;
        if (this.bEb > 0 && this.bEc > 0) {
            prog = this.bEb / (this.bEc / e.Wx);
        }
        canvas.drawArc(this.bEa, 360.0f, 360.0f, false, this.bDY);
        canvas.drawArc(this.bEa, -90.0f, (float) prog, false, this.bDX);
        prog = 0;
        if (this.bEb > 0 && this.bEc > 0) {
            prog = this.bEb / (this.bEc / 100);
        }
        if (prog > 100) {
            prog = 100;
        }
        String text = prog + "%";
        canvas.drawText(text, ((float) (this.mLayoutWidth / 2)) - (this.bDZ.measureText(text) / 2.0f), (float) ((this.mLayoutHeight / 2) + (this.textSize / 3)), this.bDZ);
    }

    public void setMaxProgress(int max) {
        this.bEc = max;
    }

    public void setProgress(int cur) {
        this.bEb = cur;
        invalidate();
    }

    public void aS(int cur, int max) {
        this.bEb = cur;
        this.bEc = max;
        invalidate();
    }

    public void aT(int cur, int max) {
        this.bEb = cur;
        this.bEc = max;
        postInvalidate();
    }
}
