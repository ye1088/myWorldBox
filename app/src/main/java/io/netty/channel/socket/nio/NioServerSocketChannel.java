package io.netty.channel.socket.nio;

import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.socket.DefaultServerSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.List;

public class NioServerSocketChannel extends AbstractNioMessageChannel implements ServerSocketChannel {
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);
    private final ServerSocketChannelConfig config;

    private final class NioServerSocketChannelConfig extends DefaultServerSocketChannelConfig {
        private NioServerSocketChannelConfig(NioServerSocketChannel channel, ServerSocket javaSocket) {
            super(channel, javaSocket);
        }

        protected void autoReadCleared() {
            NioServerSocketChannel.this.clearReadPending();
        }
    }

    private static java.nio.channels.ServerSocketChannel newSocket(SelectorProvider provider) {
        try {
            return provider.openServerSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a server socket.", e);
        }
    }

    public NioServerSocketChannel() {
        this(newSocket(DEFAULT_SELECTOR_PROVIDER));
    }

    public NioServerSocketChannel(SelectorProvider provider) {
        this(newSocket(provider));
    }

    public NioServerSocketChannel(java.nio.channels.ServerSocketChannel channel) {
        super(null, channel, 16);
        this.config = new NioServerSocketChannelConfig(this, javaChannel().socket());
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public ServerSocketChannelConfig config() {
        return this.config;
    }

    public boolean isActive() {
        return javaChannel().socket().isBound();
    }

    public InetSocketAddress remoteAddress() {
        return null;
    }

    protected java.nio.channels.ServerSocketChannel javaChannel() {
        return (java.nio.channels.ServerSocketChannel) super.javaChannel();
    }

    protected SocketAddress localAddress0() {
        return javaChannel().socket().getLocalSocketAddress();
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            javaChannel().bind(localAddress, this.config.getBacklog());
        } else {
            javaChannel().socket().bind(localAddress, this.config.getBacklog());
        }
    }

    protected void doClose() throws Exception {
        javaChannel().close();
    }

    protected int doReadMessages(List<Object> buf) throws Exception {
        SocketChannel ch = javaChannel().accept();
        if (ch != null) {
            try {
                buf.add(new NioSocketChannel(this, ch));
                return 1;
            } catch (Throwable t2) {
                logger.warn("Failed to close a socket.", t2);
            }
        }
        return 0;
    }

    protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected void doFinishConnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    protected SocketAddress remoteAddress0() {
        return null;
    }

    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected final Object filterOutboundMessage(Object msg) throws Exception {
        throw new UnsupportedOperationException();
    }
}
