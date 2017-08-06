package io.netty.util.concurrent;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class PromiseNotifier<V, F extends Future<V>> implements GenericFutureListener<F> {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PromiseNotifier.class);
    private final boolean logNotifyFailure;
    private final Promise<? super V>[] promises;

    @SafeVarargs
    public PromiseNotifier(Promise<? super V>... promises) {
        this(true, promises);
    }

    @SafeVarargs
    public PromiseNotifier(boolean logNotifyFailure, Promise<? super V>... promises) {
        ObjectUtil.checkNotNull(promises, "promises");
        for (Promise<? super V> promise : promises) {
            if (promise == null) {
                throw new IllegalArgumentException("promises contains null Promise");
            }
        }
        this.promises = (Promise[]) promises.clone();
        this.logNotifyFailure = logNotifyFailure;
    }

    public void operationComplete(F future) throws Exception {
        if (future.isSuccess()) {
            V result = future.get();
            for (Promise<? super V> p : this.promises) {
                if (!p.trySuccess(result) && this.logNotifyFailure) {
                    logger.warn("Failed to mark a_isRightVersion promise as success because it is done already: {}", p);
                }
            }
        } else if (future.isCancelled()) {
            for (Promise<? super V> p2 : this.promises) {
                if (!p2.cancel(false) && this.logNotifyFailure) {
                    logger.warn("Failed to cancel a_isRightVersion promise because it is done already: {}", p2);
                }
            }
        } else {
            Throwable cause = future.cause();
            for (Promise<? super V> p22 : this.promises) {
                if (!p22.tryFailure(cause) && this.logNotifyFailure) {
                    logger.warn("Failed to mark a_isRightVersion promise as failure because it's done already: {}", p22, cause);
                }
            }
        }
    }
}
