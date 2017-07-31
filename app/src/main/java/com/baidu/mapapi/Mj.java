package com.baidu.mapapi;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.mapapi.cloud.GeoSearchListener;
import com.baidu.mapapi.cloud.c;
import java.io.File;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpStatus;
import org.apache.tools.zip.UnixStat;

public class Mj {
    private static b V = null;
    private static Method W = null;
    private static Method X = null;
    private static Class<?> Y = null;
    static f a = null;
    static final MKLocationManager b = new MKLocationManager();
    static int c = 2;
    static MapView d = null;
    static Context e = null;
    static ServerSocket f = null;
    static int g = 0;
    static int h = 0;
    static int i = 0;
    static float j = 1.0f;
    static String k = "";
    public static int l = 0;
    public static int m = 0;
    public static int n = 0;
    public static int o = 0;
    public static int p = 0;
    static boolean q;
    static boolean r;
    static int s = -1;
    private static Handler t = null;
    private static String u = "";
    private static String v = "";
    private static String w = "";
    private static String x = "";
    private j A = null;
    private j B = null;
    private j C = null;
    private j D = null;
    private c E = null;
    private String F;
    private String G;
    private String H;
    private final long I = 3;
    private final long J = 3;
    private List<NeighboringCellInfo> K = null;
    private long L = 0;
    private long M = 0;
    private int N = 0;
    private int O = 0;
    private int P = 0;
    private int Q = 0;
    private int R = 0;
    private int S = 0;
    private List<ScanResult> T = null;
    private List<ScanResult> U = null;
    private Handler Z = new Handler();
    private ArrayList<b> aa = new ArrayList();
    private TelephonyManager y = null;
    private WifiManager z = null;

    class a {
        int a = 0;
        int b;
        int c;
        int d;
        final /* synthetic */ Mj e;

        a(Mj mj) {
            this.e = mj;
        }
    }

    private class b implements Runnable {
        public int a;
        public int b;
        final /* synthetic */ Mj c;

        private b(Mj mj) {
            this.c = mj;
        }

        public void run() {
            Mj.MsgMapProc(1, 8, this.a, 0);
            this.c.Z.postDelayed(this, (long) this.b);
        }
    }

    static {
        try {
            System.loadLibrary("BMapApiEngine_v1_3_5");
        } catch (UnsatisfiedLinkError e) {
            Log.d("BMapApiEngine_v1_3_5", "BMapApiEngine_v1_3_5 library not found!");
            Log.d("BMapApiEngine_v1_3_5", e.getLocalizedMessage());
        }
    }

    Mj(BMapManager bMapManager, Context context) {
        e = context;
        e();
    }

    public static native String AppendRecord(String str, String str2);

    public static native int DisableProviderCC(int i);

    public static native int EnableProviderCC(int i);

    public static native Bundle GetGPSStatus();

    public static native Bundle GetMapStatus();

    public static native Bundle GetNotifyInternal();

    public static native int InitLocationCC();

    public static native int InitMapControlCC(int i, int i2);

    public static native int MapProc(int i, int i2, int i3);

    public static native int MsgMapProc(int i, int i2, int i3, int i4);

    public static native void SetCellData(int i, int i2, int i3, int i4, String str, String str2, String str3);

    public static native void SetCellInfo(int i, int i2, int i3, int i4, String str);

    public static native void SetLocationCoordinateType(int i);

    public static native int SetNotifyInternal(int i, int i2);

    public static native int SetProxyInfo(String str, int i);

    public static native void SetUpdateWifi(String str);

    public static native void UpdataGPS(double d, double d2, float f, float f2, float f3, int i);

    private static String a(NetworkInfo networkInfo) {
        return networkInfo != null ? networkInfo.getExtraInfo() : null;
    }

    private void a(int i) {
        for (int i2 = 0; i2 < this.aa.size(); i2++) {
            b bVar = (b) this.aa.get(i2);
            if (bVar.a == i) {
                if (BMapManager.c) {
                    this.Z.removeCallbacks(bVar);
                }
                this.aa.remove(i2);
                return;
            }
        }
    }

