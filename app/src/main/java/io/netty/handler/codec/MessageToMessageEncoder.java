package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

public abstract class MessageToMessageEncoder<I> extends ChannelOutboundHandlerAdapter {
    private final TypeParameterMatcher matcher;

    protected abstract void encode(ChannelHandlerContext channelHandlerContext, I i, List<Object> list) throws Exception;

    protected MessageToMessageEncoder() {
        this.matcher = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
    }

    protected MessageToMessageEncoder(Class<? extends I> outboundMessageType) {
        this.matcher = TypeParameterMatcher.get(outboundMessageType);
    }

    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return this.matcher.match(msg);
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        CodecOutputList out = null;
        I cast;
        int sizeMinusOne;
        ChannelPromise voidPromise;
        boolean isVoidPromise;
        int i;
        ChannelPromise p;
        try {
            if (acceptOutboundMessage(msg)) {
                out = CodecOutputList.newInstance();
                cast = msg;
                encode(ctx, cast, out);
                ReferenceCountUtil.release(cast);
                if (out.isEmpty()) {
                    out.recycle();
                    throw new EncoderException(StringUtil.simpleClassName(this) + " must produce at least one message.");
                }
            }
            ctx.write(msg, promise);
            if (out != null) {
                sizeMinusOne = out.size() - 1;
                if (sizeMinusOne == 0) {
                    ctx.write(out.get(0), promise);
                } else if (sizeMinusOne > 0) {
                    voidPromise = ctx.voidPromise();
                    isVoidPromise = promise == voidPromise;
                    for (i = 0; i < sizeMinusOne; i++) {
                        if (isVoidPromise) {
                            p = voidPromise;
                        } else {
                            p = ctx.newPromise();
                        }
                        ctx.write(out.getUnsafe(i), p);
                    }
                    ctx.write(out.getUnsafe(sizeMinusOne), promise);
                }
                out.recycle();
            }
        } catch (EncoderException e) {
            throw e;
        } catch (Throwable th) {
            if (out != null) {
                sizeMinusOne = out.size() - 1;
                if (sizeMinusOne == 0) {
                    ctx.write(out.get(0), promise);
                } else if (sizeMinusOne > 0) {
                    voidPromise = ctx.voidPromise();
                    isVoidPromise = promise == voidPromise;
                    for (i = 0; i < sizeMinusOne; i++) {
                        if (isVoidPromise) {
                            p = voidPromise;
                        } else {
                            p = ctx.newPromise();
                        }
                        ctx.write(out.getUnsafe(i), p);
                    }
                    ctx.write(out.getUnsafe(sizeMinusOne), promise);
                }
                out.recycle();
            }
        }
    }
}
