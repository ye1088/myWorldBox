package io.netty.channel.socket.oio;

import io.netty.channel.ChannelPromise;

class OioSocketChannel$1 implements Runnable {
    final /* synthetic */ OioSocketChannel this$0;
    final /* synthetic */ ChannelPromise val$promise;

    OioSocketChannel$1(OioSocketChannel oioSocketChannel, ChannelPromise channelPromise) {
        this.this$0 = oioSocketChannel;
        this.val$promise = channelPromise;
    }

    public void run() {
        OioSocketChannel.access$000(this.this$0, this.val$promise);
    }
}
