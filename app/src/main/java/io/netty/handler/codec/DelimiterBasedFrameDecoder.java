package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;

public class DelimiterBasedFrameDecoder extends ByteToMessageDecoder {
    private final ByteBuf[] delimiters;
    private boolean discardingTooLongFrame;
    private final boolean failFast;
    private final LineBasedFrameDecoder lineBasedDecoder;
    private final int maxFrameLength;
    private final boolean stripDelimiter;
    private int tooLongFrameLength;

    public DelimiterBasedFrameDecoder(int maxFrameLength, ByteBuf delimiter) {
        this(maxFrameLength, true, delimiter);
    }

    public DelimiterBasedFrameDecoder(int maxFrameLength, boolean stripDelimiter, ByteBuf delimiter) {
        this(maxFrameLength, stripDelimiter, true, delimiter);
    }

    public DelimiterBasedFrameDecoder(int maxFrameLength, boolean stripDelimiter, boolean failFast, ByteBuf delimiter) {
        this(maxFrameLength, stripDelimiter, failFast, delimiter.slice(delimiter.readerIndex(), delimiter.readableBytes()));
    }

    public DelimiterBasedFrameDecoder(int maxFrameLength, ByteBuf... delimiters) {
        this(maxFrameLength, true, delimiters);
    }

    public DelimiterBasedFrameDecoder(int maxFrameLength, boolean stripDelimiter, ByteBuf... delimiters) {
        this(maxFrameLength, stripDelimiter, true, delimiters);
    }

    public DelimiterBasedFrameDecoder(int maxFrameLength, boolean stripDelimiter, boolean failFast, ByteBuf... delimiters) {
        validateMaxFrameLength(maxFrameLength);
        if (delimiters == null) {
            throw new NullPointerException("delimiters");
        } else if (delimiters.length == 0) {
            throw new IllegalArgumentException("empty delimiters");
        } else {
            if (!isLineBased(delimiters) || isSubclass()) {
                this.delimiters = new ByteBuf[delimiters.length];
                for (int i = 0; i < delimiters.length; i++) {
                    ByteBuf d = delimiters[i];
                    validateDelimiter(d);
                    this.delimiters[i] = d.slice(d.readerIndex(), d.readableBytes());
                }
                this.lineBasedDecoder = null;
            } else {
                this.lineBasedDecoder = new LineBasedFrameDecoder(maxFrameLength, stripDelimiter, failFast);
                this.delimiters = null;
            }
            this.maxFrameLength = maxFrameLength;
            this.stripDelimiter = stripDelimiter;
            this.failFast = failFast;
        }
    }

    private static boolean isLineBased(ByteBuf[] delimiters) {
        boolean z = true;
        if (delimiters.length != 2) {
            return false;
        }
        ByteBuf a = delimiters[0];
        ByteBuf b = delimiters[1];
        if (a.capacity() < b.capacity()) {
            a = delimiters[1];
            b = delimiters[0];
        }
        if (!(a.capacity() == 2 && b.capacity() == 1 && a.getByte(0) == (byte) 13 && a.getByte(1) == (byte) 10 && b.getByte(0) == (byte) 10)) {
            z = false;
        }
        return z;
    }

    private boolean isSubclass() {
        return getClass() != DelimiterBasedFrameDecoder.class;
    }

    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        if (this.lineBasedDecoder != null) {
            return this.lineBasedDecoder.decode(ctx, buffer);
        }
        int minFrameLength = Integer.MAX_VALUE;
        ByteBuf minDelim = null;
        for (ByteBuf delim : this.delimiters) {
            int frameLength = indexOf(buffer, delim);
            if (frameLength >= 0 && frameLength < minFrameLength) {
                minFrameLength = frameLength;
                minDelim = delim;
            }
        }
        if (minDelim != null) {
            int minDelimLength = minDelim.capacity();
            if (this.discardingTooLongFrame) {
                this.discardingTooLongFrame = false;
                buffer.skipBytes(minFrameLength + minDelimLength);
                int tooLongFrameLength = this.tooLongFrameLength;
                this.tooLongFrameLength = 0;
                if (this.failFast) {
                    return null;
                }
                fail((long) tooLongFrameLength);
                return null;
            } else if (minFrameLength > this.maxFrameLength) {
                buffer.skipBytes(minFrameLength + minDelimLength);
                fail((long) minFrameLength);
                return null;
            } else if (!this.stripDelimiter) {
                return buffer.readRetainedSlice(minFrameLength + minDelimLength);
            } else {
                Object frame = buffer.readRetainedSlice(minFrameLength);
                buffer.skipBytes(minDelimLength);
                return frame;
            }
        } else if (this.discardingTooLongFrame) {
            this.tooLongFrameLength += buffer.readableBytes();
            buffer.skipBytes(buffer.readableBytes());
            return null;
        } else if (buffer.readableBytes() <= this.maxFrameLength) {
            return null;
        } else {
            this.tooLongFrameLength = buffer.readableBytes();
            buffer.skipBytes(buffer.readableBytes());
            this.discardingTooLongFrame = true;
            if (!this.failFast) {
                return null;
            }
            fail((long) this.tooLongFrameLength);
            return null;
        }
    }

    private void fail(long frameLength) {
        if (frameLength > 0) {
            throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + ": " + frameLength + " - discarded");
        }
        throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + " - discarding");
    }

    private static int indexOf(ByteBuf haystack, ByteBuf needle) {
        for (int i = haystack.readerIndex(); i < haystack.writerIndex(); i++) {
            int haystackIndex = i;
            int needleIndex = 0;
            while (needleIndex < needle.capacity() && haystack.getByte(haystackIndex) == needle.getByte(needleIndex)) {
                haystackIndex++;
                if (haystackIndex == haystack.writerIndex() && needleIndex != needle.capacity() - 1) {
                    return -1;
                }
                needleIndex++;
            }
            if (needleIndex == needle.capacity()) {
                return i - haystack.readerIndex();
            }
        }
        return -1;
    }

    private static void validateDelimiter(ByteBuf delimiter) {
        if (delimiter == null) {
            throw new NullPointerException("delimiter");
        } else if (!delimiter.isReadable()) {
            throw new IllegalArgumentException("empty delimiter");
        }
    }

    private static void validateMaxFrameLength(int maxFrameLength) {
        if (maxFrameLength <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a_isRightVersion positive integer: " + maxFrameLength);
        }
    }
}
