package com.huluxia.widget.menudrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import com.huluxia.framework.base.image.Config;
import com.huluxia.framework.base.log.HLog;

public class OverlayDrawer extends DraggableDrawer {
    private static final String TAG = "OverlayDrawer";
    private int bBN;
    private Runnable bBO = new Runnable(this) {
        final /* synthetic */ OverlayDrawer bBP;

        {
            this.bBP = this$0;
        }

        public void run() {
            int animateTo;
            this.bBP.Pf();
            switch (AnonymousClass2.bAD[this.bBP.getPosition().ordinal()]) {
                case 1:
                case 2:
                    animateTo = -this.bBP.bBN;
                    break;
                default:
                    animateTo = 1;
                    break;
            }
            this.bBP.aN(animateTo, Config.DEFAULT_FADE_DURATION);
        }
    };

    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] bAD = new int[Position.values().length];

        static {
            try {
                bAD[Position.RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                bAD[Position.BOTTOM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                bAD[Position.LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                bAD[Position.TOP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    OverlayDrawer(Activity activity, int dragMode) {
        super(activity, dragMode);
    }

    public OverlayDrawer(Context context) {
        super(context);
    }

    public OverlayDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverlayDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void a(Context context, AttributeSet attrs, int defStyle) {
        super.a(context, attrs, defStyle);
        super.addView(this.bBl, -1, new LayoutParams(-1, -1));
        if (bAU) {
            this.bBl.setLayerType(0, null);
        }
        this.bBl.dj(false);
        super.addView(this.bBk, -1, new LayoutParams(-1, -1));
        this.bBN = md(20);
    }

    protected void g(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int offsetPixels = (int) this.bBJ;
        float openRatio = Math.abs(this.bBJ) / ((float) this.bBm);
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 1:
                this.bAZ.setBounds(0, 0, width + offsetPixels, height);
                break;
            case 2:
                this.bAZ.setBounds(0, 0, width, height + offsetPixels);
                break;
            case 3:
                this.bAZ.setBounds(offsetPixels, 0, width, height);
                break;
            case 4:
                this.bAZ.setBounds(0, offsetPixels, width, height);
                break;
        }
        this.bAZ.setAlpha((int) (((double) (185.0f * openRatio)) * 0.2d));
        this.bAZ.draw(canvas);
    }

    public void dl(boolean animate) {
        int animateTo = 0;
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 1:
            case 2:
                animateTo = -this.bBm;
                break;
            case 3:
            case 4:
                animateTo = this.bBm;
                break;
        }
        h(animateTo, 0, animate);
    }

    public void dm(boolean animate) {
        h(0, 0, animate);
    }

    protected void me(int offsetPixels) {
        if (!bAU) {
            switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
                case 1:
                    this.bBk.offsetLeftAndRight(offsetPixels - (this.bBk.getLeft() - getWidth()));
                    break;
                case 2:
                    this.bBk.offsetTopAndBottom(offsetPixels - (this.bBk.getTop() - getHeight()));
                    break;
                case 3:
                    this.bBk.offsetLeftAndRight(offsetPixels - this.bBk.getRight());
                    break;
                case 4:
                    this.bBk.offsetTopAndBottom(offsetPixels - this.bBk.getBottom());
                    break;
                default:
                    break;
            }
        }
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 1:
                this.bBk.setTranslationX((float) (this.bBm + offsetPixels));
                break;
            case 2:
                this.bBk.setTranslationY((float) (this.bBm + offsetPixels));
                break;
            case 3:
                this.bBk.setTranslationX((float) (offsetPixels - this.bBm));
                break;
            case 4:
                this.bBk.setTranslationY((float) (offsetPixels - this.bBm));
                break;
        }
        invalidate();
    }

    protected void Pi() {
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 1:
            case 2:
                this.bAv.startScroll(0, 0, -this.bBN, 0, 5000);
                return;
            default:
                this.bAv.startScroll(0, 0, this.bBN, 0, 5000);
                return;
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        me((int) this.bBJ);
    }

    protected Orientation getDropShadowOrientation() {
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 1:
                return Orientation.RIGHT_LEFT;
            case 2:
                return Orientation.BOTTOM_TOP;
            case 4:
                return Orientation.TOP_BOTTOM;
            default:
                return Orientation.LEFT_RIGHT;
        }
    }

    protected void Pr() {
        int dropShadowSize = (int) (((float) this.bBe) * (Math.abs(this.bBJ) / ((float) this.bBm)));
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 1:
                this.bBI.top = 0;
                this.bBI.bottom = getHeight();
                this.bBI.right = h.A(this.bBk);
                this.bBI.left = this.bBI.right - dropShadowSize;
                return;
            case 2:
                this.bBI.left = 0;
                this.bBI.right = getWidth();
                this.bBI.bottom = h.B(this.bBk);
                this.bBI.top = this.bBI.bottom - dropShadowSize;
                return;
            case 3:
                this.bBI.top = 0;
                this.bBI.bottom = getHeight();
                this.bBI.left = h.C(this.bBk);
                this.bBI.right = this.bBI.left + dropShadowSize;
                return;
            case 4:
                this.bBI.left = 0;
                this.bBI.right = getWidth();
                this.bBI.top = h.D(this.bBk);
                this.bBI.bottom = this.bBI.top + dropShadowSize;
                return;
            default:
                return;
        }
    }

    protected void Pc() {
        if (bAU && this.bAg && !this.bAB) {
            this.bAB = true;
            this.bBk.setLayerType(2, null);
        }
    }

    protected void Pd() {
        if (this.bAB) {
            this.bAB = false;
            this.bBk.setLayerType(0, null);
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        int height = b - t;
        this.bBl.layout(0, 0, width, height);
        if (bAU) {
            switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
                case 1:
                    this.bBk.layout(width - this.bBm, 0, width, height);
                    return;
                case 2:
                    this.bBk.layout(0, height - this.bBm, width, height);
                    return;
                case 3:
                    this.bBk.layout(0, 0, this.bBm, height);
                    return;
                case 4:
                    this.bBk.layout(0, 0, width, this.bBm);
                    return;
                default:
                    return;
            }
        }
        int offsetPixels = (int) this.bBJ;
        int menuSize = this.bBm;
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 1:
                this.bBk.layout(width + offsetPixels, 0, (width + menuSize) + offsetPixels, height);
                return;
            case 2:
                this.bBk.layout(0, height + offsetPixels, width, (height + menuSize) + offsetPixels);
                return;
            case 3:
                this.bBk.layout((-menuSize) + offsetPixels, 0, offsetPixels, height);
                return;
            case 4:
                this.bBk.layout(0, (-menuSize) + offsetPixels, width, offsetPixels);
                return;
            default:
                return;
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == 0 || heightMode == 0) {
            throw new IllegalStateException("Must measure with an exact size");
        }
        int menuWidthMeasureSpec;
        int menuHeightMeasureSpec;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (this.bBJ == -1.0f) {
            dl(false);
        }
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 2:
            case 4:
                menuWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, width);
                menuHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, this.bBm);
                break;
            default:
                menuWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, this.bBm);
                menuHeightMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, height);
                break;
        }
        this.bBk.measure(menuWidthMeasureSpec, menuHeightMeasureSpec);
        this.bBl.measure(getChildMeasureSpec(widthMeasureSpec, 0, width), getChildMeasureSpec(widthMeasureSpec, 0, height));
        setMeasuredDimension(width, height);
        Px();
    }

    private boolean aP(int x, int y) {
        boolean contentTouch = false;
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 1:
                if (h.A(this.bBk) > x) {
                    contentTouch = true;
                } else {
                    contentTouch = false;
                }
                break;
            case 2:
                contentTouch = h.B(this.bBk) > y;
                break;
            case 3:
                if (h.C(this.bBk) < x) {
                    contentTouch = true;
                } else {
                    contentTouch = false;
                }
                break;
            case 4:
                if (h.D(this.bBk) < y) {
                    contentTouch = true;
                } else {
                    contentTouch = false;
                }
                break;
        }
        return contentTouch;
    }

    protected boolean aQ(int x, int y) {
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 1:
                int width = getWidth();
                int initialMotionX = (int) this.mInitialMotionX;
                if ((this.mMenuVisible || initialMotionX < width - this.bBp) && (!this.mMenuVisible || ((float) initialMotionX) < ((float) width) + this.bBJ)) {
                    return false;
                }
                return true;
            case 2:
                int height = getHeight();
                return (!this.mMenuVisible && this.mInitialMotionY >= ((float) (height - this.bBp))) || (this.mMenuVisible && this.mInitialMotionY >= ((float) height) + this.bBJ);
            case 3:
                if ((this.mMenuVisible || this.mInitialMotionX > ((float) this.bBp)) && (!this.mMenuVisible || this.mInitialMotionX > this.bBJ)) {
                    return false;
                }
                return true;
            case 4:
                if ((this.mMenuVisible || this.mInitialMotionY > ((float) this.bBp)) && (!this.mMenuVisible || this.mInitialMotionY > this.bBJ)) {
                    return false;
                }
                return true;
            default:
                return false;
        }
    }

    protected boolean a(int x, int y, float dx, float dy) {
        if (this.mMenuVisible && this.mTouchMode == 2) {
            return true;
        }
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 1:
                int width = getWidth();
                if ((this.mMenuVisible || this.mInitialMotionX < ((float) (width - this.bBp)) || dx >= 0.0f) && ((!this.mMenuVisible || ((float) x) < ((float) width) - this.bBJ) && (Math.abs(this.bBJ) > ((float) this.bBN) || !this.mMenuVisible))) {
                    return false;
                }
                return true;
            case 2:
                int height = getHeight();
                if ((this.mMenuVisible || this.mInitialMotionY < ((float) (height - this.bBp)) || dy >= 0.0f) && ((!this.mMenuVisible || ((float) x) < ((float) height) - this.bBJ) && (Math.abs(this.bBJ) > ((float) this.bBN) || !this.mMenuVisible))) {
                    return false;
                }
                return true;
            case 3:
                if ((this.mMenuVisible || this.mInitialMotionX > ((float) this.bBp) || dx <= 0.0f) && ((!this.mMenuVisible || ((float) x) > this.bBJ) && (Math.abs(this.bBJ) > ((float) this.bBN) || !this.mMenuVisible))) {
                    return false;
                }
                return true;
            case 4:
                if ((this.mMenuVisible || this.mInitialMotionY > ((float) this.bBp) || dy <= 0.0f) && ((!this.mMenuVisible || ((float) x) > this.bBJ) && (Math.abs(this.bBJ) > ((float) this.bBN) || !this.mMenuVisible))) {
                    return false;
                }
                return true;
            default:
                return false;
        }
    }

    protected void e(float dx, float dy) {
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 1:
                setOffsetPixels(Math.max(Math.min(this.bBJ + dx, 0.0f), (float) (-this.bBm)));
                return;
            case 2:
                setOffsetPixels(Math.max(Math.min(this.bBJ + dy, 0.0f), (float) (-this.bBm)));
                return;
            case 3:
                setOffsetPixels(Math.min(Math.max(this.bBJ + dx, 0.0f), (float) this.bBm));
                return;
            case 4:
                setOffsetPixels(Math.min(Math.max(this.bBJ + dy, 0.0f), (float) this.bBm));
                return;
            default:
                return;
        }
    }

    protected void aR(int x, int y) {
        int i = 0;
        int offsetPixels = (int) this.bBJ;
        int initialVelocity;
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 1:
                int width = getWidth();
                if (this.bAt) {
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.bAw);
                    initialVelocity = (int) a(this.mVelocityTracker);
                    this.mLastMotionX = (float) x;
                    if (initialVelocity <= 0) {
                        i = -this.bBm;
                    }
                    h(i, initialVelocity, true);
                    return;
                } else if (this.mMenuVisible) {
                    Pv();
                    return;
                } else {
                    return;
                }
            case 2:
                if (this.bAt) {
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.bAw);
                    initialVelocity = (int) b(this.mVelocityTracker);
                    this.mLastMotionY = (float) y;
                    if (initialVelocity < 0) {
                        i = -this.bBm;
                    }
                    h(i, initialVelocity, true);
                    return;
                } else if (this.mMenuVisible) {
                    Pv();
                    return;
                } else {
                    return;
                }
            case 3:
                if (this.bAt) {
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.bAw);
                    initialVelocity = (int) a(this.mVelocityTracker);
                    this.mLastMotionX = (float) x;
                    if (initialVelocity > 0) {
                        i = this.bBm;
                    }
                    h(i, initialVelocity, true);
                    return;
                } else if (this.mMenuVisible) {
                    Pv();
                    return;
                } else {
                    return;
                }
            case 4:
                if (this.bAt) {
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.bAw);
                    initialVelocity = (int) b(this.mVelocityTracker);
                    this.mLastMotionY = (float) y;
                    if (initialVelocity > 0) {
                        i = this.bBm;
                    }
                    h(i, initialVelocity, true);
                    return;
                } else if (this.mMenuVisible) {
                    Pv();
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    protected boolean f(float dx, float dy) {
        switch (AnonymousClass2.bAD[getPosition().ordinal()]) {
            case 2:
            case 4:
                if (Math.abs(dy) <= ((float) this.mTouchSlop) || Math.abs(dy) <= Math.abs(dx)) {
                    return false;
                }
                return true;
            default:
                if (Math.abs(dx) <= ((float) this.mTouchSlop) || Math.abs(dx) <= Math.abs(dy)) {
                    return false;
                }
                return true;
        }
    }

    protected void Gi() {
        super.Gi();
        removeCallbacks(this.bBO);
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        removeCallbacks(this.bBO);
        if (this.bAz) {
            Pl();
            aN(0, 5000);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & 255;
        if (action == 1 || action == 3) {
            removeCallbacks(this.bBO);
            this.mActivePointerId = -1;
            this.bAt = false;
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            if (Math.abs(this.bBJ) > ((float) (this.bBm / 2))) {
                Pu();
            } else {
                Pv();
            }
            return false;
        }
        int index;
        if (action == 0 && this.mMenuVisible && Pm()) {
            setOffsetPixels(0.0f);
            Gi();
            Pl();
            setDrawerState(0);
            this.bAt = false;
        }
        if (this.mMenuVisible) {
            index = 0;
            if (this.mActivePointerId != -1) {
                index = ev.findPointerIndex(this.mActivePointerId);
                if (index == -1) {
                    index = 0;
                }
            }
            if (aP((int) ev.getX(index), (int) ev.getY(index))) {
                return true;
            }
        }
        if (!this.mMenuVisible && !this.bAt && this.mTouchMode == 0) {
            return false;
        }
        if (action != 0 && this.bAt) {
            return true;
        }
        switch (action) {
            case 0:
                float x = ev.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                x = ev.getY();
                this.mInitialMotionY = x;
                this.mLastMotionY = x;
                boolean allowDrag = aQ((int) this.mLastMotionX, (int) this.mLastMotionY);
                this.mActivePointerId = ev.getPointerId(0);
                if (allowDrag) {
                    setDrawerState(this.mMenuVisible ? 8 : 0);
                    Gi();
                    Pl();
                    if (!this.mMenuVisible && this.mInitialMotionX <= ((float) this.bBN)) {
                        postDelayed(this.bBO, 160);
                    }
                    this.bAt = false;
                    break;
                }
                break;
            case 2:
                int activePointerId = this.mActivePointerId;
                if (activePointerId != -1) {
                    int pointerIndex = ev.findPointerIndex(activePointerId);
                    if (pointerIndex == -1) {
                        this.bAt = false;
                        this.mActivePointerId = -1;
                        endDrag();
                        dm(true);
                        return false;
                    }
                    float x2 = ev.getX(pointerIndex);
                    float dx = x2 - this.mLastMotionX;
                    float y = ev.getY(pointerIndex);
                    float dy = y - this.mLastMotionY;
                    if (Math.abs(dx) >= ((float) this.mTouchSlop) || Math.abs(dy) >= ((float) this.mTouchSlop)) {
                        removeCallbacks(this.bBO);
                        Pl();
                    }
                    if (f(dx, dy)) {
                        if (this.bBx == null || ((this.mTouchMode != 2 && !this.mMenuVisible) || !B((int) dx, (int) dy, (int) x2, (int) y))) {
                            if (a((int) x2, (int) y, dx, dy)) {
                                Pl();
                                Gi();
                                setDrawerState(2);
                                this.bAt = true;
                                this.mLastMotionX = x2;
                                this.mLastMotionY = y;
                                break;
                            }
                        }
                        endDrag();
                        requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                }
                break;
            case 6:
                d(ev);
                index = ev.findPointerIndex(this.mActivePointerId);
                if (-1 != index) {
                    this.mLastMotionX = ev.getX(index);
                    this.mLastMotionY = ev.getY(index);
                    break;
                }
                this.mLastMotionX = ev.getX(0);
                this.mLastMotionY = ev.getY(0);
                HLog.debug("OverDrawer", "out of range", new Object[0]);
                break;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        return this.bAt;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (!this.mMenuVisible && !this.bAt && this.mTouchMode == 0) {
            return false;
        }
        int action = ev.getAction() & 255;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        int index;
        switch (action) {
            case 0:
                float x = ev.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                x = ev.getY();
                this.mInitialMotionY = x;
                this.mLastMotionY = x;
                boolean allowDrag = aQ((int) this.mLastMotionX, (int) this.mLastMotionY);
                this.mActivePointerId = ev.getPointerId(0);
                if (allowDrag) {
                    Gi();
                    Pl();
                    if (!this.mMenuVisible && this.mLastMotionX <= ((float) this.bBN)) {
                        postDelayed(this.bBO, 160);
                    }
                    Pc();
                    break;
                }
                break;
            case 1:
            case 3:
                removeCallbacks(this.bBO);
                index = ev.findPointerIndex(this.mActivePointerId);
                if (index == -1) {
                    index = 0;
                }
                aR((int) ev.getX(index), (int) ev.getY(index));
                this.mActivePointerId = -1;
                this.bAt = false;
                break;
            case 2:
                int pointerIndex = ev.findPointerIndex(this.mActivePointerId);
                if (pointerIndex != -1) {
                    float x2;
                    float dx;
                    float y;
                    float dy;
                    if (!this.bAt) {
                        x2 = ev.getX(pointerIndex);
                        dx = x2 - this.mLastMotionX;
                        y = ev.getY(pointerIndex);
                        dy = y - this.mLastMotionY;
                        if (f(dx, dy)) {
                            if (a((int) x2, (int) y, dx, dy)) {
                                Pl();
                                Gi();
                                setDrawerState(2);
                                this.bAt = true;
                                this.mLastMotionX = x2;
                                this.mLastMotionY = y;
                            } else {
                                this.mInitialMotionX = x2;
                                this.mInitialMotionY = y;
                            }
                        }
                    }
                    if (this.bAt) {
                        Pc();
                        x2 = ev.getX(pointerIndex);
                        dx = x2 - this.mLastMotionX;
                        y = ev.getY(pointerIndex);
                        dy = y - this.mLastMotionY;
                        this.mLastMotionX = x2;
                        this.mLastMotionY = y;
                        e(dx, dy);
                        break;
                    }
                }
                this.bAt = false;
                this.mActivePointerId = -1;
                endDrag();
                dm(true);
                return false;
                break;
            case 5:
                index = (ev.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                this.mLastMotionX = ev.getX(index);
                this.mLastMotionY = ev.getY(index);
                this.mActivePointerId = ev.getPointerId(index);
                break;
            case 6:
                d(ev);
                this.mLastMotionX = ev.getX(ev.findPointerIndex(this.mActivePointerId));
                this.mLastMotionY = ev.getY(ev.findPointerIndex(this.mActivePointerId));
                break;
        }
        return true;
    }

    private void d(MotionEvent ev) {
        int pointerIndex = ev.getActionIndex();
        if (ev.getPointerId(pointerIndex) == this.mActivePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.mLastMotionX = ev.getX(newPointerIndex);
            this.mActivePointerId = ev.getPointerId(newPointerIndex);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }
}
