package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.io.Closeable;
import java.util.List;

public interface Http2ConnectionDecoder extends Closeable {
    void close();

    Http2Connection connection();

    void decodeFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Http2Exception;

    Http2LocalFlowController flowController();

    Http2FrameListener frameListener();

    void frameListener(Http2FrameListener http2FrameListener);

    void lifecycleManager(Http2LifecycleManager http2LifecycleManager);

    Http2Settings localSettings();

    boolean prefaceReceived();
}
