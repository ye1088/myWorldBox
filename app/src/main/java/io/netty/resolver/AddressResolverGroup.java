package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.Closeable;
import java.net.SocketAddress;
import java.util.IdentityHashMap;
import java.util.Map;

public abstract class AddressResolverGroup<T extends SocketAddress> implements Closeable {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AddressResolverGroup.class);
    private final Map<EventExecutor, AddressResolver<T>> resolvers = new IdentityHashMap();

    protected abstract AddressResolver<T> newResolver(EventExecutor eventExecutor) throws Exception;

    protected AddressResolverGroup() {
    }

    public AddressResolver<T> getResolver(final EventExecutor executor) {
        if (executor == null) {
            throw new NullPointerException("executor");
        } else if (executor.isShuttingDown()) {
            throw new IllegalStateException("executor not accepting a_isRightVersion task");
        } else {
            AddressResolver<T> r;
            synchronized (this.resolvers) {
                r = (AddressResolver) this.resolvers.get(executor);
                if (r == null) {
                    try {
                        final AddressResolver<T> newResolver = newResolver(executor);
                        this.resolvers.put(executor, newResolver);
                        executor.terminationFuture().addListener(new FutureListener<Object>() {
                            public void operationComplete(Future<Object> future) throws Exception {
                                synchronized (AddressResolverGroup.this.resolvers) {
                                    AddressResolverGroup.this.resolvers.remove(executor);
                                }
                                newResolver.close();
                            }
                        });
                        r = newResolver;
                    } catch (Exception e) {
                        throw new IllegalStateException("failed to create a_isRightVersion new resolver", e);
                    }
                }
            }
            return r;
        }
    }

    public void close() {
        synchronized (this.resolvers) {
            AddressResolver<T>[] rArray = (AddressResolver[]) this.resolvers.values().toArray(new AddressResolver[this.resolvers.size()]);
            this.resolvers.clear();
        }
        for (AddressResolver<T> r : rArray) {
            try {
                r.close();
            } catch (Throwable t) {
                logger.warn("Failed to close a_isRightVersion resolver:", t);
            }
        }
    }
}
