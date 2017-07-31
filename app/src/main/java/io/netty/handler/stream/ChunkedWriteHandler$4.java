package io.netty.handler.stream;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

class ChunkedWriteHandler$4 implements ChannelFutureListener {
    final /* synthetic */ ChunkedWriteHandler this$0;
    final /* synthetic */ Channel val$channel;
    final /* synthetic */ ChunkedInput val$chunks;
    final /* synthetic */ ChunkedWriteHandler$PendingWrite val$currentWrite;
    final /* synthetic */ Object val$pendingMessage;

    ChunkedWriteHandler$4(ChunkedWriteHandler chunkedWriteHandler, Object obj, ChunkedWriteHandler$PendingWrite chunkedWriteHandler$PendingWrite, ChunkedInput chunkedInput, Channel channel) {
        this.this$0 = chunkedWriteHandler;
        this.val$pendingMessage = obj;
        this.val$currentWrite = chunkedWriteHandler$PendingWrite;
        this.val$chunks = chunkedInput;
        this.val$channel = channel;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            this.val$currentWrite.progress(this.val$chunks.progress(), this.val$chunks.length());
            if (this.val$channel.isWritable()) {
                this.this$0.resumeTransfer();
                return;
            }
            return;
        }
        ChunkedWriteHandler.closeInput((ChunkedInput) this.val$pendingMessage);
        this.val$currentWrite.fail(future.cause());
    }
}
