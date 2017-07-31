package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2FrameReader.Configuration;
import io.netty.util.internal.PlatformDependent;
import org.bytedeco.javacpp.avutil;

public class DefaultHttp2FrameReader implements Http2FrameReader, Configuration, Http2FrameSizePolicy {
    private Http2Flags flags;
    private byte frameType;
    private HeadersContinuation headersContinuation;
    private final Http2HeadersDecoder headersDecoder;
    private int maxFrameSize;
    private int payloadLength;
    private boolean readError;
    private boolean readingHeaders;
    private int streamId;

    private abstract class HeadersContinuation {
        private final HeadersBlockBuilder builder;

        abstract int getStreamId();

        abstract void processFragment(boolean z, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception;

        private HeadersContinuation() {
            this.builder = new HeadersBlockBuilder();
        }

        final HeadersBlockBuilder headersBlockBuilder() {
            return this.builder;
        }

        final void close() {
            this.builder.close();
        }
    }

    protected class HeadersBlockBuilder {
        private ByteBuf headerBlock;

        protected HeadersBlockBuilder() {
        }

        private void headerSizeExceeded() throws Http2Exception {
            close();
            throw Http2Exception.connectionError(Http2Error.ENHANCE_YOUR_CALM, "Header size exceeded max allowed size (%d)", Integer.valueOf(DefaultHttp2FrameReader.this.headersDecoder.configuration().maxHeaderSize()));
        }

        final void addFragment(ByteBuf fragment, ByteBufAllocator alloc, boolean endOfHeaders) throws Http2Exception {
            if (this.headerBlock == null) {
                if (fragment.readableBytes() > DefaultHttp2FrameReader.this.headersDecoder.configuration().maxHeaderSize()) {
                    headerSizeExceeded();
                }
                if (endOfHeaders) {
                    this.headerBlock = fragment.retain();
                    return;
                }
                this.headerBlock = alloc.buffer(fragment.readableBytes());
                this.headerBlock.writeBytes(fragment);
                return;
            }
            if (DefaultHttp2FrameReader.this.headersDecoder.configuration().maxHeaderSize() - fragment.readableBytes() < this.headerBlock.readableBytes()) {
                headerSizeExceeded();
            }
            if (this.headerBlock.isWritable(fragment.readableBytes())) {
                this.headerBlock.writeBytes(fragment);
                return;
            }
            ByteBuf buf = alloc.buffer(this.headerBlock.readableBytes() + fragment.readableBytes());
            buf.writeBytes(this.headerBlock);
            buf.writeBytes(fragment);
            this.headerBlock.release();
            this.headerBlock = buf;
        }

        Http2Headers headers() throws Http2Exception {
            try {
                Http2Headers decodeHeaders = DefaultHttp2FrameReader.this.headersDecoder.decodeHeaders(this.headerBlock);
                return decodeHeaders;
            } finally {
                close();
            }
        }

        void close() {
            if (this.headerBlock != null) {
                this.headerBlock.release();
                this.headerBlock = null;
            }
            DefaultHttp2FrameReader.this.headersContinuation = null;
        }
    }

    public DefaultHttp2FrameReader() {
        this(true);
    }

    public DefaultHttp2FrameReader(boolean validateHeaders) {
        this(new DefaultHttp2HeadersDecoder(validateHeaders));
    }

    public DefaultHttp2FrameReader(Http2HeadersDecoder headersDecoder) {
        this.readingHeaders = true;
        this.headersDecoder = headersDecoder;
        this.maxFrameSize = 16384;
    }

    public Http2HeaderTable headerTable() {
        return this.headersDecoder.configuration().headerTable();
    }

    public Configuration configuration() {
        return this;
    }

    public Http2FrameSizePolicy frameSizePolicy() {
        return this;
    }

    public void maxFrameSize(int max) throws Http2Exception {
        if (Http2CodecUtil.isMaxFrameSizeValid(max)) {
            this.maxFrameSize = max;
        } else {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Invalid MAX_FRAME_SIZE specified in sent settings: %d", Integer.valueOf(max));
        }
    }

    public int maxFrameSize() {
        return this.maxFrameSize;
    }

    public void close() {
        if (this.headersContinuation != null) {
            this.headersContinuation.close();
        }
    }

    public void readFrame(ChannelHandlerContext ctx, ByteBuf input, Http2FrameListener listener) throws Http2Exception {
        boolean z = true;
        if (this.readError) {
            input.skipBytes(input.readableBytes());
            return;
        }
        do {
            try {
                if (this.readingHeaders) {
                    processHeaderState(input);
                    if (this.readingHeaders) {
                        return;
                    }
                }
                processPayloadState(ctx, input, listener);
                if (!this.readingHeaders) {
                    return;
                }
            } catch (Http2Exception e) {
                if (Http2Exception.isStreamError(e)) {
                    z = false;
                }
                this.readError = z;
                throw e;
            } catch (RuntimeException e2) {
                this.readError = true;
                throw e2;
            } catch (Throwable cause) {
                this.readError = true;
                PlatformDependent.throwException(cause);
                return;
            }
        } while (input.isReadable());
    }

