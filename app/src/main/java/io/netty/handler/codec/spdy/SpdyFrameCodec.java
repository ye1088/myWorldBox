package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import java.net.SocketAddress;
import java.util.List;

public class SpdyFrameCodec extends ByteToMessageDecoder implements ChannelOutboundHandler, SpdyFrameDecoderDelegate {
    private static final SpdyProtocolException INVALID_FRAME = new SpdyProtocolException("Received invalid frame");
    private ChannelHandlerContext ctx;
    private boolean read;
    private final SpdyFrameDecoder spdyFrameDecoder;
    private final SpdyFrameEncoder spdyFrameEncoder;
    private final SpdyHeaderBlockDecoder spdyHeaderBlockDecoder;
    private final SpdyHeaderBlockEncoder spdyHeaderBlockEncoder;
    private SpdyHeadersFrame spdyHeadersFrame;
    private SpdySettingsFrame spdySettingsFrame;
    private final boolean validateHeaders;

    public SpdyFrameCodec(SpdyVersion version) {
        this(version, true);
    }

    public SpdyFrameCodec(SpdyVersion version, boolean validateHeaders) {
        this(version, 8192, 16384, 6, 15, 8, validateHeaders);
    }

    public SpdyFrameCodec(SpdyVersion version, int maxChunkSize, int maxHeaderSize, int compressionLevel, int windowBits, int memLevel) {
        this(version, maxChunkSize, maxHeaderSize, compressionLevel, windowBits, memLevel, true);
    }

    public SpdyFrameCodec(SpdyVersion version, int maxChunkSize, int maxHeaderSize, int compressionLevel, int windowBits, int memLevel, boolean validateHeaders) {
        this(version, maxChunkSize, SpdyHeaderBlockDecoder.newInstance(version, maxHeaderSize), SpdyHeaderBlockEncoder.newInstance(version, compressionLevel, windowBits, memLevel), validateHeaders);
    }

