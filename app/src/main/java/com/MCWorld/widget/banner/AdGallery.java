package com.MCWorld.widget.banner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Gallery;

public class AdGallery extends Gallery implements OnTouchListener {
    private static final int bvk = 10000;
    private final BroadcastReceiver aDe = new 2(this);
    protected int bvl = 10000;
    private boolean bvm = false;
    private boolean bvn = false;
    private boolean bvo = true;
    private final int bvp = 1;
    private final Handler mHandler = new 3(this);
    private boolean mRunning = false;
    private boolean mStarted = false;

    public AdGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AdGallery(Context context) {
        super(context);
        init();
    }

    public AdGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setOnTouchListener(this);
        setSoundEffectsEnabled(false);
        setFocusableInTouchMode(true);
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int kEvent;
        if (a(e1, e2)) {
            kEvent = 21;
        } else {
            kEvent = 22;
        }
        onKeyDown(kEvent, null);
        return true;
    }

    private boolean a(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX() + 50.0f;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    public void NX() {
        if (this.bvn) {
            startFlipping();
        } else {
            post(new 1(this));
        }
    }

    public void NY() {
        stopFlipping();
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (1 == event.getAction() || 3 == event.getAction()) {
            startFlipping();
        } else {
            stopFlipping();
        }
        return false;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.USER_PRESENT");
        getContext().registerReceiver(this.aDe, filter, null, this.mHandler);
        if (this.bvm) {
            startFlipping();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.bvn = false;
        getContext().unregisterReceiver(this.aDe);
        NZ();
    }

    protected void onWindowVisibilityChanged(int visibility) {
        boolean z;
        super.onWindowVisibilityChanged(visibility);
        if (visibility == 0) {
            z = true;
        } else {
            z = false;
        }
        this.bvn = z;
        di(false);
    }

    public void startFlipping() {
        this.mStarted = true;
        NZ();
    }

    public void stopFlipping() {
        this.mStarted = false;
        NZ();
    }

    private void NZ() {
        di(true);
    }

    private void di(boolean flipNow) {
        boolean running = this.bvn && this.mStarted && this.bvo;
        if (running != this.mRunning) {
            if (running) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), (long) this.bvl);
            } else {
                this.mHandler.removeMessages(1);
            }
            this.mRunning = running;
        }
    }

    public boolean isFlipping() {
        return this.mStarted;
    }

    public void setAutoStart(boolean autoStart) {
        this.bvm = autoStart;
    }

    public boolean isAutoStart() {
        return this.bvm;
    }
}
