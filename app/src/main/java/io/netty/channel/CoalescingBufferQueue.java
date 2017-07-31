package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayDeque;

public final class CoalescingBufferQueue {
    static final /* synthetic */ boolean $assertionsDisabled = (!CoalescingBufferQueue.class.desiredAssertionStatus());
    private final ArrayDeque<Object> bufAndListenerPairs;
    private final Channel channel;
    private int readableBytes;

    public CoalescingBufferQueue(Channel channel) {
        this(channel, 4);
    }

    public CoalescingBufferQueue(Channel channel, int initSize) {
        this.channel = (Channel) ObjectUtil.checkNotNull(channel, "channel");
        this.bufAndListenerPairs = new ArrayDeque(initSize);
    }

    public void add(ByteBuf buf) {
        add(buf, (ChannelFutureListener) null);
    }

    public void add(ByteBuf buf, ChannelPromise promise) {
        ChannelFutureListener channelFutureListener;
        ObjectUtil.checkNotNull(promise, "promise");
        if (promise.isVoid()) {
            channelFutureListener = null;
        } else {
            channelFutureListener = new ChannelPromiseNotifier(promise);
        }
        add(buf, channelFutureListener);
    }

    public void add(ByteBuf buf, ChannelFutureListener listener) {
        ObjectUtil.checkNotNull(buf, "buf");
        if (this.readableBytes > Integer.MAX_VALUE - buf.readableBytes()) {
            throw new IllegalStateException("buffer queue length overflow: " + this.readableBytes + " + " + buf.readableBytes());
        }
        this.bufAndListenerPairs.add(buf);
        if (listener != null) {
            this.bufAndListenerPairs.add(listener);
        }
        this.readableBytes += buf.readableBytes();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public io.netty.buffer.ByteBuf remove(int r8, io.netty.channel.ChannelPromise r9) {
        /*
        r7 = this;
        if (r8 >= 0) goto L_0x001c;
    L_0x0002:
        r4 = new java.lang.IllegalArgumentException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "bytes (expected >= 0): ";
        r5 = r5.append(r6);
        r5 = r5.append(r8);
        r5 = r5.toString();
        r4.<init>(r5);
        throw r4;
    L_0x001c:
        r4 = "aggregatePromise";
        io.netty.util.internal.ObjectUtil.checkNotNull(r9, r4);
        r4 = r7.bufAndListenerPairs;
        r4 = r4.isEmpty();
        if (r4 == 0) goto L_0x002d;
    L_0x002a:
        r3 = io.netty.buffer.Unpooled.EMPTY_BUFFER;
    L_0x002c:
        return r3;
    L_0x002d:
        r4 = r7.readableBytes;
        r8 = java.lang.Math.min(r8, r4);
        r3 = 0;
        r2 = r8;
    L_0x0035:
        r4 = r7.bufAndListenerPairs;
        r0 = r4.poll();
        if (r0 != 0) goto L_0x0052;
    L_0x003d:
        r4 = r7.readableBytes;
        r5 = r2 - r8;
        r4 = r4 - r5;
        r7.readableBytes = r4;
        r4 = $assertionsDisabled;
        if (r4 != 0) goto L_0x002c;
    L_0x0048:
        r4 = r7.readableBytes;
        if (r4 >= 0) goto L_0x002c;
    L_0x004c:
        r4 = new java.lang.AssertionError;
        r4.<init>();
        throw r4;
    L_0x0052:
        r4 = r0 instanceof io.netty.channel.ChannelFutureListener;
        if (r4 == 0) goto L_0x005c;
    L_0x0056:
        r0 = (io.netty.channel.ChannelFutureListener) r0;
        r9.addListener(r0);
        goto L_0x0035;
    L_0x005c:
        r1 = r0;
        r1 = (io.netty.buffer.ByteBuf) r1;
        r4 = r1.readableBytes();
        if (r4 <= r8) goto L_0x0076;
    L_0x0065:
        r4 = r7.bufAndListenerPairs;
        r4.addFirst(r1);
        if (r8 <= 0) goto L_0x003d;
    L_0x006c:
        r4 = r1.readRetainedSlice(r8);
        r3 = r7.compose(r3, r4);
        r8 = 0;
        goto L_0x003d;
    L_0x0076:
        r3 = r7.compose(r3, r1);
        r4 = r1.readableBytes();
        r8 = r8 - r4;
        goto L_0x0035;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.CoalescingBufferQueue.remove(int, io.netty.channel.ChannelPromise):io.netty.buffer.ByteBuf");
    }

    private ByteBuf compose(ByteBuf current, ByteBuf next) {
        if (current == null) {
            return next;
        }
        if (current instanceof CompositeByteBuf) {
            CompositeByteBuf composite = (CompositeByteBuf) current;
            composite.addComponent(true, next);
            return composite;
        }
        ByteBuf composite2 = this.channel.alloc().compositeBuffer(this.bufAndListenerPairs.size() + 2);
        composite2.addComponent(true, current);
        composite2.addComponent(true, next);
        return composite2;
    }

    public int readableBytes() {
        return this.readableBytes;
    }

    public boolean isEmpty() {
        return this.bufAndListenerPairs.isEmpty();
    }

    public void releaseAndFailAll(Throwable cause) {
        releaseAndCompleteAll(this.channel.newFailedFuture(cause));
    }

    private void releaseAndCompleteAll(ChannelFuture future) {
        this.readableBytes = 0;
        Throwable pending = null;
        while (true) {
            Object entry = this.bufAndListenerPairs.poll();
            if (entry == null) {
                break;
            }
            try {
                if (entry instanceof ByteBuf) {
                    ReferenceCountUtil.safeRelease(entry);
                } else {
                    ((ChannelFutureListener) entry).operationComplete(future);
                }
            } catch (Throwable t) {
                pending = t;
            }
        }
        if (pending != null) {
            throw new IllegalStateException(pending);
        }
    }

    public void copyTo(CoalescingBufferQueue dest) {
        dest.bufAndListenerPairs.addAll(this.bufAndListenerPairs);
        dest.readableBytes += this.readableBytes;
    }
}
