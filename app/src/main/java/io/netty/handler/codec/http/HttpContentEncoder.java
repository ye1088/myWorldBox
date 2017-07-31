package io.netty.handler.codec.http;

import com.integralblue.httpresponsecache.compat.libcore.net.http.HttpEngine;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.ReferenceCountUtil;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public abstract class HttpContentEncoder extends MessageToMessageCodec<HttpRequest, HttpObject> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HttpContentEncoder.class.desiredAssertionStatus());
    private static final int CONTINUE_CODE = HttpResponseStatus.CONTINUE.code();
    private static final CharSequence ZERO_LENGTH_CONNECT = HttpEngine.CONNECT;
    private static final CharSequence ZERO_LENGTH_HEAD = "HEAD";
    private CharSequence acceptEncoding;
    private final Queue<CharSequence> acceptEncodingQueue = new ArrayDeque();
    private EmbeddedChannel encoder;
    private State state = State.AWAIT_HEADERS;

    public static final class Result {
        private final EmbeddedChannel contentEncoder;
        private final String targetContentEncoding;

        public Result(String targetContentEncoding, EmbeddedChannel contentEncoder) {
            if (targetContentEncoding == null) {
                throw new NullPointerException("targetContentEncoding");
            } else if (contentEncoder == null) {
                throw new NullPointerException("contentEncoder");
            } else {
                this.targetContentEncoding = targetContentEncoding;
                this.contentEncoder = contentEncoder;
            }
        }

        public String targetContentEncoding() {
            return this.targetContentEncoding;
        }

        public EmbeddedChannel contentEncoder() {
            return this.contentEncoder;
        }
    }

    private enum State {
        PASS_THROUGH,
        AWAIT_HEADERS,
        AWAIT_CONTENT
    }

    protected abstract Result beginEncode(HttpResponse httpResponse, String str) throws Exception;

    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return (msg instanceof HttpContent) || (msg instanceof HttpResponse);
    }

    protected void decode(ChannelHandlerContext ctx, HttpRequest msg, List<Object> out) throws Exception {
        CharSequence acceptedEncoding = msg.headers().get(HttpHeaderNames.ACCEPT_ENCODING);
        if (acceptedEncoding == null) {
            acceptedEncoding = HttpContentDecoder.IDENTITY;
        }
        HttpMethod meth = msg.method();
        if (meth == HttpMethod.HEAD) {
            acceptedEncoding = ZERO_LENGTH_HEAD;
        } else if (meth == HttpMethod.CONNECT) {
            acceptedEncoding = ZERO_LENGTH_CONNECT;
        }
        this.acceptEncodingQueue.add(acceptedEncoding);
        out.add(ReferenceCountUtil.retain(msg));
    }

    protected void encode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
        boolean isFull = (msg instanceof HttpResponse) && (msg instanceof LastHttpContent);
        switch (this.state) {
            case AWAIT_HEADERS:
                ensureHeaders(msg);
                if ($assertionsDisabled || this.encoder == null) {
                    HttpResponse res = (HttpResponse) msg;
                    int code = res.status().code();
                    if (code == CONTINUE_CODE) {
                        this.acceptEncoding = null;
                    } else {
                        this.acceptEncoding = (CharSequence) this.acceptEncodingQueue.poll();
                        if (this.acceptEncoding == null) {
                            throw new IllegalStateException("cannot send more responses than requests");
                        }
                    }
                    if (isPassthru(res.protocolVersion(), code, this.acceptEncoding)) {
                        if (isFull) {
                            out.add(ReferenceCountUtil.retain(res));
                            return;
                        }
                        out.add(res);
                        this.state = State.PASS_THROUGH;
                        return;
                    } else if (!isFull || ((ByteBufHolder) res).content().isReadable()) {
                        Result result = beginEncode(res, this.acceptEncoding.toString());
                        if (result != null) {
                            this.encoder = result.contentEncoder();
                            res.headers().set(HttpHeaderNames.CONTENT_ENCODING, result.targetContentEncoding());
                            res.headers().remove(HttpHeaderNames.CONTENT_LENGTH);
                            res.headers().set(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
                            if (isFull) {
                                HttpResponse newRes = new DefaultHttpResponse(res.protocolVersion(), res.status());
                                newRes.headers().set(res.headers());
                                out.add(newRes);
                                break;
                            }
                            out.add(res);
                            this.state = State.AWAIT_CONTENT;
                            if (!(msg instanceof HttpContent)) {
                                return;
                            }
                        } else if (isFull) {
                            out.add(ReferenceCountUtil.retain(res));
                            return;
                        } else {
                            out.add(res);
                            this.state = State.PASS_THROUGH;
                            return;
                        }
                    } else {
                        out.add(ReferenceCountUtil.retain(res));
                        return;
                    }
                }
                throw new AssertionError();
                break;
            case AWAIT_CONTENT:
                break;
            case PASS_THROUGH:
                ensureContent(msg);
                out.add(ReferenceCountUtil.retain(msg));
                if (msg instanceof LastHttpContent) {
                    this.state = State.AWAIT_HEADERS;
                    return;
                }
                return;
            default:
                return;
        }
        ensureContent(msg);
        if (encodeContent((HttpContent) msg, out)) {
            this.state = State.AWAIT_HEADERS;
        }
    }

    private static boolean isPassthru(HttpVersion version, int code, CharSequence httpMethod) {
        return code < 200 || code == 204 || code == 304 || httpMethod == ZERO_LENGTH_HEAD || ((httpMethod == ZERO_LENGTH_CONNECT && code == 200) || version == HttpVersion.HTTP_1_0);
    }

    private static void ensureHeaders(HttpObject msg) {
        if (!(msg instanceof HttpResponse)) {
            throw new IllegalStateException("unexpected message type: " + msg.getClass().getName() + " (expected: " + HttpResponse.class.getSimpleName() + ')');
        }
    }

    private static void ensureContent(HttpObject msg) {
        if (!(msg instanceof HttpContent)) {
            throw new IllegalStateException("unexpected message type: " + msg.getClass().getName() + " (expected: " + HttpContent.class.getSimpleName() + ')');
        }
    }

    private boolean encodeContent(HttpContent c, List<Object> out) {
        encode(c.content(), out);
        if (!(c instanceof LastHttpContent)) {
            return false;
        }
        finishEncode(out);
        HttpHeaders headers = ((LastHttpContent) c).trailingHeaders();
        if (headers.isEmpty()) {
            out.add(LastHttpContent.EMPTY_LAST_CONTENT);
        } else {
            out.add(new ComposedLastHttpContent(headers));
        }
        return true;
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        cleanup();
        super.handlerRemoved(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        cleanup();
        super.channelInactive(ctx);
    }

    private void cleanup() {
        if (this.encoder != null) {
            if (this.encoder.finish()) {
                while (true) {
                    ByteBuf buf = (ByteBuf) this.encoder.readOutbound();
                    if (buf == null) {
                        break;
                    }
                    buf.release();
                }
            }
            this.encoder = null;
        }
    }

    private void encode(ByteBuf in, List<Object> out) {
        this.encoder.writeOutbound(new Object[]{in.retain()});
        fetchEncoderOutput(out);
    }

    private void finishEncode(List<Object> out) {
        if (this.encoder.finish()) {
            fetchEncoderOutput(out);
        }
        this.encoder = null;
    }

    private void fetchEncoderOutput(List<Object> out) {
        while (true) {
            ByteBuf buf = (ByteBuf) this.encoder.readOutbound();
            if (buf != null) {
                if (buf.isReadable()) {
                    out.add(new DefaultHttpContent(buf));
                } else {
                    buf.release();
                }
            } else {
                return;
            }
        }
    }
}
