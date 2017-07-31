package com.huluxia.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.huluxia.framework.base.utils.UtilsScreen;

public class RoundProgress extends View {
    private static final String TAG = "RoundProgress";
    Paint bql;
    private int bud;
    private int bue;
    private boolean buh;
    RectF bui;
    private Context mContext;
    private int progress;

    public void setDayMode(boolean dayMode) {
        this.buh = dayMode;
    }

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.bud = 100;
        this.progress = 0;
        this.mContext = context;
        this.bui = new RectF();
        this.bql = new Paint();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        this.bql.setAntiAlias(true);
        this.bql.setColor(-1);
        canvas.drawColor(0);
        int progressStrokeWidth = UtilsScreen.dipToPx(this.mContext, 3);
        this.bql.setStrokeWidth((float) progressStrokeWidth);
        this.bql.setStyle(Style.STROKE);
        this.bui.left = (float) (progressStrokeWidth / 2);
        this.bui.top = (float) (progressStrokeWidth / 2);
        this.bui.right = (float) (width - (progressStrokeWidth / 2));
        this.bui.bottom = (float) (height - (progressStrokeWidth / 2));
        if (this.buh) {
            canvas.drawArc(this.bui, -90.0f, 360.0f, false, this.bql);
        }
        if (this.bue == 0) {
            this.bql.setColor(Color.parseColor("#87dd6f"));
        } else {
            this.bql.setColor(this.bue);
        }
        canvas.drawArc(this.bui, -90.0f, 360.0f * (((float) this.progress) / ((float) this.bud)), false, this.bql);
    }

    public int getMaxProgress() {
        return this.bud;
    }

    public void setMaxProgress(int maxProgress) {
        this.bud = maxProgress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.bue = progressColor;
    }

    public synchronized void setProgressNotInUiThread(int progress) {
        this.progress = progress;
        postInvalidate();
    }
}