    private void processHeaderState(ByteBuf in) throws Http2Exception {
        if (in.readableBytes() >= 9) {
            this.payloadLength = in.readUnsignedMedium();
            if (this.payloadLength > this.maxFrameSize) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Frame length: %d exceeds maximum: %d", Integer.valueOf(this.payloadLength), Integer.valueOf(this.maxFrameSize));
            }
            this.frameType = in.readByte();
            this.flags = new Http2Flags(in.readUnsignedByte());
            this.streamId = Http2CodecUtil.readUnsignedInt(in);
            this.readingHeaders = false;
            switch (this.frameType) {
                case (byte) 0:
                    verifyDataFrame();
                    return;
                case (byte) 1:
                    verifyHeadersFrame();
                    return;
                case (byte) 2:
                    verifyPriorityFrame();
                    return;
                case (byte) 3:
                    verifyRstStreamFrame();
                    return;
                case (byte) 4:
                    verifySettingsFrame();
                    return;
                case (byte) 5:
                    verifyPushPromiseFrame();
                    return;
                case (byte) 6:
                    verifyPingFrame();
                    return;
                case (byte) 7:
                    verifyGoAwayFrame();
                    return;
                case (byte) 8:
                    verifyWindowUpdateFrame();
                    return;
                case (byte) 9:
                    verifyContinuationFrame();
                    return;
                default:
                    return;
            }
        }
    }

    private void processPayloadState(ChannelHandlerContext ctx, ByteBuf in, Http2FrameListener listener) throws Http2Exception {
        if (in.readableBytes() >= this.payloadLength) {
            ByteBuf payload = in.readSlice(this.payloadLength);
            this.readingHeaders = true;
            switch (this.frameType) {
                case (byte) 0:
                    readDataFrame(ctx, payload, listener);
                    return;
                case (byte) 1:
                    readHeadersFrame(ctx, payload, listener);
                    return;
                case (byte) 2:
                    readPriorityFrame(ctx, payload, listener);
                    return;
                case (byte) 3:
                    readRstStreamFrame(ctx, payload, listener);
                    return;
                case (byte) 4:
                    readSettingsFrame(ctx, payload, listener);
                    return;
                case (byte) 5:
                    readPushPromiseFrame(ctx, payload, listener);
                    return;
                case (byte) 6:
                    readPingFrame(ctx, payload, listener);
                    return;
                case (byte) 7:
                    readGoAwayFrame(ctx, payload, listener);
                    return;
                case (byte) 8:
                    readWindowUpdateFrame(ctx, payload, listener);
                    return;
                case (byte) 9:
                    readContinuationFrame(payload, listener);
                    return;
                default:
                    readUnknownFrame(ctx, payload, listener);
                    return;
            }
        }
    }

