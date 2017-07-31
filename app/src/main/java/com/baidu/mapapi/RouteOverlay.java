package com.baidu.mapapi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import org.mozilla.classfile.ByteCode;

public class RouteOverlay extends ItemizedOverlay<OverlayItem> {
    private ArrayList<b> a = null;
    private MapView b = null;
    private Context c = null;
    private DisplayMetrics d;
    private int e = 1;
    private HashMap<Integer, a> f;
    public MKRoute mRoute = null;

    class a {
        public GeoPoint a = new GeoPoint(0, 0);
        public ArrayList<Point> b = new ArrayList();
        final /* synthetic */ RouteOverlay c;

        public a(RouteOverlay routeOverlay) {
            this.c = routeOverlay;
        }
    }

    private class b {
        public String a;
        public GeoPoint b;
        public int c;
        public int d;
        final /* synthetic */ RouteOverlay e;

        private b(RouteOverlay routeOverlay) {
            this.e = routeOverlay;
        }
    }

    public RouteOverlay(Activity activity, MapView mapView) {
        super(null);
        this.c = activity;
        this.b = mapView;
        this.d = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(this.d);
        if (Mj.i <= 120) {
            this.e = 0;
        } else if (Mj.i <= 180) {
            this.e = 1;
        } else {
            this.e = 2;
        }
    }

    void a(int i, int i2, int i3, int i4) {
        this.f.clear();
    }

    public void animateTo() {
        if (size() > 0) {
            onTap(0);
        }
    }

    protected OverlayItem createItem(int i) {
        Drawable a;
        char[] cArr = new char[]{'l', 'm', 'h'};
        String[] strArr = new String[]{"start", "end", "foot", "car", "bus"};
        b bVar = (b) this.a.get(i);
        OverlayItem overlayItem = new OverlayItem(bVar.b, bVar.a, null);
        StringBuilder stringBuilder = new StringBuilder(32);
        if (bVar.c == 0 || bVar.c == 1 || bVar.c == 4) {
            stringBuilder.append("icon_nav_").append(strArr[bVar.c]).append('_').append(cArr[this.e]).append(hlx.data.localstore.a.bKa);
            a = n.a(this.c, stringBuilder.toString());
        } else {
            stringBuilder.append("icon_direction_").append(cArr[this.e]).append(hlx.data.localstore.a.bKa);
            a = n.a(this.c, stringBuilder.toString(), (float) (bVar.d * 30));
        }
        if (bVar.c == 0 || bVar.c == 1) {
            overlayItem.setMarker(ItemizedOverlay.boundCenterBottom(a));
        } else {
            overlayItem.setMarker(ItemizedOverlay.boundCenter(a));
        }
        return overlayItem;
    }

