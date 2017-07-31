package io.netty.channel.oio;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import io.netty.channel.ThreadPerChannelEventLoop;
import java.net.SocketAddress;

public abstract class AbstractOioChannel extends AbstractChannel {
    protected static final int SO_TIMEOUT = 1000;
    private final Runnable clearReadPendingRunnable = new 2(this);
    boolean readPending;
    private final Runnable readTask = new 1(this);

    protected abstract void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception;

    protected abstract void doRead();

    protected AbstractOioChannel(Channel parent) {
        super(parent);
    }

    protected AbstractUnsafe newUnsafe() {
        return new DefaultOioUnsafe(this, null);
    }

    protected boolean isCompatible(EventLoop loop) {
        return loop instanceof ThreadPerChannelEventLoop;
    }

    protected void doBeginRead() throws Exception {
        if (!this.readPending) {
            this.readPending = true;
            eventLoop().execute(this.readTask);
        }
    }

    @Deprecated
    protected boolean isReadPending() {
        return this.readPending;
    }

    @Deprecated
    protected void setReadPending(boolean readPending) {
        if (isRegistered()) {
            EventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                this.readPending = readPending;
                return;
            } else {
                eventLoop.execute(new 3(this, readPending));
                return;
            }
        }
        this.readPending = readPending;
    }

    protected final void clearReadPending() {
        if (isRegistered()) {
            EventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                this.readPending = false;
                return;
            } else {
                eventLoop.execute(this.clearReadPendingRunnable);
                return;
            }
        }
        this.readPending = false;
    }
}
