package com.MCWorld.http.base;

import android.os.Handler;
import com.MCWorld.McApplication;
import com.MCWorld.utils.ak;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

/* compiled from: AsyncHttpClient */
public class b {
    private static final int DEFAULT_MAX_RETRIES = 5;
    private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    private static final int DEFAULT_SOCKET_TIMEOUT = 20000;
    private static final int rR = 20;
    private static HttpClient rS = null;
    private static HttpClient rT = null;

    private static synchronized HttpClient eR() {
        DefaultHttpClient httpClient;
        synchronized (b.class) {
            BasicHttpParams httpParams = new BasicHttpParams();
            ConnManagerParams.setTimeout(httpParams, 20000);
            ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(20));
            ConnManagerParams.setMaxTotalConnections(httpParams, 20);
            HttpConnectionParams.setStaleCheckingEnabled(httpParams, false);
            HttpConnectionParams.setSoTimeout(httpParams, DEFAULT_SOCKET_TIMEOUT);
            HttpConnectionParams.setConnectionTimeout(httpParams, DEFAULT_SOCKET_TIMEOUT);
            HttpConnectionParams.setTcpNoDelay(httpParams, true);
            HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
            httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schemeRegistry), httpParams);
            httpClient.setHttpRequestRetryHandler(new g(5));
        }
        return httpClient;
    }

    public static synchronized HttpClient eS() {
        HttpClient httpClient;
        synchronized (b.class) {
            if (rS == null) {
                rS = eR();
            }
            httpClient = rS;
        }
        return httpClient;
    }

    public static synchronized HttpClient eT() {
        HttpClient httpClient;
        synchronized (b.class) {
            if (rT == null) {
                rT = eR();
            }
            httpClient = rT;
        }
        return httpClient;
    }

    public static byte[] aF(String url) {
        Exception e;
        Throwable th;
        if (!ak.isNetworkConnected(McApplication.getAppContext())) {
            return null;
        }
        HttpGet httpGet = null;
        byte[] data = null;
        try {
            HttpClient httpClient = eT();
            HttpGet httpGet2 = new HttpGet(url);
            try {
                HttpResponse response = httpClient.execute(httpGet2);
                if (response.getStatusLine().getStatusCode() != 200) {
                    if (httpGet2 != null) {
                        httpGet2.abort();
                    }
                    return null;
                }
                HttpEntity entity = response.getEntity();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                InputStream bis = entity.getContent();
                byte[] buffer = new byte[1024];
                while (true) {
                    int len = bis.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    bos.write(buffer, 0, len);
                }
                bis.close();
                if (bos.size() > 0) {
                    data = bos.toByteArray();
                }
                bos.close();
                entity.consumeContent();
                if (httpGet2 != null) {
                    httpGet2.abort();
                    httpGet = httpGet2;
                    return data;
                }
                return data;
            } catch (Exception e2) {
                e = e2;
                httpGet = httpGet2;
                try {
                    e.printStackTrace();
                    if (httpGet != null) {
                        return null;
                    }
                    httpGet.abort();
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (httpGet != null) {
                        httpGet.abort();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                httpGet = httpGet2;
                if (httpGet != null) {
                    httpGet.abort();
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            if (httpGet != null) {
                return null;
            }
            httpGet.abort();
            return null;
        }
    }

    public static byte[] a(String imageUrl, Handler handler) {
        Exception ex;
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        if (!ak.isNetworkConnected(McApplication.getAppContext())) {
            return null;
        }
        HttpGet httpGet = null;
        byte[] data = null;
        try {
            HttpEntity entity;
            ByteArrayOutputStream bos;
            HttpClient httpClient = eT();
            HttpGet httpGet2 = new HttpGet(imageUrl);
            try {
                entity = httpClient.execute(httpGet2).getEntity();
                bos = new ByteArrayOutputStream();
            } catch (Exception e) {
                ex = e;
                httpGet = httpGet2;
                try {
                    ex.printStackTrace();
                    if (httpGet != null) {
                        return data;
                    }
                    httpGet.abort();
                    return data;
                } catch (Throwable th2) {
                    th = th2;
                    if (httpGet != null) {
                        httpGet.abort();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                httpGet = httpGet2;
                if (httpGet != null) {
                    httpGet.abort();
                }
                throw th;
            }
            try {
                long total = entity.getContentLength();
                InputStream bis = entity.getContent();
                byte[] buffer = new byte[1024];
                int read = 0;
                while (true) {
                    int len = bis.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    bos.write(buffer, 0, len);
                    read += len;
                    handler.obtainMessage((int) (((((double) read) * 1.0d) / ((double) total)) * 100.0d)).sendToTarget();
                }
                bis.close();
                if (bos.size() > 0) {
                    data = bos.toByteArray();
                }
                bos.close();
                entity.consumeContent();
                if (httpGet2 != null) {
                    httpGet2.abort();
                    httpGet = httpGet2;
                    return data;
                }
                return data;
            } catch (Exception e2) {
                ex = e2;
                httpGet = httpGet2;
                byteArrayOutputStream = bos;
                ex.printStackTrace();
                if (httpGet != null) {
                    return data;
                }
                httpGet.abort();
                return data;
            } catch (Throwable th4) {
                th = th4;
                httpGet = httpGet2;
                byteArrayOutputStream = bos;
                if (httpGet != null) {
                    httpGet.abort();
                }
                throw th;
            }
        } catch (Exception e3) {
            ex = e3;
            ex.printStackTrace();
            if (httpGet != null) {
                return data;
            }
            httpGet.abort();
            return data;
        }
    }
}
