package io.netty.channel;

import io.netty.channel.AbstractChannel.AbstractUnsafe;

class AbstractChannel$AbstractUnsafe$6 implements Runnable {
    final /* synthetic */ AbstractUnsafe this$1;
    final /* synthetic */ boolean val$wasActive;

    AbstractChannel$AbstractUnsafe$6(AbstractUnsafe abstractUnsafe, boolean z) {
        this.this$1 = abstractUnsafe;
        this.val$wasActive = z;
    }

    public void run() {
        AbstractUnsafe.access$800(this.this$1, this.val$wasActive);
    }
}
