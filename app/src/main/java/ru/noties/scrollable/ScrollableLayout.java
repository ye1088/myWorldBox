package ru.noties.scrollable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import com.nineoldandroids.animation.FloatEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.util.Property;
import ru.noties.scrollable.l.b;

public class ScrollableLayout extends FrameLayout {
    private static final long cns = 200;
    private static final int cnt = 100;
    private boolean cnA;
    private boolean cnB;
    private b cnC;
    private c cnD;
    private ObjectAnimator cnE;
    private boolean cnF;
    private boolean cnG;
    private boolean cnH;
    private e cnI;
    private d cnJ;
    private View cnK;
    private boolean cnL;
    private final Rect cnM = new Rect();
    private long cnN;
    private int cnO;
    private long cnP;
    private long cnQ;
    private j cnR;
    private boolean cnS;
    private OnGlobalLayoutListener cnT;
    private int cnU;
    private final Runnable cnV = new 4(this);
    private final Runnable cnW = new 5(this);
    private final Property<ScrollableLayout, Integer> cnX = new 6(this, Integer.class, "scrollY");
    private m cnu;
    private GestureDetector cnv;
    private GestureDetector cnw;
    private b cnx;
    private k cny;
    private int cnz;

    public ScrollableLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ScrollableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray array = context.obtainStyledAttributes(attributeSet, b.ScrollableLayout);
        try {
            this.cnu = a(context, null, array.getBoolean(b.ScrollableLayout_scrollable_scrollerFlywheel, false));
            float friction = array.getFloat(b.ScrollableLayout_scrollable_friction, Float.NaN);
            if (friction == friction) {
                setFriction(friction);
            }
            this.cnz = array.getDimensionPixelSize(b.ScrollableLayout_scrollable_maxScroll, 0);
            this.cnS = array.getBoolean(b.ScrollableLayout_scrollable_autoMaxScroll, this.cnz == 0);
            this.cnU = array.getResourceId(b.ScrollableLayout_scrollable_autoMaxScrollViewId, 0);
            setConsiderIdleMillis((long) array.getInteger(b.ScrollableLayout_scrollable_considerIdleMillis, 100));
            if (array.getBoolean(b.ScrollableLayout_scrollable_defaultCloseUp, false)) {
                setCloseUpAlgorithm(new f());
            }
            int closeUpAnimationMillis = array.getInteger(b.ScrollableLayout_scrollable_closeUpAnimationMillis, -1);
            if (closeUpAnimationMillis != -1) {
                setCloseUpIdleAnimationTime(new n((long) closeUpAnimationMillis));
            }
            int interpolatorResId = array.getResourceId(b.ScrollableLayout_scrollable_closeUpAnimatorInterpolator, 0);
            if (interpolatorResId != 0) {
                setCloseAnimatorConfigurator(new i(AnimationUtils.loadInterpolator(context, interpolatorResId)));
            }
            array.recycle();
            setVerticalScrollBarEnabled(true);
            this.cnv = new GestureDetector(context, new d(this, null));
            this.cnw = new GestureDetector(context, new a(this, context));
            this.cnC = new b(new 1(this));
        } catch (Throwable th) {
            array.recycle();
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.cnS) {
            el(true);
        }
    }

    protected m a(Context context, Interpolator interpolator, boolean flywheel) {
        return new m(context, interpolator, flywheel);
    }

    public void setFriction(float friction) {
        this.cnu.setFriction(friction);
    }

    public void setCanScrollVerticallyDelegate(b delegate) {
        this.cnx = delegate;
    }

    public b getCanScrollVerticallyDelegate() {
        return this.cnx;
    }

    public void setMaxScrollY(int maxY) {
        this.cnz = maxY;
    }

    public int getMaxScrollY() {
        return this.cnz;
    }

    public void setConsiderIdleMillis(long millis) {
        this.cnN = millis;
    }

    public long getConsiderIdleMillis() {
        return this.cnN;
    }

    public void setOnScrollChangedListener(k listener) {
        this.cny = listener;
    }

    public void setOnFlingOverListener(j onFlingOverListener) {
        this.cnR = onFlingOverListener;
    }

    public void onScrollChanged(int l, int t, int oldL, int oldT) {
        boolean changed = t != oldT;
        if (changed && this.cny != null) {
            this.cny.ad(t, oldT, this.cnz);
        }
        if (this.cnD != null) {
            removeCallbacks(this.cnW);
            if (!this.cnF && changed && !this.cnH) {
                postDelayed(this.cnW, this.cnN);
            }
        }
    }

    public void setSelfUpdateScroll(boolean value) {
        this.cnF = value;
    }

    public boolean Zi() {
        return this.cnF;
    }

    public void setCloseUpAlgorithm(c closeUpAlgorithm) {
        this.cnD = closeUpAlgorithm;
    }

    public void setCloseUpIdleAnimationTime(e closeUpIdleAnimationTime) {
        this.cnI = closeUpIdleAnimationTime;
    }

    public void setCloseAnimatorConfigurator(d configurator) {
        this.cnJ = configurator;
    }

    public ValueAnimator oR(int scrollY) {
        int startY = getScrollY();
        int diff = scrollY - startY;
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.setEvaluator(new FloatEvaluator());
        animator.addUpdateListener(new 2(this, startY, diff));
        animator.addListener(new f(this, null));
        return animator;
    }

    public void scrollTo(int x, int y) {
        int newY = oS(y);
        if (newY >= 0) {
            super.scrollTo(0, newY);
        }
    }

    public void setAutoMaxScroll(boolean autoMaxScroll) {
        this.cnS = autoMaxScroll;
        el(this.cnS);
    }

    public boolean Zj() {
        return this.cnS;
    }

    protected void el(boolean autoMaxScroll) {
        if (getChildCount() != 0) {
            View view;
            if (this.cnU != 0) {
                view = findViewById(this.cnU);
            } else {
                view = getChildAt(0);
            }
            if (view == null) {
                return;
            }
            if (autoMaxScroll) {
                if (this.cnT == null) {
                    this.cnT = new 3(this, view);
                    view.getViewTreeObserver().addOnGlobalLayoutListener(this.cnT);
                }
            } else if (this.cnT != null) {
                o.a(view, this.cnT);
                this.cnT = null;
            }
        }
    }

    protected int oS(int y) {
        int currentY = getScrollY();
        if (currentY == y) {
            return -1;
        }
        int direction = y - currentY;
        boolean isScrollingBottomTop = direction < 0;
        if (this.cnx != null) {
            if (isScrollingBottomTop) {
                if (!(this.cnL || this.cnF || !this.cnx.canScrollVertically(direction))) {
                    return -1;
                }
            } else if (currentY == this.cnz && !this.cnx.canScrollVertically(direction)) {
                return -1;
            }
        }
        if (y < 0) {
            y = 0;
        } else if (y > this.cnz) {
            y = this.cnz;
        }
        return y;
    }

    public void setDraggableView(View view) {
        this.cnK = view;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (this.cnF) {
            this.cnH = false;
            this.cnL = false;
            this.cnA = false;
            this.cnB = false;
            removeCallbacks(this.cnW);
            removeCallbacks(this.cnV);
            return super.dispatchTouchEvent(event);
        }
        int action = event.getActionMasked();
        if (action == 0) {
            this.cnH = true;
            this.cnu.abortAnimation();
            if (this.cnK == null || !this.cnK.getGlobalVisibleRect(this.cnM)) {
                this.cnL = false;
            } else {
                this.cnL = this.cnM.contains((int) (event.getRawX() + 0.5f), (int) (event.getRawY() + 0.5f));
            }
        } else if (action == 1 || action == 3) {
            this.cnH = false;
            if (this.cnD != null) {
                removeCallbacks(this.cnW);
                postDelayed(this.cnW, this.cnN);
            }
        }
        boolean isPrevScrolling = this.cnA;
        boolean isPrevFlinging = this.cnB;
        this.cnB = this.cnw.onTouchEvent(event);
        this.cnA = this.cnv.onTouchEvent(event);
        removeCallbacks(this.cnV);
        post(this.cnV);
        boolean isIntercepted = this.cnA || this.cnB;
        boolean isPrevIntercepted = isPrevScrolling || isPrevFlinging;
        boolean shouldRedirectDownTouch = action == 2 && !isIntercepted && isPrevIntercepted && getScrollY() == this.cnz;
        if (isIntercepted || isPrevIntercepted) {
            this.cnC.a(event, 3);
            if (!isPrevIntercepted) {
                return true;
            }
        }
        if (shouldRedirectDownTouch) {
            this.cnC.a(event, 0);
        }
        super.dispatchTouchEvent(event);
        return true;
    }

    private void em(boolean removeCallbacks) {
        if (removeCallbacks) {
            removeCallbacks(this.cnW);
        }
        if (this.cnE != null && this.cnE.isRunning()) {
            this.cnE.cancel();
        }
    }

    public void computeScroll() {
        if (this.cnu.computeScrollOffset()) {
            int oldY = getScrollY();
            int nowY = this.cnu.getCurrY();
            scrollTo(0, nowY);
            if (oldY != nowY) {
                onScrollChanged(0, getScrollY(), 0, oldY);
            }
            postInvalidate();
        }
    }

    protected int computeVerticalScrollRange() {
        return this.cnz;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childTop = 0;
        int i = 0;
        while (i < getChildCount()) {
            try {
                View view = getChildAt(i);
                view.layout(left, childTop, right, view.getMeasuredHeight() + childTop);
                childTop += view.getMeasuredHeight();
                i++;
            } catch (StringIndexOutOfBoundsException e) {
                return;
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        e savedState = new e(super.onSaveInstanceState());
        savedState.scrollY = getScrollY();
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof e) {
            e in = (e) state;
            super.onRestoreInstanceState(in.getSuperState());
            if (VERSION.SDK_INT > 14) {
                setScrollY(in.scrollY);
                return;
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
