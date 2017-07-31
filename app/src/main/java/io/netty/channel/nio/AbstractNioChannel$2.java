package io.netty.channel.nio;

class AbstractNioChannel$2 implements Runnable {
    final /* synthetic */ AbstractNioChannel this$0;
    final /* synthetic */ boolean val$readPending;

    AbstractNioChannel$2(AbstractNioChannel abstractNioChannel, boolean z) {
        this.this$0 = abstractNioChannel;
        this.val$readPending = z;
    }

    public void run() {
        AbstractNioChannel.access$100(this.this$0, this.val$readPending);
    }
}
