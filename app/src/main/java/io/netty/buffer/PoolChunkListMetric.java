package io.netty.buffer;

public interface PoolChunkListMetric extends Iterable<PoolChunkMetric> {
    int maxUsage();

    int minUsage();
}
