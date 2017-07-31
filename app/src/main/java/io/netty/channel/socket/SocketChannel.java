package io.netty.channel.socket;

import java.net.InetSocketAddress;

public interface SocketChannel extends DuplexChannel {
    SocketChannelConfig config();

    InetSocketAddress localAddress();

    ServerSocketChannel parent();

    InetSocketAddress remoteAddress();
}
