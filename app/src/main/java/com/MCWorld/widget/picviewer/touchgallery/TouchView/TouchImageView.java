package com.MCWorld.widget.picviewer.touchgallery.TouchView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;

import com.MCWorld.framework.base.image.PipelineView;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint({"NewApi"})
public class TouchImageView extends PipelineView {
    static final int CLICK = 10;
    static final float FRICTION = 0.9f;
    static final int NONE = 0;
    static final long bDm = 600;
    static final int bDn = 1;
    static final int bDo = 2;
    float bDA = 1.0f;
    float bDB = 1.0f;
    float bDC = 3.0f;
    float bDD = 1.0f;
    PointF bDE = new PointF(0.0f, 0.0f);
    float bDF = 0.0f;
    long bDG = 0;
    long bDH = 0;
    boolean bDI = false;
    private Timer bDJ;
    private Object bDK;
    private Handler bDL = null;
    private boolean bDM = false;
    public boolean bDN = false;
    public boolean bDO = false;
    public boolean bDP = false;
    public boolean bDQ = false;
    PointF bDd = new PointF();
    private OnClickListener bDj;
    private int bDk = -1;
    Matrix bDl = new Matrix();
    float bDp;
    float bDq;
    float bDr;
    float bDs;
    float bDt;
    float bDu;
    PointF bDv = new PointF();
    PointF bDw = new PointF();
    float[] bDx;
    float bDy;
    float bDz;
    float bottom;
    Matrix bqg = new Matrix();
    float height;
    private Context mContext;
    int mode = 0;
    float right;
    float width;

    private class a extends SimpleOnScaleGestureListener {
        final /* synthetic */ TouchImageView bDR;

        private a(TouchImageView touchImageView) {
            this.bDR = touchImageView;
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            this.bDR.mode = 2;
            return true;
        }

        public boolean onScale(ScaleGestureDetector detector) {
            float mScaleFactor = (float) Math.min((double) Math.max(0.95f, detector.getScaleFactor()), 1.05d);
            float origScale = this.bDR.bDA;
            TouchImageView touchImageView = this.bDR;
            touchImageView.bDA *= mScaleFactor;
            if (this.bDR.bDA > this.bDR.bDC) {
                this.bDR.bDA = this.bDR.bDC;
                mScaleFactor = this.bDR.bDC / origScale;
            } else if (this.bDR.bDA < this.bDR.bDB) {
                this.bDR.bDA = this.bDR.bDB;
                mScaleFactor = this.bDR.bDB / origScale;
            }
            this.bDR.right = ((this.bDR.width * this.bDR.bDA) - this.bDR.width) - ((this.bDR.bDp * 2.0f) * this.bDR.bDA);
            this.bDR.bottom = ((this.bDR.height * this.bDR.bDA) - this.bDR.height) - ((this.bDR.bDq * 2.0f) * this.bDR.bDA);
            float x;
            float y;
            if (this.bDR.bDr * this.bDR.bDA <= this.bDR.width || this.bDR.bDs * this.bDR.bDA <= this.bDR.height) {
                this.bDR.bqg.postScale(mScaleFactor, mScaleFactor, this.bDR.width / 2.0f, this.bDR.height / 2.0f);
                if (mScaleFactor < 1.0f) {
                    this.bDR.bqg.getValues(this.bDR.bDx);
                    x = this.bDR.bDx[2];
                    y = this.bDR.bDx[5];
                    if (mScaleFactor < 1.0f) {
                        if (((float) Math.round(this.bDR.bDr * this.bDR.bDA)) < this.bDR.width) {
                            if (y < (-this.bDR.bottom)) {
                                this.bDR.bqg.postTranslate(0.0f, -(this.bDR.bottom + y));
                            } else if (y > 0.0f) {
                                this.bDR.bqg.postTranslate(0.0f, -y);
                            }
                        } else if (x < (-this.bDR.right)) {
                            this.bDR.bqg.postTranslate(-(this.bDR.right + x), 0.0f);
                        } else if (x > 0.0f) {
                            this.bDR.bqg.postTranslate(-x, 0.0f);
                        }
                    }
                }
            } else {
                this.bDR.bqg.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
                this.bDR.bqg.getValues(this.bDR.bDx);
                x = this.bDR.bDx[2];
                y = this.bDR.bDx[5];
                if (mScaleFactor < 1.0f) {
                    if (x < (-this.bDR.right)) {
                        this.bDR.bqg.postTranslate(-(this.bDR.right + x), 0.0f);
                    } else if (x > 0.0f) {
                        this.bDR.bqg.postTranslate(-x, 0.0f);
                    }
                    if (y < (-this.bDR.bottom)) {
                        this.bDR.bqg.postTranslate(0.0f, -(this.bDR.bottom + y));
                    } else if (y > 0.0f) {
                        this.bDR.bqg.postTranslate(0.0f, -y);
                    }
                }
            }
            return true;
        }
    }

