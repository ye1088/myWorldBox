package io.netty.util.concurrent;

public interface Promise<V> extends Future<V> {
    Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    Promise<V> await() throws InterruptedException;

    Promise<V> awaitUninterruptibly();

    Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    Promise<V> setFailure(Throwable th);

    Promise<V> setSuccess(V v);

    boolean setUncancellable();

    Promise<V> sync() throws InterruptedException;

    Promise<V> syncUninterruptibly();

    boolean tryFailure(Throwable th);

    boolean trySuccess(V v);
}
