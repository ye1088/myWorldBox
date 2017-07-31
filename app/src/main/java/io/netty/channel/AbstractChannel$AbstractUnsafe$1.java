package io.netty.channel;

import io.netty.channel.AbstractChannel.AbstractUnsafe;

class AbstractChannel$AbstractUnsafe$1 implements Runnable {
    final /* synthetic */ AbstractUnsafe this$1;
    final /* synthetic */ ChannelPromise val$promise;

    AbstractChannel$AbstractUnsafe$1(AbstractUnsafe abstractUnsafe, ChannelPromise channelPromise) {
        this.this$1 = abstractUnsafe;
        this.val$promise = channelPromise;
    }

    public void run() {
        AbstractUnsafe.access$200(this.this$1, this.val$promise);
    }
}
