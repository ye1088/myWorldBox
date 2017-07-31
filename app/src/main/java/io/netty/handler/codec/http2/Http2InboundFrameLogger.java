package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2FrameLogger.Direction;
import io.netty.handler.codec.http2.Http2FrameReader.Configuration;
import io.netty.util.internal.ObjectUtil;

public class Http2InboundFrameLogger implements Http2FrameReader {
    private final Http2FrameLogger logger;
    private final Http2FrameReader reader;

    public Http2InboundFrameLogger(Http2FrameReader reader, Http2FrameLogger logger) {
        this.reader = (Http2FrameReader) ObjectUtil.checkNotNull(reader, "reader");
        this.logger = (Http2FrameLogger) ObjectUtil.checkNotNull(logger, "logger");
    }

    public void readFrame(ChannelHandlerContext ctx, ByteBuf input, final Http2FrameListener listener) throws Http2Exception {
        this.reader.readFrame(ctx, input, new Http2FrameListener() {
            public int onDataRead(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logData(Direction.INBOUND, ctx, streamId, data, padding, endOfStream);
                return listener.onDataRead(ctx, streamId, data, padding, endOfStream);
            }

            public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endStream) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logHeaders(Direction.INBOUND, ctx, streamId, headers, padding, endStream);
                listener.onHeadersRead(ctx, streamId, headers, padding, endStream);
            }

            public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endStream) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logHeaders(Direction.INBOUND, ctx, streamId, headers, streamDependency, weight, exclusive, padding, endStream);
                listener.onHeadersRead(ctx, streamId, headers, streamDependency, weight, exclusive, padding, endStream);
            }

            public void onPriorityRead(ChannelHandlerContext ctx, int streamId, int streamDependency, short weight, boolean exclusive) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logPriority(Direction.INBOUND, ctx, streamId, streamDependency, weight, exclusive);
                listener.onPriorityRead(ctx, streamId, streamDependency, weight, exclusive);
            }

            public void onRstStreamRead(ChannelHandlerContext ctx, int streamId, long errorCode) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logRstStream(Direction.INBOUND, ctx, streamId, errorCode);
                listener.onRstStreamRead(ctx, streamId, errorCode);
            }

            public void onSettingsAckRead(ChannelHandlerContext ctx) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logSettingsAck(Direction.INBOUND, ctx);
                listener.onSettingsAckRead(ctx);
            }

            public void onSettingsRead(ChannelHandlerContext ctx, Http2Settings settings) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logSettings(Direction.INBOUND, ctx, settings);
                listener.onSettingsRead(ctx, settings);
            }

            public void onPingRead(ChannelHandlerContext ctx, ByteBuf data) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logPing(Direction.INBOUND, ctx, data);
                listener.onPingRead(ctx, data);
            }

            public void onPingAckRead(ChannelHandlerContext ctx, ByteBuf data) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logPingAck(Direction.INBOUND, ctx, data);
                listener.onPingAckRead(ctx, data);
            }

            public void onPushPromiseRead(ChannelHandlerContext ctx, int streamId, int promisedStreamId, Http2Headers headers, int padding) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logPushPromise(Direction.INBOUND, ctx, streamId, promisedStreamId, headers, padding);
                listener.onPushPromiseRead(ctx, streamId, promisedStreamId, headers, padding);
            }

            public void onGoAwayRead(ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logGoAway(Direction.INBOUND, ctx, lastStreamId, errorCode, debugData);
                listener.onGoAwayRead(ctx, lastStreamId, errorCode, debugData);
            }

            public void onWindowUpdateRead(ChannelHandlerContext ctx, int streamId, int windowSizeIncrement) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logWindowsUpdate(Direction.INBOUND, ctx, streamId, windowSizeIncrement);
                listener.onWindowUpdateRead(ctx, streamId, windowSizeIncrement);
            }

            public void onUnknownFrame(ChannelHandlerContext ctx, byte frameType, int streamId, Http2Flags flags, ByteBuf payload) throws Http2Exception {
                Http2InboundFrameLogger.this.logger.logUnknownFrame(Direction.INBOUND, ctx, frameType, streamId, flags, payload);
                listener.onUnknownFrame(ctx, frameType, streamId, flags, payload);
            }
        });
    }

    public void close() {
        this.reader.close();
    }

    public Configuration configuration() {
        return this.reader.configuration();
    }
}
