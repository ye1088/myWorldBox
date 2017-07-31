package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe;
import io.netty.channel.epoll.NativeDatagramPacketArray.NativeDatagramPacket;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;

public final class EpollDatagramChannel extends AbstractEpollChannel implements DatagramChannel {
    static final /* synthetic */ boolean $assertionsDisabled = (!EpollDatagramChannel.class.desiredAssertionStatus());
    private static final String EXPECTED_TYPES = (" (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(InetSocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')');
    private static final ChannelMetadata METADATA = new ChannelMetadata(true);
    private final EpollDatagramChannelConfig config;
    private volatile boolean connected;
    private volatile InetSocketAddress local;
    private volatile InetSocketAddress remote;

    public /* bridge */ /* synthetic */ boolean isOpen() {
        return super.isOpen();
    }

    public EpollDatagramChannel() {
        super(Socket.newSocketDgram(), Native.EPOLLIN);
        this.config = new EpollDatagramChannelConfig(this);
    }

    @Deprecated
    public EpollDatagramChannel(FileDescriptor fd) {
        this(new Socket(fd.intValue()));
    }

    public EpollDatagramChannel(Socket fd) {
        super(null, fd, Native.EPOLLIN, true);
        this.local = fd.localAddress();
        this.config = new EpollDatagramChannelConfig(this);
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public boolean isActive() {
        return fd().isOpen() && ((((Boolean) this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || this.active);
    }

    public boolean isConnected() {
        return this.connected;
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress) {
        return joinGroup(multicastAddress, newPromise());
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
        try {
            promise = joinGroup(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), null, promise);
        } catch (SocketException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        return joinGroup(multicastAddress, networkInterface, newPromise());
    }

    public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
        return joinGroup(multicastAddress.getAddress(), networkInterface, null, promise);
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
        return joinGroup(multicastAddress, networkInterface, source, newPromise());
    }

    public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        } else {
            promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
            return promise;
        }
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress) {
        return leaveGroup(multicastAddress, newPromise());
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
        try {
            promise = leaveGroup(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), null, promise);
        } catch (SocketException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        return leaveGroup(multicastAddress, networkInterface, newPromise());
    }

    public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
        return leaveGroup(multicastAddress.getAddress(), networkInterface, null, promise);
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
        return leaveGroup(multicastAddress, networkInterface, source, newPromise());
    }

