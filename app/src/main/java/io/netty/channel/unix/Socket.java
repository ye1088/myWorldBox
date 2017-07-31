package io.netty.channel.unix;

import io.netty.channel.ChannelException;
import io.netty.channel.unix.Errors.NativeIoException;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThrowableUtil;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;

public final class Socket extends FileDescriptor {
    private static final NativeIoException CONNECTION_RESET_EXCEPTION_SENDMSG = ((NativeIoException) ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:sendmsg(...)", Errors.ERRNO_EPIPE_NEGATIVE), Socket.class, "sendToAddresses(...)"));
    private static final NativeIoException CONNECTION_RESET_SHUTDOWN_EXCEPTION = ((NativeIoException) ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:shutdown(...)", Errors.ERRNO_ECONNRESET_NEGATIVE), Socket.class, "shutdown(...)"));
    private static final NativeConnectException CONNECT_REFUSED_EXCEPTION = ((NativeConnectException) ThrowableUtil.unknownStackTrace(new NativeConnectException("syscall:connect(...)", Errors.ERROR_ECONNREFUSED_NEGATIVE), Socket.class, "connect(...)"));
    private static final NativeConnectException FINISH_CONNECT_REFUSED_EXCEPTION = ((NativeConnectException) ThrowableUtil.unknownStackTrace(new NativeConnectException("syscall:getsockopt(...)", Errors.ERROR_ECONNREFUSED_NEGATIVE), Socket.class, "finishConnect(...)"));
    private static final ClosedChannelException SEND_TO_ADDRESSES_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Socket.class, "sendToAddresses(...)"));
    private static final ClosedChannelException SEND_TO_ADDRESS_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Socket.class, "sendToAddress(...)"));
    private static final NativeIoException SEND_TO_ADDRESS_CONNECTION_RESET_EXCEPTION = ((NativeIoException) ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:sendto(...)", Errors.ERRNO_EPIPE_NEGATIVE), Socket.class, "sendToAddress(...)"));
    private static final ClosedChannelException SEND_TO_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Socket.class, "sendTo(...)"));
    private static final NativeIoException SEND_TO_CONNECTION_RESET_EXCEPTION = ((NativeIoException) ThrowableUtil.unknownStackTrace(Errors.newConnectionResetException("syscall:sendto(...)", Errors.ERRNO_EPIPE_NEGATIVE), Socket.class, "sendTo(...)"));
    private static final ClosedChannelException SHUTDOWN_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Socket.class, "shutdown(...)"));

    private static native int accept(int i, byte[] bArr);

    private static native int bind(int i, byte[] bArr, int i2, int i3);

    private static native int bindDomainSocket(int i, byte[] bArr);

    private static native int connect(int i, byte[] bArr, int i2, int i3);

    private static native int connectDomainSocket(int i, byte[] bArr);

    private static native int finishConnect(int i);

    private static native int getReceiveBufferSize(int i) throws IOException;

    private static native int getSendBufferSize(int i) throws IOException;

    private static native int getSoError(int i) throws IOException;

    private static native int getSoLinger(int i) throws IOException;

    private static native int getTcpDeferAccept(int i) throws IOException;

    private static native int isKeepAlive(int i) throws IOException;

    private static native int isTcpCork(int i) throws IOException;

    private static native int isTcpNoDelay(int i) throws IOException;

    private static native int isTcpQuickAck(int i) throws IOException;

    private static native int listen(int i, int i2);

    private static native byte[] localAddress(int i);

    private static native int newSocketDgramFd();

    private static native int newSocketDomainFd();

    private static native int newSocketStreamFd();

    private static native DatagramSocketAddress recvFrom(int i, ByteBuffer byteBuffer, int i2, int i3) throws IOException;

    private static native DatagramSocketAddress recvFromAddress(int i, long j, int i2, int i3) throws IOException;

    private static native byte[] remoteAddress(int i);

    private static native int sendTo(int i, ByteBuffer byteBuffer, int i2, int i3, byte[] bArr, int i4, int i5);

    private static native int sendToAddress(int i, long j, int i2, int i3, byte[] bArr, int i4, int i5);

    private static native int sendToAddresses(int i, long j, int i2, byte[] bArr, int i3, int i4);

    private static native void setKeepAlive(int i, int i2) throws IOException;

    private static native void setReceiveBufferSize(int i, int i2) throws IOException;

    private static native void setSendBufferSize(int i, int i2) throws IOException;

    private static native void setSoLinger(int i, int i2) throws IOException;

    private static native void setTcpCork(int i, int i2) throws IOException;

    private static native void setTcpDeferAccept(int i, int i2) throws IOException;

    private static native void setTcpNoDelay(int i, int i2) throws IOException;

    private static native void setTcpQuickAck(int i, int i2) throws IOException;

    private static native int shutdown(int i, boolean z, boolean z2);

    public Socket(int fd) {
        super(fd);
    }

    public void shutdown() throws IOException {
        shutdown(true, true);
    }

    public void shutdown(boolean read, boolean write) throws IOException {
        int oldState;
        int newState;
        do {
            oldState = this.state;
            newState = oldState;
            if (read && !FileDescriptor.isInputShutdown(newState)) {
                newState = FileDescriptor.inputShutdown(newState);
            }
            if (write && !FileDescriptor.isOutputShutdown(newState)) {
                newState = FileDescriptor.outputShutdown(newState);
            }
            if (newState == oldState) {
                return;
            }
        } while (!casState(oldState, newState));
        int res = shutdown(this.fd, read, write);
        if (res < 0) {
            Errors.ioResult("shutdown", res, CONNECTION_RESET_SHUTDOWN_EXCEPTION, SHUTDOWN_CLOSED_CHANNEL_EXCEPTION);
        }
    }

    public boolean isShutdown() {
        int state = this.state;
        return FileDescriptor.isInputShutdown(state) && FileDescriptor.isOutputShutdown(state);
    }

    public boolean isInputShutdown() {
        return FileDescriptor.isInputShutdown(this.state);
    }

    public boolean isOutputShutdown() {
        return FileDescriptor.isOutputShutdown(this.state);
    }

    public int sendTo(ByteBuffer buf, int pos, int limit, InetAddress addr, int port) throws IOException {
        byte[] address;
        int scopeId;
        if (addr instanceof Inet6Address) {
            address = addr.getAddress();
            scopeId = ((Inet6Address) addr).getScopeId();
        } else {
            scopeId = 0;
            address = NativeInetAddress.ipv4MappedIpv6Address(addr.getAddress());
        }
        int res = sendTo(this.fd, buf, pos, limit, address, scopeId, port);
        return res >= 0 ? res : Errors.ioResult("sendTo", res, SEND_TO_CONNECTION_RESET_EXCEPTION, SEND_TO_CLOSED_CHANNEL_EXCEPTION);
    }

    public int sendToAddress(long memoryAddress, int pos, int limit, InetAddress addr, int port) throws IOException {
        byte[] address;
        int scopeId;
        if (addr instanceof Inet6Address) {
            address = addr.getAddress();
            scopeId = ((Inet6Address) addr).getScopeId();
        } else {
            scopeId = 0;
            address = NativeInetAddress.ipv4MappedIpv6Address(addr.getAddress());
        }
        int res = sendToAddress(this.fd, memoryAddress, pos, limit, address, scopeId, port);
        return res >= 0 ? res : Errors.ioResult("sendToAddress", res, SEND_TO_ADDRESS_CONNECTION_RESET_EXCEPTION, SEND_TO_ADDRESS_CLOSED_CHANNEL_EXCEPTION);
    }

    public int sendToAddresses(long memoryAddress, int length, InetAddress addr, int port) throws IOException {
        byte[] address;
        int scopeId;
        if (addr instanceof Inet6Address) {
            address = addr.getAddress();
            scopeId = ((Inet6Address) addr).getScopeId();
        } else {
            scopeId = 0;
            address = NativeInetAddress.ipv4MappedIpv6Address(addr.getAddress());
        }
        int res = sendToAddresses(this.fd, memoryAddress, length, address, scopeId, port);
        return res >= 0 ? res : Errors.ioResult("sendToAddresses", res, CONNECTION_RESET_EXCEPTION_SENDMSG, SEND_TO_ADDRESSES_CLOSED_CHANNEL_EXCEPTION);
    }

    public DatagramSocketAddress recvFrom(ByteBuffer buf, int pos, int limit) throws IOException {
        return recvFrom(this.fd, buf, pos, limit);
    }

    public DatagramSocketAddress recvFromAddress(long memoryAddress, int pos, int limit) throws IOException {
        return recvFromAddress(this.fd, memoryAddress, pos, limit);
    }

    public boolean connect(SocketAddress socketAddress) throws IOException {
        int res;
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
            NativeInetAddress address = NativeInetAddress.newInstance(inetSocketAddress.getAddress());
            res = connect(this.fd, address.address, address.scopeId, inetSocketAddress.getPort());
        } else if (socketAddress instanceof DomainSocketAddress) {
            res = connectDomainSocket(this.fd, ((DomainSocketAddress) socketAddress).path().getBytes(CharsetUtil.UTF_8));
        } else {
            throw new Error("Unexpected SocketAddress implementation " + socketAddress);
        }
        if (res < 0) {
            if (res == Errors.ERRNO_EINPROGRESS_NEGATIVE) {
                return false;
            }
            Errors.throwConnectException("connect", CONNECT_REFUSED_EXCEPTION, res);
        }
        return true;
    }

    public boolean finishConnect() throws IOException {
        int res = finishConnect(this.fd);
        if (res < 0) {
            if (res == Errors.ERRNO_EINPROGRESS_NEGATIVE) {
                return false;
            }
            Errors.throwConnectException("finishConnect", FINISH_CONNECT_REFUSED_EXCEPTION, res);
        }
        return true;
    }

    public void bind(SocketAddress socketAddress) throws IOException {
        int res;
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress addr = (InetSocketAddress) socketAddress;
            NativeInetAddress address = NativeInetAddress.newInstance(addr.getAddress());
            res = bind(this.fd, address.address, address.scopeId, addr.getPort());
            if (res < 0) {
                throw Errors.newIOException("bind", res);
            }
        } else if (socketAddress instanceof DomainSocketAddress) {
            res = bindDomainSocket(this.fd, ((DomainSocketAddress) socketAddress).path().getBytes(CharsetUtil.UTF_8));
            if (res < 0) {
                throw Errors.newIOException("bind", res);
            }
        } else {
            throw new Error("Unexpected SocketAddress implementation " + socketAddress);
        }
    }

    public void listen(int backlog) throws IOException {
        int res = listen(this.fd, backlog);
        if (res < 0) {
            throw Errors.newIOException("listen", res);
        }
    }

    public int accept(byte[] addr) throws IOException {
        int res = accept(this.fd, addr);
        if (res >= 0) {
            return res;
        }
        if (res == Errors.ERRNO_EAGAIN_NEGATIVE || res == Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
            return -1;
        }
        throw Errors.newIOException("accept", res);
    }

    public InetSocketAddress remoteAddress() {
        byte[] addr = remoteAddress(this.fd);
        if (addr == null) {
            return null;
        }
        return NativeInetAddress.address(addr, 0, addr.length);
    }

    public InetSocketAddress localAddress() {
        byte[] addr = localAddress(this.fd);
        if (addr == null) {
            return null;
        }
        return NativeInetAddress.address(addr, 0, addr.length);
    }

    public int getReceiveBufferSize() throws IOException {
        return getReceiveBufferSize(this.fd);
    }

    public int getSendBufferSize() throws IOException {
        return getSendBufferSize(this.fd);
    }

    public boolean isKeepAlive() throws IOException {
        return isKeepAlive(this.fd) != 0;
    }

    public boolean isTcpNoDelay() throws IOException {
        return isTcpNoDelay(this.fd) != 0;
    }

    public boolean isTcpCork() throws IOException {
        return isTcpCork(this.fd) != 0;
    }

    public int getSoLinger() throws IOException {
        return getSoLinger(this.fd);
    }

    public int getTcpDeferAccept() throws IOException {
        return getTcpDeferAccept(this.fd);
    }

    public boolean isTcpQuickAck() throws IOException {
        return isTcpQuickAck(this.fd) != 0;
    }

    public int getSoError() throws IOException {
        return getSoError(this.fd);
    }

    public void setKeepAlive(boolean keepAlive) throws IOException {
        setKeepAlive(this.fd, keepAlive ? 1 : 0);
    }

    public void setReceiveBufferSize(int receiveBufferSize) throws IOException {
        setReceiveBufferSize(this.fd, receiveBufferSize);
    }

    public void setSendBufferSize(int sendBufferSize) throws IOException {
        setSendBufferSize(this.fd, sendBufferSize);
    }

    public void setTcpNoDelay(boolean tcpNoDelay) throws IOException {
        setTcpNoDelay(this.fd, tcpNoDelay ? 1 : 0);
    }

    public void setTcpCork(boolean tcpCork) throws IOException {
        setTcpCork(this.fd, tcpCork ? 1 : 0);
    }

    public void setSoLinger(int soLinger) throws IOException {
        setSoLinger(this.fd, soLinger);
    }

    public void setTcpDeferAccept(int deferAccept) throws IOException {
        setTcpDeferAccept(this.fd, deferAccept);
    }

    public void setTcpQuickAck(boolean quickAck) throws IOException {
        setTcpQuickAck(this.fd, quickAck ? 1 : 0);
    }

    public String toString() {
        return "Socket{fd=" + this.fd + '}';
    }

    public static Socket newSocketStream() {
        int res = newSocketStreamFd();
        if (res >= 0) {
            return new Socket(res);
        }
        throw new ChannelException(Errors.newIOException("newSocketStream", res));
    }

    public static Socket newSocketDgram() {
        int res = newSocketDgramFd();
        if (res >= 0) {
            return new Socket(res);
        }
        throw new ChannelException(Errors.newIOException("newSocketDgram", res));
    }

    public static Socket newSocketDomain() {
        int res = newSocketDomainFd();
        if (res >= 0) {
            return new Socket(res);
        }
        throw new ChannelException(Errors.newIOException("newSocketDomain", res));
    }
}
