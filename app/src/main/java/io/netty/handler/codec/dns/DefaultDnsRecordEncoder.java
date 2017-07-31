package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.util.internal.StringUtil;

public class DefaultDnsRecordEncoder implements DnsRecordEncoder {
    protected DefaultDnsRecordEncoder() {
    }

    public final void encodeQuestion(DnsQuestion question, ByteBuf out) throws Exception {
        encodeName(question.name(), out);
        out.writeShort(question.type().intValue());
        out.writeShort(question.dnsClass());
    }

    public void encodeRecord(DnsRecord record, ByteBuf out) throws Exception {
        if (record instanceof DnsQuestion) {
            encodeQuestion((DnsQuestion) record, out);
        } else if (record instanceof DnsPtrRecord) {
            encodePtrRecord((DnsPtrRecord) record, out);
        } else if (record instanceof DnsRawRecord) {
            encodeRawRecord((DnsRawRecord) record, out);
        } else {
            throw new UnsupportedMessageTypeException(StringUtil.simpleClassName(record));
        }
    }

    private void encodePtrRecord(DnsPtrRecord record, ByteBuf out) throws Exception {
        encodeName(record.name(), out);
        out.writeShort(record.type().intValue());
        out.writeShort(record.dnsClass());
        out.writeInt((int) record.timeToLive());
        encodeName(record.hostname(), out);
    }

    private void encodeRawRecord(DnsRawRecord record, ByteBuf out) throws Exception {
        encodeName(record.name(), out);
        out.writeShort(record.type().intValue());
        out.writeShort(record.dnsClass());
        out.writeInt((int) record.timeToLive());
        ByteBuf content = record.content();
        int contentLen = content.readableBytes();
        out.writeShort(contentLen);
        out.writeBytes(content, content.readerIndex(), contentLen);
    }

    protected void encodeName(String name, ByteBuf buf) throws Exception {
        if (".".equals(name)) {
            buf.writeByte(0);
            return;
        }
        for (CharSequence label : name.split("\\.")) {
            int labelLen = label.length();
            if (labelLen == 0) {
                break;
            }
            buf.writeByte(labelLen);
            ByteBufUtil.writeAscii(buf, label);
        }
        buf.writeByte(0);
    }
}
