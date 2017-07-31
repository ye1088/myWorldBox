package io.netty.handler.stream;

import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

final class ChunkedWriteHandler$PendingWrite {
    final Object msg;
    final ChannelPromise promise;

    ChunkedWriteHandler$PendingWrite(Object msg, ChannelPromise promise) {
        this.msg = msg;
        this.promise = promise;
    }

    void fail(Throwable cause) {
        ReferenceCountUtil.release(this.msg);
        this.promise.tryFailure(cause);
    }

    void success(long total) {
        if (!this.promise.isDone()) {
            if (this.promise instanceof ChannelProgressivePromise) {
                ((ChannelProgressivePromise) this.promise).tryProgress(total, total);
            }
            this.promise.trySuccess();
        }
    }

    void progress(long progress, long total) {
        if (this.promise instanceof ChannelProgressivePromise) {
            ((ChannelProgressivePromise) this.promise).tryProgress(progress, total);
        }
    }
}