    private class b extends TimerTask {
        final /* synthetic */ TouchImageView bDR;

        private b(TouchImageView touchImageView) {
            this.bDR = touchImageView;
        }

        public void run() {
            this.bDR.bDL.sendEmptyMessage(0);
        }
    }

    static class c extends Handler {
        private final WeakReference<TouchImageView> bDS;

        c(TouchImageView view) {
            this.bDS = new WeakReference(view);
        }

        public void handleMessage(Message msg) {
            ((TouchImageView) this.bDS.get()).performClick();
            if (((TouchImageView) this.bDS.get()).bDj != null) {
                ((TouchImageView) this.bDS.get()).bDj.onClick((View) this.bDS.get());
            }
        }
    }

    public boolean PI() {
        return this.bDM;
    }

    public void setZoomToOriginalSize(boolean zoomToOriginalSize) {
        this.bDM = zoomToOriginalSize;
    }

    public TouchImageView(Context context) {
        super(context);
        super.setClickable(true);
        this.mContext = context;
        init();
    }

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setClickable(true);
        this.mContext = context;
        init();
    }

    protected void init() {
        this.bDL = new c(this);
        this.bqg.setTranslate(1.0f, 1.0f);
        this.bDx = new float[9];
        setImageMatrix(this.bqg);
        setScaleType(ScaleType.MATRIX);
        if (VERSION.SDK_INT >= 8) {
            this.bDK = new ScaleGestureDetector(this.mContext, new a());
        }
        setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ TouchImageView bDR;

            {
                this.bDR = this$0;
            }

            public boolean onTouch(View v, MotionEvent rawEvent) {
                b event = b.f(rawEvent);
                if (this.bDR.bDK != null) {
                    ((ScaleGestureDetector) this.bDR.bDK).onTouchEvent(rawEvent);
                }
                this.bDR.PN();
                PointF curr = new PointF(event.getX(), event.getY());
                switch (event.getAction() & 255) {
                    case 0:
                        this.bDR.bDI = false;
                        this.bDR.bDl.set(this.bDR.bqg);
                        this.bDR.bDd.set(event.getX(), event.getY());
                        this.bDR.bDw.set(this.bDR.bDd);
                        this.bDR.mode = 1;
                        break;
                    case 1:
                        this.bDR.bDI = true;
                        this.bDR.mode = 0;
                        int yDiff = (int) Math.abs(event.getY() - this.bDR.bDw.y);
                        if (((int) Math.abs(event.getX() - this.bDR.bDw.x)) < 10 && yDiff < 10) {
                            long pressTime = System.currentTimeMillis();
                            if (pressTime - this.bDR.bDG <= TouchImageView.bDm) {
                                if (this.bDR.bDJ != null) {
                                    this.bDR.bDJ.cancel();
                                }
                                if (this.bDR.bDA == 1.0f) {
                                    float targetScale = this.bDR.bDC / this.bDR.bDA;
                                    this.bDR.bqg.postScale(targetScale, targetScale, this.bDR.bDw.x, this.bDR.bDw.y);
                                    this.bDR.bDA = this.bDR.bDC;
                                } else {
                                    this.bDR.bqg.postScale(this.bDR.bDB / this.bDR.bDA, this.bDR.bDB / this.bDR.bDA, this.bDR.width / 2.0f, this.bDR.height / 2.0f);
                                    this.bDR.bDA = this.bDR.bDB;
                                }
                                this.bDR.PM();
                                this.bDR.g(0.0f, 0.0f);
                                this.bDR.bDG = 0;
                            } else {
                                this.bDR.bDG = pressTime;
                                this.bDR.bDJ = new Timer();
                                this.bDR.bDJ.schedule(new b(), 300);
                            }
                            if (this.bDR.bDA == this.bDR.bDB) {
                                this.bDR.PO();
                                break;
                            }
                        }
                        break;
                    case 2:
                        this.bDR.bDI = false;
                        if (this.bDR.mode != 1) {
                            if (this.bDR.bDK == null && this.bDR.mode == 2) {
                                float newDist = this.bDR.a(event);
                                if (rawEvent.getPointerCount() >= 2 && 10.0f <= Math.abs(this.bDR.bDD - newDist) && Math.abs(this.bDR.bDD - newDist) <= 50.0f) {
                                    float mScaleFactor = newDist / this.bDR.bDD;
                                    this.bDR.bDD = newDist;
                                    float origScale = this.bDR.bDA;
                                    TouchImageView touchImageView = this.bDR;
                                    touchImageView.bDA *= mScaleFactor;
                                    if (this.bDR.bDA > this.bDR.bDC) {
                                        this.bDR.bDA = this.bDR.bDC;
                                        mScaleFactor = this.bDR.bDC / origScale;
                                    } else if (this.bDR.bDA < this.bDR.bDB) {
                                        this.bDR.bDA = this.bDR.bDB;
                                        mScaleFactor = this.bDR.bDB / origScale;
                                    }
                                    this.bDR.PM();
                                    if (this.bDR.bDr * this.bDR.bDA <= this.bDR.width || this.bDR.bDs * this.bDR.bDA <= this.bDR.height) {
                                        this.bDR.bqg.postScale(mScaleFactor, mScaleFactor, this.bDR.width / 2.0f, this.bDR.height / 2.0f);
                                        if (mScaleFactor < 1.0f) {
                                            this.bDR.PN();
                                            if (mScaleFactor < 1.0f) {
                                                this.bDR.PO();
                                            }
                                        }
                                    } else {
                                        PointF mid = this.bDR.b(event);
                                        this.bDR.bqg.postScale(mScaleFactor, mScaleFactor, mid.x, mid.y);
                                        this.bDR.PN();
                                        if (mScaleFactor < 1.0f) {
                                            if (this.bDR.bDy < (-this.bDR.right)) {
                                                this.bDR.bqg.postTranslate(-(this.bDR.bDy + this.bDR.right), 0.0f);
                                            } else if (this.bDR.bDy > 0.0f) {
                                                this.bDR.bqg.postTranslate(-this.bDR.bDy, 0.0f);
                                            }
                                            if (this.bDR.bDz < (-this.bDR.bottom)) {
                                                this.bDR.bqg.postTranslate(0.0f, -(this.bDR.bDz + this.bDR.bottom));
                                            } else if (this.bDR.bDz > 0.0f) {
                                                this.bDR.bqg.postTranslate(0.0f, -this.bDR.bDz);
                                            }
                                        }
                                    }
                                    this.bDR.PL();
                                    break;
                                }
                            }
                        }
                        float deltaX = curr.x - this.bDR.bDd.x;
                        float deltaY = curr.y - this.bDR.bDd.y;
                        long dragTime = System.currentTimeMillis();
                        this.bDR.bDF = (((float) this.bDR.a(curr, this.bDR.bDd)) / ((float) (dragTime - this.bDR.bDH))) * TouchImageView.FRICTION;
                        this.bDR.bDH = dragTime;
                        this.bDR.g(deltaX, deltaY);
                        this.bDR.bDE.set(deltaX, deltaY);
                        this.bDR.bDd.set(curr.x, curr.y);
                        break;
                        break;
                    case 5:
                        this.bDR.bDD = this.bDR.a(event);
                        if (this.bDR.bDD > 10.0f) {
                            this.bDR.bDl.set(this.bDR.bqg);
                            this.bDR.a(this.bDR.bDv, event);
                            this.bDR.mode = 2;
                            break;
                        }
                        break;
                    case 6:
                        this.bDR.mode = 0;
                        this.bDR.bDF = 0.0f;
                        this.bDR.bDl.set(this.bDR.bqg);
                        this.bDR.bDD = this.bDR.a(event);
                        break;
                }
                this.bDR.setImageMatrix(this.bDR.bqg);
                this.bDR.invalidate();
                return false;
            }
        });
    }

    public void PJ() {
        PN();
        this.bqg.postScale(this.bDB / this.bDA, this.bDB / this.bDA, this.width / 2.0f, this.height / 2.0f);
        this.bDA = this.bDB;
        PM();
        g(0.0f, 0.0f);
        PO();
        setImageMatrix(this.bqg);
        invalidate();
    }

    public boolean PK() {
        if (this.mode == 0 && this.bDA == this.bDB) {
            return true;
        }
        return false;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.bDI) {
            float deltaX = this.bDE.x * this.bDF;
            float deltaY = this.bDE.y * this.bDF;
            if (deltaX <= this.width && deltaY <= this.height) {
                this.bDF *= FRICTION;
                if (((double) Math.abs(deltaX)) >= 0.1d || ((double) Math.abs(deltaY)) >= 0.1d) {
                    g(deltaX, deltaY);
                    setImageMatrix(this.bqg);
                }
            }
        }
    }

    private void g(float deltaX, float deltaY) {
        float scaleWidth = (float) Math.round(this.bDr * this.bDA);
        float scaleHeight = (float) Math.round(this.bDs * this.bDA);
        PN();
        if (scaleWidth < this.width) {
            deltaX = 0.0f;
            if (this.bDz + deltaY > 0.0f) {
                deltaY = -this.bDz;
            } else if (this.bDz + deltaY < (-this.bottom)) {
                deltaY = -(this.bDz + this.bottom);
            }
        } else if (scaleHeight < this.height) {
            deltaY = 0.0f;
            if (this.bDy + deltaX > 0.0f) {
                deltaX = -this.bDy;
            } else if (this.bDy + deltaX < (-this.right)) {
                deltaX = -(this.bDy + this.right);
            }
        } else {
            if (this.bDy + deltaX > 0.0f) {
                deltaX = -this.bDy;
            } else if (this.bDy + deltaX < (-this.right)) {
                deltaX = -(this.bDy + this.right);
            }
            if (this.bDz + deltaY > 0.0f) {
                deltaY = -this.bDz;
            } else if (this.bDz + deltaY < (-this.bottom)) {
                deltaY = -(this.bDz + this.bottom);
            }
        }
        this.bqg.postTranslate(deltaX, deltaY);
        PL();
    }

    private void PL() {
        PN();
        float scaleWidth = (float) Math.round(this.bDr * this.bDA);
        float scaleHeight = (float) Math.round(this.bDs * this.bDA);
        this.bDQ = false;
        this.bDO = false;
        this.bDP = false;
        this.bDN = false;
        if ((-this.bDy) < 10.0f) {
            this.bDN = true;
        }
        if ((scaleWidth >= this.width && (this.bDy + scaleWidth) - this.width < 10.0f) || (scaleWidth <= this.width && (-this.bDy) + scaleWidth <= this.width)) {
            this.bDP = true;
        }
        if ((-this.bDz) < 10.0f) {
            this.bDO = true;
        }
        if (Math.abs(((-this.bDz) + this.height) - scaleHeight) < 10.0f) {
            this.bDQ = true;
        }
    }

    private void PM() {
        this.right = ((this.width * this.bDA) - this.width) - ((this.bDp * 2.0f) * this.bDA);
        this.bottom = ((this.height * this.bDA) - this.height) - ((this.bDq * 2.0f) * this.bDA);
    }

    private void PN() {
        this.bqg.getValues(this.bDx);
        this.bDy = this.bDx[2];
        this.bDz = this.bDx[5];
    }

    private void PO() {
        if (Math.abs(this.bDy + (this.right / 2.0f)) > 0.5f) {
            this.bqg.postTranslate(-(this.bDy + (this.right / 2.0f)), 0.0f);
        }
        if (Math.abs(this.bDz + (this.bottom / 2.0f)) > 0.5f) {
            this.bqg.postTranslate(0.0f, -(this.bDz + (this.bottom / 2.0f)));
        }
    }

    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        if (bm != null) {
            this.bDt = (float) bm.getWidth();
            this.bDu = (float) bm.getHeight();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = (float) MeasureSpec.getSize(widthMeasureSpec);
        this.height = (float) MeasureSpec.getSize(heightMeasureSpec);
        float scale = Math.min(this.width / this.bDt, this.height / this.bDu);
        this.bqg.setScale(scale, scale);
        setImageMatrix(this.bqg);
        this.bDA = 1.0f;
        this.bDq = this.height - (this.bDu * scale);
        this.bDp = this.width - (this.bDt * scale);
        this.bDq /= 2.0f;
        this.bDp /= 2.0f;
        this.bqg.postTranslate(this.bDp, this.bDq);
        this.bDr = this.width - (this.bDp * 2.0f);
        this.bDs = this.height - (this.bDq * 2.0f);
        PM();
        setImageMatrix(this.bqg);
    }

    private double a(PointF left, PointF right) {
        return Math.sqrt(Math.pow((double) (left.x - right.x), 2.0d) + Math.pow((double) (left.y - right.y), 2.0d));
    }

    private float a(b event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    private void a(PointF point, b event) {
        point.set((event.getX(0) + event.getX(1)) / 2.0f, (event.getY(0) + event.getY(1)) / 2.0f);
    }

    private PointF b(b event) {
        return new PointF((event.getX(0) + event.getX(1)) / 2.0f, (event.getY(0) + event.getY(1)) / 2.0f);
    }

    public void setOnClickListener(OnClickListener l) {
        this.bDj = l;
    }
}
