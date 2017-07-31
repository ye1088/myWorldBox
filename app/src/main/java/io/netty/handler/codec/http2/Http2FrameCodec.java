package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.HttpServerUpgradeHandler.UpgradeEvent;
import io.netty.handler.codec.http2.Http2Exception.StreamException;
import io.netty.handler.codec.http2.HttpConversionUtil.ExtensionHeaderNames;
import io.netty.handler.logging.LogLevel;
import io.netty.util.ReferenceCountUtil;

public class Http2FrameCodec extends ChannelDuplexHandler {
    private static final Http2FrameLogger HTTP2_FRAME_LOGGER = new Http2FrameLogger(LogLevel.INFO, Http2FrameCodec.class);
    private ChannelHandlerContext ctx;
    private final Http2ConnectionHandler http2Handler;
    private ChannelHandlerContext http2HandlerCtx;

    private final class ConnectionListener extends Http2ConnectionAdapter {
        private ConnectionListener() {
        }

        public void onStreamActive(Http2Stream stream) {
            if (Http2FrameCodec.this.ctx != null) {
                Http2FrameCodec.this.ctx.fireUserEventTriggered(new Http2StreamActiveEvent(stream.id()));
            }
        }

        public void onStreamClosed(Http2Stream stream) {
            Http2FrameCodec.this.ctx.fireUserEventTriggered(new Http2StreamClosedEvent(stream.id()));
        }

        public void onGoAwayReceived(int lastStreamId, long errorCode, ByteBuf debugData) {
            Http2FrameCodec.this.ctx.fireChannelRead(new DefaultHttp2GoAwayFrame(lastStreamId, errorCode, debugData));
        }
    }

    private final class FrameListener extends Http2FrameAdapter {
        private FrameListener() {
        }

        public void onRstStreamRead(ChannelHandlerContext ctx, int streamId, long errorCode) {
            Http2ResetFrame rstFrame = new DefaultHttp2ResetFrame(errorCode);
            rstFrame.setStreamId(streamId);
            ctx.fireChannelRead(rstFrame);
        }

        public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endStream) {
            onHeadersRead(ctx, streamId, headers, padding, endStream);
        }

