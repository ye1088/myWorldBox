package io.netty.channel.nio;

import io.netty.channel.Channel.Unsafe;
import java.nio.channels.SelectableChannel;

public interface AbstractNioChannel$NioUnsafe extends Unsafe {
    SelectableChannel ch();

    void finishConnect();

    void forceFlush();

    void read();
}
