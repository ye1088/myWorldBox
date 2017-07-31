package io.netty.util;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

final class Recycler$Stack<T> {
    final AtomicInteger availableSharedCapacity;
    private Recycler$WeakOrderQueue cursor;
    private Recycler$DefaultHandle<?>[] elements;
    private int handleRecycleCount = -1;
    private volatile Recycler$WeakOrderQueue head;
    private final int maxCapacity;
    final int maxDelayedQueues;
    final Recycler<T> parent;
    private Recycler$WeakOrderQueue prev;
    private final int ratioMask;
    private int size;
    final Thread thread;

    Recycler$Stack(Recycler<T> parent, Thread thread, int maxCapacity, int maxSharedCapacityFactor, int ratioMask, int maxDelayedQueues) {
        this.parent = parent;
        this.thread = thread;
        this.maxCapacity = maxCapacity;
        this.availableSharedCapacity = new AtomicInteger(Math.max(maxCapacity / maxSharedCapacityFactor, Recycler.access$800()));
        this.elements = new Recycler$DefaultHandle[Math.min(Recycler.access$1700(), maxCapacity)];
        this.ratioMask = ratioMask;
        this.maxDelayedQueues = maxDelayedQueues;
    }

    int increaseCapacity(int expectedCapacity) {
        int newCapacity = this.elements.length;
        int maxCapacity = this.maxCapacity;
        do {
            newCapacity <<= 1;
            if (newCapacity >= expectedCapacity) {
                break;
            }
        } while (newCapacity < maxCapacity);
        newCapacity = Math.min(newCapacity, maxCapacity);
        if (newCapacity != this.elements.length) {
            this.elements = (Recycler$DefaultHandle[]) Arrays.copyOf(this.elements, newCapacity);
        }
        return newCapacity;
    }

    Recycler$DefaultHandle<T> pop() {
        int size = this.size;
        if (size == 0) {
            if (!scavenge()) {
                return null;
            }
            size = this.size;
        }
        size--;
        Recycler$DefaultHandle<T> ret = this.elements[size];
        this.elements[size] = null;
        if (ret.lastRecycledId != ret.recycleId) {
            throw new IllegalStateException("recycled multiple times");
        }
        ret.recycleId = 0;
        ret.lastRecycledId = 0;
        this.size = size;
        return ret;
    }

    boolean scavenge() {
        if (scavengeSome()) {
            return true;
        }
        this.prev = null;
        this.cursor = this.head;
        return false;
    }

    boolean scavengeSome() {
        Recycler$WeakOrderQueue cursor = this.cursor;
        if (cursor == null) {
            cursor = this.head;
            if (cursor == null) {
                return false;
            }
        }
        boolean success = false;
        Recycler$WeakOrderQueue prev = this.prev;
        while (!cursor.transfer(this)) {
            Recycler$WeakOrderQueue next = cursor.next;
            if (cursor.owner.get() == null) {
                if (cursor.hasFinalData()) {
                    while (cursor.transfer(this)) {
                        success = true;
                    }
                }
                if (prev != null) {
                    prev.next = next;
                }
            } else {
                prev = cursor;
            }
            cursor = next;
            if (cursor != null) {
                if (success) {
                    break;
                }
            }
            break;
        }
        success = true;
        this.prev = prev;
        this.cursor = cursor;
        return success;
    }

    void push(Recycler$DefaultHandle<?> item) {
        Thread currentThread = Thread.currentThread();
        if (this.thread == currentThread) {
            pushNow(item);
        } else {
            pushLater(item, currentThread);
        }
    }

    private void pushNow(Recycler$DefaultHandle<?> item) {
        if ((item.recycleId | item.lastRecycledId) != 0) {
            throw new IllegalStateException("recycled already");
        }
        item.recycleId = item.lastRecycledId = Recycler.access$2000();
        int size = this.size;
        if (size < this.maxCapacity && !dropHandle(item)) {
            if (size == this.elements.length) {
                this.elements = (Recycler$DefaultHandle[]) Arrays.copyOf(this.elements, Math.min(size << 1, this.maxCapacity));
            }
            this.elements[size] = item;
            this.size = size + 1;
        }
    }

    private void pushLater(Recycler$DefaultHandle<?> item, Thread thread) {
        Map<Recycler$Stack<?>, Recycler$WeakOrderQueue> delayedRecycled = (Map) Recycler.access$2100().get();
        Recycler$WeakOrderQueue queue = (Recycler$WeakOrderQueue) delayedRecycled.get(this);
        if (queue == null) {
            if (delayedRecycled.size() >= this.maxDelayedQueues) {
                delayedRecycled.put(this, Recycler$WeakOrderQueue.DUMMY);
                return;
            }
            queue = Recycler$WeakOrderQueue.allocate(this, thread);
            if (queue != null) {
                delayedRecycled.put(this, queue);
            } else {
                return;
            }
        } else if (queue == Recycler$WeakOrderQueue.DUMMY) {
            return;
        }
        queue.add(item);
    }

    boolean dropHandle(Recycler$DefaultHandle<?> handle) {
        if (!handle.hasBeenRecycled) {
            int i = this.handleRecycleCount + 1;
            this.handleRecycleCount = i;
            if ((i & this.ratioMask) != 0) {
                return true;
            }
            handle.hasBeenRecycled = true;
        }
        return false;
    }

    Recycler$DefaultHandle<T> newHandle() {
        return new Recycler$DefaultHandle(this);
    }
}
