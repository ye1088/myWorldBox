package com.baidu.mapapi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

class n {
    private static Constructor<?> a = null;
    private static final int b = Integer.parseInt(VERSION.SDK);

    private enum a {
        DOUGLAS_METHOD,
        GRID_METHOD,
        DOUGLAS_MOBILE
    }

    n() {
    }

    private static double a(int i, a aVar) {
        switch (aVar) {
            case DOUGLAS_METHOD:
                double log = Math.log(Math.pow(2.6d, (double) i));
                return 100.0d + (log * (2.6d * log));
            case DOUGLAS_MOBILE:
                return Math.pow(2.0d, (double) i) * 4.0d;
            case GRID_METHOD:
                return 100.0d + (Math.log(Math.pow(2.6d, (double) i)) * 65.0d);
            default:
                return 100.0d;
        }
    }

    static int a(ArrayList<GeoPoint> arrayList, ArrayList<GeoPoint> arrayList2, int i, ArrayList<GeoPoint> arrayList3) {
        if (arrayList == null || arrayList2 == null || arrayList3 == null) {
            return -1;
        }
        arrayList3.clear();
        a((ArrayList) arrayList, (ArrayList) arrayList2, (ArrayList) arrayList3, a(18 - i, a.DOUGLAS_MOBILE));
        return 0;
    }

    static int a(ArrayList<GeoPoint> arrayList, ArrayList<GeoPoint> arrayList2, ArrayList<GeoPoint> arrayList3, double d) {
        int i = 0;
        int size = arrayList.size();
        if (size < 2) {
            return -1;
        }
        int i2;
        int[] iArr = new int[size];
        for (i2 = 0; i2 < size; i2++) {
            iArr[i2] = 1;
        }
        a(arrayList2, iArr, 0, size - 1, d);
        i2 = 0;
        while (i < size) {
            if (iArr[i] > 0) {
                arrayList3.add(arrayList.get(i));
                i2++;
            }
            i++;
        }
        return i2;
    }

    static int a(ArrayList<GeoPoint> arrayList, int[] iArr, int i, int i2, double d) {
        if (i2 <= i + 1) {
            return 0;
        }
        double d2 = ((d * d) * 100.0d) * 100.0d;
        long j = -1;
        int i3 = 0;
        int i4 = i + 1;
        while (i4 < i2) {
            long a = a((GeoPoint) arrayList.get(i4), (GeoPoint) arrayList.get(i), (GeoPoint) arrayList.get(i2));
            if (a > j) {
                i3 = i4;
            } else {
                a = j;
            }
            i4++;
            j = a;
        }
        if (((double) j) >= d2) {
            a(arrayList, iArr, i, i3, d);
            a(arrayList, iArr, i3, i2, d);
        } else {
            for (int i5 = i + 1; i5 < i2; i5++) {
                iArr[i5] = 0;
            }
        }
        return 0;
    }

    static long a(GeoPoint geoPoint, GeoPoint geoPoint2, GeoPoint geoPoint3) {
        long longitudeE6 = (long) geoPoint.getLongitudeE6();
        long latitudeE6 = (long) geoPoint.getLatitudeE6();
        long longitudeE62 = (long) geoPoint2.getLongitudeE6();
        long latitudeE62 = (long) geoPoint2.getLatitudeE6();
        long longitudeE63 = (long) geoPoint3.getLongitudeE6();
        long latitudeE63 = (long) geoPoint3.getLatitudeE6();
        long j = longitudeE62 - longitudeE63;
        long j2 = latitudeE62 - latitudeE63;
        j = (j * j) + (j2 * j2);
        if (j == 0) {
            longitudeE6 -= longitudeE62;
            latitudeE6 -= latitudeE62;
            return (longitudeE6 * longitudeE6) + (latitudeE6 * latitudeE6);
        }
        double d = ((double) (((latitudeE62 - latitudeE6) * (latitudeE62 - latitudeE63)) - ((longitudeE62 - longitudeE6) * (longitudeE63 - longitudeE62)))) / ((double) j);
        if (d > 1.0d || d < 0.0d) {
            longitudeE62 = longitudeE6 - longitudeE62;
            longitudeE63 = longitudeE6 - longitudeE63;
            longitudeE6 = latitudeE6 - latitudeE62;
            latitudeE6 -= latitudeE63;
            longitudeE6 = (longitudeE6 * longitudeE6) + (longitudeE62 * longitudeE62);
            latitudeE6 = (latitudeE6 * latitudeE6) + (longitudeE63 * longitudeE63);
            return longitudeE6 >= latitudeE6 ? latitudeE6 : longitudeE6;
        } else {
            longitudeE6 = ((latitudeE62 - latitudeE6) * (-(longitudeE62 - longitudeE63))) - ((longitudeE62 - longitudeE6) * (latitudeE63 - latitudeE62));
            return (long) (((double) longitudeE6) * (((double) longitudeE6) / ((double) j)));
        }
    }

    static Drawable a(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            Bitmap decodeStream = BitmapFactory.decodeStream(open);
            open.close();
            if (b < 4) {
                return new BitmapDrawable(decodeStream);
            }
            Resources resources = context.getResources();
            if (a == null) {
                a = Class.forName("android.graphics.drawable.BitmapDrawable").getConstructor(new Class[]{Resources.class, Bitmap.class});
            }
            return (Drawable) a.newInstance(new Object[]{resources, decodeStream});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static Drawable a(Context context, String str, float f) {
        try {
            InputStream open = context.getAssets().open(str);
            Bitmap decodeStream = BitmapFactory.decodeStream(open);
            open.close();
            Matrix matrix = new Matrix();
            matrix.reset();
            matrix.setRotate(f);
            Bitmap createBitmap = Bitmap.createBitmap(decodeStream, 0, 0, decodeStream.getWidth(), decodeStream.getHeight(), matrix, true);
            if (b < 4) {
                return new BitmapDrawable(createBitmap);
            }
            Resources resources = context.getResources();
            if (a == null) {
                a = Class.forName("android.graphics.drawable.BitmapDrawable").getConstructor(new Class[]{Resources.class, Bitmap.class});
            }
            return (Drawable) a.newInstance(new Object[]{resources, createBitmap});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static boolean a(Point point, Point point2, int i, int i2) {
        return (point.x > 0 || point2.x > 0) ? (point.x < i || point2.x < i) ? (point.y > 0 || point2.y > 0) ? point.y < i2 || point2.y < i2 : false : false : false;
    }
}
