package io.netty.util;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

final class Recycler$WeakOrderQueue {
    static final /* synthetic */ boolean $assertionsDisabled = (!Recycler.class.desiredAssertionStatus());
    static final Recycler$WeakOrderQueue DUMMY = new Recycler$WeakOrderQueue();
    private final AtomicInteger availableSharedCapacity;
    private Link head;
    private final int id;
    private Recycler$WeakOrderQueue next;
    private final WeakReference<Thread> owner;
    private Link tail;

    private static final class Link extends AtomicInteger {
        private final Recycler$DefaultHandle<?>[] elements;
        private Link next;
        private int readIndex;

        private Link() {
            this.elements = new Recycler$DefaultHandle[Recycler.access$800()];
        }
    }

    protected void finalize() throws java.lang.Throwable {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Incorrect nodes count for selectOther: B:9:0x0024 in [B:5:0x0018, B:9:0x0024, B:8:0x0025]
	at jadx.core.utils.BlockUtils.selectOther(BlockUtils.java:53)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:64)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r3 = this;
        super.finalize();	 Catch:{ all -> 0x0013 }
        r0 = r3.head;
    L_0x0005:
        if (r0 == 0) goto L_0x0025;
    L_0x0007:
        r1 = io.netty.util.Recycler.access$800();
        r3.reclaimSpace(r1);
        r0 = r0.next;
        goto L_0x0005;
    L_0x0013:
        r1 = move-exception;
        r0 = r3.head;
    L_0x0016:
        if (r0 == 0) goto L_0x0024;
    L_0x0018:
        r2 = io.netty.util.Recycler.access$800();
        r3.reclaimSpace(r2);
        r0 = r0.next;
        goto L_0x0016;
    L_0x0024:
        throw r1;
    L_0x0025:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.Recycler$WeakOrderQueue.finalize():void");
    }

    private Recycler$WeakOrderQueue() {
        this.id = Recycler.access$900().getAndIncrement();
        this.owner = null;
        this.availableSharedCapacity = null;
    }

    private Recycler$WeakOrderQueue(Recycler$Stack<?> stack, Thread thread) {
        this.id = Recycler.access$900().getAndIncrement();
        Link link = new Link();
        this.tail = link;
        this.head = link;
        this.owner = new WeakReference(thread);
        synchronized (stack) {
            this.next = stack.head;
            stack.head = this;
        }
        this.availableSharedCapacity = stack.availableSharedCapacity;
    }

    static Recycler$WeakOrderQueue allocate(Recycler$Stack<?> stack, Thread thread) {
        return reserveSpace(stack.availableSharedCapacity, Recycler.access$800()) ? new Recycler$WeakOrderQueue(stack, thread) : null;
    }

    private static boolean reserveSpace(AtomicInteger availableSharedCapacity, int space) {
        if ($assertionsDisabled || space >= 0) {
            int available;
            do {
                available = availableSharedCapacity.get();
                if (available < space) {
                    return false;
                }
            } while (!availableSharedCapacity.compareAndSet(available, available - space));
            return true;
        }
        throw new AssertionError();
    }

    private void reclaimSpace(int space) {
        if ($assertionsDisabled || space >= 0) {
            this.availableSharedCapacity.addAndGet(space);
            return;
        }
        throw new AssertionError();
    }

    void add(Recycler$DefaultHandle<?> handle) {
        handle.lastRecycledId = this.id;
        Link tail = this.tail;
        int writeIndex = tail.get();
        if (writeIndex == Recycler.access$800()) {
            if (reserveSpace(this.availableSharedCapacity, Recycler.access$800())) {
                tail = tail.next = new Link();
                this.tail = tail;
                writeIndex = tail.get();
            } else {
                return;
            }
        }
        tail.elements[writeIndex] = handle;
        handle.stack = null;
        tail.lazySet(writeIndex + 1);
    }

    boolean hasFinalData() {
        return this.tail.readIndex != this.tail.get();
    }

    boolean transfer(Recycler$Stack<?> dst) {
        Link head = this.head;
        if (head == null) {
            return false;
        }
        if (head.readIndex == Recycler.access$800()) {
            if (head.next == null) {
                return false;
            }
            head = head.next;
            this.head = head;
        }
        int srcStart = head.readIndex;
        int srcEnd = head.get();
        int srcSize = srcEnd - srcStart;
        if (srcSize == 0) {
            return false;
        }
        int dstSize = dst.size;
        int expectedCapacity = dstSize + srcSize;
        if (expectedCapacity > dst.elements.length) {
            srcEnd = Math.min((srcStart + dst.increaseCapacity(expectedCapacity)) - dstSize, srcEnd);
        }
        if (srcStart == srcEnd) {
            return false;
        }
        Recycler$DefaultHandle[] srcElems = head.elements;
        Recycler$DefaultHandle[] dstElems = dst.elements;
        int i = srcStart;
        int newDstSize = dstSize;
        while (i < srcEnd) {
            int newDstSize2;
            Recycler$DefaultHandle element = srcElems[i];
            if (element.recycleId == 0) {
                element.recycleId = element.lastRecycledId;
            } else if (element.recycleId != element.lastRecycledId) {
                throw new IllegalStateException("recycled already");
            }
            srcElems[i] = null;
            if (dst.dropHandle(element)) {
                newDstSize2 = newDstSize;
            } else {
                element.stack = dst;
                newDstSize2 = newDstSize + 1;
                dstElems[newDstSize] = element;
            }
            i++;
            newDstSize = newDstSize2;
        }
        if (srcEnd == Recycler.access$800() && head.next != null) {
            reclaimSpace(Recycler.access$800());
            this.head = head.next;
        }
        head.readIndex = srcEnd;
        if (dst.size == newDstSize) {
            return false;
        }
        dst.size = newDstSize;
        return true;
    }
}
