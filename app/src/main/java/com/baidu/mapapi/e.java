package com.baidu.mapapi;

import android.graphics.Point;
import android.os.Bundle;
import com.tencent.open.SocialConstants;

class e implements Projection {
    private MapView a = null;

    public e(MapView mapView) {
        this.a = mapView;
    }

    Point a(GeoPoint geoPoint, Point point) {
        Bundle bundle = new Bundle();
        bundle.putInt(SocialConstants.PARAM_ACT, 15010200);
        bundle.putInt("x", geoPoint.getLongitudeE6());
        bundle.putInt("y", geoPoint.getLatitudeE6());
        Mj.sendBundle(bundle);
        if (point == null) {
            point = new Point(0, 0);
        }
        point.x = bundle.getInt("x");
        point.y = bundle.getInt("y");
        return point;
    }

    public GeoPoint fromPixels(int i, int i2) {
        int i3 = i - this.a.b.c;
        int i4 = i2 - this.a.b.d;
        if (this.a.b.e != 1.0d) {
            i3 = ((int) (((double) (i3 - this.a.b.f)) / this.a.b.e)) + this.a.b.f;
            i4 = ((int) (((double) (i4 - this.a.b.g)) / this.a.b.e)) + this.a.b.g;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(SocialConstants.PARAM_ACT, 15010100);
        bundle.putInt("x", i3);
        bundle.putInt("y", i4);
        Mj.sendBundle(bundle);
        return new GeoPoint(bundle.getInt("y"), bundle.getInt("x"));
    }

    public float metersToEquatorPixels(float f) {
        return (float) (((double) f) / this.a.h());
    }

    public Point toPixels(GeoPoint geoPoint, Point point) {
        Bundle bundle = new Bundle();
        bundle.putInt(SocialConstants.PARAM_ACT, 15010200);
        bundle.putInt("x", geoPoint.getLongitudeE6());
        bundle.putInt("y", geoPoint.getLatitudeE6());
        Mj.sendBundle(bundle);
        if (point == null) {
            point = new Point(0, 0);
        }
        point.x = bundle.getInt("x");
        point.y = bundle.getInt("y");
        if (this.a.b.e != 1.0d) {
            int i = (int) ((((double) (point.y - this.a.b.g)) * this.a.b.e) + 0.5d);
            point.x = ((int) ((((double) (point.x - this.a.b.f)) * this.a.b.e) + 0.5d)) + this.a.b.f;
            point.y = this.a.b.g + i;
        }
        point.x += this.a.b.c;
        point.y += this.a.b.d;
        return point;
    }
}
