package io.netty.handler.traffic;

import io.netty.channel.ChannelHandlerContext;

class GlobalChannelTrafficShapingHandler$2 implements Runnable {
    final /* synthetic */ GlobalChannelTrafficShapingHandler this$0;
    final /* synthetic */ ChannelHandlerContext val$ctx;
    final /* synthetic */ GlobalChannelTrafficShapingHandler$PerChannel val$forSchedule;
    final /* synthetic */ long val$futureNow;

    GlobalChannelTrafficShapingHandler$2(GlobalChannelTrafficShapingHandler globalChannelTrafficShapingHandler, ChannelHandlerContext channelHandlerContext, GlobalChannelTrafficShapingHandler$PerChannel globalChannelTrafficShapingHandler$PerChannel, long j) {
        this.this$0 = globalChannelTrafficShapingHandler;
        this.val$ctx = channelHandlerContext;
        this.val$forSchedule = globalChannelTrafficShapingHandler$PerChannel;
        this.val$futureNow = j;
    }

    public void run() {
        GlobalChannelTrafficShapingHandler.access$100(this.this$0, this.val$ctx, this.val$forSchedule, this.val$futureNow);
    }
}
