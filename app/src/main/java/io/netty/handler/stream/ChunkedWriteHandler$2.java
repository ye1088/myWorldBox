package io.netty.handler.stream;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

class ChunkedWriteHandler$2 implements ChannelFutureListener {
    final /* synthetic */ ChunkedWriteHandler this$0;
    final /* synthetic */ ChunkedInput val$chunks;
    final /* synthetic */ ChunkedWriteHandler$PendingWrite val$currentWrite;

    ChunkedWriteHandler$2(ChunkedWriteHandler chunkedWriteHandler, ChunkedWriteHandler$PendingWrite chunkedWriteHandler$PendingWrite, ChunkedInput chunkedInput) {
        this.this$0 = chunkedWriteHandler;
        this.val$currentWrite = chunkedWriteHandler$PendingWrite;
        this.val$chunks = chunkedInput;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        this.val$currentWrite.progress(this.val$chunks.progress(), this.val$chunks.length());
        this.val$currentWrite.success(this.val$chunks.length());
        ChunkedWriteHandler.closeInput(this.val$chunks);
    }
}
