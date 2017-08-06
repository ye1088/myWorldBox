package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.EventLoop;
import io.netty.channel.oio.OioByteStreamChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class OioSocketChannel extends OioByteStreamChannel implements SocketChannel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSocketChannel.class);
    private final OioSocketChannelConfig config;
    private final Socket socket;

    public OioSocketChannel() {
        this(new Socket());
    }

    public OioSocketChannel(Socket socket) {
        this(null, socket);
    }

    public OioSocketChannel(Channel parent, Socket socket) {
        super(parent);
        this.socket = socket;
        this.config = new DefaultOioSocketChannelConfig(this, socket);
        try {
            if (socket.isConnected()) {
                activate(socket.getInputStream(), socket.getOutputStream());
            }
            socket.setSoTimeout(1000);
            if (!true) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.warn("Failed to close a_isRightVersion socket.", e);
                }
            }
        } catch (Exception e2) {
            throw new ChannelException("failed to initialize a_isRightVersion socket", e2);
        } catch (Throwable th) {
            if (!false) {
                try {
                    socket.close();
                } catch (IOException e3) {
                    logger.warn("Failed to close a_isRightVersion socket.", e3);
                }
            }
        }
    }

    public ServerSocketChannel parent() {
        return (ServerSocketChannel) super.parent();
    }

    public OioSocketChannelConfig config() {
        return this.config;
    }

    public boolean isOpen() {
        return !this.socket.isClosed();
    }

    public boolean isActive() {
        return !this.socket.isClosed() && this.socket.isConnected();
    }

    public boolean isOutputShutdown() {
        return this.socket.isOutputShutdown() || !isActive();
    }

    public boolean isInputShutdown() {
        return this.socket.isInputShutdown() || !isActive();
    }

    public boolean isShutdown() {
        return (this.socket.isInputShutdown() && this.socket.isOutputShutdown()) || !isActive();
    }

    public ChannelFuture shutdownOutput() {
        return shutdownOutput(newPromise());
    }

    public ChannelFuture shutdownInput() {
        return shutdownInput(newPromise());
    }

    public ChannelFuture shutdown() {
        return shutdown(newPromise());
    }

    protected int doReadBytes(ByteBuf buf) throws Exception {
        if (this.socket.isClosed()) {
            return -1;
        }
        try {
            return super.doReadBytes(buf);
        } catch (SocketTimeoutException e) {
            return 0;
        }
    }

    public ChannelFuture shutdownOutput(ChannelPromise promise) {
        EventLoop loop = eventLoop();
        if (loop.inEventLoop()) {
            shutdownOutput0(promise);
        } else {
            loop.execute(new 1(this, promise));
        }
        return promise;
    }

    private void shutdownOutput0(ChannelPromise promise) {
        try {
            this.socket.shutdownOutput();
            promise.setSuccess();
        } catch (Throwable t) {
            promise.setFailure(t);
        }
    }

    public ChannelFuture shutdownInput(ChannelPromise promise) {
        EventLoop loop = eventLoop();
        if (loop.inEventLoop()) {
            shutdownInput0(promise);
        } else {
            loop.execute(new 2(this, promise));
        }
        return promise;
    }

    private void shutdownInput0(ChannelPromise promise) {
        try {
            this.socket.shutdownInput();
            promise.setSuccess();
        } catch (Throwable t) {
            promise.setFailure(t);
        }
    }

    public ChannelFuture shutdown(ChannelPromise promise) {
        EventLoop loop = eventLoop();
        if (loop.inEventLoop()) {
            shutdown0(promise);
        } else {
            loop.execute(new 3(this, promise));
        }
        return promise;
    }

    private void shutdown0(ChannelPromise promise) {
        Throwable cause = null;
        try {
            this.socket.shutdownOutput();
        } catch (Throwable t) {
            cause = t;
        }
        try {
            this.socket.shutdownInput();
            if (cause == null) {
                promise.setSuccess();
            } else {
                promise.setFailure(cause);
            }
        } catch (Throwable t2) {
            if (cause == null) {
                promise.setFailure(t2);
                return;
            }
            logger.debug("Exception suppressed because a_isRightVersion previous exception occurred.", t2);
            promise.setFailure(cause);
        }
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    protected SocketAddress localAddress0() {
        return this.socket.getLocalSocketAddress();
    }

    protected SocketAddress remoteAddress0() {
        return this.socket.getRemoteSocketAddress();
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        this.socket.bind(localAddress);
    }

    protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            this.socket.bind(localAddress);
        }
        try {
            this.socket.connect(remoteAddress, config().getConnectTimeoutMillis());
            activate(this.socket.getInputStream(), this.socket.getOutputStream());
            if (!true) {
                doClose();
            }
        } catch (SocketTimeoutException e) {
            ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
            cause.setStackTrace(e.getStackTrace());
            throw cause;
        } catch (Throwable th) {
            if (!false) {
                doClose();
            }
        }
    }

    protected void doDisconnect() throws Exception {
        doClose();
    }

    protected void doClose() throws Exception {
        this.socket.close();
    }

    protected boolean checkInputShutdown() {
        if (!isInputShutdown()) {
            return false;
        }
        try {
            Thread.sleep((long) config().getSoTimeout());
        } catch (Throwable th) {
        }
        return true;
    }

    @Deprecated
    protected void setReadPending(boolean readPending) {
        super.setReadPending(readPending);
    }

    final void clearReadPending0() {
        clearReadPending();
    }
}
