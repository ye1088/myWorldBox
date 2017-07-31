package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.nio.ByteOrder;
import java.util.List;

public class LengthFieldBasedFrameDecoder extends ByteToMessageDecoder {
    private final ByteOrder byteOrder;
    private long bytesToDiscard;
    private boolean discardingTooLongFrame;
    private final boolean failFast;
    private final int initialBytesToStrip;
    private final int lengthAdjustment;
    private final int lengthFieldEndOffset;
    private final int lengthFieldLength;
    private final int lengthFieldOffset;
    private final int maxFrameLength;
    private long tooLongFrameLength;

    public LengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        this(maxFrameLength, lengthFieldOffset, lengthFieldLength, 0, 0);
    }

    public LengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        this(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, true);
    }

    public LengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        this(ByteOrder.BIG_ENDIAN, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    public LengthFieldBasedFrameDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        if (byteOrder == null) {
            throw new NullPointerException("byteOrder");
        } else if (maxFrameLength <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + maxFrameLength);
        } else if (lengthFieldOffset < 0) {
            throw new IllegalArgumentException("lengthFieldOffset must be a non-negative integer: " + lengthFieldOffset);
        } else if (initialBytesToStrip < 0) {
            throw new IllegalArgumentException("initialBytesToStrip must be a non-negative integer: " + initialBytesToStrip);
        } else if (lengthFieldOffset > maxFrameLength - lengthFieldLength) {
            throw new IllegalArgumentException("maxFrameLength (" + maxFrameLength + ") " + "must be equal to or greater than " + "lengthFieldOffset (" + lengthFieldOffset + ") + " + "lengthFieldLength (" + lengthFieldLength + ").");
        } else {
            this.byteOrder = byteOrder;
            this.maxFrameLength = maxFrameLength;
            this.lengthFieldOffset = lengthFieldOffset;
            this.lengthFieldLength = lengthFieldLength;
            this.lengthAdjustment = lengthAdjustment;
            this.lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength;
            this.initialBytesToStrip = initialBytesToStrip;
            this.failFast = failFast;
        }
    }

    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (this.discardingTooLongFrame) {
            long bytesToDiscard = this.bytesToDiscard;
            int localBytesToDiscard = (int) Math.min(bytesToDiscard, (long) in.readableBytes());
            in.skipBytes(localBytesToDiscard);
            this.bytesToDiscard = bytesToDiscard - ((long) localBytesToDiscard);
            failIfNecessary(false);
        }
        if (in.readableBytes() < this.lengthFieldEndOffset) {
            return null;
        }
        long frameLength = getUnadjustedFrameLength(in, in.readerIndex() + this.lengthFieldOffset, this.lengthFieldLength, this.byteOrder);
        if (frameLength < 0) {
            in.skipBytes(this.lengthFieldEndOffset);
            throw new CorruptedFrameException("negative pre-adjustment length field: " + frameLength);
        }
        frameLength += (long) (this.lengthAdjustment + this.lengthFieldEndOffset);
        if (frameLength < ((long) this.lengthFieldEndOffset)) {
            in.skipBytes(this.lengthFieldEndOffset);
            throw new CorruptedFrameException("Adjusted frame length (" + frameLength + ") is less " + "than lengthFieldEndOffset: " + this.lengthFieldEndOffset);
        } else if (frameLength > ((long) this.maxFrameLength)) {
            long discard = frameLength - ((long) in.readableBytes());
            this.tooLongFrameLength = frameLength;
            if (discard < 0) {
                in.skipBytes((int) frameLength);
            } else {
                this.discardingTooLongFrame = true;
                this.bytesToDiscard = discard;
                in.skipBytes(in.readableBytes());
            }
            failIfNecessary(true);
            return null;
        } else {
            int frameLengthInt = (int) frameLength;
            if (in.readableBytes() < frameLengthInt) {
                return null;
            }
            if (this.initialBytesToStrip > frameLengthInt) {
                in.skipBytes(frameLengthInt);
                throw new CorruptedFrameException("Adjusted frame length (" + frameLength + ") is less " + "than initialBytesToStrip: " + this.initialBytesToStrip);
            }
            in.skipBytes(this.initialBytesToStrip);
            int readerIndex = in.readerIndex();
            int actualFrameLength = frameLengthInt - this.initialBytesToStrip;
            Object frame = extractFrame(ctx, in, readerIndex, actualFrameLength);
            in.readerIndex(readerIndex + actualFrameLength);
            return frame;
        }
    }

    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        buf = buf.order(order);
        switch (length) {
            case 1:
                return (long) buf.getUnsignedByte(offset);
            case 2:
                return (long) buf.getUnsignedShort(offset);
            case 3:
                return (long) buf.getUnsignedMedium(offset);
            case 4:
                return buf.getUnsignedInt(offset);
            case 8:
                return buf.getLong(offset);
            default:
                throw new DecoderException("unsupported lengthFieldLength: " + this.lengthFieldLength + " (expected: 1, 2, 3, 4, or 8)");
        }
    }

    private void failIfNecessary(boolean firstDetectionOfTooLongFrame) {
        if (this.bytesToDiscard == 0) {
            long tooLongFrameLength = this.tooLongFrameLength;
            this.tooLongFrameLength = 0;
            this.discardingTooLongFrame = false;
            if (!this.failFast || (this.failFast && firstDetectionOfTooLongFrame)) {
                fail(tooLongFrameLength);
            }
        } else if (this.failFast && firstDetectionOfTooLongFrame) {
            fail(this.tooLongFrameLength);
        }
    }

    protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
        return buffer.retainedSlice(index, length);
    }

    private void fail(long frameLength) {
        if (frameLength > 0) {
            throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + ": " + frameLength + " - discarded");
        }
        throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + " - discarding");
    }
}
