package com.baidu.mapapi;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import com.baidu.mapapi.Overlay.Snappable;
import com.huluxia.mcfloat.InstanceZones.e;
import java.io.InputStream;

public class MyLocationOverlay extends Overlay implements SensorEventListener, LocationListener, Snappable {
    private Location a = null;
    private GeoPoint b = null;
    private GeoPoint c = null;
    private Runnable d = null;
    private MapView e = null;
    private Bitmap f = null;
    private Bitmap g = null;
    private Bitmap h = null;
    private boolean i = false;
    private boolean j = false;
    private float k = 0.0f;
    private Paint l = null;

    public MyLocationOverlay(Context context, MapView mapView) {
        if (mapView == null) {
            throw new IllegalArgumentException("mapView is null");
        }
        this.e = mapView;
        this.l = new Paint();
        this.l.setARGB(35, 0, 0, 128);
        this.l.setStyle(Style.FILL_AND_STROKE);
        this.l.setAntiAlias(true);
        try {
            AssetManager assets = context.getAssets();
            InputStream open = assets.open("icon_my.png");
            this.f = BitmapFactory.decodeStream(open);
            open.close();
            open = assets.open("compass_bg.png");
            this.g = BitmapFactory.decodeStream(open);
            open.close();
            InputStream open2 = assets.open("compass_pointer.png");
            this.h = BitmapFactory.decodeStream(open2);
            open2.close();
        } catch (Exception e) {
        }
    }

    protected GeoPoint GetBaidu09Point(GeoPoint geoPoint, int i) {
        if (geoPoint == null) {
            return null;
        }
        switch (i) {
            case 0:
                return CoordinateConvert.bundleDecode(CoordinateConvert.fromWgs84ToBaidu(geoPoint));
            case 1:
                return CoordinateConvert.bundleDecode(CoordinateConvert.fromGcjToBaidu(geoPoint));
            default:
                return geoPoint;
        }
    }

    public void disableCompass() {
        this.j = false;
        Mj.b.b(this);
        this.e.b.b = 0;
        this.e.invalidate();
    }

    public void disableMyLocation() {
        this.i = false;
        this.a = null;
        this.b = null;
        this.c = null;
        Mj.b.removeUpdates(this);
        this.e.invalidate();
    }

    protected boolean dispatchTap() {
        return false;
    }

    public boolean draw(Canvas canvas, MapView mapView, boolean z, long j) {
        if (z) {
            return false;
        }
        if (!(this.a == null || this.b == null || this.c == null)) {
            drawMyLocation(canvas, mapView, this.a, this.c, j);
        }
        if (this.j) {
            drawCompass(canvas, this.k);
        }
        return true;
    }

    protected void drawCompass(Canvas canvas, float f) {
        if (f <= 360.0f && f >= -360.0f) {
            canvas.drawBitmap(this.g, (float) 10, (float) 10, null);
            int width = (this.h.getWidth() / 2) + 10;
            int height = (this.h.getHeight() / 2) + 10;
            Matrix matrix = new Matrix();
            matrix.postTranslate((float) 10, (float) 10);
            matrix.postRotate(-f, (float) width, (float) height);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawBitmap(this.h, matrix, paint);
        }
    }

    protected void drawMyLocation(Canvas canvas, MapView mapView, Location location, GeoPoint geoPoint, long j) {
        if (geoPoint != null) {
            Point toPixels = mapView.getProjection().toPixels(geoPoint, null);
            canvas.drawBitmap(this.f, (float) (toPixels.x - (this.f.getWidth() / 2)), (float) (toPixels.y - (this.f.getHeight() / 2)), null);
            if (location.hasAccuracy()) {
                canvas.drawCircle((float) toPixels.x, (float) toPixels.y, mapView.getProjection().metersToEquatorPixels(location.getAccuracy()), this.l);
            }
        }
    }

    public boolean enableCompass() {
        this.j = Mj.b.a((SensorEventListener) this);
        return this.j;
    }

    public boolean enableMyLocation() {
        Mj.b.requestLocationUpdates(this);
        this.a = Mj.b.getLocationInfo();
        if (this.a != null) {
            this.b = new GeoPoint(this.a.getLatitude(), this.a.getLongitude());
            this.c = GetBaidu09Point(this.b, Mj.c);
        }
        this.i = true;
        this.e.invalidate();
        return true;
    }

    public Location getLastFix() {
        return this.a;
    }

    public GeoPoint getMyLocation() {
        return this.b;
    }

    public float getOrientation() {
        return this.k;
    }

    public boolean isCompassEnabled() {
        return this.j;
    }

    public boolean isMyLocationEnabled() {
        return this.i;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onLocationChanged(Location location) {
        this.a = location;
        if (this.a == null) {
            this.b = null;
            this.c = null;
            this.e.invalidate();
            return;
        }
        this.b = new GeoPoint(location.getLatitude(), location.getLongitude());
        this.c = GetBaidu09Point(this.b, Mj.c);
        this.e.invalidate();
        if (this.d != null) {
            this.d.run();
            this.d = null;
        }
    }

    public void onProviderDisabled(String str) {
        this.i = false;
    }

    public void onProviderEnabled(String str) {
        this.i = true;
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 3) {
            int i = (int) sensorEvent.values[0];
            if (this.e.getResources().getConfiguration().orientation == 2) {
                i = (i + 90) % e.Wx;
            }
            if (Math.abs(((float) i) - this.k) >= 10.0f) {
                this.k = (float) i;
                if (this.e.b != null) {
                    this.e.b.b = i;
                    this.e.invalidate();
                }
            }
        }
    }

    public boolean onSnapToItem(int i, int i2, Point point, MapView mapView) {
        return false;
    }

    public boolean onTap(GeoPoint geoPoint, MapView mapView) {
        GeoPoint myLocation = getMyLocation();
        if (myLocation == null) {
            return false;
        }
        Point toPixels = this.e.getProjection().toPixels(myLocation, null);
        if (this.f == null) {
            return false;
        }
        int width = this.f.getWidth() / 2;
        int height = this.f.getHeight() / 2;
        Rect rect = new Rect(toPixels.x - width, toPixels.y - height, width + toPixels.x, toPixels.y + height);
        toPixels = this.e.getProjection().toPixels(geoPoint, null);
        return rect.contains(toPixels.x, toPixels.y) ? dispatchTap() : false;
    }

    public boolean runOnFirstFix(Runnable runnable) {
        if (this.a != null) {
            runnable.run();
            return true;
        }
        this.d = runnable;
        return false;
    }
}
