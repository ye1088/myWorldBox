package io.netty.handler.stream;

import io.netty.channel.ChannelHandlerContext;

class ChunkedWriteHandler$1 implements Runnable {
    final /* synthetic */ ChunkedWriteHandler this$0;
    final /* synthetic */ ChannelHandlerContext val$ctx;

    ChunkedWriteHandler$1(ChunkedWriteHandler chunkedWriteHandler, ChannelHandlerContext channelHandlerContext) {
        this.this$0 = chunkedWriteHandler;
        this.val$ctx = channelHandlerContext;
    }

    public void run() {
        try {
            ChunkedWriteHandler.access$000(this.this$0, this.val$ctx);
        } catch (Throwable e) {
            if (ChunkedWriteHandler.access$100().isWarnEnabled()) {
                ChunkedWriteHandler.access$100().warn("Unexpected exception while sending chunks.", e);
            }
        }
    }
}
