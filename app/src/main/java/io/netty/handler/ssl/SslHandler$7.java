package io.netty.handler.ssl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

class SslHandler$7 implements Runnable {
    final /* synthetic */ SslHandler this$0;
    final /* synthetic */ ChannelHandlerContext val$ctx;
    final /* synthetic */ ChannelPromise val$promise;

    SslHandler$7(SslHandler sslHandler, ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        this.this$0 = sslHandler;
        this.val$ctx = channelHandlerContext;
        this.val$promise = channelPromise;
    }

    public void run() {
        SslHandler.access$300().warn("{} Last write attempt timed out; force-closing the connection.", this.val$ctx.channel());
        SslHandler.access$800(this.val$ctx.close(this.val$ctx.newPromise()), this.val$promise);
    }
}
