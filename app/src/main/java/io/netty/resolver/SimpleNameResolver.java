package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

public abstract class SimpleNameResolver<T> implements NameResolver<T> {
    private final EventExecutor executor;

    protected abstract void doResolve(String str, Promise<T> promise) throws Exception;

    protected abstract void doResolveAll(String str, Promise<List<T>> promise) throws Exception;

    protected SimpleNameResolver(EventExecutor executor) {
        this.executor = (EventExecutor) ObjectUtil.checkNotNull(executor, "executor");
    }

    protected EventExecutor executor() {
        return this.executor;
    }

    public final Future<T> resolve(String inetHost) {
        return resolve(inetHost, executor().newPromise());
    }

    public Future<T> resolve(String inetHost, Promise<T> promise) {
        ObjectUtil.checkNotNull(inetHost, "inetHost");
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            doResolve(inetHost, promise);
            return promise;
        } catch (Exception e) {
            return promise.setFailure(e);
        }
    }

    public final Future<List<T>> resolveAll(String inetHost) {
        return resolveAll(inetHost, executor().newPromise());
    }

    public Future<List<T>> resolveAll(String inetHost, Promise<List<T>> promise) {
        ObjectUtil.checkNotNull(inetHost, "inetHost");
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            doResolveAll(inetHost, promise);
            return promise;
        } catch (Exception e) {
            return promise.setFailure(e);
        }
    }

    public void close() {
    }
}
