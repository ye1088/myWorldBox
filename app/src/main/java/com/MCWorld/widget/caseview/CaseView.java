package com.MCWorld.widget.caseview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.m;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.PopupWindow;
import com.MCWorld.widget.caseview.highlight.c;
import com.MCWorld.widget.caseview.highlight.d;
import com.MCWorld.widget.caseview.target.b;
import java.util.ArrayList;
import java.util.List;

public class CaseView extends FrameLayout {
    private static final int bvD = -1728053248;
    private PopupWindow ZY;
    private int aMe = bvD;
    private c bvB;
    private List<a> bvE;
    private ImageView bvF;
    private int bvG;
    private Paint bvH;
    private int bvI;
    private Bitmap bvJ;
    private Canvas bvK;
    private int bvL;
    private int bvM;
    private a bvN;
    private b bvz;
    private Activity mActivity;

    public interface a {
        void lR(int i);

        void lS(int i);
    }

    public CaseView(Context context) {
        super(context);
        init(context);
    }

    public CaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (this.bvJ == null || this.bvK == null || this.bvL != height || this.bvM != width) {
            if (this.bvJ != null) {
                this.bvJ.recycle();
            }
            this.bvJ = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            this.bvK = new Canvas(this.bvJ);
        }
        this.bvM = width;
        this.bvL = height;
        this.bvK.drawColor(0, Mode.CLEAR);
        this.bvK.drawColor(this.aMe);
        this.bvB.a(this.ZY, this.bvz, this.bvK, this.bvH);
        canvas.drawBitmap(this.bvJ, 0.0f, 0.0f, null);
    }

    private void init(Context context) {
        this.mActivity = (Activity) context;
        this.bvF = new ImageView(context);
        this.bvF.setScaleType(ScaleType.CENTER_INSIDE);
        addView(this.bvF, new LayoutParams(-1, -2));
        setWillNotDraw(false);
        this.bvH = new Paint();
        this.bvH.setAntiAlias(true);
        this.bvH.setColor(0);
        this.bvH.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        this.bvI = (int) TypedValue.applyDimension(1, 4.0f, context.getResources().getDisplayMetrics());
        this.bvE = new ArrayList();
    }

    public void show() {
        if (this.bvE != null) {
            this.ZY = new PopupWindow(this, -1, -1);
            this.ZY.setFocusable(true);
            this.ZY.setBackgroundDrawable(new ColorDrawable(0));
            this.ZY.showAtLocation(this.mActivity.getWindow().getDecorView(), 0, 0, 0);
            lQ(0);
        }
    }

    private void lQ(int position) {
        if (this.bvE != null && position < this.bvE.size()) {
            if (this.bvN != null) {
                this.bvN.lR(position);
            }
            a aCase = (a) this.bvE.get(position);
            this.bvF.setImageResource(aCase.Oc());
            this.bvG = position;
            this.bvz = aCase.Ob();
            this.bvB = aCase.Od();
            a(this.bvz.a(this.ZY), this.bvI);
            invalidate();
            if (this.bvN != null) {
                this.bvN.lS(position);
            }
        }
    }

    private void a(RectF bounds, int padding) {
        int centerY = (int) bounds.centerY();
        int screenHeight = this.mActivity.getResources().getDisplayMetrics().heightPixels;
        if (centerY < screenHeight / 2) {
            setPadding(0, ((int) bounds.bottom) + padding, 0, 0);
            LayoutParams lp = (LayoutParams) this.bvF.getLayoutParams();
            lp.gravity = 48;
            this.bvF.setLayoutParams(lp);
            return;
        }
        setPadding(0, 0, 0, (screenHeight - ((int) bounds.top)) + padding);
        lp = (LayoutParams) this.bvF.getLayoutParams();
        lp.gravity = 80;
        this.bvF.setLayoutParams(lp);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEventCompat.getActionMasked(event) == 0) {
            if (Og()) {
                lQ(this.bvG + 1);
            } else {
                this.ZY.dismiss();
                Of();
            }
        }
        return true;
    }

    private void Of() {
        if (this.bvJ != null && !this.bvJ.isRecycled()) {
            this.bvJ.recycle();
            this.bvJ = null;
        }
    }

    public CaseView c(View target, @m int imgRes) {
        return a(target, imgRes, false);
    }

    public CaseView b(RectF target, @m int imgRes) {
        return a(target, imgRes, false);
    }

    public CaseView a(View target, @m int imgRes, boolean circle) {
        if (target != null) {
            a caze = new com.MCWorld.widget.caseview.a.a().s(target).lP(imgRes).b(circle ? new com.MCWorld.widget.caseview.highlight.a() : new com.MCWorld.widget.caseview.highlight.b()).Oe();
            if (caze != null) {
                this.bvE.add(caze);
            }
        }
        return this;
    }

    public CaseView a(RectF target, @m int imgRes, boolean circle) {
        if (target != null) {
            a caze = new com.MCWorld.widget.caseview.a.a().a(target, getContext()).lP(imgRes).b(circle ? new com.MCWorld.widget.caseview.highlight.a() : new com.MCWorld.widget.caseview.highlight.b()).Oe();
            if (caze != null) {
                this.bvE.add(caze);
            }
        }
        return this;
    }

    public CaseView a(RectF target, @m int imgRes, int radiusX, int radiusY) {
        if (target != null) {
            a caze = new com.MCWorld.widget.caseview.a.a().a(target, getContext()).lP(imgRes).b(new d(radiusX, radiusY)).Oe();
            if (caze != null) {
                this.bvE.add(caze);
            }
        }
        return this;
    }

    private boolean Og() {
        return this.bvE != null && this.bvG + 1 < this.bvE.size();
    }

    public void dismiss() {
        Of();
        if (this.ZY != null) {
            this.ZY.dismiss();
        }
    }

    public void setOnCaseChangedListener(a listener) {
        this.bvN = listener;
    }
}
