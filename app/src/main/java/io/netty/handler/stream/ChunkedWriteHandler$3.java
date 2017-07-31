package io.netty.handler.stream;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

class ChunkedWriteHandler$3 implements ChannelFutureListener {
    final /* synthetic */ ChunkedWriteHandler this$0;
    final /* synthetic */ ChunkedInput val$chunks;
    final /* synthetic */ ChunkedWriteHandler$PendingWrite val$currentWrite;
    final /* synthetic */ Object val$pendingMessage;

    ChunkedWriteHandler$3(ChunkedWriteHandler chunkedWriteHandler, Object obj, ChunkedWriteHandler$PendingWrite chunkedWriteHandler$PendingWrite, ChunkedInput chunkedInput) {
        this.this$0 = chunkedWriteHandler;
        this.val$pendingMessage = obj;
        this.val$currentWrite = chunkedWriteHandler$PendingWrite;
        this.val$chunks = chunkedInput;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            this.val$currentWrite.progress(this.val$chunks.progress(), this.val$chunks.length());
            return;
        }
        ChunkedWriteHandler.closeInput((ChunkedInput) this.val$pendingMessage);
        this.val$currentWrite.fail(future.cause());
    }
}
