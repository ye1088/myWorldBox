package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.unix.DomainSocketChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import java.net.SocketAddress;

public final class EpollDomainSocketChannel extends AbstractEpollStreamChannel implements DomainSocketChannel {
    private final EpollDomainSocketChannelConfig config = new EpollDomainSocketChannelConfig(this);
    private volatile DomainSocketAddress local;
    private volatile DomainSocketAddress remote;

    public EpollDomainSocketChannel() {
        super(Socket.newSocketDomain(), false);
    }

    @Deprecated
    public EpollDomainSocketChannel(Channel parent, FileDescriptor fd) {
        super(parent, new Socket(fd.intValue()));
    }

    @Deprecated
    public EpollDomainSocketChannel(FileDescriptor fd) {
        super(fd);
    }

    public EpollDomainSocketChannel(Channel parent, Socket fd) {
        super(parent, fd);
    }

    public EpollDomainSocketChannel(Socket fd, boolean active) {
        super(fd, active);
    }

    protected AbstractEpollUnsafe newUnsafe() {
        return new EpollDomainUnsafe(this, null);
    }

    protected DomainSocketAddress localAddress0() {
        return this.local;
    }

    protected DomainSocketAddress remoteAddress0() {
        return this.remote;
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        fd().bind(localAddress);
        this.local = (DomainSocketAddress) localAddress;
    }

    public EpollDomainSocketChannelConfig config() {
        return this.config;
    }

    protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        if (!super.doConnect(remoteAddress, localAddress)) {
            return false;
        }
        this.local = (DomainSocketAddress) localAddress;
        this.remote = (DomainSocketAddress) remoteAddress;
        return true;
    }

    public DomainSocketAddress remoteAddress() {
        return (DomainSocketAddress) super.remoteAddress();
    }

    public DomainSocketAddress localAddress() {
        return (DomainSocketAddress) super.localAddress();
    }

    protected boolean doWriteSingle(ChannelOutboundBuffer in, int writeSpinCount) throws Exception {
        Object msg = in.current();
        if (!(msg instanceof FileDescriptor) || Native.sendFd(fd().intValue(), ((FileDescriptor) msg).intValue()) <= 0) {
            return super.doWriteSingle(in, writeSpinCount);
        }
        in.remove();
        return true;
    }

    protected Object filterOutboundMessage(Object msg) {
        return msg instanceof FileDescriptor ? msg : super.filterOutboundMessage(msg);
    }
}
