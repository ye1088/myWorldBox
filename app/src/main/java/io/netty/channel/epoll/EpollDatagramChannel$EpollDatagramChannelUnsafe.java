package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.unix.DatagramSocketAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

final class EpollDatagramChannel$EpollDatagramChannelUnsafe extends AbstractEpollUnsafe {
    static final /* synthetic */ boolean $assertionsDisabled = (!EpollDatagramChannel.class.desiredAssertionStatus());
    private final List<Object> readBuf = new ArrayList();
    final /* synthetic */ EpollDatagramChannel this$0;

    EpollDatagramChannel$EpollDatagramChannelUnsafe(EpollDatagramChannel epollDatagramChannel) {
        this.this$0 = epollDatagramChannel;
        super();
    }

    public void connect(SocketAddress remote, SocketAddress local, ChannelPromise channelPromise) {
        try {
            boolean wasActive = this.this$0.isActive();
            InetSocketAddress remoteAddress = (InetSocketAddress) remote;
            if (local != null) {
                this.this$0.doBind((InetSocketAddress) local);
            }
            AbstractEpollChannel.checkResolvable(remoteAddress);
            EpollDatagramChannel.access$002(this.this$0, remoteAddress);
            EpollDatagramChannel.access$102(this.this$0, this.this$0.fd().localAddress());
            channelPromise.trySuccess();
            if (!wasActive && this.this$0.isActive()) {
                this.this$0.pipeline().fireChannelActive();
            }
            if (true) {
                EpollDatagramChannel.access$202(this.this$0, true);
            } else {
                this.this$0.doClose();
            }
        } catch (Throwable cause) {
            channelPromise.tryFailure(cause);
        }
    }

    void epollInReady() {
        if (!$assertionsDisabled && !this.this$0.eventLoop().inEventLoop()) {
            throw new AssertionError();
        } else if (this.this$0.fd().isInputShutdown()) {
            clearEpollIn0();
        } else {
            DatagramChannelConfig config = this.this$0.config();
            EpollRecvByteAllocatorHandle allocHandle = recvBufAllocHandle();
            allocHandle.edgeTriggered(this.this$0.isFlagSet(Native.EPOLLET));
            ChannelPipeline pipeline = this.this$0.pipeline();
            ByteBufAllocator allocator = config.getAllocator();
            allocHandle.reset(config);
            epollInBefore();
            Throwable exception = null;
            ByteBuf data = null;
            do {
                try {
                    DatagramSocketAddress remoteAddress;
                    data = allocHandle.allocate(allocator);
                    allocHandle.attemptedBytesRead(data.writableBytes());
                    if (data.hasMemoryAddress()) {
                        remoteAddress = this.this$0.fd().recvFromAddress(data.memoryAddress(), data.writerIndex(), data.capacity());
                    } else {
                        ByteBuffer nioData = data.internalNioBuffer(data.writerIndex(), data.writableBytes());
                        remoteAddress = this.this$0.fd().recvFrom(nioData, nioData.position(), nioData.limit());
                    }
                    if (remoteAddress == null) {
                        allocHandle.lastBytesRead(-1);
                        data.release();
                        break;
                    }
                    allocHandle.incMessagesRead(1);
                    allocHandle.lastBytesRead(remoteAddress.receivedAmount());
                    data.writerIndex(data.writerIndex() + allocHandle.lastBytesRead());
                    this.readBuf.add(new DatagramPacket(data, (InetSocketAddress) localAddress(), remoteAddress));
                    data = null;
                } catch (Throwable th) {
                    epollInFinally(config);
                }
            } while (allocHandle.continueReading());
            int size = this.readBuf.size();
            for (int i = 0; i < size; i++) {
                this.readPending = false;
                pipeline.fireChannelRead(this.readBuf.get(i));
            }
            this.readBuf.clear();
            allocHandle.readComplete();
            pipeline.fireChannelReadComplete();
            if (exception != null) {
                pipeline.fireExceptionCaught(exception);
            }
            epollInFinally(config);
        }
    }
}
