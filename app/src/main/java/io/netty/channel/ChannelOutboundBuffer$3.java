package io.netty.channel;

import java.nio.channels.ClosedChannelException;

class ChannelOutboundBuffer$3 implements Runnable {
    final /* synthetic */ ChannelOutboundBuffer this$0;
    final /* synthetic */ ClosedChannelException val$cause;

    ChannelOutboundBuffer$3(ChannelOutboundBuffer channelOutboundBuffer, ClosedChannelException closedChannelException) {
        this.this$0 = channelOutboundBuffer;
        this.val$cause = closedChannelException;
    }

    public void run() {
        this.this$0.close(this.val$cause);
    }
}
