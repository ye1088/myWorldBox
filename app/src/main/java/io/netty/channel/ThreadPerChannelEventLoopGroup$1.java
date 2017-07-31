package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

class ThreadPerChannelEventLoopGroup$1 implements FutureListener<Object> {
    final /* synthetic */ ThreadPerChannelEventLoopGroup this$0;

    ThreadPerChannelEventLoopGroup$1(ThreadPerChannelEventLoopGroup threadPerChannelEventLoopGroup) {
        this.this$0 = threadPerChannelEventLoopGroup;
    }

    public void operationComplete(Future<Object> future) throws Exception {
        if (this.this$0.isTerminated()) {
            ThreadPerChannelEventLoopGroup.access$000(this.this$0).trySuccess(null);
        }
    }
}
