package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.EventLoop;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import java.net.InetSocketAddress;

public abstract class AbstractEpollServerChannel extends AbstractEpollChannel implements ServerChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);

    abstract Channel newChildChannel(int i, byte[] bArr, int i2, int i3) throws Exception;

    public /* bridge */ /* synthetic */ boolean isActive() {
        return super.isActive();
    }

    public /* bridge */ /* synthetic */ boolean isOpen() {
        return super.isOpen();
    }

    @Deprecated
    protected AbstractEpollServerChannel(int fd) {
        this(new Socket(fd), false);
    }

    @Deprecated
    protected AbstractEpollServerChannel(FileDescriptor fd) {
        this(new Socket(fd.intValue()));
    }

    @Deprecated
    protected AbstractEpollServerChannel(Socket fd) {
        this(fd, isSoErrorZero(fd));
    }

    protected AbstractEpollServerChannel(Socket fd, boolean active) {
        super(null, fd, Native.EPOLLIN, active);
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    protected boolean isCompatible(EventLoop loop) {
        return loop instanceof EpollEventLoop;
    }

    protected InetSocketAddress remoteAddress0() {
        return null;
    }

    protected AbstractEpollUnsafe newUnsafe() {
        return new EpollServerSocketUnsafe(this);
    }

    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected Object filterOutboundMessage(Object msg) throws Exception {
        throw new UnsupportedOperationException();
    }
}
