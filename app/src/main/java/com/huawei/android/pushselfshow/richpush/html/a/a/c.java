package com.huawei.android.pushselfshow.richpush.html.a.a;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.richpush.html.a.j;
import com.huawei.android.pushselfshow.richpush.html.api.d.a;

public class c implements LocationListener {
    protected LocationManager a;
    protected boolean b = false;
    private j c;
    private String d = "PLocationListener";

    public c(LocationManager locationManager, j jVar, String str) {
        this.a = locationManager;
        this.c = jVar;
        this.d = str;
    }

    private void a(Location location) {
        this.c.b(location);
        if (!this.c.b) {
            e.a(this.d, "Stopping global listener");
            b();
        }
    }

    public void a() {
        b();
    }

    public void a(long j, float f) {
        try {
            if (!this.b) {
                if (this.a.getProvider("network") != null) {
                    this.b = true;
                    this.a.requestLocationUpdates("network", j, f, this);
                    return;
                }
                a(a.POSITION_UNAVAILABLE_NETOWRK);
            }
        } catch (Throwable e) {
            e.d(this.d, "start postion error ", e);
        }
    }

    protected void a(a aVar) {
        this.c.a(aVar);
        if (!this.c.b) {
            e.a(this.d, "Stopping global listener");
            b();
        }
    }

    public void b() {
        try {
            if (this.b) {
                this.a.removeUpdates(this);
                this.b = false;
            }
        } catch (Throwable e) {
            e.d(this.d, "stop postion error ", e);
        }
    }

    public void onLocationChanged(Location location) {
        e.a(this.d, "The location has been updated!");
        a(location);
    }

    public void onProviderDisabled(String str) {
        e.a(this.d, "Location provider '" + str + "' disabled.");
        a(a.POSITION_UNAVAILABLE_GPS);
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
        e.a(this.d, "The status of the provider " + str + " has changed");
        if (i == 0) {
            e.a(this.d, str + " is OUT OF SERVICE");
            if ("network".equals(str)) {
                a(a.POSTION_OUT_OF_SERVICE_NETOWRK);
            } else {
                a(a.POSTION_OUT_OF_SERVICE_GPS);
            }
        } else if (i == 1) {
            e.a(this.d, str + " is TEMPORARILY_UNAVAILABLE");
        } else {
            e.a(this.d, str + " is AVAILABLE");
        }
    }
}
