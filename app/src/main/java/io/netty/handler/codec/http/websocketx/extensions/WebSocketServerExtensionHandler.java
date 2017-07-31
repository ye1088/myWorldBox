package io.netty.handler.codec.http.websocketx.extensions;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class WebSocketServerExtensionHandler extends ChannelDuplexHandler {
    private final List<WebSocketServerExtensionHandshaker> extensionHandshakers;
    private List<WebSocketServerExtension> validExtensions;

    public WebSocketServerExtensionHandler(WebSocketServerExtensionHandshaker... extensionHandshakers) {
        if (extensionHandshakers == null) {
            throw new NullPointerException("extensionHandshakers");
        } else if (extensionHandshakers.length == 0) {
            throw new IllegalArgumentException("extensionHandshakers must contains at least one handshaker");
        } else {
            this.extensionHandshakers = Arrays.asList(extensionHandshakers);
        }
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            if (WebSocketExtensionUtil.isWebsocketUpgrade(request.headers())) {
                String extensionsHeader = request.headers().getAsString(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);
                if (extensionsHeader != null) {
                    int rsv = 0;
                    for (WebSocketExtensionData extensionData : WebSocketExtensionUtil.extractExtensions(extensionsHeader)) {
                        Iterator<WebSocketServerExtensionHandshaker> extensionHandshakersIterator = this.extensionHandshakers.iterator();
                        WebSocketServerExtension validExtension = null;
                        while (validExtension == null && extensionHandshakersIterator.hasNext()) {
                            validExtension = ((WebSocketServerExtensionHandshaker) extensionHandshakersIterator.next()).handshakeExtension(extensionData);
                        }
                        if (validExtension != null && (validExtension.rsv() & rsv) == 0) {
                            if (this.validExtensions == null) {
                                this.validExtensions = new ArrayList(1);
                            }
                            rsv |= validExtension.rsv();
                            this.validExtensions.add(validExtension);
                        }
                    }
                }
            }
        }
        super.channelRead(ctx, msg);
    }

    public void write(final ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if ((msg instanceof HttpResponse) && WebSocketExtensionUtil.isWebsocketUpgrade(((HttpResponse) msg).headers()) && this.validExtensions != null) {
            HttpResponse response = (HttpResponse) msg;
            String headerValue = response.headers().getAsString(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS);
            for (WebSocketServerExtension extension : this.validExtensions) {
                WebSocketExtensionData extensionData = extension.newReponseData();
                headerValue = WebSocketExtensionUtil.appendExtension(headerValue, extensionData.name(), extensionData.parameters());
            }
            promise.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        for (WebSocketServerExtension extension : WebSocketServerExtensionHandler.this.validExtensions) {
                            WebSocketExtensionDecoder decoder = extension.newExtensionDecoder();
                            WebSocketExtensionEncoder encoder = extension.newExtensionEncoder();
                            ctx.pipeline().addAfter(ctx.name(), decoder.getClass().getName(), decoder);
                            ctx.pipeline().addAfter(ctx.name(), encoder.getClass().getName(), encoder);
                        }
                    }
                    ctx.pipeline().remove(ctx.name());
                }
            });
            if (headerValue != null) {
                response.headers().set(HttpHeaderNames.SEC_WEBSOCKET_EXTENSIONS, headerValue);
            }
        }
        super.write(ctx, msg, promise);
    }
}
