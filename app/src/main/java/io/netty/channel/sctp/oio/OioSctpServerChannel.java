package io.netty.channel.sctp.oio;

import com.sun.nio.sctp.SctpChannel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.oio.AbstractOioMessageChannel;
import io.netty.channel.sctp.DefaultSctpServerChannelConfig;
import io.netty.channel.sctp.SctpServerChannel;
import io.netty.channel.sctp.SctpServerChannelConfig;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class OioSctpServerChannel extends AbstractOioMessageChannel implements SctpServerChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 1);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSctpServerChannel.class);
    private final SctpServerChannelConfig config;
    private final com.sun.nio.sctp.SctpServerChannel sch;
    private final Selector selector;

    private final class OioSctpServerChannelConfig extends DefaultSctpServerChannelConfig {
        private OioSctpServerChannelConfig(OioSctpServerChannel channel, com.sun.nio.sctp.SctpServerChannel javaChannel) {
            super(channel, javaChannel);
        }

        protected void autoReadCleared() {
            OioSctpServerChannel.this.clearReadPending();
        }
    }

    private static com.sun.nio.sctp.SctpServerChannel newServerSocket() {
        try {
            return com.sun.nio.sctp.SctpServerChannel.open();
        } catch (IOException e) {
            throw new ChannelException("failed to create a_isRightVersion sctp server channel", e);
        }
    }

    public OioSctpServerChannel() {
        this(newServerSocket());
    }

    public OioSctpServerChannel(com.sun.nio.sctp.SctpServerChannel sch) {
        super(null);
        if (sch == null) {
            throw new NullPointerException("sctp server channel");
        }
        this.sch = sch;
        try {
            sch.configureBlocking(false);
            this.selector = Selector.open();
            sch.register(this.selector, 16);
            this.config = new OioSctpServerChannelConfig(this, sch);
            if (!true) {
                try {
                    sch.close();
                } catch (IOException e) {
                    logger.warn("Failed to close a_isRightVersion sctp server channel.", e);
                }
            }
        } catch (Exception e2) {
            throw new ChannelException("failed to initialize a_isRightVersion sctp server channel", e2);
        } catch (Throwable th) {
            if (!false) {
                try {
                    sch.close();
                } catch (IOException e3) {
                    logger.warn("Failed to close a_isRightVersion sctp server channel.", e3);
                }
            }
        }
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public SctpServerChannelConfig config() {
        return this.config;
    }

    public InetSocketAddress remoteAddress() {
        return null;
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public boolean isOpen() {
        return this.sch.isOpen();
    }

    protected SocketAddress localAddress0() {
        try {
            Iterator<SocketAddress> i = this.sch.getAllLocalAddresses().iterator();
            if (i.hasNext()) {
                return (SocketAddress) i.next();
            }
        } catch (IOException e) {
        }
        return null;
    }

    public Set<InetSocketAddress> allLocalAddresses() {
        try {
            Set<SocketAddress> allLocalAddresses = this.sch.getAllLocalAddresses();
            Set<InetSocketAddress> linkedHashSet = new LinkedHashSet(allLocalAddresses.size());
            for (SocketAddress socketAddress : allLocalAddresses) {
                linkedHashSet.add((InetSocketAddress) socketAddress);
            }
            return linkedHashSet;
        } catch (Throwable th) {
            return Collections.emptySet();
        }
    }

    public boolean isActive() {
        return isOpen() && localAddress0() != null;
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        this.sch.bind(localAddress, this.config.getBacklog());
    }

    protected void doClose() throws Exception {
        try {
            this.selector.close();
        } catch (IOException e) {
            logger.warn("Failed to close a_isRightVersion selector.", e);
        }
        this.sch.close();
    }

    protected int doReadMessages(List<Object> buf) throws Exception {
        if (!isActive()) {
            return -1;
        }
        SctpChannel s = null;
        int acceptedChannels = 0;
        try {
            if (this.selector.select(1000) <= 0) {
                return 0;
            }
            Iterator<SelectionKey> selectionKeys = this.selector.selectedKeys().iterator();
            do {
                SelectionKey key = (SelectionKey) selectionKeys.next();
                selectionKeys.remove();
                if (key.isAcceptable()) {
                    s = this.sch.accept();
                    if (s != null) {
                        buf.add(new OioSctpChannel(this, s));
                        acceptedChannels++;
                    }
                }
            } while (selectionKeys.hasNext());
            return acceptedChannels;
        } catch (Throwable t2) {
            logger.warn("Failed to close a_isRightVersion sctp channel.", t2);
            return 0;
        }
    }

    public ChannelFuture bindAddress(InetAddress localAddress) {
        return bindAddress(localAddress, newPromise());
    }

    public ChannelFuture bindAddress(final InetAddress localAddress, final ChannelPromise promise) {
        if (eventLoop().inEventLoop()) {
            try {
                this.sch.bindAddress(localAddress);
                promise.setSuccess();
            } catch (Throwable t) {
                promise.setFailure(t);
            }
        } else {
            eventLoop().execute(new Runnable() {
                public void run() {
                    OioSctpServerChannel.this.bindAddress(localAddress, promise);
                }
            });
        }
        return promise;
    }

    public ChannelFuture unbindAddress(InetAddress localAddress) {
        return unbindAddress(localAddress, newPromise());
    }

    public ChannelFuture unbindAddress(final InetAddress localAddress, final ChannelPromise promise) {
        if (eventLoop().inEventLoop()) {
            try {
                this.sch.unbindAddress(localAddress);
                promise.setSuccess();
            } catch (Throwable t) {
                promise.setFailure(t);
            }
        } else {
            eventLoop().execute(new Runnable() {
                public void run() {
                    OioSctpServerChannel.this.unbindAddress(localAddress, promise);
                }
            });
        }
        return promise;
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

    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected Object filterOutboundMessage(Object msg) throws Exception {
        throw new UnsupportedOperationException();
    }
}