    private void verifyDataFrame() throws Http2Exception {
        verifyNotProcessingHeaders();
        verifyPayloadLength(this.payloadLength);
        if (this.payloadLength < this.flags.getPaddingPresenceFieldLength()) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small.", Integer.valueOf(this.payloadLength));
        }
    }

    private void verifyHeadersFrame() throws Http2Exception {
        verifyNotProcessingHeaders();
        verifyPayloadLength(this.payloadLength);
        if (this.payloadLength < this.flags.getPaddingPresenceFieldLength() + this.flags.getNumPriorityBytes()) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame length too small." + this.payloadLength, new Object[0]);
        }
    }

    private void verifyPriorityFrame() throws Http2Exception {
        verifyNotProcessingHeaders();
        if (this.payloadLength != 5) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Invalid frame length %d.", Integer.valueOf(this.payloadLength));
        }
    }

    private void verifyRstStreamFrame() throws Http2Exception {
        verifyNotProcessingHeaders();
        if (this.payloadLength != 4) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Invalid frame length %d.", Integer.valueOf(this.payloadLength));
        }
    }

    private void verifySettingsFrame() throws Http2Exception {
        verifyNotProcessingHeaders();
        verifyPayloadLength(this.payloadLength);
        if (this.streamId != 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A stream ID must be zero.", new Object[0]);
        } else if (this.flags.ack() && this.payloadLength > 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Ack settings frame must have an empty payload.", new Object[0]);
        } else if (this.payloadLength % 6 > 0) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length %d invalid.", Integer.valueOf(this.payloadLength));
        }
    }

    private void verifyPushPromiseFrame() throws Http2Exception {
        verifyNotProcessingHeaders();
        verifyPayloadLength(this.payloadLength);
        if (this.payloadLength < this.flags.getPaddingPresenceFieldLength() + 4) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small.", Integer.valueOf(this.payloadLength));
        }
    }

    private void verifyPingFrame() throws Http2Exception {
        verifyNotProcessingHeaders();
        if (this.streamId != 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A stream ID must be zero.", new Object[0]);
        } else if (this.payloadLength != 8) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length %d incorrect size for ping.", Integer.valueOf(this.payloadLength));
        }
    }

    private void verifyGoAwayFrame() throws Http2Exception {
        verifyNotProcessingHeaders();
        verifyPayloadLength(this.payloadLength);
        if (this.streamId != 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A stream ID must be zero.", new Object[0]);
        } else if (this.payloadLength < 8) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small.", Integer.valueOf(this.payloadLength));
        }
    }

    private void verifyWindowUpdateFrame() throws Http2Exception {
        verifyNotProcessingHeaders();
        verifyStreamOrConnectionId(this.streamId, "Stream ID");
        if (this.payloadLength != 4) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Invalid frame length %d.", Integer.valueOf(this.payloadLength));
        }
    }

    private void verifyContinuationFrame() throws Http2Exception {
        verifyPayloadLength(this.payloadLength);
        if (this.headersContinuation == null) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Received %s frame but not currently processing headers.", Byte.valueOf(this.frameType));
        } else if (this.streamId != this.headersContinuation.getStreamId()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Continuation stream ID does not match pending headers. Expected %d, but received %d.", Integer.valueOf(this.headersContinuation.getStreamId()), Integer.valueOf(this.streamId));
        } else if (this.payloadLength < this.flags.getPaddingPresenceFieldLength()) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small for padding.", Integer.valueOf(this.payloadLength));
        }
    }

    private void readDataFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
        int padding = readPadding(payload);
        int dataLength = lengthWithoutTrailingPadding(payload.readableBytes(), padding);
        if (dataLength < 0) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame payload too small for padding.", new Object[0]);
        }
        ByteBuf data = payload.readSlice(dataLength);
        listener.onDataRead(ctx, this.streamId, data, padding, this.flags.endOfStream());
        payload.skipBytes(payload.readableBytes());
    }

    private void readHeadersFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
        final int headersStreamId = this.streamId;
        final Http2Flags headersFlags = this.flags;
        final int padding = readPadding(payload);
        if (this.flags.priorityPresent()) {
            long word1 = payload.readUnsignedInt();
            final boolean exclusive = (avutil.AV_CH_WIDE_LEFT & word1) != 0;
            final int streamDependency = (int) (2147483647L & word1);
            final short weight = (short) (payload.readUnsignedByte() + 1);
            ByteBuf fragment = payload.readSlice(lengthWithoutTrailingPadding(payload.readableBytes(), padding));
            final ChannelHandlerContext channelHandlerContext = ctx;
            this.headersContinuation = new HeadersContinuation() {
                public int getStreamId() {
                    return headersStreamId;
                }

                public void processFragment(boolean endOfHeaders, ByteBuf fragment, Http2FrameListener listener) throws Http2Exception {
                    HeadersBlockBuilder hdrBlockBuilder = headersBlockBuilder();
                    hdrBlockBuilder.addFragment(fragment, channelHandlerContext.alloc(), endOfHeaders);
                    if (endOfHeaders) {
                        listener.onHeadersRead(channelHandlerContext, headersStreamId, hdrBlockBuilder.headers(), streamDependency, weight, exclusive, padding, headersFlags.endOfStream());
                    }
                }
            };
            this.headersContinuation.processFragment(this.flags.endOfHeaders(), fragment, listener);
            return;
        }
        final int i = headersStreamId;
        final ChannelHandlerContext channelHandlerContext2 = ctx;
        final int i2 = padding;
        final Http2Flags http2Flags = headersFlags;
        this.headersContinuation = new HeadersContinuation() {
            public int getStreamId() {
                return i;
            }

            public void processFragment(boolean endOfHeaders, ByteBuf fragment, Http2FrameListener listener) throws Http2Exception {
                HeadersBlockBuilder hdrBlockBuilder = headersBlockBuilder();
                hdrBlockBuilder.addFragment(fragment, channelHandlerContext2.alloc(), endOfHeaders);
                if (endOfHeaders) {
                    listener.onHeadersRead(channelHandlerContext2, i, hdrBlockBuilder.headers(), i2, http2Flags.endOfStream());
                }
            }
        };
        this.headersContinuation.processFragment(this.flags.endOfHeaders(), payload.readSlice(lengthWithoutTrailingPadding(payload.readableBytes(), padding)), listener);
    }

    private void readPriorityFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
        long word1 = payload.readUnsignedInt();
        listener.onPriorityRead(ctx, this.streamId, (int) (2147483647L & word1), (short) (payload.readUnsignedByte() + 1), (avutil.AV_CH_WIDE_LEFT & word1) != 0);
    }

    private void readRstStreamFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
        listener.onRstStreamRead(ctx, this.streamId, payload.readUnsignedInt());
    }

    private void readSettingsFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
        if (this.flags.ack()) {
            listener.onSettingsAckRead(ctx);
            return;
        }
        int numSettings = this.payloadLength / 6;
        Http2Settings settings = new Http2Settings();
        int index = 0;
        while (index < numSettings) {
            char id = (char) payload.readUnsignedShort();
            try {
                settings.put(id, Long.valueOf(payload.readUnsignedInt()));
                index++;
            } catch (IllegalArgumentException e) {
                switch (id) {
                    case '\u0004':
                        throw Http2Exception.connectionError(Http2Error.FLOW_CONTROL_ERROR, e, e.getMessage(), new Object[0]);
                    case '\u0005':
                        throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, e, e.getMessage(), new Object[0]);
                    default:
                        throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, e, e.getMessage(), new Object[0]);
                }
            }
        }
        listener.onSettingsRead(ctx, settings);
    }

    private void readPushPromiseFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
        final int pushPromiseStreamId = this.streamId;
        final int padding = readPadding(payload);
        final int promisedStreamId = Http2CodecUtil.readUnsignedInt(payload);
        final ChannelHandlerContext channelHandlerContext = ctx;
        this.headersContinuation = new HeadersContinuation() {
            public int getStreamId() {
                return pushPromiseStreamId;
            }

            public void processFragment(boolean endOfHeaders, ByteBuf fragment, Http2FrameListener listener) throws Http2Exception {
                headersBlockBuilder().addFragment(fragment, channelHandlerContext.alloc(), endOfHeaders);
                if (endOfHeaders) {
                    listener.onPushPromiseRead(channelHandlerContext, pushPromiseStreamId, promisedStreamId, headersBlockBuilder().headers(), padding);
                }
            }
        };
        this.headersContinuation.processFragment(this.flags.endOfHeaders(), payload.readSlice(lengthWithoutTrailingPadding(payload.readableBytes(), padding)), listener);
    }

    private void readPingFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
        ByteBuf data = payload.readSlice(payload.readableBytes());
        if (this.flags.ack()) {
            listener.onPingAckRead(ctx, data);
        } else {
            listener.onPingRead(ctx, data);
        }
    }

    private static void readGoAwayFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
        listener.onGoAwayRead(ctx, Http2CodecUtil.readUnsignedInt(payload), payload.readUnsignedInt(), payload.readSlice(payload.readableBytes()));
    }

    private void readWindowUpdateFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
        int windowSizeIncrement = Http2CodecUtil.readUnsignedInt(payload);
        if (windowSizeIncrement == 0) {
            throw Http2Exception.streamError(this.streamId, Http2Error.PROTOCOL_ERROR, "Received WINDOW_UPDATE with delta 0 for stream: %d", Integer.valueOf(this.streamId));
        } else {
            listener.onWindowUpdateRead(ctx, this.streamId, windowSizeIncrement);
        }
    }

    private void readContinuationFrame(ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
        this.headersContinuation.processFragment(this.flags.endOfHeaders(), payload.readSlice(payload.readableBytes()), listener);
    }

    private void readUnknownFrame(ChannelHandlerContext ctx, ByteBuf payload, Http2FrameListener listener) throws Http2Exception {
        Http2FrameListener http2FrameListener = listener;
        ChannelHandlerContext channelHandlerContext = ctx;
        http2FrameListener.onUnknownFrame(channelHandlerContext, this.frameType, this.streamId, this.flags, payload.readSlice(payload.readableBytes()));
    }

    private int readPadding(ByteBuf payload) {
        if (this.flags.paddingPresent()) {
            return payload.readUnsignedByte() + 1;
        }
        return 0;
    }

    private static int lengthWithoutTrailingPadding(int readableBytes, int padding) {
        return padding == 0 ? readableBytes : readableBytes - (padding - 1);
    }

    private void verifyNotProcessingHeaders() throws Http2Exception {
        if (this.headersContinuation != null) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Received frame of type %s while processing headers.", Byte.valueOf(this.frameType));
        }
    }

    private void verifyPayloadLength(int payloadLength) throws Http2Exception {
        if (payloadLength > this.maxFrameSize) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Total payload length %d exceeds max frame length.", Integer.valueOf(payloadLength));
        }
    }

    private static void verifyStreamOrConnectionId(int streamId, String argumentName) throws Http2Exception {
        if (streamId < 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "%s must be >= 0", argumentName);
        }
    }
}
