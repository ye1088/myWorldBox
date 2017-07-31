package io.netty.channel;

import io.netty.channel.AbstractChannel.AbstractUnsafe;
import java.nio.channels.ClosedChannelException;

class AbstractChannel$AbstractUnsafe$5 implements Runnable {
    final /* synthetic */ AbstractUnsafe this$1;
    final /* synthetic */ Throwable val$cause;
    final /* synthetic */ ClosedChannelException val$closeCause;
    final /* synthetic */ boolean val$notify;
    final /* synthetic */ ChannelOutboundBuffer val$outboundBuffer;
    final /* synthetic */ ChannelPromise val$promise;
    final /* synthetic */ boolean val$wasActive;

    AbstractChannel$AbstractUnsafe$5(AbstractUnsafe abstractUnsafe, ChannelPromise channelPromise, ChannelOutboundBuffer channelOutboundBuffer, Throwable th, boolean z, ClosedChannelException closedChannelException, boolean z2) {
        this.this$1 = abstractUnsafe;
        this.val$promise = channelPromise;
        this.val$outboundBuffer = channelOutboundBuffer;
        this.val$cause = th;
        this.val$notify = z;
        this.val$closeCause = closedChannelException;
        this.val$wasActive = z2;
    }

    public void run() {
        try {
            AbstractUnsafe.access$700(this.this$1, this.val$promise);
        } finally {
            AbstractUnsafe.access$900(this.this$1, new Runnable() {
                public void run() {
                    AbstractChannel$AbstractUnsafe$5.this.val$outboundBuffer.failFlushed(AbstractChannel$AbstractUnsafe$5.this.val$cause, AbstractChannel$AbstractUnsafe$5.this.val$notify);
                    AbstractChannel$AbstractUnsafe$5.this.val$outboundBuffer.close(AbstractChannel$AbstractUnsafe$5.this.val$closeCause);
                    AbstractUnsafe.access$800(AbstractChannel$AbstractUnsafe$5.this.this$1, AbstractChannel$AbstractUnsafe$5.this.val$wasActive);
                }
            });
        }
    }
}
