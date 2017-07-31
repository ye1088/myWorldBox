package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ProgressivePromise;

public interface ChannelProgressivePromise extends ChannelProgressiveFuture, ChannelPromise, ProgressivePromise<Void> {
    ChannelProgressivePromise addListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelProgressivePromise addListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelProgressivePromise await() throws InterruptedException;

    ChannelProgressivePromise awaitUninterruptibly();

    ChannelProgressivePromise removeListener(GenericFutureListener<? extends Future<? super Void>> genericFutureListener);

    ChannelProgressivePromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... genericFutureListenerArr);

    ChannelProgressivePromise setFailure(Throwable th);

    ChannelProgressivePromise setProgress(long j, long j2);

    ChannelProgressivePromise setSuccess();

    ChannelProgressivePromise setSuccess(Void voidR);

    ChannelProgressivePromise sync() throws InterruptedException;

    ChannelProgressivePromise syncUninterruptibly();

    ChannelProgressivePromise unvoid();
}
