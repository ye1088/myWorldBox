package io.netty.handler.codec.http.cors;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class CorsHandler extends ChannelDuplexHandler {
    private static final String ANY_ORIGIN = "*";
    private static final String NULL_ORIGIN = "null";
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(CorsHandler.class);
    private final CorsConfig config;
    private HttpRequest request;

    public CorsHandler(CorsConfig config) {
        this.config = (CorsConfig) ObjectUtil.checkNotNull(config, "config");
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (this.config.isCorsSupportEnabled() && (msg instanceof HttpRequest)) {
            this.request = (HttpRequest) msg;
            if (isPreflightRequest(this.request)) {
                handlePreflight(ctx, this.request);
                return;
            } else if (this.config.isShortCircuit() && !validateOrigin()) {
                forbidden(ctx, this.request);
                return;
            }
        }
        ctx.fireChannelRead(msg);
    }

    private void handlePreflight(ChannelHandlerContext ctx, HttpRequest request) {
        HttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.OK, true, true);
        if (setOrigin(response)) {
            setAllowMethods(response);
            setAllowHeaders(response);
            setAllowCredentials(response);
            setMaxAge(response);
            setPreflightHeaders(response);
        }
        ReferenceCountUtil.release(request);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private void setPreflightHeaders(HttpResponse response) {
        response.headers().add(this.config.preflightResponseHeaders());
    }

    private boolean setOrigin(HttpResponse response) {
        String origin = this.request.headers().get(HttpHeaderNames.ORIGIN);
        if (origin != null) {
            if (NULL_ORIGIN.equals(origin) && this.config.isNullOriginAllowed()) {
                setNullOrigin(response);
                return true;
            } else if (this.config.isAnyOriginSupported()) {
                if (this.config.isCredentialsAllowed()) {
                    echoRequestOrigin(response);
                    setVaryHeader(response);
                    return true;
                }
                setAnyOrigin(response);
                return true;
            } else if (this.config.origins().contains(origin)) {
                setOrigin(response, origin);
                setVaryHeader(response);
                return true;
            } else {
                logger.debug("Request origin [{}]] was not among the configured origins [{}]", origin, this.config.origins());
            }
        }
        return false;
    }

    private boolean validateOrigin() {
        if (this.config.isAnyOriginSupported()) {
            return true;
        }
        String origin = this.request.headers().get(HttpHeaderNames.ORIGIN);
        if (origin == null) {
            return true;
        }
        if (NULL_ORIGIN.equals(origin) && this.config.isNullOriginAllowed()) {
            return true;
        }
        return this.config.origins().contains(origin);
    }

    private void echoRequestOrigin(HttpResponse response) {
        setOrigin(response, this.request.headers().get(HttpHeaderNames.ORIGIN));
    }

    private static void setVaryHeader(HttpResponse response) {
        response.headers().set(HttpHeaderNames.VARY, HttpHeaderNames.ORIGIN);
    }

    private static void setAnyOrigin(HttpResponse response) {
        setOrigin(response, "*");
    }

    private static void setNullOrigin(HttpResponse response) {
        setOrigin(response, NULL_ORIGIN);
    }

    private static void setOrigin(HttpResponse response, String origin) {
        response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
    }

    private void setAllowCredentials(HttpResponse response) {
        if (this.config.isCredentialsAllowed() && !response.headers().get(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN).equals("*")) {
            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        }
    }

    private static boolean isPreflightRequest(HttpRequest request) {
        HttpHeaders headers = request.headers();
        return request.method().equals(HttpMethod.OPTIONS) && headers.contains(HttpHeaderNames.ORIGIN) && headers.contains(HttpHeaderNames.ACCESS_CONTROL_REQUEST_METHOD);
    }

    private void setExposeHeaders(HttpResponse response) {
        if (!this.config.exposedHeaders().isEmpty()) {
            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_EXPOSE_HEADERS, this.config.exposedHeaders());
        }
    }

    private void setAllowMethods(HttpResponse response) {
        response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, this.config.allowedRequestMethods());
    }

    private void setAllowHeaders(HttpResponse response) {
        response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, this.config.allowedRequestHeaders());
    }

    private void setMaxAge(HttpResponse response) {
        response.headers().set(HttpHeaderNames.ACCESS_CONTROL_MAX_AGE, Long.valueOf(this.config.maxAge()));
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (this.config.isCorsSupportEnabled() && (msg instanceof HttpResponse)) {
            HttpResponse response = (HttpResponse) msg;
            if (setOrigin(response)) {
                setAllowCredentials(response);
                setExposeHeaders(response);
            }
        }
        ctx.writeAndFlush(msg, promise);
    }

    private static void forbidden(ChannelHandlerContext ctx, HttpRequest request) {
        ctx.writeAndFlush(new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.FORBIDDEN)).addListener(ChannelFutureListener.CLOSE);
        ReferenceCountUtil.release(request);
    }
}
