package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.DefaultChannelPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;

final class AbstractBootstrap$PendingRegistrationPromise extends DefaultChannelPromise {
    private volatile boolean registered;

    AbstractBootstrap$PendingRegistrationPromise(Channel channel) {
        super(channel);
    }

    void registered() {
        this.registered = true;
    }

    protected EventExecutor executor() {
        if (this.registered) {
            return super.executor();
        }
        return GlobalEventExecutor.INSTANCE;
    }
}
