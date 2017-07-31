package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.PrematureChannelClosureException;
import io.netty.handler.codec.http.HttpClientUpgradeHandler.SourceCodec;
import io.netty.util.ReferenceCountUtil;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

public final class HttpClientCodec extends CombinedChannelDuplexHandler<HttpResponseDecoder, HttpRequestEncoder> implements SourceCodec {
    private boolean done;
    private final boolean failOnMissingResponse;
    private final Queue<HttpMethod> queue;
    private final AtomicLong requestResponseCounter;

    private final class Decoder extends HttpResponseDecoder {
        Decoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders) {
            super(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders);
        }

        Decoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize) {
            super(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders, initialBufferSize);
        }

        protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
            if (HttpClientCodec.this.done) {
                int readable = actualReadableBytes();
                if (readable != 0) {
                    out.add(buffer.readBytes(readable));
                    return;
                }
                return;
            }
            int oldSize = out.size();
            super.decode(ctx, buffer, out);
            if (HttpClientCodec.this.failOnMissingResponse) {
                int size = out.size();
                for (int i = oldSize; i < size; i++) {
                    decrement(out.get(i));
                }
            }
        }

        private void decrement(Object msg) {
            if (msg != null && (msg instanceof LastHttpContent)) {
                HttpClientCodec.this.requestResponseCounter.decrementAndGet();
            }
        }

        protected boolean isContentAlwaysEmpty(HttpMessage msg) {
            int statusCode = ((HttpResponse) msg).status().code();
            if (statusCode == 100) {
                return true;
            }
            HttpMethod method = (HttpMethod) HttpClientCodec.this.queue.poll();
            switch (method.name().charAt(0)) {
                case 'C':
                    if (statusCode == 200 && HttpMethod.CONNECT.equals(method)) {
                        HttpClientCodec.this.done = true;
                        HttpClientCodec.this.queue.clear();
                        return true;
                    }
                case 'H':
                    if (HttpMethod.HEAD.equals(method)) {
                        return true;
                    }
                    break;
            }
            return super.isContentAlwaysEmpty(msg);
        }

        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            if (HttpClientCodec.this.failOnMissingResponse) {
                long missingResponses = HttpClientCodec.this.requestResponseCounter.get();
                if (missingResponses > 0) {
                    ctx.fireExceptionCaught(new PrematureChannelClosureException("channel gone inactive with " + missingResponses + " missing response(s)"));
                }
            }
        }
    }

    private final class Encoder extends HttpRequestEncoder {
        boolean upgraded;

        private Encoder() {
        }

        protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
            if (this.upgraded) {
                out.add(ReferenceCountUtil.retain(msg));
                return;
            }
            if ((msg instanceof HttpRequest) && !HttpClientCodec.this.done) {
                HttpClientCodec.this.queue.offer(((HttpRequest) msg).method());
            }
            super.encode(ctx, msg, out);
            if (HttpClientCodec.this.failOnMissingResponse && (msg instanceof LastHttpContent)) {
                HttpClientCodec.this.requestResponseCounter.incrementAndGet();
            }
        }
    }

    public HttpClientCodec() {
        this(4096, 8192, 8192, false);
    }

    public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
        this(maxInitialLineLength, maxHeaderSize, maxChunkSize, false);
    }

    public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse) {
        this(maxInitialLineLength, maxHeaderSize, maxChunkSize, failOnMissingResponse, true);
    }

    public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse, boolean validateHeaders) {
        this.queue = new ArrayDeque();
        this.requestResponseCounter = new AtomicLong();
        init(new Decoder(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders), new Encoder());
        this.failOnMissingResponse = failOnMissingResponse;
    }

    public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse, boolean validateHeaders, int initialBufferSize) {
        this.queue = new ArrayDeque();
        this.requestResponseCounter = new AtomicLong();
        init(new Decoder(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders, initialBufferSize), new Encoder());
        this.failOnMissingResponse = failOnMissingResponse;
    }

    public void prepareUpgradeFrom(ChannelHandlerContext ctx) {
        ((Encoder) outboundHandler()).upgraded = true;
    }

    public void upgradeFrom(ChannelHandlerContext ctx) {
        ctx.pipeline().remove((ChannelHandler) this);
    }

    public void setSingleDecode(boolean singleDecode) {
        ((HttpResponseDecoder) inboundHandler()).setSingleDecode(singleDecode);
    }

    public boolean isSingleDecode() {
        return ((HttpResponseDecoder) inboundHandler()).isSingleDecode();
    }
}
