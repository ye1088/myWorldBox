package io.netty.handler.codec.http2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultLastHttpContent;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.ReferenceCountUtil;
import java.util.List;

public class Http2ServerDowngrader extends MessageToMessageCodec<Http2StreamFrame, HttpObject> {
    private final boolean validateHeaders;

    public Http2ServerDowngrader(boolean validateHeaders) {
        this.validateHeaders = validateHeaders;
    }

    public Http2ServerDowngrader() {
        this(true);
    }

    public boolean acceptInboundMessage(Object msg) throws Exception {
        return (msg instanceof Http2HeadersFrame) || (msg instanceof Http2DataFrame);
    }

    protected void decode(ChannelHandlerContext ctx, Http2StreamFrame frame, List<Object> out) throws Exception {
        if (frame instanceof Http2HeadersFrame) {
            Http2HeadersFrame headersFrame = (Http2HeadersFrame) frame;
            Http2Headers headers = headersFrame.headers();
            if (!headersFrame.isEndStream()) {
                out.add(HttpConversionUtil.toHttpRequest(0, headersFrame.headers(), this.validateHeaders));
            } else if (headers.method() == null) {
                LastHttpContent last = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER, this.validateHeaders);
                HttpConversionUtil.addHttp2ToHttpHeaders(0, headers, last.trailingHeaders(), HttpVersion.HTTP_1_1, true, true);
                out.add(last);
            } else {
                out.add(HttpConversionUtil.toFullHttpRequest(0, headers, ctx.alloc(), this.validateHeaders));
            }
        } else if (frame instanceof Http2DataFrame) {
            Http2DataFrame dataFrame = (Http2DataFrame) frame;
            if (dataFrame.isEndStream()) {
                out.add(new DefaultLastHttpContent(dataFrame.content(), this.validateHeaders));
            } else {
                out.add(new DefaultHttpContent(dataFrame.content()));
            }
        }
        ReferenceCountUtil.retain(frame);
    }

    private void encodeLastContent(LastHttpContent last, List<Object> out) {
        boolean needFiller = !(last instanceof FullHttpResponse) && last.trailingHeaders().isEmpty();
        if (last.content().isReadable() || needFiller) {
            out.add(new DefaultHttp2DataFrame(last.content(), last.trailingHeaders().isEmpty()));
        }
        if (!last.trailingHeaders().isEmpty()) {
            out.add(new DefaultHttp2HeadersFrame(HttpConversionUtil.toHttp2Headers(last.trailingHeaders(), this.validateHeaders), true));
        }
    }

    protected void encode(ChannelHandlerContext ctx, HttpObject obj, List<Object> out) throws Exception {
        if (obj instanceof HttpResponse) {
            Http2Headers headers = HttpConversionUtil.toHttp2Headers((HttpResponse) obj, this.validateHeaders);
            boolean noMoreFrames = false;
            if (obj instanceof FullHttpResponse) {
                FullHttpResponse full = (FullHttpResponse) obj;
                if (full.content().isReadable() || !full.trailingHeaders().isEmpty()) {
                    noMoreFrames = false;
                } else {
                    noMoreFrames = true;
                }
            }
            out.add(new DefaultHttp2HeadersFrame(headers, noMoreFrames));
        }
        if (obj instanceof LastHttpContent) {
            encodeLastContent((LastHttpContent) obj, out);
        } else if (obj instanceof HttpContent) {
            out.add(new DefaultHttp2DataFrame(((HttpContent) obj).content(), false));
        }
        ReferenceCountUtil.retain(obj);
    }
}
