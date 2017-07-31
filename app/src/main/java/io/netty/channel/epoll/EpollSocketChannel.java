package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.AlreadyConnectedException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public final class EpollSocketChannel extends AbstractEpollStreamChannel implements SocketChannel {
    private final EpollSocketChannelConfig config;
    private volatile InetSocketAddress local;
    private volatile InetSocketAddress remote;
    private InetSocketAddress requestedRemote;
    private volatile Collection<InetAddress> tcpMd5SigAddresses;

    EpollSocketChannel(Channel parent, Socket fd, InetSocketAddress remote) {
        super(parent, fd);
        this.tcpMd5SigAddresses = Collections.emptyList();
        this.config = new EpollSocketChannelConfig(this);
        this.remote = remote;
        this.local = fd.localAddress();
        if (parent instanceof EpollServerSocketChannel) {
            this.tcpMd5SigAddresses = ((EpollServerSocketChannel) parent).tcpMd5SigAddresses();
        }
    }

    public EpollSocketChannel() {
        super(Socket.newSocketStream(), false);
        this.tcpMd5SigAddresses = Collections.emptyList();
        this.config = new EpollSocketChannelConfig(this);
    }

    @Deprecated
    public EpollSocketChannel(FileDescriptor fd) {
        super(fd);
        this.tcpMd5SigAddresses = Collections.emptyList();
        this.remote = fd().remoteAddress();
        this.local = fd().localAddress();
        this.config = new EpollSocketChannelConfig(this);
    }

    public EpollSocketChannel(Socket fd, boolean active) {
        super(fd, active);
        this.tcpMd5SigAddresses = Collections.emptyList();
        this.remote = fd.remoteAddress();
        this.local = fd.localAddress();
        this.config = new EpollSocketChannelConfig(this);
    }

    public EpollTcpInfo tcpInfo() {
        return tcpInfo(new EpollTcpInfo());
    }

    public EpollTcpInfo tcpInfo(EpollTcpInfo info) {
        try {
            Native.tcpInfo(fd().intValue(), info);
            return info;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    protected SocketAddress localAddress0() {
        return this.local;
    }

    protected SocketAddress remoteAddress0() {
        return this.remote;
    }

    protected void doBind(SocketAddress local) throws Exception {
        fd().bind((InetSocketAddress) local);
        this.local = fd().localAddress();
    }

    public EpollSocketChannelConfig config() {
        return this.config;
    }

    public ServerSocketChannel parent() {
        return (ServerSocketChannel) super.parent();
    }

    protected AbstractEpollUnsafe newUnsafe() {
        return new EpollSocketChannelUnsafe(this, null);
    }

    private static InetSocketAddress computeRemoteAddr(InetSocketAddress remoteAddr, InetSocketAddress osRemoteAddr) {
        if (osRemoteAddr == null) {
            return remoteAddr;
        }
        if (PlatformDependent.javaVersion() < 7) {
            return osRemoteAddr;
        }
        try {
            return new InetSocketAddress(InetAddress.getByAddress(remoteAddr.getHostString(), osRemoteAddr.getAddress().getAddress()), osRemoteAddr.getPort());
        } catch (UnknownHostException e) {
            return osRemoteAddr;
        }
    }

    protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            checkResolvable((InetSocketAddress) localAddress);
        }
        InetSocketAddress remoteAddr = (InetSocketAddress) remoteAddress;
        checkResolvable(remoteAddr);
        if (this.remote != null) {
            throw new AlreadyConnectedException();
        }
        boolean connected = super.doConnect(remoteAddress, localAddress);
        if (connected) {
            this.remote = computeRemoteAddr(remoteAddr, fd().remoteAddress());
        } else {
            this.requestedRemote = remoteAddr;
        }
        this.local = fd().localAddress();
        return connected;
    }

    void setTcpMd5Sig(Map<InetAddress, byte[]> keys) throws IOException {
        this.tcpMd5SigAddresses = TcpMd5Util.newTcpMd5Sigs(this, this.tcpMd5SigAddresses, keys);
    }
}
