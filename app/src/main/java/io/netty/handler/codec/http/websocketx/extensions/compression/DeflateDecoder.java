package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
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
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import java.util.List;

abstract class DeflateDecoder extends WebSocketExtensionDecoder {
    static final byte[] FRAME_TAIL = new byte[]{(byte) 0, (byte) 0, (byte) -1, (byte) -1};
    private EmbeddedChannel decoder;
    private final boolean noContext;

    protected abstract boolean appendFrameTail(WebSocketFrame webSocketFrame);

    protected abstract int newRsv(WebSocketFrame webSocketFrame);

    public DeflateDecoder(boolean noContext) {
        this.noContext = noContext;
    }

    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        if (this.decoder == null) {
            if ((msg instanceof TextWebSocketFrame) || (msg instanceof BinaryWebSocketFrame)) {
                this.decoder = new EmbeddedChannel(new ChannelHandler[]{ZlibCodecFactory.newZlibDecoder(ZlibWrapper.NONE)});
            } else {
                throw new CodecException("unexpected initial frame type: " + msg.getClass().getName());
            }
        }
        boolean readable = msg.content().isReadable();
        this.decoder.writeInbound(new Object[]{msg.content().retain()});
        if (appendFrameTail(msg)) {
            this.decoder.writeInbound(new Object[]{Unpooled.wrappedBuffer(FRAME_TAIL)});
        }
        CompositeByteBuf compositeUncompressedContent = ctx.alloc().compositeBuffer();
        while (true) {
            ByteBuf partUncompressedContent = (ByteBuf) this.decoder.readInbound();
            if (partUncompressedContent == null) {
                break;
            } else if (partUncompressedContent.isReadable()) {
                compositeUncompressedContent.addComponent(true, partUncompressedContent);
            } else {
                partUncompressedContent.release();
            }
        }
        if (!readable || compositeUncompressedContent.numComponents() > 0) {
            WebSocketFrame outMsg;
            if (msg.isFinalFragment() && this.noContext) {
                cleanup();
            }
            if (msg instanceof TextWebSocketFrame) {
                outMsg = new TextWebSocketFrame(msg.isFinalFragment(), newRsv(msg), compositeUncompressedContent);
            } else if (msg instanceof BinaryWebSocketFrame) {
                outMsg = new BinaryWebSocketFrame(msg.isFinalFragment(), newRsv(msg), compositeUncompressedContent);
            } else if (msg instanceof ContinuationWebSocketFrame) {
                outMsg = new ContinuationWebSocketFrame(msg.isFinalFragment(), newRsv(msg), compositeUncompressedContent);
            } else {
                throw new CodecException("unexpected frame type: " + msg.getClass().getName());
            }
            out.add(outMsg);
            return;
        }
        compositeUncompressedContent.release();
        throw new CodecException("cannot read uncompressed buffer");
    }

    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        cleanup();
        super.handlerRemoved(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        cleanup();
        super.channelInactive(ctx);
    }

    private void cleanup() {
        if (this.decoder != null) {
            if (this.decoder.finish()) {
                while (true) {
                    ByteBuf buf = (ByteBuf) this.decoder.readOutbound();
                    if (buf == null) {
                        break;
                    }
                    buf.release();
                }
            }
            this.decoder = null;
        }
    }
}
