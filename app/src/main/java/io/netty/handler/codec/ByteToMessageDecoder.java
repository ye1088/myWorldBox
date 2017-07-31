package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.util.internal.StringUtil;
import java.util.List;

public abstract class ByteToMessageDecoder extends ChannelInboundHandlerAdapter {
    public static final Cumulator COMPOSITE_CUMULATOR = new Cumulator() {
        public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
            if (cumulation.refCnt() > 1) {
                ByteBuf buffer = ByteToMessageDecoder.expandCumulation(alloc, cumulation, in.readableBytes());
                buffer.writeBytes(in);
                in.release();
                return buffer;
            }
            ByteBuf composite;
            if (cumulation instanceof CompositeByteBuf) {
                composite = (CompositeByteBuf) cumulation;
            } else {
                composite = alloc.compositeBuffer(Integer.MAX_VALUE);
                composite.addComponent(true, cumulation);
            }
            composite.addComponent(true, in);
            return composite;
        }
    };
    public static final Cumulator MERGE_CUMULATOR = new Cumulator() {
        public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {
            ByteBuf buffer;
            if (cumulation.writerIndex() > cumulation.maxCapacity() - in.readableBytes() || cumulation.refCnt() > 1) {
                buffer = ByteToMessageDecoder.expandCumulation(alloc, cumulation, in.readableBytes());
            } else {
                buffer = cumulation;
            }
            buffer.writeBytes(in);
            in.release();
            return buffer;
        }
    };
    ByteBuf cumulation;
    private Cumulator cumulator = MERGE_CUMULATOR;
    private boolean decodeWasNull;
    private int discardAfterReads = 16;
    private boolean first;
    private int numReads;
    private boolean singleDecode;

    public interface Cumulator {
        ByteBuf cumulate(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2);
    }

    protected abstract void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception;

    protected ByteToMessageDecoder() {
        CodecUtil.ensureNotSharable(this);
    }

    public void setSingleDecode(boolean singleDecode) {
        this.singleDecode = singleDecode;
    }

    public boolean isSingleDecode() {
        return this.singleDecode;
    }

    public void setCumulator(Cumulator cumulator) {
        if (cumulator == null) {
            throw new NullPointerException("cumulator");
        }
        this.cumulator = cumulator;
    }

    public void setDiscardAfterReads(int discardAfterReads) {
        if (discardAfterReads <= 0) {
            throw new IllegalArgumentException("discardAfterReads must be > 0");
        }
        this.discardAfterReads = discardAfterReads;
    }

    protected int actualReadableBytes() {
        return internalBuffer().readableBytes();
    }

    protected ByteBuf internalBuffer() {
        if (this.cumulation != null) {
            return this.cumulation;
        }
        return Unpooled.EMPTY_BUFFER;
    }

    public final void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = this.cumulation;
        if (buf != null) {
            this.cumulation = null;
            int readable = buf.readableBytes();
            if (readable > 0) {
                ByteBuf bytes = buf.readBytes(readable);
                buf.release();
                ctx.fireChannelRead(bytes);
            } else {
                buf.release();
            }
            this.numReads = 0;
            ctx.fireChannelReadComplete();
        }
        handlerRemoved0(ctx);
    }

    protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        boolean z = true;
        if (msg instanceof ByteBuf) {
            CodecOutputList out = CodecOutputList.newInstance();
            int size;
            try {
                boolean z2;
                ByteBuf data = (ByteBuf) msg;
                if (this.cumulation == null) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                this.first = z2;
                if (this.first) {
                    this.cumulation = data;
                } else {
                    this.cumulation = this.cumulator.cumulate(ctx.alloc(), this.cumulation, data);
                }
                callDecode(ctx, this.cumulation, out);
                if (this.cumulation == null || this.cumulation.isReadable()) {
                    int i = this.numReads + 1;
                    this.numReads = i;
                    if (i >= this.discardAfterReads) {
                        this.numReads = 0;
                        discardSomeReadBytes();
                    }
                } else {
                    this.numReads = 0;
                    this.cumulation.release();
                    this.cumulation = null;
                }
                size = out.size();
                if (out.insertSinceRecycled()) {
                    z = false;
                }
                this.decodeWasNull = z;
                fireChannelRead(ctx, out, size);
                out.recycle();
            } catch (DecoderException e) {
                throw e;
            } catch (Throwable th) {
                if (this.cumulation == null || this.cumulation.isReadable()) {
                    int i2 = this.numReads + 1;
                    this.numReads = i2;
                    if (i2 >= this.discardAfterReads) {
                        this.numReads = 0;
                        discardSomeReadBytes();
                    }
                } else {
                    this.numReads = 0;
                    this.cumulation.release();
                    this.cumulation = null;
                }
                size = out.size();
                if (out.insertSinceRecycled()) {
                    z = false;
                }
                this.decodeWasNull = z;
                fireChannelRead(ctx, out, size);
                out.recycle();
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    static void fireChannelRead(ChannelHandlerContext ctx, List<Object> msgs, int numElements) {
        if (msgs instanceof CodecOutputList) {
            fireChannelRead(ctx, (CodecOutputList) msgs, numElements);
            return;
        }
        for (int i = 0; i < numElements; i++) {
            ctx.fireChannelRead(msgs.get(i));
        }
    }

    static void fireChannelRead(ChannelHandlerContext ctx, CodecOutputList msgs, int numElements) {
        for (int i = 0; i < numElements; i++) {
            ctx.fireChannelRead(msgs.getUnsafe(i));
        }
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        this.numReads = 0;
        discardSomeReadBytes();
        if (this.decodeWasNull) {
            this.decodeWasNull = false;
            if (!ctx.channel().config().isAutoRead()) {
                ctx.read();
            }
        }
        ctx.fireChannelReadComplete();
    }

    protected final void discardSomeReadBytes() {
        if (this.cumulation != null && !this.first && this.cumulation.refCnt() == 1) {
            this.cumulation.discardSomeReadBytes();
        }
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channelInputClosed(ctx, true);
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof ChannelInputShutdownEvent) {
            channelInputClosed(ctx, false);
        }
        super.userEventTriggered(ctx, evt);
    }

    private void channelInputClosed(ChannelHandlerContext ctx, boolean callChannelInactive) throws Exception {
        CodecOutputList out = CodecOutputList.newInstance();
        int size;
        try {
            channelInputClosed(ctx, (List) out);
            try {
                if (this.cumulation != null) {
                    this.cumulation.release();
                    this.cumulation = null;
                }
                size = out.size();
                fireChannelRead(ctx, out, size);
                if (size > 0) {
                    ctx.fireChannelReadComplete();
                }
                if (callChannelInactive) {
                    ctx.fireChannelInactive();
                }
                out.recycle();
            } catch (Throwable th) {
                out.recycle();
            }
        } catch (DecoderException e) {
            throw e;
        } catch (Throwable e2) {
            throw new DecoderException(e2);
        } catch (Throwable th2) {
            try {
                if (this.cumulation != null) {
                    this.cumulation.release();
                    this.cumulation = null;
                }
                size = out.size();
                fireChannelRead(ctx, out, size);
                if (size > 0) {
                    ctx.fireChannelReadComplete();
                }
                if (callChannelInactive) {
                    ctx.fireChannelInactive();
                }
                out.recycle();
            } catch (Throwable th3) {
                out.recycle();
            }
        }
    }

    void channelInputClosed(ChannelHandlerContext ctx, List<Object> out) throws Exception {
        if (this.cumulation != null) {
            callDecode(ctx, this.cumulation, out);
            decodeLast(ctx, this.cumulation, out);
            return;
        }
        decodeLast(ctx, Unpooled.EMPTY_BUFFER, out);
    }

    protected void callDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        while (in.isReadable()) {
            try {
                int outSize = out.size();
                if (outSize > 0) {
                    fireChannelRead(ctx, (List) out, outSize);
                    out.clear();
                    if (!ctx.isRemoved()) {
                        outSize = 0;
                    } else {
                        return;
                    }
                }
                int oldInputLength = in.readableBytes();
                decode(ctx, in, out);
                if (!ctx.isRemoved()) {
                    if (outSize == out.size()) {
                        if (oldInputLength == in.readableBytes()) {
                            return;
                        }
                    } else if (oldInputLength == in.readableBytes()) {
                        throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() did not read anything but decoded a message.");
                    } else if (isSingleDecode()) {
                        return;
                    }
                } else {
                    return;
                }
            } catch (DecoderException e) {
                throw e;
            } catch (Throwable cause) {
                DecoderException decoderException = new DecoderException(cause);
            }
        }
    }

    protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.isReadable()) {
            decode(ctx, in, out);
        }
    }

    static ByteBuf expandCumulation(ByteBufAllocator alloc, ByteBuf cumulation, int readable) {
        ByteBuf oldCumulation = cumulation;
        cumulation = alloc.buffer(oldCumulation.readableBytes() + readable);
        cumulation.writeBytes(oldCumulation);
        oldCumulation.release();
        return cumulation;
    }
}
