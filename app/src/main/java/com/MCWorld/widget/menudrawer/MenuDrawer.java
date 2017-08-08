package com.MCWorld.widget.menudrawer;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.n;
import com.MCWorld.bbs.b.o;
import com.MCWorld.video.recorder.b;
import com.MCWorld.widget.menudrawer.compat.a;

public abstract class MenuDrawer extends ViewGroup {
    protected static final int ANIMATION_DELAY = 16;
    private static final boolean DEBUG = false;
    public static final int STATE_DRAGGING = 2;
    private static final String TAG = "MenuDrawer";
    private static final int bAJ = 24;
    private static final int bAK = 6;
    public static final int bAL = 0;
    public static final int bAM = 1;
    public static final int bAN = 0;
    public static final int bAO = 1;
    public static final int bAP = 2;
    public static final int bAQ = 0;
    public static final int bAR = 1;
    public static final int bAS = 4;
    public static final int bAT = 8;
    static final boolean bAU;
    static final int bAV = 800;
    private static final int bAW = 600;
    protected static final Interpolator bAX = new g();
    protected static final Interpolator bAY = new AccelerateInterpolator();
    protected Drawable bAZ;
    protected boolean bAg;
    private a bBA;
    private int bBB;
    private int bBC;
    private int bBD;
    private Position bBE;
    private Position bBF;
    private final Rect bBG;
    protected boolean bBH;
    protected final Rect bBI;
    protected float bBJ;
    protected boolean bBK;
    private OnScrollChangedListener bBL;
    protected boolean bBa;
    protected int bBb;
    protected Drawable bBc;
    private boolean bBd;
    protected int bBe;
    protected Bitmap bBf;
    protected View bBg;
    protected int bBh;
    private boolean bBi;
    protected final Rect bBj;
    protected BuildLayerFrameLayout bBk;
    protected BuildLayerFrameLayout bBl;
    protected int bBm;
    private int bBn;
    protected int bBo;
    protected int bBp;
    private a bBq;
    private b bBr;
    private Runnable bBs;
    protected int bBt;
    protected float bBu;
    protected boolean bBv;
    protected int bBw;
    protected b bBx;
    protected f bBy;
    protected Drawable bBz;
    private View bxR;
    private Activity mActivity;
    protected boolean mDrawerIndicatorEnabled;
    protected int mDrawerState;
    protected boolean mMenuVisible;
    protected Bundle mState;
    private final Rect mTempRect;
    protected int mTouchMode;

    public enum Type {
        BEHIND,
        STATIC,
        OVERLAY
    }

    public abstract void bL(long j);

    public abstract void dk(boolean z);

    public abstract void dl(boolean z);

    public abstract void dm(boolean z);

    protected abstract void g(Canvas canvas);

    public abstract boolean getOffsetMenuEnabled();

    public abstract int getTouchBezelSize();

    public abstract int getTouchMode();

    public abstract boolean isMenuVisible();

    public abstract void k(long j, long j2);

    protected abstract void me(int i);

    public abstract void peekDrawer();

    public abstract void setHardwareLayerEnabled(boolean z);

    public abstract void setMenuSize(int i);

    public abstract void setOffsetMenuEnabled(boolean z);

    public abstract void setTouchBezelSize(int i);

    public abstract void setTouchMode(int i);

    static {
        boolean z;
        if (VERSION.SDK_INT >= 14) {
            z = true;
        } else {
            z = false;
        }
        bAU = z;
    }

    public static MenuDrawer B(Activity activity) {
        return a(activity, Type.BEHIND);
    }

    public static MenuDrawer a(Activity activity, Type type) {
        return a(activity, type, Position.START);
    }

    public static MenuDrawer a(Activity activity, Position position) {
        return a(activity, Type.BEHIND, position);
    }

    public static MenuDrawer a(Activity activity, Type type, Position position) {
        return a(activity, type, position, 0);
    }

    public static MenuDrawer a(Activity activity, Type type, Position position, int dragMode) {
        MenuDrawer menuDrawer = a(activity, dragMode, position, type);
        menuDrawer.setId(g.md__drawer);
        switch (dragMode) {
            case 0:
                a(activity, menuDrawer);
                break;
            case 1:
                b(activity, menuDrawer);
                break;
            default:
                throw new RuntimeException("Unknown menu mode: " + dragMode);
        }
        return menuDrawer;
    }

