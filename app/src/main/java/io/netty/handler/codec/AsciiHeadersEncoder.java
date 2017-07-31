package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import java.util.Map.Entry;

public final class AsciiHeadersEncoder {
    private final ByteBuf buf;
    private final NewlineType newlineType;
    private final SeparatorType separatorType;

    public enum NewlineType {
        LF,
        CRLF
    }

    public enum SeparatorType {
        COLON,
        COLON_SPACE
    }

    public AsciiHeadersEncoder(ByteBuf buf) {
        this(buf, SeparatorType.COLON_SPACE, NewlineType.CRLF);
    }

    public AsciiHeadersEncoder(ByteBuf buf, SeparatorType separatorType, NewlineType newlineType) {
        if (buf == null) {
            throw new NullPointerException("buf");
        } else if (separatorType == null) {
            throw new NullPointerException("separatorType");
        } else if (newlineType == null) {
            throw new NullPointerException("newlineType");
        } else {
            this.buf = buf;
            this.separatorType = separatorType;
            this.newlineType = newlineType;
        }
    }

    public void encode(Entry<CharSequence, CharSequence> entry) {
        int offset;
        CharSequence name = (CharSequence) entry.getKey();
        CharSequence value = (CharSequence) entry.getValue();
        ByteBuf buf = this.buf;
        int nameLen = name.length();
        int valueLen = value.length();
        int entryLen = (nameLen + valueLen) + 4;
        int offset2 = buf.writerIndex();
        buf.ensureWritable(entryLen);
        writeAscii(buf, offset2, name, nameLen);
        offset2 += nameLen;
        switch (this.separatorType) {
            case COLON:
                offset = offset2 + 1;
                buf.setByte(offset2, 58);
                offset2 = offset;
                break;
            case COLON_SPACE:
                offset = offset2 + 1;
                buf.setByte(offset2, 58);
                offset2 = offset + 1;
                buf.setByte(offset, 32);
                break;
            default:
                throw new Error();
        }
        writeAscii(buf, offset2, value, valueLen);
        offset2 += valueLen;
        switch (this.newlineType) {
            case LF:
                offset = offset2 + 1;
                buf.setByte(offset2, 10);
                offset2 = offset;
                break;
            case CRLF:
                offset = offset2 + 1;
                buf.setByte(offset2, 13);
                offset2 = offset + 1;
                buf.setByte(offset, 10);
                break;
            default:
                throw new Error();
        }
        buf.writerIndex(offset2);
    }

    private static void writeAscii(ByteBuf buf, int offset, CharSequence value, int valueLen) {
        if (value instanceof AsciiString) {
            writeAsciiString(buf, offset, (AsciiString) value, valueLen);
        } else {
            writeCharSequence(buf, offset, value, valueLen);
        }
    }

    private static void writeAsciiString(ByteBuf buf, int offset, AsciiString value, int valueLen) {
        ByteBufUtil.copy(value, 0, buf, offset, valueLen);
    }

    private static void writeCharSequence(ByteBuf buf, int offset, CharSequence value, int valueLen) {
        int i = 0;
        int offset2 = offset;
        while (i < valueLen) {
            offset = offset2 + 1;
            buf.setByte(offset2, c2b(value.charAt(i)));
            i++;
            offset2 = offset;
        }
    }

    private static int c2b(char ch) {
        return ch < 'Ā' ? (byte) ch : 63;
    }
}
