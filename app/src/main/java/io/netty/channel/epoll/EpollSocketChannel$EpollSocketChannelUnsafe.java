package io.netty.channel.epoll;

import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.concurrent.Executor;

final class EpollSocketChannel$EpollSocketChannelUnsafe extends AbstractEpollStreamChannel$EpollStreamUnsafe {
    final /* synthetic */ EpollSocketChannel this$0;

    private EpollSocketChannel$EpollSocketChannelUnsafe(EpollSocketChannel epollSocketChannel) {
        this.this$0 = epollSocketChannel;
        super(epollSocketChannel);
    }

    protected Executor prepareToClose() {
        try {
            if (this.this$0.isOpen() && this.this$0.config().getSoLinger() > 0) {
                ((EpollEventLoop) this.this$0.eventLoop()).remove(this.this$0);
                return GlobalEventExecutor.INSTANCE;
            }
        } catch (Throwable th) {
        }
        return null;
    }

    boolean doFinishConnect() throws Exception {
        if (!super.doFinishConnect()) {
            return false;
        }
        EpollSocketChannel.access$102(this.this$0, EpollSocketChannel.access$300(EpollSocketChannel.access$200(this.this$0), this.this$0.fd().remoteAddress()));
        EpollSocketChannel.access$202(this.this$0, null);
        return true;
    }
}
