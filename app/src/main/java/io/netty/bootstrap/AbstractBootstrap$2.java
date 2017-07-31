package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import java.net.SocketAddress;

class AbstractBootstrap$2 implements Runnable {
    final /* synthetic */ Channel val$channel;
    final /* synthetic */ SocketAddress val$localAddress;
    final /* synthetic */ ChannelPromise val$promise;
    final /* synthetic */ ChannelFuture val$regFuture;

    AbstractBootstrap$2(ChannelFuture channelFuture, Channel channel, SocketAddress socketAddress, ChannelPromise channelPromise) {
        this.val$regFuture = channelFuture;
        this.val$channel = channel;
        this.val$localAddress = socketAddress;
        this.val$promise = channelPromise;
    }

    public void run() {
        if (this.val$regFuture.isSuccess()) {
            this.val$channel.bind(this.val$localAddress, this.val$promise).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            this.val$promise.setFailure(this.val$regFuture.cause());
        }
    }
}
