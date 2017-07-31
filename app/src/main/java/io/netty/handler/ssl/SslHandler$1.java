package io.netty.handler.ssl;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

class SslHandler$1 implements Runnable {
    final /* synthetic */ SslHandler this$0;
    final /* synthetic */ ChannelHandlerContext val$ctx;
    final /* synthetic */ ChannelPromise val$future;

    SslHandler$1(SslHandler sslHandler, ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        this.this$0 = sslHandler;
        this.val$ctx = channelHandlerContext;
        this.val$future = channelPromise;
    }

    public void run() {
        SslHandler.access$102(this.this$0, true);
        SslHandler.access$200(this.this$0).closeOutbound();
        try {
            this.this$0.write(this.val$ctx, Unpooled.EMPTY_BUFFER, this.val$future);
            this.this$0.flush(this.val$ctx);
        } catch (Exception e) {
            if (!this.val$future.tryFailure(e)) {
                SslHandler.access$300().warn("{} flush() raised a masked exception.", this.val$ctx.channel(), e);
            }
        }
    }
}
