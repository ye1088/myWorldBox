package com.baidu.mapapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ZoomControls;
import com.tencent.open.SocialConstants;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;
import hlx.data.localstore.a;
import java.io.InputStream;
import java.util.List;
import net.lingala.zip4j.util.e;
import org.apache.http.HttpStatus;

public class MapView extends ViewGroup {
    public static final int DRAG_MODE_NONE = 1;
    public static final int DRAG_MODE_SCALE = 0;
    private static int g = 0;
    e a = new e(this);
    a b = null;
    private GeoPoint c = new GeoPoint(0, 0);
    private int d = 12;
    private int e = 0;
    private int f = 0;
    private int h = 18;
    private int i = 3;
    private boolean j = false;
    private boolean k = false;
    private Message l = null;
    private Runnable m = null;
    private MapActivity n = null;
    private MapController o;
    private ZoomControls p = new ZoomControls(getContext());
    private ImageView q = new ImageView(getContext());
    private boolean r = false;

    public MapView(Context context) {
        super(context);
        a(context);
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public MapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(View view, LayoutParams layoutParams) {
        view.measure(this.e, this.f);
        int i = layoutParams.width;
        int i2 = layoutParams.height;
        int measuredWidth = i == -1 ? this.e : i == -2 ? view.getMeasuredWidth() : i;
        if (i2 == -1) {
            i2 = this.f;
        } else if (i2 == -2) {
            i2 = view.getMeasuredHeight();
        }
        if (checkLayoutParams(layoutParams)) {
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            int i3 = layoutParams2.x;
            i = layoutParams2.y;
            if (layoutParams2.mode == 0 && layoutParams2.point != null) {
                Point toPixels = getProjection().toPixels(layoutParams2.point, null);
                i3 = toPixels.x + layoutParams2.x;
                i = toPixels.y + layoutParams2.y;
            }
            switch (layoutParams2.alignment) {
                case 1:
                    i3 -= measuredWidth / 2;
                    break;
                case 5:
                    i3 -= measuredWidth;
                    break;
                case 16:
                    i -= i2 / 2;
                    break;
                case 17:
                    i3 -= measuredWidth / 2;
                    i -= i2 / 2;
                    break;
                case 80:
                    i -= i2;
                    break;
                case 81:
                    i3 -= measuredWidth / 2;
                    i -= i2;
                    break;
            }
            view.layout(i3, i, measuredWidth + i3, i2 + i);
            return;
        }
        view.layout(0, 0, measuredWidth, i2);
    }

    private boolean a(Context context) {
        MapActivity mapActivity = (MapActivity) context;
        this.e = Mj.g;
        this.f = Mj.h;
        this.n = mapActivity;
        g++;
        return mapActivity.a(this);
    }

    private void d(int i) {
        this.d = i;
        c();
    }

    void a(int i) {
        this.h = i;
        if (this.d > i) {
            c(i);
        }
    }

    void a(int i, int i2) {
        Mj.MapProc(4354, i, i2);
    }

    void a(GeoPoint geoPoint) {
        Mj.MapProc(4102, geoPoint.getLongitudeE6(), geoPoint.getLatitudeE6());
    }

    void a(GeoPoint geoPoint, Message message, Runnable runnable) {
        Mj.MapProc(4353, geoPoint.getLongitudeE6(), geoPoint.getLatitudeE6());
        this.l = message;
        this.m = runnable;
    }

    void a(boolean z) {
        if (z) {
            Mj.MapProc(4355, 1, 0);
        } else {
            Mj.MapProc(4355, 0, 0);
        }
    }

    boolean a() {
        InputStream open;
        Bitmap decodeStream;
        OutOfMemoryError e;
        Throwable th;
        InputStream inputStream = null;
        int i = 0;
        if (this.b == null) {
            this.b = new a(getContext(), this);
        }
        if (this.o == null) {
            this.o = new MapController(this);
        }
        if (this.b.getParent() == null) {
            addView(this.b);
        }
        if (this.p.getParent() == null) {
            this.p.setOnZoomOutClickListener(new 1(this));
            this.p.setOnZoomInClickListener(new 2(this));
            this.p.setFocusable(true);
            this.p.setVisibility(0);
            this.p.measure(0, 0);
        }
        if (this.q.getParent() == null) {
            try {
                char[] cArr = new char[]{'l', 'h'};
                if (Mj.i > 180) {
                    i = 1;
                }
                try {
                    open = this.n.getAssets().open("baidumap_logo_" + cArr[i] + a.bKa);
                    try {
                        decodeStream = BitmapFactory.decodeStream(open);
                        open.close();
                    } catch (OutOfMemoryError e2) {
                        e = e2;
                        try {
                            e.printStackTrace();
                            open.close();
                            if (decodeStream != null) {
                                this.q.setImageBitmap(decodeStream);
                                this.q.setVisibility(0);
                                this.q.measure(0, 0);
                                this.q.setScaleType(ScaleType.FIT_CENTER);
                                addView(this.q);
                            }
                            this.b.setFocusable(true);
                            this.b.setFocusableInTouchMode(true);
                            this.d = d();
                            return true;
                        } catch (Throwable th2) {
                            th = th2;
                            inputStream = open;
                            inputStream.close();
                            throw th;
                        }
                    }
                } catch (OutOfMemoryError e3) {
                    e = e3;
                    open = inputStream;
                    e.printStackTrace();
                    open.close();
                    if (decodeStream != null) {
                        this.q.setImageBitmap(decodeStream);
                        this.q.setVisibility(0);
                        this.q.measure(0, 0);
                        this.q.setScaleType(ScaleType.FIT_CENTER);
                        addView(this.q);
                    }
                    this.b.setFocusable(true);
                    this.b.setFocusableInTouchMode(true);
                    this.d = d();
                    return true;
                } catch (Throwable th3) {
                    th = th3;
                    inputStream.close();
                    throw th;
                }
                if (decodeStream != null) {
                    this.q.setImageBitmap(decodeStream);
                    this.q.setVisibility(0);
                    this.q.measure(0, 0);
                    this.q.setScaleType(ScaleType.FIT_CENTER);
                    addView(this.q);
                }
            } catch (Exception e4) {
                Log.d("MapView()", "initMapView() error!");
                Log.d("MapView()", e4.getMessage());
            }
        }
        this.b.setFocusable(true);
        this.b.setFocusableInTouchMode(true);
        this.d = d();
        return true;
    }

    boolean a(int i, int i2, int i3) {
        switch (i) {
            case 9:
                if (this.b == null) {
                    return true;
                }
                this.b.a = true;
                this.b.invalidate();
                return true;
            case HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED /*505*/:
                d(i2);
                return true;
            case 8020:
                if (this.l != null) {
                    if (this.l.getTarget() == null) {
                        return true;
                    }
                    this.l.getTarget().sendMessage(this.l);
                    this.l = null;
                    return true;
                } else if (this.m == null) {
                    return true;
                } else {
                    this.m.run();
                    return true;
                }
            default:
                return false;
        }
    }

    void b() {
        if (g > 0) {
            g--;
            if (g == 0) {
                this.b.a();
                this.b = null;
            }
        }
    }

    void b(int i) {
        this.i = i;
        if (this.d < i) {
            c(i);
        }
    }

    void b(int i, int i2) {
        Mj.MapProc(4103, (this.e / 2) + i, (this.f / 2) + i2);
    }

    void c() {
        boolean z = true;
        this.p.setIsZoomOutEnabled(this.d > getMinZoomLevel());
        ZoomControls zoomControls = this.p;
        if (this.d >= getMaxZoomLevel()) {
            z = false;
        }
        zoomControls.setIsZoomInEnabled(z);
    }

    boolean c(int i) {
        this.d = getZoomLevel();
        if (i < this.i) {
            i = this.i;
        } else if (i > this.h) {
            i = this.h;
        }
        if (this.d == i) {
            return false;
        }
        this.d = i;
        this.b.a(i);
        c();
        return true;
    }

    public boolean canCoverCenter() {
        Bundle bundle = new Bundle();
        bundle.putInt(SocialConstants.PARAM_ACT, 15010001);
        Mj.sendBundle(bundle);
        return bundle.getInt(e.clf) != 0;
    }

    protected boolean checkLayoutParams(LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void computeScroll() {
        super.computeScroll();
    }

    int d() {
        Bundle newBundle = Mj.getNewBundle(10030300, 0, 0);
        return newBundle != null ? newBundle.getInt("mapLevel") : 0;
    }

    public void displayZoomControls(boolean z) {
        if (!this.r || this.p.getParent() == null) {
            removeView(this.p);
            addView(this.p);
            this.r = true;
        }
        if (z) {
            requestChildFocus(this.p, this.p);
        }
    }

    void e() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = super.getChildAt(i);
            if (!(childAt == this.q || childAt == this.p || childAt == this.b)) {
                LayoutParams layoutParams = childAt.getLayoutParams();
                if ((layoutParams instanceof LayoutParams) && ((LayoutParams) layoutParams).mode == 0) {
                    a(childAt, layoutParams);
                }
            }
        }
    }

