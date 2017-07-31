package io.netty.channel.oio;

class AbstractOioChannel$3 implements Runnable {
    final /* synthetic */ AbstractOioChannel this$0;
    final /* synthetic */ boolean val$readPending;

    AbstractOioChannel$3(AbstractOioChannel abstractOioChannel, boolean z) {
        this.this$0 = abstractOioChannel;
        this.val$readPending = z;
    }

    public void run() {
        this.this$0.readPending = this.val$readPending;
    }
}
