package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;

public interface ChannelPromise extends ChannelFuture, Promise<Void> {
    ChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelPromise await() throws InterruptedException;

    ChannelPromise awaitUninterruptibly();

    Channel channel();

    ChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelPromise setFailure(Throwable th);

    ChannelPromise setSuccess();

    ChannelPromise setSuccess(Void voidR);

    ChannelPromise sync() throws InterruptedException;

    ChannelPromise syncUninterruptibly();

    boolean trySuccess();

    ChannelPromise unvoid();
}
