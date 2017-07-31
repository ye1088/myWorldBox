package io.netty.channel.oio;

class AbstractOioChannel$1 implements Runnable {
    final /* synthetic */ AbstractOioChannel this$0;

    AbstractOioChannel$1(AbstractOioChannel abstractOioChannel) {
        this.this$0 = abstractOioChannel;
    }

    public void run() {
        this.this$0.doRead();
    }
}
