package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.MessageAggregator;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class HttpObjectAggregator extends MessageAggregator<HttpObject, HttpMessage, HttpContent, FullHttpMessage> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HttpObjectAggregator.class.desiredAssertionStatus());
    private static final FullHttpResponse CONTINUE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER);
    private static final FullHttpResponse EXPECTATION_FAILED = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.EXPECTATION_FAILED, Unpooled.EMPTY_BUFFER);
    private static final FullHttpResponse TOO_LARGE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_ENTITY_TOO_LARGE, Unpooled.EMPTY_BUFFER);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(HttpObjectAggregator.class);
    private final boolean closeOnExpectationFailed;

    private static abstract class AggregatedFullHttpMessage implements FullHttpMessage {
        private final ByteBuf content;
        protected final HttpMessage message;
        private HttpHeaders trailingHeaders;

        public abstract FullHttpMessage copy();

        public abstract FullHttpMessage duplicate();

        public abstract FullHttpMessage retainedDuplicate();

        AggregatedFullHttpMessage(HttpMessage message, ByteBuf content, HttpHeaders trailingHeaders) {
            this.message = message;
            this.content = content;
            this.trailingHeaders = trailingHeaders;
        }

        public HttpHeaders trailingHeaders() {
            HttpHeaders trailingHeaders = this.trailingHeaders;
            if (trailingHeaders == null) {
                return EmptyHttpHeaders.INSTANCE;
            }
            return trailingHeaders;
        }

        void setTrailingHeaders(HttpHeaders trailingHeaders) {
            this.trailingHeaders = trailingHeaders;
        }

        public HttpVersion getProtocolVersion() {
            return this.message.protocolVersion();
        }

        public HttpVersion protocolVersion() {
            return this.message.protocolVersion();
        }

        public FullHttpMessage setProtocolVersion(HttpVersion version) {
            this.message.setProtocolVersion(version);
            return this;
        }

        public HttpHeaders headers() {
            return this.message.headers();
        }

        public DecoderResult decoderResult() {
            return this.message.decoderResult();
        }

        public DecoderResult getDecoderResult() {
            return this.message.decoderResult();
        }

        public void setDecoderResult(DecoderResult result) {
            this.message.setDecoderResult(result);
        }

        public ByteBuf content() {
            return this.content;
        }

        public int refCnt() {
            return this.content.refCnt();
        }

        public FullHttpMessage retain() {
            this.content.retain();
            return this;
        }

        public FullHttpMessage retain(int increment) {
            this.content.retain(increment);
            return this;
        }

        public FullHttpMessage touch(Object hint) {
            this.content.touch(hint);
            return this;
        }

        public FullHttpMessage touch() {
            this.content.touch();
            return this;
        }

        public boolean release() {
            return this.content.release();
        }

        public boolean release(int decrement) {
            return this.content.release(decrement);
        }
    }

    private static final class AggregatedFullHttpRequest extends AggregatedFullHttpMessage implements FullHttpRequest {
        AggregatedFullHttpRequest(HttpRequest request, ByteBuf content, HttpHeaders trailingHeaders) {
            super(request, content, trailingHeaders);
        }

        public FullHttpRequest copy() {
            return replace(content().copy());
        }

        public FullHttpRequest duplicate() {
            return replace(content().duplicate());
        }

        public FullHttpRequest retainedDuplicate() {
            return replace(content().retainedDuplicate());
        }

        public FullHttpRequest replace(ByteBuf content) {
            DefaultFullHttpRequest dup = new DefaultFullHttpRequest(protocolVersion(), method(), uri(), content);
            dup.headers().set(headers());
            dup.trailingHeaders().set(trailingHeaders());
            return dup;
        }

        public FullHttpRequest retain(int increment) {
            super.retain(increment);
            return this;
        }

        public FullHttpRequest retain() {
            super.retain();
            return this;
        }

        public FullHttpRequest touch() {
            super.touch();
            return this;
        }

        public FullHttpRequest touch(Object hint) {
            super.touch(hint);
            return this;
        }

        public FullHttpRequest setMethod(HttpMethod method) {
            ((HttpRequest) this.message).setMethod(method);
            return this;
        }

        public FullHttpRequest setUri(String uri) {
            ((HttpRequest) this.message).setUri(uri);
            return this;
        }

        public HttpMethod getMethod() {
            return ((HttpRequest) this.message).method();
        }

        public String getUri() {
            return ((HttpRequest) this.message).uri();
        }

        public HttpMethod method() {
            return getMethod();
        }

        public String uri() {
            return getUri();
        }

        public FullHttpRequest setProtocolVersion(HttpVersion version) {
            super.setProtocolVersion(version);
            return this;
        }

        public String toString() {
            return this;
        }
    }

    private static final class AggregatedFullHttpResponse extends AggregatedFullHttpMessage implements FullHttpResponse {
        AggregatedFullHttpResponse(HttpResponse message, ByteBuf content, HttpHeaders trailingHeaders) {
            super(message, content, trailingHeaders);
        }

        public FullHttpResponse copy() {
            return replace(content().copy());
        }

        public FullHttpResponse duplicate() {
            return replace(content().duplicate());
        }

        public FullHttpResponse retainedDuplicate() {
            return replace(content().retainedDuplicate());
        }

        public FullHttpResponse replace(ByteBuf content) {
            DefaultFullHttpResponse dup = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), content);
            dup.headers().set(headers());
            dup.trailingHeaders().set(trailingHeaders());
            return dup;
        }

        public FullHttpResponse setStatus(HttpResponseStatus status) {
            ((HttpResponse) this.message).setStatus(status);
            return this;
        }

        public HttpResponseStatus getStatus() {
            return ((HttpResponse) this.message).status();
        }

        public HttpResponseStatus status() {
            return getStatus();
        }

        public FullHttpResponse setProtocolVersion(HttpVersion version) {
            super.setProtocolVersion(version);
            return this;
        }

        public FullHttpResponse retain(int increment) {
            super.retain(increment);
            return this;
        }

        public FullHttpResponse retain() {
            super.retain();
            return this;
        }

        public FullHttpResponse touch(Object hint) {
            super.touch(hint);
            return this;
        }

        public FullHttpResponse touch() {
            super.touch();
            return this;
        }

        public String toString() {
            return this;
        }
    }

    static {
        EXPECTATION_FAILED.headers().set(HttpHeaderNames.CONTENT_LENGTH, Integer.valueOf(0));
        TOO_LARGE.headers().set(HttpHeaderNames.CONTENT_LENGTH, Integer.valueOf(0));
    }

    public HttpObjectAggregator(int maxContentLength) {
        this(maxContentLength, false);
    }

    public HttpObjectAggregator(int maxContentLength, boolean closeOnExpectationFailed) {
        super(maxContentLength);
        this.closeOnExpectationFailed = closeOnExpectationFailed;
    }

    protected boolean isStartMessage(HttpObject msg) throws Exception {
        return msg instanceof HttpMessage;
    }

    protected boolean isContentMessage(HttpObject msg) throws Exception {
        return msg instanceof HttpContent;
    }

    protected boolean isLastContentMessage(HttpContent msg) throws Exception {
        return msg instanceof LastHttpContent;
    }

    protected boolean isAggregated(HttpObject msg) throws Exception {
        return msg instanceof FullHttpMessage;
    }

    protected boolean isContentLengthInvalid(HttpMessage start, int maxContentLength) {
        return HttpUtil.getContentLength(start, -1) > ((long) maxContentLength);
    }

    protected Object newContinueResponse(HttpMessage start, int maxContentLength, ChannelPipeline pipeline) {
        if (!HttpUtil.is100ContinueExpected(start)) {
            return null;
        }
        if (HttpUtil.getContentLength(start, -1) <= ((long) maxContentLength)) {
            return CONTINUE.retainedDuplicate();
        }
        pipeline.fireUserEventTriggered(HttpExpectationFailedEvent.INSTANCE);
        return EXPECTATION_FAILED.retainedDuplicate();
    }

    protected boolean closeAfterContinueResponse(Object msg) {
        return this.closeOnExpectationFailed && ignoreContentAfterContinueResponse(msg);
    }

    protected boolean ignoreContentAfterContinueResponse(Object msg) {
        return (msg instanceof HttpResponse) && ((HttpResponse) msg).status().code() == HttpResponseStatus.EXPECTATION_FAILED.code();
    }

    protected FullHttpMessage beginAggregation(HttpMessage start, ByteBuf content) throws Exception {
        if ($assertionsDisabled || !(start instanceof FullHttpMessage)) {
            HttpUtil.setTransferEncodingChunked(start, false);
            if (start instanceof HttpRequest) {
                return new AggregatedFullHttpRequest((HttpRequest) start, content, null);
            }
            if (start instanceof HttpResponse) {
                return new AggregatedFullHttpResponse((HttpResponse) start, content, null);
            }
            throw new Error();
        }
        throw new AssertionError();
    }

    protected void aggregate(FullHttpMessage aggregated, HttpContent content) throws Exception {
        if (content instanceof LastHttpContent) {
            ((AggregatedFullHttpMessage) aggregated).setTrailingHeaders(((LastHttpContent) content).trailingHeaders());
        }
    }

    protected void finishAggregation(FullHttpMessage aggregated) throws Exception {
        if (!HttpUtil.isContentLengthSet(aggregated)) {
            aggregated.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(aggregated.content().readableBytes()));
        }
    }

    protected void handleOversizedMessage(final ChannelHandlerContext ctx, HttpMessage oversized) throws Exception {
        if (oversized instanceof HttpRequest) {
            ChannelFuture future = ctx.writeAndFlush(TOO_LARGE.retainedDuplicate()).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        HttpObjectAggregator.logger.debug("Failed to send a_isRightVersion 413 Request Entity Too Large.", future.cause());
                        ctx.close();
                    }
                }
            });
            if ((oversized instanceof FullHttpMessage) || !(HttpUtil.is100ContinueExpected(oversized) || HttpUtil.isKeepAlive(oversized))) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
            HttpObjectDecoder decoder = (HttpObjectDecoder) ctx.pipeline().get(HttpObjectDecoder.class);
            if (decoder != null) {
                decoder.reset();
            }
        } else if (oversized instanceof HttpResponse) {
            ctx.close();
            throw new TooLongFrameException("Response entity too large: " + oversized);
        } else {
            throw new IllegalStateException();
        }
    }
}
