package com.baidu.mapapi;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.util.e;

public class MKLocationManager {
    public static final int MK_COORDINATE_BAIDU09 = 2;
    public static final int MK_COORDINATE_GCJ = 1;
    public static final int MK_COORDINATE_WGS84 = 0;
    public static final int MK_GPS_PROVIDER = 0;
    public static final int MK_NETWORK_PROVIDER = 1;
    private Location a = null;
    private Location b = null;
    private long c = 864000;
    private long d = 0;
    private List<LocationListener> e = new ArrayList();
    private List<SensorEventListener> f = new ArrayList();
    private SensorEventListener g = null;

    MKLocationManager() {
    }

    void a() {
        this.b = null;
    }

    boolean a(SensorEventListener sensorEventListener) {
        if (this.f.size() != 0) {
            return this.f.add(sensorEventListener);
        }
        if (!d()) {
            return false;
        }
        if (this.f.add(sensorEventListener)) {
            return true;
        }
        e();
        return false;
    }

    boolean a(BMapManager bMapManager) {
        if (bMapManager == null) {
            return false;
        }
        Mj.r = true;
        this.e.clear();
        return Mj.InitLocationCC() == 1;
    }

    void b() {
        Mj.DisableProviderCC(0);
        Mj.DisableProviderCC(1);
    }

    void b(SensorEventListener sensorEventListener) {
        this.f.remove(sensorEventListener);
        if (this.f.size() == 0) {
            e();
        }
    }

    void c() {
        Location locationInfo = getLocationInfo();
        long currentTimeMillis = System.currentTimeMillis();
        int i;
        if (locationInfo != null && currentTimeMillis - this.d >= this.c * 1000) {
            this.d = currentTimeMillis;
            for (i = 0; i < this.e.size(); i++) {
                ((LocationListener) this.e.get(i)).onLocationChanged(locationInfo);
                this.b = locationInfo;
            }
        } else if (this.b == null || locationInfo == null || ((double) locationInfo.distanceTo(this.b)) >= 0.1d || locationInfo.getAccuracy() != this.b.getAccuracy() || locationInfo.getBearing() != this.b.getBearing() || locationInfo.getSpeed() != this.b.getSpeed() || locationInfo.getAltitude() != this.b.getAltitude()) {
            this.d = currentTimeMillis;
            for (i = 0; i < this.e.size(); i++) {
                ((LocationListener) this.e.get(i)).onLocationChanged(locationInfo);
                this.b = locationInfo;
            }
        }
    }

    boolean d() {
        SensorManager sensorManager = (SensorManager) Mj.e.getSystemService("sensor");
        if (sensorManager == null) {
            return false;
        }
        Sensor defaultSensor = sensorManager.getDefaultSensor(3);
        if (defaultSensor == null) {
            return false;
        }
        this.g = new SensorEventListener(this) {
            final /* synthetic */ MKLocationManager a;

            {
                this.a = r1;
            }

            public void onAccuracyChanged(Sensor sensor, int i) {
                for (int i2 = 0; i2 < this.a.f.size(); i2++) {
                    ((SensorEventListener) this.a.f.get(i2)).onAccuracyChanged(sensor, i);
                }
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                for (int i = 0; i < this.a.f.size(); i++) {
                    ((SensorEventListener) this.a.f.get(i)).onSensorChanged(sensorEvent);
                }
            }
        };
        return sensorManager.registerListener(this.g, defaultSensor, 3);
    }

    public boolean disableProvider(int i) {
        if ((i != 0 && i != 1) || Mj.DisableProviderCC(i) == 0) {
            return false;
        }
        if (i == 0) {
            Mj.q = false;
        } else if (i == 1) {
            Mj.r = false;
        }
        return true;
    }

    void e() {
        if (this.g != null) {
            SensorManager sensorManager = (SensorManager) Mj.e.getSystemService("sensor");
            if (sensorManager != null && sensorManager.getDefaultSensor(3) != null) {
                sensorManager.unregisterListener(this.g);
            }
        }
    }

    public boolean enableProvider(int i) {
        if ((i != 0 && i != 1) || Mj.EnableProviderCC(i) == 0) {
            return false;
        }
        if (i == 0) {
            Mj.q = true;
        } else if (i == 1) {
            Mj.r = true;
        }
        return true;
    }

    public Location getLocationInfo() {
        Bundle GetGPSStatus = Mj.GetGPSStatus();
        if (GetGPSStatus == null) {
            return null;
        }
        if (GetGPSStatus.getInt("t") == 1) {
            this.a = new Location("network");
            this.a.setLatitude((double) GetGPSStatus.getFloat("y"));
            this.a.setLongitude((double) GetGPSStatus.getFloat("x"));
            this.a.setAccuracy((float) GetGPSStatus.getInt(e.clf));
        } else {
            this.a = Mj.a.a;
            this.a.setLatitude((double) GetGPSStatus.getFloat("y"));
            this.a.setLongitude((double) GetGPSStatus.getFloat("x"));
        }
        return this.a;
    }

    public Bundle getNotifyInternal() {
        return Mj.GetNotifyInternal();
    }

    public void removeUpdates(LocationListener locationListener) {
        this.e.remove(locationListener);
    }

    public void requestLocationUpdates(LocationListener locationListener) {
        this.e.add(locationListener);
    }

    public void setLocationCoordinateType(int i) {
        Mj.c = i;
        Mj.SetLocationCoordinateType(i);
    }

    public boolean setNotifyInternal(int i, int i2) {
        if (i < i2 || i2 < 0) {
            return false;
        }
        this.c = (long) i;
        return Mj.SetNotifyInternal(i, i2) == 1;
    }
}
