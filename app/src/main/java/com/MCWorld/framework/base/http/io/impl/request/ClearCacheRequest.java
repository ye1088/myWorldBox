package com.MCWorld.framework.base.http.io.impl.request;

import android.os.Handler;
import android.os.Looper;
import com.MCWorld.framework.base.http.datasource.cache.Cache;
import com.MCWorld.framework.base.http.io.NetworkResponse;
import com.MCWorld.framework.base.http.io.Request;
import com.MCWorld.framework.base.http.io.Request$Priority;
import com.MCWorld.framework.base.http.io.Response;

public class ClearCacheRequest extends Request<Object> {
    private final Cache mCache;
    private final Runnable mCallback;

    public ClearCacheRequest(Cache cache, Runnable callback) {
        super(0, null, null);
        this.mCache = cache;
        this.mCallback = callback;
    }

    public boolean isCanceled() {
        this.mCache.clear();
        if (this.mCallback != null) {
            new Handler(Looper.getMainLooper()).postAtFrontOfQueue(this.mCallback);
        }
        return true;
    }

    public Request$Priority getPriority() {
        return Request$Priority.IMMEDIATE;
    }

    public Response<Object> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    public void deliverResponse(Object response) {
    }
}
