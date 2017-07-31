package com.integralblue.httpresponsecache.compat.libcore.net.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public final class HttpsHandler extends URLStreamHandler {
    protected URLConnection openConnection(URL url) throws IOException {
        return new HttpsURLConnectionImpl(url, getDefaultPort());
    }

    protected URLConnection openConnection(URL url, Proxy proxy) throws IOException {
        if (url != null && proxy != null) {
            return new HttpsURLConnectionImpl(url, getDefaultPort(), proxy);
        }
        throw new IllegalArgumentException("url == null || proxy == null");
    }

    protected int getDefaultPort() {
        return 443;
    }
}
