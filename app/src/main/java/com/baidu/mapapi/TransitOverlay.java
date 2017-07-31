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
import org.mozilla.classfile.ByteCode;

public class TransitOverlay extends ItemizedOverlay<OverlayItem> {
    private ArrayList<a> a = null;
    private MapView b = null;
    private Context c = null;
    private int d = 1;
    private DisplayMetrics e;
    public MKTransitRoutePlan mPlan = null;
    public boolean mUseToast = true;

    private class a {
        public String a;
        public GeoPoint b;
        public int c;
        final /* synthetic */ TransitOverlay d;

        private a(TransitOverlay transitOverlay) {
            this.d = transitOverlay;
        }
    }

    public TransitOverlay(Activity activity, MapView mapView) {
        super(null);
        this.c = activity;
        this.b = mapView;
        this.e = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(this.e);
        if (this.e.widthPixels <= 320 && this.e.heightPixels <= 320) {
            this.d = 0;
        } else if (this.e.widthPixels > 480 || this.e.heightPixels > 480) {
            this.d = 2;
        } else {
            this.d = 1;
        }
    }

    public void animateTo() {
        if (size() > 0) {
            onTap(0);
        }
    }

    protected OverlayItem createItem(int i) {
        char[] cArr = new char[]{'l', 'm', 'h'};
        String[] strArr = new String[]{"start", "end", "bus", "foot", "rail", "car"};
        a aVar = (a) this.a.get(i);
        OverlayItem overlayItem = new OverlayItem(aVar.b, aVar.a, null);
        if (aVar.c != 3) {
            StringBuilder stringBuilder = new StringBuilder(32);
            stringBuilder.append("icon_nav_").append(strArr[aVar.c]).append('_').append(cArr[this.d]).append(hlx.data.localstore.a.bKa);
            Drawable a = n.a(this.c, stringBuilder.toString());
            if (aVar.c == 0 || aVar.c == 1) {
                overlayItem.setMarker(ItemizedOverlay.boundCenterBottom(a));
            } else {
                overlayItem.setMarker(ItemizedOverlay.boundCenter(a));
            }
        }
        return overlayItem;
    }

