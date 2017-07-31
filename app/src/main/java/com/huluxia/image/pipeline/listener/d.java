package com.huluxia.image.pipeline.listener;

import android.os.SystemClock;
import android.util.Pair;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.image.pipeline.request.ImageRequest;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: RequestLoggingListener */
public class d implements c {
    private static final String TAG = "RequestLoggingListener";
    @GuardedBy("this")
    private final Map<Pair<String, String>, Long> Hk = new HashMap();
    @GuardedBy("this")
    private final Map<String, Long> Hl = new HashMap();

    public synchronized void a(ImageRequest request, Object callerContextObject, String requestId, boolean isPrefetch) {
        this.Hl.put(requestId, Long.valueOf(getTime()));
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(TAG, String.format("time %d: onRequestSubmit: {requestId: %s, callerContext: %s, isPrefetch: %b}", new Object[]{Long.valueOf(getTime()), requestId, callerContextObject, Boolean.valueOf(isPrefetch)}), new Object[0]);
        }
    }

    public synchronized void n(String requestId, String producerName) {
        this.Hk.put(Pair.create(requestId, producerName), Long.valueOf(getTime()));
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(TAG, String.format("time %d: onProducerStart: {requestId: %s, producer: %s}", new Object[]{Long.valueOf(startTime), requestId, producerName}), new Object[0]);
        }
    }

    public synchronized void c(String requestId, String producerName, @Nullable Map<String, String> extraMap) {
        boolean lowProducer = true;
        synchronized (this) {
            if (a((Long) this.Hk.remove(Pair.create(requestId, producerName)), getTime()) <= 6000) {
                lowProducer = false;
            }
            if (lowProducer || HLog.isImageLoggable(1)) {
                String log = String.format("time %d: onProducerFinishWithSuccess: {requestId: %s, producer: %s, elapsedTime: %d ms, extraMap: %s}", new Object[]{Long.valueOf(currentTime), requestId, producerName, Long.valueOf(elapsedTime), extraMap});
                if (lowProducer) {
                    HLog.warn(TAG, log, new Object[0]);
                } else {
                    HLog.verbose(TAG, log, new Object[0]);
                }
            }
        }
    }

    public synchronized void a(String requestId, String producerName, Throwable throwable, @Nullable Map<String, String> extraMap) {
        Long startTime = (Long) this.Hk.remove(Pair.create(requestId, producerName));
        long currentTime = getTime();
        if (HLog.isImageLoggable(4)) {
            HLog.warn(TAG, String.format("time %d: onProducerFinishWithFailure: {requestId: %s, stage: %s, elapsedTime: %d ms, extraMap: %s, throwable: %s}", new Object[]{Long.valueOf(currentTime), requestId, producerName, Long.valueOf(a(startTime, currentTime)), extraMap, throwable.toString()}), new Object[0]);
        }
    }

    public synchronized void d(String requestId, String producerName, @Nullable Map<String, String> extraMap) {
        Long startTime = (Long) this.Hk.remove(Pair.create(requestId, producerName));
        long currentTime = getTime();
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(TAG, String.format("time %d: onProducerFinishWithCancellation: {requestId: %s, stage: %s, elapsedTime: %d ms, extraMap: %s}", new Object[]{Long.valueOf(currentTime), requestId, producerName, Long.valueOf(a(startTime, currentTime)), extraMap}), new Object[0]);
        }
    }

    public synchronized void c(String requestId, String producerName, String producerEventName) {
        Long startTime = (Long) this.Hk.get(Pair.create(requestId, producerName));
        long currentTime = getTime();
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(TAG, String.format("time %d: onProducerEvent: {requestId: %s, stage: %s, eventName: %s; elapsedTime: %d ms}", new Object[]{Long.valueOf(getTime()), requestId, producerName, producerEventName, Long.valueOf(a(startTime, currentTime))}), new Object[0]);
        }
    }

    public synchronized void a(ImageRequest request, String requestId, boolean isPrefetch) {
        Long startTime = (Long) this.Hl.remove(requestId);
        long currentTime = getTime();
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(TAG, String.format("time %d: onRequestSuccess: {requestId: %s, elapsedTime: %d ms}", new Object[]{Long.valueOf(currentTime), requestId, Long.valueOf(a(startTime, currentTime))}), new Object[0]);
        }
    }

    public synchronized void a(ImageRequest request, String requestId, Throwable throwable, boolean isPrefetch) {
        Long startTime = (Long) this.Hl.remove(requestId);
        long currentTime = getTime();
        if (HLog.isImageLoggable(4)) {
            HLog.warn(TAG, String.format("time %d: onRequestFailure: {requestId: %s, elapsedTime: %d ms, throwable: %s}", new Object[]{Long.valueOf(currentTime), requestId, Long.valueOf(a(startTime, currentTime)), throwable.toString()}), new Object[0]);
        }
    }

    public synchronized void bD(String requestId) {
        Long startTime = (Long) this.Hl.remove(requestId);
        long currentTime = getTime();
        if (HLog.isImageLoggable(1)) {
            HLog.verbose(TAG, String.format("time %d: onRequestCancellation: {requestId: %s, elapsedTime: %d ms}", new Object[]{Long.valueOf(currentTime), requestId, Long.valueOf(a(startTime, currentTime))}), new Object[0]);
        }
    }

    public boolean bE(String id) {
        return HLog.isImageLoggable(4);
    }

    private static long a(@Nullable Long startTime, long endTime) {
        if (startTime != null) {
            return endTime - startTime.longValue();
        }
        return -1;
    }

    private static long getTime() {
        return SystemClock.uptimeMillis();
    }
}
