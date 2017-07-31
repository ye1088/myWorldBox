package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.base64.Base64Dialect;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientUpgradeHandler.UpgradeCodec;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.collection.CharObjectMap.PrimitiveEntry;
import io.netty.util.internal.ObjectUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Http2ClientUpgradeCodec implements UpgradeCodec {
    private static final List<CharSequence> UPGRADE_HEADERS = Collections.singletonList(Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER);
    private final Http2ConnectionHandler connectionHandler;
    private final String handlerName;

    public Http2ClientUpgradeCodec(Http2ConnectionHandler connectionHandler) {
        this(null, connectionHandler);
    }

    public Http2ClientUpgradeCodec(String handlerName, Http2ConnectionHandler connectionHandler) {
        this.handlerName = handlerName;
        this.connectionHandler = (Http2ConnectionHandler) ObjectUtil.checkNotNull(connectionHandler, "connectionHandler");
    }

    public CharSequence protocol() {
        return Http2CodecUtil.HTTP_UPGRADE_PROTOCOL_NAME;
    }

    public Collection<CharSequence> setUpgradeHeaders(ChannelHandlerContext ctx, HttpRequest upgradeRequest) {
        upgradeRequest.headers().set(Http2CodecUtil.HTTP_UPGRADE_SETTINGS_HEADER, getSettingsHeaderValue(ctx));
        return UPGRADE_HEADERS;
    }

    public void upgradeTo(ChannelHandlerContext ctx, FullHttpResponse upgradeResponse) throws Exception {
        this.connectionHandler.onHttpClientUpgrade();
        ctx.pipeline().addAfter(ctx.name(), this.handlerName, this.connectionHandler);
    }

    private CharSequence getSettingsHeaderValue(ChannelHandlerContext ctx) {
        ByteBuf buf = null;
        ByteBuf encodedBuf = null;
        try {
            Http2Settings settings = this.connectionHandler.decoder().localSettings();
            buf = ctx.alloc().buffer(settings.size() * 6);
            for (PrimitiveEntry<Long> entry : settings.entries()) {
                Http2CodecUtil.writeUnsignedShort(entry.key(), buf);
                Http2CodecUtil.writeUnsignedInt(((Long) entry.value()).longValue(), buf);
            }
            encodedBuf = Base64.encode(buf, Base64Dialect.URL_SAFE);
            CharSequence byteBuf = encodedBuf.toString(CharsetUtil.UTF_8);
            return byteBuf;
        } finally {
            ReferenceCountUtil.release(buf);
            ReferenceCountUtil.release(encodedBuf);
        }
    }
}
