package io.netty.channel.unix;

import io.netty.channel.socket.DuplexChannel;

public interface DomainSocketChannel extends DuplexChannel, UnixChannel {
    DomainSocketChannelConfig config();

    DomainSocketAddress localAddress();

    DomainSocketAddress remoteAddress();
}
