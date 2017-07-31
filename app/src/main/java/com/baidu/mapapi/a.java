package com.baidu.mapapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.InputDeviceCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.tencent.open.SocialConstants;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import java.lang.reflect.Method;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

public class a extends View {
    private static Method J = null;
    private static Method K = null;
    private static Method L = null;
    private static Bitmap h = null;
    private static ShortBuffer i = null;
    private static short[] j = null;
    private static int k = 0;
    private static int l = 0;
    private static int m = 0;
    private float A;
    private float B;
    private float C;
    private float D;
    private int E = 0;
    private int F = 0;
    private boolean G = false;
    private int H = 0;
    private Paint I = new Paint();
    private MapView$a M = MapView$a.DRAW_RETICLE_OVER;
    boolean a = false;
    int b = 0;
    int c = 0;
    int d = 0;
    double e = 1.0d;
    int f = 0;
    int g = 0;
    private int n = 0;
    private int o = 0;
    private int p = 0;
    private int q = 0;
    private MapView r = null;
    private List<Overlay> s = new ArrayList();
    private boolean t = false;
    private boolean u = true;
    private long v = 0;
    private long w = 0;
    private long x = 0;
    private float y;
    private float z;

    public a(Context context, MapView mapView) {
        super(context);
        this.r = mapView;
        this.I.setColor(-7829368);
        this.I.setStyle(Style.STROKE);
    }

    private void c(Canvas canvas) {
    }

    private void d(Canvas canvas) {
        long nanoTime = (System.nanoTime() * 1000) / 1000000000;
        for (Overlay draw : this.s) {
            draw.draw(canvas, this.r, false, nanoTime);
        }
    }

    void a() {
        if (h != null && h.isRecycled()) {
            h.recycle();
        }
        l = 0;
        m = 0;
        h = null;
    }

    void a(int i) {
        int minZoomLevel = this.r.getMinZoomLevel();
        int maxZoomLevel = this.r.getMaxZoomLevel();
        if (minZoomLevel <= i && i <= maxZoomLevel) {
            Mj.MapProc(InputDeviceCompat.SOURCE_TOUCHSCREEN, i, 1);
        }
    }

    public void a(int i, int i2, int i3) {
        this.F += i;
        this.E = 3;
        if (i2 == 0 && i3 == 0) {
            this.f = h.getWidth() / 2;
            this.g = h.getHeight() / 2;
        } else {
            this.f = i2;
            this.g = i3;
        }
        Mj.p = 1;
        invalidate();
    }

    public void a(int i, int i2, int i3, int i4) {
        if (i4 > 0 && i3 > 0) {
            int i5 = ((i3 + 3) / 4) * 4;
            int i6 = ((i4 + 3) / 4) * 4;
            if (l != i5 || m != i6) {
                for (Overlay a : this.s) {
                    a.a(i, i2, i5, i6);
                }
                int i7 = (i5 * i6) * 2;
                if (i7 > k) {
                    j = new short[i7];
                    i = ShortBuffer.allocate(i7);
                    k = i7;
                }
                if (!(h == null || h.isRecycled())) {
                    h.recycle();
                }
                try {
                    h = Bitmap.createBitmap(i5, i6, Config.RGB_565);
                } catch (OutOfMemoryError e) {
                    System.gc();
                    if (!(h == null || h.isRecycled())) {
                        h.recycle();
                    }
                }
                Mj.p = 1;
                Mj.renderUpdateScreen(j, i5, i6);
                l = i5;
                m = i6;
            }
        }
    }

    protected void a(Canvas canvas) {
        canvas.drawRGB(192, 192, 192);
        Matrix matrix = new Matrix();
        if (this.H == 1 || this.e == 1.0d) {
            canvas.drawBitmap(h, (float) this.c, (float) this.d, null);
            return;
        }
        double abs = Math.abs(this.e);
        matrix.postScale((float) abs, (float) abs, (float) this.f, (float) this.g);
        matrix.postTranslate((float) this.c, (float) this.d);
        canvas.drawBitmap(h, matrix, null);
    }

    void a(boolean z) {
        this.u = z;
    }

    void a(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
    }

    boolean a(int i, KeyEvent keyEvent) {
        boolean z = false;
        for (int size = this.s.size() - 1; size >= 0; size--) {
            z = ((Overlay) this.s.get(size)).onKeyDown(i, keyEvent, this.r);
            if (z) {
                break;
            }
        }
        return z;
    }

