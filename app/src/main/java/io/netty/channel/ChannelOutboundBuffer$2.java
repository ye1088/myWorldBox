package io.netty.channel;

class ChannelOutboundBuffer$2 implements Runnable {
    final /* synthetic */ ChannelOutboundBuffer this$0;
    final /* synthetic */ ChannelPipeline val$pipeline;

    ChannelOutboundBuffer$2(ChannelOutboundBuffer channelOutboundBuffer, ChannelPipeline channelPipeline) {
        this.this$0 = channelOutboundBuffer;
        this.val$pipeline = channelPipeline;
    }

    public void run() {
        this.val$pipeline.fireChannelWritabilityChanged();
    }
}