    protected SpdyFrameCodec(SpdyVersion version, int maxChunkSize, SpdyHeaderBlockDecoder spdyHeaderBlockDecoder, SpdyHeaderBlockEncoder spdyHeaderBlockEncoder, boolean validateHeaders) {
        this.spdyFrameDecoder = new SpdyFrameDecoder(version, this, maxChunkSize);
        this.spdyFrameEncoder = new SpdyFrameEncoder(version);
        this.spdyHeaderBlockDecoder = spdyHeaderBlockDecoder;
        this.spdyHeaderBlockEncoder = spdyHeaderBlockEncoder;
        this.validateHeaders = validateHeaders;
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        this.ctx = ctx;
        ctx.channel().closeFuture().addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                SpdyFrameCodec.this.spdyHeaderBlockDecoder.end();
                SpdyFrameCodec.this.spdyHeaderBlockEncoder.end();
            }
        });
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        this.spdyFrameDecoder.decode(in);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if (!(this.read || ctx.channel().config().isAutoRead())) {
            ctx.read();
        }
        this.read = false;
        super.channelReadComplete(ctx);
    }

    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }

    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.connect(remoteAddress, localAddress, promise);
    }

    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.disconnect(promise);
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.close(promise);
    }

    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.deregister(promise);
    }

    public void read(ChannelHandlerContext ctx) throws Exception {
        ctx.read();
    }

    public void flush(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf headerBlock;
        ByteBuf frame;
        if (msg instanceof SpdyDataFrame) {
            SpdyDataFrame spdyDataFrame = (SpdyDataFrame) msg;
            frame = this.spdyFrameEncoder.encodeDataFrame(ctx.alloc(), spdyDataFrame.streamId(), spdyDataFrame.isLast(), spdyDataFrame.content());
            spdyDataFrame.release();
            ctx.write(frame, promise);
        } else if (msg instanceof SpdySynStreamFrame) {
            SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame) msg;
            headerBlock = this.spdyHeaderBlockEncoder.encode(ctx.alloc(), spdySynStreamFrame);
            try {
                frame = this.spdyFrameEncoder.encodeSynStreamFrame(ctx.alloc(), spdySynStreamFrame.streamId(), spdySynStreamFrame.associatedStreamId(), spdySynStreamFrame.priority(), spdySynStreamFrame.isLast(), spdySynStreamFrame.isUnidirectional(), headerBlock);
                ctx.write(frame, promise);
            } finally {
                headerBlock.release();
            }
        } else if (msg instanceof SpdySynReplyFrame) {
            SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame) msg;
            headerBlock = this.spdyHeaderBlockEncoder.encode(ctx.alloc(), spdySynReplyFrame);
            try {
                frame = this.spdyFrameEncoder.encodeSynReplyFrame(ctx.alloc(), spdySynReplyFrame.streamId(), spdySynReplyFrame.isLast(), headerBlock);
                ctx.write(frame, promise);
            } finally {
                headerBlock.release();
            }
        } else if (msg instanceof SpdyRstStreamFrame) {
            SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame) msg;
            ctx.write(this.spdyFrameEncoder.encodeRstStreamFrame(ctx.alloc(), spdyRstStreamFrame.streamId(), spdyRstStreamFrame.status().code()), promise);
        } else if (msg instanceof SpdySettingsFrame) {
            ctx.write(this.spdyFrameEncoder.encodeSettingsFrame(ctx.alloc(), (SpdySettingsFrame) msg), promise);
        } else if (msg instanceof SpdyPingFrame) {
            ChannelHandlerContext channelHandlerContext = ctx;
            channelHandlerContext.write(this.spdyFrameEncoder.encodePingFrame(ctx.alloc(), ((SpdyPingFrame) msg).id()), promise);
        } else if (msg instanceof SpdyGoAwayFrame) {
            SpdyGoAwayFrame spdyGoAwayFrame = (SpdyGoAwayFrame) msg;
            ctx.write(this.spdyFrameEncoder.encodeGoAwayFrame(ctx.alloc(), spdyGoAwayFrame.lastGoodStreamId(), spdyGoAwayFrame.status().code()), promise);
        } else if (msg instanceof SpdyHeadersFrame) {
            SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame) msg;
            headerBlock = this.spdyHeaderBlockEncoder.encode(ctx.alloc(), spdyHeadersFrame);
            try {
                frame = this.spdyFrameEncoder.encodeHeadersFrame(ctx.alloc(), spdyHeadersFrame.streamId(), spdyHeadersFrame.isLast(), headerBlock);
                ctx.write(frame, promise);
            } finally {
                headerBlock.release();
            }
        } else if (msg instanceof SpdyWindowUpdateFrame) {
            SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame) msg;
            ctx.write(this.spdyFrameEncoder.encodeWindowUpdateFrame(ctx.alloc(), spdyWindowUpdateFrame.streamId(), spdyWindowUpdateFrame.deltaWindowSize()), promise);
        } else {
            throw new UnsupportedMessageTypeException(msg, new Class[0]);
        }
    }

    public void readDataFrame(int streamId, boolean last, ByteBuf data) {
        this.read = true;
        SpdyDataFrame spdyDataFrame = new DefaultSpdyDataFrame(streamId, data);
        spdyDataFrame.setLast(last);
        this.ctx.fireChannelRead(spdyDataFrame);
    }

    public void readSynStreamFrame(int streamId, int associatedToStreamId, byte priority, boolean last, boolean unidirectional) {
        SpdySynStreamFrame spdySynStreamFrame = new DefaultSpdySynStreamFrame(streamId, associatedToStreamId, priority, this.validateHeaders);
        spdySynStreamFrame.setLast(last);
        spdySynStreamFrame.setUnidirectional(unidirectional);
        this.spdyHeadersFrame = spdySynStreamFrame;
    }

    public void readSynReplyFrame(int streamId, boolean last) {
        SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamId, this.validateHeaders);
        spdySynReplyFrame.setLast(last);
        this.spdyHeadersFrame = spdySynReplyFrame;
    }

    public void readRstStreamFrame(int streamId, int statusCode) {
        this.read = true;
        this.ctx.fireChannelRead(new DefaultSpdyRstStreamFrame(streamId, statusCode));
    }

    public void readSettingsFrame(boolean clearPersisted) {
        this.read = true;
        this.spdySettingsFrame = new DefaultSpdySettingsFrame();
        this.spdySettingsFrame.setClearPreviouslyPersistedSettings(clearPersisted);
    }

    public void readSetting(int id, int value, boolean persistValue, boolean persisted) {
        this.spdySettingsFrame.setValue(id, value, persistValue, persisted);
    }

    public void readSettingsEnd() {
        this.read = true;
        SpdySettingsFrame frame = this.spdySettingsFrame;
        this.spdySettingsFrame = null;
        this.ctx.fireChannelRead(frame);
    }

    public void readPingFrame(int id) {
        this.read = true;
        this.ctx.fireChannelRead(new DefaultSpdyPingFrame(id));
    }

    public void readGoAwayFrame(int lastGoodStreamId, int statusCode) {
        this.read = true;
        this.ctx.fireChannelRead(new DefaultSpdyGoAwayFrame(lastGoodStreamId, statusCode));
    }

    public void readHeadersFrame(int streamId, boolean last) {
        this.spdyHeadersFrame = new DefaultSpdyHeadersFrame(streamId, this.validateHeaders);
        this.spdyHeadersFrame.setLast(last);
    }

    public void readWindowUpdateFrame(int streamId, int deltaWindowSize) {
        this.read = true;
        this.ctx.fireChannelRead(new DefaultSpdyWindowUpdateFrame(streamId, deltaWindowSize));
    }

    public void readHeaderBlock(ByteBuf headerBlock) {
        try {
            this.spdyHeaderBlockDecoder.decode(this.ctx.alloc(), headerBlock, this.spdyHeadersFrame);
        } catch (Exception e) {
            this.ctx.fireExceptionCaught(e);
        } finally {
            headerBlock.release();
        }
    }

    public void readHeaderBlockEnd() {
        Object obj = null;
        try {
            this.spdyHeaderBlockDecoder.endHeaderBlock(this.spdyHeadersFrame);
            obj = this.spdyHeadersFrame;
            this.spdyHeadersFrame = null;
        } catch (Exception e) {
            this.ctx.fireExceptionCaught(e);
        }
        if (obj != null) {
            this.read = true;
            this.ctx.fireChannelRead(obj);
        }
    }

    public void readFrameError(String message) {
        this.ctx.fireExceptionCaught(INVALID_FRAME);
    }
}
