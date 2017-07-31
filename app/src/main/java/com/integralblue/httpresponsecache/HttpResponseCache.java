package com.integralblue.httpresponsecache;

import com.integralblue.httpresponsecache.compat.URLStreamHandlerFactoryImpl;
import com.integralblue.httpresponsecache.compat.java.net.ExtendedResponseCache;
import com.integralblue.httpresponsecache.compat.java.net.ResponseSource;
import com.integralblue.httpresponsecache.compat.javax.net.ssl.DefaultHostnameVerifier;
import com.integralblue.httpresponsecache.compat.libcore.io.IoUtils;
import com.jakewharton.DiskLruCache;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

public final class HttpResponseCache extends ResponseCache implements ExtendedResponseCache, Closeable {
    private static boolean calledSetURLStreamHandlerFactory = false;
    private final com.integralblue.httpresponsecache.compat.libcore.net.http.HttpResponseCache delegate;

    private HttpResponseCache(File directory, long maxSize) throws IOException {
        this.delegate = new com.integralblue.httpresponsecache.compat.libcore.net.http.HttpResponseCache(directory, maxSize);
    }

    public static HttpResponseCache getInstalled() {
        ResponseCache installed = ResponseCache.getDefault();
        return installed instanceof HttpResponseCache ? (HttpResponseCache) installed : null;
    }

    public static HttpResponseCache install(File directory, long maxSize) throws IOException {
        Closeable installed = getInstalled();
        if (installed != null) {
            DiskLruCache installedCache = installed.delegate.getCache();
            if (installedCache.getDirectory().equals(directory) && installedCache.getMaxSize() == maxSize && !installedCache.isClosed()) {
                return installed;
            }
            IoUtils.closeQuietly(installed);
        }
        HttpResponseCache result = new HttpResponseCache(directory, maxSize);
        ResponseCache.setDefault(result);
        HttpsURLConnection.setDefaultHostnameVerifier(new DefaultHostnameVerifier());
        if (!calledSetURLStreamHandlerFactory) {
            calledSetURLStreamHandlerFactory = true;
            URL.setURLStreamHandlerFactory(new URLStreamHandlerFactoryImpl());
        }
        return result;
    }

    public CacheResponse get(URI uri, String requestMethod, Map<String, List<String>> requestHeaders) throws IOException {
        return this.delegate.get(uri, requestMethod, requestHeaders);
    }

    public CacheRequest put(URI uri, URLConnection urlConnection) throws IOException {
        return this.delegate.put(uri, urlConnection);
    }

    public long size() {
        return this.delegate.getCache().size();
    }

    public long maxSize() {
        return this.delegate.getCache().getMaxSize();
    }

    public void flush() {
        try {
            this.delegate.getCache().flush();
        } catch (IOException e) {
        }
    }

    public int getNetworkCount() {
        return this.delegate.getNetworkCount();
    }

    public int getHitCount() {
        return this.delegate.getHitCount();
    }

    public int getRequestCount() {
        return this.delegate.getRequestCount();
    }

    public void trackResponse(ResponseSource source) {
        this.delegate.trackResponse(source);
    }

    public void trackConditionalCacheHit() {
        this.delegate.trackConditionalCacheHit();
    }

    public void update(CacheResponse conditionalCacheHit, HttpURLConnection connection) {
        this.delegate.update(conditionalCacheHit, connection);
    }

    public void close() throws IOException {
        if (ResponseCache.getDefault() == this) {
            ResponseCache.setDefault(null);
        }
        this.delegate.getCache().close();
    }

    public void delete() throws IOException {
        if (ResponseCache.getDefault() == this) {
            ResponseCache.setDefault(null);
        }
        this.delegate.getCache().delete();
    }
}
