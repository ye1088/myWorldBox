package io.netty.channel;

class ThreadPerChannelEventLoop$2 implements ChannelFutureListener {
    final /* synthetic */ ThreadPerChannelEventLoop this$0;

    ThreadPerChannelEventLoop$2(ThreadPerChannelEventLoop threadPerChannelEventLoop) {
        this.this$0 = threadPerChannelEventLoop;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            ThreadPerChannelEventLoop.access$002(this.this$0, future.channel());
        } else {
            this.this$0.deregister();
        }
    }
}
