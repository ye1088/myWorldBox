package io.netty.channel.pool;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import java.io.Closeable;

public interface ChannelPool extends Closeable {
    Future<Channel> acquire();

    Future<Channel> acquire(Promise<Channel> promise);

    void close();

    Future<Void> release(Channel channel);

    Future<Void> release(Channel channel, Promise<Void> promise);
}
