package io.netty.handler.ssl;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.util.concurrent.ScheduledFuture;

class SslHandler$8 implements ChannelFutureListener {
    final /* synthetic */ SslHandler this$0;
    final /* synthetic */ ChannelHandlerContext val$ctx;
    final /* synthetic */ ChannelPromise val$promise;
    final /* synthetic */ ScheduledFuture val$timeoutFuture;

    SslHandler$8(SslHandler sslHandler, ScheduledFuture scheduledFuture, ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        this.this$0 = sslHandler;
        this.val$timeoutFuture = scheduledFuture;
        this.val$ctx = channelHandlerContext;
        this.val$promise = channelPromise;
    }

    public void operationComplete(ChannelFuture f) throws Exception {
        if (this.val$timeoutFuture != null) {
            this.val$timeoutFuture.cancel(false);
        }
        SslHandler.access$800(this.val$ctx.close(this.val$ctx.newPromise()), this.val$promise);
    }
}
