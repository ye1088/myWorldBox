package io.netty.channel;

import io.netty.channel.AbstractChannel.AbstractUnsafe;

class AbstractChannel$AbstractUnsafe$2 implements Runnable {
    final /* synthetic */ AbstractUnsafe this$1;

    AbstractChannel$AbstractUnsafe$2(AbstractUnsafe abstractUnsafe) {
        this.this$1 = abstractUnsafe;
    }

    public void run() {
        AbstractChannel.access$500(this.this$1.this$0).fireChannelActive();
    }
}
