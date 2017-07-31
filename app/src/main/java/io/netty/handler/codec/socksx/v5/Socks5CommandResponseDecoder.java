package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socksx.SocksVersion;
import java.util.List;

public class Socks5CommandResponseDecoder extends ReplayingDecoder<State> {
    private final Socks5AddressDecoder addressDecoder;

    enum State {
        INIT,
        SUCCESS,
        FAILURE
    }

    public Socks5CommandResponseDecoder() {
        this(Socks5AddressDecoder.DEFAULT);
    }

    public Socks5CommandResponseDecoder(Socks5AddressDecoder addressDecoder) {
        super(State.INIT);
        if (addressDecoder == null) {
            throw new NullPointerException("addressDecoder");
        }
        this.addressDecoder = addressDecoder;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            switch ((State) state()) {
                case INIT:
                    byte version = in.readByte();
                    if (version == SocksVersion.SOCKS5.byteValue()) {
                        Socks5CommandStatus status = Socks5CommandStatus.valueOf(in.readByte());
                        in.skipBytes(1);
                        Socks5AddressType addrType = Socks5AddressType.valueOf(in.readByte());
                        out.add(new DefaultSocks5CommandResponse(status, addrType, this.addressDecoder.decodeAddress(addrType, in), in.readUnsignedShort()));
                        checkpoint(State.SUCCESS);
                        break;
                    }
                    throw new DecoderException("unsupported version: " + version + " (expected: " + SocksVersion.SOCKS5.byteValue() + ')');
                case SUCCESS:
                    break;
                case FAILURE:
                    in.skipBytes(actualReadableBytes());
                    return;
                default:
                    return;
            }
            int readableBytes = actualReadableBytes();
            if (readableBytes > 0) {
                out.add(in.readRetainedSlice(readableBytes));
            }
        } catch (Exception e) {
            fail(out, e);
        }
    }

    private void fail(List<Object> out, Throwable cause) {
        if (!(cause instanceof DecoderException)) {
            cause = new DecoderException(cause);
        }
        checkpoint(State.FAILURE);
        Socks5Message m = new DefaultSocks5CommandResponse(Socks5CommandStatus.FAILURE, Socks5AddressType.IPv4, null, 0);
        m.setDecoderResult(DecoderResult.failure(cause));
        out.add(m);
    }
}
