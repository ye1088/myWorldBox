package io.netty.channel.epoll;

import java.io.IOException;

final class AbstractEpollStreamChannel$SpliceOutTask {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractEpollStreamChannel.class.desiredAssertionStatus());
    private final boolean autoRead;
    private final AbstractEpollStreamChannel ch;
    private int len;
    final /* synthetic */ AbstractEpollStreamChannel this$0;

    AbstractEpollStreamChannel$SpliceOutTask(AbstractEpollStreamChannel abstractEpollStreamChannel, AbstractEpollStreamChannel ch, int len, boolean autoRead) {
        this.this$0 = abstractEpollStreamChannel;
        this.ch = ch;
        this.len = len;
        this.autoRead = autoRead;
    }

    public boolean spliceOut() throws Exception {
        if ($assertionsDisabled || this.ch.eventLoop().inEventLoop()) {
            try {
                this.len -= Native.splice(AbstractEpollStreamChannel.access$1000(this.ch).intValue(), -1, this.ch.fd().intValue(), -1, (long) this.len);
                if (this.len != 0) {
                    return false;
                }
                if (this.autoRead) {
                    this.this$0.config().setAutoRead(true);
                }
                return true;
            } catch (IOException e) {
                if (this.autoRead) {
                    this.this$0.config().setAutoRead(true);
                }
                throw e;
            }
        }
        throw new AssertionError();
    }
}
