package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.handler.codec.http2.StreamByteDistributor.StreamState;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutor;

public final class Http2CodecUtil {
    private static final ByteBuf CONNECTION_PREFACE = Unpooled.unreleasableBuffer(Unpooled.directBuffer(24).writeBytes("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n".getBytes(CharsetUtil.UTF_8))).asReadOnly();
    public static final int CONNECTION_STREAM_ID = 0;
    public static final int CONTINUATION_FRAME_HEADER_LENGTH = 10;
    public static final int DATA_FRAME_HEADER_LENGTH = 10;
    public static final boolean DEFAULT_ENABLE_PUSH = true;
    public static final int DEFAULT_HEADER_TABLE_SIZE = 4096;
    public static final int DEFAULT_MAX_FRAME_SIZE = 16384;
    public static final int DEFAULT_MAX_HEADER_SIZE = 8192;
    public static final short DEFAULT_PRIORITY_WEIGHT = (short) 16;
    public static final int DEFAULT_WINDOW_SIZE = 65535;
    private static final ByteBuf EMPTY_PING = Unpooled.unreleasableBuffer(Unpooled.directBuffer(8).writeZero(8)).asReadOnly();
    public static final int FRAME_HEADER_LENGTH = 9;
    public static final int GO_AWAY_FRAME_HEADER_LENGTH = 17;
    public static final int HEADERS_FRAME_HEADER_LENGTH = 15;
    public static final CharSequence HTTP_UPGRADE_PROTOCOL_NAME = "h2c";
    public static final CharSequence HTTP_UPGRADE_SETTINGS_HEADER = new AsciiString((CharSequence) "HTTP2-Settings");
    public static final int HTTP_UPGRADE_STREAM_ID = 1;
    public static final int INT_FIELD_LENGTH = 4;
    public static final long MAX_CONCURRENT_STREAMS = 4294967295L;
    public static final int MAX_FRAME_SIZE_LOWER_BOUND = 16384;
    public static final int MAX_FRAME_SIZE_UPPER_BOUND = 16777215;
    public static final long MAX_HEADER_LIST_SIZE = Long.MAX_VALUE;
    public static final int MAX_HEADER_TABLE_SIZE = Integer.MAX_VALUE;
    public static final int MAX_INITIAL_WINDOW_SIZE = Integer.MAX_VALUE;
    public static final int MAX_PADDING = 256;
    private static final int MAX_PADDING_LENGTH_LENGTH = 1;
    public static final short MAX_UNSIGNED_BYTE = (short) 255;
    public static final long MAX_UNSIGNED_INT = 4294967295L;
    public static final int MAX_UNSIGNED_SHORT = 65535;
    public static final short MAX_WEIGHT = (short) 256;
    public static final long MIN_CONCURRENT_STREAMS = 0;
    public static final long MIN_HEADER_LIST_SIZE = 0;
    public static final long MIN_HEADER_TABLE_SIZE = 0;
    public static final int MIN_INITIAL_WINDOW_SIZE = 0;
    public static final short MIN_WEIGHT = (short) 1;
    public static final int NUM_STANDARD_SETTINGS = 6;
    public static final int PING_FRAME_PAYLOAD_LENGTH = 8;
    public static final int PRIORITY_ENTRY_LENGTH = 5;
    public static final int PRIORITY_FRAME_LENGTH = 14;
    public static final int PUSH_PROMISE_FRAME_HEADER_LENGTH = 14;
    public static final int RST_STREAM_FRAME_LENGTH = 13;
    public static final char SETTINGS_ENABLE_PUSH = '\u0002';
    public static final char SETTINGS_HEADER_TABLE_SIZE = '\u0001';
    public static final char SETTINGS_INITIAL_WINDOW_SIZE = '\u0004';
    public static final char SETTINGS_MAX_CONCURRENT_STREAMS = '\u0003';
    public static final char SETTINGS_MAX_FRAME_SIZE = '\u0005';
    public static final char SETTINGS_MAX_HEADER_LIST_SIZE = '\u0006';
    public static final int SETTING_ENTRY_LENGTH = 6;
    public static final int SMALLEST_MAX_CONCURRENT_STREAMS = 100;
    public static final CharSequence TLS_UPGRADE_PROTOCOL_NAME = ApplicationProtocolNames.HTTP_2;
    public static final int WINDOW_UPDATE_FRAME_LENGTH = 13;

    static final class SimpleChannelPromiseAggregator extends DefaultChannelPromise {
        static final /* synthetic */ boolean $assertionsDisabled = (!Http2CodecUtil.class.desiredAssertionStatus());
        private boolean doneAllocating;
        private int doneCount;
        private int expectedCount;
        private Throwable lastFailure;
        private final ChannelPromise promise;

        SimpleChannelPromiseAggregator(ChannelPromise promise, Channel c, EventExecutor e) {
            super(c, e);
            if ($assertionsDisabled || !(promise == null || promise.isDone())) {
                this.promise = promise;
                return;
            }
            throw new AssertionError();
        }

