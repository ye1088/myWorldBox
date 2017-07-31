package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Signal;
import io.netty.util.internal.StringUtil;
import java.util.List;

public abstract class ReplayingDecoder<S> extends ByteToMessageDecoder {
    static final Signal REPLAY = Signal.valueOf(ReplayingDecoder.class, "REPLAY");
    private int checkpoint;
    private final ReplayingDecoderByteBuf replayable;
    private S state;

    protected ReplayingDecoder() {
        this(null);
    }

    protected ReplayingDecoder(S initialState) {
        this.replayable = new ReplayingDecoderByteBuf();
        this.checkpoint = -1;
        this.state = initialState;
    }

    protected void checkpoint() {
        this.checkpoint = internalBuffer().readerIndex();
    }

    protected void checkpoint(S state) {
        checkpoint();
        state(state);
    }

    protected S state() {
        return this.state;
    }

    protected S state(S newState) {
        S oldState = this.state;
        this.state = newState;
        return oldState;
    }

    final void channelInputClosed(ChannelHandlerContext ctx, List<Object> out) throws Exception {
        try {
            this.replayable.terminate();
            if (this.cumulation != null) {
                callDecode(ctx, internalBuffer(), out);
                decodeLast(ctx, this.replayable, out);
                return;
            }
            this.replayable.setCumulation(Unpooled.EMPTY_BUFFER);
            decodeLast(ctx, this.replayable, out);
        } catch (Signal replay) {
            replay.expect(REPLAY);
        }
    }

    protected void callDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        this.replayable.setCumulation(in);
        while (in.isReadable()) {
            try {
                int oldReaderIndex = in.readerIndex();
                this.checkpoint = oldReaderIndex;
                int outSize = out.size();
                if (outSize > 0) {
                    ByteToMessageDecoder.fireChannelRead(ctx, (List) out, outSize);
                    out.clear();
                    if (!ctx.isRemoved()) {
                        outSize = 0;
                    } else {
                        return;
                    }
                }
                S oldState = this.state;
                int oldInputLength = in.readableBytes();
                try {
                    decode(ctx, this.replayable, out);
                    if (!ctx.isRemoved()) {
                        if (outSize == out.size()) {
                            if (oldInputLength == in.readableBytes() && oldState == this.state) {
                                throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() must consume the inbound " + "data or change its state if it did not decode anything.");
                            }
                        } else if (oldReaderIndex == in.readerIndex() && oldState == this.state) {
                            throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() method must consume the inbound data " + "or change its state if it decoded something.");
                        } else if (isSingleDecode()) {
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Signal replay) {
                    replay.expect(REPLAY);
                    if (!ctx.isRemoved()) {
                        int checkpoint = this.checkpoint;
                        if (checkpoint >= 0) {
                            in.readerIndex(checkpoint);
                            return;
                        }
                        return;
                    }
                    return;
                }
            } catch (DecoderException e) {
                throw e;
            } catch (Throwable cause) {
                DecoderException decoderException = new DecoderException(cause);
            }
        }
    }
}
