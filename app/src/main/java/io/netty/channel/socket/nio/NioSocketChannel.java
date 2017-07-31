package io.netty.channel.socket.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.channel.nio.AbstractNioByteChannel;
import io.netty.channel.socket.DefaultSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;

public class NioSocketChannel extends AbstractNioByteChannel implements SocketChannel {
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioSocketChannel.class);
    private final SocketChannelConfig config;

    private final class NioSocketChannelConfig extends DefaultSocketChannelConfig {
        private NioSocketChannelConfig(NioSocketChannel channel, Socket javaSocket) {
            super(channel, javaSocket);
        }

        protected void autoReadCleared() {
            NioSocketChannel.this.clearReadPending();
        }
    }

    private final class NioSocketChannelUnsafe extends NioByteUnsafe {
        private NioSocketChannelUnsafe() {
            super();
        }

        protected Executor prepareToClose() {
            try {
                if (NioSocketChannel.this.javaChannel().isOpen() && NioSocketChannel.this.config().getSoLinger() > 0) {
                    NioSocketChannel.this.doDeregister();
                    return GlobalEventExecutor.INSTANCE;
                }
            } catch (Throwable th) {
            }
            return null;
        }
    }

    private static java.nio.channels.SocketChannel newSocket(SelectorProvider provider) {
        try {
            return provider.openSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    public NioSocketChannel() {
        this(DEFAULT_SELECTOR_PROVIDER);
    }

    public NioSocketChannel(SelectorProvider provider) {
        this(newSocket(provider));
    }

    public NioSocketChannel(java.nio.channels.SocketChannel socket) {
        this(null, socket);
    }

    public NioSocketChannel(Channel parent, java.nio.channels.SocketChannel socket) {
        super(parent, socket);
        this.config = new NioSocketChannelConfig(this, socket.socket());
    }

    public ServerSocketChannel parent() {
        return (ServerSocketChannel) super.parent();
    }

    public SocketChannelConfig config() {
        return this.config;
    }

    protected java.nio.channels.SocketChannel javaChannel() {
        return (java.nio.channels.SocketChannel) super.javaChannel();
    }

    public boolean isActive() {
        java.nio.channels.SocketChannel ch = javaChannel();
        return ch.isOpen() && ch.isConnected();
    }

    public boolean isOutputShutdown() {
        return javaChannel().socket().isOutputShutdown() || !isActive();
    }

    public boolean isInputShutdown() {
        return javaChannel().socket().isInputShutdown() || !isActive();
    }

    public boolean isShutdown() {
        Socket socket = javaChannel().socket();
        return (socket.isInputShutdown() && socket.isOutputShutdown()) || !isActive();
    }

    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    public ChannelFuture shutdownOutput() {
        return shutdownOutput(newPromise());
    }

    public ChannelFuture shutdownOutput(final ChannelPromise promise) {
        Executor closeExecutor = ((NioSocketChannelUnsafe) unsafe()).prepareToClose();
        if (closeExecutor != null) {
            closeExecutor.execute(new Runnable() {
                public void run() {
                    NioSocketChannel.this.shutdownOutput0(promise);
                }
            });
        } else {
            EventLoop loop = eventLoop();
            if (loop.inEventLoop()) {
                shutdownOutput0(promise);
            } else {
                loop.execute(new Runnable() {
                    public void run() {
                        NioSocketChannel.this.shutdownOutput0(promise);
                    }
                });
            }
        }
        return promise;
    }

    public ChannelFuture shutdownInput() {
        return shutdownInput(newPromise());
    }

    public ChannelFuture shutdownInput(final ChannelPromise promise) {
        Executor closeExecutor = ((NioSocketChannelUnsafe) unsafe()).prepareToClose();
        if (closeExecutor != null) {
            closeExecutor.execute(new Runnable() {
                public void run() {
                    NioSocketChannel.this.shutdownInput0(promise);
                }
            });
        } else {
            EventLoop loop = eventLoop();
            if (loop.inEventLoop()) {
                shutdownInput0(promise);
            } else {
                loop.execute(new Runnable() {
                    public void run() {
                        NioSocketChannel.this.shutdownInput0(promise);
                    }
                });
            }
        }
        return promise;
    }

    public ChannelFuture shutdown() {
        return shutdown(newPromise());
    }

    public ChannelFuture shutdown(final ChannelPromise promise) {
        Executor closeExecutor = ((NioSocketChannelUnsafe) unsafe()).prepareToClose();
        if (closeExecutor != null) {
            closeExecutor.execute(new Runnable() {
                public void run() {
                    NioSocketChannel.this.shutdown0(promise);
                }
            });
        } else {
            EventLoop loop = eventLoop();
            if (loop.inEventLoop()) {
                shutdown0(promise);
            } else {
                loop.execute(new Runnable() {
                    public void run() {
                        NioSocketChannel.this.shutdown0(promise);
                    }
                });
            }
        }
        return promise;
    }

    private void shutdownOutput0(ChannelPromise promise) {
        try {
            shutdownOutput0();
            promise.setSuccess();
        } catch (Throwable t) {
            promise.setFailure(t);
        }
    }

    private void shutdownOutput0() throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            javaChannel().shutdownOutput();
        } else {
            javaChannel().socket().shutdownOutput();
        }
    }

    private void shutdownInput0(ChannelPromise promise) {
        try {
            shutdownInput0();
            promise.setSuccess();
        } catch (Throwable t) {
            promise.setFailure(t);
        }
    }

    private void shutdownInput0() throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            javaChannel().shutdownInput();
        } else {
            javaChannel().socket().shutdownInput();
        }
    }

    private void shutdown0(ChannelPromise promise) {
        Throwable cause = null;
        try {
            shutdownOutput0();
        } catch (Throwable t) {
            cause = t;
        }
        try {
            shutdownInput0();
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
            logger.debug("Exception suppressed because a previous exception occurred.", t2);
            promise.setFailure(cause);
        }
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
        super.doClose();
        javaChannel().close();
    }

    protected int doReadBytes(ByteBuf byteBuf) throws Exception {
        Handle allocHandle = unsafe().recvBufAllocHandle();
        allocHandle.attemptedBytesRead(byteBuf.writableBytes());
        return byteBuf.writeBytes(javaChannel(), allocHandle.attemptedBytesRead());
    }

    protected int doWriteBytes(ByteBuf buf) throws Exception {
        return buf.readBytes(javaChannel(), buf.readableBytes());
    }

    protected long doWriteFileRegion(FileRegion region) throws Exception {
        return region.transferTo(javaChannel(), region.transferred());
    }

    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        while (in.size() != 0) {
            long writtenBytes = 0;
            boolean done = false;
            boolean setOpWrite = false;
            ByteBuffer[] nioBuffers = in.nioBuffers();
            int nioBufferCnt = in.nioBufferCount();
            long expectedWrittenBytes = in.nioBufferSize();
            java.nio.channels.SocketChannel ch = javaChannel();
            int i;
            long localWrittenBytes;
            switch (nioBufferCnt) {
                case 0:
                    super.doWrite(in);
                    return;
                case 1:
                    ByteBuffer nioBuffer = nioBuffers[0];
                    for (i = config().getWriteSpinCount() - 1; i >= 0; i--) {
                        localWrittenBytes = ch.write(nioBuffer);
                        if (localWrittenBytes == null) {
                            setOpWrite = true;
                            break;
                        }
                        expectedWrittenBytes -= (long) localWrittenBytes;
                        writtenBytes += (long) localWrittenBytes;
                        if (expectedWrittenBytes == 0) {
                            done = true;
                            break;
                        }
                    }
                    break;
                default:
                    for (i = config().getWriteSpinCount() - 1; i >= 0; i--) {
                        localWrittenBytes = ch.write(nioBuffers, 0, nioBufferCnt);
                        if (localWrittenBytes == 0) {
                            setOpWrite = true;
                            break;
                        }
                        expectedWrittenBytes -= localWrittenBytes;
                        writtenBytes += localWrittenBytes;
                        if (expectedWrittenBytes == 0) {
                            done = true;
                            break;
                        }
                    }
                    break;
            }
            in.removeBytes(writtenBytes);
            if (!done) {
                incompleteWrite(setOpWrite);
                return;
            }
        }
        clearOpWrite();
    }

    protected AbstractNioChannel$AbstractNioUnsafe newUnsafe() {
        return new NioSocketChannelUnsafe();
    }
}
