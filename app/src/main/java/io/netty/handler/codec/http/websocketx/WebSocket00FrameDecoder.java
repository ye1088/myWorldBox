package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;
import java.util.List;

public class WebSocket00FrameDecoder extends ReplayingDecoder<Void> implements WebSocketFrameDecoder {
    static final int DEFAULT_MAX_FRAME_SIZE = 16384;
    private final long maxFrameSize;
    private boolean receivedClosingHandshake;

    public WebSocket00FrameDecoder() {
        this(16384);
    }

    public WebSocket00FrameDecoder(int maxFrameSize) {
        this.maxFrameSize = (long) maxFrameSize;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (this.receivedClosingHandshake) {
            in.skipBytes(actualReadableBytes());
            return;
        }
        WebSocketFrame frame;
        byte type = in.readByte();
        if ((type & 128) == 128) {
            frame = decodeBinaryFrame(ctx, type, in);
        } else {
            frame = decodeTextFrame(ctx, in);
        }
        if (frame != null) {
            out.add(frame);
        }
    }

    private WebSocketFrame decodeBinaryFrame(ChannelHandlerContext ctx, byte type, ByteBuf buffer) {
        long frameSize = 0;
        int lengthFieldSize = 0;
        byte b;
        do {
            b = buffer.readByte();
            frameSize = (frameSize << 7) | ((long) (b & 127));
            if (frameSize > this.maxFrameSize) {
                throw new TooLongFrameException();
            }
            lengthFieldSize++;
            if (lengthFieldSize > 8) {
                throw new TooLongFrameException();
            }
        } while ((b & 128) == 128);
        if (type != (byte) -1 || frameSize != 0) {
            return new BinaryWebSocketFrame(ByteBufUtil.readBytes(ctx.alloc(), buffer, (int) frameSize));
        }
        this.receivedClosingHandshake = true;
        return new CloseWebSocketFrame();
    }

    private WebSocketFrame decodeTextFrame(ChannelHandlerContext ctx, ByteBuf buffer) {
        int ridx = buffer.readerIndex();
        int rbytes = actualReadableBytes();
        int delimPos = buffer.indexOf(ridx, ridx + rbytes, (byte) -1);
        if (delimPos != -1) {
            int frameSize = delimPos - ridx;
            if (((long) frameSize) > this.maxFrameSize) {
                throw new TooLongFrameException();
            }
            ByteBuf binaryData = ByteBufUtil.readBytes(ctx.alloc(), buffer, frameSize);
            buffer.skipBytes(1);
            if (binaryData.indexOf(binaryData.readerIndex(), binaryData.writerIndex(), (byte) -1) < 0) {
                return new TextWebSocketFrame(binaryData);
            }
            binaryData.release();
            throw new IllegalArgumentException("a_isRightVersion text frame should not contain 0xFF.");
        } else if (((long) rbytes) <= this.maxFrameSize) {
            return null;
        } else {
            throw new TooLongFrameException();
        }
    }
}
