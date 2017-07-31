package io.netty.channel.epoll;

import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import java.net.SocketAddress;

final class AbstractEpollServerChannel$EpollServerSocketUnsafe extends AbstractEpollUnsafe {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractEpollServerChannel.class.desiredAssertionStatus());
    private final byte[] acceptedAddress = new byte[26];
    final /* synthetic */ AbstractEpollServerChannel this$0;

    AbstractEpollServerChannel$EpollServerSocketUnsafe(AbstractEpollServerChannel abstractEpollServerChannel) {
        this.this$0 = abstractEpollServerChannel;
        super();
    }

    public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure(new UnsupportedOperationException());
    }

    void epollInReady() {
        if (!$assertionsDisabled && !this.this$0.eventLoop().inEventLoop()) {
            throw new AssertionError();
        } else if (this.this$0.fd().isInputShutdown()) {
            clearEpollIn0();
        } else {
            ChannelConfig config = this.this$0.config();
            EpollRecvByteAllocatorHandle allocHandle = recvBufAllocHandle();
            allocHandle.edgeTriggered(this.this$0.isFlagSet(Native.EPOLLET));
            ChannelPipeline pipeline = this.this$0.pipeline();
            allocHandle.reset(config);
            epollInBefore();
            Throwable exception = null;
            do {
                try {
                    allocHandle.lastBytesRead(this.this$0.fd().accept(this.acceptedAddress));
                    if (allocHandle.lastBytesRead() != -1) {
                        allocHandle.incMessagesRead(1);
                        int len = this.acceptedAddress[0];
                        this.readPending = false;
                        pipeline.fireChannelRead(this.this$0.newChildChannel(allocHandle.lastBytesRead(), this.acceptedAddress, 1, len));
                    }
                } catch (Throwable t) {
                    exception = t;
                }
                break;
            } while (allocHandle.continueReading());
            try {
                allocHandle.readComplete();
                pipeline.fireChannelReadComplete();
                if (exception != null) {
                    pipeline.fireExceptionCaught(exception);
                }
                epollInFinally(config);
            } catch (Throwable th) {
                epollInFinally(config);
            }
        }
    }
}
