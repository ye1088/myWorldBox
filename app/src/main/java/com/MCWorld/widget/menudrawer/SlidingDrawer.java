package com.MCWorld.widget.menudrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;

public class SlidingDrawer extends DraggableDrawer {
    private static final String TAG = "OverlayDrawer";

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] bAD = new int[Position.values().length];

        static {
            try {
                bAD[Position.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                bAD[Position.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                bAD[Position.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                bAD[Position.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    SlidingDrawer(Activity activity, int dragMode) {
        super(activity, dragMode);
    }

    public SlidingDrawer(Context context) {
        super(context);
    }

    public SlidingDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void a(Context context, AttributeSet attrs, int defStyle) {
        super.a(context, attrs, defStyle);
        super.addView(this.bBk, -1, new LayoutParams(-1, -1));
        super.addView(this.bBl, -1, new LayoutParams(-1, -1));
    }

    public void dl(boolean animate) {
        int animateTo = 0;
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
            case 1:
            case 2:
                animateTo = this.bBm;
                break;
            case 3:
            case 4:
                animateTo = -this.bBm;
                break;
        }
        h(animateTo, 0, animate);
    }

    public void dm(boolean animate) {
        h(0, 0, animate);
    }

    protected void me(int offsetPixels) {
        if (!bAU) {
            switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
                case 2:
                case 4:
                    this.bBl.offsetTopAndBottom(offsetPixels - this.bBl.getTop());
                    break;
                default:
                    this.bBl.offsetLeftAndRight(offsetPixels - this.bBl.getLeft());
                    break;
            }
        }
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
            case 2:
            case 4:
                this.bBl.setTranslationY((float) offsetPixels);
                break;
            default:
                this.bBl.setTranslationX((float) offsetPixels);
                break;
        }
        mh(offsetPixels);
        invalidate();
    }

    protected void Pi() {
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
            case 3:
            case 4:
                this.bAv.startScroll(0, 0, (-this.bBm) / 3, 0, 5000);
                return;
            default:
                this.bAv.startScroll(0, 0, this.bBm / 3, 0, 5000);
                return;
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        me((int) this.bBJ);
    }

    protected void g(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int offsetPixels = (int) this.bBJ;
        float openRatio = Math.abs(this.bBJ) / ((float) this.bBm);
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
            case 1:
                this.bAZ.setBounds(0, 0, offsetPixels, height);
                break;
            case 2:
                this.bAZ.setBounds(0, 0, width, offsetPixels);
                break;
            case 3:
                this.bAZ.setBounds(width + offsetPixels, 0, width, height);
                break;
            case 4:
                this.bAZ.setBounds(0, height + offsetPixels, width, height);
                break;
        }
        this.bAZ.setAlpha((int) (185.0f * (1.0f - openRatio)));
        this.bAZ.draw(canvas);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        int height = b - t;
        if (bAU) {
            this.bBl.layout(0, 0, width, height);
        } else {
            int offsetPixels = (int) this.bBJ;
            if (getPosition() == Position.LEFT || getPosition() == Position.RIGHT) {
                this.bBl.layout(offsetPixels, 0, width + offsetPixels, height);
            } else {
                this.bBl.layout(0, offsetPixels, width, height + offsetPixels);
            }
        }
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
            case 1:
                this.bBk.layout(0, 0, this.bBm, height);
                return;
            case 2:
                this.bBk.layout(0, 0, width, this.bBm);
                return;
            case 3:
                this.bBk.layout(width - this.bBm, 0, width, height);
                return;
            case 4:
                this.bBk.layout(0, height - this.bBm, width, height);
                return;
            default:
                return;
        }
    }

    private void mh(int offsetPixels) {
        int i = 4;
        if (this.bAx && this.bBm != 0) {
            int width = getWidth();
            int height = getHeight();
            int menuSize = this.bBm;
            int offset = (int) ((-0.25f * ((1.0f - (Math.abs(this.bBJ) / ((float) menuSize))) * ((float) menuSize))) * ((float) ((int) (this.bBJ / Math.abs(this.bBJ)))));
            BuildLayerFrameLayout buildLayerFrameLayout;
            switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
                case 1:
                    if (!bAU) {
                        this.bBk.offsetLeftAndRight(offset - this.bBk.getLeft());
                        buildLayerFrameLayout = this.bBk;
                        if (offsetPixels != 0) {
                            i = 0;
                        }
                        buildLayerFrameLayout.setVisibility(i);
                        return;
                    } else if (offsetPixels > 0) {
                        this.bBk.setTranslationX((float) offset);
                        return;
                    } else {
                        this.bBk.setTranslationX((float) (-menuSize));
                        return;
                    }
                case 2:
                    if (!bAU) {
                        this.bBk.offsetTopAndBottom(offset - this.bBk.getTop());
                        buildLayerFrameLayout = this.bBk;
                        if (offsetPixels != 0) {
                            i = 0;
                        }
                        buildLayerFrameLayout.setVisibility(i);
                        return;
                    } else if (offsetPixels > 0) {
                        this.bBk.setTranslationY((float) offset);
                        return;
                    } else {
                        this.bBk.setTranslationY((float) (-menuSize));
                        return;
                    }
                case 3:
                    if (!bAU) {
                        this.bBk.offsetLeftAndRight(offset - (this.bBk.getRight() - width));
                        buildLayerFrameLayout = this.bBk;
                        if (offsetPixels != 0) {
                            i = 0;
                        }
                        buildLayerFrameLayout.setVisibility(i);
                        return;
                    } else if (offsetPixels != 0) {
                        this.bBk.setTranslationX((float) offset);
                        return;
                    } else {
                        this.bBk.setTranslationX((float) menuSize);
                        return;
                    }
                case 4:
                    if (!bAU) {
                        this.bBk.offsetTopAndBottom(offset - (this.bBk.getBottom() - height));
                        buildLayerFrameLayout = this.bBk;
                        if (offsetPixels != 0) {
                            i = 0;
                        }
                        buildLayerFrameLayout.setVisibility(i);
                        return;
                    } else if (offsetPixels != 0) {
                        this.bBk.setTranslationY((float) offset);
                        return;
                    } else {
                        this.bBk.setTranslationY((float) menuSize);
                        return;
                    }
                default:
                    return;
            }
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
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
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
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
            case 1:
                if (h.A(this.bBl) < x) {
                    contentTouch = true;
                } else {
                    contentTouch = false;
                }
                break;
            case 2:
                if (h.B(this.bBl) < y) {
                    contentTouch = true;
                } else {
                    contentTouch = false;
                }
                break;
            case 3:
                if (h.C(this.bBl) > x) {
                    contentTouch = true;
                } else {
                    contentTouch = false;
                }
                break;
            case 4:
                contentTouch = h.D(this.bBl) > y;
                break;
        }
        return contentTouch;
    }

    protected boolean aQ(int x, int y) {
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
            case 1:
                if ((this.mMenuVisible || this.mInitialMotionX > ((float) this.bBp)) && (!this.mMenuVisible || this.mInitialMotionX < this.bBJ)) {
                    return false;
                }
                return true;
            case 2:
                if ((this.mMenuVisible || this.mInitialMotionY > ((float) this.bBp)) && (!this.mMenuVisible || this.mInitialMotionY < this.bBJ)) {
                    return false;
                }
                return true;
            case 3:
                int width = getWidth();
                int initialMotionX = (int) this.mInitialMotionX;
                if ((this.mMenuVisible || initialMotionX < width - this.bBp) && (!this.mMenuVisible || ((float) initialMotionX) > ((float) width) + this.bBJ)) {
                    return false;
                }
                return true;
            case 4:
                int height = getHeight();
                return (!this.mMenuVisible && this.mInitialMotionY >= ((float) (height - this.bBp))) || (this.mMenuVisible && this.mInitialMotionY <= ((float) height) + this.bBJ);
            default:
                return false;
        }
    }

    protected boolean a(int x, int y, float dx, float dy) {
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
            case 1:
                if ((this.mMenuVisible || this.mInitialMotionX > ((float) this.bBp) || dx <= 0.0f) && (!this.mMenuVisible || ((float) x) < this.bBJ)) {
                    return false;
                }
                return true;
            case 2:
                if ((this.mMenuVisible || this.mInitialMotionY > ((float) this.bBp) || dy <= 0.0f) && (!this.mMenuVisible || ((float) y) < this.bBJ)) {
                    return false;
                }
                return true;
            case 3:
                int width = getWidth();
                if ((this.mMenuVisible || this.mInitialMotionX < ((float) (width - this.bBp)) || dx >= 0.0f) && (!this.mMenuVisible || ((float) x) > ((float) width) + this.bBJ)) {
                    return false;
                }
                return true;
            case 4:
                int height = getHeight();
                return (!this.mMenuVisible && this.mInitialMotionY >= ((float) (height - this.bBp)) && dy < 0.0f) || (this.mMenuVisible && ((float) y) <= ((float) height) + this.bBJ);
            default:
                return false;
        }
    }

    protected void e(float dx, float dy) {
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
            case 1:
                setOffsetPixels(Math.min(Math.max(this.bBJ + dx, 0.0f), (float) this.bBm));
                return;
            case 2:
                setOffsetPixels(Math.min(Math.max(this.bBJ + dy, 0.0f), (float) this.bBm));
                return;
            case 3:
                setOffsetPixels(Math.max(Math.min(this.bBJ + dx, 0.0f), (float) (-this.bBm)));
                return;
            case 4:
                setOffsetPixels(Math.max(Math.min(this.bBJ + dy, 0.0f), (float) (-this.bBm)));
                return;
            default:
                return;
        }
    }

    protected void aR(int x, int y) {
        int i = 0;
        int offsetPixels = (int) this.bBJ;
        int initialVelocity;
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
            case 1:
                if (this.bAt) {
                    this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.bAw);
                    initialVelocity = (int) a(this.mVelocityTracker);
                    this.mLastMotionX = (float) x;
                    if (initialVelocity > 0) {
                        i = this.bBm;
                    }
                    h(i, initialVelocity, true);
                    return;
                } else if (this.mMenuVisible && x > offsetPixels) {
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
                    if (initialVelocity > 0) {
                        i = this.bBm;
                    }
                    h(i, initialVelocity, true);
                    return;
                } else if (this.mMenuVisible && y > offsetPixels) {
                    Pv();
                    return;
                } else {
                    return;
                }
            case 3:
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
                } else if (this.mMenuVisible && x < width + offsetPixels) {
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
                    if (initialVelocity < 0) {
                        i = -this.bBm;
                    }
                    h(i, initialVelocity, true);
                    return;
                } else if (this.mMenuVisible && y < getHeight() + offsetPixels) {
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
        switch (AnonymousClass1.bAD[getPosition().ordinal()]) {
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

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & 255;
        if (action == 1 || action == 3) {
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
        if (action == 0 && this.mMenuVisible && Pm()) {
            setOffsetPixels(0.0f);
            Gi();
            Pl();
            setDrawerState(0);
            this.bAt = false;
        }
        if (this.mMenuVisible) {
            int index = 0;
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
                    if (f(dx, dy)) {
                        if (this.bBx == null || ((this.mTouchMode != 2 && !this.mMenuVisible) || !B((int) dx, (int) dy, (int) x2, (int) y))) {
                            if (a((int) x2, (int) y, dx, dy)) {
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
                this.mLastMotionX = ev.getX(ev.findPointerIndex(this.mActivePointerId));
                this.mLastMotionY = ev.getY(ev.findPointerIndex(this.mActivePointerId));
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
                    Pc();
                    break;
                }
                break;
            case 1:
            case 3:
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
