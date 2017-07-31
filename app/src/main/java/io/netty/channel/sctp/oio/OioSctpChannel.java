package io.netty.channel.sctp.oio;

import com.sun.nio.sctp.Association;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.NotificationHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.oio.AbstractOioMessageChannel;
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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class OioSctpChannel extends AbstractOioMessageChannel implements SctpChannel {
    private static final String EXPECTED_TYPE = (" (expected: " + StringUtil.simpleClassName(SctpMessage.class) + ')');
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSctpChannel.class);
    private final com.sun.nio.sctp.SctpChannel ch;
    private final SctpChannelConfig config;
    private final Selector connectSelector;
    private final NotificationHandler<?> notificationHandler;
    private final Selector readSelector;
    private final Selector writeSelector;

    private final class OioSctpChannelConfig extends DefaultSctpChannelConfig {
        private OioSctpChannelConfig(OioSctpChannel channel, com.sun.nio.sctp.SctpChannel javaChannel) {
            super(channel, javaChannel);
        }

        protected void autoReadCleared() {
            OioSctpChannel.this.clearReadPending();
        }
    }

    protected int doReadMessages(java.util.List<java.lang.Object> r15) throws java.lang.Exception {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(Unknown Source)
	at java.util.HashMap$KeyIterator.next(Unknown Source)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:80)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r14 = this;
        r9 = 0;
        r10 = r14.readSelector;
        r10 = r10.isOpen();
        if (r10 != 0) goto L_0x000b;
    L_0x0009:
        r7 = r9;
    L_0x000a:
        return r7;
    L_0x000b:
        r7 = 0;
        r10 = r14.readSelector;
        r12 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r8 = r10.select(r12);
        if (r8 <= 0) goto L_0x0054;
    L_0x0016:
        r5 = 1;
    L_0x0017:
        if (r5 == 0) goto L_0x000a;
    L_0x0019:
        r9 = r14.readSelector;
        r9 = r9.selectedKeys();
        r9.clear();
        r9 = r14.unsafe();
        r0 = r9.recvBufAllocHandle();
        r9 = r14.config();
        r9 = r9.getAllocator();
        r1 = r0.allocate(r9);
        r4 = 1;
        r9 = r1.writerIndex();	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r10 = r1.writableBytes();	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r3 = r1.nioBuffer(r9, r10);	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r9 = r14.ch;	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r10 = 0;	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r11 = r14.notificationHandler;	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r6 = r9.receive(r3, r10, r11);	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        if (r6 != 0) goto L_0x0056;
    L_0x004e:
        if (r4 == 0) goto L_0x000a;
    L_0x0050:
        r1.release();
        goto L_0x000a;
    L_0x0054:
        r5 = r9;
        goto L_0x0017;
    L_0x0056:
        r3.flip();	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r9 = r3.remaining();	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r0.lastBytesRead(r9);	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r9 = new io.netty.channel.sctp.SctpMessage;	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r10 = r1.writerIndex();	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r11 = r0.lastBytesRead();	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r10 = r10 + r11;	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r10 = r1.writerIndex(r10);	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r9.<init>(r6, r10);	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r15.add(r9);	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        r4 = 0;
        r7 = r7 + 1;
        if (r4 == 0) goto L_0x000a;
    L_0x007a:
        r1.release();
        goto L_0x000a;
    L_0x007e:
        r2 = move-exception;
        io.netty.util.internal.PlatformDependent.throwException(r2);	 Catch:{ Throwable -> 0x007e, all -> 0x0088 }
        if (r4 == 0) goto L_0x000a;
    L_0x0084:
        r1.release();
        goto L_0x000a;
    L_0x0088:
        r9 = move-exception;
        if (r4 == 0) goto L_0x008e;
    L_0x008b:
        r1.release();
    L_0x008e:
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.sctp.oio.OioSctpChannel.doReadMessages(java.util.List):int");
    }

    private static com.sun.nio.sctp.SctpChannel openChannel() {
        try {
            return com.sun.nio.sctp.SctpChannel.open();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a sctp channel.", e);
        }
    }

    public OioSctpChannel() {
        this(openChannel());
    }

    public OioSctpChannel(com.sun.nio.sctp.SctpChannel ch) {
        this(null, ch);
    }

    public OioSctpChannel(Channel parent, com.sun.nio.sctp.SctpChannel ch) {
        super(parent);
        this.ch = ch;
        try {
            ch.configureBlocking(false);
            this.readSelector = Selector.open();
            this.writeSelector = Selector.open();
            this.connectSelector = Selector.open();
            ch.register(this.readSelector, 1);
            ch.register(this.writeSelector, 4);
            ch.register(this.connectSelector, 8);
            this.config = new OioSctpChannelConfig(this, ch);
            this.notificationHandler = new SctpNotificationHandler(this);
            if (!true) {
                try {
                    ch.close();
                } catch (IOException e) {
                    logger.warn("Failed to close a sctp channel.", e);
                }
            }
        } catch (Exception e2) {
            throw new ChannelException("failed to initialize a sctp channel", e2);
        } catch (Throwable th) {
            if (!false) {
                try {
                    ch.close();
                } catch (IOException e3) {
                    logger.warn("Failed to close a sctp channel.", e3);
                }
            }
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

    public SctpChannelConfig config() {
        return this.config;
    }

    public boolean isOpen() {
        return this.ch.isOpen();
    }

    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        if (this.writeSelector.isOpen()) {
            int size = in.size();
            if (this.writeSelector.select(1000) > 0) {
                Set<SelectionKey> writableKeys = this.writeSelector.selectedKeys();
                if (!writableKeys.isEmpty()) {
                    Iterator<SelectionKey> writableKeysIt = writableKeys.iterator();
                    int written = 0;
                    while (written != size) {
                        writableKeysIt.next();
                        writableKeysIt.remove();
                        SctpMessage packet = (SctpMessage) in.current();
                        if (packet != null) {
                            ByteBuffer nioData;
                            ByteBuf data = packet.content();
                            int dataLen = data.readableBytes();
                            if (data.nioBufferCount() != -1) {
                                nioData = data.nioBuffer();
                            } else {
                                nioData = ByteBuffer.allocate(dataLen);
                                data.getBytes(data.readerIndex(), nioData);
                                nioData.flip();
                            }
                            MessageInfo mi = MessageInfo.createOutgoing(association(), null, packet.streamIdentifier());
                            mi.payloadProtocolID(packet.protocolIdentifier());
                            mi.streamNumber(packet.streamIdentifier());
                            mi.unordered(packet.isUnordered());
                            this.ch.send(nioData, mi);
                            written++;
                            in.remove();
                            if (!writableKeysIt.hasNext()) {
                                return;
                            }
                        }
                        return;
                    }
                }
            }
        }
    }

    protected Object filterOutboundMessage(Object msg) throws Exception {
        if (msg instanceof SctpMessage) {
            return msg;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPE);
    }

    public Association association() {
        try {
            return this.ch.association();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean isActive() {
        return isOpen() && association() != null;
    }

    protected SocketAddress localAddress0() {
        try {
            Iterator<SocketAddress> i = this.ch.getAllLocalAddresses().iterator();
            if (i.hasNext()) {
                return (SocketAddress) i.next();
            }
        } catch (IOException e) {
        }
        return null;
    }

    public Set<InetSocketAddress> allLocalAddresses() {
        try {
            Set<SocketAddress> allLocalAddresses = this.ch.getAllLocalAddresses();
            Set<InetSocketAddress> linkedHashSet = new LinkedHashSet(allLocalAddresses.size());
            for (SocketAddress socketAddress : allLocalAddresses) {
                linkedHashSet.add((InetSocketAddress) socketAddress);
            }
            return linkedHashSet;
        } catch (Throwable th) {
            return Collections.emptySet();
        }
    }

    protected SocketAddress remoteAddress0() {
        try {
            Iterator<SocketAddress> i = this.ch.getRemoteAddresses().iterator();
            if (i.hasNext()) {
                return (SocketAddress) i.next();
            }
        } catch (IOException e) {
        }
        return null;
    }

    public Set<InetSocketAddress> allRemoteAddresses() {
        try {
            Set<SocketAddress> allLocalAddresses = this.ch.getRemoteAddresses();
            Set<InetSocketAddress> linkedHashSet = new LinkedHashSet(allLocalAddresses.size());
            for (SocketAddress socketAddress : allLocalAddresses) {
                linkedHashSet.add((InetSocketAddress) socketAddress);
            }
            return linkedHashSet;
        } catch (Throwable th) {
            return Collections.emptySet();
        }
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        this.ch.bind(localAddress);
    }

    protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            this.ch.bind(localAddress);
        }
        boolean success = false;
        try {
            this.ch.connect(remoteAddress);
            boolean finishConnect = false;
            while (!finishConnect) {
                if (this.connectSelector.select(1000) >= 0) {
                    Set<SelectionKey> selectionKeys = this.connectSelector.selectedKeys();
                    for (SelectionKey key : selectionKeys) {
                        if (key.isConnectable()) {
                            selectionKeys.clear();
                            finishConnect = true;
                            break;
                        }
                    }
                    selectionKeys.clear();
                }
            }
            success = this.ch.finishConnect();
        } finally {
            if (!success) {
                doClose();
            }
        }
    }

    protected void doDisconnect() throws Exception {
        doClose();
    }

    protected void doClose() throws Exception {
        closeSelector("read", this.readSelector);
        closeSelector("write", this.writeSelector);
        closeSelector("connect", this.connectSelector);
        this.ch.close();
    }

    private static void closeSelector(String selectorName, Selector selector) {
        try {
            selector.close();
        } catch (IOException e) {
            logger.warn("Failed to close a " + selectorName + " selector.", e);
        }
    }

    public ChannelFuture bindAddress(InetAddress localAddress) {
        return bindAddress(localAddress, newPromise());
    }

    public ChannelFuture bindAddress(final InetAddress localAddress, final ChannelPromise promise) {
        if (eventLoop().inEventLoop()) {
            try {
                this.ch.bindAddress(localAddress);
                promise.setSuccess();
            } catch (Throwable t) {
                promise.setFailure(t);
            }
        } else {
            eventLoop().execute(new Runnable() {
                public void run() {
                    OioSctpChannel.this.bindAddress(localAddress, promise);
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
                this.ch.unbindAddress(localAddress);
                promise.setSuccess();
            } catch (Throwable t) {
                promise.setFailure(t);
            }
        } else {
            eventLoop().execute(new Runnable() {
                public void run() {
                    OioSctpChannel.this.unbindAddress(localAddress, promise);
                }
            });
        }
        return promise;
    }
}
