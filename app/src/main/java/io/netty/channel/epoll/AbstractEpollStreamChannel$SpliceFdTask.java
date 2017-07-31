package io.netty.channel.epoll;

import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.channel.unix.FileDescriptor;

final class AbstractEpollStreamChannel$SpliceFdTask extends AbstractEpollStreamChannel$SpliceInTask {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractEpollStreamChannel.class.desiredAssertionStatus());
    private final FileDescriptor fd;
    private final int offset;
    private final ChannelPromise promise;
    final /* synthetic */ AbstractEpollStreamChannel this$0;

    AbstractEpollStreamChannel$SpliceFdTask(AbstractEpollStreamChannel abstractEpollStreamChannel, FileDescriptor fd, int offset, int len, ChannelPromise promise) {
        this.this$0 = abstractEpollStreamChannel;
        super(abstractEpollStreamChannel, len, promise);
        this.fd = fd;
        this.promise = promise;
        this.offset = offset;
    }

    public boolean spliceIn(Handle handle) {
        if (!$assertionsDisabled && !this.this$0.eventLoop().inEventLoop()) {
            throw new AssertionError();
        } else if (this.len == 0) {
            this.promise.setSuccess();
            return true;
        } else {
            FileDescriptor pipeIn;
            FileDescriptor pipeOut;
            try {
                FileDescriptor[] pipe = FileDescriptor.pipe();
                pipeIn = pipe[0];
                pipeOut = pipe[1];
                int splicedIn = spliceIn(pipeOut, handle);
                if (splicedIn > 0) {
                    if (this.len != Integer.MAX_VALUE) {
                        this.len -= splicedIn;
                    }
                    do {
                        splicedIn -= Native.splice(pipeIn.intValue(), -1, this.fd.intValue(), (long) this.offset, (long) splicedIn);
                    } while (splicedIn > 0);
                    if (this.len == 0) {
                        this.promise.setSuccess();
                        AbstractEpollStreamChannel.access$1100(pipeIn);
                        AbstractEpollStreamChannel.access$1100(pipeOut);
                        return true;
                    }
                }
                AbstractEpollStreamChannel.access$1100(pipeIn);
                AbstractEpollStreamChannel.access$1100(pipeOut);
                return false;
            } catch (Throwable cause) {
                this.promise.setFailure(cause);
                return true;
            }
        }
    }
}
