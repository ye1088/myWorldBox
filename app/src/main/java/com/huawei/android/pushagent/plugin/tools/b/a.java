package com.huawei.android.pushagent.plugin.tools.b;

import android.content.Context;
import android.net.Proxy;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.c.a.i;
import com.huawei.android.pushagent.plugin.a.g;
import com.huawei.android.pushagent.plugin.tools.BLocation;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy.Type;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.tools.ant.util.ProxySetup;

public class a {

    private static class a {
        private static String a = "http://";
        private static String b = "pushaix.hicloud.com";
        private static String c = "80";
        private static String d = "/report";
    }

    private static class b {
        private static String a = "https://";
        private static String b = "pushaix.hicloud.com";
        private static String c = "443";
        private static String d = "/getSalt";
    }

    private static String a(Context context, int i, String str) {
        HttpURLConnection a;
        Throwable e;
        InputStream inputStream;
        Throwable th;
        e.a(BLocation.TAG, "httpRequest requestType is " + i);
        InputStream inputStream2 = null;
        BufferedReader bufferedReader = null;
        try {
            a = a(context, i, false, true, str);
            if (a == null) {
                try {
                    a = a(context, i, false, false, str);
                } catch (IOException e2) {
                    e = e2;
                    bufferedReader = null;
                    inputStream = null;
                    try {
                        e.a(BLocation.TAG, "connect url err:" + e.toString(), e);
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable e3) {
                                e.a(BLocation.TAG, "close is err:" + e3.toString(), e3);
                            }
                        }
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        if (a == null) {
                            return null;
                        }
                        a.disconnect();
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable e32) {
                                e.a(BLocation.TAG, "close is err:" + e32.toString(), e32);
                            }
                        }
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e42) {
                                e42.printStackTrace();
                            }
                        }
                        if (a != null) {
                            a.disconnect();
                        }
                        throw th;
                    }
                } catch (Throwable e322) {
                    bufferedReader = null;
                    inputStream = null;
                    th = e322;
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (a != null) {
                        a.disconnect();
                    }
                    throw th;
                }
            }
            if (a == null) {
                a = a(context, i, true, false, str);
            }
            if (a == null) {
                a = a(context, i, true, true, str);
            }
            if (a == null) {
                e.b(BLocation.TAG, "get conn failed");
                if (null != null) {
                    try {
                        inputStream2.close();
                    } catch (Throwable e3222) {
                        e.a(BLocation.TAG, "close is err:" + e3222.toString(), e3222);
                    }
                }
                if (null != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e422) {
                        e422.printStackTrace();
                    }
                }
                if (a == null) {
                    return null;
                }
                a.disconnect();
                return null;
            }
            e.b(BLocation.TAG, "get conn success");
            inputStream = a.getInputStream();
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable th3) {
                                e.a(BLocation.TAG, "close is err:" + th3.toString(), th3);
                            }
                        }
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        if (a != null) {
                            a.disconnect();
                        }
                        return readLine;
                    }
                    e.b(BLocation.TAG, "response is null");
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable e32222) {
                            e.a(BLocation.TAG, "close is err:" + e32222.toString(), e32222);
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e4222) {
                            e4222.printStackTrace();
                        }
                    }
                    if (a == null) {
                        return null;
                    }
                    a.disconnect();
                    return null;
                } catch (IOException e6) {
                    e32222 = e6;
                    e.a(BLocation.TAG, "connect url err:" + e32222.toString(), e32222);
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (a == null) {
                        return null;
                    }
                    a.disconnect();
                    return null;
                }
            } catch (IOException e7) {
                e32222 = e7;
                bufferedReader = null;
                e.a(BLocation.TAG, "connect url err:" + e32222.toString(), e32222);
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (a == null) {
                    return null;
                }
                a.disconnect();
                return null;
            } catch (Throwable e322222) {
                bufferedReader = null;
                th3 = e322222;
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (a != null) {
                    a.disconnect();
                }
                throw th3;
            }
        } catch (IOException e8) {
            e322222 = e8;
            a = null;
            bufferedReader = null;
            inputStream = null;
            e.a(BLocation.TAG, "connect url err:" + e322222.toString(), e322222);
            if (inputStream != null) {
                inputStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (a == null) {
                return null;
            }
            a.disconnect();
            return null;
        } catch (Throwable e3222222) {
            a = null;
            bufferedReader = null;
            inputStream = null;
            th3 = e3222222;
            if (inputStream != null) {
                inputStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (a != null) {
                a.disconnect();
            }
            throw th3;
        }
    }

    public static String a(Context context, g gVar) {
        return a(context, 2, gVar.a(context));
    }

    public static String a(Context context, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("token").append(SimpleComparison.EQUAL_TO_OPERATION).append(str).append("&").append("securityAlg").append(SimpleComparison.EQUAL_TO_OPERATION).append(2);
        return a(context, 1, stringBuffer.toString());
    }

    private static String a(Context context, boolean z) {
        String a = b.b;
        String b = b.c;
        String c = b.d;
        StringBuffer stringBuffer = new StringBuffer();
        return z ? stringBuffer.append(b.a).append(a).append(":").append(b).append(c).toString() : stringBuffer.append(b.a).append(a).append(c).toString();
    }

    private static HttpURLConnection a(Context context, int i, boolean z, boolean z2, String str) {
        HttpURLConnection httpURLConnection;
        Throwable e;
        HttpURLConnection httpURLConnection2;
        byte[] bytes;
        OutputStream outputStream;
        Throwable e2;
        int responseCode;
        Throwable th;
        Object obj = 1;
        if (1 == i) {
            try {
                a(context);
                String a = a(context, z2);
            } catch (MalformedURLException e3) {
                MalformedURLException e4 = e3;
                httpURLConnection = null;
                e.d(BLocation.TAG, "connect to srv cause MalformedURLException:" + e4.toString());
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable e5) {
                        e.c(BLocation.TAG, "close connection cause Exception:" + e5.toString(), e5);
                    }
                }
                return null;
            } catch (IOException e6) {
                e5 = e6;
                httpURLConnection = null;
                e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return null;
            } catch (NullPointerException e7) {
                e5 = e7;
                httpURLConnection = null;
                e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return null;
            } catch (IllegalArgumentException e8) {
                e5 = e8;
                httpURLConnection = null;
                e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return null;
            } catch (UnsupportedOperationException e9) {
                e5 = e9;
                httpURLConnection = null;
                e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return null;
            }
        }
        a = b(context, z2);
        if (z && 1 != com.huawei.android.pushagent.c.a.b.a(context)) {
            String property;
            int parseInt;
            if (VERSION.SDK_INT < 11) {
                obj = null;
            }
            if (obj != null) {
                property = System.getProperty(ProxySetup.HTTP_PROXY_HOST);
                String property2 = System.getProperty(ProxySetup.HTTP_PROXY_PORT);
                if (property2 == null) {
                    property2 = "-1";
                }
                parseInt = Integer.parseInt(property2);
            } else {
                property = Proxy.getHost(context);
                parseInt = Proxy.getPort(context);
            }
            if (property == null || property.length() <= 0 || parseInt == -1) {
                e.b(BLocation.TAG, "proxy param invalid");
            } else {
                e.b(BLocation.TAG, "use Proxy " + property + ":" + parseInt + " to open:" + a);
                httpURLConnection = (HttpURLConnection) new URL(a).openConnection(new java.net.Proxy(Type.HTTP, new InetSocketAddress(property, parseInt)));
                if (httpURLConnection != null) {
                    try {
                        e.a(BLocation.TAG, "direct to open " + a);
                        httpURLConnection2 = (HttpURLConnection) new URL(a).openConnection();
                    } catch (MalformedURLException e10) {
                        e4 = e10;
                        e.d(BLocation.TAG, "connect to srv cause MalformedURLException:" + e4.toString());
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return null;
                    } catch (IOException e11) {
                        e5 = e11;
                        e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return null;
                    } catch (NullPointerException e12) {
                        e5 = e12;
                        e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return null;
                    } catch (IllegalArgumentException e13) {
                        e5 = e13;
                        e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return null;
                    } catch (UnsupportedOperationException e14) {
                        e5 = e14;
                        e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return null;
                    }
                }
                httpURLConnection2 = httpURLConnection;
                if (httpURLConnection2 instanceof HttpsURLConnection) {
                    httpURLConnection2 = (HttpsURLConnection) httpURLConnection2;
                }
                httpURLConnection2.setConnectTimeout(30000);
                httpURLConnection2.setReadTimeout(20000);
                httpURLConnection2.setDoOutput(true);
                httpURLConnection2.setDoInput(true);
                if (!TextUtils.isEmpty(str)) {
                    bytes = str.getBytes("UTF-8");
                    try {
                        outputStream = httpURLConnection2.getOutputStream();
                        try {
                            outputStream.write(bytes);
                            outputStream.flush();
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (Throwable e22) {
                                    e.c(BLocation.TAG, "connect to srv cause IOException:" + e22.toString(), e22);
                                }
                            }
                        } catch (Exception e15) {
                            e22 = e15;
                            try {
                                e.c(BLocation.TAG, "connect to srv cause IOException:" + e22.toString(), e22);
                                if (outputStream != null) {
                                    try {
                                        outputStream.close();
                                    } catch (Throwable e222) {
                                        e.c(BLocation.TAG, "connect to srv cause IOException:" + e222.toString(), e222);
                                    }
                                }
                                httpURLConnection2.connect();
                                responseCode = httpURLConnection2.getResponseCode();
                                e.a(BLocation.TAG, "httpRequest responseCode is " + responseCode + "/" + httpURLConnection2.getResponseMessage());
                                return httpURLConnection2;
                            } catch (Throwable th2) {
                                e222 = th2;
                                if (outputStream != null) {
                                    try {
                                        outputStream.close();
                                    } catch (Throwable e16) {
                                        e.c(BLocation.TAG, "connect to srv cause IOException:" + e16.toString(), e16);
                                    }
                                }
                                throw e222;
                            }
                        }
                    } catch (Exception e17) {
                        e222 = e17;
                        outputStream = null;
                        e.c(BLocation.TAG, "connect to srv cause IOException:" + e222.toString(), e222);
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        httpURLConnection2.connect();
                        responseCode = httpURLConnection2.getResponseCode();
                        e.a(BLocation.TAG, "httpRequest responseCode is " + responseCode + "/" + httpURLConnection2.getResponseMessage());
                        return httpURLConnection2;
                    } catch (Throwable th3) {
                        e222 = th3;
                        outputStream = null;
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        throw e222;
                    }
                }
                httpURLConnection2.connect();
                responseCode = httpURLConnection2.getResponseCode();
                e.a(BLocation.TAG, "httpRequest responseCode is " + responseCode + "/" + httpURLConnection2.getResponseMessage());
                return httpURLConnection2;
            }
        }
        httpURLConnection = null;
        if (httpURLConnection != null) {
            httpURLConnection2 = httpURLConnection;
        } else {
            e.a(BLocation.TAG, "direct to open " + a);
            httpURLConnection2 = (HttpURLConnection) new URL(a).openConnection();
        }
        try {
            if (httpURLConnection2 instanceof HttpsURLConnection) {
                httpURLConnection2 = (HttpsURLConnection) httpURLConnection2;
            }
        } catch (MalformedURLException e18) {
            MalformedURLException malformedURLException = e18;
            httpURLConnection = httpURLConnection2;
            e4 = malformedURLException;
            e.d(BLocation.TAG, "connect to srv cause MalformedURLException:" + e4.toString());
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        } catch (Throwable e2222) {
            th = e2222;
            httpURLConnection = httpURLConnection2;
            e5 = th;
            e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        } catch (Throwable e22222) {
            th = e22222;
            httpURLConnection = httpURLConnection2;
            e5 = th;
            e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        } catch (Throwable e222222) {
            th = e222222;
            httpURLConnection = httpURLConnection2;
            e5 = th;
            e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        } catch (Throwable e2222222) {
            th = e2222222;
            httpURLConnection = httpURLConnection2;
            e5 = th;
            e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        }
        try {
            httpURLConnection2.setConnectTimeout(30000);
            httpURLConnection2.setReadTimeout(20000);
            httpURLConnection2.setDoOutput(true);
            httpURLConnection2.setDoInput(true);
            if (TextUtils.isEmpty(str)) {
                bytes = str.getBytes("UTF-8");
                outputStream = httpURLConnection2.getOutputStream();
                outputStream.write(bytes);
                outputStream.flush();
                if (outputStream != null) {
                    outputStream.close();
                }
            }
            httpURLConnection2.connect();
            responseCode = httpURLConnection2.getResponseCode();
            e.a(BLocation.TAG, "httpRequest responseCode is " + responseCode + "/" + httpURLConnection2.getResponseMessage());
            return httpURLConnection2;
        } catch (MalformedURLException e182) {
            malformedURLException = e182;
            httpURLConnection = httpURLConnection2;
            e4 = malformedURLException;
        } catch (Throwable e22222222) {
            th = e22222222;
            httpURLConnection = httpURLConnection2;
            e5 = th;
            e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        } catch (Throwable e222222222) {
            th = e222222222;
            httpURLConnection = httpURLConnection2;
            e5 = th;
            e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        } catch (Throwable e2222222222) {
            th = e2222222222;
            httpURLConnection = httpURLConnection2;
            e5 = th;
            e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        } catch (Throwable e22222222222) {
            th = e22222222222;
            httpURLConnection = httpURLConnection2;
            e5 = th;
            e.c(BLocation.TAG, "connect to srv cause IOException:" + e5.toString(), e5);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return null;
        }
    }

    private static void a(Context context) {
        try {
            SSLContext instance = SSLContext.getInstance(SSLSocketFactory.TLS);
            instance.init(null, new TrustManager[]{new i(context)}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(new com.huawei.android.pushagent.c.a.g(instance.getSocketFactory()));
            HttpsURLConnection.setDefaultHostnameVerifier(new b());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    private static String b(Context context, boolean z) {
        String a = a.b;
        String b = a.c;
        String c = a.d;
        StringBuffer stringBuffer = new StringBuffer();
        int f = new com.huawei.android.pushagent.plugin.a.e(context).f();
        if (f > 0 && a.contains("pushaix")) {
            int indexOf = a.b.indexOf(".");
            a = a.b.substring(0, indexOf) + f + a.b.substring(indexOf);
        }
        return z ? stringBuffer.append(a.a).append(a).append(":").append(b).append(c).toString() : stringBuffer.append(a.a).append(a).append(c).toString();
    }
}
