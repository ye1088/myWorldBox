package io.netty.handler.codec.http;

import com.xiaomi.mipush.sdk.MiPushClient;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpContentEncoder.Result;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

public class HttpContentCompressor extends HttpContentEncoder {
    private final int compressionLevel;
    private ChannelHandlerContext ctx;
    private final int memLevel;
    private final int windowBits;

    public HttpContentCompressor() {
        this(6);
    }

    public HttpContentCompressor(int compressionLevel) {
        this(compressionLevel, 15, 8);
    }

    public HttpContentCompressor(int compressionLevel, int windowBits, int memLevel) {
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        } else if (windowBits < 9 || windowBits > 15) {
            throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
        } else if (memLevel < 1 || memLevel > 9) {
            throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
        } else {
            this.compressionLevel = compressionLevel;
            this.windowBits = windowBits;
            this.memLevel = memLevel;
        }
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    protected Result beginEncode(HttpResponse headers, String acceptEncoding) throws Exception {
        String contentEncoding = headers.headers().get(HttpHeaderNames.CONTENT_ENCODING);
        if (contentEncoding != null && !HttpHeaderValues.IDENTITY.contentEqualsIgnoreCase(contentEncoding)) {
            return null;
        }
        ZlibWrapper wrapper = determineWrapper(acceptEncoding);
        if (wrapper == null) {
            return null;
        }
        String targetContentEncoding;
        switch (wrapper) {
            case GZIP:
                targetContentEncoding = "gzip";
                break;
            case ZLIB:
                targetContentEncoding = "deflate";
                break;
            default:
                throw new Error();
        }
        return new Result(targetContentEncoding, new EmbeddedChannel(this.ctx.channel().id(), this.ctx.channel().metadata().hasDisconnect(), this.ctx.channel().config(), new ChannelHandler[]{ZlibCodecFactory.newZlibEncoder(wrapper, this.compressionLevel, this.windowBits, this.memLevel)}));
    }

    protected ZlibWrapper determineWrapper(String acceptEncoding) {
        float starQ = -1.0f;
        float gzipQ = -1.0f;
        float deflateQ = -1.0f;
        for (String encoding : acceptEncoding.split(MiPushClient.ACCEPT_TIME_SEPARATOR)) {
            float q = 1.0f;
            int equalsPos = encoding.indexOf(61);
            if (equalsPos != -1) {
                try {
                    q = Float.parseFloat(encoding.substring(equalsPos + 1));
                } catch (NumberFormatException e) {
                    q = 0.0f;
                }
            }
            if (encoding.contains(WebSocketServerHandshaker.SUB_PROTOCOL_WILDCARD)) {
                starQ = q;
            } else if (encoding.contains("gzip") && q > gzipQ) {
                gzipQ = q;
            } else if (encoding.contains("deflate") && q > deflateQ) {
                deflateQ = q;
            }
        }
        if (gzipQ <= 0.0f && deflateQ <= 0.0f) {
            if (starQ > 0.0f) {
                if (gzipQ == -1.0f) {
                    return ZlibWrapper.GZIP;
                }
                if (deflateQ == -1.0f) {
                    return ZlibWrapper.ZLIB;
                }
            }
            return null;
        } else if (gzipQ >= deflateQ) {
            return ZlibWrapper.GZIP;
        } else {
            return ZlibWrapper.ZLIB;
        }
    }
}
