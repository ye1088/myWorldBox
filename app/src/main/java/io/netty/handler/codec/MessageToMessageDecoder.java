package io.netty.handler.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

public abstract class MessageToMessageDecoder<I> extends ChannelInboundHandlerAdapter {
    private final TypeParameterMatcher matcher;

    protected abstract void decode(ChannelHandlerContext channelHandlerContext, I i, List<Object> list) throws Exception;

    protected MessageToMessageDecoder() {
        this.matcher = TypeParameterMatcher.find(this, MessageToMessageDecoder.class, "I");
    }

    protected MessageToMessageDecoder(Class<? extends I> inboundMessageType) {
        this.matcher = TypeParameterMatcher.get(inboundMessageType);
    }

    public boolean acceptInboundMessage(Object msg) throws Exception {
        return this.matcher.match(msg);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CodecOutputList out = CodecOutputList.newInstance();
        I cast;
        int size;
        int i;
        try {
            if (acceptInboundMessage(msg)) {
                cast = msg;
                decode(ctx, cast, out);
                ReferenceCountUtil.release(cast);
            } else {
                out.add(msg);
            }
            size = out.size();
            for (i = 0; i < size; i++) {
                ctx.fireChannelRead(out.getUnsafe(i));
            }
            out.recycle();
        } catch (DecoderException e) {
            try {
                throw e;
            } catch (Throwable th) {
                size = out.size();
                for (i = 0; i < size; i++) {
                    ctx.fireChannelRead(out.getUnsafe(i));
                }
                out.recycle();
            }
        } catch (Throwable e2) {
            throw new DecoderException(e2);
        } catch (Throwable th2) {
            ReferenceCountUtil.release(cast);
        }
    }
}
