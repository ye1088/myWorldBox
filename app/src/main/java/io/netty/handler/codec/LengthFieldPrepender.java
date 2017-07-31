package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ObjectUtil;
import java.nio.ByteOrder;
import java.util.List;

@Sharable
public class LengthFieldPrepender extends MessageToMessageEncoder<ByteBuf> {
    private final ByteOrder byteOrder;
    private final int lengthAdjustment;
    private final int lengthFieldLength;
    private final boolean lengthIncludesLengthFieldLength;

    public LengthFieldPrepender(int lengthFieldLength) {
        this(lengthFieldLength, false);
    }

    public LengthFieldPrepender(int lengthFieldLength, boolean lengthIncludesLengthFieldLength) {
        this(lengthFieldLength, 0, lengthIncludesLengthFieldLength);
    }

    public LengthFieldPrepender(int lengthFieldLength, int lengthAdjustment) {
        this(lengthFieldLength, lengthAdjustment, false);
    }

    public LengthFieldPrepender(int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
        this(ByteOrder.BIG_ENDIAN, lengthFieldLength, lengthAdjustment, lengthIncludesLengthFieldLength);
    }

    public LengthFieldPrepender(ByteOrder byteOrder, int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
        if (lengthFieldLength == 1 || lengthFieldLength == 2 || lengthFieldLength == 3 || lengthFieldLength == 4 || lengthFieldLength == 8) {
            ObjectUtil.checkNotNull(byteOrder, "byteOrder");
            this.byteOrder = byteOrder;
            this.lengthFieldLength = lengthFieldLength;
            this.lengthIncludesLengthFieldLength = lengthIncludesLengthFieldLength;
            this.lengthAdjustment = lengthAdjustment;
            return;
        }
        throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + lengthFieldLength);
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int length = msg.readableBytes() + this.lengthAdjustment;
        if (this.lengthIncludesLengthFieldLength) {
            length += this.lengthFieldLength;
        }
        if (length < 0) {
            throw new IllegalArgumentException("Adjusted frame length (" + length + ") is less than zero");
        }
        switch (this.lengthFieldLength) {
            case 1:
                if (length < 256) {
                    out.add(ctx.alloc().buffer(1).order(this.byteOrder).writeByte((byte) length));
                    break;
                }
                throw new IllegalArgumentException("length does not fit into a byte: " + length);
            case 2:
                if (length < 65536) {
                    out.add(ctx.alloc().buffer(2).order(this.byteOrder).writeShort((short) length));
                    break;
                }
                throw new IllegalArgumentException("length does not fit into a short integer: " + length);
            case 3:
                if (length < 16777216) {
                    out.add(ctx.alloc().buffer(3).order(this.byteOrder).writeMedium(length));
                    break;
                }
                throw new IllegalArgumentException("length does not fit into a medium integer: " + length);
            case 4:
                out.add(ctx.alloc().buffer(4).order(this.byteOrder).writeInt(length));
                break;
            case 8:
                out.add(ctx.alloc().buffer(8).order(this.byteOrder).writeLong((long) length));
                break;
            default:
                throw new Error("should not reach here");
        }
        out.add(msg.retain());
    }
}
