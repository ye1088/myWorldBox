package io.netty.channel.socket.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.MembershipKey;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class NioDatagramChannel extends AbstractNioMessageChannel implements DatagramChannel {
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static final String EXPECTED_TYPES = (" (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(SocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')');
    private static final ChannelMetadata METADATA = new ChannelMetadata(true);
    private final DatagramChannelConfig config;
    private Map<InetAddress, List<MembershipKey>> memberships;

    private static java.nio.channels.DatagramChannel newSocket(SelectorProvider provider) {
        try {
            return provider.openDatagramChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    private static java.nio.channels.DatagramChannel newSocket(SelectorProvider provider, InternetProtocolFamily ipFamily) {
        if (ipFamily == null) {
            return newSocket(provider);
        }
        checkJavaVersion();
        try {
            return provider.openDatagramChannel(ProtocolFamilyConverter.convert(ipFamily));
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    private static void checkJavaVersion() {
        if (PlatformDependent.javaVersion() < 7) {
            throw new UnsupportedOperationException("Only supported on java 7+.");
        }
    }

    public NioDatagramChannel() {
        this(newSocket(DEFAULT_SELECTOR_PROVIDER));
    }

    public NioDatagramChannel(SelectorProvider provider) {
        this(newSocket(provider));
    }

    public NioDatagramChannel(InternetProtocolFamily ipFamily) {
        this(newSocket(DEFAULT_SELECTOR_PROVIDER, ipFamily));
    }

    public NioDatagramChannel(SelectorProvider provider, InternetProtocolFamily ipFamily) {
        this(newSocket(provider, ipFamily));
    }

    public NioDatagramChannel(java.nio.channels.DatagramChannel socket) {
        super(null, socket, 1);
        this.config = new NioDatagramChannelConfig(this, socket);
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    public DatagramChannelConfig config() {
        return this.config;
    }

    public boolean isActive() {
        java.nio.channels.DatagramChannel ch = javaChannel();
        return ch.isOpen() && ((((Boolean) this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || ch.socket().isBound());
    }

    public boolean isConnected() {
        return javaChannel().isConnected();
    }

    protected java.nio.channels.DatagramChannel javaChannel() {
        return (java.nio.channels.DatagramChannel) super.javaChannel();
    }

    protected SocketAddress localAddress0() {
        return javaChannel().socket().getLocalSocketAddress();
    }

    protected SocketAddress remoteAddress0() {
        return javaChannel().socket().getRemoteSocketAddress();
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        doBind0(localAddress);
    }

    private void doBind0(SocketAddress localAddress) throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            javaChannel().bind(localAddress);
        } else {
            javaChannel().socket().bind(localAddress);
        }
    }

    protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            doBind0(localAddress);
        }
        boolean success = false;
        try {
            javaChannel().connect(remoteAddress);
            success = true;
            return true;
        } finally {
            if (!success) {
                doClose();
            }
        }
    }

    protected void doFinishConnect() throws Exception {
        throw new Error();
    }

    protected void doDisconnect() throws Exception {
        javaChannel().disconnect();
    }

    protected void doClose() throws Exception {
        javaChannel().close();
    }

    protected int doReadMessages(List<Object> buf) throws Exception {
        int i;
        java.nio.channels.DatagramChannel ch = javaChannel();
        DatagramChannelConfig config = config();
        Handle allocHandle = unsafe().recvBufAllocHandle();
        ByteBuf data = allocHandle.allocate(config.getAllocator());
        allocHandle.attemptedBytesRead(data.writableBytes());
        try {
            ByteBuffer nioData = data.internalNioBuffer(data.writerIndex(), data.writableBytes());
            int pos = nioData.position();
            InetSocketAddress remoteAddress = (InetSocketAddress) ch.receive(nioData);
            if (remoteAddress == null) {
                i = 0;
                if (1 != null) {
                    data.release();
                }
            } else {
                allocHandle.lastBytesRead(nioData.position() - pos);
                buf.add(new DatagramPacket(data.writerIndex(data.writerIndex() + allocHandle.lastBytesRead()), localAddress(), remoteAddress));
                i = 1;
                if (false) {
                    data.release();
                }
            }
        } catch (Throwable th) {
            if (1 != null) {
                data.release();
            }
        }
        return i;
    }

    protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
        SocketAddress remoteAddress;
        ByteBuf data;
        if (msg instanceof AddressedEnvelope) {
            AddressedEnvelope<ByteBuf, SocketAddress> envelope = (AddressedEnvelope) msg;
            remoteAddress = envelope.recipient();
            data = (ByteBuf) envelope.content();
        } else {
            data = (ByteBuf) msg;
            remoteAddress = null;
        }
        int dataLen = data.readableBytes();
        if (dataLen == 0) {
            return true;
        }
        int writtenBytes;
        ByteBuffer nioData = data.internalNioBuffer(data.readerIndex(), dataLen);
        if (remoteAddress != null) {
            writtenBytes = javaChannel().send(nioData, remoteAddress);
        } else {
            writtenBytes = javaChannel().write(nioData);
        }
        if (writtenBytes <= 0) {
            return false;
        }
        return true;
    }

    protected Object filterOutboundMessage(Object msg) {
        ByteBuf content;
        if (msg instanceof DatagramPacket) {
            DatagramPacket p = (DatagramPacket) msg;
            content = (ByteBuf) p.content();
            if (isSingleDirectBuffer(content)) {
                return p;
            }
            return new DatagramPacket(newDirectBuffer(p, content), (InetSocketAddress) p.recipient());
        } else if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;
            if (isSingleDirectBuffer(buf)) {
                return buf;
            }
            return newDirectBuffer(buf);
        } else {
            if (msg instanceof AddressedEnvelope) {
                AddressedEnvelope<Object, SocketAddress> e = (AddressedEnvelope) msg;
                if (e.content() instanceof ByteBuf) {
                    content = (ByteBuf) e.content();
                    if (isSingleDirectBuffer(content)) {
                        return e;
                    }
                    return new DefaultAddressedEnvelope(newDirectBuffer(e, content), e.recipient());
                }
            }
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
        }
    }

    private static boolean isSingleDirectBuffer(ByteBuf buf) {
        return buf.isDirect() && buf.nioBufferCount() == 1;
    }

    protected boolean continueOnWriteError() {
        return true;
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
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

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public io.netty.channel.ChannelFuture joinGroup(java.net.InetAddress r8, java.net.NetworkInterface r9, java.net.InetAddress r10, io.netty.channel.ChannelPromise r11) {
        /*
        r7 = this;
        checkJavaVersion();
        if (r8 != 0) goto L_0x000e;
    L_0x0005:
        r5 = new java.lang.NullPointerException;
        r6 = "multicastAddress";
        r5.<init>(r6);
        throw r5;
    L_0x000e:
        if (r9 != 0) goto L_0x0019;
    L_0x0010:
        r5 = new java.lang.NullPointerException;
        r6 = "networkInterface";
        r5.<init>(r6);
        throw r5;
    L_0x0019:
        if (r10 != 0) goto L_0x0045;
    L_0x001b:
        r5 = r7.javaChannel();	 Catch:{ Throwable -> 0x005d }
        r2 = r5.join(r8, r9);	 Catch:{ Throwable -> 0x005d }
    L_0x0023:
        monitor-enter(r7);	 Catch:{ Throwable -> 0x005d }
        r3 = 0;
        r5 = r7.memberships;	 Catch:{ all -> 0x005a }
        if (r5 != 0) goto L_0x004e;
    L_0x0029:
        r5 = new java.util.HashMap;	 Catch:{ all -> 0x005a }
        r5.<init>();	 Catch:{ all -> 0x005a }
        r7.memberships = r5;	 Catch:{ all -> 0x005a }
        r4 = r3;
    L_0x0031:
        if (r4 != 0) goto L_0x0065;
    L_0x0033:
        r3 = new java.util.ArrayList;	 Catch:{ all -> 0x0062 }
        r3.<init>();	 Catch:{ all -> 0x0062 }
        r5 = r7.memberships;	 Catch:{ all -> 0x005a }
        r5.put(r8, r3);	 Catch:{ all -> 0x005a }
    L_0x003d:
        r3.add(r2);	 Catch:{ all -> 0x005a }
        monitor-exit(r7);	 Catch:{ all -> 0x005a }
        r11.setSuccess();	 Catch:{ Throwable -> 0x005d }
    L_0x0044:
        return r11;
    L_0x0045:
        r5 = r7.javaChannel();	 Catch:{ Throwable -> 0x005d }
        r2 = r5.join(r8, r9, r10);	 Catch:{ Throwable -> 0x005d }
        goto L_0x0023;
    L_0x004e:
        r5 = r7.memberships;	 Catch:{ all -> 0x005a }
        r5 = r5.get(r8);	 Catch:{ all -> 0x005a }
        r0 = r5;
        r0 = (java.util.List) r0;	 Catch:{ all -> 0x005a }
        r3 = r0;
        r4 = r3;
        goto L_0x0031;
    L_0x005a:
        r5 = move-exception;
    L_0x005b:
        monitor-exit(r7);	 Catch:{ all -> 0x005a }
        throw r5;	 Catch:{ Throwable -> 0x005d }
    L_0x005d:
        r1 = move-exception;
        r11.setFailure(r1);
        goto L_0x0044;
    L_0x0062:
        r5 = move-exception;
        r3 = r4;
        goto L_0x005b;
    L_0x0065:
        r3 = r4;
        goto L_0x003d;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.socket.nio.NioDatagramChannel.joinGroup(java.net.InetAddress, java.net.NetworkInterface, java.net.InetAddress, io.netty.channel.ChannelPromise):io.netty.channel.ChannelFuture");
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
        checkJavaVersion();
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        } else {
            synchronized (this) {
                if (this.memberships != null) {
                    List<MembershipKey> keys = (List) this.memberships.get(multicastAddress);
                    if (keys != null) {
                        Iterator<MembershipKey> keyIt = keys.iterator();
                        while (keyIt.hasNext()) {
                            MembershipKey key = (MembershipKey) keyIt.next();
                            if (networkInterface.equals(key.networkInterface()) && ((source == null && key.sourceAddress() == null) || (source != null && source.equals(key.sourceAddress())))) {
                                key.drop();
                                keyIt.remove();
                            }
                        }
                        if (keys.isEmpty()) {
                            this.memberships.remove(multicastAddress);
                        }
                    }
                }
            }
            promise.setSuccess();
            return promise;
        }
    }

    public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
        return block(multicastAddress, networkInterface, sourceToBlock, newPromise());
    }

    public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
        checkJavaVersion();
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (sourceToBlock == null) {
            throw new NullPointerException("sourceToBlock");
        } else if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        } else {
            synchronized (this) {
                if (this.memberships != null) {
                    for (MembershipKey key : (List) this.memberships.get(multicastAddress)) {
                        if (networkInterface.equals(key.networkInterface())) {
                            try {
                                key.block(sourceToBlock);
                            } catch (IOException e) {
                                promise.setFailure(e);
                            }
                        }
                    }
                }
            }
            promise.setSuccess();
            return promise;
        }
    }

    public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
        return block(multicastAddress, sourceToBlock, newPromise());
    }

    public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
        try {
            promise = block(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), sourceToBlock, promise);
        } catch (SocketException e) {
            promise.setFailure(e);
        }
        return promise;
    }

    @Deprecated
    protected void setReadPending(boolean readPending) {
        super.setReadPending(readPending);
    }

    void clearReadPending0() {
        clearReadPending();
    }
}
