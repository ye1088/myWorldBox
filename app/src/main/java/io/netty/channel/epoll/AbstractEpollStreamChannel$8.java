package io.netty.channel.epoll;

class AbstractEpollStreamChannel$8 implements Runnable {
    final /* synthetic */ AbstractEpollStreamChannel this$0;
    final /* synthetic */ AbstractEpollStreamChannel$SpliceInTask val$task;

    AbstractEpollStreamChannel$8(AbstractEpollStreamChannel abstractEpollStreamChannel, AbstractEpollStreamChannel$SpliceInTask abstractEpollStreamChannel$SpliceInTask) {
        this.this$0 = abstractEpollStreamChannel;
        this.val$task = abstractEpollStreamChannel$SpliceInTask;
    }

    public void run() {
        AbstractEpollStreamChannel.access$800(this.this$0, this.val$task);
    }
}
