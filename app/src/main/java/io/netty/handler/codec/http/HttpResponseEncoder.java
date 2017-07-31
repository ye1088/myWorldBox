package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;

public class HttpResponseEncoder extends HttpObjectEncoder<HttpResponse> {
    public boolean acceptOutboundMessage(Object msg) throws Exception {
        return super.acceptOutboundMessage(msg) && !(msg instanceof HttpRequest);
    }

    protected void encodeInitialLine(ByteBuf buf, HttpResponse response) throws Exception {
        response.protocolVersion().encode(buf);
        buf.writeByte(32);
        response.status().encode(buf);
        buf.writeBytes(CRLF);
    }
}
