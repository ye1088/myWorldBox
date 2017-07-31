package io.netty.channel;

import io.netty.buffer.Unpooled;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import io.netty.util.ReferenceCountUtil;
import java.nio.ByteBuffer;

final class ChannelOutboundBuffer$Entry {
    private static final Recycler<ChannelOutboundBuffer$Entry> RECYCLER = new Recycler<ChannelOutboundBuffer$Entry>() {
        protected ChannelOutboundBuffer$Entry newObject(Handle handle) {
            return new ChannelOutboundBuffer$Entry(handle);
        }
    };
    ByteBuffer buf;
    ByteBuffer[] bufs;
    boolean cancelled;
    int count;
    private final Handle<ChannelOutboundBuffer$Entry> handle;
    Object msg;
    ChannelOutboundBuffer$Entry next;
    int pendingSize;
    long progress;
    ChannelPromise promise;
    long total;

    private ChannelOutboundBuffer$Entry(Handle<ChannelOutboundBuffer$Entry> handle) {
        this.count = -1;
        this.handle = handle;
    }

    static ChannelOutboundBuffer$Entry newInstance(Object msg, int size, long total, ChannelPromise promise) {
        ChannelOutboundBuffer$Entry entry = (ChannelOutboundBuffer$Entry) RECYCLER.get();
        entry.msg = msg;
        entry.pendingSize = size;
        entry.total = total;
        entry.promise = promise;
        return entry;
    }

    int cancel() {
        if (this.cancelled) {
            return 0;
        }
        this.cancelled = true;
        int pSize = this.pendingSize;
        ReferenceCountUtil.safeRelease(this.msg);
        this.msg = Unpooled.EMPTY_BUFFER;
        this.pendingSize = 0;
        this.total = 0;
        this.progress = 0;
        this.bufs = null;
        this.buf = null;
        return pSize;
    }

    void recycle() {
        this.next = null;
        this.bufs = null;
        this.buf = null;
        this.msg = null;
        this.promise = null;
        this.progress = 0;
        this.total = 0;
        this.pendingSize = 0;
        this.count = -1;
        this.cancelled = false;
        this.handle.recycle(this);
    }

    ChannelOutboundBuffer$Entry recycleAndGetNext() {
        ChannelOutboundBuffer$Entry next = this.next;
        recycle();
        return next;
    }
}
