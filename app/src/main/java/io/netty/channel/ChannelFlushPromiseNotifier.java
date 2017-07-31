package io.netty.channel;

import java.util.ArrayDeque;
import java.util.Queue;

public final class ChannelFlushPromiseNotifier {
    private final Queue<FlushCheckpoint> flushCheckpoints;
    private final boolean tryNotify;
    private long writeCounter;

    public ChannelFlushPromiseNotifier(boolean tryNotify) {
        this.flushCheckpoints = new ArrayDeque();
        this.tryNotify = tryNotify;
    }

    public ChannelFlushPromiseNotifier() {
        this(false);
    }

    @Deprecated
    public ChannelFlushPromiseNotifier add(ChannelPromise promise, int pendingDataSize) {
        return add(promise, (long) pendingDataSize);
    }

    public ChannelFlushPromiseNotifier add(ChannelPromise promise, long pendingDataSize) {
        if (promise == null) {
            throw new NullPointerException("promise");
        } else if (pendingDataSize < 0) {
            throw new IllegalArgumentException("pendingDataSize must be >= 0 but was " + pendingDataSize);
        } else {
            long checkpoint = this.writeCounter + pendingDataSize;
            if (promise instanceof FlushCheckpoint) {
                FlushCheckpoint cp = (FlushCheckpoint) promise;
                cp.flushCheckpoint(checkpoint);
                this.flushCheckpoints.add(cp);
            } else {
                this.flushCheckpoints.add(new DefaultFlushCheckpoint(checkpoint, promise));
            }
            return this;
        }
    }

    public ChannelFlushPromiseNotifier increaseWriteCounter(long delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("delta must be >= 0 but was " + delta);
        }
        this.writeCounter += delta;
        return this;
    }

    public long writeCounter() {
        return this.writeCounter;
    }

    public ChannelFlushPromiseNotifier notifyPromises() {
        notifyPromises0(null);
        return this;
    }

    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures() {
        return notifyPromises();
    }

    public ChannelFlushPromiseNotifier notifyPromises(Throwable cause) {
        notifyPromises();
        while (true) {
            FlushCheckpoint cp = (FlushCheckpoint) this.flushCheckpoints.poll();
            if (cp == null) {
                return this;
            }
            if (this.tryNotify) {
                cp.promise().tryFailure(cause);
            } else {
                cp.promise().setFailure(cause);
            }
        }
    }

    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable cause) {
        return notifyPromises(cause);
    }

    public ChannelFlushPromiseNotifier notifyPromises(Throwable cause1, Throwable cause2) {
        notifyPromises0(cause1);
        while (true) {
            FlushCheckpoint cp = (FlushCheckpoint) this.flushCheckpoints.poll();
            if (cp == null) {
                return this;
            }
            if (this.tryNotify) {
                cp.promise().tryFailure(cause2);
            } else {
                cp.promise().setFailure(cause2);
            }
        }
    }

    @Deprecated
    public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable cause1, Throwable cause2) {
        return notifyPromises(cause1, cause2);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void notifyPromises0(java.lang.Throwable r13) {
        /*
        r12 = this;
        r10 = 0;
        r5 = r12.flushCheckpoints;
        r5 = r5.isEmpty();
        if (r5 == 0) goto L_0x000d;
    L_0x000a:
        r12.writeCounter = r10;
    L_0x000c:
        return;
    L_0x000d:
        r6 = r12.writeCounter;
    L_0x000f:
        r5 = r12.flushCheckpoints;
        r0 = r5.peek();
        r0 = (io.netty.channel.ChannelFlushPromiseNotifier.FlushCheckpoint) r0;
        if (r0 != 0) goto L_0x0043;
    L_0x0019:
        r12.writeCounter = r10;
    L_0x001b:
        r2 = r12.writeCounter;
        r8 = 549755813888; // 0x8000000000 float:0.0 double:2.716154612436E-312;
        r5 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r5 < 0) goto L_0x000c;
    L_0x0026:
        r12.writeCounter = r10;
        r5 = r12.flushCheckpoints;
        r1 = r5.iterator();
    L_0x002e:
        r5 = r1.hasNext();
        if (r5 == 0) goto L_0x000c;
    L_0x0034:
        r0 = r1.next();
        r0 = (io.netty.channel.ChannelFlushPromiseNotifier.FlushCheckpoint) r0;
        r8 = r0.flushCheckpoint();
        r8 = r8 - r2;
        r0.flushCheckpoint(r8);
        goto L_0x002e;
    L_0x0043:
        r8 = r0.flushCheckpoint();
        r5 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
        if (r5 <= 0) goto L_0x0063;
    L_0x004b:
        r5 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1));
        if (r5 <= 0) goto L_0x001b;
    L_0x004f:
        r5 = r12.flushCheckpoints;
        r5 = r5.size();
        r8 = 1;
        if (r5 != r8) goto L_0x001b;
    L_0x0058:
        r12.writeCounter = r10;
        r8 = r0.flushCheckpoint();
        r8 = r8 - r6;
        r0.flushCheckpoint(r8);
        goto L_0x001b;
    L_0x0063:
        r5 = r12.flushCheckpoints;
        r5.remove();
        r4 = r0.promise();
        if (r13 != 0) goto L_0x007a;
    L_0x006e:
        r5 = r12.tryNotify;
        if (r5 == 0) goto L_0x0076;
    L_0x0072:
        r4.trySuccess();
        goto L_0x000f;
    L_0x0076:
        r4.setSuccess();
        goto L_0x000f;
    L_0x007a:
        r5 = r12.tryNotify;
        if (r5 == 0) goto L_0x0082;
    L_0x007e:
        r4.tryFailure(r13);
        goto L_0x000f;
    L_0x0082:
        r4.setFailure(r13);
        goto L_0x000f;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.ChannelFlushPromiseNotifier.notifyPromises0(java.lang.Throwable):void");
    }
}
