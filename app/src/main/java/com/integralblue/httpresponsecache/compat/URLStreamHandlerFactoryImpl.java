package com.integralblue.httpresponsecache.compat;

import com.integralblue.httpresponsecache.compat.libcore.net.http.HttpHandler;
import com.integralblue.httpresponsecache.compat.libcore.net.http.HttpsHandler;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class URLStreamHandlerFactoryImpl implements URLStreamHandlerFactory {
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (protocol.equals("http")) {
            return new HttpHandler();
        }
        if (protocol.equals("https")) {
            return new HttpsHandler();
        }
        return null;
    }
}
