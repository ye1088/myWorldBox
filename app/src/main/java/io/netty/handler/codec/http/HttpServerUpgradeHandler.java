package io.netty.handler.codec.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AsciiString;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HttpServerUpgradeHandler extends HttpObjectAggregator {
    static final /* synthetic */ boolean $assertionsDisabled = (!HttpServerUpgradeHandler.class.desiredAssertionStatus());
    private boolean handlingUpgrade;
    private final SourceCodec sourceCodec;
    private final UpgradeCodecFactory upgradeCodecFactory;

    public interface SourceCodec {
        void upgradeFrom(ChannelHandlerContext channelHandlerContext);
    }

    public interface UpgradeCodec {
        boolean prepareUpgradeResponse(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest, HttpHeaders httpHeaders);

        Collection<CharSequence> requiredUpgradeHeaders();

        void upgradeTo(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest);
    }

    public interface UpgradeCodecFactory {
        UpgradeCodec newUpgradeCodec(CharSequence charSequence);
    }

    public static final class UpgradeEvent implements ReferenceCounted {
        private final CharSequence protocol;
        private final FullHttpRequest upgradeRequest;

        UpgradeEvent(CharSequence protocol, FullHttpRequest upgradeRequest) {
            this.protocol = protocol;
            this.upgradeRequest = upgradeRequest;
        }

        public CharSequence protocol() {
            return this.protocol;
        }

        public FullHttpRequest upgradeRequest() {
            return this.upgradeRequest;
        }

        public int refCnt() {
            return this.upgradeRequest.refCnt();
        }

        public UpgradeEvent retain() {
            this.upgradeRequest.retain();
            return this;
        }

        public UpgradeEvent retain(int increment) {
            this.upgradeRequest.retain(increment);
            return this;
        }

        public UpgradeEvent touch() {
            this.upgradeRequest.touch();
            return this;
        }

        public UpgradeEvent touch(Object hint) {
            this.upgradeRequest.touch(hint);
            return this;
        }

        public boolean release() {
            return this.upgradeRequest.release();
        }

        public boolean release(int decrement) {
            return this.upgradeRequest.release(decrement);
        }

        public String toString() {
            return "UpgradeEvent [protocol=" + this.protocol + ", upgradeRequest=" + this.upgradeRequest + ']';
        }
    }

    public HttpServerUpgradeHandler(SourceCodec sourceCodec, UpgradeCodecFactory upgradeCodecFactory) {
        this(sourceCodec, upgradeCodecFactory, 0);
    }

    public HttpServerUpgradeHandler(SourceCodec sourceCodec, UpgradeCodecFactory upgradeCodecFactory, int maxContentLength) {
        super(maxContentLength);
        this.sourceCodec = (SourceCodec) ObjectUtil.checkNotNull(sourceCodec, "sourceCodec");
        this.upgradeCodecFactory = (UpgradeCodecFactory) ObjectUtil.checkNotNull(upgradeCodecFactory, "upgradeCodecFactory");
    }

    protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
        this.handlingUpgrade |= isUpgradeRequest(msg);
        if (this.handlingUpgrade) {
            FullHttpRequest fullRequest;
            if (msg instanceof FullHttpRequest) {
                fullRequest = (FullHttpRequest) msg;
                ReferenceCountUtil.retain(msg);
                out.add(msg);
            } else {
                super.decode(ctx, msg, out);
                if (!out.isEmpty()) {
                    if ($assertionsDisabled || out.size() == 1) {
                        this.handlingUpgrade = false;
                        fullRequest = (FullHttpRequest) out.get(0);
                    } else {
                        throw new AssertionError();
                    }
                }
                return;
            }
            if (upgrade(ctx, fullRequest)) {
                out.clear();
                return;
            }
            return;
        }
        ReferenceCountUtil.retain(msg);
        out.add(msg);
    }

    private static boolean isUpgradeRequest(HttpObject msg) {
        return (msg instanceof HttpRequest) && ((HttpRequest) msg).headers().get(HttpHeaderNames.UPGRADE) != null;
    }

    private boolean upgrade(ChannelHandlerContext ctx, FullHttpRequest request) {
        List<CharSequence> requestedProtocols = splitHeader(request.headers().get(HttpHeaderNames.UPGRADE));
        int numRequestedProtocols = requestedProtocols.size();
        UpgradeCodec upgradeCodec = null;
        CharSequence upgradeProtocol = null;
        for (int i = 0; i < numRequestedProtocols; i++) {
            CharSequence p = (CharSequence) requestedProtocols.get(i);
            UpgradeCodec c = this.upgradeCodecFactory.newUpgradeCodec(p);
            if (c != null) {
                upgradeProtocol = p;
                upgradeCodec = c;
                break;
            }
        }
        if (upgradeCodec == null) {
            return false;
        }
        CharSequence connectionHeader = request.headers().get(HttpHeaderNames.CONNECTION);
        if (connectionHeader == null) {
            return false;
        }
        Collection<CharSequence> requiredHeaders = upgradeCodec.requiredUpgradeHeaders();
        List<CharSequence> values = splitHeader(connectionHeader);
        if (!AsciiString.containsContentEqualsIgnoreCase(values, HttpHeaderNames.UPGRADE) || !AsciiString.containsAllContentEqualsIgnoreCase(values, requiredHeaders)) {
            return false;
        }
        for (CharSequence requiredHeader : requiredHeaders) {
            if (!request.headers().contains(requiredHeader)) {
                return false;
            }
        }
        FullHttpResponse upgradeResponse = createUpgradeResponse(upgradeProtocol);
        if (!upgradeCodec.prepareUpgradeResponse(ctx, request, upgradeResponse.headers())) {
            return false;
        }
        final UpgradeEvent event = new UpgradeEvent(upgradeProtocol, request);
        final UpgradeCodec finalUpgradeCodec = upgradeCodec;
        final ChannelHandlerContext channelHandlerContext = ctx;
        final FullHttpRequest fullHttpRequest = request;
        ctx.writeAndFlush(upgradeResponse).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                try {
                    if (future.isSuccess()) {
                        HttpServerUpgradeHandler.this.sourceCodec.upgradeFrom(channelHandlerContext);
                        finalUpgradeCodec.upgradeTo(channelHandlerContext, fullHttpRequest);
                        channelHandlerContext.fireUserEventTriggered(event.retain());
                        channelHandlerContext.pipeline().remove(HttpServerUpgradeHandler.this);
                    } else {
                        future.channel().close();
                    }
                    event.release();
                } catch (Throwable th) {
                    event.release();
                }
            }
        });
        return true;
    }

    private static FullHttpResponse createUpgradeResponse(CharSequence upgradeProtocol) {
        DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS, Unpooled.EMPTY_BUFFER, false);
        res.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE);
        res.headers().add(HttpHeaderNames.UPGRADE, upgradeProtocol);
        res.headers().add(HttpHeaderNames.CONTENT_LENGTH, HttpHeaderValues.ZERO);
        return res;
    }

    private static List<CharSequence> splitHeader(CharSequence header) {
        StringBuilder builder = new StringBuilder(header.length());
        List<CharSequence> protocols = new ArrayList(4);
        for (int i = 0; i < header.length(); i++) {
            char c = header.charAt(i);
            if (!Character.isWhitespace(c)) {
                if (c == StringUtil.COMMA) {
                    protocols.add(builder.toString());
                    builder.setLength(0);
                } else {
                    builder.append(c);
                }
            }
        }
        if (builder.length() > 0) {
            protocols.add(builder.toString());
        }
        return protocols;
    }
}
