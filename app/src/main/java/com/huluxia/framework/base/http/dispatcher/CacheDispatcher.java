package com.huluxia.framework.base.http.dispatcher;

import android.os.Process;
import com.huluxia.framework.base.http.datasource.cache.Cache;
import com.huluxia.framework.base.http.datasource.cache.Cache.Entry;
import com.huluxia.framework.base.http.deliver.ResponseDelivery;
import com.huluxia.framework.base.http.io.NetworkResponse;
import com.huluxia.framework.base.http.io.Request;
import com.huluxia.framework.base.http.io.Response;
import com.huluxia.framework.base.http.toolbox.HttpLog;
import java.util.concurrent.BlockingQueue;

public class CacheDispatcher<T extends Request<?>> extends Thread {
    protected final Cache mCache;
    protected final BlockingQueue<T> mCacheQueue;
    protected final ResponseDelivery mDelivery;
    protected final BlockingQueue<T> mNetworkQueue;
    protected volatile boolean mQuit = false;

    public CacheDispatcher(BlockingQueue<T> cacheQueue, BlockingQueue<T> networkQueue, Cache cache, ResponseDelivery delivery) {
        this.mCacheQueue = cacheQueue;
        this.mNetworkQueue = networkQueue;
        this.mCache = cache;
        this.mDelivery = delivery;
    }

    public void quit() {
        this.mQuit = true;
        interrupt();
    }

    public void run() {
        HttpLog.v("start new dispatcher", new Object[0]);
        Process.setThreadPriority(10);
        this.mCache.initialize();
        while (true) {
            try {
                final Request request = (Request) this.mCacheQueue.take();
                request.addMarker("cache-queue-take");
                if (request.isCanceled()) {
                    request.finish("cache-discard-canceled");
                } else {
                    Entry entry = this.mCache.get(request.getCacheKey());
                    if (entry == null) {
                        request.addMarker("cache-miss");
                        this.mNetworkQueue.put(request);
                    } else if (entry.isExpired()) {
                        request.addMarker("cache-hit-expired");
                        request.setCacheEntry(entry);
                        this.mNetworkQueue.put(request);
                    } else {
                        request.addMarker("cache-hit");
                        Response<?> response = request.parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
                        request.addMarker("cache-hit-parsed");
                        if (entry.refreshNeeded()) {
                            request.addMarker("cache-hit-refresh-needed");
                            request.setCacheEntry(entry);
                            response.intermediate = true;
                            this.mDelivery.postResponse(request, response, new Runnable() {
                                public void run() {
                                    try {
                                        CacheDispatcher.this.mNetworkQueue.put(request);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            });
                        } else {
                            this.mDelivery.postResponse(request, response);
                        }
                    }
                }
            } catch (InterruptedException e) {
                if (this.mQuit) {
                    return;
                }
            }
        }
    }
}
