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
public class DatagramDnsQueryEncoder extends MessageToMessageEncoder<AddressedEnvelope<DnsQuery, InetSocketAddress>> {
    private final DnsRecordEncoder recordEncoder;

    public DatagramDnsQueryEncoder() {
        this(DnsRecordEncoder.DEFAULT);
    }

    public DatagramDnsQueryEncoder(DnsRecordEncoder recordEncoder) {
        this.recordEncoder = (DnsRecordEncoder) ObjectUtil.checkNotNull(recordEncoder, "recordEncoder");
    }

    protected void encode(ChannelHandlerContext ctx, AddressedEnvelope<DnsQuery, InetSocketAddress> in, List<Object> out) throws Exception {
        InetSocketAddress recipient = (InetSocketAddress) in.recipient();
        DnsQuery query = (DnsQuery) in.content();
        ByteBuf buf = allocateBuffer(ctx, in);
        boolean success = false;
        try {
            encodeHeader(query, buf);
            encodeQuestions(query, buf);
            encodeRecords(query, DnsSection.ADDITIONAL, buf);
            success = true;
            out.add(new DatagramPacket(buf, recipient, null));
        } finally {
            if (!success) {
                buf.release();
            }
        }
    }

    protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, AddressedEnvelope<DnsQuery, InetSocketAddress> addressedEnvelope) throws Exception {
        return ctx.alloc().ioBuffer(1024);
    }

    private static void encodeHeader(DnsQuery query, ByteBuf buf) {
        buf.writeShort(query.id());
        int flags = 0 | ((query.opCode().byteValue() & 255) << 14);
        if (query.isRecursionDesired()) {
            flags |= 256;
        }
        buf.writeShort(flags);
        buf.writeShort(query.count(DnsSection.QUESTION));
        buf.writeShort(0);
        buf.writeShort(0);
        buf.writeShort(query.count(DnsSection.ADDITIONAL));
    }

    private void encodeQuestions(DnsQuery query, ByteBuf buf) throws Exception {
        int count = query.count(DnsSection.QUESTION);
        for (int i = 0; i < count; i++) {
            this.recordEncoder.encodeQuestion((DnsQuestion) query.recordAt(DnsSection.QUESTION, i), buf);
        }
    }

    private void encodeRecords(DnsQuery query, DnsSection section, ByteBuf buf) throws Exception {
        int count = query.count(section);
        for (int i = 0; i < count; i++) {
            this.recordEncoder.encodeRecord(query.recordAt(section, i), buf);
        }
    }
}
