package io.netty.buffer;

public interface PoolSubpageMetric {
    int elementSize();

    int maxNumElements();

    int numAvailable();

    int pageSize();
}