        public ChannelPromise newPromise() {
            if ($assertionsDisabled || !this.doneAllocating) {
                this.expectedCount++;
                return this;
            }
            throw new AssertionError("Done allocating. No more promises can be allocated.");
        }

        public ChannelPromise doneAllocatingPromises() {
            if (this.doneAllocating) {
                return this;
            }
            this.doneAllocating = true;
            if (this.doneCount == this.expectedCount || this.expectedCount == 0) {
                return setPromise();
            }
            return this;
        }

        public boolean tryFailure(Throwable cause) {
            if (!allowFailure()) {
                return false;
            }
            this.doneCount++;
            this.lastFailure = cause;
            if (allPromisesDone()) {
                return tryPromise();
            }
            return true;
        }

        public ChannelPromise setFailure(Throwable cause) {
            if (!allowFailure()) {
                return this;
            }
            this.doneCount++;
            this.lastFailure = cause;
            if (allPromisesDone()) {
                return setPromise();
            }
            return this;
        }

        public ChannelPromise setSuccess(Void result) {
            if (awaitingPromises()) {
                this.doneCount++;
                if (allPromisesDone()) {
                    setPromise();
                }
            }
            return this;
        }

        public boolean trySuccess(Void result) {
            if (!awaitingPromises()) {
                return false;
            }
            this.doneCount++;
            if (allPromisesDone()) {
                return tryPromise();
            }
            return true;
        }

        private boolean allowFailure() {
            return awaitingPromises() || this.expectedCount == 0;
        }

        private boolean awaitingPromises() {
            return this.doneCount < this.expectedCount;
        }

        private boolean allPromisesDone() {
            return this.doneCount == this.expectedCount && this.doneAllocating;
        }

        private ChannelPromise setPromise() {
            if (this.lastFailure == null) {
                this.promise.setSuccess();
                return super.setSuccess(null);
            }
            this.promise.setFailure(this.lastFailure);
            return super.setFailure(this.lastFailure);
        }

        private boolean tryPromise() {
            if (this.lastFailure == null) {
                this.promise.trySuccess();
                return super.trySuccess(null);
            }
            this.promise.tryFailure(this.lastFailure);
            return super.tryFailure(this.lastFailure);
        }
    }

    public static boolean isMaxFrameSizeValid(int maxFrameSize) {
        return maxFrameSize >= 16384 && maxFrameSize <= 16777215;
    }

    public static ByteBuf connectionPrefaceBuf() {
        return CONNECTION_PREFACE.retainedDuplicate();
    }

    public static ByteBuf emptyPingBuf() {
        return EMPTY_PING.retainedDuplicate();
    }

    public static Http2Exception getEmbeddedHttp2Exception(Throwable cause) {
        while (cause != null) {
            if (cause instanceof Http2Exception) {
                return (Http2Exception) cause;
            }
            cause = cause.getCause();
        }
        return null;
    }

    public static ByteBuf toByteBuf(ChannelHandlerContext ctx, Throwable cause) {
        if (cause == null || cause.getMessage() == null) {
            return Unpooled.EMPTY_BUFFER;
        }
        return ByteBufUtil.writeUtf8(ctx.alloc(), cause.getMessage());
    }

    public static int readUnsignedInt(ByteBuf buf) {
        return ((((buf.readByte() & 127) << 24) | ((buf.readByte() & 255) << 16)) | ((buf.readByte() & 255) << 8)) | (buf.readByte() & 255);
    }

    public static void writeUnsignedInt(long value, ByteBuf out) {
        out.writeByte((int) ((value >> 24) & 255));
        out.writeByte((int) ((value >> 16) & 255));
        out.writeByte((int) ((value >> 8) & 255));
        out.writeByte((int) (value & 255));
    }

    public static void writeUnsignedShort(int value, ByteBuf out) {
        out.writeByte((value >> 8) & 255);
        out.writeByte(value & 255);
    }

    public static void writeFrameHeader(ByteBuf out, int payloadLength, byte type, Http2Flags flags, int streamId) {
        out.ensureWritable(payloadLength + 9);
        writeFrameHeaderInternal(out, payloadLength, type, flags, streamId);
    }

    public static int streamableBytes(StreamState state) {
        return Math.max(0, Math.min(state.pendingBytes(), state.windowSize()));
    }

    static void writeFrameHeaderInternal(ByteBuf out, int payloadLength, byte type, Http2Flags flags, int streamId) {
        out.writeMedium(payloadLength);
        out.writeByte(type);
        out.writeByte(flags.value());
        out.writeInt(streamId);
    }

    public static void verifyPadding(int padding) {
        if (padding < 0 || padding > 256) {
            throw new IllegalArgumentException(String.format("Invalid padding '%d'. Padding must be between 0 and %d (inclusive).", new Object[]{Integer.valueOf(padding), Integer.valueOf(256)}));
        }
    }

    private Http2CodecUtil() {
    }
}
