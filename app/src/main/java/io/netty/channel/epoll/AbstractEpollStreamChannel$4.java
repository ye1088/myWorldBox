package io.netty.channel.epoll;

import io.netty.channel.ChannelPromise;

class AbstractEpollStreamChannel$4 implements Runnable {
    final /* synthetic */ AbstractEpollStreamChannel this$0;
    final /* synthetic */ ChannelPromise val$promise;

    AbstractEpollStreamChannel$4(AbstractEpollStreamChannel abstractEpollStreamChannel, ChannelPromise channelPromise) {
        this.this$0 = abstractEpollStreamChannel;
        this.val$promise = channelPromise;
    }

    public void run() {
        AbstractEpollStreamChannel.access$200(this.this$0, this.val$promise);
    }
}
