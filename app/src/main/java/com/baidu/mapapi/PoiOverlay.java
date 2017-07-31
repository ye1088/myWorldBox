package com.baidu.mapapi;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.Toast;
import hlx.data.localstore.a;
import java.util.ArrayList;

public class PoiOverlay extends ItemizedOverlay<OverlayItem> {
    private MapView a;
    private Context b;
    private int c;
    private MKSearch d;
    public ArrayList<MKPoiInfo> mList;
    public boolean mUseToast;

    public PoiOverlay(Activity activity, MapView mapView) {
        super(null);
        this.mList = null;
        this.a = null;
        this.b = null;
        this.c = 1;
        this.mUseToast = true;
        this.b = activity;
        this.a = mapView;
        activity.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        if (Mj.i <= 120) {
            this.c = 0;
        } else if (Mj.i <= 180) {
            this.c = 1;
        } else {
            this.c = 2;
        }
    }

    public PoiOverlay(Activity activity, MapView mapView, MKSearch mKSearch) {
        this(activity, mapView);
        this.d = mKSearch;
    }

    public void animateTo() {
        if (size() > 0) {
            onTap(0);
        }
    }

    protected OverlayItem createItem(int i) {
        char[] cArr = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        char[] cArr2 = new char[]{'l', 'm', 'h'};
        MKPoiInfo mKPoiInfo = (MKPoiInfo) this.mList.get(i);
        OverlayItem overlayItem = new OverlayItem(mKPoiInfo.pt, mKPoiInfo.name, mKPoiInfo.address);
        Drawable drawable = null;
        if (i < 10) {
            StringBuilder stringBuilder = new StringBuilder(32);
            stringBuilder.append("icon_mark").append(cArr[i]).append('_').append(cArr2[this.c]).append(a.bKa);
            drawable = n.a(this.b, stringBuilder.toString());
        }
        overlayItem.setMarker(ItemizedOverlay.boundCenterBottom(drawable));
        return overlayItem;
    }

    public MKPoiInfo getPoi(int i) {
        return this.mList == null ? null : (MKPoiInfo) this.mList.get(i);
    }

    protected boolean onTap(int i) {
        OverlayItem item = getItem(i);
        this.a.getController().animateTo(item.mPoint);
        if (this.mUseToast && item.mTitle != null) {
            MKPoiInfo poi = getPoi(i);
            if (poi.hasCaterDetails) {
                this.d.poiDetailSearch(poi.uid);
            }
            Toast.makeText(this.b, item.mTitle, 1).show();
        }
        super.onTap(i);
        return true;
    }

    public void setData(ArrayList<MKPoiInfo> arrayList) {
        if (arrayList != null) {
            this.mList = arrayList;
            super.populate();
        }
    }

    public int size() {
        return this.mList == null ? 0 : this.mList.size() <= 10 ? this.mList.size() : 10;
    }
}
