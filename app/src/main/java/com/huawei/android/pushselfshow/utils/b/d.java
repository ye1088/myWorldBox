package com.huawei.android.pushselfshow.utils.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build.VERSION;
import com.huawei.android.pushagent.c.a.e;
import java.io.IOException;
import java.net.SocketTimeoutException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.tools.ant.util.ProxySetup;

public class d {
    private static String b = "PushSelfShowLog";
    private Context a;

    public d(Context context) {
        this.a = context;
    }

    public String a() {
        String property;
        Exception e;
        try {
            property = VERSION.SDK_INT >= 11 ? System.getProperty(ProxySetup.HTTP_PROXY_HOST) : Proxy.getHost(this.a);
            try {
                e.b(b, "proxyHost=" + property);
            } catch (Exception e2) {
                e = e2;
                e.d(b, "getProxyHost error:" + e.getMessage());
                return property;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            property = null;
            e = exception;
            e.d(b, "getProxyHost error:" + e.getMessage());
            return property;
        }
        return property;
    }

    public HttpResponse a(String str, HttpClient httpClient, HttpGet httpGet) {
        HttpResponse httpResponse = null;
        try {
            HttpParams params = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 30000);
            HttpConnectionParams.setSoTimeout(params, 30000);
            HttpClientParams.setRedirecting(params, true);
            HttpProtocolParams.setUseExpectContinue(params, false);
            a((HttpRequest) httpGet, httpClient, str);
            httpResponse = httpClient.execute(httpGet);
        } catch (SocketTimeoutException e) {
            e.d(b, "SocketTimeoutException occur" + e.getMessage());
        } catch (ClientProtocolException e2) {
            e.d(b, "ClientProtocolException occur" + e2.getMessage());
        } catch (IOException e3) {
            e.d(b, "IOException occur" + e3.getMessage());
        } catch (Exception e4) {
            e.d(b, "Exception occur" + e4.getMessage());
        }
        return httpResponse;
    }

    public void a(HttpRequest httpRequest, HttpClient httpClient, String str) {
        httpRequest.setHeader("Accept-Encoding", "");
        String a = a();
        int b = b();
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.a.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.getType() == 0 && a != null && a.length() > 0 && b != -1) {
            HttpParams params = httpClient.getParams();
            ConnRouteParams.setDefaultProxy(params, new HttpHost(a(), b()));
            httpRequest.setParams(params);
        }
    }

    public int b() {
        int parseInt;
        Exception e;
        try {
            if (VERSION.SDK_INT >= 11) {
                String property = System.getProperty(ProxySetup.HTTP_PROXY_PORT);
                if (property == null) {
                    property = "-1";
                }
                parseInt = Integer.parseInt(property);
            } else {
                parseInt = Proxy.getPort(this.a);
            }
            try {
                e.b(b, "proxyPort=" + parseInt);
            } catch (Exception e2) {
                e = e2;
                e.d(b, "proxyPort error:" + e.getMessage());
                return parseInt;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            parseInt = -1;
            e = exception;
            e.d(b, "proxyPort error:" + e.getMessage());
            return parseInt;
        }
        return parseInt;
    }
}