    private void a(int i, int i2) {
        Iterator it = this.aa.iterator();
        while (it.hasNext()) {
            if (((b) it.next()).a == i) {
                return;
            }
        }
        Runnable bVar = new b();
        bVar.a = i;
        bVar.b = i2;
        if (BMapManager.c) {
            this.Z.postDelayed(bVar, 500);
        }
        this.aa.add(bVar);
    }

    public static void changeGprsConnect() {
        int i = 80;
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) e.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            String extraInfo;
            String defaultHost;
            int defaultPort;
            if (!activeNetworkInfo.getTypeName().toLowerCase().equals("wifi")) {
                extraInfo = activeNetworkInfo.getExtraInfo();
                if (extraInfo == null) {
                    return;
                }
                if (extraInfo.toLowerCase().contains("wap")) {
                    defaultHost = Proxy.getDefaultHost();
                    defaultPort = Proxy.getDefaultPort();
                    if (defaultHost == null) {
                        defaultHost = "10.0.0.172";
                    }
                    if (defaultPort != -1) {
                        i = defaultPort;
                    }
                    SetProxyInfo(defaultHost, i);
                    return;
                }
                SetProxyInfo(null, 0);
            } else if (1 == s) {
                SetProxyInfo(null, 0);
            } else if (s == 0) {
                extraInfo = a(activeNetworkInfo);
                if (extraInfo == null) {
                    return;
                }
                if (extraInfo.toLowerCase().contains("wap")) {
                    defaultHost = Proxy.getDefaultHost();
                    defaultPort = Proxy.getDefaultPort();
                    if (defaultHost == null) {
                        defaultHost = "10.0.0.172";
                    }
                    if (defaultPort == -1) {
                        defaultPort = 80;
                    }
                    SetProxyInfo(defaultHost, defaultPort);
                    return;
                }
                SetProxyInfo(null, 0);
            }
        }
    }

    private int d() {
        try {
            Y = Class.forName("android.telephony.cdma.CdmaCellLocation");
            W = Y.getMethod("getBaseStationId", new Class[0]);
            X = Y.getMethod("getNetworkId", new Class[0]);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void e() {
        if (e != null) {
            File filesDir = e.getFilesDir();
            if (filesDir != null) {
                x = filesDir.getAbsolutePath();
            }
            if (this.y == null) {
                this.y = (TelephonyManager) e.getSystemService(com.huluxia.data.profile.a.qe);
            }
            if (this.y != null) {
                k = this.y.getDeviceId();
                u = this.y.getSubscriberId();
                v = Build.MODEL;
                w = VERSION.SDK;
            }
            WindowManager windowManager = (WindowManager) e.getSystemService("window");
            DisplayMetrics displayMetrics = new DisplayMetrics();
            if (windowManager != null) {
                Display defaultDisplay = windowManager.getDefaultDisplay();
                g = defaultDisplay.getWidth();
                h = defaultDisplay.getHeight();
                defaultDisplay.getMetrics(displayMetrics);
            }
            j = displayMetrics.density;
            if (Integer.parseInt(VERSION.SDK) > 3) {
                try {
                    i = Class.forName("android.util.DisplayMetrics").getField("densityDpi").getInt(displayMetrics);
                } catch (Exception e) {
                    e.printStackTrace();
                    i = 160;
                }
            } else {
                i = 160;
            }
            try {
                this.G = e.getPackageManager().getPackageInfo(e.getPackageName(), 0).applicationInfo.loadLabel(e.getPackageManager()).toString();
                this.H = e.getPackageName();
            } catch (Exception e2) {
                Log.d("baidumap", e2.getMessage());
                this.G = null;
            }
        }
    }

    private static void f() {
        String g = g();
        if (g == null) {
            SetProxyInfo(null, 0);
        } else if (g.toLowerCase().contains("wap")) {
            String defaultHost = Proxy.getDefaultHost();
            int defaultPort = Proxy.getDefaultPort();
            if (defaultHost == null) {
                defaultHost = "10.0.0.172";
            }
            if (defaultPort == -1) {
                defaultPort = 80;
            }
            SetProxyInfo(defaultHost, defaultPort);
        } else {
            SetProxyInfo(null, 0);
        }
    }

    private static String g() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) e.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            if (!activeNetworkInfo.getTypeName().toLowerCase().equals("wifi")) {
                return activeNetworkInfo.getExtraInfo();
            }
            if (1 == s) {
                return null;
            }
            if (s == 0) {
                return a(activeNetworkInfo);
            }
        }
        return null;
    }

    public static native Bundle getNewBundle(int i, int i2, int i3);

    public static native int initOfflineCC();

    public static native int initSearchCC();

    public static native void nativeDone();

    public static native void nativeInit();

    public static native void nativeRender();

    public static native void nativeResize(int i, int i2);

    public static native void renderBaiduMap(Bitmap bitmap);

    public static native void renderCalDisScreenPos(Bundle bundle);

    public static native void renderFlsScreenPos(Bundle bundle);

    public static native void renderUpdateScreen(short[] sArr, int i, int i2);

    public static native int sendBundle(Bundle bundle);

    public static native void sendPhoneInfo(Bundle bundle);

    public native int InitMapApiEngine();

    public void JNI_MapcallBackProc(int i, int i2, int i3, int i4) {
        a aVar = new a(this);
        aVar.a = i;
        aVar.b = i2;
        aVar.c = i3;
        aVar.d = i4;
        Message obtainMessage = t.obtainMessage(1, 1, 1, aVar);
        if (obtainMessage != null) {
            t.sendMessage(obtainMessage);
        }
    }

    public void JNI_callBackProc(int i, int i2, int i3) {
        switch (i) {
            case 9:
            case HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED /*505*/:
                if (d != null) {
                    d.a(i, i2, i3);
                    return;
                }
                return;
            case 506:
                c();
                return;
            case UnixStat.DEFAULT_LINK_PERM /*511*/:
                a(i2, i3);
                return;
            case 512:
                a(i2);
                return;
            case 5000:
                if (a != null) {
                    a.a(i, i2, (long) i3);
                    return;
                }
                return;
            case 10001:
            case PushConstants.ERROR_SERVICE_NOT_AVAILABLE /*10002*/:
            case PushConstants.ERROR_SERVICE_NOT_AVAILABLE_TEMP /*10003*/:
            case 10004:
            case 10006:
            case 10010:
            case 10011:
            case 10012:
            case 10015:
            case 10016:
            case 10017:
            case 10018:
                if (this.B != null) {
                    this.B.a(new MKEvent(i - 10000, i2, i3));
                    return;
                }
                return;
            case 10005:
                if (b != null) {
                    b.c();
                    return;
                }
                return;
            case 10007:
            case 10009:
                if (this.A != null) {
                    this.A.a(new MKEvent(i - 10000, i2, i3));
                    return;
                }
                return;
            case 10013:
                if (this.C != null) {
                    this.C.a(new MKEvent(i2, i2, i3));
                    return;
                }
                return;
            case 10014:
                if (d != null) {
                    d.a(8020, i2, i3);
                }
                if (this.D != null) {
                    if (Math.abs(l - n) > 2 || Math.abs(m - o) > 2 || 1 == p) {
                        this.D.a(new MKEvent(i - 10000, i2, i3));
                    }
                    l = 0;
                    m = 0;
                    o = 0;
                    n = 0;
                    p = 0;
                    return;
                }
                return;
            case 10504:
            case 10505:
                this.E.a(new com.baidu.mapapi.cloud.a(i - 10000, i2, i3));
                return;
            default:
                return;
        }
    }

    public Bundle J_GetDevInfo(int i) {
        Bundle bundle = new Bundle();
        switch (i) {
            case 1:
                bundle.putString("im", k);
                break;
            case 2:
                bundle.putString("is", u);
                break;
            case 3:
                bundle.putString("mb", v);
                bundle.putString("os", "Android_" + w);
                bundle.putInt("cx", g);
                bundle.putInt("cy", h);
                bundle.putInt("xd", i);
                bundle.putInt("yd", i);
                break;
            case 4:
                bundle.putString("na", x);
                break;
        }
        return bundle;
    }

    public native int SetCacheDirectoryCC(String str);

    public native int StartApiEngineCC(String str, String str2, String str3);

    public native int StopApiEngineCC();

    public native int UnInitMapApiEngine();

    void a(MKMapViewListener mKMapViewListener) {
        this.D = new i(mKMapViewListener);
    }

    void a(MKOfflineMapListener mKOfflineMapListener) {
        this.C = new k(mKOfflineMapListener);
    }

    void a(MKSearchListener mKSearchListener) {
        this.B = new m(mKSearchListener);
    }

    boolean a() {
        f();
        V = new b();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        e.registerReceiver(V, intentFilter, null, null);
        if (b != null) {
            this.M = 0;
            this.U = null;
            this.S = 0;
            this.R = 0;
            b.a();
            UpdataGPS(0.0d, 0.0d, 0.0f, 0.0f, 0.0f, 0);
            SetCellData(0, 0, 0, 0, null, null, null);
            if (q) {
                b.enableProvider(0);
            }
            if (r) {
                b.enableProvider(1);
            }
        }
        Iterator it = this.aa.iterator();
        while (it.hasNext()) {
            this.Z.postDelayed((b) it.next(), 500);
        }
        if (StartApiEngineCC(this.F, this.G, this.H) != 0) {
            return true;
        }
        try {
            if (V != null) {
                e.unregisterReceiver(V);
                V = null;
            }
        } catch (Exception e) {
            Log.d("baidumap", e.getMessage());
            V = null;
        }
        it = this.aa.iterator();
        while (it.hasNext()) {
            this.Z.removeCallbacks((b) it.next());
        }
        if (b == null) {
            return false;
        }
        b.b();
        return false;
    }

    boolean a(String str, MKGeneralListener mKGeneralListener) {
        if (mKGeneralListener != null) {
            this.A = new g(mKGeneralListener);
        }
        this.F = str;
        if (a == null) {
            a = new f(e);
        }
        if (e != null) {
            if (this.y == null) {
                this.y = (TelephonyManager) e.getSystemService(com.huluxia.data.profile.a.qe);
            }
            if (this.z == null) {
                this.z = (WifiManager) e.getSystemService("wifi");
            }
            if (this.z != null && this.z.isWifiEnabled()) {
                this.z.startScan();
            }
        }
        t = new Handler(this) {
            final /* synthetic */ Mj a;

            {
                this.a = r1;
            }

            public void handleMessage(Message message) {
                a aVar = (a) message.obj;
                Mj.MsgMapProc(aVar.a, aVar.b, aVar.c, aVar.d);
                super.handleMessage(message);
            }
        };
        try {
            if (initClass(new Bundle(), 0) == 0) {
                return false;
            }
            if (InitMapApiEngine() == 0) {
                return false;
            }
            this.aa.clear();
            return true;
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    boolean b() {
        try {
            if (V != null) {
                e.unregisterReceiver(V);
                V = null;
            }
        } catch (Exception e) {
            Log.d("baidumap", e.getMessage());
            V = null;
        }
        Iterator it = this.aa.iterator();
        while (it.hasNext()) {
            this.Z.removeCallbacks((b) it.next());
        }
        if (b != null) {
            b.b();
        }
        return StopApiEngineCC() != 0;
    }

    boolean c() {
        if (a == null) {
            return false;
        }
        String networkOperator;
        String str;
        String str2;
        if (this.y == null) {
            this.y = (TelephonyManager) e.getSystemService(com.huluxia.data.profile.a.qe);
        }
        if (this.y != null) {
            long currentTimeMillis = System.currentTimeMillis() / 1000;
            if (currentTimeMillis - this.M > 3) {
                CellLocation cellLocation = this.y.getCellLocation();
                this.M = currentTimeMillis;
                if (cellLocation == null || !(cellLocation instanceof GsmCellLocation)) {
                    try {
                        if (Integer.parseInt(VERSION.SDK) >= 5 && (!(Y == null && -1 == d()) && Y.isInstance(cellLocation))) {
                            Object invoke = W.invoke(cellLocation, new Object[0]);
                            if (invoke instanceof Integer) {
                                this.Q = ((Integer) invoke).intValue();
                                if (this.Q < 0) {
                                    this.Q = 0;
                                }
                                invoke = X.invoke(cellLocation, new Object[0]);
                                if (invoke instanceof Integer) {
                                    this.P = ((Integer) invoke).intValue();
                                    if (this.P < 0) {
                                        this.P = 0;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                    this.Q = gsmCellLocation.getCid();
                    if (this.Q < 0) {
                        this.Q = 0;
                    }
                    this.P = gsmCellLocation.getLac();
                    if (this.P < 0) {
                        this.P = 0;
                    }
                }
                networkOperator = this.y.getNetworkOperator();
                if (!(networkOperator == null || networkOperator.length() <= 0 || "null".equals(networkOperator))) {
                    try {
                        if (networkOperator.length() >= 3) {
                            this.N = Integer.valueOf(networkOperator.substring(0, 3)).intValue();
                        }
                        if (networkOperator.length() >= 5) {
                            this.O = Integer.valueOf(networkOperator.substring(3, 5)).intValue();
                        }
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                try {
                    if (Y != null && Y.isInstance(cellLocation)) {
                        this.O = ((Integer) Y.getMethod("getSystemId", new Class[0]).invoke(cellLocation, new Object[0])).intValue();
                    }
                } catch (Exception e32) {
                    e32.printStackTrace();
                }
            }
        } else {
            this.Q = 0;
            this.P = 0;
            this.N = 0;
            this.O = 0;
        }
        if (this.z == null) {
            this.z = (WifiManager) e.getSystemService("wifi");
        }
        if (this.z == null || !this.z.isWifiEnabled()) {
            this.T = null;
            SetUpdateWifi("");
        } else {
            long currentTimeMillis2 = System.currentTimeMillis() / 1000;
            if (currentTimeMillis2 - this.L > 3) {
                this.z.startScan();
                this.T = null;
                this.L = currentTimeMillis2;
            }
            this.T = this.z.getScanResults();
            if (this.T == null || this.T.size() <= 0) {
                SetUpdateWifi("");
            }
        }
        String str3 = "";
        String str4 = "";
        String str5 = "";
        networkOperator = "";
        String str6 = "";
        if (this.Q > 0 && this.P >= 0 && this.N >= 0) {
            str6 = a.a(this.N, this.O, this.P, this.Q, this.K, str6);
            if (str6.length() > 0) {
                str = str6;
                if (this.T != null || this.T.size() <= 0) {
                    str6 = str5;
                    str2 = str4;
                } else {
                    a.a(this.T);
                    String a = a.a(this.T, str6);
                    if (a.length() > 0) {
                        SetUpdateWifi(a);
                        networkOperator = a;
                    } else {
                        networkOperator = str4;
                    }
                    str6 = a.b(this.T, a);
                    if (str6.length() > 0) {
                        str2 = networkOperator;
                    } else {
                        str6 = str5;
                        str2 = networkOperator;
                    }
                }
                if (!(this.S == this.Q && this.R == this.P && a.a(this.T, this.U))) {
                    this.U = this.T;
                    this.S = this.Q;
                    this.R = this.P;
                    SetCellData(this.Q, this.P, this.N, this.O, str, str2, str6);
                }
                return true;
            }
        }
        str = str3;
        if (this.T != null) {
        }
        str6 = str5;
        str2 = str4;
        this.U = this.T;
        this.S = this.Q;
        this.R = this.P;
        SetCellData(this.Q, this.P, this.N, this.O, str, str2, str6);
        return true;
    }

    public native int initClass(Object obj, int i);

    public void initGeoListener(GeoSearchListener geoSearchListener) {
        this.E = new com.baidu.mapapi.cloud.b(geoSearchListener);
    }
}
