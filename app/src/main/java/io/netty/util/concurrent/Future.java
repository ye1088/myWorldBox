package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;

public interface Future<V> extends java.util.concurrent.Future<V> {
    Future<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    Future<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    Future<V> await() throws InterruptedException;

    boolean await(long j) throws InterruptedException;

    boolean await(long j, TimeUnit timeUnit) throws InterruptedException;

    Future<V> awaitUninterruptibly();

    boolean awaitUninterruptibly(long j);

    boolean awaitUninterruptibly(long j, TimeUnit timeUnit);

    boolean cancel(boolean z);

    Throwable cause();

    V getNow();

    boolean isCancellable();

    boolean isSuccess();

    Future<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    Future<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    Future<V> sync() throws InterruptedException;

    Future<V> syncUninterruptibly();
}
