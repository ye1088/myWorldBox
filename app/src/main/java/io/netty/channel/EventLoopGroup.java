package io.netty.channel;

import io.netty.util.concurrent.EventExecutorGroup;

public interface EventLoopGroup extends EventExecutorGroup {
    EventLoop next();

    ChannelFuture register(Channel channel);

    @Deprecated
    ChannelFuture register(Channel channel, ChannelPromise channelPromise);

    ChannelFuture register(ChannelPromise channelPromise);
}
