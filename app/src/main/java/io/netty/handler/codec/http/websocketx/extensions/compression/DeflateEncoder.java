package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import java.util.List;

abstract class DeflateEncoder extends WebSocketExtensionEncoder {
    private final int compressionLevel;
    private EmbeddedChannel encoder;
    private final boolean noContext;
    private final int windowSize;

    protected abstract boolean removeFrameTail(WebSocketFrame webSocketFrame);

    protected abstract int rsv(WebSocketFrame webSocketFrame);

    public DeflateEncoder(int compressionLevel, int windowSize, boolean noContext) {
        this.compressionLevel = compressionLevel;
        this.windowSize = windowSize;
        this.noContext = noContext;
    }

    protected void encode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        if (this.encoder == null) {
            this.encoder = new EmbeddedChannel(new ChannelHandler[]{ZlibCodecFactory.newZlibEncoder(ZlibWrapper.NONE, this.compressionLevel, this.windowSize, 8)});
        }
        this.encoder.writeOutbound(new Object[]{msg.content().retain()});
        ByteBuf fullCompressedContent = ctx.alloc().compositeBuffer();
        while (true) {
            ByteBuf partCompressedContent = (ByteBuf) this.encoder.readOutbound();
            if (partCompressedContent == null) {
                break;
            } else if (partCompressedContent.isReadable()) {
                fullCompressedContent.addComponent(true, partCompressedContent);
            } else {
                partCompressedContent.release();
            }
        }
        if (fullCompressedContent.numComponents() <= 0) {
            fullCompressedContent.release();
            throw new CodecException("cannot read compressed buffer");
        }
        ByteBuf compressedContent;
        WebSocketFrame outMsg;
        if (msg.isFinalFragment() && this.noContext) {
            cleanup();
        }
        if (removeFrameTail(msg)) {
            compressedContent = fullCompressedContent.slice(0, fullCompressedContent.readableBytes() - PerMessageDeflateDecoder.FRAME_TAIL.length);
        } else {
            compressedContent = fullCompressedContent;
        }
        if (msg instanceof TextWebSocketFrame) {
            outMsg = new TextWebSocketFrame(msg.isFinalFragment(), rsv(msg), compressedContent);
        } else if (msg instanceof BinaryWebSocketFrame) {
            outMsg = new BinaryWebSocketFrame(msg.isFinalFragment(), rsv(msg), compressedContent);
        } else if (msg instanceof ContinuationWebSocketFrame) {
            outMsg = new ContinuationWebSocketFrame(msg.isFinalFragment(), rsv(msg), compressedContent);
        } else {
            throw new CodecException("unexpected frame type: " + msg.getClass().getName());
        }
        out.add(outMsg);
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        cleanup();
        super.handlerRemoved(ctx);
    }

    private void cleanup() {
        if (this.encoder != null) {
            if (this.encoder.finish()) {
                while (true) {
                    ByteBuf buf = (ByteBuf) this.encoder.readOutbound();
                    if (buf == null) {
                        break;
                    }
                    buf.release();
                }
            }
            this.encoder = null;
        }
    }
}
