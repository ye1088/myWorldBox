package io.netty.channel;

import io.netty.channel.AbstractChannel.AbstractUnsafe;

class AbstractChannel$AbstractUnsafe$8 implements Runnable {
    final /* synthetic */ AbstractUnsafe this$1;
    final /* synthetic */ Exception val$e;

    AbstractChannel$AbstractUnsafe$8(AbstractUnsafe abstractUnsafe, Exception exception) {
        this.this$1 = abstractUnsafe;
        this.val$e = exception;
    }

    public void run() {
        AbstractChannel.access$500(this.this$1.this$0).fireExceptionCaught(this.val$e);
    }
}
