package com.baidu.mapapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import java.util.List;

public abstract class MapActivity extends Activity {
    private MapView a = null;

    boolean a(MapView mapView) {
        if (this.a != null) {
            throw new RuntimeException("A mapview has been created!!");
        }
        this.a = mapView;
        Mj.d = mapView;
        return true;
    }

    public boolean initMapActivity(BMapManager bMapManager) {
        if (bMapManager == null) {
            return false;
        }
        if (this.a == null) {
            throw new RuntimeException("A mapview has not been created!!");
        } else if (Mj.InitMapControlCC(20, 40) != 1) {
            return false;
        } else {
            this.a.a();
            return true;
        }
    }

    protected boolean isLocationDisplayed() {
        return this.a != null ? this.a.b.d() : false;
    }

    protected abstract boolean isRouteDisplayed();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Mj.g = defaultDisplay.getWidth();
        Mj.h = defaultDisplay.getHeight();
    }

    protected void onDestroy() {
        if (this.a != null) {
            List overlays = this.a.getOverlays();
            if (overlays != null) {
                for (int size = overlays.size() - 1; size >= 0; size--) {
                    Overlay overlay = (Overlay) overlays.get(size);
                    if (overlay instanceof MyLocationOverlay) {
                        MyLocationOverlay myLocationOverlay = (MyLocationOverlay) overlay;
                        myLocationOverlay.disableMyLocation();
                        myLocationOverlay.disableCompass();
                    }
                }
                overlays.clear();
            }
            this.a.b();
        }
        this.a = null;
        super.onDestroy();
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        if (Mj.d != this.a) {
            Mj.d = this.a;
            if (this.a != null) {
                this.a.b.a(this.a.getLeft(), this.a.getTop(), this.a.getRight(), this.a.getBottom());
            }
        }
        super.onResume();
    }
}
