package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.net.SocketAddress;

class AbstractBootstrap$1 implements ChannelFutureListener {
    final /* synthetic */ AbstractBootstrap this$0;
    final /* synthetic */ Channel val$channel;
    final /* synthetic */ SocketAddress val$localAddress;
    final /* synthetic */ AbstractBootstrap$PendingRegistrationPromise val$promise;
    final /* synthetic */ ChannelFuture val$regFuture;

    AbstractBootstrap$1(AbstractBootstrap abstractBootstrap, AbstractBootstrap$PendingRegistrationPromise abstractBootstrap$PendingRegistrationPromise, ChannelFuture channelFuture, Channel channel, SocketAddress socketAddress) {
        this.this$0 = abstractBootstrap;
        this.val$promise = abstractBootstrap$PendingRegistrationPromise;
        this.val$regFuture = channelFuture;
        this.val$channel = channel;
        this.val$localAddress = socketAddress;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        Throwable cause = future.cause();
        if (cause != null) {
            this.val$promise.setFailure(cause);
            return;
        }
        this.val$promise.registered();
        AbstractBootstrap.access$000(this.val$regFuture, this.val$channel, this.val$localAddress, this.val$promise);
    }
}