    public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        } else {
            promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
            return promise;
        }
    }

    public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
        return block(multicastAddress, networkInterface, sourceToBlock, newPromise());
    }

    public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (sourceToBlock == null) {
            throw new NullPointerException("sourceToBlock");
        } else if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        } else {
            promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
            return promise;
        }
    }

    public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
        return block(multicastAddress, sourceToBlock, newPromise());
    }

    public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
        try {
            promise = block(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), sourceToBlock, promise);
        } catch (Throwable e) {
            promise.setFailure(e);
        }
        return promise;
    }

    protected AbstractEpollUnsafe newUnsafe() {
        return new EpollDatagramChannelUnsafe(this);
    }

    protected InetSocketAddress localAddress0() {
        return this.local;
    }

    protected InetSocketAddress remoteAddress0() {
        return this.remote;
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        InetSocketAddress addr = (InetSocketAddress) localAddress;
        checkResolvable(addr);
        fd().bind(addr);
        this.local = fd().localAddress();
        this.active = true;
    }

    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        while (true) {
            Object msg = in.current();
            if (msg == null) {
                clearFlag(Native.EPOLLOUT);
                return;
            }
            try {
                int i;
                if (Native.IS_SUPPORTING_SENDMMSG && in.size() > 1) {
                    NativeDatagramPacketArray array = NativeDatagramPacketArray.getInstance(in);
                    int cnt = array.count();
                    if (cnt >= 1) {
                        int offset = 0;
                        NativeDatagramPacket[] packets = array.packets();
                        while (cnt > 0) {
                            int send = Native.sendmmsg(fd().intValue(), packets, offset, cnt);
                            if (send == 0) {
                                setFlag(Native.EPOLLOUT);
                                return;
                            }
                            for (i = 0; i < send; i++) {
                                in.remove();
                            }
                            cnt -= send;
                            offset += send;
                        }
                        continue;
                    }
                }
                boolean done = false;
                for (i = config().getWriteSpinCount() - 1; i >= 0; i--) {
                    if (doWriteMessage(msg)) {
                        done = true;
                        break;
                    }
                }
                if (done) {
                    in.remove();
                } else {
                    setFlag(Native.EPOLLOUT);
                    return;
                }
            } catch (IOException e) {
                in.remove(e);
            }
        }
    }

    private boolean doWriteMessage(Object msg) throws Exception {
        ByteBuf data;
        InetSocketAddress remoteAddress;
        if (msg instanceof AddressedEnvelope) {
            AddressedEnvelope<ByteBuf, InetSocketAddress> envelope = (AddressedEnvelope) msg;
            data = (ByteBuf) envelope.content();
            remoteAddress = (InetSocketAddress) envelope.recipient();
        } else {
            data = (ByteBuf) msg;
            remoteAddress = null;
        }
        if (data.readableBytes() == 0) {
            return true;
        }
        int writtenBytes;
        if (remoteAddress == null) {
            remoteAddress = this.remote;
            if (remoteAddress == null) {
                throw new NotYetConnectedException();
            }
        }
        if (data.hasMemoryAddress()) {
            writtenBytes = fd().sendToAddress(data.memoryAddress(), data.readerIndex(), data.writerIndex(), remoteAddress.getAddress(), remoteAddress.getPort());
        } else if (data instanceof CompositeByteBuf) {
            IovArray array = ((EpollEventLoop) eventLoop()).cleanArray();
            array.add(data);
            int cnt = array.count();
            if ($assertionsDisabled || cnt != 0) {
                writtenBytes = fd().sendToAddresses(array.memoryAddress(0), cnt, remoteAddress.getAddress(), remoteAddress.getPort());
            } else {
                throw new AssertionError();
            }
        } else {
            ByteBuffer nioData = data.internalNioBuffer(data.readerIndex(), data.readableBytes());
            writtenBytes = fd().sendTo(nioData, nioData.position(), nioData.limit(), remoteAddress.getAddress(), remoteAddress.getPort());
        }
        if (writtenBytes > 0) {
            return true;
        }
        return false;
    }

    protected Object filterOutboundMessage(Object msg) {
        ByteBuf content;
        CompositeByteBuf comp;
        if (msg instanceof DatagramPacket) {
            DatagramPacket packet = (DatagramPacket) msg;
            content = (ByteBuf) packet.content();
            if (content.hasMemoryAddress()) {
                return msg;
            }
            if (content.isDirect() && (content instanceof CompositeByteBuf)) {
                comp = (CompositeByteBuf) content;
                if (comp.isDirect() && comp.nioBufferCount() <= Native.IOV_MAX) {
                    return msg;
                }
            }
            return new DatagramPacket(newDirectBuffer(packet, content), (InetSocketAddress) packet.recipient());
        } else if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            if (!buf.hasMemoryAddress() && (PlatformDependent.hasUnsafe() || !buf.isDirect())) {
                if (buf instanceof CompositeByteBuf) {
                    comp = (CompositeByteBuf) buf;
                    if (!comp.isDirect() || comp.nioBufferCount() > Native.IOV_MAX) {
                        buf = newDirectBuffer(buf);
                        if (!($assertionsDisabled || buf.hasMemoryAddress())) {
                            throw new AssertionError();
                        }
                    }
                }
                buf = newDirectBuffer(buf);
                if (!($assertionsDisabled || buf.hasMemoryAddress())) {
                    throw new AssertionError();
                }
            }
            return buf;
        } else {
            if (msg instanceof AddressedEnvelope) {
                AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope) msg;
                if ((e.content() instanceof ByteBuf) && (e.recipient() == null || (e.recipient() instanceof InetSocketAddress))) {
                    content = (ByteBuf) e.content();
                    if (content.hasMemoryAddress()) {
                        return e;
                    }
                    if (content instanceof CompositeByteBuf) {
                        comp = (CompositeByteBuf) content;
                        if (comp.isDirect() && comp.nioBufferCount() <= Native.IOV_MAX) {
                            return e;
                        }
                    }
                    return new DefaultAddressedEnvelope(newDirectBuffer(e, content), (InetSocketAddress) e.recipient());
                }
            }
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
        }
    }

    public EpollDatagramChannelConfig config() {
        return this.config;
    }

    protected void doDisconnect() throws Exception {
        this.connected = false;
    }
}
