package io.netty.util.internal;

public interface PriorityQueueNode<T> extends Comparable<T> {
    public static final int INDEX_NOT_IN_QUEUE = -1;

    int priorityQueueIndex();

    void priorityQueueIndex(int i);
}
