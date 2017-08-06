package io.netty.channel.sctp.nio;

import com.sun.nio.sctp.Association;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.NotificationHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.sctp.DefaultSctpChannelConfig;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.sctp.SctpChannelConfig;
import io.netty.channel.sctp.SctpMessage;
import io.netty.channel.sctp.SctpNotificationHandler;
import io.netty.channel.sctp.SctpServerChannel;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class NioSctpChannel extends AbstractNioMessageChannel implements SctpChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioSctpChannel.class);
    private final SctpChannelConfig config;
    private final NotificationHandler<?> notificationHandler;

    private final class NioSctpChannelConfig extends DefaultSctpChannelConfig {
        private NioSctpChannelConfig(NioSctpChannel channel, com.sun.nio.sctp.SctpChannel javaChannel) {
            super(channel, javaChannel);
        }

        protected void autoReadCleared() {
            NioSctpChannel.this.clearReadPending();
        }
    }

    private static com.sun.nio.sctp.SctpChannel newSctpChannel() {
        try {
            return com.sun.nio.sctp.SctpChannel.open();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a_isRightVersion sctp channel.", e);
        }
    }

    public NioSctpChannel() {
        this(newSctpChannel());
    }

    public NioSctpChannel(com.sun.nio.sctp.SctpChannel sctpChannel) {
        this(null, sctpChannel);
    }

    public NioSctpChannel(Channel parent, com.sun.nio.sctp.SctpChannel sctpChannel) {
        super(parent, sctpChannel, 1);
        try {
            sctpChannel.configureBlocking(false);
            this.config = new NioSctpChannelConfig(this, sctpChannel);
            this.notificationHandler = new SctpNotificationHandler(this);
        } catch (IOException e) {
            try {
                sctpChannel.close();
            } catch (IOException e2) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Failed to close a_isRightVersion partially initialized sctp channel.", e2);
                }
            }
            throw new ChannelException("Failed to enter non-blocking mode.", e);
        }
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    public SctpServerChannel parent() {
        return (SctpServerChannel) super.parent();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public Association association() {
        try {
            return javaChannel().association();
        } catch (IOException e) {
            return null;
        }
    }

    public Set<InetSocketAddress> allLocalAddresses() {
        try {
            Set<SocketAddress> allLocalAddresses = javaChannel().getAllLocalAddresses();
            Set<InetSocketAddress> linkedHashSet = new LinkedHashSet(allLocalAddresses.size());
            for (SocketAddress socketAddress : allLocalAddresses) {
                linkedHashSet.add((InetSocketAddress) socketAddress);
            }
            return linkedHashSet;
        } catch (Throwable th) {
            return Collections.emptySet();
        }
    }

    public SctpChannelConfig config() {
        return this.config;
    }

    public Set<InetSocketAddress> allRemoteAddresses() {
        try {
            Set<SocketAddress> allLocalAddresses = javaChannel().getRemoteAddresses();
            Set<InetSocketAddress> hashSet = new HashSet(allLocalAddresses.size());
            for (SocketAddress socketAddress : allLocalAddresses) {
                hashSet.add((InetSocketAddress) socketAddress);
            }
            return hashSet;
        } catch (Throwable th) {
            return Collections.emptySet();
        }
    }

    protected com.sun.nio.sctp.SctpChannel javaChannel() {
        return (com.sun.nio.sctp.SctpChannel) super.javaChannel();
    }

    public boolean isActive() {
        return javaChannel().isOpen() && association() != null;
    }

    protected SocketAddress localAddress0() {
        try {
            Iterator<SocketAddress> i = javaChannel().getAllLocalAddresses().iterator();
            if (i.hasNext()) {
                return (SocketAddress) i.next();
            }
        } catch (IOException e) {
        }
        return null;
    }

    protected SocketAddress remoteAddress0() {
        try {
            Iterator<SocketAddress> i = javaChannel().getRemoteAddresses().iterator();
            if (i.hasNext()) {
                return (SocketAddress) i.next();
            }
        } catch (IOException e) {
        }
        return null;
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        javaChannel().bind(localAddress);
    }

    protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            javaChannel().bind(localAddress);
        }
        boolean success = false;
        try {
            boolean connected = javaChannel().connect(remoteAddress);
            if (!connected) {
                selectionKey().interestOps(8);
            }
            success = true;
            return connected;
        } finally {
            if (!success) {
                doClose();
            }
        }
    }

    protected void doFinishConnect() throws Exception {
        if (!javaChannel().finishConnect()) {
            throw new Error();
        }
    }

    protected void doDisconnect() throws Exception {
        doClose();
    }

    protected void doClose() throws Exception {
        javaChannel().close();
    }

    protected int doReadMessages(List<Object> buf) throws Exception {
        int i;
        com.sun.nio.sctp.SctpChannel ch = javaChannel();
        Handle allocHandle = unsafe().recvBufAllocHandle();
        ByteBuf buffer = allocHandle.allocate(config().getAllocator());
        try {
            ByteBuffer data = buffer.internalNioBuffer(buffer.writerIndex(), buffer.writableBytes());
            int pos = data.position();
            MessageInfo messageInfo = ch.receive(data, null, this.notificationHandler);
            if (messageInfo == null) {
                i = 0;
                if (1 != null) {
                    buffer.release();
                }
            } else {
                allocHandle.lastBytesRead(data.position() - pos);
                buf.add(new SctpMessage(messageInfo, buffer.writerIndex(buffer.writerIndex() + allocHandle.lastBytesRead())));
                i = 1;
                if (false) {
                    buffer.release();
                }
            }
        } catch (Throwable th) {
            if (1 != null) {
                buffer.release();
            }
        }
        return i;
    }

    protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
        SctpMessage packet = (SctpMessage) msg;
        ByteBuf data = packet.content();
        int dataLen = data.readableBytes();
        if (dataLen == 0) {
            return true;
        }
        boolean needsCopy;
        ByteBuffer nioData;
        ByteBufAllocator alloc = alloc();
        if (data.nioBufferCount() != 1) {
            needsCopy = true;
        } else {
            needsCopy = false;
        }
        if (!(needsCopy || data.isDirect() || !alloc.isDirectBufferPooled())) {
            needsCopy = true;
        }
        if (needsCopy) {
            nioData = alloc.directBuffer(dataLen).writeBytes(data).nioBuffer();
        } else {
            nioData = data.nioBuffer();
        }
        MessageInfo mi = MessageInfo.createOutgoing(association(), null, packet.streamIdentifier());
        mi.payloadProtocolID(packet.protocolIdentifier());
        mi.streamNumber(packet.streamIdentifier());
        mi.unordered(packet.isUnordered());
        if (javaChannel().send(nioData, mi) <= 0) {
            return false;
        }
        return true;
    }

    protected final Object filterOutboundMessage(Object msg) throws Exception {
        if (msg instanceof SctpMessage) {
            SctpMessage m = (SctpMessage) msg;
            ByteBuf buf = m.content();
            return (buf.isDirect() && buf.nioBufferCount() == 1) ? m : new SctpMessage(m.protocolIdentifier(), m.streamIdentifier(), m.isUnordered(), newDirectBuffer(m, buf));
        } else {
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + " (expected: " + StringUtil.simpleClassName(SctpMessage.class));
        }
    }

    public ChannelFuture bindAddress(InetAddress localAddress) {
        return bindAddress(localAddress, newPromise());
    }

    public ChannelFuture bindAddress(final InetAddress localAddress, final ChannelPromise promise) {
        if (eventLoop().inEventLoop()) {
            try {
                javaChannel().bindAddress(localAddress);
                promise.setSuccess();
            } catch (Throwable t) {
                promise.setFailure(t);
            }
        } else {
            eventLoop().execute(new Runnable() {
                public void run() {
                    NioSctpChannel.this.bindAddress(localAddress, promise);
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
                javaChannel().unbindAddress(localAddress);
                promise.setSuccess();
            } catch (Throwable t) {
                promise.setFailure(t);
            }
        } else {
            eventLoop().execute(new Runnable() {
                public void run() {
                    NioSctpChannel.this.unbindAddress(localAddress, promise);
                }
            });
        }
        return promise;
    }
}
