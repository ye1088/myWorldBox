package io.netty.buffer;

public interface PoolChunkMetric {
    int chunkSize();

    int freeBytes();

    int usage();
}
