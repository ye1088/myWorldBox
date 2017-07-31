package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;

final class HttpHeadersEncoder {
    private HttpHeadersEncoder() {
    }

    public static void encoderHeader(CharSequence name, CharSequence value, ByteBuf buf) throws Exception {
        int nameLen = name.length();
        int valueLen = value.length();
        buf.ensureWritable((nameLen + valueLen) + 4);
        int offset = buf.writerIndex();
        writeAscii(buf, offset, name, nameLen);
        offset += nameLen;
        int i = offset + 1;
        buf.setByte(offset, 58);
        offset = i + 1;
        buf.setByte(i, 32);
        writeAscii(buf, offset, value, valueLen);
        offset += valueLen;
        i = offset + 1;
        buf.setByte(offset, 13);
        offset = i + 1;
        buf.setByte(i, 10);
        buf.writerIndex(offset);
    }

    private static void writeAscii(ByteBuf buf, int offset, CharSequence value, int valueLen) {
        if (value instanceof AsciiString) {
            ByteBufUtil.copy((AsciiString) value, 0, buf, offset, valueLen);
        } else {
            writeCharSequence(buf, offset, value, valueLen);
        }
    }

    private static void writeCharSequence(ByteBuf buf, int offset, CharSequence value, int valueLen) {
        int i = 0;
        int offset2 = offset;
        while (i < valueLen) {
            offset = offset2 + 1;
            buf.setByte(offset2, AsciiString.c2b(value.charAt(i)));
            i++;
            offset2 = offset;
        }
    }
}
