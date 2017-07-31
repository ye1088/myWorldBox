package com.huluxia.widget.menudrawer;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

public abstract class DraggableDrawer extends MenuDrawer {
    private static final int CLOSE_ENOUGH = 3;
    protected static final int INVALID_POINTER = -1;
    private static final String bAm = "net.simonvt.menudrawer.MenuDrawer.menuVisible";
    private static final Interpolator bAn = new c();
    protected static final int bAo = 185;
    private static final long bAp = 5000;
    private static final long bAq = 10000;
    protected static final int bAr = 5000;
    private d bAA;
    protected boolean bAB;
    private final Runnable bAs = new Runnable(this) {
        final /* synthetic */ DraggableDrawer bAC;

        {
            this.bAC = this$0;
        }

        public void run() {
            this.bAC.Pg();
        }
    };
    protected boolean bAt;
    protected long bAu;
    protected d bAv;
    protected int bAw;
    protected boolean bAx = true;
    private Runnable bAy;
    protected boolean bAz;
    protected int mActivePointerId = -1;
    protected int mCloseEnough;
    protected float mInitialMotionX;
    protected float mInitialMotionY;
    protected float mLastMotionX = -1.0f;
    protected float mLastMotionY = -1.0f;
    protected final Runnable mPeekRunnable = new Runnable(this) {
        final /* synthetic */ DraggableDrawer bAC;

        {
            this.bAC = this$0;
        }

        public void run() {
            this.bAC.Pj();
        }
    };
    protected int mTouchSlop;
    protected VelocityTracker mVelocityTracker;

    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] bAD = new int[Position.values().length];

        static {
            try {
                bAD[Position.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                bAD[Position.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                bAD[Position.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                bAD[Position.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    protected abstract void Pi();

    DraggableDrawer(Activity activity, int dragMode) {
        super(activity, dragMode);
    }

    public DraggableDrawer(Context context) {
        super(context);
    }

    public DraggableDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DraggableDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void a(Context context, AttributeSet attrs, int defStyle) {
        super.a(context, attrs, defStyle);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.bAw = configuration.getScaledMaximumFlingVelocity();
        this.bAA = new d(context, MenuDrawer.bAX);
        this.bAv = new d(context, bAn);
        this.mCloseEnough = md(3);
    }

    public void dk(boolean animate) {
        if (this.mDrawerState == 8 || this.mDrawerState == 4) {
            dm(animate);
        } else if (this.mDrawerState == 0 || this.mDrawerState == 1) {
            dl(animate);
        }
    }

    public boolean isMenuVisible() {
        return this.mMenuVisible;
    }

    public void setMenuSize(int size) {
        this.bBm = size;
        if (this.mDrawerState == 8 || this.mDrawerState == 4) {
            setOffsetPixels((float) this.bBm);
        }
        requestLayout();
        invalidate();
    }

    public void setOffsetMenuEnabled(boolean offsetMenu) {
        if (offsetMenu != this.bAx) {
            this.bAx = offsetMenu;
            requestLayout();
            invalidate();
        }
    }

    public boolean getOffsetMenuEnabled() {
        return this.bAx;
    }

    public void peekDrawer() {
        k(bAp, bAq);
    }

    public void bL(long delay) {
        k(bAp, delay);
    }

    public void k(long startDelay, long delay) {
        if (startDelay < 0) {
            throw new IllegalArgumentException("startDelay must be zero or larger.");
        } else if (delay < 0) {
            throw new IllegalArgumentException("delay must be zero or larger");
        } else {
            removeCallbacks(this.mPeekRunnable);
            removeCallbacks(this.bAy);
            this.bAu = delay;
            this.bAy = new Runnable(this) {
                final /* synthetic */ DraggableDrawer bAC;

                {
                    this.bAC = this$0;
                }

                public void run() {
                    this.bAC.Ph();
                }
            };
            postDelayed(this.bAy, startDelay);
        }
    }

    public void setHardwareLayerEnabled(boolean enabled) {
        if (enabled != this.bAg) {
            this.bAg = enabled;
            this.bBk.dj(enabled);
            this.bBl.dj(enabled);
            Pd();
        }
    }

    public int getTouchMode() {
        return this.mTouchMode;
    }

    public void setTouchMode(int mode) {
        if (this.mTouchMode != mode) {
            this.mTouchMode = mode;
            Px();
        }
    }

    public void setTouchBezelSize(int size) {
        this.bBo = size;
    }

    public int getTouchBezelSize() {
        return this.bBo;
    }

    protected void Pc() {
        if (bAU && this.bAg && !this.bAB) {
            this.bAB = true;
            this.bBl.setLayerType(2, null);
            this.bBk.setLayerType(2, null);
        }
    }

    protected void Pd() {
        if (this.bAB) {
            this.bAB = false;
            this.bBl.setLayerType(0, null);
            this.bBk.setLayerType(0, null);
        }
    }

    protected void endDrag() {
        this.bAt = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    protected void Gi() {
        removeCallbacks(this.bAs);
        this.bAA.abortAnimation();
        Pd();
    }

    private void Pe() {
        this.bAA.abortAnimation();
        int finalX = this.bAA.getFinalX();
        setOffsetPixels((float) finalX);
        setDrawerState(finalX == 0 ? 0 : 8);
        Pd();
    }

    protected void Pf() {
        long now = SystemClock.uptimeMillis();
        MotionEvent cancelEvent = MotionEvent.obtain(now, now, 3, 0.0f, 0.0f, 0);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchTouchEvent(cancelEvent);
        }
        this.bBl.dispatchTouchEvent(cancelEvent);
        cancelEvent.recycle();
    }

    protected void h(int position, int velocity, boolean animate) {
        endDrag();
        Pl();
        int dx = position - ((int) this.bBJ);
        if (dx == 0 || !animate) {
            setOffsetPixels((float) position);
            setDrawerState(position == 0 ? 0 : 8);
            Pd();
            return;
        }
        int duration;
        velocity = Math.abs(velocity);
        if (velocity > 0) {
            duration = Math.round(1000.0f * Math.abs(((float) dx) / ((float) velocity))) * 4;
        } else {
            duration = (int) (600.0f * Math.abs(((float) dx) / ((float) this.bBm)));
        }
        aN(position, Math.min(duration, this.bBw));
    }

    protected void aN(int position, int duration) {
        int startX = (int) this.bBJ;
        int dx = position - startX;
        if (dx > 0) {
            setDrawerState(4);
            this.bAA.startScroll(startX, 0, dx, 0, duration);
        } else {
            setDrawerState(1);
            this.bAA.startScroll(startX, 0, dx, 0, duration);
        }
        Pc();
        Pg();
    }

    private void Pg() {
        if (this.bAA.computeScrollOffset()) {
            int oldX = (int) this.bBJ;
            int x = this.bAA.getCurrX();
            if (x != oldX) {
                setOffsetPixels((float) x);
            }
            if (x != this.bAA.getFinalX()) {
                postOnAnimation(this.bAs);
                return;
            }
        }
        Pe();
    }

    protected void Ph() {
        this.bAz = true;
        Pi();
        Pc();
        Pj();
    }

    private void Pj() {
        if (this.bAv.computeScrollOffset()) {
            int oldX = (int) this.bBJ;
            int x = this.bAv.getCurrX();
            if (x != oldX) {
                setOffsetPixels((float) x);
            }
            if (!this.bAv.isFinished()) {
                postOnAnimation(this.mPeekRunnable);
                return;
            } else if (this.bAu > 0) {
                this.bAy = new Runnable(this) {
                    final /* synthetic */ DraggableDrawer bAC;

                    {
                        this.bAC = this$0;
                    }

                    public void run() {
                        this.bAC.Ph();
                    }
                };
                postDelayed(this.bAy, this.bAu);
            }
        }
        Pk();
    }

    private void Pk() {
        this.bAv.abortAnimation();
        setOffsetPixels(0.0f);
        setDrawerState(0);
        Pd();
        this.bAz = false;
    }

    protected void Pl() {
        removeCallbacks(this.bAy);
        removeCallbacks(this.mPeekRunnable);
        Pd();
        this.bAz = false;
    }

    protected boolean Pm() {
        return Math.abs(this.bBJ) <= ((float) this.mCloseEnough);
    }

    protected boolean B(int dx, int dy, int x, int y) {
        switch (AnonymousClass5.bAD[getPosition().ordinal()]) {
            case 1:
            case 2:
                if (this.mMenuVisible) {
                    return a(this.bBk, false, dx, x - h.A(this.bBk), y - h.B(this.bBl));
                }
                return a(this.bBl, false, dx, x - h.A(this.bBl), y - h.B(this.bBl));
            case 3:
            case 4:
                if (this.mMenuVisible) {
                    return b(this.bBk, false, dy, x - h.A(this.bBk), y - h.B(this.bBl));
                }
                return b(this.bBl, false, dy, x - h.A(this.bBl), y - h.B(this.bBl));
            default:
                return false;
        }
    }

    protected boolean a(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            for (int i = group.getChildCount() - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                int childLeft = child.getLeft() + y(child);
                int childRight = child.getRight() + y(child);
                int childTop = child.getTop() + x(child);
                int childBottom = child.getBottom() + x(child);
                if (x >= childLeft && x < childRight && y >= childTop && y < childBottom) {
                    if (a(child, true, dx, x - childLeft, y - childTop)) {
                        return true;
                    }
                }
            }
        }
        return checkV && this.bBx.a(v, dx, x, y);
    }

    protected boolean b(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            for (int i = group.getChildCount() - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                int childLeft = child.getLeft() + y(child);
                int childRight = child.getRight() + y(child);
                int childTop = child.getTop() + x(child);
                int childBottom = child.getBottom() + x(child);
                if (x >= childLeft && x < childRight && y >= childTop && y < childBottom) {
                    if (b(child, true, dx, x - childLeft, y - childTop)) {
                        return true;
                    }
                }
            }
        }
        return checkV && this.bBx.a(v, dx, x, y);
    }

    protected float a(VelocityTracker velocityTracker) {
        if (VERSION.SDK_INT >= 8) {
            return velocityTracker.getXVelocity(this.mActivePointerId);
        }
        return velocityTracker.getXVelocity();
    }

    protected float b(VelocityTracker velocityTracker) {
        if (VERSION.SDK_INT >= 8) {
            return velocityTracker.getYVelocity(this.mActivePointerId);
        }
        return velocityTracker.getYVelocity();
    }

    private int x(View v) {
        if (VERSION.SDK_INT >= 11) {
            return (int) v.getTranslationY();
        }
        return 0;
    }

    private int y(View v) {
        if (VERSION.SDK_INT >= 11) {
            return (int) v.getTranslationX();
        }
        return 0;
    }

    void c(Bundle state) {
        boolean menuVisible = this.mDrawerState == 8 || this.mDrawerState == 4;
        state.putBoolean(bAm, menuVisible);
    }

    public void a(Parcelable in) {
        int i = 0;
        super.a(in);
        boolean menuOpen = ((Bundle) in).getBoolean(bAm);
        if (menuOpen) {
            dl(false);
        } else {
            setOffsetPixels(0.0f);
        }
        if (menuOpen) {
            i = 8;
        }
        this.mDrawerState = i;
    }
}
