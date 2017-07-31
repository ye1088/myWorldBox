package io.netty.handler.codec.http;

public interface HttpResponse extends HttpMessage {
    @Deprecated
    HttpResponseStatus getStatus();

    HttpResponse setProtocolVersion(HttpVersion httpVersion);

    HttpResponse setStatus(HttpResponseStatus httpResponseStatus);

    HttpResponseStatus status();
}
