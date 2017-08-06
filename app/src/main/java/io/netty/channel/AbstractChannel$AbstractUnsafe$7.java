package io.netty.channel;

import io.netty.channel.AbstractChannel.AbstractUnsafe;

class AbstractChannel$AbstractUnsafe$7 implements Runnable {
    final /* synthetic */ AbstractUnsafe this$1;
    final /* synthetic */ boolean val$fireChannelInactive;
    final /* synthetic */ ChannelPromise val$promise;

    AbstractChannel$AbstractUnsafe$7(AbstractUnsafe abstractUnsafe, boolean z, ChannelPromise channelPromise) {
        this.this$1 = abstractUnsafe;
        this.val$fireChannelInactive = z;
        this.val$promise = channelPromise;
    }

    public void run() {
        try {
            this.this$1.this$0.doDeregister();
        } catch (Throwable t) {
            AbstractChannel.access$300().warn("Unexpected exception occurred while deregistering a_isRightVersion channel.", t);
        } finally {
            if (this.val$fireChannelInactive) {
                AbstractChannel.access$500(this.this$1.this$0).fireChannelInactive();
            }
            if (AbstractChannel.access$000(this.this$1.this$0)) {
                AbstractChannel.access$002(this.this$1.this$0, false);
                AbstractChannel.access$500(this.this$1.this$0).fireChannelUnregistered();
            }
            this.this$1.safeSetSuccess(this.val$promise);
        }
    }
}
