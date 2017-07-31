package com.integralblue.httpresponsecache.compat.java.net;

import java.net.CacheResponse;
import java.net.HttpURLConnection;

public interface ExtendedResponseCache {
    void trackConditionalCacheHit();

    void trackResponse(ResponseSource responseSource);

    void update(CacheResponse cacheResponse, HttpURLConnection httpURLConnection);
}
