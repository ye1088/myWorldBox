package io.netty.channel.epoll;

import io.netty.util.internal.PlatformDependent;

final class EpollEventArray {
    private static final int EPOLL_DATA_OFFSET = Native.offsetofEpollData();
    private static final int EPOLL_EVENT_SIZE = Native.sizeofEpollEvent();
    private int length;
    private long memoryAddress;

    EpollEventArray(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("length must be >= 1 but was " + length);
        }
        this.length = length;
        this.memoryAddress = allocate(length);
    }

    private static long allocate(int length) {
        return PlatformDependent.allocateMemory((long) (EPOLL_EVENT_SIZE * length));
    }

    long memoryAddress() {
        return this.memoryAddress;
    }

    int length() {
        return this.length;
    }

    void increase() {
        this.length <<= 1;
        free();
        this.memoryAddress = allocate(this.length);
    }

    void free() {
        PlatformDependent.freeMemory(this.memoryAddress);
    }

    int events(int index) {
        return PlatformDependent.getInt(this.memoryAddress + ((long) (EPOLL_EVENT_SIZE * index)));
    }

    int fd(int index) {
        return PlatformDependent.getInt((this.memoryAddress + ((long) (EPOLL_EVENT_SIZE * index))) + ((long) EPOLL_DATA_OFFSET));
    }
}
