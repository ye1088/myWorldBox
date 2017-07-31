package io.netty.handler.codec.http.websocketx;

import com.xiaomi.mipush.sdk.MiPushClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ThrowableUtil;
import java.net.URI;
import java.nio.channels.ClosedChannelException;

public abstract class WebSocketClientHandshaker {
    private static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), WebSocketClientHandshaker.class, "processHandshake(...)"));
    private volatile String actualSubprotocol;
    protected final HttpHeaders customHeaders;
    private final String expectedSubprotocol;
    private volatile boolean handshakeComplete;
    private final int maxFramePayloadLength;
    private final URI uri;
    private final WebSocketVersion version;

    protected abstract FullHttpRequest newHandshakeRequest();

    protected abstract WebSocketFrameEncoder newWebSocketEncoder();

    protected abstract WebSocketFrameDecoder newWebsocketDecoder();

    protected abstract void verify(FullHttpResponse fullHttpResponse);

    protected WebSocketClientHandshaker(URI uri, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength) {
        this.uri = uri;
        this.version = version;
        this.expectedSubprotocol = subprotocol;
        this.customHeaders = customHeaders;
        this.maxFramePayloadLength = maxFramePayloadLength;
    }

    public URI uri() {
        return this.uri;
    }

    public WebSocketVersion version() {
        return this.version;
    }

    public int maxFramePayloadLength() {
        return this.maxFramePayloadLength;
    }

    public boolean isHandshakeComplete() {
        return this.handshakeComplete;
    }

    private void setHandshakeComplete() {
        this.handshakeComplete = true;
    }

    public String expectedSubprotocol() {
        return this.expectedSubprotocol;
    }

    public String actualSubprotocol() {
        return this.actualSubprotocol;
    }

    private void setActualSubprotocol(String actualSubprotocol) {
        this.actualSubprotocol = actualSubprotocol;
    }

    public ChannelFuture handshake(Channel channel) {
        if (channel != null) {
            return handshake(channel, channel.newPromise());
        }
        throw new NullPointerException("channel");
    }

    public final ChannelFuture handshake(Channel channel, final ChannelPromise promise) {
        FullHttpRequest request = newHandshakeRequest();
        if (((HttpResponseDecoder) channel.pipeline().get(HttpResponseDecoder.class)) == null && ((HttpClientCodec) channel.pipeline().get(HttpClientCodec.class)) == null) {
            promise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpResponseDecoder or HttpClientCodec"));
        } else {
            channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) {
                    if (future.isSuccess()) {
                        ChannelPipeline p = future.channel().pipeline();
                        ChannelHandlerContext ctx = p.context(HttpRequestEncoder.class);
                        if (ctx == null) {
                            ctx = p.context(HttpClientCodec.class);
                        }
                        if (ctx == null) {
                            promise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec"));
                            return;
                        }
                        p.addAfter(ctx.name(), "ws-encoder", WebSocketClientHandshaker.this.newWebSocketEncoder());
                        promise.setSuccess();
                        return;
                    }
                    promise.setFailure(future.cause());
                }
            });
        }
        return promise;
    }

    public final void finishHandshake(Channel channel, FullHttpResponse response) {
        verify(response);
        String receivedProtocol = response.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
        receivedProtocol = receivedProtocol != null ? receivedProtocol.trim() : null;
        String expectedProtocol = this.expectedSubprotocol != null ? this.expectedSubprotocol : "";
        boolean protocolValid = false;
        if (expectedProtocol.isEmpty() && receivedProtocol == null) {
            protocolValid = true;
            setActualSubprotocol(this.expectedSubprotocol);
        } else if (!expectedProtocol.isEmpty() && receivedProtocol != null && !receivedProtocol.isEmpty()) {
            for (String protocol : expectedProtocol.split(MiPushClient.ACCEPT_TIME_SEPARATOR)) {
                if (protocol.trim().equals(receivedProtocol)) {
                    protocolValid = true;
                    setActualSubprotocol(receivedProtocol);
                    break;
                }
            }
        }
        if (protocolValid) {
            setHandshakeComplete();
            final ChannelPipeline p = channel.pipeline();
            HttpContentDecompressor decompressor = (HttpContentDecompressor) p.get(HttpContentDecompressor.class);
            if (decompressor != null) {
                p.remove(decompressor);
            }
            HttpObjectAggregator aggregator = (HttpObjectAggregator) p.get(HttpObjectAggregator.class);
            if (aggregator != null) {
                p.remove(aggregator);
            }
            ChannelHandlerContext ctx = p.context(HttpResponseDecoder.class);
            if (ctx == null) {
                ctx = p.context(HttpClientCodec.class);
                if (ctx == null) {
                    throw new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec");
                }
                final HttpClientCodec codec = (HttpClientCodec) ctx.handler();
                codec.removeOutboundHandler();
                p.addAfter(ctx.name(), "ws-decoder", newWebsocketDecoder());
                channel.eventLoop().execute(new Runnable() {
                    public void run() {
                        p.remove(codec);
                    }
                });
                return;
            }
            if (p.get(HttpRequestEncoder.class) != null) {
                p.remove(HttpRequestEncoder.class);
            }
            final ChannelHandlerContext context = ctx;
            p.addAfter(context.name(), "ws-decoder", newWebsocketDecoder());
            channel.eventLoop().execute(new Runnable() {
                public void run() {
                    p.remove(context.handler());
                }
            });
            return;
        }
        throw new WebSocketHandshakeException(String.format("Invalid subprotocol. Actual: %s. Expected one of: %s", new Object[]{receivedProtocol, this.expectedSubprotocol}));
    }

    public final ChannelFuture processHandshake(Channel channel, HttpResponse response) {
        return processHandshake(channel, response, channel.newPromise());
    }

    public final ChannelFuture processHandshake(final Channel channel, HttpResponse response, final ChannelPromise promise) {
        if (response instanceof FullHttpResponse) {
            try {
                finishHandshake(channel, (FullHttpResponse) response);
                promise.setSuccess();
                return promise;
            } catch (Throwable cause) {
                promise.setFailure(cause);
                return promise;
            }
        }
        ChannelPipeline p = channel.pipeline();
        ChannelHandlerContext ctx = p.context(HttpResponseDecoder.class);
        if (ctx == null) {
            ctx = p.context(HttpClientCodec.class);
            if (ctx == null) {
                return promise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpResponseDecoder or HttpClientCodec"));
            }
        }
        String aggregatorName = "httpAggregator";
        p.addAfter(ctx.name(), aggregatorName, new HttpObjectAggregator(8192));
        p.addAfter(aggregatorName, "handshaker", new SimpleChannelInboundHandler<FullHttpResponse>() {
            protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
                ctx.pipeline().remove(this);
                try {
                    WebSocketClientHandshaker.this.finishHandshake(channel, msg);
                    promise.setSuccess();
                } catch (Throwable cause) {
                    promise.setFailure(cause);
                }
            }

            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                ctx.pipeline().remove(this);
                promise.setFailure(cause);
            }

            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                promise.tryFailure(WebSocketClientHandshaker.CLOSED_CHANNEL_EXCEPTION);
                ctx.fireChannelInactive();
            }
        });
        try {
            ctx.fireChannelRead(ReferenceCountUtil.retain(response));
            return promise;
        } catch (Throwable cause2) {
            promise.setFailure(cause2);
            return promise;
        }
    }

    public ChannelFuture close(Channel channel, CloseWebSocketFrame frame) {
        if (channel != null) {
            return close(channel, frame, channel.newPromise());
        }
        throw new NullPointerException("channel");
    }

    public ChannelFuture close(Channel channel, CloseWebSocketFrame frame, ChannelPromise promise) {
        if (channel != null) {
            return channel.writeAndFlush(frame, promise);
        }
        throw new NullPointerException("channel");
    }

    static String rawPath(URI wsURL) {
        String path = wsURL.getRawPath();
        String query = wsURL.getQuery();
        if (!(query == null || query.isEmpty())) {
            path = path + '?' + query;
        }
        return (path == null || path.isEmpty()) ? "/" : path;
    }

    static int websocketPort(URI wsURL) {
        int wsPort = wsURL.getPort();
        if (wsPort == -1) {
            return "wss".equals(wsURL.getScheme()) ? HttpScheme.HTTPS.port() : HttpScheme.HTTP.port();
        } else {
            return wsPort;
        }
    }

    static CharSequence websocketOriginValue(String host, int wsPort) {
        String originValue = (wsPort == HttpScheme.HTTPS.port() ? HttpScheme.HTTPS.name() : HttpScheme.HTTP.name()) + "://" + host;
        if (wsPort == HttpScheme.HTTP.port() || wsPort == HttpScheme.HTTPS.port()) {
            return originValue;
        }
        return originValue + ':' + wsPort;
    }
}
