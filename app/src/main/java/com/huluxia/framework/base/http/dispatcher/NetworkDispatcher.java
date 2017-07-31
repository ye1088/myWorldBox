package com.huluxia.framework.base.http.dispatcher;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build.VERSION;
import android.os.Process;
import com.huluxia.framework.base.http.datasource.cache.Cache;
import com.huluxia.framework.base.http.deliver.ResponseDelivery;
import com.huluxia.framework.base.http.io.NetworkResponse;
import com.huluxia.framework.base.http.io.Request;
import com.huluxia.framework.base.http.io.Response;
import com.huluxia.framework.base.http.toolbox.error.CancelError;
import com.huluxia.framework.base.http.toolbox.error.UnknownError;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.http.transport.Network;
import com.huluxia.framework.base.log.HLog;
import java.util.concurrent.BlockingQueue;

public class NetworkDispatcher<T extends Request<?>> extends Thread {
    private final Cache mCache;
    private final ResponseDelivery mDelivery;
    private final Network<Request<?>> mNetwork;
    private final BlockingQueue<T> mQueue;
    private volatile boolean mQuit = false;

    public NetworkDispatcher(BlockingQueue<T> queue, Network<Request<?>> network, Cache cache, ResponseDelivery delivery) {
        this.mQueue = queue;
        this.mNetwork = network;
        this.mCache = cache;
        this.mDelivery = delivery;
    }

    public void quit() {
        this.mQuit = true;
        interrupt();
    }

    @TargetApi(14)
    private void addTrafficStatsTag(Request<?> request) {
        if (VERSION.SDK_INT >= 14) {
            TrafficStats.setThreadStatsTag(request.getTrafficStatsTag());
        }
    }

    public void run() {
        Process.setThreadPriority(10);
        while (true) {
            try {
                Request<?> request = (Request) this.mQueue.take();
                try {
                    request.setRunning(true);
                    request.addMarker("network-queue-take");
                    if (request.isCanceled()) {
                        request.finish("network-discard-cancelled");
                    } else {
                        addTrafficStatsTag(request);
                        NetworkResponse networkResponse = this.mNetwork.performRequest(request);
                        if (request.isCanceled()) {
                            request.finish("network-discard-cancelled-during-job");
                            request.setRunning(false);
                        } else {
                            request.addMarker("network-http-complete");
                            if (networkResponse.notModified && request.hasHadResponseDelivered()) {
                                request.finish("not-modified");
                                request.setRunning(false);
                            } else {
                                Response<?> response = request.parseNetworkResponse(networkResponse);
                                request.addMarker("network-parse-complete");
                                if (request.shouldCache() && response.cacheEntry != null) {
                                    this.mCache.put(request.getCacheKey(), response.cacheEntry);
                                    request.addMarker("network-cache-written");
                                }
                                request.markDelivered();
                                this.mDelivery.postResponse(request, response);
                                request.setRunning(false);
                            }
                        }
                    }
                } catch (CancelError cancelError) {
                    request.addMarker("networkdispather-catch-cancel");
                    this.mDelivery.postCancel(request, cancelError);
                } catch (VolleyError volleyError) {
                    request.addMarker(String.format("network-dispatcher-run-error-%s", new Object[]{volleyError}));
                    HLog.error(this, "Unhandled VolleyError %s", new Object[]{volleyError.toString()});
                    parseAndDeliverNetworkError(request, volleyError);
                } catch (Throwable e) {
                    request.addMarker(String.format("network-dispatcher-run-ex-%s", new Object[]{e}));
                    HLog.error(this, "Unhandled exception %s", new Object[]{e.toString()});
                    this.mDelivery.postError(request, new UnknownError(e));
                } finally {
                    request.setRunning(false);
                }
            } catch (InterruptedException e2) {
                if (this.mQuit) {
                    return;
                }
            }
        }
    }

    private void parseAndDeliverNetworkError(Request<?> request, VolleyError error) {
        this.mDelivery.postError(request, request.parseNetworkError(error));
    }
}
