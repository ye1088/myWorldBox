package com.baidu.mapapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.HashMap;

public class d {
    static Context a;
    static HashMap<String, SoftReference<u>> b = new HashMap();
    public static boolean c = false;
    public static int d = 4;
    public static boolean e = false;
    public static byte f = (byte) 0;
    public static String g = "10.0.0.200";
    public static int h = 80;

    public interface a {
        void onError(int i, int i2, String str, Object obj);

        void onOk(int i, int i2, String str, Object obj);
    }

    public static HttpURLConnection a(String str) throws IOException {
        if (!c) {
            a();
            if (!c) {
                return null;
            }
        }
        if (!e) {
            return (HttpURLConnection) new URL(str).openConnection();
        }
        String substring;
        String str2;
        int indexOf = str.indexOf(47, 7);
        if (indexOf < 0) {
            substring = str.substring(7);
            str2 = "";
        } else {
            substring = str.substring(7, indexOf);
            str2 = str.substring(indexOf);
        }
        if (f == (byte) 1) {
            return (HttpURLConnection) new URL(str).openConnection(new Proxy(Type.HTTP, new InetSocketAddress(g, 80)));
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://" + g + str2).openConnection();
        httpURLConnection.setRequestProperty("X-Online-Host", substring);
        return httpURLConnection;
    }

    public static void a() {
        ConnectivityManager connectivityManager = null;
        if (a != null) {
            connectivityManager = (ConnectivityManager) a.getSystemService("connectivity");
        }
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                a(activeNetworkInfo, activeNetworkInfo.isConnected());
                return;
            } else {
                c = false;
                return;
            }
        }
        c = false;
    }

    public static void a(final int i, final int i2, final String str, final a aVar) {
        if (str != null && str.startsWith("http://")) {
            new Thread() {
                public void run() {
                    Exception e;
                    Throwable th;
                    InputStream inputStream = null;
                    synchronized (d.b) {
                        u uVar;
                        SoftReference softReference = (SoftReference) d.b.get(str);
                        if (softReference != null) {
                            uVar = (u) softReference.get();
                            if (uVar != null) {
                                aVar.onOk(i, i2, str, uVar);
                                return;
                            }
                        }
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        HttpURLConnection a;
                        try {
                            a = d.a(str);
                            if (a != null) {
                                try {
                                    a.setConnectTimeout(20000);
                                    a.connect();
                                    if (a.getResponseCode() == 200) {
                                        inputStream = a.getInputStream();
                                        byte[] bArr = new byte[2048];
                                        for (int read = inputStream.read(bArr); read != -1; read = inputStream.read(bArr)) {
                                            byteArrayOutputStream.write(bArr, 0, read);
                                        }
                                        if (aVar != null) {
                                            uVar = new u(byteArrayOutputStream.toByteArray());
                                            d.b.put(str, new SoftReference(uVar));
                                            aVar.onOk(i, i2, str, uVar);
                                        }
                                    }
                                } catch (Exception e2) {
                                    e = e2;
                                    try {
                                        if (aVar != null) {
                                            aVar.onError(i, i2, str, "网络连接失败");
                                        }
                                        e.printStackTrace();
                                        if (inputStream != null) {
                                            try {
                                                inputStream.close();
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        if (a != null) {
                                            a.disconnect();
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                        if (inputStream != null) {
                                            try {
                                                inputStream.close();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                            }
                                        }
                                        if (a != null) {
                                            a.disconnect();
                                        }
                                        throw th;
                                    }
                                }
                            } else if (aVar != null) {
                                aVar.onError(i, i2, str, "网络连接失败");
                            }
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e32) {
                                    e32.printStackTrace();
                                }
                            }
                            if (a != null) {
                                a.disconnect();
                            }
                        } catch (Exception e5) {
                            e = e5;
                            Object obj = inputStream;
                            if (aVar != null) {
                                aVar.onError(i, i2, str, "网络连接失败");
                            }
                            e.printStackTrace();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (a != null) {
                                a.disconnect();
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            a = inputStream;
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (a != null) {
                                a.disconnect();
                            }
                            throw th;
                        }
                    }
                }
            }.start();
        }
    }

    static void a(Context context) {
        a = context;
    }

    public static void a(NetworkInfo networkInfo, boolean z) {
        c = z;
        try {
            if (networkInfo.getType() == 1) {
                d = 4;
                e = false;
                return;
            }
            String extraInfo = networkInfo.getExtraInfo();
            if (extraInfo == null) {
                d = 0;
                g = android.net.Proxy.getDefaultHost();
                h = android.net.Proxy.getDefaultPort();
                if (g == null || "".equals(g)) {
                    d = 1;
                    e = false;
                    return;
                }
                d = 2;
                e = true;
                if ("10.0.0.200".equals(g)) {
                    f = (byte) 1;
                    return;
                } else {
                    f = (byte) 0;
                    return;
                }
            }
            extraInfo = extraInfo.toLowerCase().trim();
            if (extraInfo.startsWith("cmwap") || extraInfo.startsWith("uniwap") || extraInfo.startsWith("3gwap")) {
                d = 2;
                e = true;
                f = (byte) 0;
                g = "10.0.0.172";
            } else if (extraInfo.startsWith("ctwap")) {
                d = 2;
                e = true;
                f = (byte) 1;
                g = "10.0.0.200";
            } else if (extraInfo.startsWith("cmnet") || extraInfo.startsWith("uninet") || extraInfo.startsWith("ctnet") || extraInfo.startsWith("3gnet")) {
                d = 1;
                e = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
