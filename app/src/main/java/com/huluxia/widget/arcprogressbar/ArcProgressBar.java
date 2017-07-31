package com.huluxia.widget.arcprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.huluxia.bbs.b.o;
import com.huluxia.mcfloat.InstanceZones.e;

public class ArcProgressBar extends View {
    private int buV;
    private int buW;
    private int buX;
    private int buY;
    private int buZ;
    private int bva;
    private int bvb;
    private int bvc;
    private int bvd;
    private int bve;
    private Paint bvf;
    private Paint bvg;
    private Paint bvh;
    private RectF bvi;
    private RectF bvj;

    public ArcProgressBar(Context context) {
        this(context, null);
    }

    public ArcProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bve = 0;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, o.ArcProgressBar);
            this.buV = a.getColor(o.ArcProgressBar_backgroundArcColor, -7829368);
            this.buW = a.getColor(o.ArcProgressBar_progressArcColor, -16711936);
            this.buX = a.getColor(o.ArcProgressBar_progressBgColor, -12303292);
            this.buY = a.getDimensionPixelOffset(o.ArcProgressBar_backgroundArcWidth, n(context, 8));
            this.buZ = a.getDimensionPixelOffset(o.ArcProgressBar_progressArcWidth, n(context, 4));
            this.bva = a.getInteger(o.ArcProgressBar_startDegree, 0);
            this.bva = Math.min(Math.max(0, this.bva), e.Wx);
            this.bvb = a.getInteger(o.ArcProgressBar_sweepDegree, e.Wx);
            this.bvb = Math.min(Math.max(0, this.bvb), e.Wx);
            this.bvc = a.getInteger(o.ArcProgressBar_maxValue, 100);
            if (this.bvc <= 0) {
                this.bvc = 100;
            }
            this.bvd = a.getInteger(o.ArcProgressBar_currentValue, 0);
            this.bvd = Math.max(0, this.bvd);
            NW();
            this.bvf = new Paint(1);
            this.bvf.setStyle(Style.STROKE);
            this.bvf.setStrokeWidth((float) this.buY);
            this.bvf.setColor(this.buV);
            this.bvf.setStrokeCap(Cap.ROUND);
            this.bvg = new Paint(1);
            this.bvg.setStyle(Style.STROKE);
            this.bvg.setStrokeWidth((float) this.buZ);
            this.bvg.setColor(this.buW);
            this.bvg.setStrokeCap(Cap.ROUND);
            this.bvh = new Paint(1);
            this.bvh.setStyle(Style.STROKE);
            this.bvh.setStrokeWidth((float) this.buZ);
            this.bvh.setColor(this.buX);
            this.bvh.setStrokeCap(Cap.ROUND);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    private static int n(Context context, int dp) {
        return (int) TypedValue.applyDimension(1, (float) dp, context.getResources().getDisplayMetrics());
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int radius = Math.min(getWidth(), getHeight()) / 2;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        RectF bound = new RectF((float) (centerX - radius), (float) (centerY - radius), (float) (centerX + radius), (float) (centerY + radius));
        if (this.buY > this.buZ) {
            this.bvi = new RectF(bound);
            this.bvi.inset((float) (this.buY / 2), (float) (this.buY / 2));
            this.bvj = new RectF(this.bvi);
            return;
        }
        this.bvj = new RectF(bound);
        this.bvj.inset((float) (this.buZ / 2), (float) (this.buZ / 2));
        this.bvi = new RectF(this.bvj);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(this.bvi, (float) this.bva, (float) this.bvb, false, this.bvf);
        canvas.drawArc(this.bvj, (float) this.bva, (float) this.bvb, false, this.bvh);
        canvas.drawArc(this.bvj, (float) this.bva, (float) this.bve, false, this.bvg);
    }

    public void setCurrentValue(int newValue) {
        newValue = Math.min(this.bvc, Math.max(0, newValue));
        if (this.bvd != newValue) {
            this.bvd = newValue;
            NW();
            invalidate();
        }
    }

    public void setMaxValue(int newValue) {
        newValue = Math.max(0, newValue);
        if (newValue != this.bvc) {
            this.bvc = newValue;
            if (this.bvd > this.bvc) {
                this.bvd = this.bvc;
            }
            NW();
            invalidate();
        }
    }

    public void setBackgroundArcColor(int color) {
        if (this.buV != color) {
            this.buV = color;
            this.bvf.setColor(color);
            invalidate();
        }
    }

    public void setProgressArcColor(int color) {
        if (this.buW != color) {
            this.buW = color;
            this.bvg.setColor(color);
            invalidate();
        }
    }

    public void setProgressBgColor(int color) {
        if (this.buX != color) {
            this.buX = color;
            this.bvh.setColor(color);
            invalidate();
        }
    }

    private void NW() {
        this.bve = (int) (((float) this.bvb) * (((float) this.bvd) / ((float) this.bvc)));
    }
}