    public boolean draw(Canvas canvas, MapView mapView, boolean z, long j) {
        Projection projection = mapView.getProjection();
        ((Activity) this.c).getWindowManager().getDefaultDisplay().getMetrics(this.d);
        int zoomLevel = mapView.getZoomLevel();
        if (this.mRoute != null) {
            ArrayList arrayPoints = this.mRoute.getArrayPoints();
            ArrayList arrayList = this.mRoute.a;
            if (arrayPoints != null && arrayPoints.size() > 0) {
                int size;
                int i;
                a aVar;
                a aVar2 = (a) this.f.get(Integer.valueOf(zoomLevel));
                if (aVar2 == null) {
                    a aVar3 = new a(this);
                    int size2 = arrayPoints.size();
                    ArrayList arrayList2 = new ArrayList();
                    for (int i2 = 0; i2 < size2; i2++) {
                        n.a((ArrayList) arrayPoints.get(i2), (ArrayList) arrayList.get(i2), zoomLevel, arrayList2);
                        size = arrayList2.size();
                        for (i = 0; i < size; i++) {
                            Point point = new Point();
                            mapView.a.a((GeoPoint) arrayList2.get(i), point);
                            aVar3.b.add(point);
                        }
                    }
                    aVar3.a.setLatitudeE6(mapView.getMapCenter().getLatitudeE6());
                    aVar3.a.setLongitudeE6(mapView.getMapCenter().getLongitudeE6());
                    this.f.put(Integer.valueOf(zoomLevel), aVar3);
                    aVar = aVar3;
                } else {
                    aVar = aVar2;
                }
                int size3 = aVar.b.size();
                if (size3 > 1) {
                    int i3;
                    int i4 = 0;
                    i = 0;
                    Point point2 = new Point();
                    Point point3 = new Point();
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setStrokeWidth(6.0f);
                    paint.setColor(Color.rgb(58, 107, ByteCode.ANEWARRAY));
                    paint.setAlpha(192);
                    Point point4 = new Point();
                    Point point5 = new Point();
                    projection.toPixels(mapView.getMapCenter(), point4);
                    projection.toPixels(aVar.a, point5);
                    int i5 = (point4.x - point5.x) - mapView.b.c;
                    int i6 = (point4.y - point5.y) - mapView.b.d;
                    point2.x = ((Point) aVar.b.get(0)).x;
                    point2.y = ((Point) aVar.b.get(0)).y;
                    if (this.b.b.e != 1.0d) {
                        i3 = (int) ((((double) (point2.y - this.b.b.g)) * this.b.b.e) + 0.5d);
                        point2.x = ((int) ((((double) (point2.x - this.b.b.f)) * this.b.b.e) + 0.5d)) + this.b.b.f;
                        point2.y = this.b.b.g + i3;
                    }
                    point2.x -= i5;
                    point2.y -= i6;
                    size = 1;
                    while (size < size3) {
                        int i7;
                        point3.x = ((Point) aVar.b.get(size)).x;
                        point3.y = ((Point) aVar.b.get(size)).y;
                        if (this.b.b.e != 1.0d) {
                            i3 = (int) ((((double) (point3.y - this.b.b.g)) * this.b.b.e) + 0.5d);
                            point3.x = ((int) ((((double) (point3.x - this.b.b.f)) * this.b.b.e) + 0.5d)) + this.b.b.f;
                            point3.y = this.b.b.g + i3;
                        }
                        point3.x -= i5;
                        point3.y -= i6;
                        if (n.a(point2, point3, this.d.widthPixels, this.d.heightPixels)) {
                            canvas.drawLine((float) point2.x, (float) point2.y, (float) point3.x, (float) point3.y, paint);
                            i7 = 1;
                            i = i4;
                        } else if (i == 1) {
                            i7 = i4 + 1;
                            if (i7 > 50) {
                                break;
                            }
                            int i8 = i;
                            i = i7;
                            i7 = i8;
                        } else {
                            i7 = i;
                            i = i4;
                        }
                        point2.x = point3.x;
                        point2.y = point3.y;
                        if (i > 50) {
                            break;
                        }
                        size++;
                        i4 = i;
                        i = i7;
                    }
                }
            }
        }
        return zoomLevel >= 3 ? super.draw(canvas, mapView, z, j) : true;
    }

    protected boolean onTap(int i) {
        OverlayItem item = getItem(i);
        this.b.getController().animateTo(item.mPoint);
        if (item.mTitle != null) {
            Toast.makeText(this.c, item.mTitle, 1).show();
        }
        super.onTap(i);
        return true;
    }

    public void setData(MKRoute mKRoute) {
        int i = 2;
        if (mKRoute != null) {
            this.mRoute = mKRoute;
            this.a = new ArrayList();
            if (this.mRoute.getRouteType() == 1) {
                i = 3;
            } else if (this.mRoute.getRouteType() != 2) {
                i = this.mRoute.getRouteType() == 3 ? 4 : 0;
            }
            GeoPoint start = this.mRoute.getStart();
            if (start != null) {
                b bVar = new b();
                bVar.b = start;
                bVar.c = 0;
                this.a.add(bVar);
            }
            int numSteps = mKRoute.getNumSteps();
            if (numSteps != 0) {
                if (this.a.size() > 0) {
                    ((b) this.a.get(0)).a = mKRoute.getStep(0).getContent();
                }
                for (int i2 = 1; i2 < numSteps - 1; i2++) {
                    MKStep step = mKRoute.getStep(i2);
                    b bVar2 = new b();
                    bVar2.b = step.getPoint();
                    bVar2.a = step.getContent();
                    bVar2.c = i;
                    bVar2.d = step.a();
                    this.a.add(bVar2);
                }
            }
            start = this.mRoute.getEnd();
            if (start != null) {
                b bVar3 = new b();
                bVar3.b = start;
                bVar3.c = 1;
                this.a.add(bVar3);
            }
            this.f = new HashMap();
            super.populate();
        }
    }

    public int size() {
        return this.a == null ? 0 : this.a.size();
    }
}
