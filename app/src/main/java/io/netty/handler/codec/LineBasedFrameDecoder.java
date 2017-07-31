package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ByteProcessor;
import java.util.List;

public class LineBasedFrameDecoder extends ByteToMessageDecoder {
    private int discardedBytes;
    private boolean discarding;
    private final boolean failFast;
    private final int maxLength;
    private final boolean stripDelimiter;

    public LineBasedFrameDecoder(int maxLength) {
        this(maxLength, true, false);
    }

    public LineBasedFrameDecoder(int maxLength, boolean stripDelimiter, boolean failFast) {
        this.maxLength = maxLength;
        this.failFast = failFast;
        this.stripDelimiter = stripDelimiter;
    }

    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        int delimLength = 2;
        int eol = findEndOfLine(buffer);
        int length;
        if (this.discarding) {
            if (eol >= 0) {
                length = (this.discardedBytes + eol) - buffer.readerIndex();
                if (buffer.getByte(eol) != (byte) 13) {
                    delimLength = 1;
                }
                buffer.readerIndex(eol + delimLength);
                this.discardedBytes = 0;
                this.discarding = false;
                if (this.failFast) {
                    return null;
                }
                fail(ctx, length);
                return null;
            }
            this.discardedBytes += buffer.readableBytes();
            buffer.readerIndex(buffer.writerIndex());
            return null;
        } else if (eol >= 0) {
            length = eol - buffer.readerIndex();
            if (buffer.getByte(eol) != (byte) 13) {
                delimLength = 1;
            }
            if (length > this.maxLength) {
                buffer.readerIndex(eol + delimLength);
                fail(ctx, length);
                return null;
            } else if (!this.stripDelimiter) {
                return buffer.readRetainedSlice(length + delimLength);
            } else {
                Object frame = buffer.readRetainedSlice(length);
                buffer.skipBytes(delimLength);
                return frame;
            }
        } else {
            length = buffer.readableBytes();
            if (length <= this.maxLength) {
                return null;
            }
            this.discardedBytes = length;
            buffer.readerIndex(buffer.writerIndex());
            this.discarding = true;
            if (!this.failFast) {
                return null;
            }
            fail(ctx, "over " + this.discardedBytes);
            return null;
        }
    }

    private void fail(ChannelHandlerContext ctx, int length) {
        fail(ctx, String.valueOf(length));
    }

    private void fail(ChannelHandlerContext ctx, String length) {
        ctx.fireExceptionCaught(new TooLongFrameException("frame length (" + length + ") exceeds the allowed maximum (" + this.maxLength + ')'));
    }

    private static int findEndOfLine(ByteBuf buffer) {
        int i = buffer.forEachByte(ByteProcessor.FIND_LF);
        if (i <= 0 || buffer.getByte(i - 1) != (byte) 13) {
            return i;
        }
        return i - 1;
    }
}
