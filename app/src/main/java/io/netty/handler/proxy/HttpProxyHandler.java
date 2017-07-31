package io.netty.handler.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public final class HttpProxyHandler extends ProxyHandler {
    private static final String AUTH_BASIC = "basic";
    private static final String PROTOCOL = "http";
    private final CharSequence authorization;
    private final HttpClientCodec codec;
    private final String password;
    private HttpResponseStatus status;
    private final String username;

    public HttpProxyHandler(SocketAddress proxyAddress) {
        super(proxyAddress);
        this.codec = new HttpClientCodec();
        this.username = null;
        this.password = null;
        this.authorization = null;
    }

    public HttpProxyHandler(SocketAddress proxyAddress, String username, String password) {
        super(proxyAddress);
        this.codec = new HttpClientCodec();
        if (username == null) {
            throw new NullPointerException("username");
        } else if (password == null) {
            throw new NullPointerException("password");
        } else {
            this.username = username;
            this.password = password;
            ByteBuf authz = Unpooled.copiedBuffer(username + ':' + password, CharsetUtil.UTF_8);
            ByteBuf authzBase64 = Base64.encode(authz, false);
            this.authorization = new AsciiString("Basic " + authzBase64.toString(CharsetUtil.US_ASCII));
            authz.release();
            authzBase64.release();
        }
    }

    public String protocol() {
        return "http";
    }

    public String authScheme() {
        return this.authorization != null ? AUTH_BASIC : "none";
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    protected void addCodec(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().addBefore(ctx.name(), null, this.codec);
    }

    protected void removeEncoder(ChannelHandlerContext ctx) throws Exception {
        this.codec.removeOutboundHandler();
    }

    protected void removeDecoder(ChannelHandlerContext ctx) throws Exception {
        this.codec.removeInboundHandler();
    }

    protected Object newInitialMessage(ChannelHandlerContext ctx) throws Exception {
        String rhost;
        InetSocketAddress raddr = (InetSocketAddress) destinationAddress();
        if (raddr.isUnresolved()) {
            rhost = raddr.getHostString();
        } else {
            rhost = raddr.getAddress().getHostAddress();
        }
        String host = rhost + ':' + raddr.getPort();
        FullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.CONNECT, host, Unpooled.EMPTY_BUFFER, false);
        req.headers().set(HttpHeaderNames.HOST, host);
        if (this.authorization != null) {
            req.headers().set(HttpHeaderNames.PROXY_AUTHORIZATION, this.authorization);
        }
        return req;
    }

    protected boolean handleResponse(ChannelHandlerContext ctx, Object response) throws Exception {
        if (response instanceof HttpResponse) {
            if (this.status != null) {
                throw new ProxyConnectException(exceptionMessage("too many responses"));
            }
            this.status = ((HttpResponse) response).status();
        }
        boolean finished = response instanceof LastHttpContent;
        if (finished) {
            if (this.status == null) {
                throw new ProxyConnectException(exceptionMessage("missing response"));
            } else if (this.status.code() != 200) {
                throw new ProxyConnectException(exceptionMessage("status: " + this.status));
            }
        }
        return finished;
    }
}
