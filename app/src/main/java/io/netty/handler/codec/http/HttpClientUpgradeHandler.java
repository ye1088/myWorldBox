package io.netty.handler.codec.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.util.AsciiString;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.StringUtil;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class HttpClientUpgradeHandler extends HttpObjectAggregator implements ChannelOutboundHandler {
    static final /* synthetic */ boolean $assertionsDisabled = (!HttpClientUpgradeHandler.class.desiredAssertionStatus());
    private final SourceCodec sourceCodec;
    private final UpgradeCodec upgradeCodec;
    private boolean upgradeRequested;

    public interface SourceCodec {
        void prepareUpgradeFrom(ChannelHandlerContext channelHandlerContext);

        void upgradeFrom(ChannelHandlerContext channelHandlerContext);
    }

    public interface UpgradeCodec {
        CharSequence protocol();

        Collection<CharSequence> setUpgradeHeaders(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest);

        void upgradeTo(ChannelHandlerContext channelHandlerContext, FullHttpResponse fullHttpResponse) throws Exception;
    }

    public enum UpgradeEvent {
        UPGRADE_ISSUED,
        UPGRADE_SUCCESSFUL,
        UPGRADE_REJECTED
    }

    public HttpClientUpgradeHandler(SourceCodec sourceCodec, UpgradeCodec upgradeCodec, int maxContentLength) {
        super(maxContentLength);
        if (sourceCodec == null) {
            throw new NullPointerException("sourceCodec");
        } else if (upgradeCodec == null) {
            throw new NullPointerException("upgradeCodec");
        } else {
            this.sourceCodec = sourceCodec;
            this.upgradeCodec = upgradeCodec;
        }
    }

    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.bind(localAddress, promise);
    }

    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.connect(remoteAddress, localAddress, promise);
    }

    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.disconnect(promise);
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.close(promise);
    }

    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.deregister(promise);
    }

    public void read(ChannelHandlerContext ctx) throws Exception {
        ctx.read();
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof HttpRequest)) {
            ctx.write(msg, promise);
        } else if (this.upgradeRequested) {
            promise.setFailure(new IllegalStateException("Attempting to write HTTP request with upgrade in progress"));
        } else {
            this.upgradeRequested = true;
            setUpgradeRequestHeaders(ctx, (HttpRequest) msg);
            ctx.write(msg, promise);
            ctx.fireUserEventTriggered(UpgradeEvent.UPGRADE_ISSUED);
        }
    }

    public void flush(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
        try {
            if (this.upgradeRequested) {
                FullHttpResponse response;
                if (msg instanceof FullHttpResponse) {
                    response = (FullHttpResponse) msg;
                    response.retain();
                    out.add(response);
                } else {
                    super.decode(ctx, msg, out);
                    if (!out.isEmpty()) {
                        if ($assertionsDisabled || out.size() == 1) {
                            response = (FullHttpResponse) out.get(0);
                        } else {
                            throw new AssertionError();
                        }
                    }
                    return;
                }
                if (HttpResponseStatus.SWITCHING_PROTOCOLS.equals(response.status())) {
                    CharSequence upgradeHeader = response.headers().get(HttpHeaderNames.UPGRADE);
                    if (upgradeHeader == null || AsciiString.contentEqualsIgnoreCase(this.upgradeCodec.protocol(), upgradeHeader)) {
                        this.sourceCodec.prepareUpgradeFrom(ctx);
                        this.upgradeCodec.upgradeTo(ctx, response);
                        ctx.fireUserEventTriggered(UpgradeEvent.UPGRADE_SUCCESSFUL);
                        this.sourceCodec.upgradeFrom(ctx);
                        response.release();
                        out.clear();
                        removeThisHandler(ctx);
                        return;
                    }
                    throw new IllegalStateException("Switching Protocols response with unexpected UPGRADE protocol: " + upgradeHeader);
                }
                ctx.fireUserEventTriggered(UpgradeEvent.UPGRADE_REJECTED);
                removeThisHandler(ctx);
                return;
            }
            throw new IllegalStateException("Read HTTP response without requesting protocol switch");
        } catch (Throwable t) {
            ReferenceCountUtil.release(null);
            ctx.fireExceptionCaught(t);
            removeThisHandler(ctx);
        }
    }

    private static void removeThisHandler(ChannelHandlerContext ctx) {
        ctx.pipeline().remove(ctx.name());
    }

    private void setUpgradeRequestHeaders(ChannelHandlerContext ctx, HttpRequest request) {
        request.headers().set(HttpHeaderNames.UPGRADE, this.upgradeCodec.protocol());
        Set<CharSequence> connectionParts = new LinkedHashSet(2);
        connectionParts.addAll(this.upgradeCodec.setUpgradeHeaders(ctx, request));
        StringBuilder builder = new StringBuilder();
        for (CharSequence part : connectionParts) {
            builder.append(part);
            builder.append(StringUtil.COMMA);
        }
        builder.append(HttpHeaderValues.UPGRADE);
        request.headers().set(HttpHeaderNames.CONNECTION, builder.toString());
    }
}
