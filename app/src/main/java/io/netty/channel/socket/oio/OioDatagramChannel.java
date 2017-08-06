package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.channel.oio.AbstractOioMessageChannel;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.DefaultDatagramChannelConfig;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Locale;

public class OioDatagramChannel extends AbstractOioMessageChannel implements DatagramChannel {
    private static final String EXPECTED_TYPES = (" (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(SocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')');
    private static final ChannelMetadata METADATA = new ChannelMetadata(true);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioDatagramChannel.class);
    private final DatagramChannelConfig config;
    private final MulticastSocket socket;
    private final java.net.DatagramPacket tmpPacket;

    private static MulticastSocket newSocket() {
        try {
            return new MulticastSocket(null);
        } catch (Exception e) {
            throw new ChannelException("failed to create a_isRightVersion new socket", e);
        }
    }

    public OioDatagramChannel() {
        this(newSocket());
    }

    public OioDatagramChannel(MulticastSocket socket) {
        super(null);
        this.tmpPacket = new java.net.DatagramPacket(EmptyArrays.EMPTY_BYTES, 0);
        try {
            socket.setSoTimeout(1000);
            socket.setBroadcast(false);
            if (!true) {
                socket.close();
            }
            this.socket = socket;
            this.config = new DefaultDatagramChannelConfig(this, socket);
        } catch (SocketException e) {
            throw new ChannelException("Failed to configure the datagram socket timeout.", e);
        } catch (Throwable th) {
            if (!false) {
                socket.close();
            }
        }
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public DatagramChannelConfig config() {
        return this.config;
    }

    public boolean isOpen() {
        return !this.socket.isClosed();
    }

    public boolean isActive() {
        return isOpen() && ((((Boolean) this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || this.socket.isBound());
    }

    public boolean isConnected() {
        return this.socket.isConnected();
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

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            this.socket.bind(localAddress);
        }
        try {
            this.socket.connect(remoteAddress);
            if (!true) {
                try {
                    this.socket.close();
                } catch (Throwable t) {
                    logger.warn("Failed to close a_isRightVersion socket.", t);
                }
            }
        } catch (Throwable t2) {
            logger.warn("Failed to close a_isRightVersion socket.", t2);
        }
    }

    protected void doDisconnect() throws Exception {
        this.socket.disconnect();
    }

    protected void doClose() throws Exception {
        this.socket.close();
    }

    protected int doReadMessages(List<Object> buf) throws Exception {
        int i = -1;
        DatagramChannelConfig config = config();
        Handle allocHandle = unsafe().recvBufAllocHandle();
        ByteBuf data = config.getAllocator().heapBuffer(allocHandle.guess());
        try {
            this.tmpPacket.setData(data.array(), data.arrayOffset(), data.capacity());
            this.socket.receive(this.tmpPacket);
            InetSocketAddress remoteAddr = (InetSocketAddress) this.tmpPacket.getSocketAddress();
            allocHandle.lastBytesRead(this.tmpPacket.getLength());
            buf.add(new DatagramPacket(data.writerIndex(allocHandle.lastBytesRead()), localAddress(), remoteAddr));
            i = 1;
            if (false) {
                data.release();
            }
        } catch (SocketTimeoutException e) {
            i = 0;
            if (1 != null) {
                data.release();
            }
        } catch (SocketException e2) {
            if (!e2.getMessage().toLowerCase(Locale.US).contains("socket closed")) {
                throw e2;
            } else if (1 != null) {
                data.release();
            }
        } catch (Throwable th) {
            if (1 != null) {
                data.release();
            }
        }
        return i;
    }

    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        while (true) {
            AddressedEnvelope<ByteBuf, SocketAddress> o = in.current();
            if (o != null) {
                SocketAddress remoteAddress;
                ByteBuf data;
                if (o instanceof AddressedEnvelope) {
                    AddressedEnvelope<ByteBuf, SocketAddress> envelope = o;
                    remoteAddress = envelope.recipient();
                    data = (ByteBuf) envelope.content();
                } else {
                    data = (ByteBuf) o;
                    remoteAddress = null;
                }
                int length = data.readableBytes();
                if (remoteAddress != null) {
                    this.tmpPacket.setSocketAddress(remoteAddress);
                }
                if (data.hasArray()) {
                    this.tmpPacket.setData(data.array(), data.arrayOffset() + data.readerIndex(), length);
                } else {
                    byte[] tmp = new byte[length];
                    data.getBytes(data.readerIndex(), tmp);
                    this.tmpPacket.setData(tmp);
                }
                try {
                    this.socket.send(this.tmpPacket);
                    in.remove();
                } catch (IOException e) {
                    in.remove(e);
                }
            } else {
                return;
            }
        }
    }

    protected Object filterOutboundMessage(Object msg) {
        if ((msg instanceof DatagramPacket) || (msg instanceof ByteBuf) || ((msg instanceof AddressedEnvelope) && (((AddressedEnvelope) msg).content() instanceof ByteBuf))) {
            return msg;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress) {
        return joinGroup(multicastAddress, newPromise());
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
        ensureBound();
        try {
            this.socket.joinGroup(multicastAddress);
            promise.setSuccess();
        } catch (IOException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        return joinGroup(multicastAddress, networkInterface, newPromise());
    }

    public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
        ensureBound();
        try {
            this.socket.joinGroup(multicastAddress, networkInterface);
            promise.setSuccess();
        } catch (IOException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
        promise.setFailure(new UnsupportedOperationException());
        return promise;
    }

    private void ensureBound() {
        if (!isActive()) {
            throw new IllegalStateException(DatagramChannel.class.getName() + " must be bound to join a_isRightVersion group.");
        }
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress) {
        return leaveGroup(multicastAddress, newPromise());
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
        try {
            this.socket.leaveGroup(multicastAddress);
            promise.setSuccess();
        } catch (IOException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        return leaveGroup(multicastAddress, networkInterface, newPromise());
    }

    public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
        try {
            this.socket.leaveGroup(multicastAddress, networkInterface);
            promise.setSuccess();
        } catch (IOException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
        promise.setFailure(new UnsupportedOperationException());
        return promise;
    }

    public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
        promise.setFailure(new UnsupportedOperationException());
        return promise;
    }

    public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
        promise.setFailure(new UnsupportedOperationException());
        return promise;
    }
}