    private static MenuDrawer a(Activity activity, int dragMode, Position position, Type type) {
        MenuDrawer drawer;
        if (type == Type.STATIC) {
            drawer = new StaticDrawer(activity);
        } else if (type == Type.OVERLAY) {
            drawer = new OverlayDrawer(activity, dragMode);
            if (position == Position.LEFT || position == Position.START) {
                drawer.setupUpIndicator(activity);
            }
        } else {
            drawer = new SlidingDrawer(activity, dragMode);
            if (position == Position.LEFT || position == Position.START) {
                drawer.setupUpIndicator(activity);
            }
        }
        drawer.bBn = dragMode;
        drawer.setPosition(position);
        return drawer;
    }

    private static void a(Activity activity, MenuDrawer menuDrawer) {
        ViewGroup content = (ViewGroup) activity.findViewById(16908290);
        content.removeAllViews();
        content.addView(menuDrawer, -1, -1);
    }

    private static void b(Activity activity, MenuDrawer menuDrawer) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decorView.getChildAt(0);
        decorView.removeAllViews();
        decorView.addView(menuDrawer, -1, -1);
        menuDrawer.bBl.addView(decorChild, decorChild.getLayoutParams());
    }

    MenuDrawer(Activity activity, int dragMode) {
        this(activity);
        this.mActivity = activity;
        this.bBn = dragMode;
    }

    public MenuDrawer(Context context) {
        this(context, null);
    }

    public MenuDrawer(Context context, AttributeSet attrs) {
        this(context, attrs, c.menuDrawerStyle);
    }

    public MenuDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.bBj = new Rect();
        this.mTempRect = new Rect();
        this.bBn = 0;
        this.mDrawerState = 0;
        this.mTouchMode = 1;
        this.bAg = true;
        this.bBs = new 1(this);
        this.bBw = 600;
        this.bBG = new Rect();
        this.bBI = new Rect();
        this.bBL = new 2(this);
        a(context, attrs, defStyle);
    }

    protected void a(Context context, AttributeSet attrs, int defStyle) {
        setWillNotDraw(false);
        setFocusable(false);
        TypedArray a = context.obtainStyledAttributes(attrs, o.MenuDrawer, c.menuDrawerStyle, n.Widget_MenuDrawer);
        Drawable contentBackground = a.getDrawable(o.MenuDrawer_mdContentBackground);
        Drawable menuBackground = a.getDrawable(o.MenuDrawer_mdMenuBackground);
        this.bBm = a.getDimensionPixelSize(o.MenuDrawer_mdMenuSize, md(b.bpd));
        int indicatorResId = a.getResourceId(o.MenuDrawer_mdActiveIndicator, 0);
        if (indicatorResId != 0) {
            this.bBf = BitmapFactory.decodeResource(getResources(), indicatorResId);
        }
        this.bBa = a.getBoolean(o.MenuDrawer_mdDropShadowEnabled, true);
        this.bBc = a.getDrawable(o.MenuDrawer_mdDropShadow);
        if (this.bBc == null) {
            this.bBb = a.getColor(o.MenuDrawer_mdDropShadowColor, -16777216);
        } else {
            this.bBd = true;
        }
        this.bBe = a.getDimensionPixelSize(o.MenuDrawer_mdDropShadowSize, md(6));
        this.bBo = a.getDimensionPixelSize(o.MenuDrawer_mdTouchBezelSize, md(24));
        this.bBi = a.getBoolean(o.MenuDrawer_mdAllowIndicatorAnimation, false);
        this.bBw = a.getInt(o.MenuDrawer_mdMaxAnimationDuration, 600);
        int slideDrawableResId = a.getResourceId(o.MenuDrawer_mdSlideDrawable, -1);
        if (slideDrawableResId != -1) {
            setSlideDrawable(slideDrawableResId);
        }
        this.bBC = a.getResourceId(o.MenuDrawer_mdDrawerOpenUpContentDescription, 0);
        this.bBD = a.getResourceId(o.MenuDrawer_mdDrawerClosedUpContentDescription, 0);
        this.bBK = a.getBoolean(o.MenuDrawer_mdDrawOverlay, true);
        setPosition(Position.fromValue(a.getInt(o.MenuDrawer_mdPosition, 0)));
        a.recycle();
        this.bBk = new NoClickThroughFrameLayout(context);
        this.bBk.setId(g.md__menu);
        this.bBk.setBackgroundDrawable(menuBackground);
        this.bBl = new NoClickThroughFrameLayout(context);
        this.bBl.setId(g.md__content);
        this.bBl.setBackgroundDrawable(contentBackground);
        this.bAZ = new a(-16777216);
        this.bBr = new b(bAX);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        View menu = findViewById(g.mdMenu);
        if (menu != null) {
            removeView(menu);
            setMenuView(menu);
        }
        View content = findViewById(g.mdContent);
        if (content != null) {
            removeView(content);
            setContentView(content);
        }
        if (getChildCount() > 2) {
            throw new IllegalStateException("Menu and content view added in xml must have id's @id/mdMenu and @id/mdContent");
        }
    }

    protected int md(int dp) {
        return (int) ((getResources().getDisplayMetrics().density * ((float) dp)) + 0.5f);
    }

    protected boolean z(View v) {
        for (Object parent = v.getParent(); parent != null; parent = parent.getParent()) {
            if (parent == this) {
                return true;
            }
        }
        return false;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnScrollChangedListener(this.bBL);
    }

    protected void onDetachedFromWindow() {
        getViewTreeObserver().removeOnScrollChangedListener(this.bBL);
        super.onDetachedFromWindow();
    }

    private boolean Pq() {
        return (this.bBg == null || this.bBf == null || !z(this.bBg)) ? false : true;
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int offsetPixels = (int) this.bBJ;
        if (this.bBK && offsetPixels != 0) {
            g(canvas);
        }
        if (this.bBa && (offsetPixels != 0 || this.bBH)) {
            h(canvas);
        }
        if (!Pq()) {
            return;
        }
        if (offsetPixels != 0 || this.bBH) {
            i(canvas);
        }
    }

    private void h(Canvas canvas) {
        if (this.bBc == null) {
            setDropShadowColor(this.bBb);
        }
        Pr();
        this.bBc.setBounds(this.bBI);
        this.bBc.draw(canvas);
    }

    protected void Pr() {
        switch (3.bAD[getPosition().ordinal()]) {
            case 1:
                this.bBI.top = 0;
                this.bBI.bottom = getHeight();
                this.bBI.right = h.A(this.bBl);
                this.bBI.left = this.bBI.right - this.bBe;
                return;
            case 2:
                this.bBI.left = 0;
                this.bBI.right = getWidth();
                this.bBI.bottom = h.B(this.bBl);
                this.bBI.top = this.bBI.bottom - this.bBe;
                return;
            case 3:
                this.bBI.top = 0;
                this.bBI.bottom = getHeight();
                this.bBI.left = h.C(this.bBl);
                this.bBI.right = this.bBI.left + this.bBe;
                return;
            case 4:
                this.bBI.left = 0;
                this.bBI.right = getWidth();
                this.bBI.top = h.D(this.bBl);
                this.bBI.bottom = this.bBI.top + this.bBe;
                return;
            default:
                return;
        }
    }

    private void i(Canvas canvas) {
        Integer position = (Integer) this.bBg.getTag(g.mdActiveViewPosition);
        if ((position == null ? 0 : position.intValue()) == this.bBh) {
            Ps();
            canvas.save();
            canvas.clipRect(this.bBG);
            int drawLeft = 0;
            int drawTop = 0;
            switch (3.bAD[getPosition().ordinal()]) {
                case 1:
                case 2:
                    drawLeft = this.bBG.left;
                    drawTop = this.bBG.top;
                    break;
                case 3:
                    drawLeft = this.bBG.right - this.bBf.getWidth();
                    drawTop = this.bBG.top;
                    break;
                case 4:
                    drawLeft = this.bBG.left;
                    drawTop = this.bBG.bottom - this.bBf.getHeight();
                    break;
            }
            canvas.drawBitmap(this.bBf, (float) drawLeft, (float) drawTop, null);
            canvas.restore();
        }
    }

    protected void Ps() {
        float openRatio;
        this.bBg.getDrawingRect(this.bBj);
        offsetDescendantRectToMyCoords(this.bBg, this.bBj);
        if (this.bBH) {
            openRatio = 1.0f;
        } else {
            openRatio = Math.abs(this.bBJ) / ((float) this.bBm);
        }
        float interpolatedRatio = 1.0f - bAY.getInterpolation(1.0f - openRatio);
        int indicatorWidth = this.bBf.getWidth();
        int indicatorHeight = this.bBf.getHeight();
        int interpolatedWidth = (int) (((float) indicatorWidth) * interpolatedRatio);
        int interpolatedHeight = (int) (((float) indicatorHeight) * interpolatedRatio);
        int startPos = this.bBt;
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        switch (3.bAD[getPosition().ordinal()]) {
            case 1:
            case 3:
                int finalTop = this.bBj.top + ((this.bBj.height() - indicatorHeight) / 2);
                if (this.bBv) {
                    top = (int) (((float) startPos) + (((float) (finalTop - startPos)) * this.bBu));
                } else {
                    top = finalTop;
                }
                bottom = top + indicatorHeight;
                break;
            case 2:
            case 4:
                int finalLeft = this.bBj.left + ((this.bBj.width() - indicatorWidth) / 2);
                if (this.bBv) {
                    left = (int) (((float) startPos) + (((float) (finalLeft - startPos)) * this.bBu));
                } else {
                    left = finalLeft;
                }
                right = left + indicatorWidth;
                break;
        }
        switch (3.bAD[getPosition().ordinal()]) {
            case 1:
                right = h.A(this.bBl);
                left = right - interpolatedWidth;
                break;
            case 2:
                bottom = h.B(this.bBl);
                top = bottom - interpolatedHeight;
                break;
            case 3:
                left = h.C(this.bBl);
                right = left + interpolatedWidth;
                break;
            case 4:
                top = h.D(this.bBl);
                bottom = top + interpolatedHeight;
                break;
        }
        this.bBG.left = left;
        this.bBG.top = top;
        this.bBG.right = right;
        this.bBG.bottom = bottom;
    }

    private void setPosition(Position position) {
        this.bBE = position;
        this.bBF = getPosition();
    }

    protected Position getPosition() {
        int layoutDirection = h.getLayoutDirection(this);
        switch (3.bAD[this.bBE.ordinal()]) {
            case 5:
                if (layoutDirection == 1) {
                    return Position.RIGHT;
                }
                return Position.LEFT;
            case 6:
                if (layoutDirection == 1) {
                    return Position.LEFT;
                }
                return Position.RIGHT;
            default:
                return this.bBE;
        }
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        boolean z = true;
        super.onRtlPropertiesChanged(layoutDirection);
        if (!this.bBd) {
            setDropShadowColor(this.bBb);
        }
        if (getPosition() != this.bBF) {
            this.bBF = getPosition();
            setOffsetPixels(this.bBJ * -1.0f);
        }
        if (this.bBy != null) {
            f fVar = this.bBy;
            if (layoutDirection != 1) {
                z = false;
            }
            fVar.dn(z);
        }
        requestLayout();
        invalidate();
    }

    protected void setOffsetPixels(float offsetPixels) {
        int oldOffset = (int) this.bBJ;
        int newOffset = (int) offsetPixels;
        this.bBJ = offsetPixels;
        if (this.bBy != null) {
            this.bBy.setOffset(Math.abs(this.bBJ) / ((float) this.bBm));
            PA();
        }
        if (newOffset != oldOffset) {
            me(newOffset);
            this.mMenuVisible = newOffset != 0;
            a(((float) Math.abs(newOffset)) / ((float) this.bBm), newOffset);
        }
    }

    public void Pt() {
        dk(true);
    }

    public void Pu() {
        dl(true);
    }

    public void Pv() {
        dm(true);
    }

    public int getMenuSize() {
        return this.bBm;
    }

    public void setActiveView(View v) {
        e(v, 0);
    }

    public void e(View v, int position) {
        View oldView = this.bBg;
        this.bBg = v;
        this.bBh = position;
        if (this.bBi && oldView != null) {
            Pw();
        }
        invalidate();
    }

    public void setAllowIndicatorAnimation(boolean animate) {
        if (animate != this.bBi) {
            this.bBi = animate;
            Pz();
        }
    }

    public boolean getAllowIndicatorAnimation() {
        return this.bBi;
    }

    private void Pw() {
        this.bBt = getIndicatorStartPos();
        this.bBv = true;
        this.bBr.a(0.0f, 1.0f, 800);
        Py();
    }

    private int getIndicatorStartPos() {
        switch (3.bAD[getPosition().ordinal()]) {
            case 2:
                return this.bBG.left;
            case 3:
                return this.bBG.top;
            case 4:
                return this.bBG.left;
            default:
                return this.bBG.top;
        }
    }

    protected void Px() {
        if (this.mTouchMode == 1) {
            this.bBp = this.bBo;
        } else if (this.mTouchMode == 2) {
            this.bBp = getMeasuredWidth();
        } else {
            this.bBp = 0;
        }
    }

    private void Py() {
        if (this.bBr.computeScrollOffset()) {
            this.bBu = this.bBr.Pn();
            invalidate();
            if (!this.bBr.isFinished()) {
                postOnAnimation(this.bBs);
                return;
            }
        }
        Pz();
    }

    private void Pz() {
        this.bBu = 1.0f;
        this.bBv = false;
        invalidate();
    }

    public int getDrawerState() {
        return this.mDrawerState;
    }

    public void setOnDrawerStateChangeListener(a listener) {
        this.bBq = listener;
    }

    public void setOnInterceptMoveEventListener(b listener) {
        this.bBx = listener;
    }

    public void setDropShadowEnabled(boolean enabled) {
        this.bBa = enabled;
        invalidate();
    }

    protected Orientation getDropShadowOrientation() {
        switch (3.bAD[getPosition().ordinal()]) {
            case 2:
                return Orientation.BOTTOM_TOP;
            case 3:
                return Orientation.LEFT_RIGHT;
            case 4:
                return Orientation.TOP_BOTTOM;
            default:
                return Orientation.RIGHT_LEFT;
        }
    }

    public void setDropShadowColor(int color) {
        int endColor = color & 16777215;
        this.bBc = new GradientDrawable(getDropShadowOrientation(), new int[]{color, endColor});
        invalidate();
    }

    public void setDropShadow(Drawable drawable) {
        this.bBc = drawable;
        this.bBd = drawable != null;
        invalidate();
    }

    public void setDropShadow(int resId) {
        setDropShadow(getResources().getDrawable(resId));
    }

    public Drawable getDropShadow() {
        return this.bBc;
    }

    public void setDropShadowSize(int size) {
        this.bBe = size;
        invalidate();
    }

    public void setMaxAnimationDuration(int duration) {
        this.bBw = duration;
    }

    public void setDrawOverlay(boolean drawOverlay) {
        this.bBK = drawOverlay;
    }

    public boolean getDrawOverlay() {
        return this.bBK;
    }

    protected void PA() {
        int upContentDesc = isMenuVisible() ? this.bBC : this.bBD;
        if (this.mDrawerIndicatorEnabled && this.bBA != null && upContentDesc != this.bBB) {
            this.bBB = upContentDesc;
            this.bBA.setActionBarDescription(upContentDesc);
        }
    }

    public void setSlideDrawable(int drawableRes) {
        setSlideDrawable(getResources().getDrawable(drawableRes));
    }

    public void setSlideDrawable(Drawable drawable) {
        this.bBy = new f(drawable);
        this.bBy.dn(h.getLayoutDirection(this) == 1);
        if (this.bBA != null) {
            this.bBA.do(true);
            if (this.mDrawerIndicatorEnabled) {
                this.bBA.setActionBarUpIndicator(this.bBy, isMenuVisible() ? this.bBC : this.bBD);
            }
        }
    }

    public void setupUpIndicator(Activity activity) {
        if (this.bBA == null) {
            this.bBA = new a(activity);
            this.bBz = this.bBA.getThemeUpIndicator();
            if (this.mDrawerIndicatorEnabled) {
                this.bBA.setActionBarUpIndicator(this.bBy, isMenuVisible() ? this.bBC : this.bBD);
            }
        }
    }

    public void setDrawerIndicatorEnabled(boolean enabled) {
        if (this.bBA == null) {
            throw new IllegalStateException("setupUpIndicator(Activity) has not been called");
        }
        this.mDrawerIndicatorEnabled = enabled;
        if (enabled) {
            this.bBA.setActionBarUpIndicator(this.bBy, isMenuVisible() ? this.bBC : this.bBD);
        } else {
            this.bBA.setActionBarUpIndicator(this.bBz, 0);
        }
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.mDrawerIndicatorEnabled;
    }

    public ViewGroup getMenuContainer() {
        return this.bBk;
    }

    public ViewGroup getContentContainer() {
        if (this.bBn == 0) {
            return this.bBl;
        }
        return (ViewGroup) findViewById(16908290);
    }

    public void setMenuView(int layoutResId) {
        this.bBk.removeAllViews();
        this.bxR = LayoutInflater.from(getContext()).inflate(layoutResId, this.bBk, false);
        this.bBk.addView(this.bxR);
    }

    public void setMenuView(View view) {
        b(view, new LayoutParams(-1, -1));
    }

    public void b(View view, LayoutParams params) {
        this.bxR = view;
        this.bBk.removeAllViews();
        this.bBk.addView(view, params);
    }

    public View getMenuView() {
        return this.bxR;
    }

    public void setContentView(int layoutResId) {
        switch (this.bBn) {
            case 0:
                this.bBl.removeAllViews();
                LayoutInflater.from(getContext()).inflate(layoutResId, this.bBl, true);
                return;
            case 1:
                this.mActivity.setContentView(layoutResId);
                return;
            default:
                return;
        }
    }

    public void setContentView(View view) {
        setContentView(view, new LayoutParams(-1, -1));
    }

    public void setContentView(View view, LayoutParams params) {
        switch (this.bBn) {
            case 0:
                this.bBl.removeAllViews();
                this.bBl.addView(view, params);
                return;
            case 1:
                this.mActivity.setContentView(view, params);
                return;
            default:
                return;
        }
    }

    protected void setDrawerState(int state) {
        if (state != this.mDrawerState) {
            int oldState = this.mDrawerState;
            this.mDrawerState = state;
            if (this.bBq != null) {
                this.bBq.aO(oldState, state);
            }
        }
    }

    protected void mf(int state) {
        switch (state) {
            case 0:
                Log.d(TAG, "[DrawerState] STATE_CLOSED");
                return;
            case 1:
                Log.d(TAG, "[DrawerState] STATE_CLOSING");
                return;
            case 2:
                Log.d(TAG, "[DrawerState] STATE_DRAGGING");
                return;
            case 4:
                Log.d(TAG, "[DrawerState] STATE_OPENING");
                return;
            case 8:
                Log.d(TAG, "[DrawerState] STATE_OPEN");
                return;
            default:
                Log.d(TAG, "[DrawerState] Unknown: " + state);
                return;
        }
    }

    public void postOnAnimation(Runnable action) {
        if (VERSION.SDK_INT >= 16) {
            super.postOnAnimation(action);
        } else {
            postDelayed(action, 16);
        }
    }

    protected boolean fitSystemWindows(Rect insets) {
        if (this.bBn == 1 && this.bBE != Position.BOTTOM) {
            this.bBk.setPadding(0, insets.top, 0, 0);
        }
        return super.fitSystemWindows(insets);
    }

    protected void a(float openRatio, int offsetPixels) {
        if (this.bBq != null) {
            this.bBq.b(openRatio, offsetPixels);
        }
    }

    public final Parcelable saveState() {
        if (this.mState == null) {
            this.mState = new Bundle();
        }
        c(this.mState);
        return this.mState;
    }

    void c(Bundle state) {
    }

    public void a(Parcelable in) {
        this.mState = (Bundle) in;
    }

    protected Parcelable onSaveInstanceState() {
        c state = new c(super.onSaveInstanceState());
        if (this.mState == null) {
            this.mState = new Bundle();
        }
        c(this.mState);
        state.mState = this.mState;
        return state;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        c savedState = (c) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        a(savedState.mState);
    }
}
