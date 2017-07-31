package com.baidu.mapapi;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.telephony.NeighboringCellInfo;
import android.util.Log;
import java.util.List;

class f {
    Location a = null;
    private LocationManager b = null;
    private LocationListener c = null;
    private Context d;
    private a e = null;
    private int f = 0;
    private GpsStatus g;

    private class a implements Listener {
        final /* synthetic */ f a;

        private a(f fVar) {
            this.a = fVar;
        }

        public void onGpsStatusChanged(int i) {
            switch (i) {
                case 2:
                    Mj.UpdataGPS(0.0d, 0.0d, 0.0f, 0.0f, 0.0f, 0);
                    return;
                case 4:
                    if (this.a.b == null) {
                        this.a.b = (LocationManager) this.a.d.getSystemService("location");
                    }
                    if (this.a.b != null) {
                        if (this.a.g == null) {
                            this.a.g = this.a.b.getGpsStatus(null);
                        } else {
                            this.a.b.getGpsStatus(this.a.g);
                        }
                    }
                    int i2 = 0;
                    for (GpsSatellite usedInFix : this.a.g.getSatellites()) {
                        i2 = usedInFix.usedInFix() ? i2 + 1 : i2;
                    }
                    if (i2 < 3 && this.a.f >= 3) {
                        Mj.UpdataGPS(0.0d, 0.0d, 0.0f, 0.0f, 0.0f, 0);
                    }
                    this.a.f = i2;
                    return;
                default:
                    return;
            }
        }
    }

    public f(Context context) {
        this.d = context;
    }

    String a(int i, int i2, int i3, int i4, List<NeighboringCellInfo> list, String str) {
        if (i3 == 0 || i4 == 0) {
            return "";
        }
        return str.concat(String.format("%d|%d|%d|%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)}));
    }

    public String a(List<ScanResult> list, String str) {
        String str2 = "";
        String str3 = "";
        int size = list.size();
        if (list.size() > 10) {
            size = 10;
        }
        int i = 0;
        while (i < size) {
            String str4;
            if (i == 0) {
                if (((ScanResult) list.get(i)).level == 0) {
                    str4 = str2;
                } else {
                    str4 = str2.concat(((ScanResult) list.get(i)).BSSID.replace(":", "")).concat(String.format(";%d;", new Object[]{Integer.valueOf(((ScanResult) list.get(i)).level)})).concat(((ScanResult) list.get(i)).SSID);
                }
            } else if (((ScanResult) list.get(i)).level == 0) {
                break;
            } else {
                str4 = str2.concat("|").concat(((ScanResult) list.get(i)).BSSID.replace(":", "")).concat(String.format(";%d;", new Object[]{Integer.valueOf(((ScanResult) list.get(i)).level)})).concat(((ScanResult) list.get(i)).SSID);
            }
            i++;
            str2 = str4;
        }
        return str2;
    }

    void a() {
        if (this.d != null) {
            Mj.UpdataGPS(0.0d, 0.0d, 0.0f, 0.0f, 0.0f, 0);
            if (this.c == null) {
                this.c = new LocationListener(this) {
                    final /* synthetic */ f a;

                    {
                        this.a = r1;
                    }

                    public void onLocationChanged(Location location) {
                        int i = 0;
                        if (location != null) {
                            Bundle extras = location.getExtras();
                            if (extras != null) {
                                i = extras.getInt("NumSatellite", 0);
                            }
                            float f = 0.0f;
                            if (location.hasAccuracy()) {
                                f = location.getAccuracy();
                            }
                            Mj.UpdataGPS(location.getLongitude(), location.getLatitude(), (float) (((double) location.getSpeed()) * 3.6d), location.getBearing(), f, i);
                            this.a.a = location;
                        }
                    }

                    public void onProviderDisabled(String str) {
                        Mj.UpdataGPS(0.0d, 0.0d, 0.0f, 0.0f, 0.0f, 0);
                        this.a.a = null;
                    }

                    public void onProviderEnabled(String str) {
                    }

                    public void onStatusChanged(String str, int i, Bundle bundle) {
                        switch (i) {
                            case 0:
                            case 1:
                                Mj.UpdataGPS(0.0d, 0.0d, 0.0f, 0.0f, 0.0f, 0);
                                this.a.a = null;
                                return;
                            default:
                                return;
                        }
                    }
                };
            }
            if (this.b == null) {
                this.b = (LocationManager) this.d.getSystemService("location");
            }
            if (this.b != null) {
                try {
                    this.b.requestLocationUpdates("gps", 1000, 0.0f, this.c);
                    this.e = new a();
                    this.b.addGpsStatusListener(this.e);
                } catch (Exception e) {
                    Log.d("InitGPS", e.getMessage());
                }
            }
        }
    }

    public void a(int i, int i2, long j) {
        switch (i) {
            case 5000:
                if (i2 == 1) {
                    a();
                    return;
                } else {
                    b();
                    return;
                }
            default:
                return;
        }
    }

    public void a(List<ScanResult> list) {
        Object obj = 1;
        for (int size = list.size() - 1; size >= 1 && r2 != null; size--) {
            int i = 0;
            obj = null;
            while (i < size) {
                Object obj2;
                if (((ScanResult) list.get(i)).level < ((ScanResult) list.get(i + 1)).level) {
                    ScanResult scanResult = (ScanResult) list.get(i + 1);
                    list.set(i + 1, list.get(i));
                    list.set(i, scanResult);
                    obj2 = 1;
                } else {
                    obj2 = obj;
                }
                i++;
                obj = obj2;
            }
        }
    }

    public boolean a(List<ScanResult> list, List<ScanResult> list2) {
        if (list == list2) {
            return true;
        }
        if (list == null || list2 == null) {
            return false;
        }
        int size = list.size();
        int size2 = list2.size();
        if (size == 0 || size2 == 0) {
            return false;
        }
        int i = 0;
        int i2 = 0;
        while (i < size) {
            int i3;
            String str = ((ScanResult) list.get(i)).BSSID;
            if (str == null) {
                i3 = i2;
            } else {
                for (int i4 = 0; i4 < size2; i4++) {
                    if (str.equals(((ScanResult) list2.get(i4)).BSSID)) {
                        i3 = i2 + 1;
                        break;
                    }
                }
                i3 = i2;
            }
            i++;
            i2 = i3;
        }
        return i2 >= size / 2;
    }

    public String b(List<ScanResult> list, String str) {
        String str2 = "";
        String str3 = "";
        int size = list.size();
        if (list.size() > 10) {
            size = 10;
        }
        int i = 0;
        while (i < size) {
            String concat;
            if (i == 0) {
                concat = ((ScanResult) list.get(i)).level == 0 ? str2 : str2.concat(((ScanResult) list.get(i)).BSSID.replace(":", ""));
            } else if (((ScanResult) list.get(i)).level == 0) {
                break;
            } else {
                concat = str2.concat("|").concat(((ScanResult) list.get(i)).BSSID.replace(":", ""));
            }
            i++;
            str2 = concat;
        }
        return str2;
    }

    void b() {
        try {
            if (this.b != null) {
                this.b.removeUpdates(this.c);
            }
        } catch (Exception e) {
            Log.d("UnInitGPS", e.getMessage());
        }
        Mj.UpdataGPS(0.0d, 0.0d, 0.0f, 0.0f, 0.0f, 0);
    }
}
