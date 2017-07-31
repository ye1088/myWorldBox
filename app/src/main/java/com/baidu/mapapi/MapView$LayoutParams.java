package com.baidu.mapapi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;

public class MapView$LayoutParams extends LayoutParams {
    public static final int BOTTOM = 80;
    public static final int BOTTOM_CENTER = 81;
    public static final int CENTER = 17;
    public static final int CENTER_HORIZONTAL = 1;
    public static final int CENTER_VERTICAL = 16;
    public static final int LEFT = 3;
    public static final int MODE_MAP = 0;
    public static final int MODE_VIEW = 1;
    public static final int RIGHT = 5;
    public static final int TOP = 48;
    public static final int TOP_LEFT = 51;
    public int alignment;
    public int mode;
    public GeoPoint point;
    public int x;
    public int y;

    public MapView$LayoutParams(int i, int i2, int i3, int i4, int i5) {
        super(i, i2);
        this.mode = 1;
        this.point = null;
        this.x = 0;
        this.y = 0;
        this.alignment = 51;
        this.mode = 1;
        this.x = i3;
        this.y = i4;
        this.alignment = i5;
    }

    public MapView$LayoutParams(int i, int i2, GeoPoint geoPoint, int i3) {
        this(i, i2, geoPoint, 0, 0, i3);
    }

    public MapView$LayoutParams(int i, int i2, GeoPoint geoPoint, int i3, int i4, int i5) {
        super(i, i2);
        this.mode = 1;
        this.point = null;
        this.x = 0;
        this.y = 0;
        this.alignment = 51;
        this.mode = 0;
        this.point = geoPoint;
        this.x = i3;
        this.y = i4;
        this.alignment = i5;
    }

    public MapView$LayoutParams(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mode = 1;
        this.point = null;
        this.x = 0;
        this.y = 0;
        this.alignment = 51;
    }

    public MapView$LayoutParams(LayoutParams layoutParams) {
        super(layoutParams);
        this.mode = 1;
        this.point = null;
        this.x = 0;
        this.y = 0;
        this.alignment = 51;
    }
}
