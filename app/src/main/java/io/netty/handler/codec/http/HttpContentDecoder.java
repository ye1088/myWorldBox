package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import java.util.List;

public abstract class HttpContentDecoder extends MessageToMessageDecoder<HttpObject> {
    static final String IDENTITY = HttpHeaderValues.IDENTITY.toString();
    private boolean continueResponse;
    protected ChannelHandlerContext ctx;
    private EmbeddedChannel decoder;

    protected abstract EmbeddedChannel newContentDecoder(String str) throws Exception;

    protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
        if ((msg instanceof HttpResponse) && ((HttpResponse) msg).status().code() == 100) {
            if (!(msg instanceof LastHttpContent)) {
                this.continueResponse = true;
            }
            out.add(ReferenceCountUtil.retain(msg));
        } else if (this.continueResponse) {
            if (msg instanceof LastHttpContent) {
                this.continueResponse = false;
            }
            out.add(ReferenceCountUtil.retain(msg));
        } else {
            if (msg instanceof HttpMessage) {
                cleanup();
                HttpMessage message = (HttpMessage) msg;
                HttpHeaders headers = message.headers();
                String contentEncoding = headers.get(HttpHeaderNames.CONTENT_ENCODING);
                if (contentEncoding != null) {
                    contentEncoding = contentEncoding.trim();
                } else {
                    contentEncoding = IDENTITY;
                }
                this.decoder = newContentDecoder(contentEncoding);
                if (this.decoder == null) {
                    if (message instanceof HttpContent) {
                        ((HttpContent) message).retain();
                    }
                    out.add(message);
                    return;
                }
                headers.remove(HttpHeaderNames.CONTENT_LENGTH);
                headers.set(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
                CharSequence targetContentEncoding = getTargetContentEncoding(contentEncoding);
                if (HttpHeaderValues.IDENTITY.contentEquals(targetContentEncoding)) {
                    headers.remove(HttpHeaderNames.CONTENT_ENCODING);
                } else {
                    headers.set(HttpHeaderNames.CONTENT_ENCODING, targetContentEncoding);
                }
                if (message instanceof HttpContent) {
                    HttpMessage copy;
                    if (message instanceof HttpRequest) {
                        HttpRequest r = (HttpRequest) message;
                        copy = new DefaultHttpRequest(r.protocolVersion(), r.method(), r.uri());
                    } else if (message instanceof HttpResponse) {
                        HttpResponse r2 = (HttpResponse) message;
                        copy = new DefaultHttpResponse(r2.protocolVersion(), r2.status());
                    } else {
                        throw new CodecException("Object of class " + message.getClass().getName() + " is not a HttpRequest or HttpResponse");
                    }
                    copy.headers().set(message.headers());
                    copy.setDecoderResult(message.decoderResult());
                    out.add(copy);
                } else {
                    out.add(message);
                }
            }
            if (msg instanceof HttpContent) {
                HttpContent c = (HttpContent) msg;
                if (this.decoder == null) {
                    out.add(c.retain());
                } else {
                    decodeContent(c, out);
                }
            }
        }
    }

    private void decodeContent(HttpContent c, List<Object> out) {
        decode(c.content(), out);
        if (c instanceof LastHttpContent) {
            finishDecode(out);
            HttpHeaders headers = ((LastHttpContent) c).trailingHeaders();
            if (headers.isEmpty()) {
                out.add(LastHttpContent.EMPTY_LAST_CONTENT);
            } else {
                out.add(new ComposedLastHttpContent(headers));
            }
        }
    }

    protected String getTargetContentEncoding(String contentEncoding) throws Exception {
        return IDENTITY;
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        cleanup();
        super.handlerRemoved(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        cleanup();
        super.channelInactive(ctx);
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.handlerAdded(ctx);
    }

    private void cleanup() {
        if (this.decoder != null) {
            if (this.decoder.finish()) {
                while (true) {
                    ByteBuf buf = (ByteBuf) this.decoder.readInbound();
                    if (buf == null) {
                        break;
                    }
                    buf.release();
                }
            }
            this.decoder = null;
        }
    }

    private void decode(ByteBuf in, List<Object> out) {
        this.decoder.writeInbound(new Object[]{in.retain()});
        fetchDecoderOutput(out);
    }

    private void finishDecode(List<Object> out) {
        if (this.decoder.finish()) {
            fetchDecoderOutput(out);
        }
        this.decoder = null;
    }

    private void fetchDecoderOutput(List<Object> out) {
        while (true) {
            ByteBuf buf = (ByteBuf) this.decoder.readInbound();
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
