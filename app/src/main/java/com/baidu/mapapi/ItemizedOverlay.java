package com.baidu.mapapi;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.baidu.mapapi.Overlay.Snappable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public abstract class ItemizedOverlay<Item extends OverlayItem> extends Overlay implements Snappable {
    private static int d = -1;
    private boolean a = true;
    private Drawable b;
    private Drawable c;
    private a e = null;
    private OnFocusChangeListener f = null;
    private int g = -1;
    private int h = -1;

    public interface OnFocusChangeListener {
        void onFocusChanged(ItemizedOverlay<?> itemizedOverlay, OverlayItem overlayItem);
    }

    class a implements Comparator<Integer> {
        final /* synthetic */ ItemizedOverlay a;
        private ArrayList<Item> b;
        private ArrayList<Integer> c;
        private ItemizedOverlay<Item> d;

        public a(ItemizedOverlay itemizedOverlay) {
            this.a = itemizedOverlay;
            this.d = itemizedOverlay;
            int size = itemizedOverlay.size();
            this.b = new ArrayList(size);
            this.c = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                this.c.add(Integer.valueOf(i));
                this.b.add(itemizedOverlay.createItem(i));
            }
            Collections.sort(this.c, this);
        }

        private Point a(OverlayItem overlayItem, Projection projection, Point point) {
            Point toPixels = projection.toPixels(overlayItem.getPoint(), null);
            return new Point(point.x - toPixels.x, point.y - toPixels.y);
        }

        public final int a() {
            return this.b.size();
        }

        public final int a(Item item) {
            if (item == null) {
                return -1;
            }
            for (int i = 0; i < this.b.size(); i++) {
                if (item.equals(this.b.get(i))) {
                    return i;
                }
            }
            return -1;
        }

        public int a(Integer num, Integer num2) {
            GeoPoint point = ((OverlayItem) this.b.get(num.intValue())).getPoint();
            GeoPoint point2 = ((OverlayItem) this.b.get(num2.intValue())).getPoint();
            if (point.getLatitudeE6() > point2.getLatitudeE6()) {
                return -1;
            }
            if (point.getLatitudeE6() < point2.getLatitudeE6()) {
                return 1;
            }
            if (point.getLongitudeE6() < point2.getLongitudeE6()) {
                return -1;
            }
            return point.getLongitudeE6() == point2.getLongitudeE6() ? 0 : 1;
        }

        public final int a(boolean z) {
            if (this.b.size() == 0) {
                return 0;
            }
            Iterator it = this.b.iterator();
            int i = Integer.MIN_VALUE;
            int i2 = Integer.MAX_VALUE;
            while (it.hasNext()) {
                GeoPoint point = ((OverlayItem) it.next()).getPoint();
                int latitudeE6 = z ? point.getLatitudeE6() : point.getLongitudeE6();
                if (latitudeE6 > i) {
                    i = latitudeE6;
                }
                if (latitudeE6 >= i2) {
                    latitudeE6 = i2;
                }
                i2 = latitudeE6;
            }
            return i - i2;
        }

        public final Item a(int i) {
            return (OverlayItem) this.b.get(i);
        }

        public final boolean a(GeoPoint geoPoint, MapView mapView) {
            int i;
            Projection projection = mapView.getProjection();
            Point toPixels = projection.toPixels(geoPoint, null);
            int size = this.b.size();
            for (int i2 = 0; i2 < size; i2++) {
                OverlayItem overlayItem = (OverlayItem) this.b.get(i2);
                Point a = a(overlayItem, projection, toPixels);
                Drawable drawable = overlayItem.mMarker;
                if (drawable == null) {
                    drawable = ItemizedOverlay.a(this.d);
                }
                double d = this.d.hitTest(overlayItem, drawable, a.x, a.y) ? (double) ((a.x * a.x) + (a.y * a.y)) : -1.0d;
                if (d >= 0.0d && d < Double.MAX_VALUE) {
                    i = i2;
                    break;
                }
            }
            i = -1;
            if (-1 != i) {
                return this.d.onTap(i);
            }
            this.d.setFocus(null);
            return false;
        }

        public final int b(int i) {
            return ((Integer) this.c.get(i)).intValue();
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return a((Integer) obj, (Integer) obj2);
        }
    }

    enum b {
        Normal("Normal", 0),
        Center("Center", 1),
        CenterBottom("CenterBottom", 2);

        private b(String str, int i) {
        }
    }

    public ItemizedOverlay(Drawable drawable) {
        this.b = drawable;
        if (this.b != null) {
            this.c = new r().a(this.b);
            if (d == 0) {
                a(this.b, b.CenterBottom);
            }
        }
    }

    private static Drawable a(Drawable drawable, b bVar) {
        int i = 0;
        if (drawable == null || b.Normal == bVar) {
            return null;
        }
        Rect bounds = drawable.getBounds();
        if (bounds.height() == 0 || bounds.width() == 0) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        bounds = drawable.getBounds();
        int width = bounds.width() / 2;
        int i2 = -bounds.height();
        if (bVar == b.Center) {
            i2 /= 2;
            i = -i2;
        }
        drawable.setBounds(-width, i2, width, i);
        return drawable;
    }

    static Drawable a(ItemizedOverlay<?> itemizedOverlay) {
        return itemizedOverlay.b;
    }

    private void a(Canvas canvas, MapView mapView, boolean z, OverlayItem overlayItem, int i) {
        Drawable marker = overlayItem.getMarker(i);
        if (marker != null || this.b != null) {
            boolean equals = marker != null ? this.b == null ? false : marker.equals(this.b) : true;
            if (equals) {
                if (z) {
                    marker = this.c;
                    this.c.setBounds(this.b.copyBounds());
                    r.a(this.c, this.b);
                } else {
                    marker = this.b;
                }
            }
            Point toPixels = mapView.getProjection().toPixels(overlayItem.getPoint(), null);
            if (equals) {
                Overlay.a(canvas, marker, toPixels.x, toPixels.y);
            } else {
                Overlay.drawAt(canvas, marker, toPixels.x, toPixels.y, z);
            }
        }
    }

    protected static Drawable boundCenter(Drawable drawable) {
        d = 2;
        return a(drawable, b.Center);
    }

    protected static Drawable boundCenterBottom(Drawable drawable) {
        d = 1;
        return a(drawable, b.CenterBottom);
    }

    protected abstract Item createItem(int i);

    public void draw(Canvas canvas, MapView mapView, boolean z) {
        if (this.e != null) {
            int a = this.e.a();
            for (int i = 0; i < a; i++) {
                int indexToDraw = getIndexToDraw(i);
                if (indexToDraw != this.h) {
                    OverlayItem item = getItem(indexToDraw);
                    Point toPixels = mapView.getProjection().toPixels(item.getPoint(), null);
                    int left = mapView.getLeft();
                    int right = mapView.getRight();
                    int top = mapView.getTop();
                    int bottom = mapView.getBottom();
                    toPixels.x += left;
                    toPixels.y += top;
                    if (toPixels.x >= left && toPixels.y >= top && toPixels.x <= right && toPixels.y <= bottom) {
                        a(canvas, mapView, z, item, 0);
                    }
                }
            }
        }
        if (this.a) {
            OverlayItem focus = getFocus();
            if (focus != null) {
                a(canvas, mapView, false, focus, 4);
            }
        }
    }

    public GeoPoint getCenter() {
        return getItem(getIndexToDraw(0)).getPoint();
    }

    public Item getFocus() {
        return this.h != -1 ? this.e.a(this.h) : null;
    }

    protected int getIndexToDraw(int i) {
        return this.e.b(i);
    }

    public final Item getItem(int i) {
        return this.e.a(i);
    }

    public final int getLastFocusedIndex() {
        return this.g;
    }

    public int getLatSpanE6() {
        return this.e.a(true);
    }

    public int getLonSpanE6() {
        return this.e.a(false);
    }

    protected boolean hitTest(OverlayItem overlayItem, Drawable drawable, int i, int i2) {
        Rect bounds = drawable.getBounds();
        bounds.left -= 10;
        bounds.right += 10;
        bounds.bottom += 10;
        bounds.top -= 10;
        boolean contains = bounds.contains(i, i2);
        bounds.left += 10;
        bounds.right -= 10;
        bounds.bottom -= 10;
        bounds.top = 10 + bounds.top;
        return contains;
    }

    public Item nextFocus(boolean z) {
        if (this.e.a() == 0) {
            return null;
        }
        if (this.g == -1) {
            return this.h != -1 ? this.e.a(0) : null;
        } else {
            int i = this.h != -1 ? this.h : this.g;
            return z ? i != this.e.a() + -1 ? this.e.a(i + 1) : null : i != 0 ? this.e.a(i - 1) : null;
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent, MapView mapView) {
        return false;
    }

    public boolean onSnapToItem(int i, int i2, Point point, MapView mapView) {
        if (this.e.a() <= 0) {
            return false;
        }
        Point point2 = new Point();
        OverlayItem a = this.e.a(0);
        mapView.getProjection().toPixels(a.getPoint(), point2);
        if (!hitTest(a, a.mMarker, i - point2.x, i2 - point2.y)) {
            return false;
        }
        point.x = point2.x;
        point.y = point2.y;
        return true;
    }

    protected boolean onTap(int i) {
        if (i != this.h) {
            setFocus(getItem(i));
        }
        return false;
    }

    public boolean onTap(GeoPoint geoPoint, MapView mapView) {
        return this.e != null ? this.e.a(geoPoint, mapView) : false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
        return false;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent, MapView mapView) {
        return false;
    }

    protected final void populate() {
        this.e = new a(this);
        this.g = -1;
        this.h = -1;
    }

    public void setDrawFocusedItem(boolean z) {
        this.a = z;
    }

    public void setFocus(Item item) {
        if (item != null && this.h == this.e.a((OverlayItem) item)) {
            return;
        }
        if (item != null || this.h == -1) {
            this.h = this.e.a((OverlayItem) item);
            if (this.h != -1) {
                setLastFocusedIndex(this.h);
                if (this.f != null) {
                    this.f.onFocusChanged(this, item);
                    return;
                }
                return;
            }
            return;
        }
        if (this.f != null) {
            this.f.onFocusChanged(this, item);
        }
        this.h = -1;
    }

    protected void setLastFocusedIndex(int i) {
        this.g = i;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.f = onFocusChangeListener;
    }

    public abstract int size();
}
