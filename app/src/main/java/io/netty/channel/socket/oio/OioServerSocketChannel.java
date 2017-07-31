package io.netty.channel.socket.oio;

import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.oio.AbstractOioMessageChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OioServerSocketChannel extends AbstractOioMessageChannel implements ServerSocketChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 1);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioServerSocketChannel.class);
    private final OioServerSocketChannelConfig config;
    final Lock shutdownLock;
    final ServerSocket socket;

    private static ServerSocket newServerSocket() {
        try {
            return new ServerSocket();
        } catch (IOException e) {
            throw new ChannelException("failed to create a server socket", e);
        }
    }

    public OioServerSocketChannel() {
        this(newServerSocket());
    }

    public OioServerSocketChannel(ServerSocket socket) {
        super(null);
        this.shutdownLock = new ReentrantLock();
        if (socket == null) {
            throw new NullPointerException("socket");
        }
        try {
            socket.setSoTimeout(1000);
            if (!true) {
                try {
                    socket.close();
                } catch (IOException e) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Failed to close a partially initialized socket.", e);
                    }
                }
            }
            this.socket = socket;
            this.config = new DefaultOioServerSocketChannelConfig(this, socket);
        } catch (IOException e2) {
            throw new ChannelException("Failed to set the server socket timeout.", e2);
        } catch (Throwable th) {
            if (!false) {
                try {
                    socket.close();
                } catch (IOException e22) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Failed to close a partially initialized socket.", e22);
                    }
                }
            }
        }
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public OioServerSocketChannelConfig config() {
        return this.config;
    }

    public InetSocketAddress remoteAddress() {
        return null;
    }

    public boolean isOpen() {
        return !this.socket.isClosed();
    }

    public boolean isActive() {
        return isOpen() && this.socket.isBound();
    }

    protected SocketAddress localAddress0() {
        return this.socket.getLocalSocketAddress();
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        this.socket.bind(localAddress, this.config.getBacklog());
    }

    protected void doClose() throws Exception {
        this.socket.close();
    }

    protected int doReadMessages(List<Object> buf) throws Exception {
        if (this.socket.isClosed()) {
            return -1;
        }
        try {
            Socket s = this.socket.accept();
            try {
                buf.add(new OioSocketChannel(this, s));
                return 1;
            } catch (Throwable t2) {
                logger.warn("Failed to close a socket.", t2);
            }
        } catch (SocketTimeoutException e) {
        }
        return 0;
    }

    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected Object filterOutboundMessage(Object msg) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected SocketAddress remoteAddress0() {
        return null;
    }

    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    protected void setReadPending(boolean readPending) {
        super.setReadPending(readPending);
    }

    final void clearReadPending0() {
        super.clearReadPending();
    }
}
