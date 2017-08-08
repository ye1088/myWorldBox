package com.MCWorld.image.pipeline.producers;

import com.MCWorld.image.base.imagepipeline.common.Priority;
import com.MCWorld.image.pipeline.request.ImageRequest;
import com.MCWorld.image.pipeline.request.ImageRequest.RequestLevel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: BaseProducerContext */
public class d implements ao {
    private final ImageRequest Ja;
    private final aq Jb;
    private final RequestLevel Jc;
    @GuardedBy("this")
    private boolean Jd;
    @GuardedBy("this")
    private Priority Je;
    @GuardedBy("this")
    private boolean Jf;
    @GuardedBy("this")
    private boolean Jg = false;
    @GuardedBy("this")
    private final List<ap> Jh = new ArrayList();
    private final String mId;
    private final Object ty;

    public d(ImageRequest imageRequest, String id, aq producerListener, Object callerContext, RequestLevel lowestPermittedRequestLevel, boolean isPrefetch, boolean isIntermediateResultExpected, Priority priority) {
        this.Ja = imageRequest;
        this.mId = id;
        this.Jb = producerListener;
        this.ty = callerContext;
        this.Jc = lowestPermittedRequestLevel;
        this.Jd = isPrefetch;
        this.Je = priority;
        this.Jf = isIntermediateResultExpected;
    }

    public ImageRequest oA() {
        return this.Ja;
    }

    public String getId() {
        return this.mId;
    }

    public aq oB() {
        return this.Jb;
    }

    public Object gk() {
        return this.ty;
    }

    public RequestLevel oC() {
        return this.Jc;
    }

    public synchronized boolean oD() {
        return this.Jd;
    }

    public synchronized Priority oE() {
        return this.Je;
    }

    public synchronized boolean oF() {
        return this.Jf;
    }

    public synchronized boolean isCancelled() {
        return this.Jg;
    }

    public void a(ap callbacks) {
        boolean cancelImmediately = false;
        synchronized (this) {
            this.Jh.add(callbacks);
            if (this.Jg) {
                cancelImmediately = true;
            }
        }
        if (cancelImmediately) {
            callbacks.cj();
        }
    }

    public void cancel() {
        q(oG());
    }

    @Nullable
    public synchronized List<ap> ao(boolean isPrefetch) {
        List<ap> list;
        if (isPrefetch == this.Jd) {
            list = null;
        } else {
            this.Jd = isPrefetch;
            list = new ArrayList(this.Jh);
        }
        return list;
    }

    @Nullable
    public synchronized List<ap> a(Priority priority) {
        List<ap> list;
        if (priority == this.Je) {
            list = null;
        } else {
            this.Je = priority;
            list = new ArrayList(this.Jh);
        }
        return list;
    }

    @Nullable
    public synchronized List<ap> ap(boolean isIntermediateResultExpected) {
        List<ap> list;
        if (isIntermediateResultExpected == this.Jf) {
            list = null;
        } else {
            this.Jf = isIntermediateResultExpected;
            list = new ArrayList(this.Jh);
        }
        return list;
    }

    @Nullable
    public synchronized List<ap> oG() {
        List<ap> list;
        if (this.Jg) {
            list = null;
        } else {
            this.Jg = true;
            list = new ArrayList(this.Jh);
        }
        return list;
    }

    public static void q(@Nullable List<ap> callbacks) {
        if (callbacks != null) {
            for (ap callback : callbacks) {
                callback.cj();
            }
        }
    }

    public static void r(@Nullable List<ap> callbacks) {
        if (callbacks != null) {
            for (ap callback : callbacks) {
                callback.oH();
            }
        }
    }

    public static void s(@Nullable List<ap> callbacks) {
        if (callbacks != null) {
            for (ap callback : callbacks) {
                callback.oI();
            }
        }
    }

    public static void t(@Nullable List<ap> callbacks) {
        if (callbacks != null) {
            for (ap callback : callbacks) {
                callback.oJ();
            }
        }
    }
}