        public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endOfStream) {
            Http2HeadersFrame headersFrame = new DefaultHttp2HeadersFrame(headers, endOfStream, padding);
            headersFrame.setStreamId(streamId);
            ctx.fireChannelRead(headersFrame);
        }

        public int onDataRead(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream) {
            Http2DataFrame dataFrame = new DefaultHttp2DataFrame(data.retain(), endOfStream, padding);
            dataFrame.setStreamId(streamId);
            ctx.fireChannelRead(dataFrame);
            return 0;
        }
    }

    private static final class InternalHttp2ConnectionHandler extends Http2ConnectionHandler {
        InternalHttp2ConnectionHandler(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder, Http2Settings initialSettings) {
            super(decoder, encoder, initialSettings);
        }

        protected void onStreamError(ChannelHandlerContext ctx, Throwable cause, StreamException http2Ex) {
            try {
                if (connection().stream(http2Ex.streamId()) != null) {
                    ctx.fireExceptionCaught(http2Ex);
                    super.onStreamError(ctx, cause, http2Ex);
                }
            } finally {
                super.onStreamError(ctx, cause, http2Ex);
            }
        }
    }

    public Http2FrameCodec(boolean server) {
        this(server, new DefaultHttp2FrameWriter());
    }

    Http2FrameCodec(boolean server, Http2FrameWriter frameWriter) {
        Http2Connection connection = new DefaultHttp2Connection(server);
        Http2ConnectionEncoder encoder = new DefaultHttp2ConnectionEncoder(connection, new Http2OutboundFrameLogger(frameWriter, HTTP2_FRAME_LOGGER));
        Http2ConnectionDecoder decoder = new DefaultHttp2ConnectionDecoder(connection, encoder, new Http2InboundFrameLogger(new DefaultHttp2FrameReader(), HTTP2_FRAME_LOGGER));
        decoder.frameListener(new FrameListener());
        this.http2Handler = new InternalHttp2ConnectionHandler(decoder, encoder, new Http2Settings());
        this.http2Handler.connection().addListener(new ConnectionListener());
    }

    Http2ConnectionHandler connectionHandler() {
        return this.http2Handler;
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        ctx.pipeline().addBefore(ctx.executor(), ctx.name(), null, this.http2Handler);
        this.http2HandlerCtx = ctx.pipeline().context(this.http2Handler);
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().remove(this.http2Handler);
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof UpgradeEvent) {
            UpgradeEvent upgrade = (UpgradeEvent) evt;
            ctx.fireUserEventTriggered(upgrade.retain());
            try {
                new ConnectionListener().onStreamActive(this.http2Handler.connection().stream(1));
                upgrade.upgradeRequest().headers().setInt(ExtensionHeaderNames.STREAM_ID.text(), 1);
                new InboundHttpToHttp2Adapter(this.http2Handler.connection(), this.http2Handler.decoder().frameListener()).channelRead(ctx, upgrade.upgradeRequest().retain());
            } finally {
                upgrade.release();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.fireExceptionCaught(cause);
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        if (msg instanceof Http2Frame) {
            try {
                if (msg instanceof Http2WindowUpdateFrame) {
                    Http2WindowUpdateFrame frame = (Http2WindowUpdateFrame) msg;
                    consumeBytes(frame.streamId(), frame.windowSizeIncrement(), promise);
                } else if (msg instanceof Http2StreamFrame) {
                    writeStreamFrame((Http2StreamFrame) msg, promise);
                } else if (msg instanceof Http2GoAwayFrame) {
                    writeGoAwayFrame((Http2GoAwayFrame) msg, promise);
                } else {
                    throw new UnsupportedMessageTypeException(msg, new Class[0]);
                }
                ReferenceCountUtil.release(msg);
            } catch (Throwable th) {
                ReferenceCountUtil.release(msg);
            }
        } else {
            ctx.write(msg, promise);
        }
    }

    private void consumeBytes(int streamId, int bytes, ChannelPromise promise) {
        try {
            ((Http2LocalFlowController) this.http2Handler.connection().local().flowController()).consumeBytes(this.http2Handler.connection().stream(streamId), bytes);
            promise.setSuccess();
        } catch (Throwable t) {
            promise.setFailure(t);
        }
    }

    private void writeGoAwayFrame(Http2GoAwayFrame frame, ChannelPromise promise) {
        if (frame.lastStreamId() > -1) {
            throw new IllegalArgumentException("Last stream id must not be set on GOAWAY frame");
        }
        int lastStreamCreated = this.http2Handler.connection().remote().lastStreamCreated();
        int lastStreamId = lastStreamCreated + (frame.extraStreamIds() * 2);
        if (lastStreamId < lastStreamCreated) {
            lastStreamId = Integer.MAX_VALUE;
        }
        this.http2Handler.goAway(this.http2HandlerCtx, lastStreamId, frame.errorCode(), frame.content().retain(), promise);
    }

    private void writeStreamFrame(Http2StreamFrame frame, ChannelPromise promise) {
        int streamId = frame.streamId();
        if (frame instanceof Http2DataFrame) {
            Http2DataFrame dataFrame = (Http2DataFrame) frame;
            this.http2Handler.encoder().writeData(this.http2HandlerCtx, streamId, dataFrame.content().retain(), dataFrame.padding(), dataFrame.isEndStream(), promise);
        } else if (frame instanceof Http2HeadersFrame) {
            Http2HeadersFrame headerFrame = (Http2HeadersFrame) frame;
            this.http2Handler.encoder().writeHeaders(this.http2HandlerCtx, streamId, headerFrame.headers(), headerFrame.padding(), headerFrame.isEndStream(), promise);
        } else if (frame instanceof Http2ResetFrame) {
            this.http2Handler.resetStream(this.http2HandlerCtx, streamId, ((Http2ResetFrame) frame).errorCode(), promise);
        } else {
            throw new UnsupportedMessageTypeException(frame, new Class[0]);
        }
    }
}
