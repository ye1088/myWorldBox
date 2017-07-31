package io.netty.channel.udt.nio;

import com.barchart.udt.StatusUDT;
import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.SocketChannelUDT;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.udt.DefaultUdtChannelConfig;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtChannelConfig;
import io.netty.channel.udt.UdtMessage;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

public class NioUdtMessageConnectorChannel extends AbstractNioMessageChannel implements UdtChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioUdtMessageConnectorChannel.class);
    private final UdtChannelConfig config;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$barchart$udt$StatusUDT = new int[StatusUDT.values().length];

        static {
            try {
                $SwitchMap$com$barchart$udt$StatusUDT[StatusUDT.INIT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$barchart$udt$StatusUDT[StatusUDT.OPENED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public NioUdtMessageConnectorChannel() {
        this(TypeUDT.DATAGRAM);
    }

    public NioUdtMessageConnectorChannel(Channel parent, SocketChannelUDT channelUDT) {
        super(parent, channelUDT, 1);
        try {
            channelUDT.configureBlocking(false);
            switch (AnonymousClass1.$SwitchMap$com$barchart$udt$StatusUDT[channelUDT.socketUDT().status().ordinal()]) {
                case 1:
                case 2:
                    this.config = new DefaultUdtChannelConfig(this, channelUDT, true);
                    return;
                default:
                    this.config = new DefaultUdtChannelConfig(this, channelUDT, false);
                    return;
            }
        } catch (Exception e) {
            try {
                channelUDT.close();
            } catch (Exception e2) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Failed to close channel.", e2);
                }
            }
            throw new ChannelException("Failed to configure channel.", e);
        }
    }

    public NioUdtMessageConnectorChannel(SocketChannelUDT channelUDT) {
        this(null, channelUDT);
    }

    public NioUdtMessageConnectorChannel(TypeUDT type) {
        this(NioUdtProvider.newConnectorChannelUDT(type));
    }

    public UdtChannelConfig config() {
        return this.config;
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        javaChannel().bind(localAddress);
    }

    protected void doClose() throws Exception {
        javaChannel().close();
    }

    protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        if (localAddress == null) {
            localAddress = new InetSocketAddress(0);
        }
        doBind(localAddress);
        boolean success = false;
        try {
            boolean connected = javaChannel().connect(remoteAddress);
            if (!connected) {
                selectionKey().interestOps(selectionKey().interestOps() | 8);
            }
            success = true;
            return connected;
        } finally {
            if (!success) {
                doClose();
            }
        }
    }

    protected void doDisconnect() throws Exception {
        doClose();
    }

    protected void doFinishConnect() throws Exception {
        if (javaChannel().finishConnect()) {
            selectionKey().interestOps(selectionKey().interestOps() & -9);
            return;
        }
        throw new Error("Provider error: failed to finish connect. Provider library should be upgraded.");
    }

    protected int doReadMessages(List<Object> buf) throws Exception {
        int maximumMessageSize = this.config.getReceiveBufferSize();
        ByteBuf byteBuf = this.config.getAllocator().directBuffer(maximumMessageSize);
        int receivedMessageSize = byteBuf.writeBytes(javaChannel(), maximumMessageSize);
        if (receivedMessageSize <= 0) {
            byteBuf.release();
            return 0;
        } else if (receivedMessageSize >= maximumMessageSize) {
            javaChannel().close();
            throw new ChannelException("Invalid config : increase receive buffer size to avoid message truncation");
        } else {
            buf.add(new UdtMessage(byteBuf));
            return 1;
        }
    }

    protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
        ByteBuf byteBuf = ((UdtMessage) msg).content();
        int messageSize = byteBuf.readableBytes();
        if (messageSize == 0) {
            return true;
        }
        long writtenBytes;
        if (byteBuf.nioBufferCount() == 1) {
            writtenBytes = (long) javaChannel().write(byteBuf.nioBuffer());
        } else {
            writtenBytes = javaChannel().write(byteBuf.nioBuffers());
        }
        if (writtenBytes > 0 && writtenBytes != ((long) messageSize)) {
            throw new Error("Provider error: failed to write message. Provider library should be upgraded.");
        } else if (writtenBytes <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isActive() {
        SocketChannelUDT channelUDT = javaChannel();
        return channelUDT.isOpen() && channelUDT.isConnectFinished();
    }

    protected SocketChannelUDT javaChannel() {
        return (SocketChannelUDT) super.javaChannel();
    }

    protected SocketAddress localAddress0() {
        return javaChannel().socket().getLocalSocketAddress();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    protected SocketAddress remoteAddress0() {
        return javaChannel().socket().getRemoteSocketAddress();
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }
}
