package com.MCWorld.framework.base.http.dispatcher;

import android.os.Handler;
import android.os.Looper;
import com.MCWorld.framework.base.http.datasource.cache.Cache;
import com.MCWorld.framework.base.http.deliver.ExecutorDelivery;
import com.MCWorld.framework.base.http.deliver.ResponseDelivery;
import com.MCWorld.framework.base.http.io.Request;
import com.MCWorld.framework.base.http.io.impl.request.DownloadRequest;
import com.MCWorld.framework.base.http.toolbox.HttpLog;
import com.MCWorld.framework.base.http.transport.Network;
import com.MCWorld.framework.base.log.HLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestQueue<T extends Request<?>> {
    protected static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;
    private static final String TAG = "RequestQueue";
    protected final Cache mCache;
    protected CacheDispatcher mCacheDispatcher;
    protected final PriorityBlockingQueue<T> mCacheQueue;
    protected final Set<Request<?>> mCurrentRequests;
    protected final ResponseDelivery mDelivery;
    protected NetworkDispatcher[] mDispatchers;
    protected final Network<Request<?>> mNetwork;
    protected final PriorityBlockingQueue<T> mNetworkQueue;
    protected AtomicInteger mSequenceGenerator;
    protected final Map<String, Queue<T>> mWaitingRequests;

    public interface RequestFilter {
        boolean apply(Request<?> request);
    }

    public RequestQueue(Cache cache, Network network, int threadPoolSize, ResponseDelivery delivery) {
        this.mSequenceGenerator = new AtomicInteger();
        this.mWaitingRequests = new HashMap();
        this.mCurrentRequests = new HashSet();
        this.mCacheQueue = new PriorityBlockingQueue();
        this.mNetworkQueue = new PriorityBlockingQueue();
        this.mCache = cache;
        this.mNetwork = network;
        this.mDispatchers = new NetworkDispatcher[threadPoolSize];
        this.mDelivery = delivery;
    }

    public RequestQueue(Cache cache, Network network, int threadPoolSize) {
        this(cache, network, threadPoolSize, new ExecutorDelivery(new Handler(Looper.getMainLooper())));
    }

    public RequestQueue(Cache cache, Network network) {
        this(cache, network, 4);
    }

    public Network getNetwork() {
        return this.mNetwork;
    }

    public ResponseDelivery getDelivery() {
        return this.mDelivery;
    }

    public PriorityBlockingQueue<T> getNetworkQueue() {
        return this.mNetworkQueue;
    }

    public void start() {
        stop();
        this.mCacheDispatcher = new CacheDispatcher(this.mCacheQueue, this.mNetworkQueue, this.mCache, this.mDelivery);
        this.mCacheDispatcher.start();
        for (int i = 0; i < this.mDispatchers.length; i++) {
            NetworkDispatcher networkDispatcher = new NetworkDispatcher(this.mNetworkQueue, this.mNetwork, this.mCache, this.mDelivery);
            this.mDispatchers[i] = networkDispatcher;
            networkDispatcher.start();
        }
    }

    public void stop() {
        if (this.mCacheDispatcher != null) {
            this.mCacheDispatcher.quit();
        }
        for (int i = 0; i < this.mDispatchers.length; i++) {
            if (this.mDispatchers[i] != null) {
                this.mDispatchers[i].quit();
            }
        }
    }

    public int getSequenceNumber() {
        return this.mSequenceGenerator.incrementAndGet();
    }

    public Cache getCache() {
        return this.mCache;
    }

    public boolean cancelAll(RequestFilter filter) {
        return cancelAll(filter, false);
    }

    public boolean cancelAll(RequestFilter filter, boolean mayInterruptIfRunning) {
        boolean apply = false;
        synchronized (this.mCurrentRequests) {
            List<Request<?>> notRunningReq = new ArrayList();
            for (Request<?> request : this.mCurrentRequests) {
                if (filter.apply(request)) {
                    apply = true;
                    if (request.isRunning()) {
                        request.cancel(mayInterruptIfRunning);
                    } else {
                        notRunningReq.add(request);
                    }
                }
            }
            HLog.debug(TAG, "cancelAll task , currentrequsts size " + this.mCurrentRequests.size() + ", not running task size " + notRunningReq.size(), new Object[0]);
            for (Request<?> request2 : notRunningReq) {
                this.mNetworkQueue.remove(request2);
                this.mCurrentRequests.remove(request2);
                if (request2 instanceof DownloadRequest) {
                    ((DownloadRequest) request2).getCancelListener().onCancel();
                }
            }
        }
        return apply;
    }

    public boolean cancelAll(Object tag) {
        return cancelAll(tag, false);
    }

    public boolean cancelAll(final Object tag, boolean mayInterruptIfRunning) {
        if (tag != null) {
            return cancelAll(new RequestFilter() {
                public boolean apply(Request<?> request) {
                    return request.getTag() == tag;
                }
            }, mayInterruptIfRunning);
        }
        throw new IllegalArgumentException("Cannot cancelAll with a_isRightVersion null tag");
    }

    public Request findRequest(RequestFilter filter) {
        Request<?> request;
        synchronized (this.mCurrentRequests) {
            for (Request<?> request2 : this.mCurrentRequests) {
                if (filter.apply(request2)) {
                    break;
                }
            }
            request2 = null;
        }
        return request2;
    }

    public T add(T request) {
        request.setRequestQueue(this);
        synchronized (this.mCurrentRequests) {
            this.mCurrentRequests.add(request);
        }
        request.setSequence(getSequenceNumber());
        request.addMarker("add-to-queue");
        if (request.shouldCache()) {
            synchronized (this.mWaitingRequests) {
                String cacheKey = request.getCacheKey();
                if (this.mWaitingRequests.containsKey(cacheKey)) {
                    Queue<T> stagedRequests = (Queue) this.mWaitingRequests.get(cacheKey);
                    if (stagedRequests == null) {
                        stagedRequests = new LinkedList();
                    }
                    stagedRequests.add(request);
                    this.mWaitingRequests.put(cacheKey, stagedRequests);
                    HttpLog.v("Request for cacheKey=%s is in flight, putting on hold.", cacheKey);
                } else {
                    this.mWaitingRequests.put(cacheKey, null);
                    this.mCacheQueue.add(request);
                }
            }
        } else {
            this.mNetworkQueue.add(request);
        }
        return request;
    }

    public boolean hasRequestRunning() {
        boolean z;
        synchronized (this.mCurrentRequests) {
            z = this.mCurrentRequests.size() > 0;
        }
        return z;
    }

    public void finish(Request<?> request) {
        HLog.debug(TAG, "finish request %s", new Object[]{request.toString()});
        synchronized (this.mCurrentRequests) {
            this.mCurrentRequests.remove(request);
        }
        if (request.shouldCache()) {
            synchronized (this.mWaitingRequests) {
                Queue<T> waitingRequests = (Queue) this.mWaitingRequests.remove(request.getCacheKey());
                if (waitingRequests != null) {
                    this.mCacheQueue.addAll(waitingRequests);
                }
            }
        }
    }
}
