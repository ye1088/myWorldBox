package io.netty.handler.traffic;

import io.netty.channel.ChannelHandlerContext;

class GlobalTrafficShapingHandler$1 implements Runnable {
    final /* synthetic */ GlobalTrafficShapingHandler this$0;
    final /* synthetic */ ChannelHandlerContext val$ctx;
    final /* synthetic */ GlobalTrafficShapingHandler$PerChannel val$forSchedule;
    final /* synthetic */ long val$futureNow;

    GlobalTrafficShapingHandler$1(GlobalTrafficShapingHandler globalTrafficShapingHandler, ChannelHandlerContext channelHandlerContext, GlobalTrafficShapingHandler$PerChannel globalTrafficShapingHandler$PerChannel, long j) {
        this.this$0 = globalTrafficShapingHandler;
        this.val$ctx = channelHandlerContext;
        this.val$forSchedule = globalTrafficShapingHandler$PerChannel;
        this.val$futureNow = j;
    }

    public void run() {
        GlobalTrafficShapingHandler.access$200(this.this$0, this.val$ctx, this.val$forSchedule, this.val$futureNow);
    }
}
