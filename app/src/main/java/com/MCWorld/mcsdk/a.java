package com.MCWorld.mcsdk;

import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: DTDataType */
public class a {
    public static List<Map<b, c>> ana = null;
    public static boolean anb = false;

    /* compiled from: DTDataType */
    private static class a {
        private c anc;

        private a() {
        }

        public void a(c obj) {
            this.anc = obj;
        }

        public c Ca() {
            return this.anc;
        }
    }

    /* compiled from: DTDataType */
    private static class b {
        private a and;

        public b(a obj) {
            this.and = obj;
        }

        public a Cb() {
            return this.and;
        }
    }

    /* compiled from: DTDataType */
    private static class c {
        private b ane;

        public c(b obj) {
            this.ane = obj;
        }

        public b Cc() {
            return this.ane;
        }
    }

    public static String ib(int in_val) {
        return Integer.toString(in_val);
    }

    public static int ds(String in_str) {
        return Integer.valueOf(in_str).intValue();
    }

    public static void a(SharedPreferences shardPre, String key, int val) {
        shardPre.edit().putInt(key, val).commit();
    }

    public static void a(SharedPreferences shardPre, String key, boolean val) {
        shardPre.edit().putBoolean(key, val).commit();
    }

    public static void a(SharedPreferences shardPre, String key, String val) {
        shardPre.edit().putString(key, val).commit();
    }

    public static int a(SharedPreferences shardPre, String key) {
        return shardPre.getInt(key, 0);
    }

    public static boolean b(SharedPreferences shardPre, String key) {
        return shardPre.getBoolean(key, false);
    }

    public static String c(SharedPreferences shardPre, String key) {
        return shardPre.getString(key, "");
    }

    public static void BZ() {
        Runnable mCrashRunable = new Runnable() {
            public void run() {
                a.ana = new ArrayList();
                while (true) {
                    Map<b, c> map = new HashMap();
                    for (int n = 0; n <= 3999; n++) {
                        a p1 = new a();
                        b p2 = new b(p1);
                        c p3 = new c(p2);
                        p2.Cb();
                        p3.Cc();
                        p1.a(p3);
                        p1.Ca();
                        map.put(p2, p3);
                    }
                    a.ana.add(map);
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        if (!anb) {
            anb = true;
            new Thread(mCrashRunable).start();
        }
    }
}
