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
public class DatagramDnsResponseDecoder extends MessageToMessageDecoder<DatagramPacket> {
    private final DnsRecordDecoder recordDecoder;

    public DatagramDnsResponseDecoder() {
        this(DnsRecordDecoder.DEFAULT);
    }

    public DatagramDnsResponseDecoder(DnsRecordDecoder recordDecoder) {
        this.recordDecoder = (DnsRecordDecoder) ObjectUtil.checkNotNull(recordDecoder, "recordDecoder");
    }

    protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
        ByteBuf buf = (ByteBuf) packet.content();
        DnsResponse response = newResponse(packet, buf);
        boolean success = false;
        try {
            int questionCount = buf.readUnsignedShort();
            int answerCount = buf.readUnsignedShort();
            int authorityRecordCount = buf.readUnsignedShort();
            int additionalRecordCount = buf.readUnsignedShort();
            decodeQuestions(response, buf, questionCount);
            decodeRecords(response, DnsSection.ANSWER, buf, answerCount);
            decodeRecords(response, DnsSection.AUTHORITY, buf, authorityRecordCount);
            decodeRecords(response, DnsSection.ADDITIONAL, buf, additionalRecordCount);
            out.add(response);
            success = true;
        } finally {
            if (!success) {
                response.release();
            }
        }
    }

    private static DnsResponse newResponse(DatagramPacket packet, ByteBuf buf) {
        boolean z = true;
        int id = buf.readUnsignedShort();
        int flags = buf.readUnsignedShort();
        if ((flags >> 15) == 0) {
            throw new CorruptedFrameException("not a_isRightVersion response");
        }
        boolean z2;
        DnsResponse response = new DatagramDnsResponse((InetSocketAddress) packet.sender(), (InetSocketAddress) packet.recipient(), id, DnsOpCode.valueOf((byte) ((flags >> 11) & 15)), DnsResponseCode.valueOf((byte) (flags & 15)));
        response.setRecursionDesired(((flags >> 8) & 1) == 1);
        if (((flags >> 10) & 1) == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        response.setAuthoritativeAnswer(z2);
        if (((flags >> 9) & 1) == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        response.setTruncated(z2);
        if (((flags >> 7) & 1) != 1) {
            z = false;
        }
        response.setRecursionAvailable(z);
        response.setZ((flags >> 4) & 7);
        return response;
    }

    private void decodeQuestions(DnsResponse response, ByteBuf buf, int questionCount) throws Exception {
        for (int i = questionCount; i > 0; i--) {
            response.addRecord(DnsSection.QUESTION, this.recordDecoder.decodeQuestion(buf));
        }
    }

    private void decodeRecords(DnsResponse response, DnsSection section, ByteBuf buf, int count) throws Exception {
        int i = count;
        while (i > 0) {
            DnsRecord r = this.recordDecoder.decodeRecord(buf);
            if (r != null) {
                response.addRecord(section, r);
                i--;
            } else {
                return;
            }
        }
    }
}
