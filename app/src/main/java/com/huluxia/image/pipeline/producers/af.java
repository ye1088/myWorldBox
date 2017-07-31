package com.huluxia.image.pipeline.producers;

import com.huluxia.framework.base.utils.VisibleForTesting;
import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: MultiplexProducer */
public abstract class af<K, T extends Closeable> implements am<T> {
    private final am<T> IY;
    @VisibleForTesting
    @GuardedBy("this")
    final Map<K, a> KL = new HashMap();

    protected abstract K b(ao aoVar);

    protected abstract T d(T t);

    protected af(am<T> inputProducer) {
        this.IY = inputProducer;
    }

    public void b(j<T> consumer, ao context) {
        a multiplexer;
        K key = b(context);
        do {
            boolean createdNewMultiplexer = false;
            synchronized (this) {
                multiplexer = N(key);
                if (multiplexer == null) {
                    multiplexer = O(key);
                    createdNewMultiplexer = true;
                }
            }
        } while (!multiplexer.f(consumer, context));
        if (createdNewMultiplexer) {
            a.a(multiplexer);
        }
    }

    private synchronized a N(K key) {
        return (a) this.KL.get(key);
    }

    private synchronized a O(K key) {
        a multiplexer;
        multiplexer = new a(this, key);
        this.KL.put(key, multiplexer);
        return multiplexer;
    }

    private synchronized void a(K key, a multiplexer) {
        if (this.KL.get(key) == multiplexer) {
            this.KL.remove(key);
        }
    }
}
