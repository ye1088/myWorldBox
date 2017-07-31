package com.huawei.android.pushagent.plugin.tools;

import android.content.Context;
import android.location.Location;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKGeneralListener;
import com.huawei.android.pushagent.c.a.e;

public class BLocation {
    public static final String TAG = "PushLogSC2705";
    private static BLocation a = null;
    private static BMapManager b = null;
    private LocationListener c = new MyLocationListener();
    private Location d;

    private static class MyGeneralListener implements MKGeneralListener {
        private MyGeneralListener() {
        }

        public void onGetNetworkState(int i) {
            e.a(BLocation.TAG, "onGetNetworkState error is " + i);
        }

        public void onGetPermissionState(int i) {
            e.a(BLocation.TAG, "onGetPermissionState error is " + i);
            if (i == 300) {
                e.a(BLocation.TAG, "key is invalid");
            }
        }
    }

    private class MyLocationListener implements LocationListener {
        final /* synthetic */ BLocation a;

        private MyLocationListener(BLocation bLocation) {
            this.a = bLocation;
        }

        public void onLocationChanged(Location location) {
            e.a(BLocation.TAG, "onLocationChanged:" + com.huawei.android.pushagent.c.a.a.e.a(location));
            if (location != null) {
                try {
                    this.a.d = location;
                    BLocation.b.getLocationManager().removeUpdates(this.a.c);
                    BLocation.b.stop();
                } catch (Throwable e) {
                    e.a(BLocation.TAG, "onLocationChanged error:" + e.getMessage(), e);
                }
            }
        }
    }

    private BLocation() {
    }

    private synchronized void a(Context context) {
        if (b == null) {
            try {
                b = new BMapManager(context.getApplicationContext());
                b.init("C031E14DA209AEF13B985501A3ACC7F83053D7D4", new MyGeneralListener());
                b.start();
            } catch (Throwable e) {
                e.a(TAG, "initBdLocation error:" + e.getMessage(), e);
            }
        }
    }

    public static synchronized BLocation getInstance(Context context) {
        BLocation bLocation;
        synchronized (BLocation.class) {
            if (a == null) {
                a = new BLocation();
                a.a(context);
            }
            bLocation = a;
        }
        return bLocation;
    }

    public Location getLocation() {
        return this.d;
    }

    public void requestLocation() {
        try {
            this.d = null;
            b.getLocationManager().requestLocationUpdates(this.c);
            b.start();
        } catch (Throwable e) {
            e.a(TAG, "requestLocation error:" + e.getMessage(), e);
        }
    }
}
