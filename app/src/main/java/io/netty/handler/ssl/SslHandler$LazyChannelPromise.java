package io.netty.handler.ssl;

import io.netty.channel.Channel;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;

final class SslHandler$LazyChannelPromise extends DefaultPromise<Channel> {
    final /* synthetic */ SslHandler this$0;

    private SslHandler$LazyChannelPromise(SslHandler sslHandler) {
        this.this$0 = sslHandler;
    }

    protected EventExecutor executor() {
        if (SslHandler.access$400(this.this$0) != null) {
            return SslHandler.access$400(this.this$0).executor();
        }
        throw new IllegalStateException();
    }

    protected void checkDeadLock() {
        if (SslHandler.access$400(this.this$0) != null) {
            super.checkDeadLock();
        }
    }
}