    boolean f() {
        if (this.d >= this.h) {
            return false;
        }
        this.b.a(1, this.e / 2, this.f / 2);
        return true;
    }

    boolean g() {
        if (this.d <= this.i) {
            return false;
        }
        this.b.a(-1, this.e / 2, this.f / 2);
        return true;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.n, attributeSet);
    }

    protected LayoutParams generateLayoutParams(LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public MapController getController() {
        return this.o;
    }

    public int getLatitudeSpan() {
        e eVar = (e) getProjection();
        return Math.abs(eVar.fromPixels(0, 0).getLatitudeE6() - eVar.fromPixels(this.e - 1, this.f - 1).getLatitudeE6());
    }

    public int getLongitudeSpan() {
        e eVar = (e) getProjection();
        return Math.abs(eVar.fromPixels(this.e - 1, this.f - 1).getLongitudeE6() - eVar.fromPixels(0, 0).getLongitudeE6());
    }

    public GeoPoint getMapCenter() {
        Bundle GetMapStatus = Mj.GetMapStatus();
        if (GetMapStatus != null) {
            int i = GetMapStatus.getInt("x");
            int i2 = GetMapStatus.getInt("y");
            this.c.setLongitudeE6(i);
            this.c.setLatitudeE6(i2);
        }
        return this.c;
    }

    public int getMaxZoomLevel() {
        return this.h;
    }

    public int getMinZoomLevel() {
        return this.i;
    }

    public final List<Overlay> getOverlays() {
        return this.b == null ? null : this.b.c();
    }

    public Projection getProjection() {
        return this.a;
    }

    @Deprecated
    public View getZoomControls() {
        return this.p;
    }

    public int getZoomLevel() {
        int d = d();
        if (this.d != 0) {
            this.d = d;
        }
        return this.d;
    }

    double h() {
        return Math.pow(2.0d, (double) (18 - this.d));
    }

    public void invalidate() {
        if (this.b != null) {
            this.b.invalidate();
        }
        if (this.p != null) {
            this.p.invalidate();
        }
        super.invalidate();
    }

    public boolean isDoubleClickZooming() {
        return this.b.b();
    }

    public boolean isSatellite() {
        return this.k;
    }

    public boolean isStreetView() {
        return false;
    }

    public boolean isTraffic() {
        return this.j;
    }

    protected void onAttachedToWindow() {
        a();
        if (this.r) {
            setBuiltInZoomControls(true);
        }
        super.onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        if (this.r && this.p.getParent() != null) {
            removeView(this.p);
        }
        removeView(this.b);
        removeView(this.q);
        super.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void onFocusChanged(boolean z, int i, Rect rect) {
        this.b.a(z, i, rect);
        super.onFocusChanged(z, i, rect);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.b.a(i, keyEvent) ? true : super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.b.b(i, keyEvent) ? true : super.onKeyUp(i, keyEvent);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.e = i3 - i;
        this.f = i4 - i2;
        this.b.setLayoutParams(new LayoutParams(this.e, this.f));
        this.b.setVisibility(0);
        this.b.layout(0, 0, this.e, this.f);
        this.p.setLayoutParams(new LayoutParams(this.e, this.f));
        this.p.setVisibility(0);
        this.p.measure(i3 - i, i4 - i2);
        int measuredWidth = this.p.getMeasuredWidth();
        int measuredHeight = this.p.getMeasuredHeight();
        this.p.layout((i3 - 10) - measuredWidth, ((i4 - 5) - measuredHeight) - i2, i3 - 10, (i4 - 5) - i2);
        this.q.setLayoutParams(new LayoutParams(this.e, this.f));
        this.q.setVisibility(0);
        this.q.measure(i3 - i, i4 - i2);
        int measuredWidth2 = this.q.getMeasuredWidth();
        measuredWidth = this.q.getMeasuredHeight();
        if (measuredHeight <= measuredWidth) {
            measuredHeight = measuredWidth;
        }
        this.q.layout(10, ((i4 - 5) - measuredHeight) - i2, measuredWidth2 + 10, (i4 - 5) - i2);
        measuredWidth = getChildCount();
        for (measuredHeight = 0; measuredHeight < measuredWidth; measuredHeight++) {
            View childAt = super.getChildAt(measuredHeight);
            if (!(childAt == this.q || childAt == this.p || childAt == this.b)) {
                a(childAt, childAt.getLayoutParams());
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        int i = bundle.getInt("lat");
        int i2 = bundle.getInt("lon");
        if (!(i == 0 || i2 == 0)) {
            a(new GeoPoint(i, i2));
        }
        i = bundle.getInt("zoom");
        if (i != 0) {
            c(i);
        }
        setTraffic(bundle.getBoolean("traffic"));
    }

    public void onSaveInstanceState(Bundle bundle) {
        GeoPoint mapCenter = getMapCenter();
        bundle.putInt("lat", mapCenter.getLatitudeE6());
        bundle.putInt("lon", mapCenter.getLongitudeE6());
        bundle.putInt("zoom", getZoomLevel());
        bundle.putBoolean("traffic", isTraffic());
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.e = i;
        this.f = i2;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return (this.b == null || !this.b.a(motionEvent)) ? super.onTouchEvent(motionEvent) : true;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return this.b.b(motionEvent) ? true : super.onTrackballEvent(motionEvent);
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
    }

    public void preLoad() {
    }

    public void regMapViewListener(BMapManager bMapManager, MKMapViewListener mKMapViewListener) {
        if (bMapManager != null && bMapManager.a != null) {
            bMapManager.a.a(mKMapViewListener);
        }
    }

    public void setBuiltInZoomControls(boolean z) {
        if (z) {
            if (this.r || this.p.getParent() != null) {
                removeView(this.p);
            }
            addView(this.p);
            this.r = true;
            return;
        }
        removeView(this.p);
    }

    public void setDoubleClickZooming(boolean z) {
        this.b.a(z);
    }

    public void setDragMode(int i) {
        this.b.b(i);
    }

    public void setDrawOverlayWhenZooming(boolean z) {
        this.b.b(z);
    }

    public void setReticleDrawMode(a aVar) {
        throw new RuntimeException("this feature is not implemented!!");
    }

    public void setSatellite(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putInt(SocialConstants.PARAM_ACT, ErrorCode.ERROR_HOSTAPP_UNAVAILABLE);
        bundle.putInt("opt", 10020803);
        if (z) {
            bundle.putInt("on", 1);
        } else {
            bundle.putInt("on", 0);
        }
        Mj.sendBundle(bundle);
        this.k = z;
    }

    public void setStreetView(boolean z) {
        throw new RuntimeException("this feature is not implemented!!");
    }

    public void setTraffic(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putInt(SocialConstants.PARAM_ACT, ErrorCode.ERROR_HOSTAPP_UNAVAILABLE);
        bundle.putInt("opt", 10020400);
        if (z) {
            bundle.putInt("on", 1);
        } else {
            bundle.putInt("on", 0);
        }
        Mj.sendBundle(bundle);
        this.j = z;
    }
}
