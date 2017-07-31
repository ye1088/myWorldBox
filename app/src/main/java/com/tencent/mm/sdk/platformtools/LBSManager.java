package com.tencent.mm.sdk.platformtools;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import com.tencent.mm.sdk.platformtools.MTimerHandler.CallBack;
import com.tencent.mm.sdk.platformtools.PhoneUtil.MacInfo;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.LinkedList;
import java.util.List;
import org.apache.tools.ant.taskdefs.WaitFor;
import org.bytedeco.javacpp.avutil;

public class LBSManager extends BroadcastReceiver {
    private static LocationCache F = null;
    public static final String FILTER_GPS = "filter_gps";
    public static final int INVALID_ACC = -1000;
    public static final float INVALID_LAT = -1000.0f;
    public static final float INVALID_LNG = -1000.0f;
    public static final int MM_SOURCE_HARDWARE = 0;
    public static final int MM_SOURCE_NET = 1;
    public static final int MM_SOURCE_REPORT_HARWARE = 3;
    public static final int MM_SOURCE_REPORT_NETWORK = 4;
    private OnLocationGotListener G;
    private LocationManager H;
    private PendingIntent I;
    private boolean J = false;
    boolean K;
    boolean L = false;
    boolean M = false;
    int N;
    private MTimerHandler O = new MTimerHandler(new CallBack(this) {
        final /* synthetic */ LBSManager P;

        {
            this.P = r1;
        }

        public boolean onTimerExpired() {
            Log.v("MicroMsg.LBSManager", "get location by GPS failed.");
            this.P.K = true;
            this.P.start();
            this.P.J = false;
            return false;
        }
    }, false);
    private Context q;

    static class LocationCache {
        float Q = -1000.0f;
        float R = -1000.0f;
        int S = LBSManager.INVALID_ACC;
        int T = 1;
        long time;

        LocationCache() {
        }
    }

    public interface OnLocationGotListener {
        void onLocationGot(float f, float f2, int i, int i2, String str, String str2, boolean z);
    }

    public LBSManager(Context context, OnLocationGotListener onLocationGotListener) {
        this.G = onLocationGotListener;
        this.K = false;
        this.N = 0;
        this.q = context;
        PhoneUtil.getSignalStrength(context);
        this.H = (LocationManager) context.getSystemService("location");
        a();
        this.I = PendingIntent.getBroadcast(context, 0, new Intent(FILTER_GPS), avutil.AV_CPU_FLAG_AVXSLOW);
    }

