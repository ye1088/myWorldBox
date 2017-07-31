package io.netty.channel.pool;

public interface ChannelPoolMap<K, P extends ChannelPool> {
    boolean contains(K k);

    P get(K k);
}