    public boolean draw(Canvas canvas, MapView mapView, boolean z, long j) {
        Projection projection = mapView.getProjection();
        ((Activity) this.c).getWindowManager().getDefaultDisplay().getMetrics(this.e);
        if (this.mPlan != null) {
            int numLines = this.mPlan.getNumLines();
            if (numLines + 1 == this.mPlan.getNumRoute() && numLines != 0) {
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setStrokeWidth(6.0f);
                Point point = new Point();
                Point point2 = new Point();
                ArrayList arrayList = new ArrayList();
                int zoomLevel = mapView.getZoomLevel();
                int i = 0;
                while (i < numLines) {
                    int size;
                    int i2;
                    MKRoute route = this.mPlan.getRoute(i);
                    ArrayList arrayPoints = route.getArrayPoints();
                    if (arrayPoints != null && arrayPoints.size() > 0) {
                        arrayPoints = (ArrayList) arrayPoints.get(0);
                        if (arrayPoints != null && arrayPoints.size() > 0) {
                            n.a(arrayPoints, (ArrayList) route.a.get(0), zoomLevel, arrayList);
                            size = arrayList.size();
                            if (size > 1) {
                                paint.setColor(Color.rgb(48, 162, 8));
                                paint.setAlpha(192);
                                projection.toPixels((GeoPoint) arrayList.get(0), point);
                                for (i2 = 1; i2 < size; i2++) {
                                    projection.toPixels((GeoPoint) arrayList.get(i2), point2);
                                    if (n.a(point, point2, this.e.widthPixels, this.e.heightPixels)) {
                                        canvas.drawLine((float) point.x, (float) point.y, (float) point2.x, (float) point2.y, paint);
                                    }
                                    point.x = point2.x;
                                    point.y = point2.y;
                                }
                            }
                        }
                    }
                    MKLine line = this.mPlan.getLine(i);
                    ArrayList points = line.getPoints();
                    if (points != null && points.size() > 0) {
                        n.a(points, line.a, zoomLevel, arrayList);
                        size = arrayList.size();
                        if (size > 1) {
                            paint.setColor(Color.rgb(58, 107, ByteCode.ANEWARRAY));
                            paint.setAlpha(192);
                            projection.toPixels((GeoPoint) arrayList.get(0), point);
                            for (i2 = 1; i2 < size; i2++) {
                                projection.toPixels((GeoPoint) arrayList.get(i2), point2);
                                if (n.a(point, point2, this.e.widthPixels, this.e.heightPixels)) {
                                    canvas.drawLine((float) point.x, (float) point.y, (float) point2.x, (float) point2.y, paint);
                                }
                                point.x = point2.x;
                                point.y = point2.y;
                            }
                        }
                    }
                    if (i == numLines - 1) {
                        route = this.mPlan.getRoute(i + 1);
                        arrayPoints = route.getArrayPoints();
                        if (arrayPoints != null && arrayPoints.size() > 0) {
                            arrayPoints = (ArrayList) arrayPoints.get(0);
                            if (arrayPoints != null && arrayPoints.size() > 0) {
                                n.a(arrayPoints, (ArrayList) route.a.get(0), zoomLevel, arrayList);
                                i = arrayList.size();
                                if (i > 1) {
                                    paint.setColor(Color.rgb(48, 162, 8));
                                    paint.setAlpha(192);
                                    projection.toPixels((GeoPoint) arrayList.get(0), point);
                                    for (i2 = 1; i2 < i; i2++) {
                                        projection.toPixels((GeoPoint) arrayList.get(i2), point2);
                                        if (n.a(point, point2, this.e.widthPixels, this.e.heightPixels)) {
                                            canvas.drawLine((float) point.x, (float) point.y, (float) point2.x, (float) point2.y, paint);
                                        }
                                        point.x = point2.x;
                                        point.y = point2.y;
                                    }
                                }
                            }
                        }
                    } else {
                        i++;
                    }
                }
            }
        }
        return super.draw(canvas, mapView, z, j);
    }

    protected boolean onTap(int i) {
        OverlayItem item = getItem(i);
        this.b.getController().animateTo(item.mPoint);
        if (this.mUseToast && item.mTitle != null) {
            Toast.makeText(this.c, item.mTitle, 1).show();
        }
        super.onTap(i);
        return true;
    }

    public void setData(MKTransitRoutePlan mKTransitRoutePlan) {
        int i = 0;
        if (mKTransitRoutePlan != null) {
            this.a = new ArrayList();
            this.mPlan = mKTransitRoutePlan;
            int numLines = mKTransitRoutePlan.getNumLines();
            if (numLines + 1 == this.mPlan.getNumRoute() && numLines != 0) {
                a aVar;
                GeoPoint start = mKTransitRoutePlan.getStart();
                if (start != null) {
                    aVar = new a();
                    aVar.b = start;
                    aVar.c = 0;
                    this.a.add(aVar);
                }
                while (i < numLines) {
                    MKLine line = mKTransitRoutePlan.getLine(i);
                    aVar = new a();
                    aVar.b = line.getGetOnStop().pt;
                    aVar.a = line.a();
                    if (line.getType() == 0) {
                        aVar.c = 2;
                    } else {
                        aVar.c = 4;
                    }
                    this.a.add(aVar);
                    MKRoute route = this.mPlan.getRoute(i + 1);
                    a aVar2 = new a();
                    aVar2.b = line.getGetOffStop().pt;
                    aVar2.a = route.a();
                    if (line.getType() == 0) {
                        aVar2.c = 2;
                    } else {
                        aVar2.c = 4;
                    }
                    this.a.add(aVar2);
                    i++;
                }
                GeoPoint end = mKTransitRoutePlan.getEnd();
                if (end != null) {
                    a aVar3 = new a();
                    aVar3.b = end;
                    aVar3.c = 1;
                    this.a.add(aVar3);
                }
                super.populate();
            }
        }
    }

    public int size() {
        return this.a == null ? 0 : this.a.size();
    }
}