    private boolean a() {
        if (this.H == null) {
            return false;
        }
        try {
            this.H.sendExtraCommand("gps", "force_xtra_injection", null);
            this.H.sendExtraCommand("gps", "force_time_injection", null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void b() {
        this.O.stopTimer();
        this.K = true;
    }

    public static void setLocationCache(float f, float f2, int i, int i2) {
        if (i != 0) {
            Log.v("MicroMsg.LBSManager", "setLocationCache [" + f + MiPushClient.ACCEPT_TIME_SEPARATOR + f2 + "] acc:" + i + " source:" + i2);
            if (F == null) {
                F = new LocationCache();
            }
            F.Q = f;
            F.R = f2;
            F.S = i;
            F.time = System.currentTimeMillis();
            F.T = i2;
        }
    }

    public String getTelLocation() {
        return PhoneUtil.getCellXml(PhoneUtil.getCellInfoList(this.q));
    }

    public String getWIFILocation() {
        WifiManager wifiManager = (WifiManager) this.q.getSystemService("wifi");
        if (wifiManager == null) {
            Log.e("MicroMsg.LBSManager", "no wifi service");
            return "";
        } else if (wifiManager.getConnectionInfo() == null) {
            Log.e("MicroMsg.LBSManager", "WIFILocation wifi info null");
            return "";
        } else {
            List linkedList = new LinkedList();
            List scanResults = wifiManager.getScanResults();
            if (scanResults != null) {
                for (int i = 0; i < scanResults.size(); i++) {
                    linkedList.add(new MacInfo(((ScanResult) scanResults.get(i)).BSSID, ((ScanResult) scanResults.get(i)).level));
                }
            }
            return PhoneUtil.getMacXml(linkedList);
        }
    }

    public boolean isGpsEnable() {
        try {
            return this.H.isProviderEnabled("gps");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isNetworkPrividerEnable() {
        try {
            return this.H.isProviderEnabled("network");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onReceive(Context context, Intent intent) {
        Location location = (Location) intent.getExtras().get("location");
        this.N++;
        if (location != null) {
            boolean equals = "gps".equals(location.getProvider());
            if (((equals && location.getAccuracy() <= 200.0f) || (!equals && location.getAccuracy() <= 1000.0f)) && location.getAccuracy() > 0.0f) {
                int i = equals ? 0 : 1;
                setLocationCache((float) location.getLatitude(), (float) location.getLongitude(), (int) location.getAccuracy(), i);
                if (this.G == null) {
                    return;
                }
                if (!this.K || !this.L || !this.M) {
                    String nullAsNil = Util.nullAsNil(getWIFILocation());
                    String nullAsNil2 = Util.nullAsNil(getTelLocation());
                    if (!this.K) {
                        b();
                        this.K = true;
                        Log.v("MicroMsg.LBSManager", "location by provider ok:[" + location.getLatitude() + " , " + location.getLongitude() + "]  accuracy:" + location.getAccuracy() + "  retry count:" + this.N + " isGpsProvider:" + equals);
                        this.G.onLocationGot((float) location.getLatitude(), (float) location.getLongitude(), (int) location.getAccuracy(), i, nullAsNil, nullAsNil2, true);
                    } else if (!this.L && i == 0) {
                        this.L = true;
                        Log.v("MicroMsg.LBSManager", "report location by GPS ok:[" + location.getLatitude() + " , " + location.getLongitude() + "]  accuracy:" + location.getAccuracy() + "  retry count:" + this.N + " isGpsProvider:" + equals);
                        this.G.onLocationGot((float) location.getLatitude(), (float) location.getLongitude(), (int) location.getAccuracy(), 3, nullAsNil, nullAsNil2, true);
                    } else if (!this.M && i == 1) {
                        this.M = true;
                        Log.v("MicroMsg.LBSManager", "report location by Network ok:[" + location.getLatitude() + " , " + location.getLongitude() + "]  accuracy:" + location.getAccuracy() + "  retry count:" + this.N + " isGpsProvider:" + equals);
                        this.G.onLocationGot((float) location.getLatitude(), (float) location.getLongitude(), (int) location.getAccuracy(), 4, nullAsNil, nullAsNil2, true);
                    }
                }
            }
        }
    }

    public void removeGpsUpdate() {
        Log.v("MicroMsg.LBSManager", "removed gps update");
        if (this.H != null) {
            this.H.removeUpdates(this.I);
        }
        try {
            this.q.unregisterReceiver(this);
        } catch (Exception e) {
            Log.v("MicroMsg.LBSManager", "location receiver has already unregistered");
        }
    }

    public void removeListener() {
        Log.v("MicroMsg.LBSManager", "removed gps update on destroy");
        removeGpsUpdate();
        if (this.O != null) {
            b();
        }
        this.G = null;
        this.q = null;
        this.O = null;
        this.H = null;
    }

    public void requestGpsUpdate() {
        requestGpsUpdate(500);
    }

    public void requestGpsUpdate(int i) {
        if (isGpsEnable() || isNetworkPrividerEnable()) {
            if (i <= 0) {
                i = 500;
            }
            Log.v("MicroMsg.LBSManager", "requested gps update");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(FILTER_GPS);
            this.q.registerReceiver(this, intentFilter);
            if (isGpsEnable()) {
                this.H.requestLocationUpdates("gps", (long) i, 0.0f, this.I);
            }
            if (isNetworkPrividerEnable()) {
                this.H.requestLocationUpdates("network", (long) i, 0.0f, this.I);
            }
        }
    }

    public void resetForContinueGetLocation() {
        this.L = false;
        this.M = false;
    }

    public void start() {
        String nullAsNil = Util.nullAsNil(getWIFILocation());
        String nullAsNil2 = Util.nullAsNil(getTelLocation());
        int i = (isGpsEnable() || isNetworkPrividerEnable()) ? true : 0;
        if (i == 0 || this.J) {
            if (F == null) {
                i = 0;
            } else if (System.currentTimeMillis() - F.time > WaitFor.DEFAULT_MAX_WAIT_MILLIS || F.S <= 0) {
                i = 0;
            } else {
                boolean z = true;
            }
            if (i == 0) {
                this.K = true;
                if (nullAsNil.equals("") && nullAsNil2.equals("")) {
                    Log.v("MicroMsg.LBSManager", "get location by network failed");
                    if (this.G != null) {
                        this.G.onLocationGot(-1000.0f, -1000.0f, INVALID_ACC, 0, "", "", false);
                        return;
                    }
                    return;
                }
                Log.v("MicroMsg.LBSManager", "get location by network ok, macs : " + nullAsNil + " cell ids :" + nullAsNil2);
                if (this.G != null) {
                    this.G.onLocationGot(-1000.0f, -1000.0f, INVALID_ACC, 0, nullAsNil, nullAsNil2, true);
                    return;
                }
                return;
            } else if (this.G != null) {
                this.K = true;
                Log.v("MicroMsg.LBSManager", "location by GPS cache ok:[" + F.Q + " , " + F.R + "]  accuracy:" + F.S + " source:" + F.T);
                this.G.onLocationGot(F.Q, F.R, F.S, F.T, nullAsNil, nullAsNil2, true);
                return;
            } else {
                return;
            }
        }
        this.J = true;
        this.N = 0;
        requestGpsUpdate();
        this.O.startTimer(3000);
    }
}
