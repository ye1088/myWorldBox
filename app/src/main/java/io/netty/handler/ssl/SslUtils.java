package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.base64.Base64Dialect;
import org.bytedeco.javacpp.avutil;

final class SslUtils {
    public static final int SSL_CONTENT_TYPE_ALERT = 21;
    public static final int SSL_CONTENT_TYPE_APPLICATION_DATA = 23;
    public static final int SSL_CONTENT_TYPE_CHANGE_CIPHER_SPEC = 20;
    public static final int SSL_CONTENT_TYPE_HANDSHAKE = 22;
    public static final int SSL_RECORD_HEADER_LENGTH = 5;

    static int getEncryptedPacketLength(ByteBuf buffer, int offset) {
        boolean tls;
        int packetLength = 0;
        switch (buffer.getUnsignedByte(offset)) {
            case (short) 20:
            case (short) 21:
            case (short) 22:
            case (short) 23:
                tls = true;
                break;
            default:
                tls = false;
                break;
        }
        if (tls) {
            if (buffer.getUnsignedByte(offset + 1) == 3) {
                packetLength = buffer.getUnsignedShort(offset + 3) + 5;
                if (packetLength <= 5) {
                    tls = false;
                }
            } else {
                tls = false;
            }
        }
        if (!tls) {
            int headerLength;
            if ((buffer.getUnsignedByte(offset) & 128) != 0) {
                headerLength = 2;
            } else {
                headerLength = 3;
            }
            int majorVersion = buffer.getUnsignedByte((offset + headerLength) + 1);
            if (majorVersion != 2 && majorVersion != 3) {
                return -1;
            }
            if (headerLength == 2) {
                packetLength = (buffer.getShort(offset) & avutil.FF_LAMBDA_MAX) + 2;
            } else {
                packetLength = (buffer.getShort(offset) & 16383) + 3;
            }
            if (packetLength <= headerLength) {
                return -1;
            }
        }
        return packetLength;
    }

    static void notifyHandshakeFailure(ChannelHandlerContext ctx, Throwable cause) {
        ctx.flush();
        ctx.fireUserEventTriggered(new SslHandshakeCompletionEvent(cause));
        ctx.close();
    }

    static void zeroout(ByteBuf buffer) {
        if (!buffer.isReadOnly()) {
            buffer.setZero(0, buffer.capacity());
        }
    }

    static void zerooutAndRelease(ByteBuf buffer) {
        zeroout(buffer);
        buffer.release();
    }

    static ByteBuf toBase64(ByteBufAllocator allocator, ByteBuf src) {
        ByteBuf dst = Base64.encode(src, src.readerIndex(), src.readableBytes(), true, Base64Dialect.STANDARD, allocator);
        src.readerIndex(src.writerIndex());
        return dst;
    }

    private SslUtils() {
    }
}
