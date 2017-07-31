package io.netty.handler.codec.socksx.v4;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;
import java.util.List;

public class Socks4ServerDecoder extends ReplayingDecoder<State> {
    private static final int MAX_FIELD_LENGTH = 255;
    private String dstAddr;
    private int dstPort;
    private Socks4CommandType type;
    private String userId;

    enum State {
        START,
        READ_USERID,
        READ_DOMAIN,
        SUCCESS,
        FAILURE
    }

    public Socks4ServerDecoder() {
        super(State.START);
        setSingleDecode(true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void decode(io.netty.channel.ChannelHandlerContext r9, io.netty.buffer.ByteBuf r10, java.util.List<java.lang.Object> r11) throws java.lang.Exception {
        /*
        r8 = this;
        r4 = io.netty.handler.codec.socksx.v4.Socks4ServerDecoder.AnonymousClass1.$SwitchMap$io$netty$handler$codec$socksx$v4$Socks4ServerDecoder$State;	 Catch:{ Exception -> 0x0038 }
        r3 = r8.state();	 Catch:{ Exception -> 0x0038 }
        r3 = (io.netty.handler.codec.socksx.v4.Socks4ServerDecoder.State) r3;	 Catch:{ Exception -> 0x0038 }
        r3 = r3.ordinal();	 Catch:{ Exception -> 0x0038 }
        r3 = r4[r3];	 Catch:{ Exception -> 0x0038 }
        switch(r3) {
            case 1: goto L_0x0012;
            case 2: goto L_0x005c;
            case 3: goto L_0x006a;
            case 4: goto L_0x009e;
            case 5: goto L_0x00ad;
            default: goto L_0x0011;
        };	 Catch:{ Exception -> 0x0038 }
    L_0x0011:
        return;
    L_0x0012:
        r2 = r10.readUnsignedByte();	 Catch:{ Exception -> 0x0038 }
        r3 = io.netty.handler.codec.socksx.SocksVersion.SOCKS4a;	 Catch:{ Exception -> 0x0038 }
        r3 = r3.byteValue();	 Catch:{ Exception -> 0x0038 }
        if (r2 == r3) goto L_0x003d;
    L_0x001e:
        r3 = new io.netty.handler.codec.DecoderException;	 Catch:{ Exception -> 0x0038 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0038 }
        r4.<init>();	 Catch:{ Exception -> 0x0038 }
        r5 = "unsupported protocol version: ";
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x0038 }
        r4 = r4.append(r2);	 Catch:{ Exception -> 0x0038 }
        r4 = r4.toString();	 Catch:{ Exception -> 0x0038 }
        r3.<init>(r4);	 Catch:{ Exception -> 0x0038 }
        throw r3;	 Catch:{ Exception -> 0x0038 }
    L_0x0038:
        r0 = move-exception;
        r8.fail(r11, r0);
        goto L_0x0011;
    L_0x003d:
        r3 = r10.readByte();	 Catch:{ Exception -> 0x0038 }
        r3 = io.netty.handler.codec.socksx.v4.Socks4CommandType.valueOf(r3);	 Catch:{ Exception -> 0x0038 }
        r8.type = r3;	 Catch:{ Exception -> 0x0038 }
        r3 = r10.readUnsignedShort();	 Catch:{ Exception -> 0x0038 }
        r8.dstPort = r3;	 Catch:{ Exception -> 0x0038 }
        r3 = r10.readInt();	 Catch:{ Exception -> 0x0038 }
        r3 = io.netty.util.NetUtil.intToIpAddress(r3);	 Catch:{ Exception -> 0x0038 }
        r8.dstAddr = r3;	 Catch:{ Exception -> 0x0038 }
        r3 = io.netty.handler.codec.socksx.v4.Socks4ServerDecoder.State.READ_USERID;	 Catch:{ Exception -> 0x0038 }
        r8.checkpoint(r3);	 Catch:{ Exception -> 0x0038 }
    L_0x005c:
        r3 = "userid";
        r3 = readString(r3, r10);	 Catch:{ Exception -> 0x0038 }
        r8.userId = r3;	 Catch:{ Exception -> 0x0038 }
        r3 = io.netty.handler.codec.socksx.v4.Socks4ServerDecoder.State.READ_DOMAIN;	 Catch:{ Exception -> 0x0038 }
        r8.checkpoint(r3);	 Catch:{ Exception -> 0x0038 }
    L_0x006a:
        r3 = "0.0.0.0";
        r4 = r8.dstAddr;	 Catch:{ Exception -> 0x0038 }
        r3 = r3.equals(r4);	 Catch:{ Exception -> 0x0038 }
        if (r3 != 0) goto L_0x0089;
    L_0x0075:
        r3 = r8.dstAddr;	 Catch:{ Exception -> 0x0038 }
        r4 = "0.0.0.";
        r3 = r3.startsWith(r4);	 Catch:{ Exception -> 0x0038 }
        if (r3 == 0) goto L_0x0089;
    L_0x0080:
        r3 = "dstAddr";
        r3 = readString(r3, r10);	 Catch:{ Exception -> 0x0038 }
        r8.dstAddr = r3;	 Catch:{ Exception -> 0x0038 }
    L_0x0089:
        r3 = new io.netty.handler.codec.socksx.v4.DefaultSocks4CommandRequest;	 Catch:{ Exception -> 0x0038 }
        r4 = r8.type;	 Catch:{ Exception -> 0x0038 }
        r5 = r8.dstAddr;	 Catch:{ Exception -> 0x0038 }
        r6 = r8.dstPort;	 Catch:{ Exception -> 0x0038 }
        r7 = r8.userId;	 Catch:{ Exception -> 0x0038 }
        r3.<init>(r4, r5, r6, r7);	 Catch:{ Exception -> 0x0038 }
        r11.add(r3);	 Catch:{ Exception -> 0x0038 }
        r3 = io.netty.handler.codec.socksx.v4.Socks4ServerDecoder.State.SUCCESS;	 Catch:{ Exception -> 0x0038 }
        r8.checkpoint(r3);	 Catch:{ Exception -> 0x0038 }
    L_0x009e:
        r1 = r8.actualReadableBytes();	 Catch:{ Exception -> 0x0038 }
        if (r1 <= 0) goto L_0x0011;
    L_0x00a4:
        r3 = r10.readRetainedSlice(r1);	 Catch:{ Exception -> 0x0038 }
        r11.add(r3);	 Catch:{ Exception -> 0x0038 }
        goto L_0x0011;
    L_0x00ad:
        r3 = r8.actualReadableBytes();	 Catch:{ Exception -> 0x0038 }
        r10.skipBytes(r3);	 Catch:{ Exception -> 0x0038 }
        goto L_0x0011;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.socksx.v4.Socks4ServerDecoder.decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }

    private void fail(List<Object> out, Throwable cause) {
        if (!(cause instanceof DecoderException)) {
            cause = new DecoderException(cause);
        }
        Socks4CommandRequest m = new DefaultSocks4CommandRequest(this.type != null ? this.type : Socks4CommandType.CONNECT, this.dstAddr != null ? this.dstAddr : "", this.dstPort != 0 ? this.dstPort : 65535, this.userId != null ? this.userId : "");
        m.setDecoderResult(DecoderResult.failure(cause));
        out.add(m);
        checkpoint(State.FAILURE);
    }

    private static String readString(String fieldName, ByteBuf in) {
        int length = in.bytesBefore(256, (byte) 0);
        if (length < 0) {
            throw new DecoderException("field '" + fieldName + "' longer than " + 255 + " chars");
        }
        String value = in.readSlice(length).toString(CharsetUtil.US_ASCII);
        in.skipBytes(1);
        return value;
    }
}
