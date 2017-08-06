package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.ObjectUtil;
import java.net.InetSocketAddress;
import java.util.List;

@Sharable
public class DatagramDnsQueryDecoder extends MessageToMessageDecoder<DatagramPacket> {
    private final DnsRecordDecoder recordDecoder;

    public DatagramDnsQueryDecoder() {
        this(DnsRecordDecoder.DEFAULT);
    }

    public DatagramDnsQueryDecoder(DnsRecordDecoder recordDecoder) {
        this.recordDecoder = (DnsRecordDecoder) ObjectUtil.checkNotNull(recordDecoder, "recordDecoder");
    }

    protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
        ByteBuf buf = (ByteBuf) packet.content();
        DnsQuery query = newQuery(packet, buf);
        boolean success = false;
        try {
            int questionCount = buf.readUnsignedShort();
            int answerCount = buf.readUnsignedShort();
            int authorityRecordCount = buf.readUnsignedShort();
            int additionalRecordCount = buf.readUnsignedShort();
            decodeQuestions(query, buf, questionCount);
            decodeRecords(query, DnsSection.ANSWER, buf, answerCount);
            decodeRecords(query, DnsSection.AUTHORITY, buf, authorityRecordCount);
            decodeRecords(query, DnsSection.ADDITIONAL, buf, additionalRecordCount);
            out.add(query);
            success = true;
        } finally {
            if (!success) {
                query.release();
            }
        }
    }

    private static DnsQuery newQuery(DatagramPacket packet, ByteBuf buf) {
        int id = buf.readUnsignedShort();
        int flags = buf.readUnsignedShort();
        if ((flags >> 15) == 1) {
            throw new CorruptedFrameException("not a_isRightVersion query");
        }
        DnsQuery query = new DatagramDnsQuery((InetSocketAddress) packet.sender(), (InetSocketAddress) packet.recipient(), id, DnsOpCode.valueOf((byte) ((flags >> 11) & 15)));
        query.setRecursionDesired(((flags >> 8) & 1) == 1);
        query.setZ((flags >> 4) & 7);
        return query;
    }

    private void decodeQuestions(DnsQuery query, ByteBuf buf, int questionCount) throws Exception {
        for (int i = questionCount; i > 0; i--) {
            query.addRecord(DnsSection.QUESTION, this.recordDecoder.decodeQuestion(buf));
        }
    }

    private void decodeRecords(DnsQuery query, DnsSection section, ByteBuf buf, int count) throws Exception {
        int i = count;
        while (i > 0) {
            DnsRecord r = this.recordDecoder.decodeRecord(buf);
            if (r != null) {
                query.addRecord(section, r);
                i--;
            } else {
                return;
            }
        }
    }
}
