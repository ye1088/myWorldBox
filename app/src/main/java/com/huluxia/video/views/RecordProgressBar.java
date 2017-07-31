package com.huluxia.video.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import com.huluxia.video.b.j;
import java.lang.ref.WeakReference;

public class RecordProgressBar extends View {
    private static final long bqn = 10;
    public static final int bqo = 0;
    public static final int bqp = 1;
    public static final int bqq = 2;
    private static final int bqr = 6;
    private static final int bqs = 0;
    private static final int bqt = -16711936;
    private static final int bqu = -65536;
    private int bqv;
    private int bqw;
    private int bqx;
    private int bqy;
    private float bqz;
    private Paint mPaint;
    private long mStartTime;
    private int mState;

    private static class a implements Runnable {
        private WeakReference<Handler> bqA;
        private WeakReference<View> bqB;

        public a(Handler handler, View view) {
            this.bqA = new WeakReference(handler);
            this.bqB = new WeakReference(view);
        }

        public void run() {
            Handler handler = (Handler) this.bqA.get();
            if (handler != null) {
                View view = (View) this.bqB.get();
                if (view != null) {
                    view.invalidate();
                    handler.postDelayed(this, RecordProgressBar.bqn);
                }
            }
        }
    }

    public RecordProgressBar(Context context) {
        this(context, null);
    }

    public RecordProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mState = 0;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, j.RecordProgressBar);
        this.bqw = typedArray.getColor(j.RecordProgressBar_rpb_backgroundColor, 0);
        this.bqy = typedArray.getColor(j.RecordProgressBar_rpb_cancelColor, -65536);
        this.bqx = typedArray.getColor(j.RecordProgressBar_rpb_runningColor, bqt);
        this.bqv = typedArray.getInteger(j.RecordProgressBar_rpb_timeLength, 6);
        typedArray.recycle();
        this.mPaint = new Paint(1);
    }

    public int getState() {
        return this.mState;
    }

    public int getRunningTime() {
        return this.bqv;
    }

    public void setRunningTime(int runningTime) {
        this.bqv = runningTime;
    }

    public void start() {
        this.mStartTime = System.currentTimeMillis();
        this.bqz = (((float) getWidth()) / 2.0f) / ((float) this.bqv);
        this.mState = 1;
        Handler handler = getHandler();
        if (handler != null) {
            handler.post(new a(handler, this));
        }
    }

    public void Nx() {
        this.mState = 1;
    }

    public void cancel() {
        this.mState = 2;
    }

    public void stop() {
        this.mState = 0;
        invalidate();
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    protected void onDraw(Canvas canvas) {
        int color;
        super.onDraw(canvas);
        canvas.drawColor(this.bqw);
        switch (this.mState) {
            case 1:
                color = this.bqx;
                break;
            case 2:
                color = this.bqy;
                break;
            default:
                color = -1;
                break;
        }
        long pastTime = System.currentTimeMillis() - this.mStartTime;
        if (color == -1) {
            return;
        }
        if (pastTime > ((long) (this.bqv * 1000))) {
            stop();
            return;
        }
        this.mPaint.setColor(color);
        float left = (this.bqz * ((float) pastTime)) / 1000.0f;
        canvas.drawRect(left, 0.0f, ((float) canvas.getWidth()) - left, (float) canvas.getHeight(), this.mPaint);
    }
}
