package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class WebSocket08FrameEncoder extends MessageToMessageEncoder<WebSocketFrame> implements WebSocketFrameEncoder {
    private static final int GATHERING_WRITE_THRESHOLD = 1024;
    private static final byte OPCODE_BINARY = (byte) 2;
    private static final byte OPCODE_CLOSE = (byte) 8;
    private static final byte OPCODE_CONT = (byte) 0;
    private static final byte OPCODE_PING = (byte) 9;
    private static final byte OPCODE_PONG = (byte) 10;
    private static final byte OPCODE_TEXT = (byte) 1;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocket08FrameEncoder.class);
    private final boolean maskPayload;

    public WebSocket08FrameEncoder(boolean maskPayload) {
        this.maskPayload = maskPayload;
    }

    protected void encode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        byte opcode;
        ByteBuf data = msg.content();
        if (msg instanceof TextWebSocketFrame) {
            opcode = (byte) 1;
        } else if (msg instanceof PingWebSocketFrame) {
            opcode = (byte) 9;
        } else if (msg instanceof PongWebSocketFrame) {
            opcode = (byte) 10;
        } else if (msg instanceof CloseWebSocketFrame) {
            opcode = (byte) 8;
        } else if (msg instanceof BinaryWebSocketFrame) {
            opcode = (byte) 2;
        } else if (msg instanceof ContinuationWebSocketFrame) {
            opcode = (byte) 0;
        } else {
            throw new UnsupportedOperationException("Cannot encode frame of type: " + msg.getClass().getName());
        }
        int length = data.readableBytes();
        if (logger.isDebugEnabled()) {
            logger.debug("Encoding WebSocket Frame opCode=" + opcode + " length=" + length);
        }
        int b0 = 0;
        if (msg.isFinalFragment()) {
            b0 = 0 | 128;
        }
        b0 = (b0 | ((msg.rsv() % 8) << 4)) | (opcode % 128);
        if (opcode != (byte) 9 || length <= 125) {
            boolean release = true;
            ByteBuf buf = null;
            try {
                int maskLength = this.maskPayload ? 4 : 0;
                int size;
                if (length <= 125) {
                    int i;
                    size = maskLength + 2;
                    if (this.maskPayload || length <= 1024) {
                        size += length;
                    }
                    buf = ctx.alloc().buffer(size);
                    buf.writeByte(b0);
                    if (this.maskPayload) {
                        i = ((byte) length) | 128;
                    } else {
                        byte b = (byte) length;
                    }
                    buf.writeByte((byte) i);
                } else if (length <= 65535) {
                    size = maskLength + 4;
                    if (this.maskPayload || length <= 1024) {
                        size += length;
                    }
                    buf = ctx.alloc().buffer(size);
                    buf.writeByte(b0);
                    buf.writeByte(this.maskPayload ? 254 : 126);
                    buf.writeByte((length >>> 8) & 255);
                    buf.writeByte(length & 255);
                } else {
                    size = maskLength + 10;
                    if (this.maskPayload || length <= 1024) {
                        size += length;
                    }
                    buf = ctx.alloc().buffer(size);
                    buf.writeByte(b0);
                    buf.writeByte(this.maskPayload ? 255 : 127);
                    buf.writeLong((long) length);
                }
                if (this.maskPayload) {
                    byte[] mask = ByteBuffer.allocate(4).putInt((int) (Math.random() * 2.147483647E9d)).array();
                    buf.writeBytes(mask);
                    ByteOrder srcOrder = data.order();
                    ByteOrder dstOrder = buf.order();
                    int readerIndex = data.readerIndex();
                    int end = data.writerIndex();
                    if (srcOrder == dstOrder) {
                        int intMask = ((((mask[0] & 255) << 24) | ((mask[1] & 255) << 16)) | ((mask[2] & 255) << 8)) | (mask[3] & 255);
                        if (srcOrder == ByteOrder.LITTLE_ENDIAN) {
                            intMask = Integer.reverseBytes(intMask);
                        }
                        while (readerIndex + 3 < end) {
                            buf.writeInt(data.getInt(readerIndex) ^ intMask);
                            readerIndex += 4;
                        }
                    }
                    int counter = 0;
                    while (readerIndex < end) {
                        int counter2 = counter + 1;
                        buf.writeByte(mask[counter % 4] ^ data.getByte(readerIndex));
                        readerIndex++;
                        counter = counter2;
                    }
                    out.add(buf);
                } else {
                    if (buf.writableBytes() >= data.readableBytes()) {
                        buf.writeBytes(data);
                        out.add(buf);
                    } else {
                        out.add(buf);
                        out.add(data.retain());
                    }
                }
                release = false;
            } finally {
                if (release && buf != null) {
                    buf.release();
                }
            }
        } else {
            throw new TooLongFrameException("invalid payload for PING (payload length must be <= 125, was " + length);
        }
    }
}
