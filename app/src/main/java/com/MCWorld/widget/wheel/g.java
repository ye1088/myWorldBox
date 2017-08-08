package com.MCWorld.widget.wheel;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/* compiled from: WheelScroller */
public class g {
    private static final int bFO = 400;
    public static final int bFP = 1;
    private a bFQ;
    private GestureDetector bFR;
    private Scroller bFS;
    private int bFT;
    private float bFU;
    private boolean bFV;
    private SimpleOnGestureListener bFW = new SimpleOnGestureListener(this) {
        final /* synthetic */ g bGa;

        {
            this.bGa = this$0;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            this.bGa.bFT = 0;
            this.bGa.bFS.fling(0, this.bGa.bFT, 0, (int) (-velocityY), 0, 0, -2147483647, Integer.MAX_VALUE);
            this.bGa.mq(0);
            return true;
        }
    };
    private final int bFX = 0;
    private final int bFY = 1;
    private Handler bFZ = new Handler(this) {
        final /* synthetic */ g bGa;

        {
            this.bGa = this$0;
        }

        public void handleMessage(Message msg) {
            this.bGa.bFS.computeScrollOffset();
            int currY = this.bGa.bFS.getCurrY();
            int delta = this.bGa.bFT - currY;
            this.bGa.bFT = currY;
            if (delta != 0) {
                this.bGa.bFQ.mr(delta);
            }
            if (Math.abs(currY - this.bGa.bFS.getFinalY()) < 1) {
                currY = this.bGa.bFS.getFinalY();
                this.bGa.bFS.forceFinished(true);
            }
            if (!this.bGa.bFS.isFinished()) {
                this.bGa.bFZ.sendEmptyMessage(msg.what);
            } else if (msg.what == 0) {
                this.bGa.Qg();
            } else {
                this.bGa.Qi();
            }
        }
    };
    private Context context;

    /* compiled from: WheelScroller */
    public interface a {
        void Qj();

        void Qk();

        void Ql();

        void mr(int i);
    }

    public g(Context context, a listener) {
        this.bFR = new GestureDetector(context, this.bFW);
        this.bFR.setIsLongpressEnabled(false);
        this.bFS = new Scroller(context);
        this.bFQ = listener;
        this.context = context;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.bFS.forceFinished(true);
        this.bFS = new Scroller(this.context, interpolator);
    }

    public void aU(int distance, int time) {
        this.bFS.forceFinished(true);
        this.bFT = 0;
        this.bFS.startScroll(0, 0, 0, distance, time != 0 ? time : 400);
        mq(0);
        Qh();
    }

    public void Qe() {
        this.bFS.forceFinished(true);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.bFU = event.getY();
                this.bFS.forceFinished(true);
                Qf();
                break;
            case 2:
                int distanceY = (int) (event.getY() - this.bFU);
                if (distanceY != 0) {
                    Qh();
                    this.bFQ.mr(distanceY);
                    this.bFU = event.getY();
                    break;
                }
                break;
        }
        if (!this.bFR.onTouchEvent(event) && event.getAction() == 1) {
            Qg();
        }
        return true;
    }

    private void mq(int message) {
        Qf();
        this.bFZ.sendEmptyMessage(message);
    }

    private void Qf() {
        this.bFZ.removeMessages(0);
        this.bFZ.removeMessages(1);
    }

    private void Qg() {
        this.bFQ.Ql();
        mq(1);
    }

    private void Qh() {
        if (!this.bFV) {
            this.bFV = true;
            this.bFQ.Qj();
        }
    }

    void Qi() {
        if (this.bFV) {
            this.bFQ.Qk();
            this.bFV = false;
        }
    }
}
