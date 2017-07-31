package io.netty.channel.oio;

class AbstractOioChannel$2 implements Runnable {
    final /* synthetic */ AbstractOioChannel this$0;

    AbstractOioChannel$2(AbstractOioChannel abstractOioChannel) {
        this.this$0 = abstractOioChannel;
    }

    public void run() {
        this.this$0.readPending = false;
    }
}
