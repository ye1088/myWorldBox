package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.ReferenceCountUtil;
import java.util.List;

public abstract class MessageAggregator<I, S, C extends ByteBufHolder, O extends ByteBufHolder> extends MessageToMessageDecoder<I> {
    private static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;
    private ChannelFutureListener continueResponseWriteListener;
    private ChannelHandlerContext ctx;
    private O currentMessage;
    private boolean handlingOversizedMessage;
    private final int maxContentLength;
    private int maxCumulationBufferComponents = 1024;

    protected abstract O beginAggregation(S s, ByteBuf byteBuf) throws Exception;

    protected abstract boolean closeAfterContinueResponse(Object obj) throws Exception;

    protected abstract boolean ignoreContentAfterContinueResponse(Object obj) throws Exception;

    protected abstract boolean isAggregated(I i) throws Exception;

    protected abstract boolean isContentLengthInvalid(S s, int i) throws Exception;

    protected abstract boolean isContentMessage(I i) throws Exception;

    protected abstract boolean isLastContentMessage(C c) throws Exception;

    protected abstract boolean isStartMessage(I i) throws Exception;

    protected abstract Object newContinueResponse(S s, int i, ChannelPipeline channelPipeline) throws Exception;

    protected MessageAggregator(int maxContentLength) {
        validateMaxContentLength(maxContentLength);
        this.maxContentLength = maxContentLength;
    }

    protected MessageAggregator(int maxContentLength, Class<? extends I> inboundMessageType) {
        super(inboundMessageType);
        validateMaxContentLength(maxContentLength);
        this.maxContentLength = maxContentLength;
    }

    private static void validateMaxContentLength(int maxContentLength) {
        if (maxContentLength < 0) {
            throw new IllegalArgumentException("maxContentLength: " + maxContentLength + " (expected: >= 0)");
        }
    }

    public boolean acceptInboundMessage(Object msg) throws Exception {
        if (!super.acceptInboundMessage(msg)) {
            return false;
        }
        I in = msg;
        if ((isContentMessage(in) || isStartMessage(in)) && !isAggregated(in)) {
            return true;
        }
        return false;
    }

    public final int maxContentLength() {
        return this.maxContentLength;
    }

    public final int maxCumulationBufferComponents() {
        return this.maxCumulationBufferComponents;
    }

    public final void setMaxCumulationBufferComponents(int maxCumulationBufferComponents) {
        if (maxCumulationBufferComponents < 2) {
            throw new IllegalArgumentException("maxCumulationBufferComponents: " + maxCumulationBufferComponents + " (expected: >= 2)");
        } else if (this.ctx == null) {
            this.maxCumulationBufferComponents = maxCumulationBufferComponents;
        } else {
            throw new IllegalStateException("decoder properties cannot be changed once the decoder is added to a pipeline.");
        }
    }

    public final boolean isHandlingOversizedMessage() {
        return this.handlingOversizedMessage;
    }

    protected final ChannelHandlerContext ctx() {
        if (this.ctx != null) {
            return this.ctx;
        }
        throw new IllegalStateException("not added to a pipeline yet");
    }