    boolean a(MotionEvent motionEvent) {
        int i;
        if (Integer.parseInt(VERSION.SDK) <= 4) {
            i = 1;
        } else {
            try {
                if (J == null) {
                    Class cls = Class.forName("android.view.MotionEvent");
                    J = cls.getMethod("getPointerCount", (Class[]) null);
                    L = cls.getMethod("getX", new Class[]{Integer.TYPE});
                    K = cls.getMethod("getY", new Class[]{Integer.TYPE});
                }
                i = ((Integer) J.invoke(motionEvent, new Object[0])).intValue();
            } catch (Exception e) {
                e.printStackTrace();
                i = 0;
            }
        }
        if (i == 2) {
            try {
                this.y = ((Float) L.invoke(motionEvent, new Object[]{Integer.valueOf(0)})).floatValue();
                this.z = ((Float) K.invoke(motionEvent, new Object[]{Integer.valueOf(0)})).floatValue();
                this.A = ((Float) L.invoke(motionEvent, new Object[]{Integer.valueOf(1)})).floatValue();
                this.B = ((Float) K.invoke(motionEvent, new Object[]{Integer.valueOf(1)})).floatValue();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.D = ((float) Math.sqrt((double) (((this.y - this.A) * (this.y - this.A)) + ((this.z - this.B) * (this.z - this.B))))) / 2.0f;
            if (this.E == 0) {
                if (this.D > Mj.j * 30.0f) {
                    this.t = false;
                    this.f = (int) (((this.y + this.A) / 2.0f) - ((float) this.c));
                    this.g = (int) (((this.z + this.B) / 2.0f) - ((float) this.d));
                    this.C = this.D;
                    this.E = 1;
                }
            } else if (this.H == 1) {
                double log = Math.log((double) (this.D / this.C)) / Math.log(3.0d);
                i = log >= 0.0d ? (int) (log + 0.5d) : (int) (log - 0.5d);
                if (i != 0) {
                    this.C = this.D;
                    b(i, (this.f + this.c) - (l / 2), (this.g + this.d) - (m / 2));
                }
            } else {
                this.e = (double) (this.D / this.C);
                invalidate();
            }
        } else if (this.E == 1) {
            this.E = 2;
            invalidate();
        }
        if (this.E != 0) {
            return false;
        }
        int size;
        for (size = this.s.size() - 1; size >= 0; size--) {
            if (((Overlay) this.s.get(size)).onTouchEvent(motionEvent, this.r)) {
                return true;
            }
        }
        switch (motionEvent.getAction()) {
            case 0:
                this.n = (int) motionEvent.getX();
                this.o = (int) motionEvent.getY();
                this.c = 0;
                this.d = 0;
                this.t = true;
                Mj.l = (int) motionEvent.getX();
                Mj.m = (int) motionEvent.getY();
                Mj.MapProc(4, (int) motionEvent.getX(), (int) motionEvent.getY());
                break;
            case 1:
                if (this.t) {
                    this.t = false;
                    if (!(this.c == 0 && this.d == 0)) {
                        this.c = 0;
                        this.d = 0;
                    }
                    int x = (int) motionEvent.getX();
                    i = (int) motionEvent.getY();
                    if (this.u) {
                        size = (int) (50.0f * Mj.j);
                        if (this.x - this.w < 1000 && Math.abs(x - this.n) < size && Math.abs(i - this.o) < size) {
                            this.v++;
                        }
                        if (this.v != 1) {
                            if (this.v >= 2) {
                                this.x = System.currentTimeMillis();
                                if (this.x - this.w < 1000 && Math.abs(this.p - x) < size && Math.abs(this.q - i) < size) {
                                    if (this.r.getZoomLevel() < 18) {
                                        a(1, x, i);
                                    }
                                    this.v = 0;
                                    this.w = 0;
                                    this.x = 0;
                                    break;
                                }
                                this.v = 0;
                                this.w = 0;
                                this.x = 0;
                            }
                        } else {
                            this.w = System.currentTimeMillis();
                        }
                    }
                    size = (int) (10.0f * Mj.j);
                    if (Math.abs(this.n - x) < size && Math.abs(this.o - i) < size) {
                        x = this.n;
                        i = this.o;
                    }
                    this.p = x;
                    this.q = i;
                    Mj.n = x;
                    Mj.o = i;
                    Mj.MapProc(5, x, i);
                    if (Math.abs(x - this.n) < 20 && Math.abs(i - this.o) < 20) {
                        for (x = this.s.size() - 1; x >= 0; x--) {
                            Overlay overlay = (Overlay) this.s.get(x);
                            GeoPoint fromPixels = this.r.getProjection().fromPixels((int) motionEvent.getX(), (int) motionEvent.getY());
                            if (overlay instanceof ItemizedOverlay ? ((ItemizedOverlay) overlay).onTap(fromPixels, this.r) : overlay.onTap(fromPixels, this.r)) {
                                return true;
                            }
                        }
                        break;
                    }
                }
                return true;
                break;
            case 2:
                if (this.t) {
                    this.c = ((int) motionEvent.getX()) - this.n;
                    this.d = ((int) motionEvent.getY()) - this.o;
                    invalidate();
                    Mj.MapProc(3, (int) motionEvent.getX(), (int) motionEvent.getY());
                    break;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    void b(int i) {
        this.H = i;
    }

    void b(int i, int i2, int i3) {
        int minZoomLevel = this.r.getMinZoomLevel();
        int maxZoomLevel = this.r.getMaxZoomLevel();
        int zoomLevel = this.r.getZoomLevel() + i;
        if (minZoomLevel <= zoomLevel && zoomLevel <= maxZoomLevel) {
            Bundle bundle = new Bundle();
            bundle.putInt(SocialConstants.PARAM_ACT, ErrorCode.ERROR_HOSTAPP_UNAVAILABLE);
            bundle.putInt("opt", 10020600);
            bundle.putInt("level", i);
            bundle.putInt("x", this.f);
            bundle.putInt("y", this.g);
            bundle.putInt("dx", i2);
            bundle.putInt("dy", i3);
            Mj.sendBundle(bundle);
        }
    }

    protected void b(Canvas canvas) {
        a(canvas);
        if (this.G || this.e == 1.0d) {
            switch (this.M) {
                case DRAW_RETICLE_NEVER:
                    d(canvas);
                    return;
                case DRAW_RETICLE_OVER:
                    d(canvas);
                    c(canvas);
                    return;
                case DRAW_RETICLE_UNDER:
                    c(canvas);
                    d(canvas);
                    return;
                default:
                    c(canvas);
                    d(canvas);
                    return;
            }
        }
    }

    void b(boolean z) {
        this.G = z;
    }

    boolean b() {
        return this.u;
    }

    boolean b(int i, KeyEvent keyEvent) {
        boolean z = false;
        for (int size = this.s.size() - 1; size >= 0; size--) {
            z = ((Overlay) this.s.get(size)).onKeyUp(i, keyEvent, this.r);
            if (z) {
                break;
            }
        }
        return z;
    }

    boolean b(MotionEvent motionEvent) {
        boolean z = false;
        for (int size = this.s.size() - 1; size >= 0; size--) {
            z = ((Overlay) this.s.get(size)).onTrackballEvent(motionEvent, this.r);
            if (z) {
                break;
            }
        }
        return z;
    }

    public final List<Overlay> c() {
        return this.s;
    }

    boolean d() {
        for (int size = this.s.size() - 1; size >= 0; size--) {
            if (((Overlay) this.s.get(size)) instanceof MyLocationOverlay) {
                return true;
            }
        }
        return false;
    }

    protected void onDraw(Canvas canvas) {
        this.r.e();
        double d;
        switch (this.E) {
            case 0:
                if (j != null && this.a) {
                    this.c = 0;
                    this.d = 0;
                    i.rewind();
                    i.put(j);
                    i.rewind();
                    h.copyPixelsFromBuffer(i);
                    this.a = false;
                    this.e = 1.0d;
                }
                b(canvas);
                return;
            case 1:
                if (this.H == 1 && j != null && this.a) {
                    this.c = 0;
                    this.d = 0;
                    i.rewind();
                    i.put(j);
                    i.rewind();
                    h.copyPixelsFromBuffer(i);
                    this.a = false;
                    this.e = 1.0d;
                }
                b(canvas);
                return;
            case 2:
                if (this.H == 1) {
                    this.E = 0;
                    b(canvas);
                    return;
                }
                double log = Math.log(this.e) / Math.log(2.0d);
                int i = log >= 0.0d ? (int) (0.5d + log) : (int) (log - 0.5d);
                int i2 = (int) ((((double) i) - log) * 10.0d);
                if (i2 != 0) {
                    int i3;
                    if (i2 > 0) {
                        d = 0.1d + log;
                        i3 = i2 - 1;
                    } else {
                        d = log - 0.1d;
                        i3 = i2 + 1;
                    }
                    this.e = Math.pow(2.0d, d);
                    b(canvas);
                    postInvalidate();
                    return;
                }
                this.e = Math.pow(2.0d, (double) i);
                b(canvas);
                if (log != 0.0d) {
                    b(i, (this.f + this.c) - (l / 2), (this.g + this.d) - (m / 2));
                }
                this.E = 0;
                return;
            case 3:
                if (this.H == 1) {
                    b(canvas);
                    b(this.F, 0, 0);
                    this.E = 0;
                    this.F = 0;
                    return;
                }
                d = Math.log(this.e) / Math.log(2.0d);
                if (this.F > 0) {
                    this.e = (Math.pow(2.0d, (double) ((int) d)) / 10.0d) + this.e;
                } else if (this.F < 0) {
                    this.e -= Math.pow(2.0d, (double) ((int) d)) / 20.0d;
                }
                if ((this.e >= ((double) (this.F * 2)) || this.e <= 0.0d) && (this.F >= 0 || this.e <= ((double) (-1.0f / ((float) (this.F * 2)))))) {
                    b(canvas);
                    b(this.F, 0, 0);
                    this.E = 0;
                    this.F = 0;
                    return;
                }
                b(canvas);
                postInvalidate();
                return;
            default:
                b(canvas);
                return;
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        a(i, i2, i3 - i, i4 - i2);
    }
}
