package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LogLevel;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class Http2FrameLogger extends ChannelHandlerAdapter {
    private static final int BUFFER_LENGTH_THRESHOLD = 64;
    private final InternalLogLevel level;
    private final InternalLogger logger;

    public enum Direction {
        INBOUND,
        OUTBOUND
    }

    public Http2FrameLogger(LogLevel level) {
        this(level.toInternalLevel(), InternalLoggerFactory.getInstance(Http2FrameLogger.class));
    }

    public Http2FrameLogger(LogLevel level, String name) {
        this(level.toInternalLevel(), InternalLoggerFactory.getInstance(name));
    }

    public Http2FrameLogger(LogLevel level, Class<?> clazz) {
        this(level.toInternalLevel(), InternalLoggerFactory.getInstance((Class) clazz));
    }

    private Http2FrameLogger(InternalLogLevel level, InternalLogger logger) {
        this.level = (InternalLogLevel) ObjectUtil.checkNotNull(level, "level");
        this.logger = (InternalLogger) ObjectUtil.checkNotNull(logger, "logger");
    }

    public void logData(Direction direction, ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endStream) {
        if (enabled()) {
            log(direction, "%s DATA: streamId=%d, padding=%d, endStream=%b, length=%d, bytes=%s", ctx.channel(), Integer.valueOf(streamId), Integer.valueOf(padding), Boolean.valueOf(endStream), Integer.valueOf(data.readableBytes()), toString(data));
        }
    }

    public void logHeaders(Direction direction, ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endStream) {
        if (enabled()) {
            log(direction, "%s HEADERS: streamId=%d, headers=%s, padding=%d, endStream=%b", ctx.channel(), Integer.valueOf(streamId), headers, Integer.valueOf(padding), Boolean.valueOf(endStream));
        }
    }

    public void logHeaders(Direction direction, ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endStream) {
        if (enabled()) {
            log(direction, "%s HEADERS: streamId=%d, headers=%s, streamDependency=%d, weight=%d, exclusive=%b, padding=%d, endStream=%b", ctx.channel(), Integer.valueOf(streamId), headers, Integer.valueOf(streamDependency), Short.valueOf(weight), Boolean.valueOf(exclusive), Integer.valueOf(padding), Boolean.valueOf(endStream));
        }
    }

    public void logPriority(Direction direction, ChannelHandlerContext ctx, int streamId, int streamDependency, short weight, boolean exclusive) {
        if (enabled()) {
            log(direction, "%s PRIORITY: streamId=%d, streamDependency=%d, weight=%d, exclusive=%b", ctx.channel(), Integer.valueOf(streamId), Integer.valueOf(streamDependency), Short.valueOf(weight), Boolean.valueOf(exclusive));
        }
    }

    public void logRstStream(Direction direction, ChannelHandlerContext ctx, int streamId, long errorCode) {
        if (enabled()) {
            log(direction, "%s RST_STREAM: streamId=%d, errorCode=%d", ctx.channel(), Integer.valueOf(streamId), Long.valueOf(errorCode));
        }
    }

    public void logSettingsAck(Direction direction, ChannelHandlerContext ctx) {
        if (enabled()) {
            log(direction, "%s SETTINGS: ack=true", ctx.channel());
        }
    }

    public void logSettings(Direction direction, ChannelHandlerContext ctx, Http2Settings settings) {
        if (enabled()) {
            log(direction, "%s SETTINGS: ack=false, settings=%s", ctx.channel(), settings);
        }
    }

    public void logPing(Direction direction, ChannelHandlerContext ctx, ByteBuf data) {
        if (enabled()) {
            log(direction, "%s PING: ack=false, length=%d, bytes=%s", ctx.channel(), Integer.valueOf(data.readableBytes()), toString(data));
        }
    }

    public void logPingAck(Direction direction, ChannelHandlerContext ctx, ByteBuf data) {
        if (enabled()) {
            log(direction, "%s PING: ack=true, length=%d, bytes=%s", ctx.channel(), Integer.valueOf(data.readableBytes()), toString(data));
        }
    }

    public void logPushPromise(Direction direction, ChannelHandlerContext ctx, int streamId, int promisedStreamId, Http2Headers headers, int padding) {
        if (enabled()) {
            log(direction, "%s PUSH_PROMISE: streamId=%d, promisedStreamId=%d, headers=%s, padding=%d", ctx.channel(), Integer.valueOf(streamId), Integer.valueOf(promisedStreamId), headers, Integer.valueOf(padding));
        }
    }

    public void logGoAway(Direction direction, ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData) {
        if (enabled()) {
            log(direction, "%s GO_AWAY: lastStreamId=%d, errorCode=%d, length=%d, bytes=%s", ctx.channel(), Integer.valueOf(lastStreamId), Long.valueOf(errorCode), Integer.valueOf(debugData.readableBytes()), toString(debugData));
        }
    }

    public void logWindowsUpdate(Direction direction, ChannelHandlerContext ctx, int streamId, int windowSizeIncrement) {
        if (enabled()) {
            log(direction, "%s WINDOW_UPDATE: streamId=%d, windowSizeIncrement=%d", ctx.channel(), Integer.valueOf(streamId), Integer.valueOf(windowSizeIncrement));
        }
    }

    public void logUnknownFrame(Direction direction, ChannelHandlerContext ctx, byte frameType, int streamId, Http2Flags flags, ByteBuf data) {
        if (enabled()) {
            log(direction, "%s UNKNOWN: frameType=%d, streamId=%d, flags=%d, length=%d, bytes=%s", ctx.channel(), Integer.valueOf(frameType & 255), Integer.valueOf(streamId), Short.valueOf(flags.value()), Integer.valueOf(data.readableBytes()), toString(data));
        }
    }

    private boolean enabled() {
        return this.logger.isEnabled(this.level);
    }

    private String toString(ByteBuf buf) {
        if (this.level == InternalLogLevel.TRACE || buf.readableBytes() <= 64) {
            return ByteBufUtil.hexDump(buf);
        }
        return ByteBufUtil.hexDump(buf, buf.readerIndex(), Math.min(buf.readableBytes(), 64)) + "...";
    }

    private void log(Direction direction, String format, Object... args) {
        StringBuilder b = new StringBuilder(200);
        b.append("\n----------------").append(direction.name()).append("--------------------\n").append(String.format(format, args)).append("\n------------------------------------");
        this.logger.log(this.level, b.toString());
    }
}
