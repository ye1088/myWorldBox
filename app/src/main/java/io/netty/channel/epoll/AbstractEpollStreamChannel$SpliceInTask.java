package io.netty.channel.epoll;

import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.channel.unix.FileDescriptor;
import java.io.IOException;

protected abstract class AbstractEpollStreamChannel$SpliceInTask {
    int len;
    final ChannelPromise promise;
    final /* synthetic */ AbstractEpollStreamChannel this$0;

    abstract boolean spliceIn(Handle handle);

    protected AbstractEpollStreamChannel$SpliceInTask(AbstractEpollStreamChannel abstractEpollStreamChannel, int len, ChannelPromise promise) {
        this.this$0 = abstractEpollStreamChannel;
        this.promise = promise;
        this.len = len;
    }

    protected final int spliceIn(FileDescriptor pipeOut, Handle handle) throws IOException {
        int length = Math.min(handle.guess(), this.len);
        int splicedIn = 0;
        while (true) {
            int localSplicedIn = Native.splice(this.this$0.fd().intValue(), -1, pipeOut.intValue(), -1, (long) length);
            if (localSplicedIn == 0) {
                return splicedIn;
            }
            splicedIn += localSplicedIn;
            length -= localSplicedIn;
        }
    }
}
