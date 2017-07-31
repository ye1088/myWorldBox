package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.ObjectUtil;
import java.net.InetSocketAddress;
import java.util.List;

@Sharable
public class DatagramDnsResponseEncoder extends MessageToMessageEncoder<AddressedEnvelope<DnsResponse, InetSocketAddress>> {
    private final DnsRecordEncoder recordEncoder;

    public DatagramDnsResponseEncoder() {
        this(DnsRecordEncoder.DEFAULT);
    }

    public DatagramDnsResponseEncoder(DnsRecordEncoder recordEncoder) {
        this.recordEncoder = (DnsRecordEncoder) ObjectUtil.checkNotNull(recordEncoder, "recordEncoder");
    }

    protected void encode(ChannelHandlerContext ctx, AddressedEnvelope<DnsResponse, InetSocketAddress> in, List<Object> out) throws Exception {
        InetSocketAddress recipient = (InetSocketAddress) in.recipient();
        DnsResponse response = (DnsResponse) in.content();
        ByteBuf buf = allocateBuffer(ctx, in);
        boolean success = false;
        try {
            encodeHeader(response, buf);
            encodeQuestions(response, buf);
            encodeRecords(response, DnsSection.ANSWER, buf);
            encodeRecords(response, DnsSection.AUTHORITY, buf);
            encodeRecords(response, DnsSection.ADDITIONAL, buf);
            success = true;
            out.add(new DatagramPacket(buf, recipient, null));
        } finally {
            if (!success) {
                buf.release();
            }
        }
    }

    protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope) throws Exception {
        return ctx.alloc().ioBuffer(1024);
    }

    private static void encodeHeader(DnsResponse response, ByteBuf buf) {
        buf.writeShort(response.id());
        int flags = 32768 | ((response.opCode().byteValue() & 255) << 11);
        if (response.isAuthoritativeAnswer()) {
            flags |= 1024;
        }
        if (response.isTruncated()) {
            flags |= 512;
        }
        if (response.isRecursionDesired()) {
            flags |= 256;
        }
        if (response.isRecursionAvailable()) {
            flags |= 128;
        }
        buf.writeShort((flags | (response.z() << 4)) | response.code().intValue());
        buf.writeShort(response.count(DnsSection.QUESTION));
        buf.writeShort(response.count(DnsSection.ANSWER));
        buf.writeShort(response.count(DnsSection.AUTHORITY));
        buf.writeShort(response.count(DnsSection.ADDITIONAL));
    }

    private void encodeQuestions(DnsResponse response, ByteBuf buf) throws Exception {
        int count = response.count(DnsSection.QUESTION);
        for (int i = 0; i < count; i++) {
            this.recordEncoder.encodeQuestion((DnsQuestion) response.recordAt(DnsSection.QUESTION, i), buf);
        }
    }

    private void encodeRecords(DnsResponse response, DnsSection section, ByteBuf buf) throws Exception {
        int count = response.count(section);
        for (int i = 0; i < count; i++) {
            this.recordEncoder.encodeRecord(response.recordAt(section, i), buf);
        }
    }
}
