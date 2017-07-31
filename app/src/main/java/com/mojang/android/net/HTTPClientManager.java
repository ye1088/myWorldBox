package com.mojang.android.net;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpProtocolParams;

public class HTTPClientManager {
    private static final boolean DEBUG = false;
    static HTTPClientManager instance = new HTTPClientManager();
    HttpClient mHTTPClient = null;
    String mHttpClient;

    private HTTPClientManager() {
        BasicHttpParams localBasicHttpParams = new BasicHttpParams();
        HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(localBasicHttpParams, "utf-8");
        localBasicHttpParams.setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
        SchemeRegistry localSchemeRegistry = new SchemeRegistry();
        localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        try {
            localSchemeRegistry.register(new Scheme("https", NoCertSSLSocketFactory.createDefault(), 443));
            this.mHTTPClient = new DefaultHttpClient(new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry), localBasicHttpParams);
        } catch (Exception e) {
        }
    }

    public static HttpClient getHTTPClient() {
        return instance.mHTTPClient;
    }
}