    protected void decode(ChannelHandlerContext ctx, I msg, List<Object> out) throws Exception {
        O currentMessage = this.currentMessage;
        CompositeByteBuf content;
        if (isStartMessage(msg)) {
            this.handlingOversizedMessage = false;
            if (currentMessage != null) {
                throw new MessageAggregationException();
            }
            S m = msg;
            Object continueResponse = newContinueResponse(m, this.maxContentLength, ctx.pipeline());
            if (continueResponse != null) {
                ChannelFutureListener listener = this.continueResponseWriteListener;
                if (listener == null) {
                    final ChannelHandlerContext channelHandlerContext = ctx;
                    listener = new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (!future.isSuccess()) {
                                channelHandlerContext.fireExceptionCaught(future.cause());
                            }
                        }
                    };
                    this.continueResponseWriteListener = listener;
                }
                boolean closeAfterWrite = closeAfterContinueResponse(continueResponse);
                this.handlingOversizedMessage = ignoreContentAfterContinueResponse(continueResponse);
                ChannelFuture future = ctx.writeAndFlush(continueResponse).addListener(listener);
                if (closeAfterWrite) {
                    future.addListener(ChannelFutureListener.CLOSE);
                    return;
                } else if (this.handlingOversizedMessage) {
                    return;
                }
            }
            if (isContentLengthInvalid(m, this.maxContentLength)) {
                invokeHandleOversizedMessage(ctx, m);
                return;
            }
            if (!(m instanceof DecoderResultProvider) || ((DecoderResultProvider) m).decoderResult().isSuccess()) {
                content = ctx.alloc().compositeBuffer(this.maxCumulationBufferComponents);
                if (m instanceof ByteBufHolder) {
                    appendPartialContent(content, ((ByteBufHolder) m).content());
                }
                this.currentMessage = beginAggregation(m, content);
                return;
            }
            O aggregated;
            if ((m instanceof ByteBufHolder) && ((ByteBufHolder) m).content().isReadable()) {
                aggregated = beginAggregation(m, ((ByteBufHolder) m).content().retain());
            } else {
                aggregated = beginAggregation(m, Unpooled.EMPTY_BUFFER);
            }
            finishAggregation(aggregated);
            out.add(aggregated);
            this.currentMessage = null;
        } else if (isContentMessage(msg)) {
            ByteBufHolder m2 = (ByteBufHolder) msg;
            ByteBuf partialContent = ((ByteBufHolder) msg).content();
            boolean isLastContentMessage = isLastContentMessage(m2);
            if (this.handlingOversizedMessage) {
                if (isLastContentMessage) {
                    this.currentMessage = null;
                }
            } else if (currentMessage == null) {
                throw new MessageAggregationException();
            } else {
                content = (CompositeByteBuf) currentMessage.content();
                if (content.readableBytes() > this.maxContentLength - partialContent.readableBytes()) {
                    invokeHandleOversizedMessage(ctx, currentMessage);
                    return;
                }
                boolean last;
                appendPartialContent(content, partialContent);
                aggregate(currentMessage, m2);
                if (m2 instanceof DecoderResultProvider) {
                    DecoderResult decoderResult = ((DecoderResultProvider) m2).decoderResult();
                    if (decoderResult.isSuccess()) {
                        last = isLastContentMessage;
                    } else {
                        if (currentMessage instanceof DecoderResultProvider) {
                            ((DecoderResultProvider) currentMessage).setDecoderResult(DecoderResult.failure(decoderResult.cause()));
                        }
                        last = true;
                    }
                } else {
                    last = isLastContentMessage;
                }
                if (last) {
                    finishAggregation(currentMessage);
                    out.add(currentMessage);
                    this.currentMessage = null;
                }
            }
        } else {
            throw new MessageAggregationException();
        }
    }

    private static void appendPartialContent(CompositeByteBuf content, ByteBuf partialContent) {
        if (partialContent.isReadable()) {
            partialContent.retain();
            content.addComponent(true, partialContent);
        }
    }

    protected void aggregate(O o, C c) throws Exception {
    }

    protected void finishAggregation(O o) throws Exception {
    }

    private void invokeHandleOversizedMessage(ChannelHandlerContext ctx, S oversized) throws Exception {
        this.handlingOversizedMessage = true;
        this.currentMessage = null;
        try {
            handleOversizedMessage(ctx, oversized);
        } finally {
            ReferenceCountUtil.release(oversized);
        }
    }

    protected void handleOversizedMessage(ChannelHandlerContext ctx, S s) throws Exception {
        ctx.fireExceptionCaught(new TooLongFrameException("content length exceeded " + maxContentLength() + " bytes."));
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (this.currentMessage != null) {
            this.currentMessage.release();
            this.currentMessage = null;
        }
        super.channelInactive(ctx);
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        if (this.currentMessage != null) {
            this.currentMessage.release();
            this.currentMessage = null;
        }
    }
}
