package io.netty.channel;

import io.netty.channel.AbstractChannel.AbstractUnsafe;

class AbstractChannel$AbstractUnsafe$4 implements ChannelFutureListener {
    final /* synthetic */ AbstractUnsafe this$1;
    final /* synthetic */ ChannelPromise val$promise;

    AbstractChannel$AbstractUnsafe$4(AbstractUnsafe abstractUnsafe, ChannelPromise channelPromise) {
        this.this$1 = abstractUnsafe;
        this.val$promise = channelPromise;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        this.val$promise.setSuccess();
    }
}
