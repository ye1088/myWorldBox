package io.netty.channel.epoll;

import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.unix.FileDescriptor;

final class EpollDomainSocketChannel$EpollDomainUnsafe extends AbstractEpollStreamChannel$EpollStreamUnsafe {
    final /* synthetic */ EpollDomainSocketChannel this$0;

    private EpollDomainSocketChannel$EpollDomainUnsafe(EpollDomainSocketChannel epollDomainSocketChannel) {
        this.this$0 = epollDomainSocketChannel;
        super(epollDomainSocketChannel);
    }

    void epollInReady() {
        switch (EpollDomainSocketChannel$1.$SwitchMap$io$netty$channel$unix$DomainSocketReadMode[this.this$0.config().getReadMode().ordinal()]) {
            case 1:
                super.epollInReady();
                return;
            case 2:
                epollInReadFd();
                return;
            default:
                throw new Error();
        }
    }

    private void epollInReadFd() {
        if (this.this$0.fd().isInputShutdown()) {
            clearEpollIn0();
            return;
        }
        ChannelConfig config = this.this$0.config();
        EpollRecvByteAllocatorHandle allocHandle = recvBufAllocHandle();
        allocHandle.edgeTriggered(this.this$0.isFlagSet(Native.EPOLLET));
        ChannelPipeline pipeline = this.this$0.pipeline();
        allocHandle.reset(config);
        epollInBefore();
        while (true) {
            try {
                allocHandle.lastBytesRead(Native.recvFd(this.this$0.fd().intValue()));
                switch (allocHandle.lastBytesRead()) {
                    case -1:
                        close(voidPromise());
                        epollInFinally(config);
                        return;
                    case 0:
                        break;
                    default:
                        allocHandle.incMessagesRead(1);
                        this.readPending = false;
                        pipeline.fireChannelRead(new FileDescriptor(allocHandle.lastBytesRead()));
                        if (!allocHandle.continueReading()) {
                            break;
                        }
                }
                allocHandle.readComplete();
                pipeline.fireChannelReadComplete();
                return;
            } catch (Throwable t) {
                allocHandle.readComplete();
                pipeline.fireChannelReadComplete();
                pipeline.fireExceptionCaught(t);
                return;
            } finally {
                epollInFinally(config);
            }
        }
    }
}
