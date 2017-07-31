package com.tencent.smtt.sdk.b;

import android.net.NetworkInfo;
import android.net.Proxy;
import android.text.TextUtils;

public class a {
    private static String a = "Wlan";
    private static String b = "";
    private static int c = 4;
    private static int d = 4;
    private static a e = new a();

    private static class a {
        private String a;
        private int b;
        private byte c;
        private boolean d;

        private a() {
            this.a = null;
            this.b = 80;
            this.c = (byte) 0;
            this.d = false;
        }
    }

    static synchronized int a() {
        int a;
        synchronized (a.class) {
            a = a(true);
        }
        return a;
    }

    private static synchronized int a(boolean z) {
        int i;
        synchronized (a.class) {
            if (z) {
                c();
            }
            i = d;
        }
        return i;
    }

    static String a(int i) {
        switch (i) {
            case 4:
                return "Wlan";
            case 8:
                return "cmwap";
            case 16:
                return "3gwap";
            case 32:
                return "uniwap";
            case 64:
                return "ctwap";
            case 128:
                return "ctnet";
            case 256:
                return "uninet";
            case 512:
                return "3gnet";
            case 1024:
                return "cmnet";
            default:
                return "N/A";
        }
    }

    public static boolean b() {
        NetworkInfo d = d();
        return d != null && d.getType() == 1;
    }

    private static boolean b(int i) {
        return i == 2 || i == 0;
    }

    private static void c() {
        NetworkInfo d = d();
        int i = -1;
        try {
            c = 0;
            d = 0;
            String str = null;
            if (d != null) {
                i = d.getType();
                str = d.getExtraInfo();
                if (str == null) {
                    c = 0;
                    d = 0;
                } else {
                    str = str.trim().toLowerCase();
                }
            }
            if (i == 1) {
                c = 4;
                d = 4;
                e.d = false;
                b = "Wlan-unknown";
                return;
            }
            if (str == null) {
                c = 0;
                d = 0;
            } else if (str.contains("cmwap")) {
                c = 2;
                d = 8;
            } else if (str.contains("uniwap")) {
                c = 2;
                d = 32;
            } else if (str.contains("3gwap")) {
                c = 2;
                d = 16;
            } else if (str.contains("ctwap")) {
                c = 2;
                d = 64;
            } else if (str.contains("cmnet")) {
                c = 1;
                d = 1024;
            } else if (str.contains("uninet")) {
                c = 1;
                d = 256;
            } else if (str.contains("3gnet")) {
                c = 1;
                d = 512;
            } else if (str.contains("ctnet")) {
                c = 1;
                d = 128;
            } else if (str.contains("#777")) {
                c = 0;
                d = 0;
            } else {
                c = 0;
                d = 0;
            }
            e.d = false;
            if (b(c)) {
                e.a = Proxy.getDefaultHost();
                e.b = Proxy.getDefaultPort();
                if (e.a != null) {
                    e.a = e.a.trim();
                }
                if (TextUtils.isEmpty(e.a)) {
                    e.d = false;
                    c = 1;
                    if (str != null && str.contains("#777")) {
                        d = 128;
                    }
                } else {
                    e.d = true;
                    c = 2;
                    if ("10.0.0.200".equals(e.a)) {
                        e.c = (byte) 1;
                        d = 64;
                    } else {
                        e.c = (byte) 0;
                    }
                }
            }
            b = a(d) + "-" + (d != null ? d.getSubtypeName() : "unknown");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static NetworkInfo d() {
        try {
            return b.a().getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
