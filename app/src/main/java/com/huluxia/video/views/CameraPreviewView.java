package com.huluxia.video.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsVersion;
import com.huluxia.video.b.d;
import com.huluxia.video.b.e;
import com.huluxia.video.b.j;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.taskdefs.email.EmailTask;

public class CameraPreviewView extends FrameLayout {
    public static final String TAG = "CameraPreviewView";
    private static final long bpQ = 350;
    private boolean bpR;
    private List<a> bpS;
    private Camera bpT;
    private int bpU;
    private float bpV;
    private ImageView bpW;
    private Animation bpX;
    private final ImageView bpY;
    private Animation bpZ;
    private RealCameraPreviewView bqa;

    public interface a {
        void Nj();

        void Nk();

        void Nl();

        void df(boolean z);
    }

    private class RealCameraPreviewView extends SurfaceView implements AutoFocusCallback, Callback {
        private static final String TAG = "RealCameraPreviewView";
        private static final long bqc = 200;
        private Camera bpT;
        final /* synthetic */ CameraPreviewView bqb;
        private long bqd;
        private b bqe;
        private int bqf = getResources().getDimensionPixelSize(d.camera_focus_area_size);
        private Matrix bqg = new Matrix();

        public RealCameraPreviewView(CameraPreviewView cameraPreviewView, Context context, Camera camera) {
            this.bqb = cameraPreviewView;
            super(context);
            this.bpT = camera;
            getHolder().addCallback(this);
            if (!UtilsVersion.hasHoneycomb()) {
                getHolder().setType(3);
            }
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            Parameters parameters = CameraPreviewView.b(this.bpT);
            int wms = widthMeasureSpec;
            int hms = heightMeasureSpec;
            if (parameters != null) {
                Size size = parameters.getPreviewSize();
                float ratio = (1.0f * ((float) size.height)) / ((float) size.width);
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = (int) (((float) width) / ratio);
                wms = MeasureSpec.makeMeasureSpec(width, 1073741824);
                hms = MeasureSpec.makeMeasureSpec(height, 1073741824);
            }
            super.onMeasure(wms, hms);
        }

        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    long downTime = System.currentTimeMillis();
                    Parameters parameters = CameraPreviewView.b(this.bpT);
                    if (parameters != null && parameters.isZoomSupported() && downTime - this.bqd <= bqc) {
                        Nv();
                    }
                    this.bqd = downTime;
                    c(event.getX(), event.getY());
                    break;
            }
            return super.onTouchEvent(event);
        }

        public void onAutoFocus(boolean success, Camera camera) {
            for (a previewEventListener : this.bqb.bpS) {
                previewEventListener.df(success);
            }
            com.huluxia.video.camera.a.a("continuous-video", this.bpT);
        }

        private void Nv() {
            Parameters parameters = CameraPreviewView.b(this.bpT);
            if (parameters != null) {
                int currentZoom = parameters.getZoom();
                int destZoom = currentZoom == 0 ? (int) (((double) (((float) parameters.getMaxZoom()) / 2.0f)) + 0.5d) : 0;
                if (parameters.isSmoothZoomSupported()) {
                    this.bpT.stopSmoothZoom();
                    this.bpT.startSmoothZoom(destZoom);
                    return;
                }
                Handler handler = getHandler();
                if (handler != null) {
                    handler.removeCallbacks(this.bqe);
                    Runnable bVar = new b(destZoom, currentZoom, this.bpT);
                    this.bqe = bVar;
                    handler.post(bVar);
                }
            }
        }

        private void c(float x, float y) {
            Log.d(TAG, "focusOnTouch x = " + x + "y = " + y);
            this.bpT.cancelAutoFocus();
            com.huluxia.video.camera.a.a(EmailTask.AUTO, this.bpT);
            this.bpT.autoFocus(this);
            this.bqb.bpX.cancel();
            this.bqb.bpW.clearAnimation();
            int left = (int) (x - (((float) this.bqb.bpW.getWidth()) / 2.0f));
            int top = (int) (y - (((float) this.bqb.bpW.getHeight()) / 2.0f));
            this.bqb.bpW.layout(left, top, left + this.bqb.bpW.getWidth(), top + this.bqb.bpW.getHeight());
            this.bqb.bpW.setVisibility(0);
            this.bqb.bpW.startAnimation(this.bqb.bpX);
        }

        private Rect d(float x, float y, float coefficient) {
            int areaSize = Float.valueOf(((float) this.bqf) * coefficient).intValue();
            int left = clamp(((int) x) - (areaSize / 2), 0, getWidth() - areaSize);
            int top = clamp(((int) y) - (areaSize / 2), 0, getHeight() - areaSize);
            RectF rectF = new RectF((float) left, (float) top, (float) (left + areaSize), (float) (top + areaSize));
            this.bqg.mapRect(rectF);
            return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
        }

        private int clamp(int x, int min, int max) {
            if (x > max) {
                return max;
            }
            if (x < min) {
                return min;
            }
            return x;
        }

        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated");
            getHolder().addCallback(this);
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "surfaceDestroyed");
            getHolder().removeCallback(this);
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            Log.d(TAG, "surfaceChanged w: " + w + "---h: " + h);
            if (holder.getSurface() != null) {
                try {
                    this.bpT.stopPreview();
                } catch (Exception e) {
                }
                Parameters parameters = CameraPreviewView.b(this.bpT);
                if (parameters != null) {
                    Size size = com.huluxia.video.camera.a.a(parameters.getSupportedPreviewSizes(), Math.min(w, h));
                    Log.d(TAG, "OptimalPreviewSize w: " + size.width + "---h: " + size.height);
                    parameters.setPreviewSize(size.width, size.height);
                    this.bpT.setParameters(parameters);
                    requestLayout();
                    for (a previewEventListener : this.bqb.bpS) {
                        previewEventListener.Nj();
                    }
                    try {
                        this.bpT.setPreviewDisplay(holder);
                        this.bpT.startPreview();
                        for (a previewEventListener2 : this.bqb.bpS) {
                            previewEventListener2.Nk();
                        }
                        if (!this.bqb.bpR) {
                            this.bqb.bpY.startAnimation(this.bqb.bpZ);
                            this.bqb.bpR = true;
                        }
                        c(((float) this.bqb.getWidth()) / 2.0f, ((float) this.bqb.getHeight()) / 2.0f);
                    } catch (Exception e2) {
                        Log.d(TAG, "Error starting camera preview: " + e2.getMessage());
                        for (a previewEventListener22 : this.bqb.bpS) {
                            previewEventListener22.Nl();
                        }
                    }
                }
            }
        }
    }

    private static class b implements Runnable {
        int bqh;
        int bqi;
        WeakReference<Camera> bqj;

        public b(int destZoom, int currentZoom, Camera camera) {
            this.bqh = destZoom;
            this.bqi = currentZoom;
            this.bqj = new WeakReference(camera);
        }

        public void run() {
            Camera camera = (Camera) this.bqj.get();
            if (camera != null) {
                boolean zoomUp = this.bqh > this.bqi;
                int i = this.bqi;
                while (true) {
                    if (zoomUp) {
                        if (i > this.bqh) {
                            return;
                        }
                    } else if (i < this.bqh) {
                        return;
                    }
                    Parameters parameters = CameraPreviewView.b(camera);
                    if (parameters != null) {
                        parameters.setZoom(i);
                        camera.setParameters(parameters);
                        if (zoomUp) {
                            i++;
                            int i2 = i;
                        } else {
                            i--;
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public CameraPreviewView(Context context) {
        this(context, null);
    }

    public CameraPreviewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraPreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.bpR = false;
        this.bpS = new ArrayList();
        setBackgroundColor(-16777216);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, j.CameraPreviewView);
        Drawable focusDrawable = typedArray.getDrawable(j.CameraPreviewView_cpv_focusDrawable);
        Drawable indicatorDrawable = typedArray.getDrawable(j.CameraPreviewView_cpv_indicatorDrawable);
        typedArray.recycle();
        addView(new View(getContext()));
        this.bpY = new ImageView(context);
        if (indicatorDrawable == null) {
            this.bpY.setImageResource(e.ms_smallvideo_icon);
        } else {
            this.bpY.setImageDrawable(indicatorDrawable);
        }
        addView(this.bpY, new LayoutParams(-2, -2, 17));
        this.bpZ = AnimationUtils.loadAnimation(context, com.huluxia.video.b.a.indicator_animation);
        this.bpZ.setAnimationListener(new AnimationListener(this) {
            final /* synthetic */ CameraPreviewView bqb;

            {
                this.bqb = this$0;
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                this.bqb.bpY.setVisibility(4);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.bpW = new ImageView(context);
        this.bpW.setVisibility(4);
        if (focusDrawable == null) {
            this.bpW.setImageResource(e.ms_video_focus_icon);
        } else {
            this.bpW.setImageDrawable(focusDrawable);
        }
        addView(this.bpW, new LayoutParams(-2, -2, 17));
        this.bpX = AnimationUtils.loadAnimation(context, com.huluxia.video.b.a.focus_animation);
        this.bpX.setAnimationListener(new AnimationListener(this) {
            final /* synthetic */ CameraPreviewView bqb;

            {
                this.bqb = this$0;
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                this.bqb.bpW.setVisibility(4);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public View getRealCameraPreviewView() {
        return this.bqa;
    }

    public Camera getCamera() {
        return this.bpT;
    }

    public int getCameraId() {
        return this.bpU;
    }

    public void a(Camera camera, int cameraId) {
        this.bpT = camera;
        this.bpU = cameraId;
        com.huluxia.video.camera.a.a((Activity) getContext(), this.bpU, this.bpT);
        if (this.bqa != null) {
            removeView(this.bqa);
        }
        postDelayed(new Runnable(this) {
            final /* synthetic */ CameraPreviewView bqb;

            {
                this.bqb = this$0;
            }

            public void run() {
                this.bqb.addView(this.bqb.bqa = new RealCameraPreviewView(this.bqb, this.bqb.getContext(), this.bqb.bpT), 0);
            }
        }, this.bpR ? 0 : bpQ);
    }

    public void a(a previewEventListener) {
        this.bpS.add(previewEventListener);
    }

    public void setViewWHRatio(float viewWHRatio) {
        this.bpV = viewWHRatio;
        requestLayout();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, 1073741824), MeasureSpec.makeMeasureSpec((int) (((float) width) / this.bpV), 1073741824));
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.bpS.clear();
        this.bpS = null;
    }

    private static Parameters b(Camera camera) {
        try {
            return camera.getParameters();
        } catch (Exception e) {
            HLog.error(TAG, "Camera is being used after Camera.release() was called", new Object[0]);
            return null;
        }
    }
}
