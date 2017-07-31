package io.netty.channel;

import io.netty.util.concurrent.EventExecutorGroup;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public interface ChannelPipeline extends ChannelInboundInvoker, ChannelOutboundInvoker, Iterable<Entry<String, ChannelHandler>> {
    ChannelPipeline addAfter(EventExecutorGroup eventExecutorGroup, String str, String str2, ChannelHandler channelHandler);

    ChannelPipeline addAfter(String str, String str2, ChannelHandler channelHandler);

    ChannelPipeline addBefore(EventExecutorGroup eventExecutorGroup, String str, String str2, ChannelHandler channelHandler);

    ChannelPipeline addBefore(String str, String str2, ChannelHandler channelHandler);

    ChannelPipeline addFirst(EventExecutorGroup eventExecutorGroup, String str, ChannelHandler channelHandler);

    ChannelPipeline addFirst(EventExecutorGroup eventExecutorGroup, ChannelHandler... channelHandlerArr);

    ChannelPipeline addFirst(String str, ChannelHandler channelHandler);

    ChannelPipeline addFirst(ChannelHandler... channelHandlerArr);

    ChannelPipeline addLast(EventExecutorGroup eventExecutorGroup, String str, ChannelHandler channelHandler);

    ChannelPipeline addLast(EventExecutorGroup eventExecutorGroup, ChannelHandler... channelHandlerArr);

    ChannelPipeline addLast(String str, ChannelHandler channelHandler);

    ChannelPipeline addLast(ChannelHandler... channelHandlerArr);

    Channel channel();

    ChannelHandlerContext context(ChannelHandler channelHandler);

    ChannelHandlerContext context(Class<? extends ChannelHandler> cls);

    ChannelHandlerContext context(String str);

    ChannelPipeline fireChannelActive();

    ChannelPipeline fireChannelInactive();

    ChannelPipeline fireChannelRead(Object obj);

    ChannelPipeline fireChannelReadComplete();

    ChannelPipeline fireChannelRegistered();

    ChannelPipeline fireChannelUnregistered();

    ChannelPipeline fireChannelWritabilityChanged();

    ChannelPipeline fireExceptionCaught(Throwable th);

    ChannelPipeline fireUserEventTriggered(Object obj);

    ChannelHandler first();

    ChannelHandlerContext firstContext();

    ChannelPipeline flush();

    <T extends ChannelHandler> T get(Class<T> cls);

    ChannelHandler get(String str);

    ChannelHandler last();

    ChannelHandlerContext lastContext();

    List<String> names();

    <T extends ChannelHandler> T remove(Class<T> cls);

    ChannelHandler remove(String str);

    ChannelPipeline remove(ChannelHandler channelHandler);

    ChannelHandler removeFirst();

    ChannelHandler removeLast();

    <T extends ChannelHandler> T replace(Class<T> cls, String str, ChannelHandler channelHandler);

    ChannelHandler replace(String str, String str2, ChannelHandler channelHandler);

    ChannelPipeline replace(ChannelHandler channelHandler, String str, ChannelHandler channelHandler2);

    Map<String, ChannelHandler> toMap();
}
