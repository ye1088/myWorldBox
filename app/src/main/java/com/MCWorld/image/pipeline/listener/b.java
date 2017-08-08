package com.MCWorld.image.pipeline.listener;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.image.pipeline.request.ImageRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* compiled from: ForwardingRequestListener */
public class b implements c {
    private static final String TAG = "ForwardingRequestListener";
    private final List<c> Hj;

    public b(Set<c> requestListeners) {
        this.Hj = new ArrayList(requestListeners.size());
        for (c requestListener : requestListeners) {
            this.Hj.add(requestListener);
        }
    }

    public b(c... requestListeners) {
        this.Hj = Arrays.asList(requestListeners);
    }

    public void a(ImageRequest request, Object callerContext, String requestId, boolean isPrefetch) {
        int numberOfListeners = this.Hj.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.Hj.get(i)).a(request, callerContext, requestId, isPrefetch);
            } catch (Exception exception) {
                e("InternalListener exception in onRequestStart", exception);
            }
        }
    }

    public void n(String requestId, String producerName) {
        int numberOfListeners = this.Hj.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.Hj.get(i)).n(requestId, producerName);
            } catch (Exception exception) {
                e("InternalListener exception in onProducerStart", exception);
            }
        }
    }

    public void c(String requestId, String producerName, @Nullable Map<String, String> extraMap) {
        int numberOfListeners = this.Hj.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.Hj.get(i)).c(requestId, producerName, extraMap);
            } catch (Exception exception) {
                e("InternalListener exception in onProducerFinishWithSuccess", exception);
            }
        }
    }

    public void a(String requestId, String producerName, Throwable t, @Nullable Map<String, String> extraMap) {
        int numberOfListeners = this.Hj.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.Hj.get(i)).a(requestId, producerName, t, extraMap);
            } catch (Exception exception) {
                e("InternalListener exception in onProducerFinishWithFailure", exception);
            }
        }
    }

    public void d(String requestId, String producerName, @Nullable Map<String, String> extraMap) {
        int numberOfListeners = this.Hj.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.Hj.get(i)).d(requestId, producerName, extraMap);
            } catch (Exception exception) {
                e("InternalListener exception in onProducerFinishWithCancellation", exception);
            }
        }
    }

    public void c(String requestId, String producerName, String producerEventName) {
        int numberOfListeners = this.Hj.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.Hj.get(i)).c(requestId, producerName, producerEventName);
            } catch (Exception exception) {
                e("InternalListener exception in onIntermediateChunkStart", exception);
            }
        }
    }

    public void a(ImageRequest request, String requestId, boolean isPrefetch) {
        int numberOfListeners = this.Hj.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.Hj.get(i)).a(request, requestId, isPrefetch);
            } catch (Exception exception) {
                e("InternalListener exception in onRequestSuccess", exception);
            }
        }
    }

    public void a(ImageRequest request, String requestId, Throwable throwable, boolean isPrefetch) {
        int numberOfListeners = this.Hj.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.Hj.get(i)).a(request, requestId, throwable, isPrefetch);
            } catch (Exception exception) {
                e("InternalListener exception in onRequestFailure", exception);
            }
        }
    }

    public void bD(String requestId) {
        int numberOfListeners = this.Hj.size();
        for (int i = 0; i < numberOfListeners; i++) {
            try {
                ((c) this.Hj.get(i)).bD(requestId);
            } catch (Exception exception) {
                e("InternalListener exception in onRequestCancellation", exception);
            }
        }
    }

    public boolean bE(String id) {
        int numberOfListeners = this.Hj.size();
        for (int i = 0; i < numberOfListeners; i++) {
            if (((c) this.Hj.get(i)).bE(id)) {
                return true;
            }
        }
        return false;
    }

    private void e(String message, Throwable t) {
        HLog.error(TAG, message, t, new Object[0]);
    }
}
